/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 */
package dev.sixseven.module.impl;

import dev.sixseven.mixin.MinecraftAccessor;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_310;

public class AutoClickerModule
extends Module {
    public final BooleanSetting inScreens = this.addSetting(new BooleanSetting(Deobf.decrypt("$D!\u0002w\u00e4\u0085\u0084\u00e9\u0128\u010a\u0111\u0126\u0195\u01fd\u01c7"), Deobf.decrypt("$D-\u001az\u00a1\u00be\u00ca\u00bd\u0114\u0149\u0100\u012f\u0199\u01f0\u01df\u01be\u020c\u0219\u025c\u0230\u029c\u02d5\u02c3\u02fb\u030d\u031a\u030e\u0344\u03d5\u03fb\u03d6\u03b0\u03ce\u040f\u0421\u041f\u0468\u04c7\u04f9"), true));
    public final ModeSetting leftMode = this.addSetting(new ModeSetting(Deobf.decrypt("?I.\u001a2\u0087\u00a0\u0083\u00aa\u0110\u0149\u012e\u012c\u0194\u01f6"), Deobf.decrypt("'D-N\u007f\u00a1\u00b8\u0082\u00a6\u011f\u0149\u010c\u0125\u01d0\u01f0\u01d8\u01f7\u0218\u021a\u025c\u0232\u029e\u02d5\u02c4\u02b4\u030c\u0359\u0310\u0344\u03d6\u03e1\u03d6\u03ba\u03d1\u0446\u042d\u0404\u047e\u0487"), Deobf.decrypt("#^-\u001da"), Deobf.decrypt("7E;\u000fp\u00a8\u00a9\u008e"), Deobf.decrypt(";C$\n"), Deobf.decrypt("#^-\u001da")));
    public final SliderSetting leftDelay = this.addSetting(new SliderSetting(Deobf.decrypt("?I.\u001a2\u0087\u00a0\u0083\u00aa\u0110\u0149\u0127\u0126\u019c\u01f2\u01cd"), Deobf.decrypt("7I$\u000fk\u00e4\u00ae\u008f\u00bd\u010c\u010c\u0106\u012d\u01d0\u01ff\u01d1\u01f8\u020f\u0251\u0256\u0230\u0290\u0296\u02c9\u02a8\u035e\u0310\u0312\u0301\u03c4\u03fc\u0395\u03b2\u03ce\u0401"), 2.0, 0.0, 60.0, 1.0, Deobf.decrypt("SX!\ry\u00b7")));
    public final ModeSetting rightMode = this.addSetting(new ModeSetting(Deobf.decrypt("!E/\u0006f\u00e4\u008f\u0086\u00a0\u0118\u0102\u0143\u010e\u019f\u01f7\u01d1"), Deobf.decrypt("'D-N\u007f\u00a1\u00b8\u0082\u00a6\u011f\u0149\u010c\u0125\u01d0\u01f0\u01d8\u01f7\u0218\u021a\u025c\u0232\u029e\u02d5\u02c4\u02b4\u030c\u0359\u030e\u0348\u03d7\u03fd\u0382\u03f9\u03de\u0443\u0427\u040c\u0466\u04da\u04f9"), Deobf.decrypt("#^-\u001da"), Deobf.decrypt("7E;\u000fp\u00a8\u00a9\u008e"), Deobf.decrypt(";C$\n"), Deobf.decrypt("#^-\u001da")));
    public final SliderSetting rightDelay = this.addSetting(new SliderSetting(Deobf.decrypt("!E/\u0006f\u00e4\u008f\u0086\u00a0\u0118\u0102\u0143\u0107\u0195\u01ff\u01d5\u01e7"), Deobf.decrypt("7I$\u000fk\u00e4\u00ae\u008f\u00bd\u010c\u010c\u0106\u012d\u01d0\u01e1\u01dd\u01f9\u0213\u0205\u0215\u023f\u0295\u029c\u02c1\u02b0\u030d\u0359\u0315\u034f\u0390\u03e1\u039f\u03ba\u03d6\u045c\u0460"), 2.0, 0.0, 60.0, 1.0, Deobf.decrypt("SX!\ry\u00b7")));
    private int leftTimer;
    private int rightTimer;

    public AutoClickerModule() {
        super(Deobf.decrypt("2Y<\u0001Q\u00a8\u00a5\u0089\u00a2\u011e\u011b"), Deobf.decrypt("2Y<\u0001\u007f\u00a5\u00b8\u0083\u00aa\u011a\u0105\u010f\u013a\u01d0\u01f0\u01d8\u01f7\u0218\u021a\u0246\u0272"), Category.MISC);
        this.leftDelay.visibleWhen(() -> this.leftMode.is(Deobf.decrypt("#^-\u001da")));
        this.rightDelay.visibleWhen(() -> this.rightMode.is(Deobf.decrypt("#^-\u001da")));
    }

    @Override
    protected void onEnable() {
        this.leftTimer = 0;
        this.rightTimer = 0;
        this.release();
    }

    @Override
    protected void onDisable() {
        this.release();
    }

    private void release() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1690 == null) {
            return;
        }
        mc.field_1690.field_1886.method_23481(false);
        mc.field_1690.field_1904.method_23481(false);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onTick() {
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

    private void leftClick(class_310 mc) {
        if (mc.field_1771 == 10000) {
            mc.field_1771 = 0;
        }
        mc.field_1690.field_1886.method_23481(true);
        ((MinecraftAccessor)mc).sixsevenclient$startAttack();
        mc.field_1690.field_1886.method_23481(false);
    }

    private void rightClick(class_310 mc) {
        ((MinecraftAccessor)mc).sixsevenclient$startUseItem();
    }
}

