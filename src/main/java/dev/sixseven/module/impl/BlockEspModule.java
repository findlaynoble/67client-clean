/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.render.BlockEspRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BlockListSetting;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;

public class BlockEspModule
extends Module {
    public final BlockListSetting targets = this.addSetting(new BlockListSetting(Deobf.decrypt("'M:\tw\u00b0\u00ec\u00a8\u00a5\u0114\u010a\u0108\u0130"), Deobf.decrypt("#E+\u00052\u00b3\u00a4\u0083\u00aa\u0113\u0149\u0101\u012f\u019f\u01f0\u01df\u01ed\u025b\u0205\u025a\u027c\u0291\u029c\u02c5\u02b3\u0312\u0310\u031b\u0349\u03c4")));
    public final ModeSetting shapeMode = this.addSetting(new ModeSetting(Deobf.decrypt(" D)\u001ew\u00e4\u0081\u0085\u00ad\u011e"), Deobf.decrypt(";C?Nz\u00ad\u00ab\u0082\u00a5\u0112\u010e\u010b\u0137\u0183\u01b3\u01d5\u01ec\u021e\u0251\u0251\u022e\u0298\u0282\u02cc"), Deobf.decrypt("1C<\u0006"), Deobf.decrypt("1C<\u0006"), Deobf.decrypt("?E&\u000ba"), Deobf.decrypt(" E,\u000ba")));
    public final ColorSetting lineColor = this.addSetting(new ColorSetting(Deobf.decrypt("7I.\u000fg\u00a8\u00b8\u00ca\u0086\u010e\u011d\u010f\u012a\u019e\u01f6\u0194\u01dd\u0214\u021d\u025a\u022e"), Deobf.decrypt("0C$\u0001`\u00e4\u00b9\u0099\u00ac\u011f\u0149\u0105\u012c\u0182\u01b3\u01da\u01fb\u020c\u021d\u024c\u027c\u0298\u0291\u02c6\u02be\u031a\u0359\u031e\u034d\u03df\u03f6\u039d\u03aa"), -16711736));
    public final ColorSetting sideColor = this.addSetting(new ColorSetting(Deobf.decrypt("5E$\u00022\u008b\u00ba\u008f\u00bb\u0117\u0108\u011a"), Deobf.decrypt("7I.\u000fg\u00a8\u00b8\u00ca\u00af\u0112\u0105\u010f\u0163\u0184\u01fa\u01da\u01ea"), 419495880));
    public final BooleanSetting tracers = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6\u00bf"), Deobf.decrypt("7^)\u00192\u00a8\u00a5\u0084\u00ac\u0108\u0149\u0105\u0131\u019f\u01fe\u0194\u01ea\u0213\u0214\u0215\u023f\u028b\u029a\u02d1\u02a8\u0316\u0318\u0315\u0353\u0390\u03e1\u0399\u03f9\u03d8\u044e\u042d\u0407\u042d\u04cb\u04bb\u04e8\u04e2\u054e"), false));
    public final BooleanSetting tracer = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6"), Deobf.decrypt(" I+\u0001|\u00a0\u00ad\u0098\u00b0\u015b\u011d\u0111\u0122\u0193\u01f6\u01c6\u01be\u021e\u021f\u0254\u023e\u0295\u0290"), true));
    public final ColorSetting tracerColor = this.addSetting(new ColorSetting(Deobf.decrypt("7I.\u000fg\u00a8\u00b8\u00ca\u009d\u0109\u0108\u0100\u0126\u0182\u01b3\u01e0\u01f7\u0215\u0205"), Deobf.decrypt("'^)\rw\u00b6\u00ec\u0086\u00a0\u0115\u010c\u0143\u0120\u019f\u01ff\u01db\u01ec\u025b\u025e\u0215\u023d\u0295\u0285\u02ca\u02ba"), 2097217480));
    public final SliderSetting highlightAlpha = this.addSetting(new SliderSetting(Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u0128\u010f\u0133\u0198\u01f2"), Deobf.decrypt("1C0N}\u00b4\u00ad\u0089\u00a0\u010f\u0110"), 255.0, 0.0, 255.0, 5.0));
    public final SliderSetting rangeExtraChunks = this.addSetting(new SliderSetting(Deobf.decrypt("!M&\tw\u00e4\u0089\u0092\u00bd\u0109\u0108\u0143\u0100\u0198\u01e6\u01da\u01f5\u0208"), Deobf.decrypt("6T<\u001cs\u00e4\u00bf\u0089\u00a8\u0115\u0149\u0111\u0122\u0194\u01fa\u01c1\u01ed\u025b\u0213\u0250\u0225\u0296\u029b\u02c6\u02fb\u030c\u031c\u0312\u0345\u03d5\u03e7\u03d6\u03bd\u03d4\u045c\u043a\u040e\u0463\u04ca\u04b2"), 1.0, 0.0, 4.0, 1.0));

    public BlockEspModule() {
        super(Deobf.decrypt("1@'\ry\u0081\u009f\u00ba"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u0108\u0149\u0100\u012b\u019f\u01e0\u01d1\u01f0\u025b\u0213\u0259\u0233\u029a\u029e\u02d1\u02fb\u030a\u0311\u030e\u034e\u03c5\u03f2\u039e\u03f9\u03ca\u044e\u0422\u0403\u047e"), Category.RENDER);
        this.targets.seedDefaults();
        this.tracer.visibleWhen(this.tracers::get);
        this.tracerColor.visibleWhen(this.tracers::get);
    }

    @Override
    public void onTick() {
        BlockEspRenderer.scan(this);
    }

    @Override
    protected void onDisable() {
        BlockEspRenderer.clear();
    }
}

