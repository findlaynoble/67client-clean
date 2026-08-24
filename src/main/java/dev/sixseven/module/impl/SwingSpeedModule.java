/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.SliderSetting;

public class SwingSpeedModule
extends Module {
    public final SliderSetting speed = this.addSetting(new SliderSetting(Deobf.decrypt(" \\-\u000bv"), Deobf.decrypt(" [!\u0000u\u00e4\u00bf\u009a\u00ac\u011e\u010d\u0143\u012e\u0185\u01ff\u01c0\u01f7\u020b\u021d\u025c\u0239\u028b\u02d5\u028a\u02e7\u034f\u0359\u0341\u0301\u03c3\u03f9\u0399\u03ae\u03d8\u045d\u0461\u041c\u0460\u04c6\u04b8\u04f3\u04e9\u0540\u0576\u0511"), 0.3, 0.1, 3.0, 0.1, Deobf.decrypt("\u000b")));

    public SwingSpeedModule() {
        super(Deobf.decrypt(" [!\u0000u\u0097\u00bc\u008f\u00ac\u011f"), Deobf.decrypt("2H\"\u001ba\u00b0\u00bf\u00ca\u00a1\u011a\u0107\u0107\u0163\u0183\u01e4\u01dd\u01f0\u021c\u0251\u0254\u0232\u0290\u0298\u02c3\u02af\u0317\u0316\u0312\u0301\u03c3\u03e5\u0393\u03bc\u03d9"), Category.CLIENT);
    }

    public float multiplier() {
        return this.speed.getFloat();
    }
}

