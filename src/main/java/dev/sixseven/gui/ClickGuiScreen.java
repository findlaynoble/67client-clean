/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_11905
 *  net.minecraft.class_11908
 *  net.minecraft.class_11909
 *  net.minecraft.class_2561
 *  net.minecraft.class_332
 *  net.minecraft.class_437
 */
package dev.sixseven.gui;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.ClickGuiState;
import dev.sixseven.gui.IconPickerScreen;
import dev.sixseven.gui.config.ConfigPanel;
import dev.sixseven.gui.panel.CategoryPanel;
import dev.sixseven.gui.panel.Panel;
import dev.sixseven.gui.panel.ThemesPanel;
import dev.sixseven.gui.picker.BlockGridModel;
import dev.sixseven.gui.picker.IconListGridModel;
import dev.sixseven.module.Category;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.Modules;
import dev.sixseven.module.impl.BlockEspModule;
import dev.sixseven.render.BlurHook;
import dev.sixseven.render.NvgDrawable;
import dev.sixseven.render.OverlayRenderer;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BlockListSetting;
import dev.sixseven.settings.IconListSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_11905;
import net.minecraft.class_11908;
import net.minecraft.class_11909;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class ClickGuiScreen
extends class_437
implements NvgDrawable {
    private static final ClickGuiState STATE = new ClickGuiState();
    private final List<Panel> panels = new ArrayList<Panel>();
    private final Animation openAnim = new Animation(180.0f, 0.0f);
    private boolean closing;
    private final ConfigPanel configPanel = new ConfigPanel();
    private static final float FIDGET_W = 118.0f;
    private static final float FIDGET_H = 28.0f;
    private boolean fidgetHovered;
    private static final float SEARCH_W = 280.0f;
    private static final float SEARCH_H = 32.0f;
    private final StringBuilder search = new StringBuilder();
    private boolean searchFocused;
    private Panel dragging;
    private float dragOffsetX;
    private float dragOffsetY;
    private float pressX;
    private float pressY;
    private boolean dragMoved;
    private Panel pressedContentPanel;
    private final class_437 parent;

    public ClickGuiScreen() {
        this(null);
    }

    public ClickGuiScreen(class_437 parent) {
        super((class_2561)class_2561.method_43470((String)Deobf.decrypt("E\u001b\u000b\u0002{\u00a1\u00a2\u009e\u00e9\u0138\u0105\u010a\u0120\u019b\u01d4\u01e1\u01d7")));
        this.parent = parent;
        STATE.ensureDefaultLayout(OverlayRenderer.uiWidth(), OverlayRenderer.uiHeight());
        ModuleManager modules = SixSevenClient.modules();
        ThemeManager themes = SixSevenClient.themes();
        for (Category category : Category.values()) {
            this.panels.add(new CategoryPanel(category, modules, themes, STATE));
        }
        this.panels.add(new ThemesPanel(themes, STATE));
        this.openAnim.setTarget(1.0f);
    }

    public void method_49589() {
        super.method_49589();
        UiSounds.guiOpen();
    }

    public static ClickGuiState state() {
        return STATE;
    }

    private Modules.ClickGuiModule guiModule() {
        return SixSevenClient.modules().clickGui;
    }

    public boolean method_25421() {
        return false;
    }

    public void method_25419() {
        if (!this.closing) {
            this.closing = true;
            this.openAnim.setTarget(0.0f);
            UiSounds.guiClose();
        }
    }

    public void method_25432() {
        BlurHook.clear();
        SixSevenClient.config().save();
    }

    public void method_25394(class_332 guiGraphics, int mouseX, int mouseY, float partialTick) {
        int alpha = (int)(48.0f * this.openAnim.value());
        guiGraphics.method_25294(0, 0, this.field_22789, this.field_22790, alpha << 24 | 0x6050A);
        this.finishCloseIfDone();
    }

    public void method_25393() {
        this.finishCloseIfDone();
    }

    private void finishCloseIfDone() {
        if (this.closing && this.openAnim.isDone()) {
            this.field_22787.method_1507(this.parent);
        }
    }

    public void method_25420(class_332 guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.field_22787.field_1687 == null) {
            this.method_57728(guiGraphics, partialTick);
        }
        if (((Boolean)this.guiModule().blur.get()).booleanValue()) {
            BlurHook.set(this.guiModule().blurStrength.getFloat() * this.openAnim.value());
            guiGraphics.method_71278();
        } else {
            BlurHook.clear();
        }
    }

    @Override
    public void renderNvg(NVGRenderer vg, float mouseX, float mouseY, float uiWidth, float uiHeight) {
        vg.setFontMode((String)this.guiModule().font.get());
        if (!vg.hasFont()) {
            return;
        }
        float t = this.openAnim.value();
        if (t <= 0.002f && this.closing) {
            return;
        }
        vg.save();
        vg.alpha(t);
        float scale = 0.96f + 0.04f * t;
        vg.translate(uiWidth / 2.0f, uiHeight / 2.0f);
        vg.scale(scale);
        vg.translate(-uiWidth / 2.0f, -uiHeight / 2.0f);
        String query = this.search.toString();
        for (Panel panel : this.panels) {
            if (!(panel instanceof CategoryPanel)) continue;
            CategoryPanel categoryPanel = (CategoryPanel)panel;
            categoryPanel.setFilter(query);
        }
        for (int i = this.panels.size() - 1; i >= 0; --i) {
            this.panels.get(i).render(vg, mouseX, mouseY, uiWidth, uiHeight);
        }
        this.renderSearchBar(vg, uiWidth);
        this.renderFidget(vg, mouseX, mouseY, uiWidth, uiHeight);
        this.configPanel.render(vg, mouseX, mouseY, uiWidth, uiHeight);
        vg.restore();
    }

    private void renderFidget(NVGRenderer vg, float mouseX, float mouseY, float uiWidth, float uiHeight) {
        boolean lit;
        Theme theme = SixSevenClient.themes().current();
        float fx = ClickGuiScreen.fidgetX(uiWidth);
        float fy = ClickGuiScreen.fidgetY(uiHeight);
        boolean hover = mouseX >= fx && mouseX <= fx + 118.0f && mouseY >= fy && mouseY <= fy + 28.0f;
        boolean bl = lit = hover || this.configPanel.isOpen();
        if (lit != this.fidgetHovered) {
            this.fidgetHovered = lit;
            if (lit) {
                UiSounds.hover();
            }
        }
        vg.glow(fx, fy, 118.0f, 28.0f, 14.0f, lit ? 7.0f : 4.0f, Colors.withAlpha(theme.accent(), lit ? 0.28f : 0.14f));
        vg.rectGradient(fx, fy, 118.0f, 28.0f, 14.0f, theme.headerTop(), theme.headerBottom(), true);
        vg.rectOutline(fx, fy, 118.0f, 28.0f, 14.0f, 1.2f, Colors.withAlpha(lit ? theme.accentBright() : theme.accent(), lit ? 0.9f : 0.4f));
        float gx = fx + 16.0f;
        float gy = fy + 14.0f - 6.0f;
        vg.rect(gx + 3.0f, gy + 3.0f, 11.0f, 9.0f, 2.5f, Colors.withAlpha(theme.accent(), 0.5f));
        vg.rect(gx, gy, 11.0f, 9.0f, 2.5f, lit ? theme.accentBright() : theme.accent());
        vg.text(Deobf.decrypt("0C&\b{\u00a3\u00bf"), fx + 36.0f, fy + 14.0f, 13.5f, lit ? theme.textPrimary() : theme.textMuted());
    }

    private static float fidgetX(float uiWidth) {
        return (uiWidth - 118.0f) / 2.0f;
    }

    private static float fidgetY(float uiHeight) {
        return uiHeight - 28.0f - 14.0f;
    }

    private boolean fidgetHit(float mx, float my) {
        float fx = ClickGuiScreen.fidgetX(OverlayRenderer.uiWidth());
        float fy = ClickGuiScreen.fidgetY(OverlayRenderer.uiHeight());
        return mx >= fx && mx <= fx + 118.0f && my >= fy && my <= fy + 28.0f;
    }

    private void renderSearchBar(NVGRenderer vg, float uiWidth) {
        Theme theme = SixSevenClient.themes().current();
        float sx = (uiWidth - 280.0f) / 2.0f;
        float sy = 19.0f;
        vg.glow(sx, sy, 280.0f, 32.0f, 16.0f, 6.0f, Colors.withAlpha(theme.accent(), this.searchFocused ? 0.25f : 0.12f));
        vg.rectGradient(sx, sy, 280.0f, 32.0f, 16.0f, theme.headerTop(), theme.headerBottom(), true);
        vg.rectOutline(sx, sy, 280.0f, 32.0f, 16.0f, 1.2f, Colors.withAlpha(this.searchFocused ? theme.accentBright() : theme.accent(), this.searchFocused ? 0.9f : 0.4f));
        float ix = sx + 16.0f;
        float iy = sy + 16.0f;
        vg.circleOutline(ix, iy - 1.5f, 4.5f, 1.6f, theme.textMuted());
        vg.line(ix + 3.4f, iy + 2.2f, ix + 6.5f, iy + 5.4f, 1.6f, theme.textMuted());
        float textX = sx + 30.0f;
        if (this.search.isEmpty() && !this.searchFocused) {
            vg.text(Deobf.decrypt(" I)\u001cq\u00ac\u00ec\u0087\u00a6\u011f\u011c\u010f\u0126\u0183\u21b5"), textX, iy, 13.0f, theme.textDisabled());
        } else {
            float w = vg.text(this.search.toString(), textX, iy, 13.0f, theme.textPrimary());
            if (this.searchFocused && System.nanoTime() / 400000000L % 2L == 0L) {
                vg.rect(textX + w + 2.0f, iy - 7.0f, 1.5f, 14.0f, 0.75f, theme.accentBright());
            }
        }
        if (!this.search.isEmpty()) {
            vg.cross(sx + 280.0f - 26.0f, iy - 6.0f, 12.0f, 1.6f, theme.textMuted());
        }
    }

    public void openSearch(String query) {
        this.search.setLength(0);
        this.search.append(query);
        this.searchFocused = true;
    }

    public ConfigPanel configPanel() {
        return this.configPanel;
    }

    private boolean searchBarHit(float mx, float my) {
        float sx = (OverlayRenderer.uiWidth() - 280.0f) / 2.0f;
        return mx >= sx && mx <= sx + 280.0f && my >= 19.0f && my <= 51.0f;
    }

    private float uiX(double guiX) {
        return OverlayRenderer.guiToUi(guiX);
    }

    private float uiY(double guiY) {
        return OverlayRenderer.guiToUi(guiY);
    }

    public boolean method_25402(class_11909 event, boolean doubled) {
        float mx = this.uiX(event.comp_4798());
        float my = this.uiY(event.comp_4799());
        NVGRenderer vg = NVGRenderer.get();
        float uiHeight = OverlayRenderer.uiHeight();
        if (this.configPanel.isOpen()) {
            this.configPanel.mouseClicked(mx, my, event.method_74245());
            return true;
        }
        if (this.fidgetHit(mx, my)) {
            this.configPanel.open();
            UiSounds.guiOpen();
            return true;
        }
        if (this.searchBarHit(mx, my)) {
            float clearX = (OverlayRenderer.uiWidth() + 280.0f) / 2.0f - 26.0f;
            if (!this.search.isEmpty() && mx >= clearX - 4.0f && mx <= clearX + 16.0f) {
                this.search.setLength(0);
            } else {
                this.searchFocused = true;
            }
            UiSounds.select();
            return true;
        }
        this.searchFocused = false;
        for (Panel panel : this.panels) {
            if (panel.headerHit(mx, my)) {
                this.bringToFront(panel);
                if (event.method_74245() == 0) {
                    this.dragging = panel;
                    this.dragOffsetX = mx - panel.x();
                    this.dragOffsetY = my - panel.y();
                    this.pressX = mx;
                    this.pressY = my;
                    this.dragMoved = false;
                } else {
                    panel.toggleCollapsed();
                    UiSounds.panelCollapse();
                }
                return true;
            }
            if (!panel.bodyHit(vg, mx, my, uiHeight)) continue;
            this.bringToFront(panel);
            this.pressedContentPanel = panel;
            panel.mouseClicked(mx, my, event.method_74245());
            return true;
        }
        return true;
    }

    public boolean method_25403(class_11909 event, double dx, double dy) {
        if (this.configPanel.isOpen()) {
            return true;
        }
        float mx = this.uiX(event.comp_4798());
        float my = this.uiY(event.comp_4799());
        if (this.dragging != null) {
            if (Math.abs(mx - this.pressX) + Math.abs(my - this.pressY) > 3.0f) {
                this.dragMoved = true;
            }
            if (this.dragMoved) {
                this.dragging.moveTo(mx - this.dragOffsetX, my - this.dragOffsetY);
            }
            return true;
        }
        if (this.pressedContentPanel != null) {
            this.pressedContentPanel.mouseDragged(mx, my);
            return true;
        }
        return true;
    }

    public boolean method_25406(class_11909 event) {
        if (this.configPanel.isOpen()) {
            return true;
        }
        if (this.dragging != null) {
            if (!this.dragMoved && event.method_74245() == 0) {
                this.dragging.toggleCollapsed();
                UiSounds.panelCollapse();
            } else if (this.dragMoved) {
                STATE.markCustomized();
            }
            this.dragging = null;
            return true;
        }
        if (this.pressedContentPanel != null) {
            this.pressedContentPanel.mouseReleased();
            this.pressedContentPanel = null;
        }
        return true;
    }

    public boolean method_25401(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.configPanel.isOpen()) {
            return true;
        }
        float mx = this.uiX(mouseX);
        float my = this.uiY(mouseY);
        NVGRenderer vg = NVGRenderer.get();
        float uiHeight = OverlayRenderer.uiHeight();
        for (Panel panel : this.panels) {
            if (!panel.bodyHit(vg, mx, my, uiHeight) && !panel.headerHit(mx, my)) continue;
            panel.onScroll(scrollY);
            return true;
        }
        return true;
    }

    public boolean method_25404(class_11908 event) {
        if (this.configPanel.isOpen()) {
            this.configPanel.keyPressed(event.comp_4795());
            return true;
        }
        for (Panel panel : this.panels) {
            if (!panel.isListening()) continue;
            panel.keyPressed(event.comp_4795());
            return true;
        }
        if (this.searchFocused) {
            switch (event.comp_4795()) {
                case 256: {
                    this.search.setLength(0);
                    this.searchFocused = false;
                    break;
                }
                case 257: 
                case 335: {
                    this.searchFocused = false;
                    break;
                }
                case 259: {
                    if (this.search.isEmpty()) break;
                    this.search.deleteCharAt(this.search.length() - 1);
                    break;
                }
            }
            return true;
        }
        if (event.method_74231() || this.guiModule().getKeybind().matches(event.comp_4795())) {
            this.method_25419();
            return true;
        }
        return super.method_25404(event);
    }

    public boolean method_25400(class_11905 event) {
        if (this.configPanel.isOpen()) {
            this.configPanel.charTyped(event.comp_4793());
            return true;
        }
        for (Panel panel : this.panels) {
            if (!panel.isListening()) continue;
            panel.charTyped(event.comp_4793());
            return true;
        }
        if (this.searchFocused && event.method_74227()) {
            if (this.search.length() < 32) {
                this.search.append(event.method_74226());
            }
            return true;
        }
        return super.method_25400(event);
    }

    private void bringToFront(Panel panel) {
        if (this.panels.remove(panel)) {
            this.panels.addFirst(panel);
        }
    }

    public void openBlockPicker(BlockListSetting setting) {
        BlockGridModel model = new BlockGridModel(setting, () -> {
            BlockEspModule blockEsp = SixSevenClient.modules().blockEsp;
            return blockEsp != null ? (Integer)blockEsp.lineColor.get() : -16711736;
        }, Deobf.decrypt("#E+\u00052\u0086\u00a0\u0085\u00aa\u0110"));
        this.field_22787.method_1507((class_437)new IconPickerScreen(this, model, SixSevenClient.themes()));
    }

    public void openIconPicker(IconListSetting setting) {
        IconListGridModel model = new IconListGridModel(setting);
        this.field_22787.method_1507((class_437)new IconPickerScreen(this, model, SixSevenClient.themes()));
    }
}

