/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.widget;

import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;

public class ColorWidget
extends SettingWidget {
    private static final float ROW = 22.0f;
    private static final float SV_H = 74.0f;
    private static final float HUE_H = 10.0f;
    private static final float BOTTOM_H = 22.0f;
    private static final float GAP = 6.0f;
    private final ColorSetting setting;
    private final Animation expand = new Animation(170.0f, 0.0f);
    private boolean expanded;
    private float hue;
    private float sat;
    private float val;
    private boolean draggingSv;
    private boolean draggingHue;
    private boolean hexFocused;
    private final StringBuilder hexBuffer = new StringBuilder();

    public ColorWidget(ThemeManager themes, ColorSetting setting) {
        super(themes, setting);
        this.setting = setting;
        this.syncFromSetting();
    }

    private void syncFromSetting() {
        float[] hsv = Colors.rgbToHsv((Integer)this.setting.get());
        this.hue = hsv[0];
        this.sat = hsv[1];
        this.val = hsv[2];
    }

    public void setExpanded(boolean value) {
        if (value && !this.expanded) {
            this.syncFromSetting();
        }
        this.expanded = value;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    private void apply() {
        this.setting.set(Colors.hsvToRgb(this.hue, this.sat, this.val));
    }

    @Override
    public float height(NVGRenderer vg) {
        return 22.0f + this.expand.value() * 124.0f;
    }

    @Override
    public void render(NVGRenderer vg, float mx, float my) {
        Theme theme = this.theme();
        float cy = this.y + 11.0f;
        vg.text(this.setting.getName(), this.x, cy, 12.5f, theme.textMuted());
        float sw = 26.0f;
        float sh = 14.0f;
        vg.rect(this.x + this.width - sw, cy - sh / 2.0f, sw, sh, 5.0f, (int)((Integer)this.setting.get() | 0xFF000000));
        vg.rectOutline(this.x + this.width - sw, cy - sh / 2.0f, sw, sh, 5.0f, 1.0f, Colors.withAlpha(-1, 0.25f));
        this.expand.setTarget(this.expanded ? 1.0f : 0.0f);
        float t = this.expand.value();
        if (t < 0.01f) {
            return;
        }
        vg.save();
        vg.scissor(this.x - 4.0f, this.y + 22.0f, this.width + 8.0f, t * 124.0f);
        vg.alpha(t);
        float svY = this.svTop();
        int hueColor = Colors.hsvToRgb(this.hue, 1.0f, 1.0f);
        vg.rect(this.x, svY, this.width, 74.0f, 6.0f, hueColor);
        vg.rectGradient(this.x, svY, this.width, 74.0f, 6.0f, -1, Colors.withAlpha(-1, 0), false);
        vg.rectGradient(this.x, svY, this.width, 74.0f, 6.0f, Colors.withAlpha(-16777216, 0), -16777216, true);
        float knobX = this.x + this.sat * this.width;
        float knobY = svY + (1.0f - this.val) * 74.0f;
        vg.circle(knobX, knobY, 6.0f, -1);
        vg.circle(knobX, knobY, 4.2f, Colors.hsvToRgb(this.hue, this.sat, this.val));
        float hueY = this.hueTop();
        float seg = this.width / 6.0f;
        for (int i = 0; i < 6; ++i) {
            int from = Colors.hsvToRgb(i * 60, 1.0f, 1.0f);
            int to = Colors.hsvToRgb((i + 1) * 60 % 360 == 0 ? 359.9f : (float)((i + 1) * 60), 1.0f, 1.0f);
            vg.rectGradient(this.x + (float)i * seg, hueY, seg + 0.5f, 10.0f, 0.0f, from, to, false);
        }
        vg.rectOutline(this.x, hueY, this.width, 10.0f, 5.0f, 1.5f, Colors.withAlpha(-15988208, 0.9f));
        float hueKnobX = this.x + this.hue / 360.0f * this.width;
        vg.circle(hueKnobX, hueY + 5.0f, 6.0f, -1);
        vg.circle(hueKnobX, hueY + 5.0f, 4.2f, hueColor);
        float botY = this.bottomTop();
        vg.rect(this.x, botY, 30.0f, 18.0f, 5.0f, (int)((Integer)this.setting.get() | 0xFF000000));
        vg.rectOutline(this.x, botY, 30.0f, 18.0f, 5.0f, 1.0f, Colors.withAlpha(-1, 0.25f));
        float hexX = this.x + 38.0f;
        float hexW = this.width - 38.0f;
        int boxFill = this.hexFocused ? Colors.withAlpha(theme.accent(), 0.18f) : Colors.withAlpha(-16777216, 0.45f);
        vg.rect(hexX, botY, hexW, 18.0f, 5.0f, boxFill);
        if (this.hexFocused) {
            vg.rectOutline(hexX, botY, hexW, 18.0f, 5.0f, 1.2f, theme.accentBright());
        }
        String text = this.hexFocused ? "#" + String.valueOf(this.hexBuffer) + (System.nanoTime() / 400000000L % 2L == 0L ? Deobf.decrypt(",") : Deobf.decrypt("")) : this.setting.hex();
        vg.text(text, hexX + 8.0f, botY + 9.0f, 12.0f, this.hexFocused ? theme.textPrimary() : theme.textMuted());
        vg.restore();
    }

    private float svTop() {
        return this.y + 22.0f + 2.0f;
    }

    private float hueTop() {
        return this.svTop() + 74.0f + 6.0f;
    }

    private float bottomTop() {
        return this.hueTop() + 10.0f + 6.0f;
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (button != 0) {
            return false;
        }
        if (my >= this.y && my <= this.y + 22.0f && mx >= this.x && mx <= this.x + this.width) {
            boolean bl = this.expanded = !this.expanded;
            if (this.expanded) {
                this.syncFromSetting();
            }
            this.hexFocused = false;
            UiSounds.select();
            return true;
        }
        if (!this.expanded) {
            return false;
        }
        if (ColorWidget.inRect(mx, my, this.x, this.svTop(), this.width, 74.0f)) {
            this.draggingSv = true;
            this.applySv(mx, my);
            return true;
        }
        if (ColorWidget.inRect(mx, my, this.x, this.hueTop() - 3.0f, this.width, 16.0f)) {
            this.draggingHue = true;
            this.applyHue(mx);
            return true;
        }
        if (ColorWidget.inRect(mx, my, this.x + 38.0f, this.bottomTop(), this.width - 38.0f, 18.0f)) {
            this.hexFocused = true;
            this.hexBuffer.setLength(0);
            UiSounds.select();
            return true;
        }
        if (this.hexFocused) {
            this.commitHex();
        }
        return false;
    }

    private static boolean inRect(float mx, float my, float rx, float ry, float rw, float rh) {
        return mx >= rx && mx <= rx + rw && my >= ry && my <= ry + rh;
    }

    private void applySv(float mx, float my) {
        this.sat = Math.clamp((mx - this.x) / this.width, 0.0f, 1.0f);
        this.val = 1.0f - Math.clamp((my - this.svTop()) / 74.0f, 0.0f, 1.0f);
        this.apply();
    }

    private void applyHue(float mx) {
        this.hue = Math.clamp((mx - this.x) / this.width, 0.0f, 1.0f) * 359.9f;
        this.apply();
    }

    @Override
    public void mouseDragged(float mx, float my) {
        if (this.draggingSv) {
            this.applySv(mx, my);
        }
        if (this.draggingHue) {
            this.applyHue(mx);
        }
    }

    @Override
    public void mouseReleased() {
        this.draggingSv = false;
        this.draggingHue = false;
    }

    @Override
    public boolean keyPressed(int keyCode) {
        if (!this.hexFocused) {
            return false;
        }
        if (keyCode == 256) {
            this.hexFocused = false;
            return true;
        }
        if (keyCode == 257 || keyCode == 335) {
            this.commitHex();
            return true;
        }
        if (keyCode == 259) {
            if (!this.hexBuffer.isEmpty()) {
                this.hexBuffer.deleteCharAt(this.hexBuffer.length() - 1);
            }
            return true;
        }
        char c = ColorWidget.hexChar(keyCode);
        if (c != '\u0000' && this.hexBuffer.length() < 6) {
            this.hexBuffer.append(c);
            if (this.hexBuffer.length() == 6) {
                this.commitHex();
            }
        }
        return true;
    }

    private static char hexChar(int keyCode) {
        if (keyCode >= 48 && keyCode <= 57) {
            return (char)(48 + keyCode - 48);
        }
        if (keyCode >= 320 && keyCode <= 329) {
            return (char)(48 + keyCode - 320);
        }
        if (keyCode >= 65 && keyCode <= 70) {
            return (char)(65 + keyCode - 65);
        }
        return '\u0000';
    }

    private void commitHex() {
        this.hexFocused = false;
        if (this.hexBuffer.length() == 6) {
            try {
                this.setting.set(0xFF000000 | Integer.parseInt(this.hexBuffer.toString(), 16));
                this.syncFromSetting();
                UiSounds.keybindSet();
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        this.hexBuffer.setLength(0);
    }

    @Override
    public boolean isListening() {
        return this.hexFocused;
    }
}

