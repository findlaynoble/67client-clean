/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.widget;

import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;

public class SliderWidget
extends SettingWidget {
    public static final float HEIGHT = 27.0f;
    private static final float BAR_HEIGHT = 5.0f;
    private static final float KNOB_RADIUS = 5.0f;
    private final SliderSetting setting;
    private final Animation fill = new Animation(90.0f, 0.0f);
    private boolean dragging;

    public SliderWidget(ThemeManager themes, SliderSetting setting) {
        super(themes, setting);
        this.setting = setting;
        this.fill.snapTo((float)setting.getNormalized());
    }

    @Override
    public float height(NVGRenderer vg) {
        return 27.0f;
    }

    @Override
    public void render(NVGRenderer vg, float mx, float my) {
        Theme theme = this.theme();
        float textY = this.y + 8.0f;
        vg.text(this.setting.getName(), this.x, textY, 12.5f, theme.textMuted());
        String value = this.setting.formatValue();
        vg.text(value, this.x + this.width - vg.textWidth(value, 12.5f), textY, 12.5f, theme.textPrimary());
        float barY = this.y + 27.0f - 5.0f - 5.0f;
        this.fill.setTarget((float)this.setting.getNormalized());
        float t = Math.clamp(this.fill.value(), 0.0f, 1.0f);
        vg.rect(this.x, barY, this.width, 5.0f, 2.5f, Colors.withAlpha(-16777216, 0.45f));
        float fillW = Math.max(5.0f, t * this.width);
        vg.rectGradient(this.x, barY, fillW, 5.0f, 2.5f, theme.accent(), theme.accentBright(), false);
        float knobX = this.x + t * (this.width - 5.0f) + 2.5f;
        if (this.dragging) {
            vg.circleGlow(knobX, barY + 2.5f, 5.0f, 5.0f, theme.accentHover());
        }
        vg.circle(knobX, barY + 2.5f, 5.0f, -1);
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (button != 0 || !this.contains(mx, my) || my < this.y + 10.0f) {
            return false;
        }
        this.dragging = true;
        this.applyMouse(mx);
        return true;
    }

    @Override
    public void mouseDragged(float mx, float my) {
        if (this.dragging) {
            this.applyMouse(mx);
        }
    }

    @Override
    public void mouseReleased() {
        this.dragging = false;
    }

    private void applyMouse(float mx) {
        double before = this.setting.getNormalized();
        this.setting.setNormalized((mx - this.x) / this.width);
        if (this.setting.getNormalized() != before) {
            UiSounds.sliderTick((float)this.setting.getNormalized());
        }
    }
}

