/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_5250
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.settings.StringSetting;
import dev.sixseven.util.Amounts;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_5250;

public class FakeStatsModule
extends Module {
    private static final int GRAY = 0xAAAAAA;
    private static final int GREEN = 0x55FF55;
    private static final String[] MONEY_KEYS = new String[]{Deobf.decrypt("\u001eC&\u000bk"), Deobf.decrypt("\u0011M$\u000f|\u00a7\u00a9"), Deobf.decrypt("\u0010C!\u0000a"), Deobf.decrypt("\u0010M;\u0006")};
    private static final String[] SHARD_KEYS = new String[]{Deobf.decrypt("\u0000D)\u001cv")};
    private static final String[] KILL_KEYS = new String[]{Deobf.decrypt("\u0018E$\u0002")};
    private static final String[] DEATH_KEYS = new String[]{Deobf.decrypt("\u0017I)\u001az")};
    private static final String[] PLAYTIME_KEYS = new String[]{Deobf.decrypt("\u0003@)\u0017f\u00ad\u00a1\u008f"), Deobf.decrypt("\u0003@)\u00172\u00b0\u00a5\u0087\u00ac"), Deobf.decrypt("\u0007E%\u000b2\u00b4\u00a0\u008b\u00b0\u011e\u010d"), Deobf.decrypt("\u0003@)\u0017w\u00a0")};
    public final StringSetting money = this.addSetting(new StringSetting(Deobf.decrypt(">C&\u000bk"), Deobf.decrypt("*C=\u001c2\u00a2\u00ad\u0081\u00ac\u015b\u010b\u0102\u012f\u0191\u01fd\u01d7\u01fb\u025b\u0259\u0204\u0231\u02d5\u02d5\u0294\u02ec\u0315\u0355\u035c\u0313\u0385\u03a5\u03c6\u03e9\u038d\u0406\u0460\u044f\u045a\u04c0\u04a5\u04e2\u04e5\u0505\u0570\u0557\u050c\u05e2\u05e7\u05d8\u05ab\u063b\u0622\u0641\u061a\u06d2\u06db\u06d6\u06d9\u0703\u077d\u071d\u0701\u079b\u078b\u0795\u078e\u07e7"), Deobf.decrypt("BA"), 32, Deobf.decrypt("\u0016\u0002/@2\u00f5\u00a1\u00c6\u00e9\u0149\u015c\u0153\u0128")));
    public final SliderSetting moneyLine = this.addSetting(new SliderSetting(Deobf.decrypt(">C&\u000bk\u00e4\u0080\u0083\u00a7\u011e"), Deobf.decrypt("$D!\rz\u00e4\u00bf\u0083\u00ad\u011e\u010b\u0102\u0131\u01d0\u01ff\u01dd\u01f0\u021e\u0251\u025c\u022f\u02d9\u0298\u02cd\u02b5\u031b\u0300\u035c\u0309\u03d3\u03fa\u0383\u03b7\u03c9\u040f\u0428\u041d\u0462\u04c4\u04f7\u04f3\u04e9\u0540\u0524\u054c\u0543\u05d4\u05af\u059d\u05ee\u065b\u0663\u0605\u0614\u0693\u06ec\u06ce\u06d7\u074d\u0774\u0744\u071c\u07d5\u0785\u079e\u078d\u07e7"), 1.0, 0.0, 15.0, 1.0).withLabel(FakeStatsModule::lineLabel));
    public final StringSetting shards = this.addSetting(new StringSetting(Deobf.decrypt(" D)\u001cv\u00b7"), Deobf.decrypt("5M#\u000b2\u00b7\u00a4\u008b\u00bb\u011f\u0149\u0100\u012c\u0185\u01fd\u01c0\u01b0\u025b\u0233\u0259\u023d\u0297\u029e\u0282\u02e6\u035e\u0316\u031a\u0347\u039e"), Deobf.decrypt(""), 32, Deobf.decrypt("\u0016\u0002/@2\u00fd\u00e0\u00d3\u00f0\u0142")));
    public final SliderSetting shardsLine = this.addSetting(new SliderSetting(Deobf.decrypt(" D)\u001cv\u00b7\u00ec\u00a6\u00a0\u0115\u010c"), Deobf.decrypt("$D!\rz\u00e4\u00bf\u0083\u00ad\u011e\u010b\u0102\u0131\u01d0\u01ff\u01dd\u01f0\u021e\u0251\u025c\u022f\u02d9\u0286\u02ca\u02ba\u030c\u031d\u030f\u030f\u0390\u03a5\u03d6\u03e4\u039d\u044e\u043b\u041b\u0462\u0489\u04b5\u04fe\u04a1\u054b\u0565\u0555\u0549\u058a"), 2.0, 0.0, 15.0, 1.0).withLabel(FakeStatsModule::lineLabel));
    public final StringSetting kills = this.addSetting(new StringSetting(Deobf.decrypt("8E$\u0002a"), Deobf.decrypt("5M#\u000b2\u00af\u00a5\u0086\u00a5\u015b\u010a\u010c\u0136\u019e\u01e7\u019a\u01be\u0239\u021d\u0254\u0232\u0292\u02d5\u029f\u02fb\u0311\u031f\u031a\u030f"), Deobf.decrypt(""), 32, Deobf.decrypt("\u0016\u0002/@2\u00f5\u00e0\u00da\u00f9\u014b")));
    public final SliderSetting killsLine = this.addSetting(new SliderSetting(Deobf.decrypt("8E$\u0002a\u00e4\u0080\u0083\u00a7\u011e"), Deobf.decrypt("$D!\rz\u00e4\u00bf\u0083\u00ad\u011e\u010b\u0102\u0131\u01d0\u01ff\u01dd\u01f0\u021e\u0251\u025c\u022f\u02d9\u029e\u02cb\u02b7\u0312\u030a\u0352\u0301\u0380\u03b5\u03cb\u03f9\u03dc\u045a\u043a\u0400\u042d\u04cb\u04ae\u04a7\u04ef\u0544\u0569\u055d\u0502"), 3.0, 0.0, 15.0, 1.0).withLabel(FakeStatsModule::lineLabel));
    public final StringSetting deaths = this.addSetting(new StringSetting(Deobf.decrypt("7I)\u001az\u00b7"), Deobf.decrypt("5M#\u000b2\u00a0\u00a9\u008b\u00bd\u0113\u0149\u0100\u012c\u0185\u01fd\u01c0\u01b0\u025b\u0233\u0259\u023d\u0297\u029e\u0282\u02e6\u035e\u0316\u031a\u0347\u039e"), Deobf.decrypt(""), 32, Deobf.decrypt("\u0016\u0002/@2\u00f4")));
    public final SliderSetting deathsLine = this.addSetting(new SliderSetting(Deobf.decrypt("7I)\u001az\u00b7\u00ec\u00a6\u00a0\u0115\u010c"), Deobf.decrypt("$D!\rz\u00e4\u00bf\u0083\u00ad\u011e\u010b\u0102\u0131\u01d0\u01ff\u01dd\u01f0\u021e\u0251\u025c\u022f\u02d9\u0291\u02c7\u02ba\u030a\u0311\u030f\u030f\u0390\u03a5\u03d6\u03e4\u039d\u044e\u043b\u041b\u0462\u0489\u04b5\u04fe\u04a1\u054b\u0565\u0555\u0549\u058a"), 4.0, 0.0, 15.0, 1.0).withLabel(FakeStatsModule::lineLabel));
    public final StringSetting playtime = this.addSetting(new StringSetting(Deobf.decrypt("#@)\u0017f\u00ad\u00a1\u008f"), Deobf.decrypt("5M#\u000b2\u00b4\u00a0\u008b\u00b0\u010f\u0100\u010e\u0126\u01dc\u01b3\u01d5\u01f0\u0202\u0251\u0241\u0239\u0281\u0281\u028c\u02fb\u033c\u0315\u031d\u034f\u03db\u03b5\u03cb\u03f9\u03d2\u0449\u0428\u0441"), Deobf.decrypt(""), 32, Deobf.decrypt("\u0016\u0002/@2\u00f7\u00fa\u00df\u00ad\u015b\u0158\u0151\u012b")));
    public final SliderSetting playtimeLine = this.addSetting(new SliderSetting(Deobf.decrypt("#@)\u0017f\u00ad\u00a1\u008f\u00e9\u0137\u0100\u010d\u0126"), Deobf.decrypt("$D!\rz\u00e4\u00bf\u0083\u00ad\u011e\u010b\u0102\u0131\u01d0\u01ff\u01dd\u01f0\u021e\u0251\u025c\u022f\u02d9\u0285\u02ce\u02ba\u0307\u030d\u0315\u034c\u03d5\u03bb\u03d6\u03e9\u039d\u0412\u046e\u040e\u0478\u04dd\u04b8\u04a7\u04e3\u055c\u0524\u0556\u054d\u05c9\u05e3\u059d"), 5.0, 0.0, 15.0, 1.0).withLabel(FakeStatsModule::lineLabel));
    public final BooleanSetting sidebar = this.addSetting(new BooleanSetting(Deobf.decrypt(" E,\u000bp\u00a5\u00be"), Deobf.decrypt("!I?\u001c{\u00b0\u00a9\u00ca\u00bd\u0113\u010c\u0143\u0130\u0195\u01e1\u01c2\u01fb\u0209\u0251\u0246\u023f\u0296\u0287\u02c7\u02b9\u0311\u0318\u030e\u0345\u0390\u03bd\u039a\u03bc\u03dc\u044b\u042b\u041d\u046f\u04c6\u04b6\u04f5\u04e5\u050c\u0524\u0557\u0542\u0584\u05f2\u05db\u05ab\u064b\u0631\u0651\u0653\u069a\u06ed\u0694"), true));
    public final BooleanSetting balanceCommand = this.addSetting(new BooleanSetting(Deobf.decrypt("1M$\u000f|\u00a7\u00a9\u00ca\u008a\u0114\u0104\u010e\u0122\u019e\u01f7"), Deobf.decrypt(":B<\u000b`\u00a7\u00a9\u009a\u00bd\u015b\u0146\u0101\u0122\u019c\u01b3\u0192\u01be\u0254\u0213\u0254\u0230\u0298\u029b\u02c1\u02be\u035e\u030d\u0313\u0301\u03c3\u03fd\u0399\u03ae\u039d\u0456\u0421\u041a\u047f\u0489\u04b1\u04e6\u04ea\u0540\u0524\u055a\u054d\u05c8\u05e7\u05dd\u05ad\u060e\u066d"), true));
    public final BooleanSetting deductOnPay = this.addSetting(new BooleanSetting(Deobf.decrypt("7I,\u001bq\u00b0\u00ec\u00a5\u00a7\u015b\u0139\u0102\u013a"), Deobf.decrypt("5M#\u000bB\u00a5\u00b5\u00ca\u00ba\u010e\u010b\u0117\u0131\u0191\u01f0\u01c0\u01ed\u025b\u0206\u025d\u023d\u028d\u02d5\u02db\u02b4\u030b\u0359\u030c\u0340\u03c9\u03b5\u0390\u03ab\u03d2\u0442\u046e\u0422\u0462\u04c7\u04b2\u04fe\u04af"), true));
    private String lastMoneyText;
    private double liveBalance;
    private int widthIndex;
    private int drawIndex;

    public FakeStatsModule() {
        super(Deobf.decrypt("5M#\u000bA\u00b0\u00ad\u009e\u00ba"), Deobf.decrypt("5M#\u000b2\u00a6\u00ad\u0086\u00a8\u0115\u010a\u0106\u0163\u01db\u01b3\u01d1\u01fa\u0212\u0205\u0254\u023e\u0295\u0290\u0282\u02b7\u031b\u0318\u0318\u0344\u03c2\u03f7\u0399\u03b8\u03cf\u044b\u0462\u044f\u047a\u04c0\u04a5\u04e2\u04e5\u0505\u0570\u0557\u050c\u05e2\u05e7\u05d8\u05ab\u063b\u0622\u0641"), Category.MISC);
        this.moneyLine.visibleWhen(() -> (Boolean)this.sidebar.get() != false && !((String)this.money.get()).isBlank());
        this.shardsLine.visibleWhen(() -> (Boolean)this.sidebar.get() != false && !((String)this.shards.get()).isBlank());
        this.killsLine.visibleWhen(() -> (Boolean)this.sidebar.get() != false && !((String)this.kills.get()).isBlank());
        this.deathsLine.visibleWhen(() -> (Boolean)this.sidebar.get() != false && !((String)this.deaths.get()).isBlank());
        this.playtimeLine.visibleWhen(() -> (Boolean)this.sidebar.get() != false && !((String)this.playtime.get()).isBlank());
    }

    @Override
    protected void onEnable() {
        this.lastMoneyText = null;
        this.syncFromSetting();
    }

    private static String lineLabel(double v) {
        return (int)v == 0 ? Deobf.decrypt("2Y<\u0001") : "Line " + (int)v;
    }

    private void syncFromSetting() {
        String text = (String)this.money.get();
        if (!Objects.equals(text, this.lastMoneyText)) {
            this.lastMoneyText = text;
            double parsed = Amounts.parse(text);
            this.liveBalance = Double.isNaN(parsed) ? 0.0 : Math.max(0.0, parsed);
        }
    }

    public double getLiveBalance() {
        this.syncFromSetting();
        return this.liveBalance;
    }

    public void deduct(double amount) {
        this.syncFromSetting();
        this.liveBalance = Math.max(0.0, this.liveBalance - Math.max(0.0, amount));
    }

    public void beginSidebar() {
        this.widthIndex = 0;
        this.drawIndex = 0;
    }

    public class_2561 rewriteForWidth(class_2561 comp) {
        return this.rewriteLine(comp, true);
    }

    public class_2561 rewriteForDraw(class_2561 comp) {
        return this.rewriteLine(comp, false);
    }

    private class_2561 rewriteLine(class_2561 comp, boolean widthPass) {
        int n;
        if (!this.isEnabled() || !((Boolean)this.sidebar.get()).booleanValue()) {
            return comp;
        }
        String text = FakeStatsModule.stripCodes(comp.getString());
        if (text.isBlank()) {
            return comp;
        }
        if (widthPass) {
            int n2 = this.widthIndex;
            n = n2;
            this.widthIndex = n2 + 1;
        } else {
            int n3 = this.drawIndex;
            n = n3;
            this.drawIndex = n3 + 1;
        }
        int line = n;
        return this.applyOverride(line, text.toLowerCase(Locale.ROOT), comp);
    }

    private class_2561 applyOverride(int line, String lower, class_2561 comp) {
        if (!((String)this.money.get()).isBlank() && FakeStatsModule.matches(this.moneyLine, line, lower, MONEY_KEYS)) {
            return Amounts.replaceNumberStyled(comp, Amounts.shortForm(this.getLiveBalance()));
        }
        if (!((String)this.shards.get()).isBlank() && FakeStatsModule.matches(this.shardsLine, line, lower, SHARD_KEYS)) {
            return Amounts.replaceNumberStyled(comp, ((String)this.shards.get()).trim());
        }
        if (!((String)this.kills.get()).isBlank() && FakeStatsModule.matches(this.killsLine, line, lower, KILL_KEYS)) {
            return Amounts.replaceNumberStyled(comp, ((String)this.kills.get()).trim());
        }
        if (!((String)this.deaths.get()).isBlank() && FakeStatsModule.matches(this.deathsLine, line, lower, DEATH_KEYS)) {
            return Amounts.replaceNumberStyled(comp, ((String)this.deaths.get()).trim());
        }
        if (!((String)this.playtime.get()).isBlank() && FakeStatsModule.matches(this.playtimeLine, line, lower, PLAYTIME_KEYS)) {
            return Amounts.replaceValueStyled(comp, ((String)this.playtime.get()).trim());
        }
        return comp;
    }

    private static boolean matches(SliderSetting lineSetting, int line, String lower, String[] keys) {
        int configured = lineSetting.getInt();
        if (configured > 0) {
            return line + 1 == configured;
        }
        return FakeStatsModule.containsAny(lower, keys);
    }

    private static boolean containsAny(String haystack, String[] keys) {
        for (String k : keys) {
            if (!haystack.contains(k)) continue;
            return true;
        }
        return false;
    }

    public boolean tryInterceptBalance(String command) {
        if (!this.isEnabled() || !((Boolean)this.balanceCommand.get()).booleanValue()) {
            return false;
        }
        String[] parts = command.trim().split(Deobf.decrypt("/_c"));
        if (parts.length == 0) {
            return false;
        }
        String name = parts[0];
        if (name.startsWith(Deobf.decrypt("\\"))) {
            name = name.substring(1);
        }
        if (!name.equalsIgnoreCase(Deobf.decrypt("\u0011M$")) && !name.equalsIgnoreCase(Deobf.decrypt("\u0011M$\u000f|\u00a7\u00a9"))) {
            return false;
        }
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null) {
            return false;
        }
        if (parts.length >= 2 && !parts[1].equalsIgnoreCase(mc.field_1724.method_7334().name())) {
            return false;
        }
        String amount = Amounts.shortForm(this.getLiveBalance());
        class_5250 line = class_2561.method_43473().method_10852((class_2561)class_2561.method_43470((String)Deobf.decrypt("*C=Nz\u00a5\u00ba\u008f\u00e9")).method_54663(0xAAAAAA)).method_10852((class_2561)class_2561.method_43470((String)amount).method_54663(0x55FF55));
        mc.field_1724.method_7353((class_2561)line, false);
        mc.field_1724.method_7353((class_2561)line, true);
        return true;
    }

    private static String stripCodes(String s) {
        return s == null ? Deobf.decrypt("") : s.replaceAll(Deobf.decrypt("[\u0013!G\u00b5\u009f\u00fc\u00c7\u00f0\u013a\u0144\u0125\u0108\u01dd\u01dc\u01e6\u01c3"), Deobf.decrypt(""));
    }
}

