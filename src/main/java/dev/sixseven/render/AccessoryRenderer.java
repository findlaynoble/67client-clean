/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderPipeline
 *  com.mojang.blaze3d.pipeline.RenderPipeline$Snippet
 *  com.mojang.blaze3d.platform.DepthTestFunction
 *  net.minecraft.class_10799
 *  net.minecraft.class_12247
 *  net.minecraft.class_12249
 *  net.minecraft.class_1921
 *  net.minecraft.class_243
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_4184
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_4608
 *  net.minecraft.class_746
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package dev.sixseven.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import dev.sixseven.module.impl.CustomAccessoriesModule;
import dev.sixseven.render.FlatOverlay;
import dev.sixseven.rt.Deobf;
import dev.sixseven.util.Colors;
import java.util.Deque;
import net.minecraft.class_10799;
import net.minecraft.class_12247;
import net.minecraft.class_12249;
import net.minecraft.class_1921;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_746;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public final class AccessoryRenderer {
    private static final class_2960 TEXTURE_67 = class_2960.method_60655((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184"), (String)Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099\u00e6\u0116\u0100\u0110\u0120\u01df\u01a5\u0183\u01b0\u020b\u021f\u0252"));
    private static final int CAPE_COLS = 7;
    private static final int CAPE_ROWS = 9;
    private static final float CAPE_WIDTH = 0.62f;
    private static final float CAPE_LENGTH = 1.05f;
    private static final RenderPipeline CAPE_FILL_PIPELINE = RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{class_10799.field_56860}).withLocation(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01bc\u01c4\u01f7\u020b\u0214\u0259\u0235\u0297\u0290\u028d\u02b8\u031f\u0309\u0319\u037e\u03d6\u03fc\u039a\u03b5")).withCull(false).withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST).build();
    private static final class_1921 CAPE_FILL = class_1921.method_75940((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01a9\u01d7\u01ff\u020b\u0214\u026a\u023a\u0290\u0299\u02ce"), (class_12247)class_12247.method_75927((RenderPipeline)CAPE_FILL_PIPELINE).method_75937().method_75938());

    private AccessoryRenderer() {
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, CustomAccessoriesModule module) {
        boolean crownOn;
        class_310 mc = class_310.method_1551();
        class_746 player = mc.field_1724;
        if (player == null || mc.field_1687 == null) {
            return;
        }
        boolean firstPersonHidden = mc.field_1690.method_31044().method_31034() && (Boolean)module.firstPerson.get() == false;
        float pt = mc.method_61966().method_60637(false);
        double px = class_3532.method_16436((double)pt, (double)player.field_6014, (double)player.method_23317());
        double py = class_3532.method_16436((double)pt, (double)player.field_6036, (double)player.method_23318());
        double pz = class_3532.method_16436((double)pt, (double)player.field_5969, (double)player.method_23321());
        float bodyYaw = class_3532.method_17821((float)pt, (float)player.field_6220, (float)player.field_6283);
        float height = player.method_17682();
        float speed = (float)Math.hypot(player.method_23317() - player.field_6014, player.method_23321() - player.field_5969);
        long now = System.nanoTime();
        float time = (float)(now % 1000000000000L) / 1.0E9f;
        int rgb = module.currentRgb();
        float glow = module.glowStrength();
        class_4184 camera = mc.field_1773.method_19418();
        Vector3f fwd = new Vector3f(camera.method_19335());
        Vector3f bRight = new Vector3f();
        fwd.cross((Vector3fc)new Vector3f(0.0f, 1.0f, 0.0f), bRight);
        if (bRight.lengthSquared() < 1.0E-6f) {
            bRight.set(1.0f, 0.0f, 0.0f);
        }
        bRight.normalize();
        Vector3f bUp = new Vector3f();
        bRight.cross((Vector3fc)fwd, bUp);
        bUp.normalize();
        class_4587.class_4665 pose = poseStack.method_23760();
        boolean capeOn = (Boolean)module.cape.get() != false && !firstPersonHidden;
        boolean cape67 = capeOn && module.capeStyle.is(Deobf.decrypt("E\u001b"));
        float[] capeX = null;
        float[] capeY = null;
        float[] capeZ = null;
        if (capeOn) {
            int n = 80;
            capeX = new float[n];
            capeY = new float[n];
            capeZ = new float[n];
            AccessoryRenderer.buildCape(capeX, capeY, capeZ, px, py, pz, height, bodyYaw, speed, time, (Boolean)module.capePhysics.get(), cam);
        }
        class_4588 lines = bufferSource.method_73477(FlatOverlay.LINES);
        if (!firstPersonHidden && ((Boolean)module.trail.get()).booleanValue() && module.trailStyle.is(Deobf.decrypt("6O \u0001"))) {
            AccessoryRenderer.renderTrailEcho(lines, pose, cam, module, now, rgb);
        }
        if (((Boolean)module.aura.get()).booleanValue() && module.auraStyle.is(Deobf.decrypt("!E&\t"))) {
            AccessoryRenderer.renderAuraRing(lines, pose, cam, px, py, pz, time, rgb, glow);
        }
        if (capeOn && !cape67) {
            class_4588 capeFill = bufferSource.method_73477(CAPE_FILL);
            AccessoryRenderer.fillCape(capeFill, pose, capeX, capeY, capeZ, (String)module.capeStyle.get(), time, rgb);
            bufferSource.method_22994(CAPE_FILL);
        }
        class_4588 fill = bufferSource.method_73477(FlatOverlay.FILL);
        if (!firstPersonHidden && ((Boolean)module.trail.get()).booleanValue() && module.trailStyle.is(Deobf.decrypt("!E*\f}\u00aa"))) {
            AccessoryRenderer.renderTrailRibbon(fill, pose, cam, bRight, module, now, rgb, glow);
        }
        if (!firstPersonHidden && ((Boolean)module.trail.get()).booleanValue() && module.trailStyle.is(Deobf.decrypt(" \\)\u001cy\u00a8\u00a9"))) {
            AccessoryRenderer.renderTrailSparkle(fill, pose, cam, bRight, bUp, module, now, rgb, glow);
        }
        if (((Boolean)module.aura.get()).booleanValue() && module.auraStyle.is(Deobf.decrypt("<^*\u0007f"))) {
            AccessoryRenderer.renderAuraOrbit(fill, pose, cam, bRight, bUp, px, py, pz, time, rgb, glow);
        }
        FlatOverlay.flush(bufferSource);
        boolean bl = crownOn = (Boolean)module.crown.get() != false && !firstPersonHidden;
        if (cape67 || crownOn) {
            class_4588 glyphs = bufferSource.method_73477(class_12249.method_76002((class_2960)TEXTURE_67));
            if (cape67) {
                AccessoryRenderer.texCape(glyphs, pose, capeX, capeY, capeZ, fwd, rgb);
            }
            if (crownOn) {
                AccessoryRenderer.renderCrown(glyphs, pose, cam, bRight, bUp, fwd, px, py, pz, height, time, rgb);
            }
        }
    }

    private static void buildCape(float[] gx, float[] gy, float[] gz, double px, double py, double pz, float height, float bodyYaw, float speed, float time, boolean physics, class_243 cam) {
        float fz;
        float yawRad = bodyYaw * ((float)Math.PI / 180);
        float fx = -class_3532.method_15374((double)yawRad);
        float rx = fz = class_3532.method_15362((double)yawRad);
        float rz = -fx;
        float backX = -fx;
        float backZ = -fz;
        float shoulderY = (float)py + height * 0.78f;
        float ax = (float)px + backX * 0.14f;
        float az = (float)pz + backZ * 0.14f;
        float speedLean = physics ? Math.min(speed * 5.0f, 1.05f) : 0.0f;
        float idleLean = 0.1f;
        float waveAmp = physics ? 0.06f + speedLean * 0.14f : 0.02f;
        for (int r = 0; r <= 9; ++r) {
            float fr = (float)r / 9.0f;
            float droop = fr * fr;
            float back = (idleLean + speedLean) * droop;
            for (int c = 0; c <= 7; ++c) {
                float u = (float)c / 7.0f;
                float across = (u - 0.5f) * 0.62f;
                float wave = physics ? class_3532.method_15374((double)(time * 6.5f - fr * 4.2f + (float)c * 0.7f)) * waveAmp * fr : 0.0f;
                float shimmy = physics ? class_3532.method_15374((double)(time * 5.0f + fr * 3.5f)) * 0.03f * fr : 0.0f;
                float b = back + wave;
                int i = r * 8 + c;
                gx[i] = ax + rx * (across + shimmy) + backX * b - (float)cam.field_1352;
                gy[i] = shoulderY - fr * 1.05f - (float)cam.field_1351;
                gz[i] = az + rz * (across + shimmy) + backZ * b - (float)cam.field_1350;
            }
        }
    }

    private static void fillCape(class_4588 buf, class_4587.class_4665 pose, float[] gx, float[] gy, float[] gz, String style, float time, int rgb) {
        boolean grid = style.equals(Deobf.decrypt("4^!\n"));
        boolean wave = style.equals(Deobf.decrypt("$M>\u000b"));
        int top = Colors.lighten(rgb, 0.22f);
        int bottom = AccessoryRenderer.darkenRgb(rgb, 0.18f);
        for (int r = 0; r < 9; ++r) {
            for (int c = 0; c < 7; ++c) {
                int i00 = r * 8 + c;
                int i10 = i00 + 1;
                int i01 = i00 + 8;
                int i11 = i01 + 1;
                if (grid) {
                    boolean lit = (r + c & 1) == 0;
                    int cell = lit ? Colors.lighten(rgb, 0.35f) : AccessoryRenderer.darkenRgb(rgb, 0.5f);
                    int a = lit ? AccessoryRenderer.withA(cell, 0.9f) : AccessoryRenderer.withA(cell, 0.6f);
                    AccessoryRenderer.v(buf, pose, gx[i00], gy[i00], gz[i00], a);
                    AccessoryRenderer.v(buf, pose, gx[i01], gy[i01], gz[i01], a);
                    AccessoryRenderer.v(buf, pose, gx[i11], gy[i11], gz[i11], a);
                    AccessoryRenderer.v(buf, pose, gx[i10], gy[i10], gz[i10], a);
                    continue;
                }
                float f0 = (float)r / 9.0f;
                float f1 = (float)(r + 1) / 9.0f;
                int c0 = AccessoryRenderer.lerpRgb(top, bottom, f0);
                int c1 = AccessoryRenderer.lerpRgb(top, bottom, f1);
                if (wave) {
                    c0 = AccessoryRenderer.lerpRgb(c0, 0xFFFFFF, AccessoryRenderer.highlight(f0, time));
                    c1 = AccessoryRenderer.lerpRgb(c1, 0xFFFFFF, AccessoryRenderer.highlight(f1, time));
                }
                int a0 = AccessoryRenderer.withA(c0, 0.86f - 0.08f * f0);
                int a1 = AccessoryRenderer.withA(c1, 0.86f - 0.08f * f1);
                AccessoryRenderer.v(buf, pose, gx[i00], gy[i00], gz[i00], a0);
                AccessoryRenderer.v(buf, pose, gx[i01], gy[i01], gz[i01], a1);
                AccessoryRenderer.v(buf, pose, gx[i11], gy[i11], gz[i11], a1);
                AccessoryRenderer.v(buf, pose, gx[i10], gy[i10], gz[i10], a0);
            }
        }
    }

    private static void texCape(class_4588 buf, class_4587.class_4665 pose, float[] gx, float[] gy, float[] gz, Vector3f fwd, int rgb) {
        int argb = AccessoryRenderer.withA(rgb, 0.98f);
        float nx = -fwd.x;
        float ny = -fwd.y;
        float nz = -fwd.z;
        for (int r = 0; r < 9; ++r) {
            float v0 = (float)r / 9.0f;
            float v1 = (float)(r + 1) / 9.0f;
            for (int c = 0; c < 7; ++c) {
                float u0 = (float)c / 7.0f;
                float u1 = (float)(c + 1) / 7.0f;
                int i00 = r * 8 + c;
                int i10 = i00 + 1;
                int i01 = i00 + 8;
                int i11 = i01 + 1;
                AccessoryRenderer.tex(buf, pose, gx[i00], gy[i00], gz[i00], u0, v0, argb, nx, ny, nz);
                AccessoryRenderer.tex(buf, pose, gx[i01], gy[i01], gz[i01], u0, v1, argb, nx, ny, nz);
                AccessoryRenderer.tex(buf, pose, gx[i11], gy[i11], gz[i11], u1, v1, argb, nx, ny, nz);
                AccessoryRenderer.tex(buf, pose, gx[i10], gy[i10], gz[i10], u1, v0, argb, nx, ny, nz);
            }
        }
    }

    private static float highlight(float fr, float time) {
        float band = class_3532.method_15374((double)((fr - time * 0.35f % 1.0f) * ((float)Math.PI * 2)));
        return Math.max(0.0f, band) * 0.5f;
    }

    private static void renderTrailRibbon(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, CustomAccessoriesModule module, long now, int rgb, float glow) {
        Deque<CustomAccessoriesModule.TrailNode> nodes = module.trailNodes();
        if (nodes.size() < 2) {
            return;
        }
        float life = Math.max(0.2f, module.trailLength.getFloat());
        float maxHalf = 0.28f * (0.7f + 0.6f * glow);
        CustomAccessoriesModule.TrailNode prev = null;
        float prevHalf = 0.0f;
        float prevAlpha = 0.0f;
        float pLx = 0.0f;
        float pLy = 0.0f;
        float pLz = 0.0f;
        float pRx = 0.0f;
        float pRy = 0.0f;
        float pRz = 0.0f;
        for (CustomAccessoriesModule.TrailNode node : nodes) {
            float age = node.ageSeconds(now);
            if (age > life) {
                prev = null;
                continue;
            }
            float t = age / life;
            float half = maxHalf * (1.0f - t);
            float alpha = (1.0f - t) * (1.0f - t);
            float bx = (float)(node.x - cam.field_1352);
            float by = (float)(node.y - cam.field_1351);
            float bz = (float)(node.z - cam.field_1350);
            float lx = bx - right.x * half;
            float ly = by - right.y * half;
            float lz = bz - right.z * half;
            float rx = bx + right.x * half;
            float ry = by + right.y * half;
            float rz = bz + right.z * half;
            if (prev != null) {
                int cOld = AccessoryRenderer.withA(rgb, prevAlpha * 0.85f);
                int cNew = AccessoryRenderer.withA(Colors.lighten(rgb, 0.25f), alpha * 0.85f);
                AccessoryRenderer.v(buf, pose, pLx, pLy, pLz, cOld);
                AccessoryRenderer.v(buf, pose, lx, ly, lz, cNew);
                AccessoryRenderer.v(buf, pose, rx, ry, rz, cNew);
                AccessoryRenderer.v(buf, pose, pRx, pRy, pRz, cOld);
            }
            prev = node;
            prevHalf = half;
            prevAlpha = alpha;
            pLx = lx;
            pLy = ly;
            pLz = lz;
            pRx = rx;
            pRy = ry;
            pRz = rz;
        }
    }

    private static void renderTrailSparkle(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, CustomAccessoriesModule module, long now, int rgb, float glow) {
        Deque<CustomAccessoriesModule.TrailNode> nodes = module.trailNodes();
        float life = Math.max(0.2f, module.trailLength.getFloat());
        int idx = 0;
        for (CustomAccessoriesModule.TrailNode node : nodes) {
            float t;
            float alpha;
            float age;
            int i;
            if (((i = idx++) & 1) == 1 || (age = node.ageSeconds(now)) > life || (alpha = 1.0f - (t = age / life)) <= 0.02f) continue;
            float pop = t < 0.15f ? t / 0.15f : 1.0f;
            float sx = (AccessoryRenderer.hash(node.nanos, 1) - 0.5f) * 0.5f;
            float sy = (AccessoryRenderer.hash(node.nanos, 2) - 0.5f) * 0.4f + t * 0.35f;
            float sz = (AccessoryRenderer.hash(node.nanos, 3) - 0.5f) * 0.5f;
            float bx = (float)(node.x - cam.field_1352) + sx;
            float by = (float)(node.y - cam.field_1351) + sy;
            float bz = (float)(node.z - cam.field_1350) + sz;
            float sz2 = 0.05f * pop * (0.7f + 0.6f * glow) * (0.6f + 0.8f * (1.0f - t));
            int core = AccessoryRenderer.withA(Colors.lighten(rgb, 0.5f), alpha);
            AccessoryRenderer.diamond(buf, pose, right, up, bx, by, bz, sz2, core);
            if (!(glow > 0.01f)) continue;
            AccessoryRenderer.diamond(buf, pose, right, up, bx, by, bz, sz2 * 2.0f, AccessoryRenderer.withA(rgb, alpha * 0.25f * glow));
        }
    }

    private static void renderTrailEcho(class_4588 buf, class_4587.class_4665 pose, class_243 cam, CustomAccessoriesModule module, long now, int rgb) {
        Deque<CustomAccessoriesModule.TrailNode> nodes = module.trailNodes();
        float life = Math.max(0.2f, module.trailLength.getFloat());
        int idx = 0;
        for (CustomAccessoriesModule.TrailNode node : nodes) {
            float t;
            float alpha;
            float age;
            int i;
            if ((i = idx++) % 5 != 0 || (age = node.ageSeconds(now)) > life || (alpha = (1.0f - (t = age / life)) * 0.8f) <= 0.02f) continue;
            int color = AccessoryRenderer.withA(rgb, alpha);
            float x0 = (float)(node.x - 0.32 - cam.field_1352);
            float x1 = (float)(node.x + 0.32 - cam.field_1352);
            float y0 = (float)(node.y - 0.9 - cam.field_1351);
            float y1 = (float)(node.y + 0.9 - cam.field_1351);
            float z0 = (float)(node.z - 0.32 - cam.field_1350);
            float z1 = (float)(node.z + 0.32 - cam.field_1350);
            AccessoryRenderer.box(buf, pose, x0, y0, z0, x1, y1, z1, color, 1.6f);
        }
    }

    private static void box(class_4588 buf, class_4587.class_4665 pose, float x0, float y0, float z0, float x1, float y1, float z1, int color, float w) {
        AccessoryRenderer.line(buf, pose, x0, y0, z0, x1, y0, z0, color, w);
        AccessoryRenderer.line(buf, pose, x1, y0, z0, x1, y0, z1, color, w);
        AccessoryRenderer.line(buf, pose, x1, y0, z1, x0, y0, z1, color, w);
        AccessoryRenderer.line(buf, pose, x0, y0, z1, x0, y0, z0, color, w);
        AccessoryRenderer.line(buf, pose, x0, y1, z0, x1, y1, z0, color, w);
        AccessoryRenderer.line(buf, pose, x1, y1, z0, x1, y1, z1, color, w);
        AccessoryRenderer.line(buf, pose, x1, y1, z1, x0, y1, z1, color, w);
        AccessoryRenderer.line(buf, pose, x0, y1, z1, x0, y1, z0, color, w);
        AccessoryRenderer.line(buf, pose, x0, y0, z0, x0, y1, z0, color, w);
        AccessoryRenderer.line(buf, pose, x1, y0, z0, x1, y1, z0, color, w);
        AccessoryRenderer.line(buf, pose, x1, y0, z1, x1, y1, z1, color, w);
        AccessoryRenderer.line(buf, pose, x0, y0, z1, x0, y1, z1, color, w);
    }

    private static void renderAuraOrbit(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, double px, double py, double pz, float time, int rgb, float glow) {
        int count = 8;
        float radius = 0.72f;
        float baseY = (float)(py - cam.field_1351) + 0.12f;
        float cxr = (float)(px - cam.field_1352);
        float czr = (float)(pz - cam.field_1350);
        for (int i = 0; i < count; ++i) {
            float a = time * 1.7f + (float)i * ((float)Math.PI * 2 / (float)count);
            float ox = class_3532.method_15362((double)a) * radius;
            float oz = class_3532.method_15374((double)a) * radius;
            float oy = baseY + class_3532.method_15374((double)(time * 2.4f + (float)i)) * 0.18f + 0.25f;
            float bx = cxr + ox;
            float by = oy;
            float bz = czr + oz;
            float sz = 0.07f * (0.75f + 0.5f * glow);
            int core = AccessoryRenderer.withA(Colors.lighten(rgb, 0.45f), 0.95f);
            AccessoryRenderer.diamond(buf, pose, right, up, bx, by, bz, sz, core);
            if (!(glow > 0.01f)) continue;
            AccessoryRenderer.diamond(buf, pose, right, up, bx, by, bz, sz * 2.1f, AccessoryRenderer.withA(rgb, 0.22f * glow));
        }
    }

    private static void renderAuraRing(class_4588 buf, class_4587.class_4665 pose, class_243 cam, double px, double py, double pz, float time, int rgb, float glow) {
        float cxr = (float)(px - cam.field_1352);
        float czr = (float)(pz - cam.field_1350);
        for (int ring = 0; ring < 2; ++ring) {
            float phase = time * 0.9f + (float)ring * 0.5f;
            float pulse = phase - (float)Math.floor(phase);
            float radius = 0.4f + pulse * 1.1f;
            float alpha = (1.0f - pulse) * (0.7f + 0.3f * glow);
            if (alpha <= 0.02f) continue;
            int color = AccessoryRenderer.withA(Colors.lighten(rgb, 0.2f), alpha);
            float fy = (float)(py - cam.field_1351) + 0.04f + (float)ring * 0.02f;
            AccessoryRenderer.ring(buf, pose, cxr, fy, czr, radius, 40, color, 2.4f);
        }
    }

    private static void renderCrown(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, Vector3f fwd, double px, double py, double pz, float height, float time, int rgb) {
        float bob = class_3532.method_15374((double)(time * 2.0f)) * 0.06f;
        float bx = (float)(px - cam.field_1352);
        float by = (float)(py - cam.field_1351) + height + 0.55f + bob;
        float bz = (float)(pz - cam.field_1350);
        float half = 0.34f;
        float angle = time * 1.4f;
        float ca = class_3532.method_15362((double)angle);
        float sa = class_3532.method_15374((double)angle);
        Vector3f rAx = AccessoryRenderer.axis(right, up, ca * half, sa * half);
        Vector3f uAx = AccessoryRenderer.axis(right, up, -sa * half, ca * half);
        int argb = AccessoryRenderer.withA(rgb, 0.98f);
        float nx = -fwd.x;
        float ny = -fwd.y;
        float nz = -fwd.z;
        AccessoryRenderer.tex(buf, pose, bx - rAx.x - uAx.x, by - rAx.y - uAx.y, bz - rAx.z - uAx.z, 0.0f, 0.0f, argb, nx, ny, nz);
        AccessoryRenderer.tex(buf, pose, bx - rAx.x + uAx.x, by - rAx.y + uAx.y, bz - rAx.z + uAx.z, 0.0f, 1.0f, argb, nx, ny, nz);
        AccessoryRenderer.tex(buf, pose, bx + rAx.x + uAx.x, by + rAx.y + uAx.y, bz + rAx.z + uAx.z, 1.0f, 1.0f, argb, nx, ny, nz);
        AccessoryRenderer.tex(buf, pose, bx + rAx.x - uAx.x, by + rAx.y - uAx.y, bz + rAx.z - uAx.z, 1.0f, 0.0f, argb, nx, ny, nz);
    }

    private static Vector3f axis(Vector3f right, Vector3f up, float a, float b) {
        return new Vector3f(right.x * a + up.x * b, right.y * a + up.y * b, right.z * a + up.z * b);
    }

    private static void diamond(class_4588 buf, class_4587.class_4665 pose, Vector3f right, Vector3f up, float bx, float by, float bz, float r, int argb) {
        Vector3f a = AccessoryRenderer.axis(right, up, r, r);
        Vector3f b = AccessoryRenderer.axis(right, up, -r, r);
        AccessoryRenderer.v(buf, pose, bx - a.x, by - a.y, bz - a.z, argb);
        AccessoryRenderer.v(buf, pose, bx + b.x, by + b.y, bz + b.z, argb);
        AccessoryRenderer.v(buf, pose, bx + a.x, by + a.y, bz + a.z, argb);
        AccessoryRenderer.v(buf, pose, bx - b.x, by - b.y, bz - b.z, argb);
    }

    private static void ring(class_4588 buf, class_4587.class_4665 pose, float cx, float fy, float cz, float radius, int segments, int color, float width) {
        float prevX = cx + radius;
        float prevZ = cz;
        for (int i = 1; i <= segments; ++i) {
            float a = (float)i / (float)segments * ((float)Math.PI * 2);
            float x = cx + class_3532.method_15362((double)a) * radius;
            float z = cz + class_3532.method_15374((double)a) * radius;
            AccessoryRenderer.line(buf, pose, prevX, fy, prevZ, x, fy, z, color, width);
            prevX = x;
            prevZ = z;
        }
    }

    private static void v(class_4588 buf, class_4587.class_4665 pose, float x, float y, float z, int argb) {
        buf.method_56824(pose, x, y, z).method_39415(argb);
    }

    private static void line(class_4588 buf, class_4587.class_4665 pose, float x1, float y1, float z1, float x2, float y2, float z2, int argb, float width) {
        Vector3f n = new Vector3f(x2 - x1, y2 - y1, z2 - z1);
        if (n.lengthSquared() > 1.0E-9f) {
            n.normalize();
        } else {
            n.set(0.0f, 1.0f, 0.0f);
        }
        buf.method_56824(pose, x1, y1, z1).method_39415(argb).method_61959(pose, n).method_75298(width);
        buf.method_56824(pose, x2, y2, z2).method_39415(argb).method_61959(pose, n).method_75298(width);
    }

    private static void tex(class_4588 buf, class_4587.class_4665 pose, float x, float y, float z, float u, float vv, int argb, float nx, float ny, float nz) {
        buf.method_56824(pose, x, y, z).method_39415(argb).method_22913(u, vv).method_22922(class_4608.field_21444).method_60803(0xF000F0).method_60831(pose, nx, ny, nz);
    }

    private static int withA(int rgb, float alpha) {
        return Colors.withAlpha(rgb, alpha);
    }

    private static int lerpRgb(int from, int to, float t) {
        t = t < 0.0f ? 0.0f : (t > 1.0f ? 1.0f : t);
        int r = (int)class_3532.method_16439((float)t, (float)(from >> 16 & 0xFF), (float)(to >> 16 & 0xFF));
        int g = (int)class_3532.method_16439((float)t, (float)(from >> 8 & 0xFF), (float)(to >> 8 & 0xFF));
        int b = (int)class_3532.method_16439((float)t, (float)(from & 0xFF), (float)(to & 0xFF));
        return r << 16 | g << 8 | b;
    }

    private static int darkenRgb(int rgb, float t) {
        return AccessoryRenderer.lerpRgb(rgb, 0, t);
    }

    private static float hash(long seed, int salt) {
        float s = class_3532.method_15374((double)((float)(seed % 100000L) * 0.0131f + (float)salt * 12.9898f)) * 43758.547f;
        return s - (float)class_3532.method_15375((float)s);
    }
}

