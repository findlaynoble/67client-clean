/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class CustomFovModule
extends Module {
    private static final double EASE_SPEED = 12.0;
    private static final double SPRINT_SPEED = 0.2825;
    public final SliderSetting fov = this.addSetting(new SliderSetting(Deobf.decrypt("5c\u001e"), Deobf.decrypt("'M:\tw\u00b0\u00ec\u008c\u00a0\u011e\u0105\u0107\u0163\u019f\u01f5\u0194\u01e8\u0212\u0214\u0242"), 95.0, 30.0, 140.0, 1.0, Deobf.decrypt("\u00c3")).withLabel(v -> (int)v + "\u00b0 \u00b7 " + CustomFovModule.tag((int)v)));
    public final BooleanSetting smooth = this.addSetting(new BooleanSetting(Deobf.decrypt(" A'\u0001f\u00ac"), Deobf.decrypt("6M;\u000b2\u0082\u0083\u00bc\u00e9\u0118\u0101\u0102\u012d\u0197\u01f6\u01c7\u01be\u0212\u021f\u0215\u023d\u0297\u0291\u0282\u02b4\u030b\u030d"), true));
    public final BooleanSetting noSprintZoom = this.addSetting(new BooleanSetting(Deobf.decrypt("=Ch=b\u00b6\u00a5\u0084\u00bd\u015b\u0133\u010c\u012c\u019d"), Deobf.decrypt("0M&\rw\u00a8\u00ec\u009e\u00a1\u011e\u0149\u0115\u0122\u019e\u01fa\u01d8\u01f2\u021a\u0251\u0246\u022c\u028b\u029c\u02cc\u02af\u035e\u0356\u035c\u0352\u03c0\u03f0\u0393\u03bd\u039d\u0469\u0401\u0439\u042d\u04d9\u04a2\u04e9\u04e2\u054d"), true));
    public final BooleanSetting speedFov = this.addSetting(new BooleanSetting(Deobf.decrypt(" \\-\u000bv\u00e4\u008a\u00a5\u009f"), Deobf.decrypt("$E,\u000b|\u00e4\u00b8\u0082\u00ac\u015b\u011f\u010a\u0126\u0187\u01b3\u01c3\u01f7\u020f\u0219\u0215\u0225\u0296\u0280\u02d0\u02fb\u0313\u0316\u030a\u0344\u03dd\u03f0\u0398\u03ad\u039d\u045c\u043e\u040a\u0468\u04cd"), false));
    public final SliderSetting speedStrength = this.addSetting(new SliderSetting(Deobf.decrypt(" \\-\u000bv\u00e4\u009f\u009e\u00bb\u011e\u0107\u0104\u0137\u0198"), Deobf.decrypt("6T<\u001cs\u00e4\u00a8\u008f\u00ae\u0109\u010c\u0106\u0130\u01d0\u01f2\u01c0\u01be\u021d\u0204\u0259\u0230\u02d9\u0286\u02d2\u02a9\u0317\u0317\u0308"), 12.0, 0.0, 30.0, 1.0, Deobf.decrypt("\u00c3")));
    private double current = -1.0;
    private long lastNanos = 0L;

    public CustomFovModule() {
        super(Deobf.decrypt("0Y;\u001a}\u00a9\u008a\u00a5\u009f"), Deobf.decrypt("<Z-\u001c`\u00ad\u00a8\u008f\u00ba\u015b\u011d\u010b\u0126\u01d0\u01f5\u01dd\u01fb\u0217\u0215\u0215\u0233\u029f\u02d5\u02d4\u02b2\u031b\u030e"), Category.MISC);
        this.speedStrength.visibleWhen(this.speedFov::get);
    }

    public float fovMultiplier(float vanillaSprintMultiplier) {
        class_310 mc = class_310.method_1551();
        int optionsFov = (Integer)mc.field_1690.method_41808().method_41753();
        if (optionsFov <= 0) {
            return vanillaSprintMultiplier;
        }
        double target = this.isEnabled() ? (Double)this.fov.get() + this.speedBonus(mc) : (double)optionsFov;
        long now = System.nanoTime();
        double dt = this.lastNanos == 0L ? 0.0 : (double)(now - this.lastNanos) / 1.0E9;
        this.lastNanos = now;
        if (this.current < 0.0) {
            this.current = optionsFov;
        }
        if (!((Boolean)this.smooth.get()).booleanValue()) {
            this.current = target;
        } else {
            double t = 1.0 - Math.exp(-12.0 * Math.max(0.0, dt));
            this.current += (target - this.current) * t;
            if (Math.abs(this.current - target) < 0.05) {
                this.current = target;
            }
        }
        if (!this.isEnabled() && this.current == (double)optionsFov) {
            return vanillaSprintMultiplier;
        }
        float sprint = this.isEnabled() && (Boolean)this.noSprintZoom.get() != false ? 1.0f : vanillaSprintMultiplier;
        return (float)(this.current / (double)optionsFov) * sprint;
    }

    public double currentFov() {
        return this.current;
    }

    private double speedBonus(class_310 mc) {
        if (!((Boolean)this.speedFov.get()).booleanValue()) {
            return 0.0;
        }
        class_746 p = mc.field_1724;
        if (p == null) {
            return 0.0;
        }
        class_243 v = p.method_18798();
        double horizontal = Math.sqrt(v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350);
        double t = Math.min(1.0, horizontal / 0.2825);
        return (double)this.speedStrength.getFloat() * t;
    }

    private static String tag(int v) {
        if (v <= 45) {
            return Deobf.decrypt("'Y&\u0000w\u00a8");
        }
        if (v <= 65) {
            return Deobf.decrypt("5C+\u001ba\u00a1\u00a8");
        }
        if (v <= 80) {
            return Deobf.decrypt("=C:\u0003s\u00a8");
        }
        if (v <= 100) {
            return Deobf.decrypt("$E,\u000b");
        }
        if (v <= 118) {
            return Deobf.decrypt("&@<\u001cs");
        }
        return Deobf.decrypt("5E;\u0006w\u00bd\u00a9");
    }
}

