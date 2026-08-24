/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 *  net.minecraft.class_315
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.CpsTracker;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_315;

public class KeystrokesHud
extends HudComponent {
    private static final float KEY = 26.0f;
    private static final float GAP = 3.0f;
    private static final float FONT = 12.5f;
    private final ThemeManager themes;
    private final Map<String, Animation> press = new HashMap<String, Animation>();

    public KeystrokesHud(ThemeManager themes, BooleanSupplier visible) {
        super(Deobf.decrypt("\u0018I1\u001df\u00b6\u00a3\u0081\u00ac\u0108"), 0.03f, 0.72f, visible);
        this.themes = themes;
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 84.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        float h = 55.0f;
        h += 29.0f;
        return h += 18.6f;
    }

    private float pressT(String id, boolean down) {
        Animation anim = this.press.computeIfAbsent(id, k -> new Animation(110.0f, 0.0f));
        anim.setTarget(down ? 1.0f : 0.0f);
        return anim.value();
    }

    private void key(NVGRenderer vg, Theme theme, String id, String label, boolean down, float x, float y, float w, float h) {
        float t = this.pressT(id, down);
        int bg = Colors.lerp(Colors.withAlpha(-15462118, 0.78f), Colors.withAlpha(theme.accent(), 0.85f), t);
        vg.rect(x, y, w, h, 6.0f, bg);
        int fg = Colors.lerp(theme.textMuted(), -1, t);
        vg.text(label, x + (w - vg.textWidth(label, 12.5f)) / 2.0f, y + h / 2.0f, 12.5f, fg);
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        class_315 options = class_310.method_1551().field_1690;
        float row = y;
        this.key(vg, theme, Deobf.decrypt("\u0004"), Deobf.decrypt("$"), KeystrokesHud.isDown(options.field_1894), x + 26.0f + 3.0f, row, 26.0f, 26.0f);
        this.key(vg, theme, Deobf.decrypt("\u0012"), Deobf.decrypt("2"), KeystrokesHud.isDown(options.field_1913), x, row += 29.0f, 26.0f, 26.0f);
        this.key(vg, theme, Deobf.decrypt("\u0000"), Deobf.decrypt(" "), KeystrokesHud.isDown(options.field_1881), x + 26.0f + 3.0f, row, 26.0f, 26.0f);
        this.key(vg, theme, Deobf.decrypt("\u0017"), Deobf.decrypt("7"), KeystrokesHud.isDown(options.field_1849), x + 58.0f, row, 26.0f, 26.0f);
        float half = (w - 3.0f) / 2.0f;
        this.key(vg, theme, Deobf.decrypt("\u001fA*"), "LMB " + CpsTracker.get(0), KeystrokesHud.isDown(options.field_1886), x, row += 29.0f, half, 26.0f);
        this.key(vg, theme, Deobf.decrypt("\u0001A*"), "RMB " + CpsTracker.get(1), KeystrokesHud.isDown(options.field_1904), x + half + 3.0f, row, half, 26.0f);
        float t = this.pressT(Deobf.decrypt("\u0000\\)\rw"), KeystrokesHud.isDown(options.field_1903));
        float sh = 15.6f;
        int bg = Colors.lerp(Colors.withAlpha(-15462118, 0.78f), Colors.withAlpha(theme.accent(), 0.85f), t);
        vg.rect(x, row += 29.0f, w, sh, 6.0f, bg);
        vg.rect(x + w * 0.25f, row + sh / 2.0f - 1.25f, w * 0.5f, 2.5f, 1.25f, Colors.lerp(theme.textMuted(), -1, t));
    }

    private static boolean isDown(class_304 mapping) {
        return mapping.method_1434();
    }
}

