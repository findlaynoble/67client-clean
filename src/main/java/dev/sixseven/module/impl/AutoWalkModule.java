/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_304
 *  net.minecraft.class_310
 *  net.minecraft.class_315
 *  net.minecraft.class_3675$class_306
 *  net.minecraft.class_3675$class_307
 */
package dev.sixseven.module.impl;

import dev.sixseven.mixin.KeyMappingAccessor;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import net.minecraft.class_304;
import net.minecraft.class_310;
import net.minecraft.class_315;
import net.minecraft.class_3675;

public class AutoWalkModule
extends Module {
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt("$M$\u0005{\u00aa\u00ab\u00ca\u00a4\u0114\u010d\u0106\u016d"), Deobf.decrypt(" E%\u001e~\u00a1"), Deobf.decrypt(" E%\u001e~\u00a1"), Deobf.decrypt(" A)\u001cf")));
    public final ModeSetting direction = this.addSetting(new ModeSetting(Deobf.decrypt("7E:\u000bq\u00b0\u00a5\u0085\u00a7"), Deobf.decrypt("'D-Nv\u00ad\u00be\u008f\u00aa\u010f\u0100\u010c\u012d\u01d0\u01e7\u01db\u01be\u020c\u0210\u0259\u0237\u02d9\u029c\u02cc\u02fb\u032d\u0310\u0311\u0351\u03dc\u03f0\u03d6\u03b4\u03d2\u044b\u042b\u0441"), Deobf.decrypt("5C:\u0019s\u00b6\u00a8\u0099"), Deobf.decrypt("5C:\u0019s\u00b6\u00a8\u0099"), Deobf.decrypt("1M+\u0005e\u00a5\u00be\u008e\u00ba"), Deobf.decrypt("?I.\u001a"), Deobf.decrypt("!E/\u0006f")));
    public final BooleanSetting disableOnInput = this.addSetting(new BooleanSetting(Deobf.decrypt("7E;\u000fp\u00a8\u00a9\u00ca\u0086\u0115\u0149\u012a\u012d\u0180\u01e6\u01c0"), Deobf.decrypt("7E;\u000fp\u00a8\u00a9\u00ca\u00bd\u0113\u010c\u0143\u012e\u019f\u01f7\u01c1\u01f2\u021e\u0251\u025a\u0232\u02d9\u0298\u02c3\u02b5\u030b\u0318\u0310\u0301\u03dd\u03fa\u0380\u03bc\u03d0\u044a\u0420\u041b\u042d\u04c0\u04b9\u04f7\u04f4\u0551\u052a"), false));
    public final BooleanSetting disableOnY = this.addSetting(new BooleanSetting(Deobf.decrypt("7E;\u000fp\u00a8\u00a9\u00ca\u0086\u0115\u0149\u013a\u0163\u01b3\u01fb\u01d5\u01f0\u021c\u0214"), Deobf.decrypt("7E;\u000fp\u00a8\u00a9\u00ca\u00bd\u0113\u010c\u0143\u012e\u019f\u01f7\u01c1\u01f2\u021e\u0251\u025c\u023a\u02d9\u028c\u02cd\u02ae\u035e\u0314\u0313\u0357\u03d5\u03b5\u0380\u03bc\u03cf\u045b\u0427\u040c\u046c\u04c5\u04bb\u04fe\u04af"), false));
    public final BooleanSetting waitForChunks = this.addSetting(new BooleanSetting(Deobf.decrypt("=Ch;|\u00a8\u00a3\u008b\u00ad\u011e\u010d\u0143\u0100\u0198\u01e6\u01da\u01f5\u0208"), Deobf.decrypt("7Ch\u0000}\u00b0\u00ec\u009d\u00a8\u0117\u0102\u0143\u012a\u019e\u01e7\u01db\u01be\u020e\u021f\u0259\u0233\u0298\u0291\u02c7\u02bf\u035e\u031a\u0314\u0354\u03de\u03fe\u0385\u03f7"), true));

    public AutoWalkModule() {
        super(Deobf.decrypt("2Y<\u0001E\u00a5\u00a0\u0081"), Deobf.decrypt("2Y<\u0001\u007f\u00a5\u00b8\u0083\u00aa\u011a\u0105\u010f\u013a\u01d0\u01e4\u01d5\u01f2\u0210\u0202\u0215\u023a\u0296\u0287\u02d5\u02ba\u030c\u031d\u0352"), Category.MISC);
        this.direction.visibleWhen(() -> this.mode.is(Deobf.decrypt(" E%\u001e~\u00a1")));
        this.disableOnY.visibleWhen(() -> this.mode.is(Deobf.decrypt(" E%\u001e~\u00a1")));
        this.waitForChunks.visibleWhen(() -> this.mode.is(Deobf.decrypt(" E%\u001e~\u00a1")));
    }

    @Override
    protected void onDisable() {
        this.release(class_310.method_1551());
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

    @Override
    public boolean onKeyPress(int keyCode) {
        class_310 mc = class_310.method_1551();
        if (((Boolean)this.disableOnInput.get()).booleanValue() && mc.field_1755 == null && this.isMovementKey(mc, keyCode)) {
            this.toggle();
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    private boolean chunkAheadLoaded(class_310 mc) {
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

    private void release(class_310 mc) {
        if (mc.field_1690 == null) {
            return;
        }
        mc.field_1690.field_1894.method_23481(false);
        mc.field_1690.field_1881.method_23481(false);
        mc.field_1690.field_1913.method_23481(false);
        mc.field_1690.field_1849.method_23481(false);
        mc.field_1690.field_1903.method_23481(false);
        mc.field_1690.field_1867.method_23481(false);
    }

    private boolean isMovementKey(class_310 mc, int keyCode) {
        class_315 o = mc.field_1690;
        return AutoWalkModule.matches(o.field_1894, keyCode) || AutoWalkModule.matches(o.field_1881, keyCode) || AutoWalkModule.matches(o.field_1913, keyCode) || AutoWalkModule.matches(o.field_1849, keyCode) || AutoWalkModule.matches(o.field_1903, keyCode) || AutoWalkModule.matches(o.field_1832, keyCode);
    }

    private static boolean matches(class_304 km, int keyCode) {
        class_3675.class_306 key = ((KeyMappingAccessor)km).sixsevenclient$getKey();
        return key.method_1442() == class_3675.class_307.field_1668 && key.method_1444() == keyCode;
    }
}

