/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_638
 *  org.joml.Vector3fc
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.BlockEntityEspModule;
import dev.sixseven.render.EspBoxRenderer;
import dev.sixseven.rt.Deobf;
import java.util.Collection;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_638;
import org.joml.Vector3fc;

public final class BlockEntityEspRenderer {
    private static final double INSET = 0.002;
    private static final float TRACER_WIDTH = 1.2f;

    private BlockEntityEspRenderer() {
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, BlockEntityEspModule module) {
        Collection<BlockEntityEspModule.Cached> snapshot = module.entries();
        if (snapshot.isEmpty()) {
            return;
        }
        class_310 mc = class_310.method_1551();
        class_638 level = mc.field_1687;
        if (level == null) {
            return;
        }
        boolean fill = module.mode.is(Deobf.decrypt("5Y$\u0002"));
        int alpha = Math.clamp((long)module.highlightAlpha.getInt(), 0, 255);
        boolean showGhosts = (Boolean)module.showGhosts.get();
        boolean tracers = (Boolean)module.tracers.get();
        int ghostTint = (Integer)module.ghostTint.get();
        Vector3fc forward = tracers ? mc.field_1773.method_19418().method_19335() : null;
        for (BlockEntityEspModule.Cached entry : snapshot) {
            boolean ghost;
            String key = entry.typeKey();
            if (!module.blockEntities.isEnabled(key)) continue;
            class_2338 p = entry.pos();
            boolean bl = ghost = level.method_8321(p) == null;
            if (ghost && !showGhosts) continue;
            int rgb = BlockEntityEspRenderer.tint(module.blockEntities.color(key) & 0xFFFFFF, ghost, ghostTint);
            int argb = rgb | alpha << 24;
            double x0 = p.method_10263();
            double y0 = p.method_10264();
            double z0 = p.method_10260();
            double x1 = x0 + 1.0;
            double y1 = y0 + 1.0;
            double z1 = z0 + 1.0;
            if (fill) {
                EspBoxRenderer.fill(bufferSource, poseStack, cam, x0 - 0.002, y0 - 0.002, z0 - 0.002, x1 + 0.002, y1 + 0.002, z1 + 0.002, argb);
            } else {
                EspBoxRenderer.outline(bufferSource, poseStack, cam, x0, y0, z0, x1, y1, z1, argb, 1.6f);
            }
            if (!tracers) continue;
            int tracerColor = rgb | Math.max(alpha, 160) << 24;
            EspBoxRenderer.tracer(bufferSource, poseStack, cam, forward, x0 + 0.5, y0 + 0.5, z0 + 0.5, tracerColor, 1.2f);
        }
        EspBoxRenderer.flush(bufferSource);
    }

    private static int tint(int rgb, boolean ghost, int ghostTintArgb) {
        if (!ghost) {
            return rgb;
        }
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        int tr = ghostTintArgb >> 16 & 0xFF;
        int tg = ghostTintArgb >> 8 & 0xFF;
        int tb = ghostTintArgb & 0xFF;
        return (r + tr) / 2 << 16 | (g + tg) / 2 << 8 | (b + tb) / 2;
    }
}

