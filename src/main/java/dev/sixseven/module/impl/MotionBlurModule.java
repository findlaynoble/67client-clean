/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.render.MotionBlurRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;

public class MotionBlurModule
extends Module {
    public final SliderSetting strength = this.addSetting(new SliderSetting(Deobf.decrypt(" X:\u000b|\u00a3\u00b8\u0082"), Deobf.decrypt("1@=\u001c2\u00b7\u00b8\u0098\u00ac\u0115\u010e\u0117\u012b"), 30.0, 5.0, 100.0, 5.0, Deobf.decrypt("V")).withLabel(v -> {
        int pct = (int)Math.round(v);
        String tier = pct <= 20 ? Deobf.decrypt(" Y*\u001a~\u00a1") : (pct <= 45 ? Deobf.decrypt("1M$\u000f|\u00a7\u00a9\u008e") : (pct <= 70 ? Deobf.decrypt(" A'\u0001f\u00ac") : (pct <= 90 ? Deobf.decrypt(";I)\u0018k") : Deobf.decrypt("0E&\u000b\u007f\u00a5\u00b8\u0083\u00aa"))));
        return pct + "% \u00b7 " + tier;
    }));
    public final BooleanSetting pinkTrails = this.addSetting(new BooleanSetting(Deobf.decrypt("#E&\u00052\u0090\u00be\u008b\u00a0\u0117\u011a"), Deobf.decrypt("'E&\u001a2\u00b0\u00a4\u008f\u00e9\u010f\u011b\u0102\u012a\u019c\u01e0\u0194\u01e9\u0212\u0205\u025d\u027c\u0280\u029a\u02d7\u02a9\u035e\u030d\u0314\u0344\u03dd\u03f0\u03d6\u03b8\u03de\u044c\u042b\u0401\u0479\u0489\u04ff\u04b1\u04b6\u0505\u0568\u0557\u0543\u05cf\u05af"), true));
    public final SliderSetting tint = this.addSetting(new SliderSetting(Deobf.decrypt("'E&\u001a"), Deobf.decrypt(";C?Na\u00b0\u00be\u0085\u00a7\u011c\u0105\u011a\u0163\u0184\u01e1\u01d5\u01f7\u0217\u0202\u0215\u0228\u0298\u029e\u02c7\u02fb\u030a\u0311\u0319\u0301\u03c4\u03fd\u0393\u03b4\u03d8\u040f\u042d\u0400\u0461\u04c6\u04a5"), 30.0, 0.0, 100.0, 5.0, Deobf.decrypt("V")));
    public final BooleanSetting fpsCompensated = this.addSetting(new BooleanSetting(Deobf.decrypt("5|\u001bNQ\u00ab\u00a1\u009a\u00ac\u0115\u011a\u0102\u0137\u0195\u01f7"), Deobf.decrypt("8I-\u001e2\u00b0\u00a4\u008f\u00e9\u0119\u0105\u0116\u0131\u01d0\u01f0\u01db\u01f0\u0208\u0218\u0246\u0228\u029c\u029b\u02d6\u02fb\u031f\u031a\u030e\u034e\u03c3\u03e6\u03d6\u03bf\u03cf\u044e\u0423\u040a\u047f\u04c8\u04a3\u04e2\u04f2"), true));

    public MotionBlurModule() {
        super(Deobf.decrypt(">C<\u0007}\u00aa\u008e\u0086\u00bc\u0109"), Deobf.decrypt("0E&\u000b\u007f\u00a5\u00b8\u0083\u00aa\u015b\u0104\u010c\u0137\u0199\u01fc\u01da\u01be\u0219\u021d\u0240\u022e"), Category.VISUALS);
        this.tint.visibleWhen(this.pinkTrails::get);
    }

    @Override
    protected void onDisable() {
        MotionBlurRenderer.reset();
    }

    public double retention() {
        return Math.clamp((double)this.strength.getFloat() / 100.0, 0.05, 0.95);
    }

    public float tintAmount() {
        return (Boolean)this.pinkTrails.get() != false ? (float)Math.clamp((double)this.tint.getFloat() / 100.0, 0.0, 1.0) : 0.0f;
    }

    public int accentColor() {
        return SixSevenClient.themes().current().accent();
    }
}

