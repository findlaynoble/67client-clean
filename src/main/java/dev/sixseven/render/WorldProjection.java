/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 *  net.minecraft.class_276
 *  net.minecraft.class_310
 *  net.minecraft.class_4184
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 *  org.joml.Vector4f
 */
package dev.sixseven.render;

import dev.sixseven.render.OverlayRenderer;
import net.minecraft.class_243;
import net.minecraft.class_276;
import net.minecraft.class_310;
import net.minecraft.class_4184;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector4f;

public final class WorldProjection {
    private static final Matrix4f mvp = new Matrix4f();
    private static final Vector4f scratch = new Vector4f();
    private static class_243 camPos = class_243.field_1353;
    private static int fbWidth;
    private static int fbHeight;
    private static float partialTick;
    private static boolean valid;

    private WorldProjection() {
    }

    public static void capture(Matrix4f projection, float partialTick) {
        class_310 mc = class_310.method_1551();
        class_4184 camera = mc.field_1773.method_19418();
        if (camera == null) {
            valid = false;
            return;
        }
        Quaternionf view = camera.method_23767().conjugate(new Quaternionf());
        mvp.set((Matrix4fc)projection).rotate((Quaternionfc)view);
        camPos = camera.method_71156();
        class_276 target = mc.method_1522();
        fbWidth = target.field_1482;
        fbHeight = target.field_1481;
        WorldProjection.partialTick = partialTick;
        valid = true;
    }

    public static void invalidate() {
        valid = false;
    }

    public static boolean isValid() {
        return valid;
    }

    public static float partialTick() {
        return partialTick;
    }

    public static float[] project(double wx, double wy, double wz) {
        float[] px = WorldProjection.projectRaw(wx, wy, wz);
        if (px == null) {
            return null;
        }
        float scale = OverlayRenderer.uiScale();
        return new float[]{px[0] / scale, px[1] / scale};
    }

    public static float[] projectRaw(double wx, double wy, double wz) {
        if (!valid) {
            return null;
        }
        scratch.set((float)(wx - WorldProjection.camPos.field_1352), (float)(wy - WorldProjection.camPos.field_1351), (float)(wz - WorldProjection.camPos.field_1350), 1.0f);
        mvp.transform(scratch);
        if (WorldProjection.scratch.w <= 1.0E-4f) {
            return null;
        }
        float ndcX = WorldProjection.scratch.x / WorldProjection.scratch.w;
        float ndcY = WorldProjection.scratch.y / WorldProjection.scratch.w;
        float px = (ndcX * 0.5f + 0.5f) * (float)fbWidth;
        float py = (1.0f - (ndcY * 0.5f + 0.5f)) * (float)fbHeight;
        return new float[]{px, py};
    }
}

