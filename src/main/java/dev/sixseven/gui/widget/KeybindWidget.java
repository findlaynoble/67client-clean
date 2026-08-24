/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.widget;

import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.KeybindSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;

public class KeybindWidget
extends SettingWidget {
    public static final float HEIGHT = 22.0f;
    private final KeybindSetting setting;
    private boolean listening;

    public KeybindWidget(ThemeManager themes, KeybindSetting setting) {
        super(themes, setting);
        this.setting = setting;
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
        String label = this.listening ? Deobf.decrypt("]\u0002f") : this.setting.keyName();
        float chipW = Math.max(30.0f, vg.textWidth(label, 11.5f) + 12.0f);
        float chipX = this.x + this.width - chipW;
        float chipH = 16.0f;
        float chipY = cy - chipH / 2.0f;
        int fill = this.listening ? Colors.withAlpha(theme.accent(), 0.3f) : Colors.withAlpha(-16777216, 0.45f);
        vg.rect(chipX, chipY, chipW, chipH, chipH / 2.0f, fill);
        if (this.listening) {
            float pulse = (float)(0.5 + 0.5 * Math.sin((double)System.nanoTime() / 2.2E8));
            vg.rectOutline(chipX, chipY, chipW, chipH, chipH / 2.0f, 1.2f, Colors.withAlpha(theme.accentBright(), 0.4f + 0.6f * pulse));
        }
        vg.text(label, chipX + (chipW - vg.textWidth(label, 11.5f)) / 2.0f, cy, 11.5f, this.listening ? theme.accentBright() : theme.textPrimary());
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (this.listening) {
            if (button == 0) {
                this.listening = false;
                return this.contains(mx, my);
            }
            this.setting.set(button);
            this.listening = false;
            UiSounds.keybindSet();
            return true;
        }
        if (button == 0 && this.contains(mx, my)) {
            this.listening = true;
            UiSounds.keybindListen();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode) {
        if (!this.listening) {
            return false;
        }
        if (keyCode == 256) {
            this.setting.set(-1);
        } else {
            this.setting.set(keyCode);
        }
        this.listening = false;
        UiSounds.keybindSet();
        return true;
    }

    @Override
    public boolean isListening() {
        return this.listening;
    }
}

