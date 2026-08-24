/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.widget;

import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;

public class BooleanWidget
extends SettingWidget {
    public static final float HEIGHT = 22.0f;
    private static final float BOX = 14.0f;
    private final BooleanSetting setting;
    private final Animation check = new Animation(150.0f, 0.0f);

    public BooleanWidget(ThemeManager themes, BooleanSetting setting) {
        super(themes, setting);
        this.setting = setting;
        this.check.snapTo((Boolean)setting.get() != false ? 1.0f : 0.0f);
    }

    @Override
    public float height(NVGRenderer vg) {
        return 22.0f;
    }

    @Override
    public void render(NVGRenderer vg, float mx, float my) {
        Theme theme = this.theme();
        float cy = this.y + 11.0f;
        vg.text(this.setting.getName(), this.x, cy, 12.5f, theme.textMuted());
        this.check.setTarget((Boolean)this.setting.get() != false ? 1.0f : 0.0f);
        float t = this.check.value();
        float bx = this.x + this.width - 14.0f;
        float by = cy - 7.0f;
        int fill = Colors.lerp(Colors.withAlpha(-16777216, 0.45f), theme.accent(), t);
        vg.rect(bx, by, 14.0f, 14.0f, 4.0f, fill);
        if (t > 0.02f) {
            vg.save();
            vg.alpha(t);
            vg.checkmark(bx, by, 14.0f, 2.0f, -1);
            vg.restore();
        }
        if (t < 0.98f) {
            vg.save();
            vg.alpha(1.0f - t);
            vg.cross(bx, by, 14.0f, 1.8f, theme.textDisabled());
            vg.restore();
        }
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (button != 0 || !this.contains(mx, my)) {
            return false;
        }
        this.setting.toggle();
        UiSounds.checkbox((Boolean)this.setting.get());
        return true;
    }
}

