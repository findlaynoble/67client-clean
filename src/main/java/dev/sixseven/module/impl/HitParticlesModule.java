/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_5819
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.Colors;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.class_1297;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_5819;

public class HitParticlesModule
extends Module {
    public static final int STYLE_SPARKS = 0;
    public static final int STYLE_HEARTS = 1;
    public static final int STYLE_LIGHTNING = 2;
    public static final int STYLE_67 = 3;
    public final ModeSetting style = this.addSetting(new ModeSetting(Deobf.decrypt(" X1\u0002w"), Deobf.decrypt("1Y:\u001df\u00e4\u00bf\u009e\u00b0\u0117\u010c"), Deobf.decrypt(" \\)\u001cy\u00b7"), Deobf.decrypt(" \\)\u001cy\u00b7"), Deobf.decrypt(";I)\u001cf\u00b7"), Deobf.decrypt("?E/\u0006f\u00aa\u00a5\u0084\u00ae"), Deobf.decrypt("E\u001b")));
    public final SliderSetting amount = this.addSetting(new SliderSetting(Deobf.decrypt("2A'\u001b|\u00b0"), Deobf.decrypt("#M:\u001a{\u00a7\u00a0\u008f\u00ba\u015b\u011a\u0113\u0122\u0187\u01fd\u01d1\u01fa\u025b\u0201\u0250\u022e\u02d9\u029d\u02cb\u02af"), 14.0, 4.0, 40.0, 1.0));
    public final SliderSetting size = this.addSetting(new SliderSetting(Deobf.decrypt(" E2\u000b"), Deobf.decrypt("#M:\u001a{\u00a7\u00a0\u008f\u00e9\u0108\u010a\u0102\u012f\u0195"), 1.0, 0.3, 3.0, 0.1, Deobf.decrypt("\u000b")));
    public final SliderSetting lifetime = this.addSetting(new SliderSetting(Deobf.decrypt("?E.\u000bf\u00ad\u00a1\u008f"), Deobf.decrypt(";C?N~\u00ab\u00a2\u008d\u00e9\u010f\u0101\u0106\u0163\u0192\u01e6\u01c6\u01ed\u020f\u0251\u0259\u0235\u0297\u0292\u02c7\u02a9\u030d"), 0.7, 0.3, 2.0, 0.1, Deobf.decrypt("\u0000")));
    public final SliderSetting spread = this.addSetting(new SliderSetting(Deobf.decrypt(" \\:\u000bs\u00a0"), Deobf.decrypt(";C?Nt\u00a5\u00be\u00ca\u00b9\u011a\u011b\u0117\u012a\u0193\u01ff\u01d1\u01ed\u025b\u0217\u0259\u0225\u02d9\u029a\u02d7\u02af"), 1.0, 0.3, 2.5, 0.1, Deobf.decrypt("\u000b")));
    public final ColorSetting color = this.addSetting(new ColorSetting(Deobf.decrypt("0C$\u0001`"), Deobf.decrypt("#M:\u001a{\u00a7\u00a0\u008f\u00e9\u010f\u0100\u010d\u0137"), -49508));
    public final BooleanSetting rainbow = this.addSetting(new BooleanSetting(Deobf.decrypt("!M!\u0000p\u00ab\u00bb"), Deobf.decrypt("0U+\u0002w\u00e4\u00a9\u008b\u00aa\u0113\u0149\u0101\u0136\u0182\u01e0\u01c0\u01be\u020f\u0219\u0247\u0233\u028c\u0292\u02ca\u02fb\u030a\u0311\u0319\u0301\u03c2\u03f4\u039f\u03b7\u03df\u0440\u0439"), false));
    public final SliderSetting glow = this.addSetting(new SliderSetting(Deobf.decrypt("4@'\u0019"), Deobf.decrypt(" C.\u001a2\u00ab\u00b9\u009e\u00ac\u0109\u0149\u010b\u0122\u019c\u01fc\u0194\u01f7\u0215\u0205\u0250\u0232\u028a\u029c\u02d6\u02a2"), 65.0, 0.0, 100.0, 5.0, Deobf.decrypt("V")));
    public final BooleanSetting shockwave = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\ry\u00b3\u00ad\u009c\u00ac"), Deobf.decrypt("6T8\u000f|\u00a0\u00a5\u0084\u00ae\u015b\u011b\u010a\u012d\u0197\u01b3\u01db\u01f0\u025b\u0214\u0254\u023f\u0291\u02d5\u02ca\u02b2\u030a"), true));
    private final Deque<HitParticle> particles = new ArrayDeque<HitParticle>();
    private final Deque<Shock> shocks = new ArrayDeque<Shock>();
    private static final int MAX_PARTICLES = 600;
    private static final int MAX_SHOCKS = 24;

    public HitParticlesModule() {
        super(Deobf.decrypt(";E<>s\u00b6\u00b8\u0083\u00aa\u0117\u010c\u0110"), Deobf.decrypt("'D-\u0003w\u00a0\u00ec\u009a\u00a8\u0109\u011d\u010a\u0120\u019c\u01f6\u0194\u01fc\u020e\u0203\u0246\u0228\u028a\u02d5\u02d5\u02b3\u031b\u0317\u035c\u0358\u03df\u03e0\u03d6\u03b1\u03d4\u045b\u046e\u040e\u0463\u0489\u04b2\u04e9\u04f5\u054c\u0570\u0541"), Category.VISUALS);
    }

    public Deque<HitParticle> particles() {
        return this.particles;
    }

    public Deque<Shock> shocks() {
        return this.shocks;
    }

    @Override
    protected void onDisable() {
        this.clear();
    }

    public void clear() {
        this.particles.clear();
        this.shocks.clear();
    }

    private int currentRgb() {
        if (((Boolean)this.rainbow.get()).booleanValue()) {
            float hue = (float)(System.currentTimeMillis() % 3500L) / 3500.0f * 360.0f;
            return Colors.hsvToRgb(hue, 0.85f, 1.0f) & 0xFFFFFF;
        }
        return (Integer)this.color.get() & 0xFFFFFF;
    }

    public void onHit(class_1297 target) {
        if (!this.isEnabled() || target == null) {
            return;
        }
        double cx = target.method_23317();
        double cy = target.method_23318() + (double)target.method_17682() * 0.6;
        double cz = target.method_23321();
        this.spawnAt(cx, cy, cz);
    }

    public void spawnAt(double cx, double cy, double cz) {
        class_310 mc = class_310.method_1551();
        if (mc.field_1687 == null) {
            return;
        }
        class_5819 random = mc.field_1687.field_9229;
        int styleId = this.styleId();
        int rgb = this.currentRgb();
        int count = this.amount.getInt();
        float life = this.lifetime.getFloat();
        float spreadScale = this.spread.getFloat();
        float sizeScale = this.size.getFloat();
        long now = System.nanoTime();
        for (int i = 0; i < count; ++i) {
            this.spawnOne(random, styleId, rgb, cx, cy, cz, life, spreadScale, sizeScale, now);
        }
        if (((Boolean)this.shockwave.get()).booleanValue()) {
            this.shocks.addLast(new Shock(cx, cy, cz, rgb, styleId, now));
            while (this.shocks.size() > 24) {
                this.shocks.removeFirst();
            }
        }
        while (this.particles.size() > 600) {
            this.particles.removeFirst();
        }
    }

    private void spawnOne(class_5819 random, int styleId, int rgb, double cx, double cy, double cz, float life, float spreadScale, float sizeScale, long now) {
        float gravity;
        float speed;
        double theta = random.method_43058() * Math.PI * 2.0;
        double cosPhi = 2.0 * random.method_43058() - 1.0;
        double sinPhi = Math.sqrt(Math.max(0.0, 1.0 - cosPhi * cosPhi));
        float dx = (float)(sinPhi * Math.cos(theta));
        float dy = (float)cosPhi;
        float dz = (float)(sinPhi * Math.sin(theta));
        float particleLife = life * (0.75f + random.method_43057() * 0.25f);
        float pSize = sizeScale * (0.7f + random.method_43057() * 0.6f);
        switch (styleId) {
            case 1: {
                dx *= 0.5f;
                dz *= 0.5f;
                dy = 0.4f + Math.abs(dy) * 0.5f;
                speed = (1.6f + random.method_43057() * 1.0f) * spreadScale;
                gravity = 1.2f;
                particleLife = life * (1.0f + random.method_43057() * 0.3f);
                break;
            }
            case 2: {
                dy *= 0.25f;
                float horiz = class_3532.method_15355((float)Math.max(1.0E-4f, dx * dx + dz * dz));
                dx /= horiz;
                dz /= horiz;
                speed = (5.0f + random.method_43057() * 3.0f) * spreadScale;
                gravity = 0.6f;
                particleLife = life * (0.45f + random.method_43057() * 0.25f);
                break;
            }
            case 3: {
                dy = 0.12f + dy * 0.35f;
                speed = (1.9f + random.method_43057() * 1.4f) * spreadScale;
                gravity = 1.3f;
                particleLife = life * (1.0f + random.method_43057() * 0.25f);
                break;
            }
            default: {
                dy = dy * 0.7f + 0.3f;
                speed = (4.0f + random.method_43057() * 3.5f) * spreadScale;
                gravity = 6.5f;
            }
        }
        float vx = dx * speed;
        float vy = dy * speed;
        float vz = dz * speed;
        float rot = random.method_43057() * ((float)Math.PI * 2);
        float rotSpeed = (random.method_43057() - 0.5f) * 8.0f;
        this.particles.addLast(new HitParticle(cx, cy, cz, vx, vy, vz, rgb, styleId, pSize, rot, rotSpeed, gravity, particleLife, now));
    }

    private int styleId() {
        if (this.style.is(Deobf.decrypt(";I)\u001cf\u00b7"))) {
            return 1;
        }
        if (this.style.is(Deobf.decrypt("?E/\u0006f\u00aa\u00a5\u0084\u00ae"))) {
            return 2;
        }
        if (this.style.is(Deobf.decrypt("E\u001b"))) {
            return 3;
        }
        return 0;
    }

    public float glowStrength() {
        return this.glow.getFloat() / 100.0f;
    }

    public static final class Shock {
        public final double x;
        public final double y;
        public final double z;
        public final int rgb;
        public final int styleId;
        public final long spawnNanos;

        Shock(double x, double y, double z, int rgb, int styleId, long spawnNanos) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.rgb = rgb;
            this.styleId = styleId;
            this.spawnNanos = spawnNanos;
        }

        public float ageSeconds(long nowNanos) {
            return (float)(nowNanos - this.spawnNanos) / 1.0E9f;
        }
    }

    public static final class HitParticle {
        public final double ox;
        public final double oy;
        public final double oz;
        public final float vx;
        public final float vy;
        public final float vz;
        public final int rgb;
        public final int styleId;
        public final float size;
        public final float rot;
        public final float rotSpeed;
        public final float gravity;
        public final float lifetime;
        public final long spawnNanos;

        HitParticle(double ox, double oy, double oz, float vx, float vy, float vz, int rgb, int styleId, float size, float rot, float rotSpeed, float gravity, float lifetime, long spawnNanos) {
            this.ox = ox;
            this.oy = oy;
            this.oz = oz;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.rgb = rgb;
            this.styleId = styleId;
            this.size = size;
            this.rot = rot;
            this.rotSpeed = rotSpeed;
            this.gravity = gravity;
            this.lifetime = lifetime;
            this.spawnNanos = spawnNanos;
        }

        public float ageSeconds(long nowNanos) {
            return (float)(nowNanos - this.spawnNanos) / 1.0E9f;
        }

        public double x(float age) {
            return this.ox + (double)(this.vx * age);
        }

        public double y(float age) {
            return this.oy + (double)(this.vy * age) - 0.5 * (double)this.gravity * (double)age * (double)age;
        }

        public double z(float age) {
            return this.oz + (double)(this.vz * age);
        }
    }
}

