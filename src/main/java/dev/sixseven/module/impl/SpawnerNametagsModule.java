/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2246
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.render.BlockScanCache;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_2246;

public class SpawnerNametagsModule
extends Module {
    public final BooleanSetting nametag = this.addSetting(new BooleanSetting(Deobf.decrypt("=M%\u000bf\u00a5\u00ab"), Deobf.decrypt("5@'\u000ff\u00ad\u00a2\u008d\u00e9\u010f\u0110\u0113\u0126\u01d0\u01b8\u0194\u01fa\u0212\u0202\u0241\u023d\u0297\u0296\u02c7\u02fb\u0312\u0318\u031e\u0344\u03dc"), true));
    public final BooleanSetting box = this.addSetting(new BooleanSetting(Deobf.decrypt("1C0"), Deobf.decrypt("'D:\u0001g\u00a3\u00a4\u00c7\u00be\u011a\u0105\u010f\u0163\u0192\u01fc\u01cc\u01be\u0214\u021f\u0215\u0228\u0291\u0290\u0282\u02a8\u030e\u0318\u030b\u034f\u03d5\u03e7"), true));
    public final BooleanSetting rangeRing = this.addSetting(new BooleanSetting(Deobf.decrypt("!M&\tw\u00e4\u009e\u0083\u00a7\u011c"), Deobf.decrypt("7^)\u00192\u00b0\u00a4\u008f\u00e9\u014a\u015f\u014e\u0121\u019c\u01fc\u01d7\u01f5\u025b\u0210\u0256\u0228\u0290\u0283\u02c3\u02af\u0317\u0316\u0312\u0301\u03c2\u03fc\u0398\u03be"), true));
    public final BooleanSetting distance = this.addSetting(new BooleanSetting(Deobf.decrypt("7E;\u001as\u00aa\u00af\u008f"), Deobf.decrypt("2\\8\u000b|\u00a0\u00ec\u009e\u00a1\u011e\u0149\u0107\u012a\u0183\u01e7\u01d5\u01f0\u0218\u0214\u0215\u0235\u0297\u02d5\u02c0\u02b7\u0311\u031a\u0317\u0352"), true));
    public final SliderSetting opacity = this.addSetting(new SliderSetting(Deobf.decrypt("<\\)\r{\u00b0\u00b5"), Deobf.decrypt("=M%\u000bf\u00a5\u00ab\u00ca\u00bd\u0109\u0108\u010d\u0130\u0180\u01f2\u01c6\u01fb\u0215\u0212\u024c"), 100.0, 10.0, 100.0, 5.0, Deobf.decrypt("V")));
    public final BooleanSetting tracers = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6\u00bf"), Deobf.decrypt("7^)\u00192\u00a8\u00a5\u0084\u00ac\u0108\u0149\u0105\u0131\u019f\u01fe\u0194\u01ea\u0213\u0214\u0215\u023f\u028b\u029a\u02d1\u02a8\u0316\u0318\u0315\u0353\u0390\u03e1\u0399\u03f9\u03d8\u044e\u042d\u0407\u042d\u04da\u04a7\u04e6\u04f6\u054b\u0561\u054a"), false));
    public final BlockScanCache scan = new BlockScanCache(state -> state.method_27852(class_2246.field_10260) || state.method_27852(class_2246.field_47336), 12, 8, 400, 96.0);

    public SpawnerNametagsModule() {
        super(Deobf.decrypt(" \\)\u0019|\u00a1\u00be\u00a4\u00a8\u0116\u010c\u0117\u0122\u0197\u01e0"), Deobf.decrypt(" D'\u0019a\u00e4\u00bf\u009a\u00a8\u010c\u0107\u0106\u0131\u01d0\u01e7\u01cd\u01ee\u021e\u0251\u021e\u027c\u0298\u0296\u02d6\u02b2\u0308\u0318\u0308\u0348\u03df\u03fb\u03d6\u03ab\u03dc\u0441\u0429\u040a"), Category.RENDER);
    }

    @Override
    public void onTick() {
        this.scan.scan();
    }

    @Override
    protected void onDisable() {
        this.scan.clear();
    }
}

