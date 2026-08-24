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
 *  org.joml.Matrix3x2fStack
 */
package dev.sixseven.gui;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.picker.PickerGrid;
import dev.sixseven.gui.widget.ColorWidget;
import dev.sixseven.render.NvgDrawable;
import dev.sixseven.render.OverlayRenderer;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.class_11905;
import net.minecraft.class_11908;
import net.minecraft.class_11909;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.joml.Matrix3x2fStack;

public class IconPickerScreen
extends class_437
implements NvgDrawable {
    private static final float HEADER_H = 42.0f;
    private static final float SEARCH_H = 34.0f;
    private static final float PAD = 12.0f;
    private static final float CELL = 34.0f;
    private static final float SEL_CHIP_W = 116.0f;
    private static final float PANEL_RADIUS = 14.0f;
    private final class_437 parent;
    private final PickerGrid model;
    private final ThemeManager themes;
    private final StringBuilder search = new StringBuilder();
    private boolean searchFocused;
    private boolean selectedOnly;
    private List<PickerGrid.Cell> filtered = List.of();
    private String lastQuery = null;
    private boolean lastSelectedOnly;
    private boolean filterDirty = true;
    private float scroll;
    private float maxScroll;
    private ColorSetting colorSetting;
    private String colorTitle;
    private ColorWidget colorWidget;
    private final float[] popupRect = new float[4];
    private final float[] closeRect = new float[4];
    private final float[] searchRect = new float[4];
    private final float[] selChipRect = new float[4];

    public IconPickerScreen(class_437 parent, PickerGrid model, ThemeManager themes) {
        super((class_2561)class_2561.method_43470((String)model.title()));
        this.parent = parent;
        this.model = model;
        this.themes = themes;
    }

    private void refreshFilter() {
        String q = this.search.toString().trim().toLowerCase(Locale.ROOT);
        if (!this.filterDirty && q.equals(this.lastQuery) && this.selectedOnly == this.lastSelectedOnly) {
            return;
        }
        this.lastQuery = q;
        this.lastSelectedOnly = this.selectedOnly;
        this.filterDirty = false;
        ArrayList<PickerGrid.Cell> out = new ArrayList<PickerGrid.Cell>();
        for (PickerGrid.Cell cell : this.model.cells()) {
            if (this.selectedOnly && !cell.selected() || !q.isEmpty() && !cell.matches(q)) continue;
            out.add(cell);
        }
        this.filtered = out;
        this.scroll = 0.0f;
    }

    private Layout layout() {
        float uiW = OverlayRenderer.uiWidth();
        float uiH = OverlayRenderer.uiHeight();
        float panelW = Math.min(uiW * 0.82f, 940.0f);
        float panelH = Math.min(uiH * 0.84f, 660.0f);
        float px = (uiW - panelW) / 2.0f;
        float py = (uiH - panelH) / 2.0f;
        float gridX = px + 12.0f;
        float gridY = py + 42.0f + 34.0f;
        float gridW = panelW - 24.0f;
        float gridH = panelH - 42.0f - 34.0f - 12.0f;
        int cols = Math.max(1, (int)(gridW / 34.0f));
        float cell = gridW / (float)cols;
        return new Layout(px, py, panelW, panelH, gridX, gridY, gridW, gridH, cols, cell, cell - 11.0f);
    }

    private float contentHeight(Layout l) {
        int rows = (this.filtered.size() + l.cols() - 1) / l.cols();
        return (float)rows * l.cell();
    }

    public void method_25394(class_332 gg, int mouseX, int mouseY, float partialTick) {
        this.refreshFilter();
        Layout l = this.layout();
        this.maxScroll = Math.max(0.0f, this.contentHeight(l) - l.gridH());
        this.scroll = Math.clamp(this.scroll, 0.0f, this.maxScroll);
        float uiScale = OverlayRenderer.uiScale();
        double guiScale = this.field_22787.method_22683().method_4495();
        float k = (float)((double)uiScale / guiScale);
        gg.method_25294(0, 0, this.field_22789, this.field_22790, -670694391);
        IconPickerScreen.fillRoundedRectV(gg, l.px() * k, l.py() * k, l.panelW() * k, l.panelH() * k, 14.0f * k, -132771297, -133560304);
        gg.method_44379(IconPickerScreen.gi(l.gridX() * k), IconPickerScreen.gi(l.gridY() * k), IconPickerScreen.gi((l.gridX() + l.gridW()) * k), IconPickerScreen.gi((l.gridY() + l.gridH()) * k));
        int first = Math.max(0, (int)(this.scroll / l.cell()) * l.cols());
        int perView = l.cols() * ((int)(l.gridH() / l.cell()) + 3);
        int last = Math.min(this.filtered.size(), first + perView);
        Matrix3x2fStack pose = gg.method_51448();
        for (int i = first; i < last; ++i) {
            PickerGrid.Cell cell = this.filtered.get(i);
            int col = i % l.cols();
            int row = i / l.cols();
            float cx = l.gridX() + (float)col * l.cell() + (l.cell() - l.icon()) / 2.0f;
            float cy = l.gridY() - this.scroll + (float)row * l.cell() + (l.cell() - l.icon()) / 2.0f;
            float sc = l.icon() * k / 16.0f;
            pose.pushMatrix();
            pose.translate(cx * k, cy * k);
            pose.scale(sc, sc);
            gg.method_51427(cell.icon(), 0, 0);
            pose.popMatrix();
        }
        gg.method_44380();
    }

    private static int gi(float v) {
        return Math.round(v);
    }

    private static void fillRoundedRectV(class_332 gg, float x, float y, float w, float h, float radius, int top, int bottom) {
        int x0 = Math.round(x);
        int y0 = Math.round(y);
        int x1 = Math.round(x + w);
        int y1 = Math.round(y + h);
        int height = y1 - y0;
        int width = x1 - x0;
        if (height <= 0 || width <= 0) {
            return;
        }
        int r = Math.min(Math.round(radius), Math.min(width, height) / 2);
        for (int row = 0; row < height; ++row) {
            int inset = 0;
            if (r > 0) {
                int dy;
                int n = row < r ? row : (dy = row >= height - r ? height - 1 - row : -1);
                if (dy >= 0) {
                    double off = (double)(r - dy) - 0.5;
                    inset = (int)Math.round((double)r - Math.sqrt(Math.max(0.0, (double)r * (double)r - off * off)));
                }
            }
            int color = Colors.lerp(top, bottom, height <= 1 ? 0.0f : (float)row / (float)(height - 1));
            gg.method_25294(x0 + inset, y0 + row, x1 - inset, y0 + row + 1, color);
        }
    }

    @Override
    public void renderNvg(NVGRenderer vg, float mouseX, float mouseY, float uiWidth, float uiHeight) {
        float chipX;
        if (!vg.hasFont()) {
            return;
        }
        Theme theme = this.themes.current();
        Layout l = this.layout();
        boolean blink = System.nanoTime() / 400000000L % 2L == 0L;
        vg.glow(l.px(), l.py(), l.panelW(), l.panelH(), 14.0f, 17.0f, Colors.withAlpha(-16777216, 0.45f));
        vg.glow(l.px(), l.py(), l.panelW(), l.panelH(), 14.0f, 8.0f, Colors.withAlpha(theme.accent(), 0.28f));
        vg.rectOutline(l.px(), l.py(), l.panelW(), l.panelH(), 14.0f, 1.4f, Colors.withAlpha(theme.accentBright(), 0.75f));
        vg.rectOutline(l.px() + 2.2f, l.py() + 2.2f, l.panelW() - 4.4f, l.panelH() - 4.4f, 11.8f, 1.0f, Colors.withAlpha(theme.accentBright(), 0.13f));
        vg.textGradient(this.model.title(), l.px() + 12.0f, l.py() + 16.0f, 16.0f, theme.accentBright(), theme.accent());
        String hint = "Left-click: toggle   \u00b7   Right-click: color   \u00b7   " + this.model.activeCount() + " active";
        vg.text(hint, l.px() + 12.0f, l.py() + 31.0f, 11.0f, theme.textDisabled());
        float closeCx = l.px() + l.panelW() - 12.0f - 3.0f;
        float closeCy = l.py() + 18.0f;
        boolean closeHover = Math.abs(mouseX - closeCx) < 10.0f && Math.abs(mouseY - closeCy) < 10.0f;
        vg.cross(closeCx - 6.0f, closeCy - 6.0f, 12.0f, 1.8f, closeHover ? theme.accentBright() : theme.textMuted());
        this.closeRect[0] = closeCx - 10.0f;
        this.closeRect[1] = closeCy - 10.0f;
        this.closeRect[2] = closeCx + 10.0f;
        this.closeRect[3] = closeCy + 10.0f;
        float sx = l.px() + 12.0f;
        float sy = l.py() + 42.0f + 4.0f;
        float sh = 22.0f;
        float sw = l.panelW() - 24.0f - 116.0f - 8.0f;
        this.searchRect[0] = sx;
        this.searchRect[1] = sy;
        this.searchRect[2] = sx + sw;
        this.searchRect[3] = sy + sh;
        vg.rect(sx, sy, sw, sh, sh / 2.0f, this.searchFocused ? Colors.withAlpha(theme.accent(), 0.16f) : Colors.withAlpha(-16777216, 0.4f));
        vg.rectOutline(sx, sy, sw, sh, sh / 2.0f, 1.1f, Colors.withAlpha(this.searchFocused ? theme.accentBright() : theme.accent(), this.searchFocused ? 0.9f : 0.35f));
        float ix = sx + 12.0f;
        float iy = sy + sh / 2.0f;
        vg.circleOutline(ix, iy - 1.0f, 4.0f, 1.4f, theme.textMuted());
        vg.line(ix + 3.0f, iy + 2.0f, ix + 6.0f, iy + 5.0f, 1.4f, theme.textMuted());
        if (this.search.length() == 0 && !this.searchFocused) {
            vg.text("Search\u2026  (" + this.filtered.size() + " shown)", sx + 24.0f, iy, 12.5f, theme.textDisabled());
        } else {
            float w = vg.text(this.search.toString(), sx + 24.0f, iy, 12.5f, theme.textPrimary());
            if (this.searchFocused && blink) {
                vg.rect(sx + 24.0f + w + 1.5f, iy - 6.0f, 1.4f, 12.0f, 0.7f, theme.accentBright());
            }
        }
        this.selChipRect[0] = chipX = sx + sw + 8.0f;
        this.selChipRect[1] = sy;
        this.selChipRect[2] = chipX + 116.0f;
        this.selChipRect[3] = sy + sh;
        boolean chipHover = IconPickerScreen.inRect(mouseX, mouseY, this.selChipRect);
        vg.rect(chipX, sy, 116.0f, sh, sh / 2.0f, this.selectedOnly ? Colors.withAlpha(theme.accent(), 0.22f) : Colors.withAlpha(-16777216, 0.4f));
        vg.rectOutline(chipX, sy, 116.0f, sh, sh / 2.0f, 1.1f, Colors.withAlpha(this.selectedOnly || chipHover ? theme.accentBright() : theme.accent(), this.selectedOnly ? 0.9f : 0.4f));
        float dotX = chipX + 13.0f;
        float dotY = sy + sh / 2.0f;
        if (this.selectedOnly) {
            vg.circle(dotX, dotY, 4.0f, theme.statusEnabled());
            vg.circleGlow(dotX, dotY, 4.0f, 4.0f, Colors.withAlpha(theme.statusEnabled(), 0.5f));
        } else {
            vg.circleOutline(dotX, dotY, 4.0f, 1.4f, theme.textMuted());
        }
        vg.text(Deobf.decrypt(" I$\u000bq\u00b0\u00a9\u008e\u00e9\u0114\u0107\u010f\u013a"), chipX + 24.0f, dotY, 11.5f, this.selectedOnly ? theme.textPrimary() : theme.textMuted());
        vg.save();
        vg.scissor(l.gridX(), l.gridY(), l.gridW(), l.gridH());
        int first = Math.max(0, (int)(this.scroll / l.cell()) * l.cols());
        int perView = l.cols() * ((int)(l.gridH() / l.cell()) + 3);
        int last = Math.min(this.filtered.size(), first + perView);
        int hovered = this.cellAt(mouseX, mouseY, l);
        for (int i = first; i < last; ++i) {
            PickerGrid.Cell cell = this.filtered.get(i);
            int col = i % l.cols();
            int row = i / l.cols();
            float cellX = l.gridX() + (float)col * l.cell();
            float cellY = l.gridY() - this.scroll + (float)row * l.cell();
            boolean tracked = cell.tracked();
            boolean active = cell.enabled();
            if (i == hovered) {
                vg.rect(cellX + 1.0f, cellY + 1.0f, l.cell() - 2.0f, l.cell() - 2.0f, 6.0f, Colors.withAlpha(theme.accent(), 0.12f));
            }
            if (!tracked) continue;
            int base = cell.color();
            int ring = active ? base : Colors.withAlpha(base, 0.35f);
            vg.rectOutline(cellX + 1.5f, cellY + 1.5f, l.cell() - 3.0f, l.cell() - 3.0f, 6.0f, active ? 1.8f : 1.0f, ring | (active ? -16777216 : 0));
            vg.rect(cellX + 4.0f, cellY + l.cell() - 5.0f, l.cell() - 8.0f, 2.5f, 1.0f, base | 0xFF000000);
            if (!active) continue;
            vg.circle(cellX + l.cell() - 6.0f, cellY + 6.0f, 2.6f, theme.statusEnabled());
        }
        vg.restore();
        if (this.filtered.isEmpty() && this.selectedOnly) {
            vg.text(Deobf.decrypt("=C<\u0006{\u00aa\u00ab\u00ca\u00ba\u011e\u0105\u0106\u0120\u0184\u01f6\u01d0\u01be\u0202\u0214\u0241\u027c\u22ed\u02d5\u02d6\u02ae\u030c\u0317\u035c\u034e\u03d6\u03f3\u03d6\u03fb\u03ee\u044a\u0422\u040a\u046e\u04dd\u04b2\u04e3\u04a1\u054a\u056a\u0554\u0555\u0586\u05a6\u05c7\u05a1\u064b\u0621\u064a\u065b\u0685\u06ea\u06df\u0696"), l.gridX() + 4.0f, l.gridY() + 16.0f, 12.5f, theme.textDisabled());
        }
        if (this.maxScroll > 0.0f) {
            float trackX = l.px() + l.panelW() - 6.0f;
            float thumbH = Math.max(24.0f, l.gridH() * (l.gridH() / this.contentHeight(l)));
            float thumbY = l.gridY() + (l.gridH() - thumbH) * (this.scroll / this.maxScroll);
            vg.rect(trackX, thumbY, 3.0f, thumbH, 1.5f, Colors.withAlpha(theme.accent(), 0.55f));
        }
        if (this.colorSetting != null && this.colorWidget != null) {
            this.renderColorPopup(vg, theme);
        } else {
            this.popupRect[3] = 0.0f;
            this.popupRect[2] = 0.0f;
            this.popupRect[1] = 0.0f;
            this.popupRect[0] = 0.0f;
        }
    }

    private void renderColorPopup(NVGRenderer vg, Theme theme) {
        this.colorWidget.setExpanded(true);
        float cardW = 220.0f;
        float titleH = 26.0f;
        float uiW = OverlayRenderer.uiWidth();
        float uiH = OverlayRenderer.uiHeight();
        float wx = (uiW - cardW) / 2.0f + 12.0f;
        float bodyH = this.colorWidget.height(vg);
        float cardH = titleH + bodyH + 12.0f;
        float cardX = (uiW - cardW) / 2.0f;
        float cardY = (uiH - cardH) / 2.0f;
        this.popupRect[0] = cardX;
        this.popupRect[1] = cardY;
        this.popupRect[2] = cardX + cardW;
        this.popupRect[3] = cardY + cardH;
        vg.glow(cardX, cardY, cardW, cardH, 12.0f, 14.0f, Colors.withAlpha(theme.accent(), 0.3f));
        vg.rectGradient(cardX, cardY, cardW, cardH, 12.0f, theme.background(), theme.backgroundTo(), true);
        vg.rectOutline(cardX, cardY, cardW, cardH, 12.0f, 1.4f, Colors.withAlpha(theme.accentBright(), 0.7f));
        vg.textGradient(this.colorTitle, cardX + 12.0f, cardY + 14.0f, 12.5f, theme.accentBright(), theme.accent());
        this.colorWidget.setBounds(wx, cardY + titleH, cardW - 24.0f);
        this.colorWidget.render(vg, OverlayRenderer.uiMouseX(), OverlayRenderer.uiMouseY());
    }

    private int cellAt(float mx, float my, Layout l) {
        if (mx < l.gridX() || mx > l.gridX() + l.gridW() || my < l.gridY() || my > l.gridY() + l.gridH()) {
            return -1;
        }
        int col = (int)((mx - l.gridX()) / l.cell());
        int row = (int)((my - (l.gridY() - this.scroll)) / l.cell());
        if (col < 0 || col >= l.cols() || row < 0) {
            return -1;
        }
        int index = row * l.cols() + col;
        return index >= 0 && index < this.filtered.size() ? index : -1;
    }

    private float ux(double guiX) {
        return OverlayRenderer.guiToUi(guiX);
    }

    public boolean method_25402(class_11909 event, boolean doubled) {
        float mx = this.ux(event.comp_4798());
        float my = this.ux(event.comp_4799());
        Layout l = this.layout();
        if (this.colorSetting != null) {
            if (IconPickerScreen.inRect(mx, my, this.popupRect)) {
                this.colorWidget.mouseClicked(mx, my, event.method_74245());
            } else {
                this.closeColor();
                UiSounds.select();
            }
            return true;
        }
        if (IconPickerScreen.inRect(mx, my, this.closeRect)) {
            this.method_25419();
            return true;
        }
        if (IconPickerScreen.inRect(mx, my, this.selChipRect)) {
            this.selectedOnly = !this.selectedOnly;
            this.filterDirty = true;
            this.searchFocused = false;
            UiSounds.toggle(this.selectedOnly);
            return true;
        }
        if (IconPickerScreen.inRect(mx, my, this.searchRect)) {
            this.searchFocused = true;
            UiSounds.select();
            return true;
        }
        this.searchFocused = false;
        if (mx < l.px() || mx > l.px() + l.panelW() || my < l.py() || my > l.py() + l.panelH()) {
            this.method_25419();
            return true;
        }
        int index = this.cellAt(mx, my, l);
        if (index >= 0) {
            PickerGrid.Cell cell = this.filtered.get(index);
            if (event.method_74245() == 1) {
                this.openColor(cell.colorTarget(), cell.label());
            } else if (event.method_74245() == 0) {
                boolean wasSelected = cell.selected();
                cell.toggle();
                UiSounds.toggle(cell.enabled());
                if (this.selectedOnly && wasSelected && !cell.selected()) {
                    this.filterDirty = true;
                }
            }
            return true;
        }
        return true;
    }

    private void openColor(ColorSetting target, String title) {
        if (target == null) {
            return;
        }
        this.colorSetting = target;
        this.colorTitle = title;
        this.colorWidget = new ColorWidget(this.themes, target);
        this.colorWidget.setExpanded(true);
        this.searchFocused = false;
        UiSounds.select();
    }

    private void closeColor() {
        this.colorSetting = null;
        this.colorTitle = null;
        this.colorWidget = null;
    }

    public boolean method_25403(class_11909 event, double dx, double dy) {
        if (this.colorSetting != null && this.colorWidget != null) {
            this.colorWidget.mouseDragged(this.ux(event.comp_4798()), this.ux(event.comp_4799()));
        }
        return true;
    }

    public boolean method_25406(class_11909 event) {
        if (this.colorWidget != null) {
            this.colorWidget.mouseReleased();
        }
        return true;
    }

    public boolean method_25401(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.colorSetting == null) {
            this.scroll = Math.clamp(this.scroll - (float)(scrollY * (double)this.layout().cell()), 0.0f, this.maxScroll);
        }
        return true;
    }

    public boolean method_25404(class_11908 event) {
        int key = event.comp_4795();
        if (this.colorSetting != null) {
            if (this.colorWidget != null && this.colorWidget.isListening()) {
                this.colorWidget.keyPressed(key);
                return true;
            }
            if (key == 256) {
                this.closeColor();
                return true;
            }
            return true;
        }
        if (this.searchFocused) {
            switch (key) {
                case 256: 
                case 257: 
                case 335: {
                    this.searchFocused = false;
                    break;
                }
                case 259: {
                    if (this.search.length() <= 0) break;
                    this.search.deleteCharAt(this.search.length() - 1);
                    this.filterDirty = true;
                    break;
                }
            }
            return true;
        }
        if (key == 256) {
            this.method_25419();
            return true;
        }
        return super.method_25404(event);
    }

    public boolean method_25400(class_11905 event) {
        if (this.colorSetting != null) {
            return true;
        }
        if (!this.searchFocused) {
            return true;
        }
        if (this.search.length() >= 48) {
            return true;
        }
        char c = (char)event.comp_4793();
        if (c == ' ' || c == '_' || c == ':' || c == '/' || c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
            this.search.append(Character.toLowerCase(c));
            this.filterDirty = true;
        }
        return true;
    }

    public void method_25419() {
        SixSevenClient.config().save();
        this.field_22787.method_1507(this.parent);
    }

    public boolean method_25421() {
        return false;
    }

    public void debugSetSearch(String query) {
        this.search.setLength(0);
        this.search.append(query);
        this.searchFocused = true;
        this.filterDirty = true;
        this.refreshFilter();
    }

    public void debugSetSelectedOnly(boolean value) {
        this.selectedOnly = value;
        this.filterDirty = true;
        this.refreshFilter();
    }

    public void debugOpenColor(int filteredIndex) {
        if (filteredIndex < 0 || filteredIndex >= this.filtered.size()) {
            return;
        }
        PickerGrid.Cell cell = this.filtered.get(filteredIndex);
        this.openColor(cell.colorTarget(), cell.label());
    }

    public int debugFilteredCount() {
        return this.filtered.size();
    }

    private static boolean inRect(float mx, float my, float[] r) {
        return mx >= r[0] && mx <= r[2] && my >= r[1] && my <= r[3];
    }

    private record Layout(float px, float py, float panelW, float panelH, float gridX, float gridY, float gridW, float gridH, int cols, float cell, float icon) {
    }
}

