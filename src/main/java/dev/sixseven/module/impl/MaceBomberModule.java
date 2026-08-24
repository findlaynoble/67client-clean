/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1309
 *  net.minecraft.class_1657
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
import dev.sixseven.util.InventoryHelper;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1802;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_3966;

public class MaceBomberModule
extends Module {
    public final SliderSetting height = this.addSetting(new SliderSetting(Deobf.decrypt(";I!\tz\u00b0"), Deobf.decrypt(">E&\u0007\u007f\u00b1\u00a1\u00ca\u00af\u011a\u0105\u010f\u0163\u0194\u01fa\u01c7\u01ea\u021a\u021f\u0256\u0239\u02d9\u0297\u02c7\u02bd\u0311\u030b\u0319\u0301\u03d9\u03e1\u03d6\u03aa\u03d0\u044e\u043d\u0407\u0468\u04da"), 8.0, 3.0, 30.0, 1.0, Deobf.decrypt("\u001e")));
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt("7E>\u000b2\u00b7\u00b8\u0093\u00a5\u011e"), Deobf.decrypt("7E:\u000bq\u00b0"), Deobf.decrypt("7E:\u000bq\u00b0"), Deobf.decrypt(" \\!\u001cs\u00a8"), Deobf.decrypt("7I$\u000fk\u00a1\u00a8")));
    public final BooleanSetting autoMace = this.addSetting(new BooleanSetting(Deobf.decrypt("2Y<\u00012\u0089\u00ad\u0089\u00ac"), Deobf.decrypt(" [)\u001e2\u00b0\u00a3\u00ca\u00a8\u015b\u0104\u0102\u0120\u0195\u01b3\u01dd\u01f0\u025b\u0208\u025a\u0229\u028b\u02d5\u02ca\u02b4\u030a\u031b\u031d\u0353\u0390\u03f7\u0393\u03bf\u03d2\u045d\u042b\u044f\u047e\u04c4\u04b6\u04f4\u04e9\u054c\u056a\u055f"), true));
    public final BooleanSetting switchBack = this.addSetting(new BooleanSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00a8\u00a8\u0118\u0102"), Deobf.decrypt("!I<\u001b`\u00aa\u00ec\u009e\u00a6\u015b\u011d\u010b\u0126\u01d0\u01e3\u01c6\u01fb\u020d\u0218\u025a\u0229\u028a\u02d5\u02d1\u02b7\u0311\u030d\u035c\u0340\u03d6\u03e1\u0393\u03ab\u039d\u045b\u0426\u040a\u042d\u04da\u04ba\u04e6\u04f2\u054d"), false));
    private int spiralDir = 1;
    private int spiralTimer;
    private int cooldown;
    private int returnSlot = -1;
    private boolean strafing;

    public MaceBomberModule() {
        super(Deobf.decrypt(">M+\u000b2\u0086\u00a3\u0087\u00ab\u011e\u011b"), Deobf.decrypt("'E%\u000ba\u00e4\u00ad\u00ca\u00af\u010e\u0105\u010f\u013a\u01dd\u01f0\u01dc\u01ff\u0209\u0216\u0250\u0238\u02d9\u0298\u02c3\u02b8\u031b\u0359\u030f\u034c\u03d1\u03e6\u039e\u03f9\u03d2\u0441\u046e\u041b\u0465\u04cc\u04f7\u04f0\u04e0\u055c\u0524\u055c\u0543\u05d3\u05e8"), Category.COMBAT);
    }

    @Override
    protected void onDisable() {
        this.releaseStrafe();
        this.cooldown = 0;
        this.returnSlot = -1;
    }

    @Override
    public void onTick() {
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null || client.field_1755 != null) {
            this.releaseStrafe();
            return;
        }
        if (this.cooldown > 0) {
            --this.cooldown;
        }
        if (client.field_1724.method_24828()) {
            this.releaseStrafe();
            this.cooldown = 0;
            this.returnSlot = -1;
            return;
        }
        class_1309 target = this.crosshairTarget(client);
        if (target == null) {
            this.releaseStrafe();
            return;
        }
        if (this.mode.is(Deobf.decrypt(" \\!\u001cs\u00a8"))) {
            this.spiral(client);
        } else {
            this.releaseStrafe();
        }
        if (this.cooldown > 0) {
            return;
        }
        if (client.field_1724.field_6017 < (double)this.height.getFloat()) {
            return;
        }
        if (this.mode.is(Deobf.decrypt("7I$\u000fk\u00a1\u00a8")) && client.field_1724.method_18798().field_1351 > -0.5) {
            return;
        }
        if (!client.field_1724.method_6047().method_31574(class_1802.field_49814)) {
            if (!((Boolean)this.autoMace.get()).booleanValue()) {
                return;
            }
            if (this.returnSlot < 0) {
                this.returnSlot = client.field_1724.method_31548().method_67532();
            }
            if (!InventoryHelper.swapToItem(class_1802.field_49814)) {
                return;
            }
        }
        if (client.field_1724.method_7261(0.5f) < 1.0f) {
            return;
        }
        client.field_1761.method_2918((class_1657)client.field_1724, (class_1297)target);
        client.field_1724.method_6104(class_1268.field_5808);
        this.cooldown = 6;
        if (((Boolean)this.switchBack.get()).booleanValue() && this.returnSlot >= 0) {
            InventoryHelper.swap(this.returnSlot);
        }
        this.returnSlot = -1;
    }

    private class_1309 crosshairTarget(class_310 client) {
        if (client.field_1765 == null || client.field_1765.method_17783() != class_239.class_240.field_1331) {
            return null;
        }
        class_1297 class_12972 = ((class_3966)client.field_1765).method_17782();
        if (!(class_12972 instanceof class_1309)) {
            return null;
        }
        class_1309 living = (class_1309)class_12972;
        if (living == client.field_1724 || !living.method_5805()) {
            return null;
        }
        return living;
    }

    private void spiral(class_310 client) {
        if (--this.spiralTimer <= 0) {
            this.spiralDir = -this.spiralDir;
            this.spiralTimer = 6 + client.field_1724.field_6012 % 7;
        }
        client.field_1690.field_1913.method_23481(this.spiralDir < 0);
        client.field_1690.field_1849.method_23481(this.spiralDir > 0);
        this.strafing = true;
    }

    private void releaseStrafe() {
        if (!this.strafing) {
            return;
        }
        class_310 client = class_310.method_1551();
        if (client.field_1690 != null) {
            client.field_1690.field_1913.method_23481(false);
            client.field_1690.field_1849.method_23481(false);
        }
        this.strafing = false;
    }
}

