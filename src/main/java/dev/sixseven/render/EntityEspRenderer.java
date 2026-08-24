/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1308
 *  net.minecraft.class_1569
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_638
 *  net.minecraft.class_742
 *  org.joml.Vector3fc
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.MobEspModule;
import dev.sixseven.module.impl.PlayerEspModule;
import dev.sixseven.render.EspBoxRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.util.Colors;
import net.minecraft.class_1297;
import net.minecraft.class_1308;
import net.minecraft.class_1569;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_638;
import net.minecraft.class_742;
import org.joml.Vector3fc;

public final class EntityEspRenderer {
    private static final float TRACER_WIDTH = 1.2f;

    private EntityEspRenderer() {
    }

    public static void renderPlayers(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, PlayerEspModule module) {
        class_310 mc = class_310.method_1551();
        class_638 level = mc.field_1687;
        if (level == null || mc.field_1724 == null) {
            return;
        }
        int color = (Integer)module.color.get();
        boolean glow = module.style.is(Deobf.decrypt("4@'\u0019"));
        boolean tracers = (Boolean)module.tracers.get();
        Vector3fc forward = tracers ? mc.field_1773.method_19418().method_19335() : null;
        for (class_742 player : level.method_18456()) {
            if (player == mc.field_1724 || !player.method_5805() || player.method_7325()) continue;
            EntityEspRenderer.box(bufferSource, poseStack, cam, player.method_5829(), color, glow);
            if (!tracers) continue;
            EntityEspRenderer.tracer(bufferSource, poseStack, cam, forward, player.method_5829(), color);
        }
        EspBoxRenderer.flush(bufferSource);
    }

    public static void renderMobs(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, MobEspModule module) {
        class_310 mc = class_310.method_1551();
        class_638 level = mc.field_1687;
        if (level == null) {
            return;
        }
        int hostileColor = (Integer)module.hostile.get();
        int passiveColor = (Integer)module.passive.get();
        boolean passiveToo = (Boolean)module.passiveToo.get();
        boolean tracers = (Boolean)module.tracers.get();
        Vector3fc forward = tracers ? mc.field_1773.method_19418().method_19335() : null;
        for (class_1297 entity : level.method_18112()) {
            boolean hostile;
            if (!(entity instanceof class_1308) || !entity.method_5805() || !(hostile = entity instanceof class_1569) && !passiveToo) continue;
            int color = hostile ? hostileColor : passiveColor;
            EntityEspRenderer.box(bufferSource, poseStack, cam, entity.method_5829(), color, false);
            if (!tracers) continue;
            EntityEspRenderer.tracer(bufferSource, poseStack, cam, forward, entity.method_5829(), color);
        }
        EspBoxRenderer.flush(bufferSource);
    }

    private static void box(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, class_238 b, int color, boolean glow) {
        EspBoxRenderer.outline(bufferSource, poseStack, cam, b.field_1323, b.field_1322, b.field_1321, b.field_1320, b.field_1325, b.field_1324, color, 2.0f);
        if (glow) {
            EspBoxRenderer.fill(bufferSource, poseStack, cam, b.field_1323, b.field_1322, b.field_1321, b.field_1320, b.field_1325, b.field_1324, Colors.withAlpha(color, 0.18f));
        }
    }

    private static void tracer(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, Vector3fc forward, class_238 b, int color) {
        EspBoxRenderer.tracer(bufferSource, poseStack, cam, forward, (b.field_1323 + b.field_1320) / 2.0, (b.field_1322 + b.field_1325) / 2.0, (b.field_1321 + b.field_1324) / 2.0, Colors.withAlpha(color, 0.72f), 1.2f);
    }
}

