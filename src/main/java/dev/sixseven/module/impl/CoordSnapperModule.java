/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_239
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_310
 *  net.minecraft.class_3965
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.KeybindSetting;
import dev.sixseven.settings.ModeSetting;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_3965;

public class CoordSnapperModule
extends Module {
    public final ModeSetting format = this.addSetting(new ModeSetting(Deobf.decrypt("5C:\u0003s\u00b0"), Deobf.decrypt("0@!\u001ep\u00ab\u00ad\u0098\u00ad\u015b\u010f\u010c\u0131\u019d\u01f2\u01c0\u01b0"), Deobf.decrypt("+\f\u0011NH"), Deobf.decrypt("+\f\u0011NH"), Deobf.decrypt("9\u007f\u0007 "), Deobf.decrypt("0C%\u0003s\u00aa\u00a8")));
    public final ModeSetting target = this.addSetting(new ModeSetting(Deobf.decrypt("'M:\tw\u00b0"), Deobf.decrypt("$D!\rz\u00e4\u00af\u0085\u00a6\u0109\u010d\u010a\u012d\u0191\u01e7\u01d1\u01ed\u025b\u0205\u025a\u027c\u029a\u029a\u02d2\u02a2\u0350"), Deobf.decrypt("?C'\u0005w\u00a0\u00e1\u008b\u00bd\u015b\u012b\u010f\u012c\u0193\u01f8"), Deobf.decrypt("?C'\u0005w\u00a0\u00e1\u008b\u00bd\u015b\u012b\u010f\u012c\u0193\u01f8"), Deobf.decrypt("#@)\u0017w\u00b6")));
    public final KeybindSetting copyKey = this.addSetting(new KeybindSetting(Deobf.decrypt("0C8\u00172\u008f\u00a9\u0093"), Deobf.decrypt("#^-\u001da\u00e4\u00b8\u0085\u00e9\u0118\u0106\u0113\u013a\u01d0\u01e7\u01dc\u01fb\u025b\u0212\u025a\u0233\u028b\u0291\u02cb\u02b5\u031f\u030d\u0319\u0352\u039e"), -1));
    public final BooleanSetting notify = this.addSetting(new BooleanSetting(Deobf.decrypt("=C<\u0007t\u00bd"), Deobf.decrypt(" D'\u00192\u00a5\u00ec\u0089\u00a6\u0115\u010f\u010a\u0131\u019d\u01f2\u01c0\u01f7\u0214\u021f\u0215\u022b\u0291\u0290\u02cc\u02fb\u031d\u0316\u030c\u0348\u03d5\u03f1\u03d8"), true));
    private String lastCopied = Deobf.decrypt("");

    public CoordSnapperModule() {
        super(Deobf.decrypt("0C'\u001cv\u0097\u00a2\u008b\u00b9\u010b\u010c\u0111"), Deobf.decrypt("0C8\u0007w\u00b7\u00ec\u0086\u00a6\u0114\u0102\u0106\u0127\u01dd\u01f2\u01c0\u01be\u0218\u021e\u025a\u022e\u029d\u029c\u02cc\u02ba\u030a\u031c\u030f\u0301\u03c7\u03fc\u0382\u03b1\u039d\u0440\u0420\u040a\u042d\u04c2\u04b2\u04fe\u04af"), Category.MISC);
    }

    public String lastCopied() {
        return this.lastCopied;
    }

    @Override
    public boolean onKeyPress(int keyCode) {
        if (!this.copyKey.matches(keyCode)) {
            return false;
        }
        this.snap();
        return true;
    }

    /*
     * Exception decompiling
     */
    private void snap() {
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

    private class_2338 resolvePos(class_310 mc) {
        if (this.target.is(Deobf.decrypt("#@)\u0017w\u00b6"))) {
            return mc.field_1724.method_24515();
        }
        class_239 hit = mc.field_1765;
        if (hit instanceof class_3965) {
            class_3965 block = (class_3965)hit;
            if (hit.method_17783() == class_239.class_240.field_1332) {
                return block.method_17777();
            }
        }
        return null;
    }
}

