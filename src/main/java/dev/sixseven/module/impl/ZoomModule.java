/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;

public class ZoomModule
extends Module {
    private static final double EASE_SPEED = 14.0;
    public final SliderSetting factor = this.addSetting(new SliderSetting(Deobf.decrypt("5M+\u001a}\u00b6"), Deobf.decrypt(")C'\u00032\u00a2\u00ad\u0089\u00bd\u0114\u011b"), 4.0, 2.0, 10.0, 0.5, Deobf.decrypt("\u000b")));
    public final BooleanSetting smooth = this.addSetting(new BooleanSetting(Deobf.decrypt(" A'\u0001f\u00ac"), Deobf.decrypt(" A'\u0001f\u00ac\u00ec\u0090\u00a6\u0114\u0104\u0143\u012a\u019e\u01bc\u01db\u01eb\u020f"), true));
    private double current = 1.0;
    private long lastNanos = 0L;

    public ZoomModule() {
        super(Deobf.decrypt(")C'\u0003"), Deobf.decrypt("<\\<\u0007q\u00a5\u00a0\u00ca\u00b3\u0114\u0106\u010e\u0163\u019f\u01fd\u0194\u01ff\u025b\u021a\u0250\u0225"), Category.MISC);
    }

    public double currentFactor() {
        double target;
        long now = System.nanoTime();
        double dt = this.lastNanos == 0L ? 0.0 : (double)(now - this.lastNanos) / 1.0E9;
        this.lastNanos = now;
        double d = target = this.isEnabled() ? (double)this.factor.getFloat() : 1.0;
        if (!((Boolean)this.smooth.get()).booleanValue()) {
            this.current = target;
            return this.current;
        }
        double t = 1.0 - Math.exp(-14.0 * Math.max(0.0, dt));
        this.current += (target - this.current) * t;
        if (Math.abs(this.current - target) < 0.001) {
            this.current = target;
        }
        return this.current;
    }
}

