/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_2583
 *  net.minecraft.class_2588
 *  net.minecraft.class_310
 *  net.minecraft.class_5250
 *  net.minecraft.class_7417
 *  net.minecraft.class_8828
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_2588;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_7417;
import net.minecraft.class_8828;

public class FakeRolesModule
extends Module {
    public static final String ROLE_NONE = "None";
    public static final String ROLE_SRMOD = "SR.MOD";
    public static final String ROLE_MEDIA = "MEDIA";
    public static final String ROLE_SRADMIN = "SR.ADMIN";
    private static final int GRAY = 0x7F7F7F;
    private static final int GREEN = 0x55FF55;
    private static final int PINK = 0xFF55FF;
    private static final int RED = 0xFF5455;
    private static final int WHITE = 0xFFFFFF;
    public final ModeSetting role = this.addSetting(new ModeSetting(Deobf.decrypt("!C$\u000b"), Deobf.decrypt("$D!\rz\u00e4\u00aa\u008b\u00a2\u011e\u0149\u0111\u0122\u019e\u01f8\u0194\u01ea\u021a\u0216\u0215\u0228\u0296\u02d5\u02d5\u02be\u031f\u030b\u035c\u0348\u03de\u03b5\u0390\u03ab\u03d2\u0441\u043a\u044f\u0462\u04cf\u04f7\u04fe\u04ee\u0550\u0576\u0518\u0542\u05c5\u05eb\u05d6"), Deobf.decrypt("=C&\u000b"), Deobf.decrypt("=C&\u000b"), Deobf.decrypt(" ~f#]\u0080"), Deobf.decrypt(">i\f'S"), Deobf.decrypt(" ~f/V\u0089\u0085\u00a4")));
    public final BooleanSetting nametag = this.addSetting(new BooleanSetting(Deobf.decrypt("=M%\u000bf\u00a5\u00ab"), Deobf.decrypt(" D'\u00192\u00b0\u00a4\u008f\u00e9\u010f\u0108\u0104\u0163\u019f\u01fd\u0194\u01e7\u0214\u0204\u0247\u027c\u029f\u0299\u02cd\u02ba\u030a\u0310\u0312\u0346\u0390\u03fb\u0397\u03b4\u03d8\u045b\u042f\u0408\u042d\u0481\u04e4\u04f5\u04e5\u0505\u0574\u055d\u055e\u05d7\u05e9\u05dd\u05e7"), true));
    public final BooleanSetting tabList = this.addSetting(new BooleanSetting(Deobf.decrypt("'M*N^\u00ad\u00bf\u009e"), Deobf.decrypt(" D'\u00192\u00b0\u00a4\u008f\u00e9\u010f\u0108\u0104\u0163\u0192\u01f6\u01d2\u01f1\u0209\u0214\u0215\u0225\u0296\u0280\u02d0\u02fb\u0310\u0318\u0311\u0344\u0390\u03fc\u0398\u03f9\u03c9\u0447\u042b\u044f\u047d\u04c5\u04b6\u04fe\u04e4\u0557\u0524\u0554\u0545\u05d7\u05f2\u0593\u05e6\u063f\u0622\u065a\u061d"), true));
    public final BooleanSetting chat = this.addSetting(new BooleanSetting(Deobf.decrypt("0D)\u001a"), Deobf.decrypt(" D'\u00192\u00b0\u00a4\u008f\u00e9\u010f\u0108\u0104\u0163\u0192\u01f6\u01d2\u01f1\u0209\u0214\u0215\u0225\u0296\u0280\u02d0\u02fb\u0310\u0318\u0311\u0344\u0390\u03fc\u0398\u03f9\u03de\u0447\u042f\u041b\u042d\u04c4\u04b2\u04f4\u04f2\u0544\u0563\u055d\u055f"), true));

    public FakeRolesModule() {
        super(Deobf.decrypt("5M#\u000b@\u00ab\u00a0\u008f\u00ba"), Deobf.decrypt("5M#\u000b2\u009f\u009f\u00b8\u00e7\u0136\u0126\u0127\u011e\u01d0\u01bc\u0194\u01c5\u0236\u0234\u0271\u0215\u02b8\u02a8\u0282\u02f4\u035e\u0322\u032f\u0373\u039e\u03d4\u03b2\u0394\u03f4\u0461\u0413\u044f\u047f\u04c8\u04b9\u04ec\u04a1\u0551\u0565\u055f\u050c\u05cb\u05e8\u0593\u05b7\u0604\u0636\u064a\u0614\u069d\u06ee\u06d4\u0698\u0703\u0777\u0750\u0759"), Category.MISC);
    }

    public boolean isActive() {
        class_310 mc = class_310.method_1551();
        return this.isEnabled() && !this.role.is(Deobf.decrypt("=C&\u000b")) && mc.field_1724 != null;
    }

    private String selfName() {
        class_310 mc = class_310.method_1551();
        return mc.field_1724 == null ? null : mc.field_1724.method_7334().name();
    }

    private boolean isSelf(String exactName) {
        String me = this.selfName();
        return me != null && !me.isEmpty() && me.equals(exactName);
    }

    /*
     * Exception decompiling
     */
    private int roleColor() {
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

    private class_2583 bracketStyle() {
        return class_2583.field_24360.method_36139(0x7F7F7F).method_10982(Boolean.valueOf(false));
    }

    private class_2583 tagStyle() {
        return class_2583.field_24360.method_36139(this.roleColor()).method_10982(Boolean.valueOf(true));
    }

    /*
     * Exception decompiling
     */
    private class_2583 nameStyle() {
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

    public class_2561 tagComponent() {
        return class_2561.method_43473().method_10852((class_2561)class_2561.method_43470((String)Deobf.decrypt("(")).method_10862(this.bracketStyle())).method_10852((class_2561)class_2561.method_43470((String)((String)this.role.get())).method_10862(this.tagStyle())).method_10852((class_2561)class_2561.method_43470((String)Deobf.decrypt(".\f")).method_10862(this.bracketStyle()));
    }

    public class_2561 buildPrefixedDisplayName(String name) {
        if (!this.isActive() || name == null) {
            return null;
        }
        return class_2561.method_43473().method_10852(this.tagComponent()).method_10852((class_2561)class_2561.method_43470((String)name).method_10862(this.nameStyle()));
    }

    public class_2561 decorateNametag(class_2561 tag) {
        if (!this.isActive() || !((Boolean)this.nametag.get()).booleanValue()) {
            return tag;
        }
        return this.modifyText(tag);
    }

    public class_2561 decorateTab(class_2561 display, String realName) {
        if (!this.isActive() || !((Boolean)this.tabList.get()).booleanValue()) {
            return display;
        }
        class_2561 prefixed = this.isSelf(realName) ? this.buildPrefixedDisplayName(realName) : null;
        return prefixed != null ? prefixed : display;
    }

    public class_2561 decorateChat(class_2561 input) {
        if (!this.isActive() || !((Boolean)this.chat.get()).booleanValue()) {
            return input;
        }
        return this.modifyText(input);
    }

    private class_2561 modifyText(class_2561 input) {
        if (input == null) {
            return null;
        }
        String me = this.selfName();
        if (me == null || me.isBlank() || !input.getString().contains(me)) {
            return input;
        }
        return this.splice(input, me, new boolean[]{false});
    }

    private class_2561 splice(class_2561 c, String name, boolean[] done) {
        class_5250 result;
        class_7417 contents = c.method_10851();
        if (!done[0] && contents instanceof class_8828) {
            class_8828 ptc = (class_8828)contents;
            String text = ptc.comp_737();
            int idx = text.indexOf(name);
            if (idx >= 0) {
                done[0] = true;
                result = class_2561.method_43473();
                if (idx > 0) {
                    result.method_10852((class_2561)class_2561.method_43470((String)text.substring(0, idx)).method_10862(c.method_10866()));
                }
                result.method_10852(this.buildPrefixedDisplayName(name));
                int end = idx + name.length();
                if (end < text.length()) {
                    result.method_10852((class_2561)class_2561.method_43470((String)text.substring(end)).method_10862(c.method_10866()));
                }
            } else {
                result = class_5250.method_43477((class_7417)contents).method_10862(c.method_10866());
            }
        } else if (!done[0] && contents instanceof class_2588) {
            class_2588 tc = (class_2588)contents;
            Object[] args = tc.method_11023();
            Object[] out = new Object[args.length];
            for (int i = 0; i < args.length; ++i) {
                String s;
                Object arg = args[i];
                if (arg instanceof class_2561) {
                    class_2561 ac = (class_2561)arg;
                    out[i] = this.splice(ac, name, done);
                    continue;
                }
                if (!done[0] && arg instanceof String && (s = (String)arg).contains(name)) {
                    done[0] = true;
                    int idx = s.indexOf(name);
                    class_5250 split = class_2561.method_43473();
                    if (idx > 0) {
                        split.method_10852((class_2561)class_2561.method_43470((String)s.substring(0, idx)));
                    }
                    split.method_10852(this.buildPrefixedDisplayName(name));
                    int end = idx + name.length();
                    if (end < s.length()) {
                        split.method_10852((class_2561)class_2561.method_43470((String)s.substring(end)));
                    }
                    out[i] = split;
                    continue;
                }
                out[i] = arg;
            }
            result = class_5250.method_43477((class_7417)new class_2588(tc.method_11022(), tc.method_48323(), out)).method_10862(c.method_10866());
        } else {
            result = class_5250.method_43477((class_7417)contents).method_10862(c.method_10866());
        }
        for (class_2561 sibling : c.method_10855()) {
            result.method_10852(this.splice(sibling, name, done));
        }
        return result;
    }
}

