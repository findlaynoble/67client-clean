/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1291
 *  net.minecraft.class_1293
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.render.nanovg.NVGImages;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class PotionsHud
extends HudComponent {
    private static final float ROW = 24.0f;
    private static final float ICON = 18.0f;
    private static final float PAD = 7.0f;
    private static final float FONT = 12.5f;
    private final ThemeManager themes;

    public PotionsHud(ThemeManager themes, BooleanSupplier visible) {
        super(Deobf.decrypt("\u0003C<\u0007}\u00aa\u00bf"), 0.995f, 0.6f, visible);
        this.themes = themes;
    }

    private List<class_1293> effects() {
        class_746 player = class_310.method_1551().field_1724;
        if (player == null) {
            return List.of();
        }
        ArrayList<class_1293> list = new ArrayList<class_1293>(player.method_6026());
        list.sort(Comparator.comparingInt(class_1293::method_5584).reversed());
        return list;
    }

    private static String label(class_1293 effect) {
        String name = ((class_1291)effect.method_5579().comp_349()).method_5560().getString();
        int amp = effect.method_5578();
        return amp > 0 ? name + " " + (amp + 1) : name;
    }

    private static String timer(class_1293 effect) {
        if (effect.method_48559()) {
            return Deobf.decrypt("\u226d");
        }
        int seconds = effect.method_5584() / 20;
        return String.format(Deobf.decrypt("VHrK\"\u00f6\u00a8"), seconds / 60, seconds % 60);
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        float max = 110.0f;
        for (class_1293 effect : this.effects()) {
            max = Math.max(max, 31.0f + vg.textWidth(PotionsHud.label(effect), 12.5f) + 10.0f + vg.textWidth(PotionsHud.timer(effect), 12.5f) + 7.0f);
        }
        return max;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        return Math.max(24.0f, (float)this.effects().size() * 24.0f);
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        List<class_1293> effects = this.effects();
        if (effects.isEmpty()) {
            return;
        }
        boolean right = this.rightAnchored();
        float rowY = y;
        for (class_1293 effect : effects) {
            int image;
            float labelW = vg.textWidth(PotionsHud.label(effect), 12.5f);
            float timerW = vg.textWidth(PotionsHud.timer(effect), 12.5f);
            float rowW = 31.0f + labelW + 10.0f + timerW + 7.0f;
            float rowX = right ? x + w - rowW : x;
            float cy = rowY + 12.0f;
            vg.rect(rowX, rowY + 1.0f, rowW, 22.0f, 7.0f, Colors.withAlpha(-15462118, 0.78f));
            class_2960 effectId = effect.method_5579().method_40230().map(k -> k.method_29177()).orElse(null);
            if (effectId != null && (image = NVGImages.fromResource(class_2960.method_60655((String)effectId.method_12836(), (String)("textures/mob_effect/" + effectId.method_12832() + ".png")))) > 0) {
                vg.imagePattern(image, rowX + 7.0f, cy - 9.0f, 18.0f, 18.0f, rowX + 7.0f, cy - 9.0f, 18.0f, 18.0f, 1.0f);
            }
            vg.text(PotionsHud.label(effect), rowX + 7.0f + 18.0f + 6.0f, cy, 12.5f, theme.textPrimary());
            vg.textGradient(PotionsHud.timer(effect), rowX + rowW - 7.0f - timerW, cy, 12.5f, theme.accentBright(), theme.accent());
            rowY += 24.0f;
        }
    }
}

