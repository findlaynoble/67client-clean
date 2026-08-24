/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.settings.StringSetting;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_310;

public class AutoTpaModule
extends Module {
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt("$D!\rz\u00e4\u00be\u008f\u00b8\u010e\u010c\u0110\u0137\u01d0\u01e7\u01db\u01be\u0208\u0214\u025b\u0238\u02d7"), Deobf.decrypt("'|\t"), Deobf.decrypt("'|\t"), Deobf.decrypt("'|\t&w\u00b6\u00a9")));
    public final StringSetting target = this.addSetting(new StringSetting(Deobf.decrypt("'M:\tw\u00b0"), Deobf.decrypt("#@)\u0017w\u00b6\u00ec\u009e\u00a6\u015b\u011a\u0106\u012d\u0194\u01b3\u01c0\u01f6\u021e\u0251\u0247\u0239\u0288\u0280\u02c7\u02a8\u030a\u0359\u0308\u034e\u039e"), Deobf.decrypt(""), 32, Deobf.decrypt(" X-\u0018w")));
    public final SliderSetting delay = this.addSetting(new SliderSetting(Deobf.decrypt("7I$\u000fk"), Deobf.decrypt("'E%\u000b2\u00a6\u00a9\u009e\u00be\u011e\u010c\u010d\u0163\u0182\u01f6\u01c5\u01eb\u021e\u0202\u0241\u022f\u02d9\u22e1\u0282\u02b7\u0311\u030e\u0319\u0353\u0390\u03fc\u0385\u03f9\u03db\u044e\u043d\u041b\u0468\u04db\u04f9"), 2000.0, 250.0, 10000.0, 50.0, Deobf.decrypt("\u001e_")));
    public final SliderSetting humanize = this.addSetting(new SliderSetting(Deobf.decrypt(";Y%\u000f|\u00ad\u00b6\u008f"), Deobf.decrypt("!M&\n}\u00a9\u00ec\u00c1\u00e6\u0156\u0149\u0110\u0134\u0199\u01fd\u01d3\u01be\u0214\u021f\u0215\u0239\u0298\u0296\u02ca\u02fb\u031a\u031c\u0310\u0340\u03c9\u03b5\u0385\u03b6\u039d\u045b\u0426\u040a\u042d\u04dd\u04be\u04ea\u04e8\u054b\u0563\u0518\u0545\u05d7\u05e8\u0594\u05ba\u064b\u0622\u0618\u0652\u069b\u06e1\u06df\u06dc\u0741\u0736\u075f\u0753\u07cf\u07c9\u079f\u0781\u07a2\u0841\u085e\u0803\u0867\u08de\u08ab\u0896\u08ee\u092b\u092e\u094e\u0963\u09cd\u09cf\u09c4\u09f9\u0a01\u0a6b\u0a48\u0a2a"), 25.0, 0.0, 60.0, 5.0, Deobf.decrypt("V")));
    public final BooleanSetting notify = this.addSetting(new BooleanSetting(Deobf.decrypt("=C<\u0007t\u00bd"), Deobf.decrypt(" D'\u00192\u00a5\u00ec\u0084\u00a6\u010f\u0100\u0105\u012a\u0193\u01f2\u01c0\u01f7\u0214\u021f\u0215\u0239\u0298\u0296\u02ca\u02fb\u030a\u0310\u0311\u0344\u0390\u03f4\u03d6\u03ab\u03d8\u045e\u043b\u040a\u047e\u04dd\u04f7\u04ee\u04f2\u0505\u0577\u055d\u0542\u05d0\u05a8"), false));
    private long nextSendAtMs = -1L;
    private String lastSent;

    public AutoTpaModule() {
        super(Deobf.decrypt("2Y<\u0001F\u0094\u008d"), Deobf.decrypt(" \\)\u0003a\u00e4\u0098\u00ba\u0088\u015b\u011b\u0106\u0132\u0185\u01f6\u01c7\u01ea\u0208\u0251\u0254\u0228\u02d9\u0294\u0282\u02af\u031f\u030b\u031b\u0344\u03c4\u03b5\u0399\u03b7\u039d\u044e\u046e\u0407\u0478\u04c4\u04b6\u04e9\u04e8\u055f\u0561\u055c\u050c\u05d0\u05ef\u05de\u05ab\u0619\u066d"), Category.MISC);
    }

    @Override
    protected void onEnable() {
        if (((String)this.target.get()).trim().isEmpty()) {
            SixSevenClient.notifications().pushInfo(Deobf.decrypt("2Y<\u0001F\u0094\u008d\u00ca~\u015b\u011a\u0106\u0137\u01d0\u01f2\u0194\u01ca\u021a\u0203\u0252\u0239\u028d\u02d5\u02c4\u02b2\u030c\u030a\u0308"));
        }
        this.lastSent = null;
        this.nextSendAtMs = 0L;
    }

    @Override
    protected void onDisable() {
        this.nextSendAtMs = -1L;
    }

    @Override
    public void onTick() {
        if (this.nextSendAtMs < 0L) {
            return;
        }
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.field_1724.field_3944 == null) {
            return;
        }
        if (System.currentTimeMillis() < this.nextSendAtMs) {
            return;
        }
        String name = ((String)this.target.get()).trim();
        if (name.isEmpty()) {
            this.nextSendAtMs = this.scheduleNext();
            return;
        }
        String cmd = (this.mode.is(Deobf.decrypt("'|\t&w\u00b6\u00a9")) ? Deobf.decrypt("\u0007\\)\u0006w\u00b6\u00a9\u00ca") : Deobf.decrypt("\u0007\\)N")) + name;
        mc.field_1724.field_3944.method_45730(cmd);
        this.lastSent = cmd;
        if (((Boolean)this.notify.get()).booleanValue()) {
            SixSevenClient.notifications().pushInfo("AutoTPA \u00b7 /" + cmd);
        }
        this.nextSendAtMs = this.scheduleNext();
    }

    public String lastSent() {
        return this.lastSent;
    }

    private long scheduleNext() {
        double base = (Double)this.delay.get();
        double j = (Double)this.humanize.get() / 100.0;
        double factor = j <= 0.0 ? 1.0 : 1.0 + (ThreadLocalRandom.current().nextDouble() * 2.0 - 1.0) * j;
        long wait = Math.max(0L, Math.round(base * factor));
        return System.currentTimeMillis() + wait;
    }
}

