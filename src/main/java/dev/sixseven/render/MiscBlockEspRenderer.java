/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 *  org.joml.Vector3fc
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.SpawnerNametagsModule;
import dev.sixseven.render.EspBoxRenderer;
import dev.sixseven.util.Colors;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import org.joml.Vector3fc;

public final class MiscBlockEspRenderer {
    private static final int SPAWNER_COLOR = -24576;
    private static final double SPAWNER_RANGE = 16.0;
    private static final float TRACER_WIDTH = 1.2f;

    private MiscBlockEspRenderer() {
    }

    public static void renderSpawners(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, SpawnerNametagsModule module) {
        List<class_2338> snapshot = module.scan.get();
        if (snapshot.isEmpty()) {
            return;
        }
        boolean ring = (Boolean)module.rangeRing.get();
        boolean box = (Boolean)module.box.get();
        boolean tracers = (Boolean)module.tracers.get();
        if (!(ring || box || tracers)) {
            return;
        }
        Vector3fc forward = tracers ? class_310.method_1551().field_1773.method_19418().method_19335() : null;
        for (class_2338 pos : snapshot) {
            if (box) {
                EspBoxRenderer.outline(bufferSource, poseStack, cam, pos.method_10263(), pos.method_10264(), pos.method_10260(), pos.method_10263() + 1, pos.method_10264() + 1, pos.method_10260() + 1, -24576, 2.0f);
            }
            if (ring) {
                EspBoxRenderer.ring(bufferSource, poseStack, cam, (double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5, 16.0, 48, Colors.withAlpha(-24576, 0.6f), 2.0f);
            }
            if (!tracers) continue;
            EspBoxRenderer.tracer(bufferSource, poseStack, cam, forward, (double)pos.method_10263() + 0.5, (double)pos.method_10264() + 0.5, (double)pos.method_10260() + 0.5, Colors.withAlpha(-24576, 0.7f), 1.2f);
        }
        EspBoxRenderer.flush(bufferSource);
    }
}

