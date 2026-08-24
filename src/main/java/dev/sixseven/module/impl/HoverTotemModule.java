/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1713
 *  net.minecraft.class_1735
 *  net.minecraft.class_1802
 *  net.minecraft.class_310
 *  net.minecraft.class_437
 *  net.minecraft.class_465
 */
package dev.sixseven.module.impl;

import dev.sixseven.mixin.AbstractContainerScreenAccessor;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.InventoryHelper;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_465;

public class HoverTotemModule
extends Module {
    public final SliderSetting tickDelay = this.addSetting(new SliderSetting(Deobf.decrypt("'E+\u00052\u0080\u00a9\u0086\u00a8\u0102"), Deobf.decrypt(""), 0.0, 0.0, 20.0, 1.0));
    public final BooleanSetting hotbarTotem = this.addSetting(new BooleanSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00be\u00a6\u010f\u010c\u010e"), Deobf.decrypt(""), true));
    public final SliderSetting hotbarSlot = this.addSetting(new SliderSetting(Deobf.decrypt(";C<\fs\u00b6\u00ec\u00b9\u00a5\u0114\u011d"), Deobf.decrypt(""), 1.0, 1.0, 9.0, 1.0));
    public final BooleanSetting autoSwitchToTotem = this.addSetting(new BooleanSetting(Deobf.decrypt("2Y<\u00012\u0097\u00bb\u0083\u00bd\u0118\u0101\u0143\u0117\u019f\u01b3\u01e0\u01f1\u020f\u0214\u0258"), Deobf.decrypt(""), false));
    private int tickCounter;

    public HoverTotemModule() {
        super(Deobf.decrypt(";C>\u000b`\u00e4\u0098\u0085\u00bd\u011e\u0104"), Deobf.decrypt("6]=\u0007b\u00e4\u00b8\u0085\u00bd\u011e\u0104\u0143\u0134\u0198\u01f6\u01da\u01be\u0213\u021e\u0243\u0239\u028b\u029c\u02cc\u02bc\u035e\u0316\u030a\u0344\u03c2\u03b5\u0399\u03b7\u03d8\u040f\u0427\u0401\u042d\u04c0\u04b9\u04f1\u04e4\u054b\u0570\u0557\u055e\u05dd"), Category.COMBAT);
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
        class_437 class_4372;
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null) {
            return;
        }
        if (((Boolean)this.autoSwitchToTotem.get()).booleanValue() && ((Boolean)this.hotbarTotem.get()).booleanValue()) {
            int slot = this.hotbarSlot.getInt() - 1;
            if (client.field_1724.method_31548().method_5438(slot).method_31574(class_1802.field_8288)) {
                InventoryHelper.swap(slot);
            }
        }
        if ((class_4372 = client.field_1755) instanceof class_465) {
            class_465 handledScreen = (class_465)class_4372;
            ++this.tickCounter;
            if (this.tickCounter >= this.tickDelay.getInt()) {
                this.tickCounter = 0;
                class_1735 focusedSlot = this.getFocusedSlot(handledScreen);
                if (focusedSlot != null && focusedSlot.method_7681() && focusedSlot.method_7677().method_31574(class_1802.field_8288)) {
                    int slotId = focusedSlot.field_7874;
                    client.field_1761.method_2906(handledScreen.method_17577().field_7763, slotId, 0, class_1713.field_7790, (class_1657)client.field_1724);
                    client.field_1761.method_2906(handledScreen.method_17577().field_7763, 45, 0, class_1713.field_7790, (class_1657)client.field_1724);
                    client.field_1761.method_2906(handledScreen.method_17577().field_7763, slotId, 0, class_1713.field_7790, (class_1657)client.field_1724);
                }
            }
        }
    }

    private class_1735 getFocusedSlot(class_465<?> screen) {
        return ((AbstractContainerScreenAccessor)screen).getHoveredSlot();
    }
}

