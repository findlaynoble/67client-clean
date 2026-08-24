/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class InfoHud
extends HudComponent {
    private static final float HEIGHT = 22.0f;
    private static final float FONT_SIZE = 13.0f;
    private static final float PAD_X = 9.0f;
    private final ThemeManager themes;
    private final String label;
    private final Supplier<String> value;

    public InfoHud(String id, ThemeManager themes, String label, Supplier<String> value, float defaultFx, float defaultFy, BooleanSupplier visible) {
        super(id, defaultFx, defaultFy, visible);
        this.themes = themes;
        this.label = label;
        this.value = value;
    }

    private String currentValue() {
        try {
            return this.value.get();
        }
        catch (Exception e) {
            return Deobf.decrypt("L");
        }
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 9.0f + vg.textWidth(this.label, 13.0f) + 5.0f + vg.textWidth(this.currentValue(), 13.0f) + 9.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        return 22.0f;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        float cy = y + h / 2.0f;
        vg.rectGradient(x, y, w, h, h / 2.0f, theme.background(), theme.backgroundTo(), true);
        float tx = x + 9.0f;
        tx += vg.textGradient(this.label, tx, cy, 13.0f, theme.accentBright(), theme.accent());
        vg.text(this.currentValue(), tx + 5.0f, cy, 13.0f, theme.textPrimary());
    }
}

