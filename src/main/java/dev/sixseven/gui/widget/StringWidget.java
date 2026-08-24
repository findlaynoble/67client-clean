/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.widget;

import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.settings.StringSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;

public class StringWidget
extends SettingWidget {
    private static final float HEIGHT = 34.0f;
    private static final float BOX_H = 18.0f;
    private static final float FONT = 12.0f;
    private final StringSetting setting;
    private boolean focused;

    public StringWidget(ThemeManager themes, StringSetting setting) {
        super(themes, setting);
        this.setting = setting;
    }

    @Override
    public float height(NVGRenderer vg) {
        return 34.0f;
    }

    @Override
    public void render(NVGRenderer vg, float mx, float my) {
        Theme theme = this.theme();
        vg.text(this.setting.getName(), this.x, this.y + 8.0f, 12.5f, theme.textMuted());
        float bx = this.x;
        float by = this.y + 14.0f;
        float bw = this.width;
        int fill = Colors.withAlpha(-16777216, 0.45f);
        vg.rect(bx, by, bw, 18.0f, 9.0f, fill);
        vg.rectOutline(bx, by, bw, 18.0f, 9.0f, 1.1f, Colors.withAlpha(this.focused ? theme.accentBright() : theme.accent(), this.focused ? 0.9f : 0.35f));
        float textX = bx + 8.0f;
        float textY = by + 9.0f;
        String value = (String)this.setting.get();
        if (value.isEmpty() && !this.focused) {
            vg.text(this.setting.getPlaceholder(), textX, textY, 12.0f, theme.textDisabled());
        } else {
            float w = vg.text(value, textX, textY, 12.0f, theme.textPrimary());
            if (this.focused && System.nanoTime() / 400000000L % 2L == 0L) {
                vg.rect(textX + w + 1.5f, textY - 6.0f, 1.4f, 12.0f, 0.7f, theme.accentBright());
            }
        }
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (button == 0 && this.contains(mx, my)) {
            this.focused = true;
            UiSounds.select();
            return true;
        }
        this.focused = false;
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode) {
        if (!this.focused) {
            return false;
        }
        switch (keyCode) {
            case 256: 
            case 257: 
            case 335: {
                this.focused = false;
                break;
            }
            case 259: {
                String v = (String)this.setting.get();
                if (v.isEmpty()) break;
                this.setting.set(v.substring(0, v.length() - 1));
                break;
            }
        }
        return true;
    }

    @Override
    public boolean charTyped(int codepoint) {
        if (!this.focused) {
            return false;
        }
        if (((String)this.setting.get()).length() >= this.setting.getMaxLength()) {
            return true;
        }
        if (!Character.isValidCodePoint(codepoint) || Character.isISOControl(codepoint)) {
            return true;
        }
        this.setting.set((String)this.setting.get() + new String(Character.toChars(codepoint)));
        return true;
    }

    @Override
    public boolean isListening() {
        return this.focused;
    }
}

