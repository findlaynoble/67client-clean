/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1802
 *  net.minecraft.class_310
 *  net.minecraft.class_490
 *  net.minecraft.class_746
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.InventoryHelper;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_490;
import net.minecraft.class_746;

public class AutoTotemModule
extends Module {
    public final SliderSetting delay = this.addSetting(new SliderSetting(Deobf.decrypt("7I$\u000fk"), Deobf.decrypt(""), 4.0, 1.0, 40.0, 1.0));
    public final BooleanSetting forceTotem = this.addSetting(new BooleanSetting(Deobf.decrypt("5C:\rw\u00e4\u0098\u0085\u00bd\u011e\u0104"), Deobf.decrypt(""), false));
    public final BooleanSetting hotbarTotem = this.addSetting(new BooleanSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00be\u00a6\u010f\u010c\u010e"), Deobf.decrypt(""), false));
    public final SliderSetting hotbarSlot = this.addSetting(new SliderSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00b9\u00a5\u0114\u011d"), Deobf.decrypt(""), 1.0, 1.0, 9.0, 1.0));
    public final SliderSetting hotbarDelay = this.addSetting(new SliderSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00ae\u00ac\u0117\u0108\u011a"), Deobf.decrypt(""), 4.0, 1.0, 40.0, 1.0));
    private int tickCounter;
    private int pendingHotbarSlot = -1;

    public AutoTotemModule() {
        super(Deobf.decrypt("2Y<\u00012\u0090\u00a3\u009e\u00ac\u0116"), Deobf.decrypt("'C<\u000b\u007f\u00e4\u00b8\u0085\u00e9\u0114\u010f\u0105\u012b\u0191\u01fd\u01d0\u01b1\u0213\u021e\u0241\u023e\u0298\u0287\u0282\u22cf\u035e\u0311\u0313\u0355\u03d2\u03f4\u0384\u03f9\u03ce\u044a\u0422\u040a\u046e\u04dd\u04f7\u04ac\u04a1\u0563\u0524\u054b\u055b\u05c5\u05f6\u0593\u05e6\u0640\u0663\u066b\u0663\u06b3\u06c9\u069a\u06de\u071f\u0779\u0750\u071c\u07d6\u0785\u079a\u0786\u07e9\u084d\u0810\u081c\u0829\u08dd\u08a6\u0881\u08f6\u096a\u092c\u0905\u0926\u0999\u098a\u099d\u09f0"), Category.COMBAT);
    }

    @Override
    protected void onEnable() {
        this.tickCounter = 0;
        this.pendingHotbarSlot = -1;
    }

    @Override
    protected void onDisable() {
        this.tickCounter = 0;
        this.pendingHotbarSlot = -1;
    }

    @Override
    public void onTick() {
        int wait;
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null) {
            return;
        }
        if (!AutoTotemModule.canRun(client)) {
            return;
        }
        ++this.tickCounter;
        int n = wait = (Boolean)this.hotbarTotem.get() != false && this.needsHotbarWork(client) && !this.needsOffhandWork(client) ? this.hotbarDelay.getInt() : this.delay.getInt();
        if (this.tickCounter < wait) {
            return;
        }
        this.tickCounter = 0;
        class_746 player = client.field_1724;
        int configuredHotbar = this.hotbarSlot.getInt() - 1;
        if (((Boolean)this.hotbarTotem.get()).booleanValue() && this.needsHotbarWork(client)) {
            if (player.method_31548().method_5438(configuredHotbar).method_31574(class_1802.field_8288)) {
                InventoryHelper.selectHotbarSlot(configuredHotbar);
            } else {
                int source = AutoTotemModule.findTotemInMainInventory(client);
                if (source != -1 && AutoTotemModule.canContainerClick(client)) {
                    InventoryHelper.swapInventoryToHotbar(source, configuredHotbar);
                }
            }
        } else if (!this.needsOffhandWork(client)) {
            this.pendingHotbarSlot = -1;
        } else if (!player.method_6079().method_31574(class_1802.field_8288) || ((Boolean)this.forceTotem.get()).booleanValue()) {
            int hotbarTotemSlot = AutoTotemModule.findTotemInHotbar(client);
            if (hotbarTotemSlot != -1) {
                this.pendingHotbarSlot = hotbarTotemSlot;
                InventoryHelper.selectHotbarSlot(hotbarTotemSlot);
                if (player.method_6047().method_31574(class_1802.field_8288)) {
                    InventoryHelper.swapOffhand();
                }
            } else if (this.pendingHotbarSlot >= 0) {
                InventoryHelper.selectHotbarSlot(this.pendingHotbarSlot);
                if (player.method_6047().method_31574(class_1802.field_8288)) {
                    InventoryHelper.swapOffhand();
                    this.pendingHotbarSlot = -1;
                }
            } else {
                int mainInvTotem = AutoTotemModule.findTotemInMainInventory(client);
                if (mainInvTotem != -1 && AutoTotemModule.canContainerClick(client)) {
                    int targetHotbar = configuredHotbar;
                    if (!((Boolean)this.hotbarTotem.get()).booleanValue()) {
                        targetHotbar = player.method_31548().method_67532();
                    }
                    InventoryHelper.swapInventoryToHotbar(mainInvTotem, targetHotbar);
                    this.pendingHotbarSlot = targetHotbar;
                } else if (player.method_6047().method_31574(class_1802.field_8288)) {
                    InventoryHelper.swapOffhand();
                }
            }
        }
    }

    private static boolean canRun(class_310 client) {
        return client.field_1755 == null || client.field_1755 instanceof class_490;
    }

    private static boolean canContainerClick(class_310 client) {
        return client.field_1755 != null || !AutoTotemModule.isMoving(client);
    }

    private static boolean isMoving(class_310 client) {
        if (client.field_1724 == null) {
            return false;
        }
        if (client.field_1690.field_1894.method_1434() || client.field_1690.field_1881.method_1434() || client.field_1690.field_1913.method_1434() || client.field_1690.field_1849.method_1434() || client.field_1690.field_1903.method_1434() || client.field_1724.method_5624() || client.field_1724.method_5715()) {
            return true;
        }
        double vx = client.field_1724.method_18798().field_1352;
        double vz = client.field_1724.method_18798().field_1350;
        return vx * vx + vz * vz > 0.0025;
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
}

