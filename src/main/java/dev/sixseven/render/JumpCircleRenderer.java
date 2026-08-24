/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_12249
 *  net.minecraft.class_243
 *  net.minecraft.class_2960
 *  net.minecraft.class_3532
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_4608
 *  net.minecraft.class_7833
 *  org.joml.Quaternionfc
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.JumpCirclesModule;
import dev.sixseven.rt.Deobf;
import java.util.Deque;
import net.minecraft.class_12249;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_7833;
import org.joml.Quaternionfc;

public final class JumpCircleRenderer {
    private static final class_2960 TEXTURE_67 = class_2960.method_60655((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184"), (String)Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099\u00e6\u0116\u0100\u0110\u0120\u01df\u01a5\u0183\u01b0\u020b\u021f\u0252"));
    private static final class_2960 TEXTURE_RING = class_2960.method_60655((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184"), (String)Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099\u00e6\u0116\u0100\u0110\u0120\u01df\u01e1\u01dd\u01f0\u021c\u025f\u0245\u0232\u029e"));
    private static final float POP_IN_TIME = 0.25f;
    private static final float FADE_OUT_TIME = 0.4f;
    private static final float SHOCKWAVE_TIME = 0.45f;
    private static final float PULSE_SPEED = 10.0f;
    private static final int SHIMMER_TARGET_RGB = 16761566;

    private JumpCircleRenderer() {
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, JumpCirclesModule module) {
        Deque<JumpCirclesModule.JumpCircle> circles = module.circles();
        if (circles.isEmpty()) {
            return;
        }
        long now = System.nanoTime();
        float lifetime = Math.max(0.1f, module.lifetime.getFloat());
        while (!circles.isEmpty() && circles.peekFirst().ageSeconds(now) > lifetime) {
            circles.removeFirst();
        }
        if (circles.isEmpty()) {
            return;
        }
        float size = module.size.getFloat();
        int baseRgb = JumpCircleRenderer.currentBaseRgb(module);
        class_4588 decalBuffer = bufferSource.method_73477(class_12249.method_76002((class_2960)TEXTURE_67));
        for (JumpCirclesModule.JumpCircle circle : circles) {
            JumpCircleRenderer.renderDecal(poseStack, decalBuffer, circle, cam, now, lifetime, size, baseRgb);
        }
        if (((Boolean)module.shockwave.get()).booleanValue()) {
            class_4588 ringBuffer = bufferSource.method_73477(class_12249.method_76002((class_2960)TEXTURE_RING));
            for (JumpCirclesModule.JumpCircle circle : circles) {
                JumpCircleRenderer.renderShockwave(poseStack, ringBuffer, circle, cam, now, size, baseRgb);
            }
        }
    }

    private static void renderDecal(class_4587 poseStack, class_4588 buffer, JumpCirclesModule.JumpCircle circle, class_243 cam, long now, float lifetime, float size, int baseRgb) {
        float scale;
        float age = circle.ageSeconds(now);
        if (age < 0.25f) {
            t = age / 0.25f;
            scale = JumpCircleRenderer.easeOutBack(t);
            alpha = JumpCircleRenderer.easeOutCubic(Math.min(1.0f, t * 2.0f));
        } else if (age > lifetime - 0.4f) {
            t = (age - (lifetime - 0.4f)) / 0.4f;
            scale = JumpCircleRenderer.lerp(JumpCircleRenderer.easeOutCubic(t), 1.0f, 1.3f);
            alpha = 1.0f - JumpCircleRenderer.easeInQuad(t);
        } else {
            scale = 1.0f;
            alpha = 1.0f;
        }
        float pulse = 0.5f + 0.5f * class_3532.method_15374((double)(age * 10.0f));
        int rgb = JumpCircleRenderer.lerpRgb(baseRgb, 16761566, pulse * 0.35f);
        int argb = JumpCircleRenderer.withAlpha(rgb, alpha *= 0.82f + 0.18f * pulse);
        poseStack.method_22903();
        poseStack.method_22904(circle.x - cam.field_1352, circle.y + (double)circle.yLift - cam.field_1351, circle.z - cam.field_1350);
        poseStack.method_22907((Quaternionfc)class_7833.field_40716.rotationDegrees(180.0f - circle.yawDegrees));
        JumpCircleRenderer.emitQuad(poseStack.method_23760(), buffer, 0.5f * size * scale, argb);
        poseStack.method_22909();
    }

    private static void renderShockwave(class_4587 poseStack, class_4588 buffer, JumpCirclesModule.JumpCircle circle, class_243 cam, long now, float size, int baseRgb) {
        float age = circle.ageSeconds(now);
        if (age >= 0.45f) {
            return;
        }
        float t = age / 0.45f;
        float half = JumpCircleRenderer.lerp(JumpCircleRenderer.easeOutCubic(t), 0.35f, 2.2f) * size;
        float alpha = 0.85f * (1.0f - JumpCircleRenderer.easeInQuad(t));
        int argb = JumpCircleRenderer.withAlpha(baseRgb, alpha);
        poseStack.method_22903();
        poseStack.method_22904(circle.x - cam.field_1352, circle.y + (double)(circle.yLift * 0.5f) + (double)0.004f - cam.field_1351, circle.z - cam.field_1350);
        JumpCircleRenderer.emitQuad(poseStack.method_23760(), buffer, half, argb);
        poseStack.method_22909();
    }

    private static int currentBaseRgb(JumpCirclesModule module) {
        if (((Boolean)module.rainbow.get()).booleanValue()) {
            float hue = (float)(System.currentTimeMillis() % 4000L) / 4000.0f;
            return class_3532.method_15369((float)hue, (float)0.75f, (float)1.0f) & 0xFFFFFF;
        }
        return module.baseRgb();
    }

    private static void emitQuad(class_4587.class_4665 pose, class_4588 buffer, float half, int argb) {
        JumpCircleRenderer.vertex(pose, buffer, -half, -half, 0.0f, 0.0f, argb);
        JumpCircleRenderer.vertex(pose, buffer, -half, half, 0.0f, 1.0f, argb);
        JumpCircleRenderer.vertex(pose, buffer, half, half, 1.0f, 1.0f, argb);
        JumpCircleRenderer.vertex(pose, buffer, half, -half, 1.0f, 0.0f, argb);
    }

    private static void vertex(class_4587.class_4665 pose, class_4588 buffer, float x, float z, float u, float v, int argb) {
        buffer.method_56824(pose, x, 0.0f, z).method_39415(argb).method_22913(u, v).method_22922(class_4608.field_21444).method_60803(0xF000F0).method_60831(pose, 0.0f, 1.0f, 0.0f);
    }

    private static int withAlpha(int rgb, float alpha) {
        int a = (int)(JumpCircleRenderer.clamp01(alpha) * 255.0f);
        return a << 24 | rgb & 0xFFFFFF;
    }

    private static int lerpRgb(int from, int to, float t) {
        int r = (int)class_3532.method_16439((float)t, (float)(from >> 16 & 0xFF), (float)(to >> 16 & 0xFF));
        int g = (int)class_3532.method_16439((float)t, (float)(from >> 8 & 0xFF), (float)(to >> 8 & 0xFF));
        int b = (int)class_3532.method_16439((float)t, (float)(from & 0xFF), (float)(to & 0xFF));
        return r << 16 | g << 8 | b;
    }

    private static float clamp01(float t) {
        return t < 0.0f ? 0.0f : (t > 1.0f ? 1.0f : t);
    }

    private static float lerp(float t, float a, float b) {
        return a + (b - a) * t;
    }

    private static float easeOutCubic(float t) {
        t = JumpCircleRenderer.clamp01(t);
        float inv = 1.0f - t;
        return 1.0f - inv * inv * inv;
    }

    private static float easeInQuad(float t) {
        t = JumpCircleRenderer.clamp01(t);
        return t * t;
    }

    private static float easeOutBack(float t) {
        t = JumpCircleRenderer.clamp01(t);
        float overshoot = 2.2f;
        float c3 = overshoot + 1.0f;
        float u = t - 1.0f;
        return 1.0f + c3 * u * u * u + overshoot * u * u;
    }
}

