/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1713
 *  net.minecraft.class_1802
 *  net.minecraft.class_310
 *  net.minecraft.class_490
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_490;

public class AutoInventoryTotemModule
extends Module {
    public final SliderSetting delay = this.addSetting(new SliderSetting(Deobf.decrypt("7I$\u000fk"), Deobf.decrypt(""), 4.0, 1.0, 40.0, 1.0));
    public final BooleanSetting forceTotem = this.addSetting(new BooleanSetting(Deobf.decrypt("5C:\rw\u00e4\u0098\u0085\u00bd\u011e\u0104"), Deobf.decrypt(""), false));
    public final BooleanSetting hotbarTotem = this.addSetting(new BooleanSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00be\u00a6\u010f\u010c\u010e"), Deobf.decrypt(""), false));
    public final SliderSetting hotbarSlot = this.addSetting(new SliderSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00b9\u00a5\u0114\u011d"), Deobf.decrypt(""), 1.0, 1.0, 9.0, 1.0));
    public final SliderSetting hotbarDelay = this.addSetting(new SliderSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00ae\u00ac\u0117\u0108\u011a"), Deobf.decrypt(""), 4.0, 1.0, 40.0, 1.0));
    private int tickCounter;

    public AutoInventoryTotemModule() {
        super(Deobf.decrypt(":B>NF\u00ab\u00b8\u008f\u00a4"), Deobf.decrypt(">C>\u000ba\u00e4\u00b8\u0085\u00bd\u011e\u0104\u0110\u0163\u019f\u01fd\u01d8\u01e7\u025b\u0206\u025d\u0235\u0295\u0290\u0282\u02b2\u0310\u030f\u0319\u034f\u03c4\u03fa\u0384\u03a0\u039d\u0407\u040b\u0446\u042d\u04c0\u04a4\u04a7\u04ee\u0555\u0561\u0556\u050c\u25b0\u05a6\u05d0\u05a2\u0602\u0620\u0653\u0619\u0690\u06f8\u06c9\u06dd\u0709\u073a\u071d\u0752\u07d4\u0790\u07d3\u079f\u07a6\u0856\u0812\u080e\u0824\u08cb\u08bb\u0890\u08f7"), Category.COMBAT);
    }

    @Override
    protected void onEnable() {
        this.tickCounter = 0;
    }

    @Override
    protected void onDisable() {
        this.tickCounter = 0;
    }

    @Override
    public void onTick() {
        int totemIdx;
        int wait;
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null) {
            return;
        }
        if (!(client.field_1755 instanceof class_490)) {
            return;
        }
        ++this.tickCounter;
        int n = wait = (Boolean)this.hotbarTotem.get() != false && this.needsHotbarWork(client) && !this.needsOffhandWork(client) ? this.hotbarDelay.getInt() : this.delay.getInt();
        if (this.tickCounter < wait) {
            return;
        }
        this.tickCounter = 0;
        if (((Boolean)this.hotbarTotem.get()).booleanValue() && this.needsHotbarWork(client)) {
            int hotbarIndex;
            int source = AutoInventoryTotemModule.findTotemInMainInventory(client);
            if (source != -1 && (hotbarIndex = this.hotbarSlot.getInt() - 1) >= 0 && hotbarIndex <= 8) {
                this.performHotbarSwap(client, source, hotbarIndex);
            }
        } else if (this.needsOffhandWork(client) && (totemIdx = AutoInventoryTotemModule.findTotemForOffhand(client)) != -1) {
            this.performOffhandSwap(client, totemIdx);
        }
    }

    private boolean needsOffhandWork(class_310 client) {
        boolean hasTotemOffhand = client.field_1724.method_6079().method_31574(class_1802.field_8288);
        return (Boolean)this.forceTotem.get() != false || !hasTotemOffhand;
    }

    private boolean needsHotbarWork(class_310 client) {
        if (!((Boolean)this.hotbarTotem.get()).booleanValue()) {
            return false;
        }
        int slot = this.hotbarSlot.getInt() - 1;
        return !client.field_1724.method_31548().method_5438(slot).method_31574(class_1802.field_8288);
    }

    private static int findTotemInMainInventory(class_310 client) {
        for (int i = 9; i < 36; ++i) {
            if (!client.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_8288)) continue;
            return i;
        }
        return -1;
    }

    private static int findTotemInHotbar(class_310 client) {
        for (int i = 0; i < 9; ++i) {
            if (!client.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_8288)) continue;
            return i;
        }
        return -1;
    }

    private static int findTotemForOffhand(class_310 client) {
        int main = AutoInventoryTotemModule.findTotemInMainInventory(client);
        return main != -1 ? main : AutoInventoryTotemModule.findTotemInHotbar(client);
    }

    private static int toScreenSlot(int invIndex) {
        return invIndex < 9 ? invIndex + 36 : invIndex;
    }

    private void performOffhandSwap(class_310 client, int totemInvIndex) {
        int syncId = client.field_1724.field_7512.field_7763;
        int screenSlot = AutoInventoryTotemModule.toScreenSlot(totemInvIndex);
        client.field_1761.method_2906(syncId, screenSlot, 0, class_1713.field_7790, (class_1657)client.field_1724);
        client.field_1761.method_2906(syncId, 45, 0, class_1713.field_7790, (class_1657)client.field_1724);
        client.field_1761.method_2906(syncId, screenSlot, 0, class_1713.field_7790, (class_1657)client.field_1724);
    }

    private void performHotbarSwap(class_310 client, int sourceInvIndex, int hotbarIndex) {
        int syncId = client.field_1724.field_7512.field_7763;
        client.field_1761.method_2906(syncId, sourceInvIndex, hotbarIndex, class_1713.field_7791, (class_1657)client.field_1724);
    }
}

