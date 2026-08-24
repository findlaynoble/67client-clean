/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_1743
 *  net.minecraft.class_1799
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
import net.minecraft.class_1657;
import net.minecraft.class_1743;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_3966;

public class ShieldBreakerModule
extends Module {
    public final BooleanSetting switchBack = this.addSetting(new BooleanSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00a8\u00a8\u0118\u0102"), Deobf.decrypt(""), true));
    public final ModeSetting axePriority = this.addSetting(new ModeSetting(Deobf.decrypt("2T-NB\u00b6\u00a5\u0085\u00bb\u0112\u011d\u011a"), Deobf.decrypt(""), Deobf.decrypt("1I;\u001a"), Deobf.decrypt("1I;\u001a"), Deobf.decrypt("=I)\u001cw\u00b7\u00b8")));
    public final SliderSetting switchDelay = this.addSetting(new SliderSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00ae\u00ac\u0117\u0108\u011a"), Deobf.decrypt(""), 150.0, 0.0, 500.0, 10.0));
    private int previousSlot = -1;
    private int tickCounter;
    private boolean waitingToSwapBack;

    public ShieldBreakerModule() {
        super(Deobf.decrypt(" D!\u000b~\u00a0\u00ec\u00a8\u00bb\u011e\u0108\u0108\u0126\u0182"), Deobf.decrypt("2Y<\u0001?\u00b7\u00bb\u0083\u00bd\u0118\u0101\u0143\u0137\u019f\u01b3\u01d5\u01e6\u021e\u0251\u0242\u0234\u029c\u029b\u0282\u02af\u031f\u030b\u031b\u0344\u03c4\u03b5\u039f\u03aa\u039d\u044d\u0422\u0400\u046e\u04c2\u04be\u04e9\u04e6"), Category.COMBAT);
    }

    @Override
    protected void onEnable() {
        this.resetState();
    }

    @Override
    protected void onDisable() {
        this.resetState();
    }

    @Override
    public void onTick() {
        int axeSlot;
        class_1657 targetPlayer;
        class_1297 class_12972;
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null) {
            return;
        }
        if (this.waitingToSwapBack) {
            ++this.tickCounter;
            int delayTicks = Math.max(1, this.switchDelay.getInt() / 50);
            if (this.tickCounter >= delayTicks) {
                InventoryHelper.swap(this.previousSlot);
                this.resetState();
            }
        } else if (client.field_1765 != null && client.field_1765.method_17783() == class_239.class_240.field_1331 && (class_12972 = ((class_3966)client.field_1765).method_17782()) instanceof class_1657 && (targetPlayer = (class_1657)class_12972).method_6039() && !(client.field_1724.method_6047().method_7909() instanceof class_1743) && (axeSlot = this.findAxeSlot(client)) != -1) {
            this.previousSlot = client.field_1724.method_31548().method_67532();
            InventoryHelper.swap(axeSlot);
            client.field_1761.method_2918((class_1657)client.field_1724, (class_1297)targetPlayer);
            client.field_1724.method_6104(class_1268.field_5808);
            if (((Boolean)this.switchBack.get()).booleanValue()) {
                this.waitingToSwapBack = true;
                this.tickCounter = 0;
            } else {
                this.previousSlot = -1;
            }
        }
    }

    private int findAxeSlot(class_310 client) {
        if (this.axePriority.is(Deobf.decrypt("=I)\u001cw\u00b7\u00b8"))) {
            for (int i = 0; i < 9; ++i) {
                if (!(client.field_1724.method_31548().method_5438(i).method_7909() instanceof class_1743)) continue;
                return i;
            }
            return -1;
        }
        int bestSlot = -1;
        float bestDamage = -1.0f;
        for (int i = 0; i < 9; ++i) {
            float damage;
            class_1799 stack = client.field_1724.method_31548().method_5438(i);
            if (!(stack.method_7909() instanceof class_1743) || !((damage = this.getAxeTier(stack)) > bestDamage)) continue;
            bestDamage = damage;
            bestSlot = i;
        }
        return bestSlot;
    }

    private float getAxeTier(class_1799 stack) {
        if (stack.method_31574(class_1802.field_22025)) {
            return 5.0f;
        }
        if (stack.method_31574(class_1802.field_8556)) {
            return 4.0f;
        }
        if (stack.method_31574(class_1802.field_8475)) {
            return 3.0f;
        }
        if (stack.method_31574(class_1802.field_8825)) {
            return 2.0f;
        }
        if (stack.method_31574(class_1802.field_8062)) {
            return 1.0f;
        }
        return stack.method_31574(class_1802.field_8406) ? 0.0f : -1.0f;
    }

    private void resetState() {
        this.previousSlot = -1;
        this.tickCounter = 0;
        this.waitingToSwapBack = false;
    }
}

