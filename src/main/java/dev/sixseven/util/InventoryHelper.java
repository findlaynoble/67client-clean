/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1657
 *  net.minecraft.class_1713
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2596
 *  net.minecraft.class_2846
 *  net.minecraft.class_2846$class_2847
 *  net.minecraft.class_2868
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package dev.sixseven.util;

import java.util.function.Predicate;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2596;
import net.minecraft.class_2846;
import net.minecraft.class_2868;
import net.minecraft.class_310;
import net.minecraft.class_746;

public final class InventoryHelper {
    private InventoryHelper() {
    }

    private static class_310 mc() {
        return class_310.method_1551();
    }

    public static int toScreenSlot(int invIndex) {
        return invIndex < 9 ? invIndex + 36 : invIndex;
    }

    public static void selectHotbarSlot(int slot) {
        class_310 mc = InventoryHelper.mc();
        if (slot < 0 || slot > 8 || mc.field_1724 == null) {
            return;
        }
        if (mc.field_1724.method_31548().method_67532() == slot) {
            return;
        }
        mc.field_1724.method_31548().method_61496(slot);
        if (mc.method_1562() != null) {
            mc.method_1562().method_52787((class_2596)new class_2868(slot));
        }
    }

    public static void swapOffhand() {
        class_310 mc = InventoryHelper.mc();
        if (mc.field_1724 == null || mc.method_1562() == null) {
            return;
        }
        mc.method_1562().method_52787((class_2596)new class_2846(class_2846.class_2847.field_12969, class_2338.field_10980, class_2350.field_11033));
    }

    public static void swapInventoryToHotbar(int invIndex, int hotbarIndex) {
        class_310 mc = InventoryHelper.mc();
        if (mc.field_1724 == null || mc.field_1761 == null) {
            return;
        }
        if (invIndex < 9 || invIndex > 35 || hotbarIndex < 0 || hotbarIndex > 8) {
            return;
        }
        mc.field_1761.method_2906(mc.field_1724.field_7512.field_7763, InventoryHelper.toScreenSlot(invIndex), hotbarIndex, class_1713.field_7791, (class_1657)mc.field_1724);
    }

    public static void swap(int slot) {
        InventoryHelper.selectHotbarSlot(slot);
    }

    public static class_1268 handHolding(class_1792 item) {
        class_746 player = InventoryHelper.mc().field_1724;
        if (player == null) {
            return null;
        }
        if (player.method_6047().method_31574(item)) {
            return class_1268.field_5808;
        }
        if (player.method_6079().method_31574(item)) {
            return class_1268.field_5810;
        }
        return null;
    }

    public static int getHotbarSlot(class_1792 item) {
        class_746 player = InventoryHelper.mc().field_1724;
        if (player == null) {
            return -1;
        }
        for (int i = 0; i < 9; ++i) {
            if (!player.method_31548().method_5438(i).method_31574(item)) continue;
            return i;
        }
        return -1;
    }

    public static int findItemSlot(class_1792 item) {
        class_746 player = InventoryHelper.mc().field_1724;
        if (player == null) {
            return -1;
        }
        for (int i = 0; i < 36; ++i) {
            if (!player.method_31548().method_5438(i).method_31574(item)) continue;
            return i;
        }
        return -1;
    }

    public static boolean swapToItem(class_1792 item) {
        int slot = InventoryHelper.getHotbarSlot(item);
        if (slot < 0) {
            return false;
        }
        InventoryHelper.selectHotbarSlot(slot);
        return true;
    }

    public static boolean swapToStack(Predicate<class_1799> predicate) {
        class_746 player = InventoryHelper.mc().field_1724;
        if (player == null) {
            return false;
        }
        for (int i = 0; i < 9; ++i) {
            if (!predicate.test(player.method_31548().method_5438(i))) continue;
            InventoryHelper.selectHotbarSlot(i);
            return true;
        }
        return false;
    }

    public static int findEmptyHotbarSlot() {
        class_746 player = InventoryHelper.mc().field_1724;
        if (player == null) {
            return -1;
        }
        for (int i = 0; i < 9; ++i) {
            if (!player.method_31548().method_5438(i).method_7960()) continue;
            return i;
        }
        return -1;
    }
}

