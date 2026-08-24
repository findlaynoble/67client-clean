/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_10192
 *  net.minecraft.class_1304
 *  net.minecraft.class_1657
 *  net.minecraft.class_1713
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_310
 *  net.minecraft.class_9334
 *  org.lwjgl.glfw.GLFW
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.KeybindSetting;
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_10192;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1713;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_310;
import net.minecraft.class_9334;
import org.lwjgl.glfw.GLFW;

public class ElytraSwapModule
extends Module {
    public final KeybindSetting activateKey = this.addSetting(new KeybindSetting(Deobf.decrypt("2O<\u0007d\u00a5\u00b8\u008f\u00e9\u0130\u010c\u011a"), Deobf.decrypt(""), 71));
    public final SliderSetting swapDelay = this.addSetting(new SliderSetting(Deobf.decrypt(" [)\u001e2\u0080\u00a9\u0086\u00a8\u0102"), Deobf.decrypt(""), 0.0, 0.0, 20.0, 1.0));
    public final BooleanSetting switchBack = this.addSetting(new BooleanSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00a8\u00a8\u0118\u0102"), Deobf.decrypt(""), true));
    public final SliderSetting switchDelay = this.addSetting(new SliderSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00ae\u00ac\u0117\u0108\u011a"), Deobf.decrypt(""), 0.0, 0.0, 20.0, 1.0));
    public final BooleanSetting moveToSlot = this.addSetting(new BooleanSetting(Deobf.decrypt(">C>\u000b2\u0090\u00a3\u00ca\u009a\u0117\u0106\u0117"), Deobf.decrypt(""), true));
    public final SliderSetting elytraSlot = this.addSetting(new SliderSetting(Deobf.decrypt("6@1\u001a`\u00a5\u00ec\u00b9\u00a5\u0114\u011d"), Deobf.decrypt(""), 9.0, 1.0, 9.0, 1.0));
    private boolean keyWasDown;
    private boolean swappedToElytra;
    private int tickCounter;
    private boolean waitingForSwap;
    private boolean waitingForSwitchBack;

    public ElytraSwapModule() {
        super(Deobf.decrypt("6@1\u001a`\u00a5\u00ec\u00b9\u00be\u011a\u0119"), Deobf.decrypt(" [)\u001e2\u00a6\u00a9\u009e\u00be\u011e\u010c\u010d\u0163\u01b5\u01ff\u01cd\u01ea\u0209\u0210\u0215\u023d\u0297\u0291\u0282\u0298\u0316\u031c\u030f\u0355\u03c0\u03f9\u0397\u03ad\u03d8\u040f\u0439\u0406\u0479\u04c1\u04f7\u04e6\u04a1\u054e\u0561\u0541\u054e\u05cd\u05e8\u05d7"), Category.COMBAT);
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
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null) {
            return;
        }
        if (this.waitingForSwap) {
            ++this.tickCounter;
            if (this.tickCounter >= this.swapDelay.getInt()) {
                this.performSwap(client);
                this.waitingForSwap = false;
                if (((Boolean)this.switchBack.get()).booleanValue()) {
                    this.waitingForSwitchBack = true;
                    this.tickCounter = 0;
                }
            }
        } else if (this.waitingForSwitchBack) {
            ++this.tickCounter;
            if (this.tickCounter >= this.switchDelay.getInt()) {
                this.performSwap(client);
                this.waitingForSwitchBack = false;
            }
        } else {
            int key = (Integer)this.activateKey.get();
            if (key != -1) {
                boolean pressed;
                long handle = client.method_22683().method_4490();
                boolean bl = key <= 7 ? GLFW.glfwGetMouseButton((long)handle, (int)key) == 1 : (pressed = GLFW.glfwGetKey((long)handle, (int)key) == 1);
                if (pressed && !this.keyWasDown) {
                    this.waitingForSwap = true;
                    this.tickCounter = 0;
                }
                this.keyWasDown = pressed;
            }
        }
    }

    private void performSwap(class_310 client) {
        class_1799 chestSlot = client.field_1724.method_6118(class_1304.field_6174);
        boolean wearingElytra = chestSlot.method_31574(class_1802.field_8833);
        int targetSlot = -1;
        if (wearingElytra) {
            for (i = 0; i < 36; ++i) {
                stack = client.field_1724.method_31548().method_5438(i);
                class_10192 equippable = (class_10192)stack.method_58694(class_9334.field_54196);
                if (equippable == null || equippable.comp_3174() != class_1304.field_6174 || stack.method_31574(class_1802.field_8833)) continue;
                targetSlot = i;
                break;
            }
        } else {
            if (((Boolean)this.moveToSlot.get()).booleanValue()) {
                int slotIdx = this.elytraSlot.getInt() - 1;
                stack = client.field_1724.method_31548().method_5438(slotIdx);
                if (stack.method_31574(class_1802.field_8833)) {
                    targetSlot = slotIdx;
                }
            }
            if (targetSlot == -1) {
                for (i = 0; i < 36; ++i) {
                    if (!client.field_1724.method_31548().method_5438(i).method_31574(class_1802.field_8833)) continue;
                    targetSlot = i;
                    break;
                }
            }
        }
        if (targetSlot != -1) {
            int screenSlot = targetSlot < 9 ? targetSlot + 36 : targetSlot;
            int armorScreenSlot = 6;
            client.field_1761.method_2906(client.field_1724.field_7512.field_7763, screenSlot, 0, class_1713.field_7790, (class_1657)client.field_1724);
            client.field_1761.method_2906(client.field_1724.field_7512.field_7763, armorScreenSlot, 0, class_1713.field_7790, (class_1657)client.field_1724);
            client.field_1761.method_2906(client.field_1724.field_7512.field_7763, screenSlot, 0, class_1713.field_7790, (class_1657)client.field_1724);
            this.swappedToElytra = !wearingElytra;
        }
    }

    private void resetState() {
        this.keyWasDown = false;
        this.swappedToElytra = false;
        this.tickCounter = 0;
        this.waitingForSwap = false;
        this.waitingForSwitchBack = false;
    }
}

