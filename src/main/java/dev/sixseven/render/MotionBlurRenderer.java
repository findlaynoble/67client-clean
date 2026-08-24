/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.nanovg.NanoVG
 *  org.lwjgl.nanovg.NanoVGGL3
 *  org.lwjgl.opengl.GL33C
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.MotionBlurModule;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.util.Colors;
import java.nio.ByteBuffer;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL33C;

public final class MotionBlurRenderer {
    private static final double EASE_SPEED = 12.0;
    private static int historyTex = -1;
    private static int historyImage = -1;
    private static int texW;
    private static int texH;
    private static boolean primed;
    private static long lastNanos;
    private static double enableAmount;
    private static int framesRendered;
    private static float lastRetention;

    private MotionBlurRenderer() {
    }

    public static void render(NVGRenderer vg, int width, int height, MotionBlurModule module) {
        if (width <= 0 || height <= 0) {
            return;
        }
        long now = System.nanoTime();
        double dt = lastNanos == 0L ? 0.0 : (double)(now - lastNanos) / 1.0E9;
        lastNanos = now;
        double easeT = 1.0 - Math.exp(-12.0 * Math.max(0.0, dt));
        if ((enableAmount += (1.0 - enableAmount) * easeT) > 0.999) {
            enableAmount = 1.0;
        }
        if (historyTex == -1 || texW != width || texH != height) {
            MotionBlurRenderer.allocate(vg, width, height);
            primed = false;
        }
        double base = module.retention();
        if (((Boolean)module.fpsCompensated.get()).booleanValue() && dt > 0.0) {
            base = Math.pow(base, dt * 60.0);
        }
        double retention = Math.clamp(base * enableAmount, 0.0, 0.97);
        lastRetention = (float)retention;
        if (!primed) {
            MotionBlurRenderer.copyToHistory(width, height);
            primed = true;
            ++framesRendered;
            return;
        }
        int tintColor = -1;
        float tintAmount = module.tintAmount();
        if (tintAmount > 0.0f) {
            tintColor = Colors.lerp(-1, Colors.withAlpha(module.accentColor(), 255), tintAmount);
        }
        vg.beginFrame(width, height, 1.0f);
        vg.save();
        vg.alpha((float)retention);
        vg.image(historyImage, 0.0f, 0.0f, width, height, tintColor);
        vg.restore();
        vg.endFrame();
        MotionBlurRenderer.copyToHistory(width, height);
        ++framesRendered;
    }

    private static void allocate(NVGRenderer vg, int width, int height) {
        long ctx = vg.ctx();
        if (historyImage > 0) {
            NanoVG.nvgDeleteImage((long)ctx, (int)historyImage);
            historyImage = -1;
        }
        if (historyTex != -1) {
            GL33C.glDeleteTextures((int)historyTex);
        }
        historyTex = GL33C.glGenTextures();
        GL33C.glActiveTexture((int)33984);
        GL33C.glBindTexture((int)3553, (int)historyTex);
        GL33C.glTexImage2D((int)3553, (int)0, (int)32856, (int)width, (int)height, (int)0, (int)6408, (int)5121, (ByteBuffer)null);
        GL33C.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL33C.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL33C.glTexParameteri((int)3553, (int)10242, (int)33071);
        GL33C.glTexParameteri((int)3553, (int)10243, (int)33071);
        GL33C.glTexParameteri((int)3553, (int)36421, (int)1);
        texW = width;
        texH = height;
        historyImage = NanoVGGL3.nvglCreateImageFromHandle((long)ctx, (int)historyTex, (int)width, (int)height, (int)65544);
    }

    private static void copyToHistory(int width, int height) {
        GL33C.glActiveTexture((int)33984);
        GL33C.glBindTexture((int)3553, (int)historyTex);
        GL33C.glCopyTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)0, (int)0, (int)width, (int)height);
    }

    public static void reset() {
        primed = false;
        enableAmount = 0.0;
        lastNanos = 0L;
    }

    public static int framesRendered() {
        return framesRendered;
    }

    public static float lastRetention() {
        return lastRetention;
    }
}

