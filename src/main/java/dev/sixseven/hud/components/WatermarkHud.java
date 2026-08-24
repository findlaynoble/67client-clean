/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import java.util.function.BooleanSupplier;

public class WatermarkHud
extends HudComponent {
    private static final float HEIGHT = 30.0f;
    private static final float PAD = 13.0f;
    private static final float LOGO_SIZE = 18.0f;
    private static final float TEXT_SIZE = 14.0f;
    private final ThemeManager themes;

    public WatermarkHud(ThemeManager themes, BooleanSupplier visible) {
        super(Deobf.decrypt("\u0004M<\u000b`\u00a9\u00ad\u0098\u00a2"), 0.006f, 0.01f, visible);
        this.themes = themes;
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 13.0f + vg.textWidth(Deobf.decrypt("E\u001b"), 18.0f) + 7.0f + vg.textWidth(Deobf.decrypt("\u0010@!\u000b|\u00b0"), 14.0f) + 13.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        return 30.0f;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        float cy = y + h / 2.0f;
        float breathe = (float)(0.5 + 0.5 * Math.sin((double)System.nanoTime() / 8.0E8));
        float drift = (float)(0.5 + 0.5 * Math.sin((double)System.nanoTime() / 4.8E8));
        int gradTop = Colors.lerp(theme.accentBright(), theme.accent(), drift);
        int gradBottom = Colors.lerp(theme.accent(), theme.accentBright(), drift);
        vg.glow(x, y, w, h, h / 2.0f, 8.0f, Colors.withAlpha(theme.accent(), 0.1f + 0.1f * breathe));
        vg.rectGradient(x, y, w, h, h / 2.0f, Colors.withAlpha(-15264995, 0.88f), Colors.withAlpha(-15856878, 0.88f), true);
        vg.rectOutline(x, y, w, h, h / 2.0f, 1.0f, Colors.withAlpha(Colors.lerp(theme.accent(), theme.accentBright(), breathe), 0.55f));
        float tx = x + 13.0f;
        vg.textGlow(Deobf.decrypt("E\u001b"), tx, cy, 18.0f, Colors.withAlpha(theme.accent(), 0.45f + 0.3f * breathe));
        vg.textGradient(Deobf.decrypt("E\u001b"), tx, cy, 18.0f, gradTop, gradBottom);
        vg.circle((tx += vg.textWidth(Deobf.decrypt("E\u001b"), 18.0f) + 7.0f) - 4.5f, cy, 1.4f, Colors.withAlpha(theme.textMuted(), 0.8f));
        vg.text(Deobf.decrypt("\u0010@!\u000b|\u00b0"), tx, cy, 14.0f, Colors.withAlpha(-856073, 0.92f));
    }
}

