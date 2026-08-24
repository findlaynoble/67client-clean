/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1304
 *  net.minecraft.class_1799
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 *  net.minecraft.class_7923
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.render.nanovg.NVGImages;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import java.util.function.BooleanSupplier;
import net.minecraft.class_1304;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_746;
import net.minecraft.class_7923;

public class ArmorHud
extends HudComponent {
    private static final class_1304[] SLOTS = new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166};
    private static final float ICON = 22.0f;
    private static final float PAD = 7.0f;
    private static final float GAP = 6.0f;
    private static final float BAR_H = 3.0f;
    private final ThemeManager themes;

    public ArmorHud(ThemeManager themes, BooleanSupplier visible) {
        super(Deobf.decrypt("\u0012^%\u0001`"), 0.5f, 0.8f, visible);
        this.themes = themes;
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 14.0f + (float)SLOTS.length * 22.0f + (float)(SLOTS.length - 1) * 6.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        return 38.0f;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        class_746 player = class_310.method_1551().field_1724;
        if (player == null) {
            return;
        }
        vg.rectGradient(x, y, w, h, 9.0f, theme.background(), theme.backgroundTo(), true);
        float ix = x + 7.0f;
        for (class_1304 slot : SLOTS) {
            class_1799 stack = player.method_6118(slot);
            float iy = y + 4.0f;
            if (stack.method_7960()) {
                vg.rectOutline(ix, iy, 22.0f, 22.0f, 5.0f, 1.0f, Colors.withAlpha(theme.textDisabled(), 0.5f));
            } else {
                class_2960 itemId = class_7923.field_41178.method_10221((Object)stack.method_7909());
                int image = NVGImages.fromResource(class_2960.method_60655((String)itemId.method_12836(), (String)("textures/item/" + itemId.method_12832() + ".png")));
                if (image > 0) {
                    vg.imagePattern(image, ix, iy, 22.0f, 22.0f, ix, iy, 22.0f, 22.0f, 1.0f);
                } else {
                    vg.rect(ix, iy, 22.0f, 22.0f, 5.0f, Colors.withAlpha(theme.accent(), 0.3f));
                }
                if (stack.method_7963()) {
                    float frac = 1.0f - (float)stack.method_7919() / (float)stack.method_7936();
                    int barColor = Colors.lerp(-1684147, -11671924, frac);
                    float barY = iy + 22.0f + 3.0f;
                    vg.rect(ix, barY, 22.0f, 3.0f, 1.5f, Colors.withAlpha(-16777216, 0.45f));
                    vg.rect(ix, barY, Math.max(3.0f, 22.0f * frac), 3.0f, 1.5f, barColor);
                }
            }
            ix += 28.0f;
        }
    }
}

