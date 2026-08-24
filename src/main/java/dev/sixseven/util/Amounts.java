/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_2583
 *  net.minecraft.class_5250
 */
package dev.sixseven.util;

import dev.sixseven.rt.Deobf;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public final class Amounts {
    private static final String[] SUFFIX = new String[]{Deobf.decrypt("\u0002X"), Deobf.decrypt("\u0002"), Deobf.decrypt("\u0007"), Deobf.decrypt("\u0011"), Deobf.decrypt("\u001e"), Deobf.decrypt("\u0018")};
    private static final String[] DISPLAY = new String[]{Deobf.decrypt("\"x"), Deobf.decrypt("\""), Deobf.decrypt("'"), Deobf.decrypt("1"), Deobf.decrypt(">"), Deobf.decrypt("\u0018")};
    private static final int[] POWER = new int[]{18, 15, 12, 9, 6, 3};
    private static final Pattern NUMBER = Pattern.compile(Deobf.decrypt("/H\u00132v\u00ea\u00e0\u00b7\u00e3\u0153\u0156\u0159\u011f\u0183\u01ac\u019c\u01a1\u0241\u022a\u0244\u020d\u02a4\u02ae\u02d6\u028f\u0323\u0305\u0327\u034a\u03fb\u03f8\u03bb\u03bb\u03ff\u045b\u041a\u041e\u045c\u04f4\u04fe\u04ae\u04be"));

    private Amounts() {
    }

    public static double parse(String raw) {
        if (raw == null) {
            return Double.NaN;
        }
        String s = raw.trim().toLowerCase(Locale.ROOT).replaceAll(Deobf.decrypt("(\u0000\u0014\u001dM\u0099"), Deobf.decrypt(""));
        while (!(s.isEmpty() || s.charAt(0) != '$' && s.charAt(0) != '\u20ac' && s.charAt(0) != '\u00a3')) {
            s = s.substring(1);
        }
        if (s.isEmpty()) {
            return Double.NaN;
        }
        for (int i = 0; i < SUFFIX.length; ++i) {
            if (!s.endsWith(SUFFIX[i]) || s.length() <= SUFFIX[i].length()) continue;
            String num = s.substring(0, s.length() - SUFFIX[i].length());
            try {
                return Double.parseDouble(num) * Math.pow(10.0, POWER[i]);
            }
            catch (NumberFormatException e) {
                return Double.NaN;
            }
        }
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static String shortForm(double n) {
        double abs = Math.abs(n);
        for (int i = 0; i < POWER.length; ++i) {
            double pow = Math.pow(10.0, POWER[i]);
            if (!(abs >= pow)) continue;
            double v = (double)Math.round(n / pow * 10.0) / 10.0;
            return Amounts.trimZero(v) + DISPLAY[i];
        }
        return Long.toString(Math.round(n));
    }

    public static String comma(double n) {
        return String.format(Locale.US, Deobf.decrypt("V\u0000,"), (long)Math.floor(n));
    }

    public static String plain(double n) {
        return Long.toString((long)Math.floor(n));
    }

    /*
     * Exception decompiling
     */
    public static String format(double n, String mode) {
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

    private static String trimZero(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v)) {
            return Long.toString((long)v);
        }
        return String.valueOf(v);
    }

    public static int[] lastNumberSpan(String text) {
        int[] nArray;
        Matcher m = NUMBER.matcher(text);
        int start = -1;
        int end = -1;
        while (m.find()) {
            start = m.start();
            end = m.end();
        }
        if (start < 0) {
            nArray = null;
        } else {
            int[] nArray2 = new int[2];
            nArray2[0] = start;
            nArray = nArray2;
            nArray2[1] = end;
        }
        return nArray;
    }

    public static int[] valueSpan(String text) {
        for (int i = 0; i < text.length(); ++i) {
            if (!Character.isDigit(text.charAt(i))) continue;
            return new int[]{i, text.length()};
        }
        return null;
    }

    public static class_2561 replaceNumberStyled(class_2561 original, String replacement) {
        return Amounts.spliceStyled(original, replacement, false);
    }

    public static class_2561 replaceValueStyled(class_2561 original, String replacement) {
        return Amounts.spliceStyled(original, replacement, true);
    }

    private static class_2561 spliceStyled(class_2561 original, String replacement, boolean toEnd) {
        int[] span;
        ArrayList styles = new ArrayList();
        ArrayList parts = new ArrayList();
        original.method_27658((style, str) -> {
            styles.add(style);
            parts.add(str);
            return Optional.empty();
        }, class_2583.field_24360);
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(p);
        }
        int[] nArray = span = toEnd ? Amounts.valueSpan(sb.toString()) : Amounts.lastNumberSpan(sb.toString());
        if (span == null) {
            return original;
        }
        int start = span[0];
        int end = span[1];
        class_5250 out = class_2561.method_43473();
        int pos = 0;
        boolean inserted = false;
        for (int i = 0; i < parts.size(); ++i) {
            int afterStart;
            String s = (String)parts.get(i);
            class_2583 st = (class_2583)styles.get(i);
            int rs = pos;
            int re = pos + s.length();
            int beforeEnd = Math.min(re, start);
            if (beforeEnd > rs) {
                out.method_10852((class_2561)class_2561.method_43470((String)s.substring(0, beforeEnd - rs)).method_10862(st));
            }
            if (!inserted && start >= rs && start < re) {
                out.method_10852((class_2561)class_2561.method_43470((String)replacement).method_10862(st));
                inserted = true;
            }
            if (re > (afterStart = Math.max(rs, end))) {
                out.method_10852((class_2561)class_2561.method_43470((String)s.substring(afterStart - rs)).method_10862(st));
            }
            pos = re;
        }
        if (!inserted) {
            out.method_10852((class_2561)class_2561.method_43470((String)replacement));
        }
        return out;
    }
}

