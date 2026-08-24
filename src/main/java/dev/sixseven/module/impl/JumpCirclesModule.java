/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2390
 *  net.minecraft.class_2394
 *  net.minecraft.class_5819
 *  net.minecraft.class_746
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.SliderSetting;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.class_2390;
import net.minecraft.class_2394;
import net.minecraft.class_5819;
import net.minecraft.class_746;

public class JumpCirclesModule
extends Module {
    public final SliderSetting size = this.addSetting(new SliderSetting(Deobf.decrypt(" E2\u000b"), Deobf.decrypt("7I+\u000f~\u00e4\u00bf\u0089\u00a8\u0117\u010c\u0143\u016b\u01c1\u01bd\u0184\u01be\u0246\u0251\u025a\u0232\u029c\u02d5\u02c0\u02b7\u0311\u031a\u0317\u0301\u03c7\u03fc\u0392\u03bc\u0394"), 1.0, 0.5, 3.0, 0.1, Deobf.decrypt("\u000b")));
    public final ColorSetting color = this.addSetting(new ColorSetting(Deobf.decrypt("0C$\u0001`"), Deobf.decrypt("7I+\u000f~\u00e4\u00af\u0085\u00a5\u0114\u011b\u0143\u016b\u0191\u01ff\u01c4\u01f6\u021a\u0251\u025c\u022f\u02d9\u0294\u02cc\u02b2\u0313\u0318\u0308\u0344\u03d4\u03bc"), -38476));
    public final SliderSetting lifetime = this.addSetting(new SliderSetting(Deobf.decrypt("?E.\u000bf\u00ad\u00a1\u008f"), Deobf.decrypt(";C?N~\u00ab\u00a2\u008d\u00e9\u011a\u0149\u0155\u0174\u01d0\u01e0\u01c0\u01ff\u0202\u0202\u0215\u022a\u0290\u0286\u02cb\u02b9\u0312\u031c"), 1.5, 0.5, 4.0, 0.1, Deobf.decrypt("\u0000")));
    public final BooleanSetting rainbow = this.addSetting(new BooleanSetting(Deobf.decrypt("!M!\u0000p\u00ab\u00bb"), Deobf.decrypt("0U+\u0002w\u00e4\u00b8\u0082\u00ac\u015b\u010a\u010c\u012f\u019f\u01e1\u0194\u01ea\u0213\u0203\u025a\u0229\u029e\u029d\u0282\u02af\u0316\u031c\u035c\u0353\u03d1\u03fc\u0398\u03bb\u03d2\u0458"), false));
    public final BooleanSetting shockwave = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\ry\u00b3\u00ad\u009c\u00ac"), Deobf.decrypt("6T8\u000f|\u00a0\u00a5\u0084\u00ae\u015b\u011b\u010a\u012d\u0197\u01b3\u01db\u01f0\u025b\u0202\u0245\u023d\u028e\u029b"), true));
    public final BooleanSetting particles = this.addSetting(new BooleanSetting(Deobf.decrypt("#M:\u001a{\u00a7\u00a0\u008f\u00ba"), Deobf.decrypt("7^!\bf\u00ad\u00a2\u008d\u00e9\u011f\u011c\u0110\u0137\u01d0\u01fe\u01db\u01ea\u021e\u0202\u0215\u0233\u0297\u02d5\u02c8\u02ae\u0313\u0309"), true));
    public final SliderSetting maxCircles = this.addSetting(new SliderSetting(Deobf.decrypt(">M0"), Deobf.decrypt(">M0Na\u00ad\u00a1\u009f\u00a5\u010f\u0108\u010d\u0126\u019f\u01e6\u01c7\u01be\u021f\u0214\u0256\u023d\u0295\u0286\u0282\u02f3\u0311\u0315\u0318\u0344\u03c3\u03e1\u03d6\u03bd\u03cf\u0440\u043e\u041c\u042d\u04cf\u04be\u04f5\u04f2\u0551\u052d"), 10.0, 1.0, 30.0, 1.0));
    private final Deque<JumpCircle> circles = new ArrayDeque<JumpCircle>();
    private int spawnCounter;

    public JumpCirclesModule() {
        super(Deobf.decrypt("E\u001b\u0002\u001b\u007f\u00b4\u008f\u0083\u00bb\u0118\u0105\u0106\u0130"), Deobf.decrypt(" X)\u0003b\u00b7\u00ec\u008b\u00e9\u011c\u0105\u010c\u0134\u0199\u01fd\u01d3\u01be\u024d\u0246\u0215\u0233\u0297\u02d5\u02d6\u02b3\u031b\u0359\u031b\u0353\u03df\u03e0\u0398\u03bd\u039d\u0458\u0426\u040a\u0463\u0489\u04ae\u04e8\u04f4\u0505\u056e\u054d\u0541\u05d4"), Category.CLIENT);
    }

    public Deque<JumpCircle> circles() {
        return this.circles;
    }

    @Override
    protected void onDisable() {
        this.clear();
    }

    public void clear() {
        this.circles.clear();
    }

    public int baseRgb() {
        return (Integer)this.color.get() & 0xFFFFFF;
    }

    public void onPlayerJump(class_746 player) {
        if (!this.isEnabled()) {
            return;
        }
        double x = player.method_23317();
        double y = player.method_23318();
        double z = player.method_23321();
        float yLift = 0.01f + (float)(this.spawnCounter % 8) * 0.001f;
        ++this.spawnCounter;
        this.circles.addLast(new JumpCircle(x, y, z, player.method_36454(), yLift, System.nanoTime()));
        int max = Math.max(1, this.maxCircles.getInt());
        while (this.circles.size() > max) {
            this.circles.removeFirst();
        }
        if (((Boolean)this.particles.get()).booleanValue()) {
            this.spawnParticles(player);
        }
    }

    private void spawnParticles(class_746 player) {
        class_5819 random = player.method_59922();
        int rgb = this.baseRgb();
        for (int i = 0; i < 10; ++i) {
            double angle = random.method_43058() * Math.PI * 2.0;
            double dist = 0.15 + random.method_43058() * 0.45;
            player.method_73183().method_8406((class_2394)new class_2390(rgb, 0.9f), player.method_23317() + Math.cos(angle) * dist, player.method_23318() + 0.05, player.method_23321() + Math.sin(angle) * dist, 0.0, 0.6 + random.method_43058() * 0.4, 0.0);
        }
    }

    public static final class JumpCircle {
        public final double x;
        public final double y;
        public final double z;
        public final float yawDegrees;
        public final float yLift;
        public final long spawnNanos;

        JumpCircle(double x, double y, double z, float yawDegrees, float yLift, long spawnNanos) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yawDegrees = yawDegrees;
            this.yLift = yLift;
            this.spawnNanos = spawnNanos;
        }

        public float ageSeconds(long nowNanos) {
            return (float)(nowNanos - this.spawnNanos) / 1.0E9f;
        }
    }
}

