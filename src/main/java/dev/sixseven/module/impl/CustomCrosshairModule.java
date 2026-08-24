/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.Colors;

public class CustomCrosshairModule
extends Module {
    private static final int HOT_PINK = -49508;
    public final ModeSetting style = this.addSetting(new ModeSetting(Deobf.decrypt(" X1\u0002w"), Deobf.decrypt("0^'\u001da\u00ac\u00ad\u0083\u00bb\u015b\u011a\u010b\u0122\u0180\u01f6"), Deobf.decrypt("0^'\u001da"), Deobf.decrypt("7C<"), Deobf.decrypt("0^'\u001da"), Deobf.decrypt("0E:\r~\u00a1"), Deobf.decrypt("'\u0001\u001b\u0006s\u00b4\u00a9"), Deobf.decrypt("1^)\ry\u00a1\u00b8\u0099"), Deobf.decrypt("0D-\u0018`\u00ab\u00a2"), Deobf.decrypt("E\u001b")));
    public final SliderSetting size = this.addSetting(new SliderSetting(Deobf.decrypt(" E2\u000b"), Deobf.decrypt("<Z-\u001cs\u00a8\u00a0\u00ca\u00aa\u0109\u0106\u0110\u0130\u0198\u01f2\u01dd\u01ec\u025b\u0202\u025c\u0226\u029c"), 7.0, 2.0, 24.0, 1.0, Deobf.decrypt("\u0003T")));
    public final SliderSetting thickness = this.addSetting(new SliderSetting(Deobf.decrypt("'D!\ry\u00aa\u00a9\u0099\u00ba"), Deobf.decrypt("?E&\u000b2\u00eb\u00ec\u008e\u00a6\u010f\u0149\u0117\u012b\u0199\u01f0\u01df\u01f0\u021e\u0202\u0246"), 2.0, 1.0, 6.0, 0.5, Deobf.decrypt("\u0003T")));
    public final SliderSetting gap = this.addSetting(new SliderSetting(Deobf.decrypt("4M8"), Deobf.decrypt("0I&\u001aw\u00b6\u00ec\u008d\u00a8\u010b\u0149\u0105\u012c\u0182\u01b3\u01d8\u01f7\u0215\u0214\u0215\u022f\u028d\u028c\u02ce\u02be\u030d"), 3.0, 0.0, 12.0, 1.0, Deobf.decrypt("\u0003T")));
    public final ColorSetting color = this.addSetting(new ColorSetting(Deobf.decrypt("0C$\u0001`"), Deobf.decrypt("0^'\u001da\u00ac\u00ad\u0083\u00bb\u015b\u010a\u010c\u012f\u019f\u01e1"), -49508));
    public final BooleanSetting rainbow = this.addSetting(new BooleanSetting(Deobf.decrypt("!M!\u0000p\u00ab\u00bb"), Deobf.decrypt("0U+\u0002w\u00e4\u00b8\u0082\u00bb\u0114\u011c\u0104\u012b\u01d0\u01e7\u01dc\u01fb\u025b\u0203\u0254\u0235\u0297\u0297\u02cd\u02ac"), false));
    public final BooleanSetting centerDot = this.addSetting(new BooleanSetting(Deobf.decrypt("0I&\u001aw\u00b6\u00ec\u00ae\u00a6\u010f"), Deobf.decrypt("2H,Ns\u00e4\u00aa\u0083\u00a5\u0117\u010c\u0107\u0163\u0194\u01fc\u01c0\u01be\u021a\u0205\u0215\u0228\u0291\u0290\u0282\u02ad\u031b\u030b\u0305\u0301\u03d3\u03f0\u0398\u03ad\u03d8\u045d"), false));
    public final BooleanSetting outline = this.addSetting(new BooleanSetting(Deobf.decrypt("<Y<\u0002{\u00aa\u00a9"), Deobf.decrypt("7M:\u00052\u00a6\u00a3\u0098\u00ad\u011e\u011b\u0143\u0125\u019f\u01e1\u0194\u01fd\u0214\u021f\u0241\u022e\u0298\u0286\u02d6\u02fb\u0311\u0317\u035c\u0340\u03de\u03ec\u03d6\u03bb\u03dc\u044c\u0425\u0408\u047f\u04c6\u04a2\u04e9\u04e5"), true));
    public final BooleanSetting glow = this.addSetting(new BooleanSetting(Deobf.decrypt("4@'\u0019"), Deobf.decrypt(" C.\u001a2\u00a3\u00a0\u0085\u00be\u015b\u010b\u0106\u012b\u0199\u01fd\u01d0\u01be\u020f\u0219\u0250\u027c\u029a\u0287\u02cd\u02a8\u030d\u0311\u031d\u0348\u03c2"), true));
    public final BooleanSetting hideVanilla = this.addSetting(new BooleanSetting(Deobf.decrypt(";E,\u000b2\u0092\u00ad\u0084\u00a0\u0117\u0105\u0102"), Deobf.decrypt(";E,\u000b2\u0089\u00a5\u0084\u00ac\u0118\u011b\u0102\u0125\u0184\u01b4\u01c7\u01be\u021f\u0214\u0253\u023d\u028c\u0299\u02d6\u02fb\u031d\u030b\u0313\u0352\u03c3\u03fd\u0397\u03b0\u03cf"), true));

    public CustomCrosshairModule() {
        super(Deobf.decrypt("0Y;\u001a}\u00a9\u008f\u0098\u00a6\u0108\u011a\u010b\u0122\u0199\u01e1"), Deobf.decrypt("7^)\u0019a\u00e4\u00ad\u00ca\u00aa\u010e\u011a\u0117\u012c\u019d\u01b3\u01d7\u01ec\u0214\u0202\u0246\u0234\u0298\u029c\u02d0"), Category.MISC);
    }

    public boolean shouldHideVanilla() {
        return this.isEnabled() && (Boolean)this.hideVanilla.get() != false;
    }

    private int resolveColor() {
        if (((Boolean)this.rainbow.get()).booleanValue()) {
            float hue = (float)(System.nanoTime() % 3000000000L) / 3.0E9f;
            return 0xFF000000 | Colors.hsvToRgb(hue, 0.85f, 1.0f) & 0xFFFFFF;
        }
        return (Integer)this.color.get();
    }

    /*
     * Exception decompiling
     */
    public void render(NVGRenderer vg, float cx, float cy) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void dot(NVGRenderer vg, float cx, float cy, float r, int col, int line) {
        if (line != 0) {
            vg.circle(cx, cy, r + 1.0f, line);
        }
        vg.circle(cx, cy, r, col);
    }

    private void ring(NVGRenderer vg, float cx, float cy, float r, float t, int col, int line) {
        if (line != 0) {
            vg.circleOutline(cx, cy, r, t + 2.0f, line);
        }
        vg.circleOutline(cx, cy, r, t, col);
    }

    private void cross(NVGRenderer vg, float cx, float cy, float len, float t, float g, int col, int line, boolean top, boolean bottom) {
        if (line != 0) {
            this.segments(vg, cx, cy, len, t + 2.0f, g, line, top, bottom);
        }
        this.segments(vg, cx, cy, len, t, g, col, top, bottom);
    }

    private void segments(NVGRenderer vg, float cx, float cy, float len, float t, float g, int argb, boolean top, boolean bottom) {
        vg.line(cx + g, cy, cx + g + len, cy, t, argb);
        vg.line(cx - g, cy, cx - g - len, cy, t, argb);
        if (top) {
            vg.line(cx, cy - g, cx, cy - g - len, t, argb);
        }
        if (bottom) {
            vg.line(cx, cy + g, cx, cy + g + len, t, argb);
        }
    }

    private void brackets(NVGRenderer vg, float cx, float cy, float len, float t, float g, int col, int line) {
        float arm = Math.max(2.0f, len * 0.5f);
        float d = len + g * 0.4f;
        if (line != 0) {
            this.drawBrackets(vg, cx, cy, d, arm, t + 2.0f, line);
        }
        this.drawBrackets(vg, cx, cy, d, arm, t, col);
    }

    private void drawBrackets(NVGRenderer vg, float cx, float cy, float d, float arm, float t, int argb) {
        vg.line(cx - d, cy - d, cx - d + arm, cy - d, t, argb);
        vg.line(cx - d, cy - d, cx - d, cy - d + arm, t, argb);
        vg.line(cx + d, cy - d, cx + d - arm, cy - d, t, argb);
        vg.line(cx + d, cy - d, cx + d, cy - d + arm, t, argb);
        vg.line(cx - d, cy + d, cx - d + arm, cy + d, t, argb);
        vg.line(cx - d, cy + d, cx - d, cy + d - arm, t, argb);
        vg.line(cx + d, cy + d, cx + d - arm, cy + d, t, argb);
        vg.line(cx + d, cy + d, cx + d, cy + d - arm, t, argb);
    }

    private void logo(NVGRenderer vg, float cx, float cy, float s, int col) {
        float font = s * 2.4f;
        float w = vg.textWidth(Deobf.decrypt("E\u001b"), font);
        float tx = cx - w / 2.0f;
        vg.textGlow(Deobf.decrypt("E\u001b"), tx, cy, font, Colors.withAlpha(-49508, 0.6f));
        vg.text(Deobf.decrypt("E\u001b"), tx, cy, font, col);
    }
}

