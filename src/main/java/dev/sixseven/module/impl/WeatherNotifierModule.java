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
 *  net.minecraft.class_638
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.notification.NotificationManager;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_638;

public class WeatherNotifierModule
extends Module {
    public final BooleanSetting rain = this.addSetting(new BooleanSetting(Deobf.decrypt("!M!\u0000"), Deobf.decrypt("2B&\u0001g\u00aa\u00af\u008f\u00e9\u010c\u0101\u0106\u012d\u01d0\u01e1\u01d5\u01f7\u0215\u0251\u0246\u0228\u0298\u0287\u02d6\u02a8\u0351\u030a\u0308\u034e\u03c0\u03e6"), true));
    public final BooleanSetting thunder = this.addSetting(new BooleanSetting(Deobf.decrypt("'D=\u0000v\u00a1\u00be"), Deobf.decrypt("2B&\u0001g\u00aa\u00af\u008f\u00e9\u010c\u0101\u0106\u012d\u01d0\u01f2\u0194\u01ea\u0213\u0204\u025b\u0238\u029c\u0287\u02d1\u02af\u0311\u030b\u0311\u0301\u03c3\u03e1\u0397\u03ab\u03c9\u045c\u0461\u041c\u0479\u04c6\u04a7\u04f4"), true));
    public final BooleanSetting sound = this.addSetting(new BooleanSetting(Deobf.decrypt(" C=\u0000v"), Deobf.decrypt("#@)\u00172\u00a5\u00ec\u009a\u00a0\u0115\u010e\u0143\u012c\u019e\u01b3\u01d1\u01ff\u0218\u0219\u0215\u023f\u0291\u0294\u02cc\u02bc\u031b"), true));
    public final ModeSetting output = this.addSetting(new ModeSetting(Deobf.decrypt("<Y<\u001eg\u00b0"), Deobf.decrypt(";C?Nf\u00ac\u00a9\u00ca\u00aa\u0113\u0108\u010d\u0124\u0195\u01b3\u01dd\u01ed\u025b\u0202\u025d\u0233\u028e\u029b"), Deobf.decrypt("=C<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d"), Deobf.decrypt("=C<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d"), Deobf.decrypt("0D)\u001a"), Deobf.decrypt("2O<\u0007}\u00aa\u00ec\u00a8\u00a8\u0109")));
    private Boolean lastRaining;
    private Boolean lastThundering;

    public WeatherNotifierModule() {
        super(Deobf.decrypt("$I)\u001az\u00a1\u00be\u00a4\u00a6\u010f\u0100\u0105\u012a\u0195\u01e1"), Deobf.decrypt("'D-\u0003w\u00a0\u00ec\u009e\u00a6\u011a\u011a\u0117\u0163\u0187\u01fb\u01d1\u01f0\u025b\u0205\u025d\u0239\u02d9\u0282\u02c7\u02ba\u030a\u0311\u0319\u0353\u0390\u03f6\u039e\u03b8\u03d3\u0448\u042b\u041c"), Category.MISC);
    }

    @Override
    protected void onEnable() {
        this.lastRaining = null;
        this.lastThundering = null;
    }

    @Override
    public void onTick() {
        class_310 mc = class_310.method_1551();
        class_638 level = mc.field_1687;
        if (level == null || mc.field_1724 == null) {
            return;
        }
        boolean raining = level.method_8419();
        boolean thundering = level.method_8546();
        if (this.lastRaining == null || this.lastThundering == null) {
            this.lastRaining = raining;
            this.lastThundering = thundering;
            return;
        }
        if (thundering != this.lastThundering) {
            this.lastThundering = thundering;
            if (((Boolean)this.thunder.get()).booleanValue()) {
                if (thundering) {
                    this.notify(mc, Deobf.decrypt("'D=\u0000v\u00a1\u00be\u0099\u00bd\u0114\u011b\u010e"), Deobf.decrypt("2\f;\u001a}\u00b6\u00a1\u00ca\u00bb\u0114\u0105\u010f\u0130\u01d0\u01fa\u01da"), NotificationManager.Weather.THUNDER, true);
                } else {
                    this.notify(mc, Deobf.decrypt(" X'\u001c\u007f\u00e4\u00af\u0086\u00ac\u011a\u011b\u0106\u0127"), Deobf.decrypt("'D-Nf\u00ac\u00b9\u0084\u00ad\u011e\u011b\u0143\u012b\u0191\u01e0\u0194\u01ee\u021a\u0202\u0246\u0239\u029d"), NotificationManager.Weather.CLEAR, false);
                }
            }
        }
        if (raining != this.lastRaining) {
            this.lastRaining = raining;
            if (((Boolean)this.rain.get()).booleanValue() && !thundering) {
                if (raining) {
                    this.notify(mc, Deobf.decrypt("!M!\u0000"), Deobf.decrypt("!M!\u00002\u00b7\u00b8\u008b\u00bb\u010f\u011a\u0143\u0137\u019f\u01b3\u01d2\u01ff\u0217\u021d"), NotificationManager.Weather.RAIN, true);
                } else {
                    this.notify(mc, Deobf.decrypt(" G!\u000ba\u00e4\u00af\u0086\u00ac\u011a\u011b\u0106\u0127"), Deobf.decrypt("'D-N`\u00a5\u00a5\u0084\u00e9\u0113\u0108\u0110\u0163\u0183\u01e7\u01db\u01ee\u020b\u0214\u0251"), NotificationManager.Weather.CLEAR, false);
                }
            }
        }
    }

    private void notify(class_310 mc, String title, String subtitle, NotificationManager.Weather weather, boolean started) {
        if (mc.field_1724 == null) {
            return;
        }
        if (this.output.is(Deobf.decrypt("=C<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d"))) {
            if (SixSevenClient.notifications() != null) {
                SixSevenClient.notifications().pushWeather(title, subtitle, weather, started);
            }
        } else {
            boolean actionBar = this.output.is(Deobf.decrypt("2O<\u0007}\u00aa\u00ec\u00a8\u00a8\u0109"));
            mc.field_1724.method_7353((class_2561)class_2561.method_43470((String)("\u00a7d[67] \u00a7f" + title + " \u2014 " + subtitle)), actionBar);
        }
        if (((Boolean)this.sound.get()).booleanValue()) {
            float pitch = started ? 1.2f : 0.8f;
            mc.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)((class_3414)class_3417.field_14793.comp_349()), (float)pitch, (float)0.6f));
        }
    }
}

