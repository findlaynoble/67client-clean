/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_12249
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
 *  org.joml.Vector3f
 *  org.joml.Vector3fc
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.HitParticlesModule;
import dev.sixseven.render.FlatOverlay;
import dev.sixseven.rt.Deobf;
import dev.sixseven.util.Colors;
import java.util.Deque;
import net.minecraft.class_12249;
import net.minecraft.class_243;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_4184;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public final class HitParticleRenderer {
    private static final class_2960 TEXTURE_67 = class_2960.method_60655((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184"), (String)Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099\u00e6\u0116\u0100\u0110\u0120\u01df\u01a5\u0183\u01b0\u020b\u021f\u0252"));
    private static final int HEART_SEGMENTS = 20;
    private static final float[] HEART_X = new float[21];
    private static final float[] HEART_Y = new float[21];
    private static final float SHOCK_TIME = 0.5f;

    private HitParticleRenderer() {
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, HitParticlesModule module) {
        Deque<HitParticlesModule.HitParticle> particles = module.particles();
        Deque<HitParticlesModule.Shock> shocks = module.shocks();
        if (particles.isEmpty() && shocks.isEmpty()) {
            return;
        }
        long now = System.nanoTime();
        while (!particles.isEmpty() && particles.peekFirst().ageSeconds(now) > particles.peekFirst().lifetime) {
            particles.removeFirst();
        }
        while (!shocks.isEmpty() && shocks.peekFirst().ageSeconds(now) > 0.5f) {
            shocks.removeFirst();
        }
        if (particles.isEmpty() && shocks.isEmpty()) {
            return;
        }
        class_4184 camera = class_310.method_1551().field_1773.method_19418();
        Vector3f fwd = new Vector3f(camera.method_19335());
        Vector3f right = new Vector3f();
        fwd.cross((Vector3fc)new Vector3f(0.0f, 1.0f, 0.0f), right);
        if (right.lengthSquared() < 1.0E-6f) {
            right.set(1.0f, 0.0f, 0.0f);
        }
        right.normalize();
        Vector3f up = new Vector3f();
        right.cross((Vector3fc)fwd, up);
        up.normalize();
        float glow = module.glowStrength();
        class_4587.class_4665 pose = poseStack.method_23760();
        class_4588 lines = bufferSource.method_73477(FlatOverlay.LINES);
        for (HitParticlesModule.Shock shock : shocks) {
            HitParticleRenderer.renderShock(lines, pose, cam, right, up, shock, now);
        }
        boolean any67 = false;
        for (HitParticlesModule.HitParticle hitParticle : particles) {
            float age = hitParticle.ageSeconds(now);
            if (age < 0.0f || age > hitParticle.lifetime) continue;
            if (hitParticle.styleId == 2) {
                HitParticleRenderer.renderLightning(lines, pose, cam, right, up, hitParticle, age);
                continue;
            }
            if (hitParticle.styleId != 3) continue;
            any67 = true;
        }
        class_4588 class_45882 = bufferSource.method_73477(FlatOverlay.FILL);
        for (HitParticlesModule.HitParticle p : particles) {
            float age = p.ageSeconds(now);
            if (age < 0.0f || age > p.lifetime) continue;
            if (p.styleId == 1) {
                HitParticleRenderer.renderHeart(class_45882, pose, cam, right, up, p, age, glow);
                continue;
            }
            if (p.styleId != 0) continue;
            HitParticleRenderer.renderSpark(class_45882, pose, cam, right, up, p, age, glow);
        }
        FlatOverlay.flush(bufferSource);
        if (any67) {
            class_4588 class_45883 = bufferSource.method_73477(class_12249.method_76002((class_2960)TEXTURE_67));
            for (HitParticlesModule.HitParticle p : particles) {
                float age;
                if (p.styleId != 3 || (age = p.ageSeconds(now)) < 0.0f || age > p.lifetime) continue;
                HitParticleRenderer.render67(class_45883, pose, cam, right, up, fwd, p, age);
            }
        }
    }

    private static void renderSpark(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, HitParticlesModule.HitParticle p, float age, float glow) {
        float dirU;
        float dirR;
        float t = age / p.lifetime;
        float alpha = HitParticleRenderer.fadeAlpha(t);
        if (alpha <= 0.01f) {
            return;
        }
        float bx = (float)(p.x(age) - cam.field_1352);
        float by = (float)(p.y(age) - cam.field_1351);
        float bz = (float)(p.z(age) - cam.field_1350);
        float vy = p.vy - p.gravity * age;
        float sr = p.vx * right.x + vy * right.y + p.vz * right.z;
        float su = p.vx * up.x + vy * up.y + p.vz * up.z;
        float slen = class_3532.method_15355((float)(sr * sr + su * su));
        if (slen > 1.0E-4f) {
            dirR = sr / slen;
            dirU = su / slen;
        } else {
            dirR = 0.0f;
            dirU = 1.0f;
        }
        float shrink = 0.35f + 0.65f * (1.0f - t);
        float streak = 0.28f * p.size * shrink;
        float width = 0.055f * p.size * shrink;
        Vector3f lAx = HitParticleRenderer.axis(right, up, dirR * streak, dirU * streak);
        Vector3f sAx = HitParticleRenderer.axis(right, up, -dirU * width, dirR * width);
        int core = Colors.withAlpha(Colors.lighten(p.rgb, 0.55f), alpha);
        HitParticleRenderer.quad(buf, pose, bx, by, bz, lAx, sAx, core);
        if (glow > 0.01f) {
            float halo = 0.06f * p.size * shrink * (1.0f + 0.6f * glow);
            Vector3f hA = HitParticleRenderer.axis(right, up, halo, halo);
            Vector3f hB = HitParticleRenderer.axis(right, up, -halo, halo);
            int haloColor = Colors.withAlpha(p.rgb, alpha * 0.22f * glow);
            HitParticleRenderer.quad(buf, pose, bx, by, bz, hA, hB, haloColor);
        }
    }

    private static void renderHeart(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, HitParticlesModule.HitParticle p, float age, float glow) {
        float t = age / p.lifetime;
        float alpha = HitParticleRenderer.fadeAlpha(t);
        if (alpha <= 0.01f) {
            return;
        }
        float pop = t < 0.2f ? HitParticleRenderer.easeOutBack(t / 0.2f) : 1.0f;
        float scale = 0.16f * p.size * pop;
        float bx = (float)(p.x(age) - cam.field_1352);
        float by = (float)(p.y(age) - cam.field_1351);
        float bz = (float)(p.z(age) - cam.field_1350);
        float wobble = 0.18f * class_3532.method_15374((double)(age * 6.0f + p.rot));
        int core = Colors.withAlpha(Colors.lighten(p.rgb, 0.25f), alpha);
        HitParticleRenderer.fanHeart(buf, pose, right, up, bx, by, bz, scale, wobble, core);
        if (glow > 0.01f) {
            int haloColor = Colors.withAlpha(p.rgb, alpha * 0.3f * glow);
            HitParticleRenderer.fanHeart(buf, pose, right, up, bx, by, bz, scale * (1.35f + 0.35f * glow), wobble, haloColor);
        }
    }

    private static void fanHeart(class_4588 buf, class_4587.class_4665 pose, Vector3f right, Vector3f up, float bx, float by, float bz, float scale, float shearX, int argb) {
        for (int i = 0; i < 20; ++i) {
            float ax0 = (HEART_X[i] + shearX * HEART_Y[i]) * scale;
            float ay0 = HEART_Y[i] * scale;
            float ax1 = (HEART_X[i + 1] + shearX * HEART_Y[i + 1]) * scale;
            float ay1 = HEART_Y[i + 1] * scale;
            HitParticleRenderer.v(buf, pose, bx, by, bz, argb);
            HitParticleRenderer.v(buf, pose, bx + right.x * ax0 + up.x * ay0, by + right.y * ax0 + up.y * ay0, bz + right.z * ax0 + up.z * ay0, argb);
            HitParticleRenderer.v(buf, pose, bx + right.x * ax1 + up.x * ay1, by + right.y * ax1 + up.y * ay1, bz + right.z * ax1 + up.z * ay1, argb);
            HitParticleRenderer.v(buf, pose, bx, by, bz, argb);
        }
    }

    private static void renderLightning(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, HitParticlesModule.HitParticle p, float age) {
        float pu;
        float pr;
        float du;
        float ez;
        float dz;
        float ey;
        float dy;
        float t = age / p.lifetime;
        float alpha = HitParticleRenderer.fadeAlpha(t);
        if (alpha <= 0.01f) {
            return;
        }
        float flicker = 0.45f + 0.55f * class_3532.method_15379((float)class_3532.method_15374((double)(age * 42.0f + p.rot)));
        alpha *= flicker;
        float sx = (float)(p.ox - cam.field_1352);
        float sy = (float)(p.oy - cam.field_1351);
        float sz = (float)(p.oz - cam.field_1350);
        float ex = (float)(p.x(age) - cam.field_1352);
        float dx = ex - sx;
        float dr = dx * right.x + (dy = (ey = (float)(p.y(age) - cam.field_1351)) - sy) * right.y + (dz = (ez = (float)(p.z(age) - cam.field_1350)) - sz) * right.z;
        float dlen = class_3532.method_15355((float)(dr * dr + (du = dx * up.x + dy * up.y + dz * up.z) * du));
        if (dlen > 1.0E-4f) {
            pr = -du / dlen;
            pu = dr / dlen;
        } else {
            pr = 1.0f;
            pu = 0.0f;
        }
        float amp = 0.16f * p.size;
        int core = Colors.withAlpha(Colors.lighten(p.rgb, 0.6f), alpha);
        float lineW = 2.4f * p.size;
        int kinks = 4;
        float px = sx;
        float py = sy;
        float pz = sz;
        for (int i = 1; i <= kinks; ++i) {
            float f = (float)i / (float)kinks;
            float taper = class_3532.method_15374((double)(f * (float)Math.PI));
            float j = i == kinks ? 0.0f : (HitParticleRenderer.hash(p, i) * 2.0f - 1.0f) * amp * taper;
            float nx = sx + dx * f + (right.x * pr + up.x * pu) * j;
            float ny = sy + dy * f + (right.y * pr + up.y * pu) * j;
            float nz = sz + dz * f + (right.z * pr + up.z * pu) * j;
            HitParticleRenderer.line(buf, pose, px, py, pz, nx, ny, nz, core, lineW);
            px = nx;
            py = ny;
            pz = nz;
        }
    }

    private static void render67(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, Vector3f fwd, HitParticlesModule.HitParticle p, float age) {
        float t = age / p.lifetime;
        float alpha = HitParticleRenderer.fadeAlpha(t);
        if (alpha <= 0.01f) {
            return;
        }
        float pop = t < 0.22f ? HitParticleRenderer.easeOutBack(t / 0.22f) : 1.0f;
        float half = 0.16f * p.size * pop;
        float angle = p.rot + p.rotSpeed * age * 0.5f;
        float ca = class_3532.method_15362((double)angle);
        float sa = class_3532.method_15374((double)angle);
        Vector3f rAx = HitParticleRenderer.axis(right, up, ca * half, sa * half);
        Vector3f uAx = HitParticleRenderer.axis(right, up, -sa * half, ca * half);
        float bx = (float)(p.x(age) - cam.field_1352);
        float by = (float)(p.y(age) - cam.field_1351);
        float bz = (float)(p.z(age) - cam.field_1350);
        int argb = Colors.withAlpha(p.rgb, alpha);
        float nx = -fwd.x;
        float ny = -fwd.y;
        float nz = -fwd.z;
        HitParticleRenderer.texVertex(buf, pose, bx - rAx.x - uAx.x, by - rAx.y - uAx.y, bz - rAx.z - uAx.z, 0.0f, 0.0f, argb, nx, ny, nz);
        HitParticleRenderer.texVertex(buf, pose, bx - rAx.x + uAx.x, by - rAx.y + uAx.y, bz - rAx.z + uAx.z, 0.0f, 1.0f, argb, nx, ny, nz);
        HitParticleRenderer.texVertex(buf, pose, bx + rAx.x + uAx.x, by + rAx.y + uAx.y, bz + rAx.z + uAx.z, 1.0f, 1.0f, argb, nx, ny, nz);
        HitParticleRenderer.texVertex(buf, pose, bx + rAx.x - uAx.x, by + rAx.y - uAx.y, bz + rAx.z - uAx.z, 1.0f, 0.0f, argb, nx, ny, nz);
    }

    private static void renderShock(class_4588 buf, class_4587.class_4665 pose, class_243 cam, Vector3f right, Vector3f up, HitParticlesModule.Shock shock, long now) {
        float age = shock.ageSeconds(now);
        if (age < 0.0f || age >= 0.5f) {
            return;
        }
        float t = age / 0.5f;
        float radius = 0.15f + HitParticleRenderer.easeOutCubic(t) * 1.15f;
        float alpha = 0.8f * (1.0f - HitParticleRenderer.easeInQuad(t));
        int color = Colors.withAlpha(Colors.lighten(shock.rgb, 0.2f), alpha);
        float bx = (float)(shock.x - cam.field_1352);
        float by = (float)(shock.y - cam.field_1351);
        float bz = (float)(shock.z - cam.field_1350);
        int segs = 28;
        float prevX = 0.0f;
        float prevY = 0.0f;
        float prevZ = 0.0f;
        for (int i = 0; i <= segs; ++i) {
            float a = (float)i / (float)segs * ((float)Math.PI * 2);
            float ox = class_3532.method_15362((double)a) * radius;
            float oy = class_3532.method_15374((double)a) * radius;
            float x = bx + right.x * ox + up.x * oy;
            float y = by + right.y * ox + up.y * oy;
            float z = bz + right.z * ox + up.z * oy;
            if (i > 0) {
                HitParticleRenderer.line(buf, pose, prevX, prevY, prevZ, x, y, z, color, 2.2f);
            }
            prevX = x;
            prevY = y;
            prevZ = z;
        }
    }

    private static Vector3f axis(Vector3f right, Vector3f up, float a, float b) {
        return new Vector3f(right.x * a + up.x * b, right.y * a + up.y * b, right.z * a + up.z * b);
    }

    private static void quad(class_4588 buf, class_4587.class_4665 pose, float bx, float by, float bz, Vector3f ax1, Vector3f ax2, int argb) {
        HitParticleRenderer.v(buf, pose, bx - ax1.x - ax2.x, by - ax1.y - ax2.y, bz - ax1.z - ax2.z, argb);
        HitParticleRenderer.v(buf, pose, bx + ax1.x - ax2.x, by + ax1.y - ax2.y, bz + ax1.z - ax2.z, argb);
        HitParticleRenderer.v(buf, pose, bx + ax1.x + ax2.x, by + ax1.y + ax2.y, bz + ax1.z + ax2.z, argb);
        HitParticleRenderer.v(buf, pose, bx - ax1.x + ax2.x, by - ax1.y + ax2.y, bz - ax1.z + ax2.z, argb);
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

    private static void texVertex(class_4588 buf, class_4587.class_4665 pose, float x, float y, float z, float u, float vv, int argb, float nx, float ny, float nz) {
        buf.method_56824(pose, x, y, z).method_39415(argb).method_22913(u, vv).method_22922(class_4608.field_21444).method_60803(0xF000F0).method_60831(pose, nx, ny, nz);
    }

    private static float hash(HitParticlesModule.HitParticle p, int salt) {
        float s = class_3532.method_15374((double)((float)(p.ox * 12.9898 + p.oz * 78.233 + (double)p.rot * 3.17 + (double)salt * 43.123))) * 43758.547f;
        return s - (float)class_3532.method_15375((float)s);
    }

    private static float fadeAlpha(float t) {
        if ((t = HitParticleRenderer.clamp01(t)) < 0.12f) {
            return t / 0.12f;
        }
        return 1.0f - HitParticleRenderer.easeInQuad((t - 0.12f) / 0.88f);
    }

    private static float clamp01(float t) {
        return t < 0.0f ? 0.0f : (t > 1.0f ? 1.0f : t);
    }

    private static float easeInQuad(float t) {
        t = HitParticleRenderer.clamp01(t);
        return t * t;
    }

    private static float easeOutCubic(float t) {
        t = HitParticleRenderer.clamp01(t);
        float inv = 1.0f - t;
        return 1.0f - inv * inv * inv;
    }

    private static float easeOutBack(float t) {
        t = HitParticleRenderer.clamp01(t);
        float overshoot = 2.4f;
        float c3 = overshoot + 1.0f;
        float u = t - 1.0f;
        return 1.0f + c3 * u * u * u + overshoot * u * u;
    }

    static {
        for (int i = 0; i <= 20; ++i) {
            double t = (double)i / 20.0 * Math.PI * 2.0;
            double hx = 16.0 * Math.pow(Math.sin(t), 3.0);
            double hy = 13.0 * Math.cos(t) - 5.0 * Math.cos(2.0 * t) - 2.0 * Math.cos(3.0 * t) - Math.cos(4.0 * t);
            HitParticleRenderer.HEART_X[i] = (float)(hx / 17.0);
            HitParticleRenderer.HEART_Y[i] = (float)(hy / 17.0);
        }
    }
}

