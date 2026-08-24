/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.panel;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.ClickGuiState;
import dev.sixseven.gui.panel.Panel;
import dev.sixseven.gui.widget.BooleanWidget;
import dev.sixseven.gui.widget.ColorWidget;
import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.gui.widget.SliderWidget;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.Setting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.theme.SoundSettings;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThemesPanel
extends Panel {
    private static final float ROW_H = 26.0f;
    private static final float ADD_ROW_H = 28.0f;
    private static final float SECTION_H = 24.0f;
    private final ColorWidget accentWidget;
    private final ColorSetting accentProxy;
    private final List<SettingWidget> soundWidgets = new ArrayList<SettingWidget>();
    private static final int STARTUP_FIRST_WIDGET = 5;
    private int hoveredRow = -1;
    private float lastStartY = Float.MIN_VALUE;

    public ThemesPanel(final ThemeManager themes, ClickGuiState state) {
        super(themes, state.panel(Deobf.decrypt(",s<\u0006w\u00a9\u00a9\u0099\u0096\u0124")));
        this.accentProxy = new ColorSetting(this, Deobf.decrypt("2O+\u000b|\u00b0"), Deobf.decrypt("0Y;\u001a}\u00a9\u00ec\u009e\u00a1\u011e\u0104\u0106\u0163\u0191\u01f0\u01d7\u01fb\u0215\u0205\u0215\u023f\u0296\u0299\u02cd\u02a9"), themes.current().accent()){

            @Override
            public void set(Integer value) {
                super.set(value);
                Theme current = themes.current();
                if (current.isCustom()) {
                    current.setAccent((int)(value | 0xFF000000));
                }
            }
        };
        this.accentWidget = new ColorWidget(themes, this.accentProxy);
        SoundSettings sounds = SixSevenClient.sounds();
        if (sounds != null) {
            for (Setting<?> setting : sounds.all()) {
                if (setting instanceof SliderSetting) {
                    SliderSetting slider = (SliderSetting)setting;
                    this.soundWidgets.add(new SliderWidget(themes, slider));
                    continue;
                }
                if (!(setting instanceof BooleanSetting)) continue;
                BooleanSetting bool = (BooleanSetting)setting;
                this.soundWidgets.add(new BooleanWidget(themes, bool));
            }
        }
    }

    @Override
    protected String title() {
        return Deobf.decrypt("'D-\u0003w\u00b7");
    }

    @Override
    protected float contentHeight(NVGRenderer vg) {
        float h = 12.0f + (float)this.themes.getThemes().size() * 26.0f + 28.0f;
        if (this.themes.current().isCustom()) {
            h += this.accentWidget.height(vg) + 6.0f;
        }
        h += 48.0f;
        for (SettingWidget widget : this.soundWidgets) {
            h += widget.height(vg) + 3.0f;
        }
        return h;
    }

    @Override
    protected void renderContent(NVGRenderer vg, float startY, float mx, float my, float viewTop, float viewBottom) {
        this.lastStartY = startY;
        Theme active = this.themes.current();
        float rowY = startY;
        int rowIndex = 0;
        int newHoveredRow = -1;
        for (Theme theme : this.themes.getThemes()) {
            boolean hovered;
            float fade = this.edgeFade(rowY, rowY + 26.0f, viewTop, viewBottom);
            vg.save();
            vg.alpha(fade);
            boolean selected = theme == active;
            boolean bl = hovered = my >= rowY && my <= rowY + 26.0f && mx >= this.ps.x + 6.0f && mx <= this.ps.x + 210.0f - 6.0f;
            if (hovered) {
                newHoveredRow = rowIndex;
            }
            ++rowIndex;
            if (selected || hovered) {
                vg.rect(this.ps.x + 6.0f, rowY, 198.0f, 26.0f, 7.0f, Colors.withAlpha(this.theme().accent(), selected ? 0.16f : 0.08f));
            }
            float sy = rowY + 13.0f;
            vg.circle(this.ps.x + 20.0f, sy, 6.0f, theme.accent());
            if (selected) {
                vg.rectOutline(this.ps.x + 20.0f - 9.0f, sy - 9.0f, 18.0f, 18.0f, 9.0f, 1.5f, this.theme().accentBright());
            }
            vg.text(theme.getName(), this.ps.x + 36.0f, sy, 13.5f, selected ? this.theme().textPrimary() : this.theme().textMuted());
            if (theme.isCustom()) {
                vg.cross(this.ps.x + 210.0f - 28.0f, sy - 6.0f, 12.0f, 1.6f, this.theme().textDisabled());
            }
            vg.restore();
            rowY += 26.0f;
        }
        if (active.isCustom()) {
            this.accentWidget.setBounds(this.ps.x + 14.0f, rowY + 3.0f, 182.0f);
            this.accentWidget.render(vg, mx, my);
            rowY += this.accentWidget.height(vg) + 6.0f;
        }
        float fade = this.edgeFade(rowY, rowY + 28.0f, viewTop, viewBottom);
        vg.save();
        vg.alpha(fade);
        boolean hovered = my >= rowY && my <= rowY + 28.0f - 4.0f && mx >= this.ps.x + 6.0f && mx <= this.ps.x + 210.0f - 6.0f;
        vg.rect(this.ps.x + 6.0f, rowY, 198.0f, 24.0f, 7.0f, Colors.withAlpha(this.theme().accent(), hovered ? 0.22f : 0.12f));
        String label = Deobf.decrypt("X\fh/v\u00a0\u00ec\u00a9\u00bc\u0108\u011d\u010c\u012e");
        vg.text(label, this.ps.x + (210.0f - vg.textWidth(label, 13.0f)) / 2.0f, rowY + 12.0f, 13.0f, hovered ? this.theme().accentBright() : this.theme().textPrimary());
        vg.restore();
        if (hovered) {
            newHoveredRow = 999;
        }
        rowY += 28.0f;
        rowY = this.sectionHeader(vg, Deobf.decrypt(" C=\u0000v\u00b7"), rowY, viewTop, viewBottom);
        for (int i = 0; i < this.soundWidgets.size(); ++i) {
            if (i == 5) {
                rowY = this.sectionHeader(vg, Deobf.decrypt(" X)\u001cf\u00b1\u00bc\u00ca\u009a\u0114\u011c\u010d\u0127"), rowY, viewTop, viewBottom);
            }
            SettingWidget widget = this.soundWidgets.get(i);
            widget.setBounds(this.ps.x + 14.0f, rowY, 182.0f);
            float wFade = this.edgeFade(rowY, rowY + widget.height(vg), viewTop, viewBottom);
            vg.save();
            vg.alpha(wFade);
            widget.render(vg, mx, my);
            vg.restore();
            rowY += widget.height(vg) + 3.0f;
        }
        if (newHoveredRow != this.hoveredRow && newHoveredRow != -1) {
            UiSounds.hover();
        }
        this.hoveredRow = newHoveredRow;
    }

    private float sectionHeader(NVGRenderer vg, String title, float rowY, float viewTop, float viewBottom) {
        float fade = this.edgeFade(rowY, rowY + 24.0f, viewTop, viewBottom);
        vg.save();
        vg.alpha(fade);
        float cy = rowY + 12.0f + 3.0f;
        vg.textGradient(title.toUpperCase(Locale.ROOT), this.ps.x + 14.0f, cy, 12.0f, this.theme().accentBright(), this.theme().accent());
        float lineX = this.ps.x + 14.0f + vg.textWidth(title.toUpperCase(Locale.ROOT), 12.0f) + 8.0f;
        vg.rect(lineX, cy - 0.5f, Math.max(0.0f, this.ps.x + 210.0f - 14.0f - lineX), 1.0f, 0.5f, Colors.withAlpha(this.theme().accent(), 0.3f));
        vg.restore();
        return rowY + 24.0f;
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (this.accentWidget.mouseClicked(mx, my, button)) {
            return true;
        }
        for (SettingWidget settingWidget : this.soundWidgets) {
            if (!settingWidget.mouseClicked(mx, my, button)) continue;
            return true;
        }
        if (button != 0) {
            return false;
        }
        float rowY = this.firstRowY();
        if (rowY == Float.MIN_VALUE) {
            return false;
        }
        for (Theme theme : this.themes.getThemes()) {
            if (my >= rowY && my <= rowY + 26.0f && mx >= this.ps.x + 6.0f && mx <= this.ps.x + 210.0f - 6.0f) {
                if (theme.isCustom() && mx >= this.ps.x + 210.0f - 34.0f) {
                    this.themes.removeCustom(theme);
                } else {
                    this.themes.select(theme);
                    if (theme.isCustom()) {
                        this.accentProxy.set(theme.accent());
                    }
                }
                return true;
            }
            rowY += 26.0f;
        }
        if (this.themes.current().isCustom()) {
            rowY += this.accentWidget.height(null) + 6.0f;
        }
        if (my >= rowY && my <= rowY + 28.0f - 4.0f && mx >= this.ps.x + 6.0f && mx <= this.ps.x + 210.0f - 6.0f) {
            Theme theme = this.themes.addCustom(this.themes.current().accent());
            this.themes.select(theme);
            this.accentProxy.set(theme.accent());
            return true;
        }
        return false;
    }

    private float firstRowY() {
        return this.lastStartY;
    }

    @Override
    public void mouseDragged(float mx, float my) {
        this.accentWidget.mouseDragged(mx, my);
        for (SettingWidget widget : this.soundWidgets) {
            widget.mouseDragged(mx, my);
        }
    }

    @Override
    public void mouseReleased() {
        this.accentWidget.mouseReleased();
        for (SettingWidget widget : this.soundWidgets) {
            widget.mouseReleased();
        }
    }

    @Override
    public boolean keyPressed(int keyCode) {
        return this.accentWidget.keyPressed(keyCode);
    }

    @Override
    public boolean isListening() {
        return this.accentWidget.isListening();
    }
}

