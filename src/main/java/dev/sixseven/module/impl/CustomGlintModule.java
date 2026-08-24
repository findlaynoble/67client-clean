/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.Colors;
import java.util.Locale;

public class CustomGlintModule
extends Module {
    public final ModeSetting style = this.addSetting(new ModeSetting(Deobf.decrypt(" X1\u0002w"), Deobf.decrypt("4@!\u0000f\u00e4\u00b8\u008f\u00b1\u010f\u011c\u0111\u0126\u01de\u01b3\u01f0\u01fb\u021d\u0210\u0240\u0230\u028d\u02d5\u02d0\u02be\u031d\u0316\u0310\u034e\u03c2\u03e6\u03d6\u03ad\u03d5\u044a\u046e\u0419\u046c\u04c7\u04be\u04eb\u04ed\u0544\u0524\u055e\u0543\u05cd\u05ea\u0588\u05ee\u061f\u062b\u065d\u0614\u0680\u06fc\u06c9\u06cc\u074d\u0777\u074f\u0759\u079b\u0787\u0786\u079b\u07bd\u084b\u0813\u084a\u0879\u08cb\u08ba\u0890\u08fd\u0938\u092c\u0913"), Deobf.decrypt("7I.\u000fg\u00a8\u00b8"), Deobf.decrypt("7I.\u000fg\u00a8\u00b8"), Deobf.decrypt("6B,\u000b`"), Deobf.decrypt("%C!\n"), Deobf.decrypt("4M$\u000fj\u00bd"), Deobf.decrypt("'C0\u0007q"), Deobf.decrypt("2A-\u001az\u00bd\u00bf\u009e"), Deobf.decrypt("0U*\u000b`"), Deobf.decrypt(" C$\u000f`"), Deobf.decrypt("#^!\u001d\u007f\u00a5\u00b8\u0083\u00aa"), Deobf.decrypt("2N1\u001da")));
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt("$D-\u001cw\u00e4\u00b8\u0082\u00ac\u015b\u010e\u010f\u012a\u019e\u01e7\u0194\u01fd\u0214\u021d\u025a\u022e\u02d9\u0296\u02cd\u02b6\u031b\u030a\u035c\u0347\u03c2\u03fa\u039b"), Deobf.decrypt(" C$\u0007v"), Deobf.decrypt(" C$\u0007v"), Deobf.decrypt("!M!\u0000p\u00ab\u00bb"), Deobf.decrypt("'D-\u0003w")));
    public final ColorSetting color = this.addSetting(new ColorSetting(Deobf.decrypt("0C$\u0001`"), Deobf.decrypt("4@!\u0000f\u00e4\u00af\u0085\u00a5\u0114\u011b"), -49508));
    public final SliderSetting strength = this.addSetting(new SliderSetting(Deobf.decrypt(" X:\u000b|\u00a3\u00b8\u0082"), Deobf.decrypt("4@!\u0000f\u00e4\u00a5\u0084\u00bd\u011e\u0107\u0110\u012a\u0184\u01ea"), 60.0, 0.0, 100.0, 5.0, Deobf.decrypt("V")));
    public final SliderSetting speed = this.addSetting(new SliderSetting(Deobf.decrypt(" \\-\u000bv"), Deobf.decrypt("!M!\u0000p\u00ab\u00bb\u00ca\u00aa\u0102\u010a\u010f\u0126\u01d0\u01e0\u01c4\u01fb\u021e\u0215"), 100.0, 10.0, 300.0, 10.0, Deobf.decrypt("V")));

    public CustomGlintModule() {
        super(Deobf.decrypt("0Y;\u001a}\u00a9\u008b\u0086\u00a0\u0115\u011d"), Deobf.decrypt("!I+\u0001~\u00ab\u00be\u0099\u00e9\u0114\u011b\u0143\u0131\u0195\u01e0\u01c0\u01e7\u0217\u0214\u0246\u027c\u028d\u029d\u02c7\u02fb\u031b\u0317\u031f\u0349\u03d1\u03fb\u0382\u03b4\u03d8\u0441\u043a\u044f\u046a\u04c5\u04be\u04e9\u04f5"), Category.MISC);
        this.mode.visibleWhen(() -> this.style.is(Deobf.decrypt("7I.\u000fg\u00a8\u00b8")));
        this.color.visibleWhen(() -> this.style.is(Deobf.decrypt("7I.\u000fg\u00a8\u00b8")) && this.mode.is(Deobf.decrypt(" C$\u0007v")));
        this.speed.visibleWhen(() -> this.style.is(Deobf.decrypt("7I.\u000fg\u00a8\u00b8")) && this.mode.is(Deobf.decrypt("!M!\u0000p\u00ab\u00bb")));
    }

    public boolean isActive() {
        return this.isEnabled();
    }

    public boolean usesTexture() {
        return !this.style.is(Deobf.decrypt("7I.\u000fg\u00a8\u00b8"));
    }

    public String textureName() {
        return ((String)this.style.get()).toLowerCase(Locale.ROOT);
    }

    public float strengthUnit() {
        return this.strength.getFloat() / 100.0f;
    }

    /*
     * Exception decompiling
     */
    public int glintColor() {
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

    private int rainbow() {
        double seconds = (double)(System.nanoTime() % 1000000000000L) / 1.0E9;
        double hue = seconds * 36.0 * (double)(this.speed.getFloat() / 100.0f) % 360.0;
        return Colors.hsvToRgb((float)hue, 0.85f, 1.0f);
    }
}

