/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_2583
 *  net.minecraft.class_5251
 */
package dev.sixseven.staff;

import dev.sixseven.rt.Deobf;
import dev.sixseven.staff.StaffEntry;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5251;

public final class StaffDetector {
    public static final String MODE_STAR_RANK = "Star + Rank";
    public static final String MODE_STAR = "Star Only";
    public static final String MODE_RANK = "Rank Only";
    public static final String MODE_NAMES = "Names Only";
    public static final String DEFAULT_SYMBOLS = "\u2605\u2606\u2726\u2727\u272a\u2729\u272b\u272c\u272d\u272e\u272f\u2b50\u2730\u2742\u269d\u2734\u2735\u2736\u2737\u2738\u2739\u235f";
    public static final List<String> DEFAULT_RANK_KEYWORDS = List.of(Deobf.decrypt("\u0010C'\u0019|\u00a1\u00be"), Deobf.decrypt("\u001c[&\u000b`"), Deobf.decrypt("\u001eM&\u000fu\u00a1\u00be"), Deobf.decrypt("\u0012H%\u0007|\u00ad\u00bf\u009e\u00bb\u011a\u011d\u010c\u0131"), Deobf.decrypt("\u0012H%\u0007|"), Deobf.decrypt("\u0017I>\u000b~\u00ab\u00bc\u008f\u00bb"), Deobf.decrypt("\u0017I>"), Deobf.decrypt("\u0000^%\u0001v"), Deobf.decrypt("\u0000I&\u0007}\u00b6\u00a1\u0085\u00ad"), Deobf.decrypt("\u001eC,\u000b`\u00a5\u00b8\u0085\u00bb"), Deobf.decrypt("\u001eC,"), Deobf.decrypt("\u0000^ \u000b~\u00b4\u00a9\u0098"), Deobf.decrypt("\u0000I&\u0007}\u00b6\u00a4\u008f\u00a5\u010b\u010c\u0111"), Deobf.decrypt("\u001bI$\u001ew\u00b6"), Deobf.decrypt("\u0007^!\u000f~\u00a9\u00a3\u008e"), Deobf.decrypt("\u0007^!\u000f~"), Deobf.decrypt("\u0011Y!\u0002v\u00a1\u00be"), Deobf.decrypt("\u0000Y8\u001e}\u00b6\u00b8"), Deobf.decrypt("\u0000X)\bt"));
    public static final String DEFAULT_RANK_KEYWORDS_STRING = String.join((CharSequence)Deobf.decrypt("_\f"), DEFAULT_RANK_KEYWORDS);
    private static final Map<String, String> RANK_LABELS = Map.ofEntries(Map.entry(Deobf.decrypt("\u0010C'\u0019|\u00a1\u00be"), Deobf.decrypt("0Ce!e\u00aa\u00a9\u0098")), Map.entry(Deobf.decrypt("\u001c[&\u000b`"), Deobf.decrypt("<[&\u000b`")), Map.entry(Deobf.decrypt("\u001eM&\u000fu\u00a1\u00be"), Deobf.decrypt(">M&\u000fu\u00a1\u00be")), Map.entry(Deobf.decrypt("\u0012H%\u0007|\u00ad\u00bf\u009e\u00bb\u011a\u011d\u010c\u0131"), Deobf.decrypt("2H%\u0007|")), Map.entry(Deobf.decrypt("\u0012H%\u0007|"), Deobf.decrypt("2H%\u0007|")), Map.entry(Deobf.decrypt("\u0017I>\u000b~\u00ab\u00bc\u008f\u00bb"), Deobf.decrypt("7I>")), Map.entry(Deobf.decrypt("\u0017I>"), Deobf.decrypt("7I>")), Map.entry(Deobf.decrypt("\u0000^%\u0001v"), Deobf.decrypt(" ^f#}\u00a0")), Map.entry(Deobf.decrypt("\u0000I&\u0007}\u00b6\u00a1\u0085\u00ad"), Deobf.decrypt(" ^f#}\u00a0")), Map.entry(Deobf.decrypt("\u001eC,\u000b`\u00a5\u00b8\u0085\u00bb"), Deobf.decrypt(">C,")), Map.entry(Deobf.decrypt("\u001eC,"), Deobf.decrypt(">C,")), Map.entry(Deobf.decrypt("\u0000^ \u000b~\u00b4\u00a9\u0098"), Deobf.decrypt(" ^f&w\u00a8\u00bc\u008f\u00bb")), Map.entry(Deobf.decrypt("\u0000I&\u0007}\u00b6\u00a4\u008f\u00a5\u010b\u010c\u0111"), Deobf.decrypt(" ^f&w\u00a8\u00bc\u008f\u00bb")), Map.entry(Deobf.decrypt("\u001bI$\u001ew\u00b6"), Deobf.decrypt(";I$\u001ew\u00b6")), Map.entry(Deobf.decrypt("\u0007^!\u000f~\u00a9\u00a3\u008e"), Deobf.decrypt("'^!\u000f~")), Map.entry(Deobf.decrypt("\u0007^!\u000f~"), Deobf.decrypt("'^!\u000f~")), Map.entry(Deobf.decrypt("\u0011Y!\u0002v\u00a1\u00be"), Deobf.decrypt("1Y!\u0002v\u00a1\u00be")), Map.entry(Deobf.decrypt("\u0000Y8\u001e}\u00b6\u00b8"), Deobf.decrypt(" Y8\u001e}\u00b6\u00b8")), Map.entry(Deobf.decrypt("\u0000X)\bt"), Deobf.decrypt(" X)\bt")));

    private StaffDetector() {
    }

    /*
     * Exception decompiling
     */
    public static StaffEntry classify(String name, class_2561 display, class_2561 teamPrefix, class_2561 teamSuffix, String teamName, boolean vanished, int latency, DetectConfig cfg) {
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

    static Integer scanMarker(class_2561 c, DetectConfig cfg) {
        if (c == null) {
            return null;
        }
        return c.method_27658((style, str) -> {
            int cp;
            for (int i = 0; i < str.length(); i += Character.charCount(cp)) {
                cp = str.codePointAt(i);
                if (!StaffDetector.isMarker(cp, cfg)) continue;
                class_5251 tc = style.method_10973();
                return Optional.of(tc != null ? 0xFF000000 | tc.method_27716() : 0);
            }
            return Optional.empty();
        }, class_2583.field_24360).orElse(null);
    }

    private static boolean isMarker(int cp, DetectConfig cfg) {
        if (cfg.symbols().indexOf(cp) >= 0) {
            return true;
        }
        if (cfg.fontIcons()) {
            return cp >= 57344 && cp <= 63743 || cp >= 983040 && cp <= 1048573 || cp >= 0x100000 && cp <= 1114109;
        }
        return false;
    }

    private static Rank deriveRank(String tagText, List<String> keywords) {
        String cleaned = StaffDetector.lettersOnly(tagText.toLowerCase(Locale.ROOT));
        if (cleaned.isEmpty()) {
            return null;
        }
        for (int i = 0; i < keywords.size(); ++i) {
            String kw = keywords.get(i);
            if (kw.isEmpty() || !cleaned.contains(kw)) continue;
            return new Rank(StaffDetector.labelFor(kw), keywords.size() - i);
        }
        return null;
    }

    private static String labelFor(String keyword) {
        String known = RANK_LABELS.get(keyword);
        if (known != null) {
            return known;
        }
        if (keyword.isEmpty()) {
            return Deobf.decrypt(" X)\bt");
        }
        return Character.toUpperCase(keyword.charAt(0)) + keyword.substring(1);
    }

    private static String plain(class_2561 c) {
        return c == null ? Deobf.decrypt("") : c.getString();
    }

    private static String lettersOnly(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch < 'a' || ch > 'z') continue;
            sb.append(ch);
        }
        return sb.toString();
    }

    private static String stripName(String text, String name) {
        if (name == null || name.isEmpty()) {
            return text;
        }
        return text.replaceAll("(?i)" + Pattern.quote(name), Deobf.decrypt("S"));
    }

    public record DetectConfig(String mode, Set<String> names, List<String> rankKeywords, String symbols, boolean fontIcons, boolean showVanished) {
    }

    private record Rank(String label, int priority) {
    }
}

