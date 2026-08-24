/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.class_2561
 *  net.minecraft.class_2588
 *  net.minecraft.class_310
 *  net.minecraft.class_5250
 *  net.minecraft.class_634
 *  net.minecraft.class_640
 *  net.minecraft.class_7417
 *  net.minecraft.class_8828
 */
package dev.sixseven.module.impl;

import com.mojang.authlib.GameProfile;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.StringSetting;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.class_2561;
import net.minecraft.class_2588;
import net.minecraft.class_310;
import net.minecraft.class_5250;
import net.minecraft.class_634;
import net.minecraft.class_640;
import net.minecraft.class_7417;
import net.minecraft.class_8828;

public class NameProtectModule
extends Module {
    public final StringSetting ownName = this.addSetting(new StringSetting(Deobf.decrypt("*C=\u001c2\u0085\u00a0\u0083\u00a8\u0108"), Deobf.decrypt("$D)\u001a2\u00bd\u00a3\u009f\u00bb\u015b\u0106\u0114\u012d\u01d0\u01fd\u01d5\u01f3\u021e\u0251\u025c\u022f\u02d9\u0287\u02c7\u02ab\u0312\u0318\u031f\u0344\u03d4\u03b5\u0381\u03b0\u03c9\u0447"), Deobf.decrypt("*C="), 16, Deobf.decrypt("*C=")));
    public final ModeSetting style = this.addSetting(new ModeSetting(Deobf.decrypt("<X \u000b`\u00b7"), Deobf.decrypt(";C?N}\u00b0\u00a4\u008f\u00bb\u015b\u0119\u010f\u0122\u0189\u01f6\u01c6\u01ed\u025c\u0251\u025b\u023d\u0294\u0290\u02d1\u02fb\u031f\u030b\u0319\u0301\u03c2\u03f0\u0386\u03b5\u03dc\u044c\u042b\u040b"), Deobf.decrypt("2@!\u000fa\u00a1\u00bf"), Deobf.decrypt("2@!\u000fa\u00a1\u00bf"), Deobf.decrypt("1@)\u0000y"), Deobf.decrypt("#@)\u0017w\u00b6\u00ec\u00c9")));
    public final BooleanSetting selfOnly = this.addSetting(new BooleanSetting(Deobf.decrypt(" I$\b2\u008b\u00a2\u0086\u00b0"), Deobf.decrypt("<B$\u00172\u00ac\u00a5\u008e\u00ac\u015b\u0110\u010c\u0136\u0182\u01b3\u01db\u01e9\u0215\u0251\u025b\u023d\u0294\u0290\u028e\u02fb\u0312\u031c\u031d\u0357\u03d5\u03b5\u0399\u03ad\u03d5\u044a\u043c\u041c\u042d\u04c8\u04bb\u04e8\u04ef\u0540"), false));
    private static final int SEEN_CAP = 256;
    private final LinkedHashMap<String, String> seen = new LinkedHashMap<String, String>(16, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return this.size() > 256;
        }
    };
    private String cacheSig = null;
    private Map<String, String> cachedTargets = Map.of();
    private Pattern cachedPattern = null;
    private class_634 lastConnection = null;

    public NameProtectModule() {
        super(Deobf.decrypt("=M%\u000bB\u00b6\u00a3\u009e\u00ac\u0118\u011d"), Deobf.decrypt(";E,\u000ba\u00e4\u00bc\u0086\u00a8\u0102\u010c\u0111\u0163\u019e\u01f2\u01d9\u01fb\u0208\u0251\u025c\u0232\u02d9\u0296\u02ce\u02b2\u030e\u030a"), Category.MISC);
    }

    private String selfName() {
        class_310 mc = class_310.method_1551();
        return mc.field_1724 == null ? null : mc.field_1724.method_7334().name();
    }

    private boolean isSelf(String realName) {
        String self = this.selfName();
        return self != null && self.equalsIgnoreCase(realName);
    }

    public String styledFor(String realName) {
        if (this.isSelf(realName)) {
            String alias = (String)this.ownName.get();
            return alias == null || alias.isBlank() ? Deobf.decrypt("*C=") : alias;
        }
        if (this.style.is(Deobf.decrypt("1@)\u0000y"))) {
            return Deobf.decrypt("");
        }
        if (this.style.is(Deobf.decrypt("#@)\u0017w\u00b6\u00ec\u00c9"))) {
            return "Player " + (Math.floorMod(realName.toLowerCase(Locale.ROOT).hashCode(), 99) + 1);
        }
        return Deobf.decrypt("#@)\u0017w\u00b6");
    }

    private Map<String, String> buildTargets() {
        boolean others;
        String self;
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        class_310 mc = class_310.method_1551();
        class_634 connection = mc.method_1562();
        if (connection != this.lastConnection) {
            this.seen.clear();
            this.lastConnection = connection;
        }
        if ((self = this.selfName()) != null && !self.isEmpty()) {
            this.seen.put(self.toLowerCase(Locale.ROOT), self);
            map.put(self, this.styledFor(self));
        }
        boolean bl = others = (Boolean)this.selfOnly.get() == false;
        if (connection != null) {
            for (class_640 info : connection.method_2880()) {
                GameProfile profile = info.method_2966();
                String name = profile == null ? null : profile.name();
                if (name == null || name.isEmpty()) continue;
                this.seen.put(name.toLowerCase(Locale.ROOT), name);
                if (!others) continue;
                map.putIfAbsent(name, this.styledFor(name));
            }
        }
        if (others) {
            for (String name : new ArrayList<String>(this.seen.values())) {
                map.putIfAbsent(name, this.styledFor(name));
            }
        }
        map.entrySet().removeIf(e -> ((String)e.getKey()).equalsIgnoreCase((String)e.getValue()));
        return map;
    }

    private void ensureCache() {
        class_310 mc = class_310.method_1551();
        int tick = mc.field_1724 == null ? -1 : mc.field_1724.field_6012;
        String sig = tick + "|" + String.valueOf(this.selfOnly.get()) + "|" + (String)this.style.get() + "|" + (String)this.ownName.get();
        if (sig.equals(this.cacheSig)) {
            return;
        }
        this.cacheSig = sig;
        this.cachedTargets = this.buildTargets();
        this.cachedPattern = NameProtectModule.buildPattern(this.cachedTargets);
    }

    private static Pattern buildPattern(Map<String, String> targets) {
        if (targets.isEmpty()) {
            return null;
        }
        ArrayList<String> names = new ArrayList<String>(targets.keySet());
        names.sort((a, b) -> Integer.compare(b.length(), a.length()));
        StringBuilder sb = new StringBuilder(Deobf.decrypt("[\u0013!G:\u00fb\u00f0\u00cb\u0092\u013a\u0144\u0139\u0122\u01dd\u01e9\u0184\u01b3\u0242\u022e\u0268\u0275\u02d1"));
        for (int i = 0; i < names.size(); ++i) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(Pattern.quote((String)names.get(i)));
        }
        sb.append(Deobf.decrypt("Z\u0004wOI\u0085\u00e1\u00b0\u00a8\u0156\u0113\u0153\u016e\u01c9\u01cc\u01e9\u01b7"));
        return Pattern.compile(sb.toString());
    }

    public String replacementForDisplay(String display) {
        if (display == null || display.isEmpty()) {
            return null;
        }
        this.ensureCache();
        if (this.cachedPattern == null) {
            return null;
        }
        String replaced = this.replaceNames(display);
        return replaced.equals(display) ? null : replaced;
    }

    public class_2561 censorChat(class_2561 input) {
        if (input == null) {
            return null;
        }
        this.ensureCache();
        if (this.cachedPattern == null) {
            return input;
        }
        return this.rewrite(input);
    }

    private class_2561 rewrite(class_2561 c) {
        class_5250 result;
        class_7417 contents = c.method_10851();
        if (contents instanceof class_8828) {
            class_8828 ptc = (class_8828)contents;
            result = class_5250.method_43477((class_7417)class_8828.method_54232((String)this.replaceNames(ptc.comp_737())));
        } else if (contents instanceof class_2588) {
            class_2588 tc = (class_2588)contents;
            Object[] args = tc.method_11023();
            Object[] newArgs = new Object[args.length];
            for (int i = 0; i < args.length; ++i) {
                Object arg = args[i];
                if (arg instanceof class_2561) {
                    class_2561 ac = (class_2561)arg;
                    newArgs[i] = this.rewrite(ac);
                    continue;
                }
                if (arg instanceof String) {
                    String s = (String)arg;
                    newArgs[i] = this.replaceNames(s);
                    continue;
                }
                newArgs[i] = arg;
            }
            result = class_5250.method_43477((class_7417)new class_2588(tc.method_11022(), tc.method_48323(), newArgs));
        } else {
            result = class_5250.method_43477((class_7417)contents);
        }
        result.method_10862(c.method_10866());
        for (class_2561 sibling : c.method_10855()) {
            result.method_10852(this.rewrite(sibling));
        }
        return result;
    }

    private String replaceNames(String text) {
        if (text == null || text.isEmpty() || this.cachedPattern == null) {
            return text;
        }
        Matcher m = this.cachedPattern.matcher(text);
        if (!m.find()) {
            return text;
        }
        StringBuilder out = new StringBuilder(text.length());
        int last = 0;
        do {
            String matched = m.group(1);
            String alias = this.aliasFor(matched);
            out.append(text, last, m.start());
            out.append(alias != null ? alias : matched);
            last = m.end();
        } while (m.find());
        out.append(text, last, text.length());
        return out.toString();
    }

    private String aliasFor(String matched) {
        String direct = this.cachedTargets.get(matched);
        if (direct != null) {
            return direct;
        }
        for (Map.Entry<String, String> e : this.cachedTargets.entrySet()) {
            if (!e.getKey().equalsIgnoreCase(matched)) continue;
            return e.getValue();
        }
        return null;
    }
}

