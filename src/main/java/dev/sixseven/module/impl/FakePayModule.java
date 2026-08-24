/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_3414
 *  net.minecraft.class_3417
 *  net.minecraft.class_5250
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.FakeStatsModule;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.StringSetting;
import dev.sixseven.util.Amounts;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_5250;

public class FakePayModule
extends Module {
    private static final int WHITE = 0xFFFFFF;
    private static final int RED = 0xFF5555;
    public final StringSetting command = this.addSetting(new StringSetting(Deobf.decrypt("0C%\u0003s\u00aa\u00a8"), Deobf.decrypt("0C%\u0003s\u00aa\u00a8\u00ca\u00a7\u011a\u0104\u0106\u0163\u0184\u01fc\u0194\u01f6\u0212\u021b\u0254\u023f\u0292\u02d9\u0282\u02ac\u0317\u030d\u0314\u034e\u03c5\u03e1\u03d6\u03ad\u03d5\u044a\u046e\u041c\u0461\u04c8\u04a4\u04ef\u04af"), Deobf.decrypt("\u0003M1"), 32, Deobf.decrypt("\u0003M1")));
    public final ModeSetting feedback = this.addSetting(new ModeSetting(Deobf.decrypt("5I-\np\u00a5\u00af\u0081"), Deobf.decrypt("$D-\u001cw\u00e4\u00b8\u0082\u00ac\u015b\u010a\u010c\u012d\u0196\u01fa\u01c6\u01f3\u021a\u0205\u025c\u0233\u0297\u02d5\u02d1\u02b3\u0311\u030e\u030f\u030f"), Deobf.decrypt("1C<\u0006"), Deobf.decrypt("1C<\u0006"), Deobf.decrypt("2O<\u0007}\u00aa\u00ec\u00a8\u00a8\u0109"), Deobf.decrypt("0D)\u001a")));
    public final StringSetting currency = this.addSetting(new StringSetting(Deobf.decrypt("0Y:\u001cw\u00aa\u00af\u0093"), Deobf.decrypt(" U%\f}\u00a8\u00ec\u0088\u00ac\u011d\u0106\u0111\u0126\u01d0\u01e7\u01dc\u01fb\u025b\u0210\u0258\u0233\u028c\u029b\u02d6\u02fb\u0356\u031a\u0313\u034d\u03df\u03e7\u0393\u03bd\u0394\u0401"), Deobf.decrypt("W"), 4, Deobf.decrypt("W")));
    public final ColorSetting currencyColor = this.addSetting(new ColorSetting(Deobf.decrypt("0Y:\u001cw\u00aa\u00af\u0093\u00e9\u0138\u0106\u010f\u012c\u0182"), Deobf.decrypt("0C$\u0001`\u00e4\u00a3\u008c\u00e9\u010f\u0101\u0106\u0163\u0193\u01e6\u01c6\u01ec\u021e\u021f\u0256\u0225\u02d9\u0286\u02db\u02b6\u031c\u0316\u0310\u0301\u0398\u03d1\u0399\u03b7\u03c8\u045b\u041d\u0422\u045d\u0489\u04b5\u04eb\u04f4\u0540\u0524\u055a\u0555\u0584\u05e2\u05d6\u05a8\u060a\u0636\u0654\u0640\u06db\u06b7"), -16740609));
    public final BooleanSetting sounds = this.addSetting(new BooleanSetting(Deobf.decrypt(" C=\u0000v\u00b7"), Deobf.decrypt("?I>\u000b~\u00e9\u00b9\u009a\u00e9\u0114\u0107\u0143\u0130\u0185\u01f0\u01d7\u01fb\u0208\u0202\u0219\u027c\u028f\u029c\u02ce\u02b7\u031f\u031e\u0319\u0353\u0390\u03b2\u0398\u03b6\u039a\u040f\u0439\u0407\u0468\u04c7\u04f7\u04ee\u04f5\u0505\u0562\u0559\u0545\u05c8\u05f5\u059d"), true));
    public final BooleanSetting checkBalance = this.addSetting(new BooleanSetting(Deobf.decrypt("0D-\ry\u00e4\u008e\u008b\u00a5\u011a\u0107\u0100\u0126"), Deobf.decrypt("$E<\u00062\u0082\u00ad\u0081\u00ac\u0128\u011d\u0102\u0137\u0183\u01b3\u01db\u01f0\u0257\u0251\u0247\u0239\u029f\u0280\u02d1\u02be\u035e\u0309\u031d\u0358\u03c3\u03b5\u038f\u03b6\u03c8\u040f\u042d\u040e\u0463\u048e\u04a3\u04a7\u04e0\u0543\u0562\u0557\u055e\u05c0\u05a8"), true));
    public final BooleanSetting selfGuard = this.addSetting(new BooleanSetting(Deobf.decrypt(" I$\b?\u0094\u00ad\u0093\u00e9\u013c\u011c\u0102\u0131\u0194"), Deobf.decrypt("1@'\ry\u00e4\u00bc\u008b\u00b0\u0112\u0107\u0104\u0163\u0189\u01fc\u01c1\u01ec\u025b\u021e\u0242\u0232\u02d9\u029b\u02c3\u02b6\u031b\u0355\u035c\u034d\u03d9\u03fe\u0393\u03f9\u03c9\u0447\u042b\u044f\u047f\u04cc\u04b6\u04eb\u04a1\u0546\u056b\u0555\u0541\u05c5\u05e8\u05d7\u05e0"), true));

    public FakePayModule() {
        super(Deobf.decrypt("5M#\u000bB\u00a5\u00b5"), Deobf.decrypt("5M#\u000ba\u00e4\u00ad\u00ca\u00e6\u010b\u0108\u011a\u0163\u0196\u01fc\u01c6\u01be\u0218\u021d\u025c\u022c\u028a\u02d5\u22b6\u02fb\u031c\u0315\u0313\u0342\u03db\u03e6\u03d6\u03ad\u03d5\u044a\u046e\u041d\u0468\u04c8\u04bb\u04a7\u04e2\u054a\u0569\u0555\u054d\u05ca\u05e2"), Category.MISC);
    }

    public boolean tryIntercept(String rawCommand) {
        String target;
        if (!this.isEnabled() || rawCommand == null) {
            return false;
        }
        String[] parts = rawCommand.trim().split(Deobf.decrypt("/_c"));
        if (parts.length == 0) {
            return false;
        }
        String name = parts[0];
        if (name.startsWith(Deobf.decrypt("\\"))) {
            name = name.substring(1);
        }
        if ((target = ((String)this.command.get()).trim()).isEmpty() || !name.equalsIgnoreCase(target)) {
            return false;
        }
        this.handle(parts);
        return true;
    }

    private void handle(String[] parts) {
        boolean useStats;
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null) {
            return;
        }
        if (parts.length < 3) {
            this.show(mc, (class_2561)class_2561.method_43470((String)("Usage: /" + parts[0] + " <player> <amount>")).method_54663(0xFF5555));
            this.fail(mc);
            return;
        }
        String player = parts[1];
        double amount = Amounts.parse(parts[2]);
        if (Double.isNaN(amount) || amount <= 0.0) {
            this.show(mc, (class_2561)class_2561.method_43470((String)("Invalid amount: " + parts[2])).method_54663(0xFF5555));
            this.fail(mc);
            return;
        }
        if (((Boolean)this.selfGuard.get()).booleanValue() && player.equalsIgnoreCase(mc.field_1724.method_7334().name())) {
            this.show(mc, (class_2561)class_2561.method_43470((String)Deobf.decrypt("*C=Nq\u00a5\u00a2\u00cd\u00bd\u015b\u0119\u0102\u013a\u01d0\u01ea\u01db\u01eb\u0209\u0202\u0250\u0230\u029f\u02d4")).method_54663(0xFF5555));
            this.fail(mc);
            return;
        }
        FakeStatsModule stats = this.stats();
        boolean bl = useStats = stats != null && stats.isEnabled() && (Boolean)stats.deductOnPay.get() != false;
        if (useStats && ((Boolean)this.checkBalance.get()).booleanValue() && stats.getLiveBalance() < amount) {
            this.show(mc, (class_2561)class_2561.method_43470((String)Deobf.decrypt("*C=Nv\u00ab\u00a2\u00cd\u00bd\u015b\u0101\u0102\u0135\u0195\u01b3\u01d1\u01f0\u0214\u0204\u0252\u0234\u02d9\u0298\u02cd\u02b5\u031b\u0300\u035d")).method_54663(0xFF5555));
            this.fail(mc);
            return;
        }
        if (useStats) {
            stats.deduct(amount);
        }
        class_5250 line = class_2561.method_43473().method_10852((class_2561)class_2561.method_43470((String)("You paid " + player)).method_54663(0xFFFFFF));
        String symbol = (String)this.currency.get();
        if (!symbol.isEmpty()) {
            line.method_10852((class_2561)class_2561.method_43470((String)(" " + symbol)).method_54663((Integer)this.currencyColor.get() & 0xFFFFFF)).method_10852((class_2561)class_2561.method_43470((String)Amounts.shortForm(amount)).method_54663(0xFFFFFF));
        } else {
            line.method_10852((class_2561)class_2561.method_43470((String)(" " + Amounts.shortForm(amount))).method_54663(0xFFFFFF));
        }
        this.show(mc, (class_2561)line);
        this.succeed(mc);
    }

    private void show(class_310 mc, class_2561 component) {
        if (mc.field_1724 == null) {
            return;
        }
        String mode = (String)this.feedback.get();
        if (mode.equals(Deobf.decrypt("2O<\u0007}\u00aa\u00ec\u00a8\u00a8\u0109")) || mode.equals(Deobf.decrypt("1C<\u0006"))) {
            mc.field_1724.method_7353(component, true);
        }
        if (mode.equals(Deobf.decrypt("0D)\u001a")) || mode.equals(Deobf.decrypt("1C<\u0006"))) {
            mc.field_1724.method_7353(component, false);
        }
    }

    private void succeed(class_310 mc) {
        if (((Boolean)this.sounds.get()).booleanValue()) {
            this.play(mc, class_3417.field_14709, 1.0f);
        }
    }

    private void fail(class_310 mc) {
        if (((Boolean)this.sounds.get()).booleanValue()) {
            this.play(mc, class_3417.field_15008, 1.0f);
        }
    }

    private void play(class_310 mc, class_3414 event, float pitch) {
        mc.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)event, (float)pitch, (float)1.0f));
    }

    private FakeStatsModule stats() {
        ModuleManager modules = SixSevenClient.modules();
        return modules == null ? null : modules.fakeStats;
    }
}

