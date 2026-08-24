/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.widget;

import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.UiSounds;
import java.util.ArrayList;
import java.util.List;

public class ModeWidget
extends SettingWidget {
    private static final float LINE_HEIGHT = 16.0f;
    private static final float FONT_SIZE = 12.5f;
    private static final float GAP = 10.0f;
    private final ModeSetting setting;
    private final List<float[]> optionBounds = new ArrayList<float[]>();
    private int lines = 1;

    public ModeWidget(ThemeManager themes, ModeSetting setting) {
        super(themes, setting);
        this.setting = setting;
    }

    @Override
    public float height(NVGRenderer vg) {
        return 17.0f + (float)this.lines * 16.0f + 3.0f;
    }

    @Override
    public void render(NVGRenderer vg, float mx, float my) {
        Theme theme = this.theme();
        vg.text(this.setting.getName(), this.x, this.y + 8.0f, 12.5f, theme.textMuted());
        this.optionBounds.clear();
        float cx = this.x;
        float cy = this.y + 17.0f + 8.0f;
        this.lines = 1;
        for (String mode : this.setting.getModes()) {
            boolean hovered;
            float w = vg.textWidth(mode, 12.5f);
            if (cx + w > this.x + this.width && cx > this.x) {
                cx = this.x;
                cy += 16.0f;
                ++this.lines;
            }
            boolean selected = this.setting.is(mode);
            boolean bl = hovered = mx >= cx && mx <= cx + w && my >= cy - 8.0f && my <= cy + 8.0f;
            if (selected) {
                vg.textGradient(mode, cx, cy, 12.5f, theme.accentBright(), theme.accent());
            } else {
                vg.text(mode, cx, cy, 12.5f, hovered ? theme.textPrimary() : theme.textDisabled());
            }
            this.optionBounds.add(new float[]{cx, cy - 8.0f, w});
            cx += w + 10.0f;
        }
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (button != 0) {
            return false;
        }
        List<String> modes = this.setting.getModes();
        for (int i = 0; i < this.optionBounds.size() && i < modes.size(); ++i) {
            float[] b = this.optionBounds.get(i);
            if (!(mx >= b[0]) || !(mx <= b[0] + b[2]) || !(my >= b[1]) || !(my <= b[1] + 16.0f)) continue;
            this.setting.set(modes.get(i));
            UiSounds.select();
            return true;
        }
        return false;
    }
}

