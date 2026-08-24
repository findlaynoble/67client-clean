/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
 *  net.minecraft.class_1743
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_310
 *  net.minecraft.class_3966
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import java.util.Random;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1743;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_3966;

public class TriggerbotModule
extends Module {
    public final SliderSetting minDelay = this.addSetting(new SliderSetting(Deobf.decrypt(">E&NV\u00a1\u00a0\u008b\u00b0"), Deobf.decrypt(""), 9.0, 0.0, 20.0, 1.0));
    public final SliderSetting maxDelay = this.addSetting(new SliderSetting(Deobf.decrypt(">M0NV\u00a1\u00a0\u008b\u00b0"), Deobf.decrypt(""), 11.0, 0.0, 20.0, 1.0));
    public final BooleanSetting onlyItem = this.addSetting(new BooleanSetting(Deobf.decrypt("<B$\u00172\u008d\u00b8\u008f\u00a4"), Deobf.decrypt(""), false));
    public final ModeSetting itemFilter = this.addSetting(new ModeSetting(Deobf.decrypt(":X-\u00032\u0082\u00a5\u0086\u00bd\u011e\u011b"), Deobf.decrypt(""), Deobf.decrypt(" ['\u001cv"), Deobf.decrypt(" ['\u001cv"), Deobf.decrypt("2T-"), Deobf.decrypt(";M&\n")));
    public final BooleanSetting onlyCrit = this.addSetting(new BooleanSetting(Deobf.decrypt("<B$\u00172\u0087\u00be\u0083\u00bd"), Deobf.decrypt(""), false));
    public final BooleanSetting checkShield = this.addSetting(new BooleanSetting(Deobf.decrypt("0D-\ry\u00e4\u009f\u0082\u00a0\u011e\u0105\u0107"), Deobf.decrypt(""), false));
    private final Random random = new Random();
    private int tickCounter;
    private int currentDelay = 10;

    public TriggerbotModule() {
        super(Deobf.decrypt("'^!\tu\u00a1\u00be\u0088\u00a6\u010f"), Deobf.decrypt("2Y<\u0001?\u00a5\u00b8\u009e\u00a8\u0118\u0102\u0110\u0163\u0195\u01fd\u01c0\u01f7\u020f\u0218\u0250\u022f\u02d9\u029a\u02cc\u02fb\u031d\u030b\u0313\u0352\u03c3\u03fd\u0397\u03b0\u03cf"), Category.COMBAT);
    }

    @Override
    protected void onEnable() {
        this.tickCounter = 0;
        this.randomizeDelay();
    }

    @Override
    protected void onDisable() {
        this.tickCounter = 0;
    }

    @Override
    public void onTick() {
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null || client.field_1755 != null) {
            return;
        }
        if (client.field_1765 != null && client.field_1765.method_17783() == class_239.class_240.field_1331) {
            class_1297 target = ((class_3966)client.field_1765).method_17782();
            if (target instanceof class_1309 && target != client.field_1724) {
                class_1657 targetPlayer;
                if (((Boolean)this.onlyItem.get()).booleanValue()) {
                    if (this.itemFilter.is(Deobf.decrypt(" ['\u001cv")) && !this.isSword(client.field_1724.method_6047().method_7909())) {
                        return;
                    }
                    if (this.itemFilter.is(Deobf.decrypt("2T-")) && !(client.field_1724.method_6047().method_7909() instanceof class_1743)) {
                        return;
                    }
                    if (this.itemFilter.is(Deobf.decrypt(";M&\n")) && !client.field_1724.method_6047().method_7960()) {
                        return;
                    }
                }
                if (!(((Boolean)this.onlyCrit.get()).booleanValue() && (client.field_1724.method_24828() || client.field_1724.field_6017 <= 0.0) || ((Boolean)this.checkShield.get()).booleanValue() && target instanceof class_1657 && (targetPlayer = (class_1657)target).method_6039() || client.field_1724.method_7261(0.5f) < 1.0f)) {
                    ++this.tickCounter;
                    if (this.tickCounter >= this.currentDelay) {
                        client.field_1761.method_2918((class_1657)client.field_1724, target);
                        client.field_1724.method_6104(class_1268.field_5808);
                        this.tickCounter = 0;
                        this.randomizeDelay();
                    }
                }
            }
        } else {
            this.tickCounter = 0;
        }
    }

    private boolean isSword(class_1792 item) {
        return item == class_1802.field_8091 || item == class_1802.field_8528 || item == class_1802.field_8371 || item == class_1802.field_8845 || item == class_1802.field_8802 || item == class_1802.field_22022;
    }

    private void randomizeDelay() {
        int min = this.minDelay.getInt();
        int max = this.maxDelay.getInt();
        if (max < min) {
            max = min;
        }
        this.currentDelay = min + (max > min ? this.random.nextInt(max - min + 1) : 0);
    }
}

