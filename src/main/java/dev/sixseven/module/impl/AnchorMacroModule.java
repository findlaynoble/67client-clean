/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1802
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_239
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_310
 *  net.minecraft.class_3965
 *  org.lwjgl.glfw.GLFW
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.BlockHelper;
import dev.sixseven.util.InventoryHelper;
import net.minecraft.class_1802;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_3965;
import org.lwjgl.glfw.GLFW;

public class AnchorMacroModule
extends Module {
    public final SliderSetting switchDelay = this.addSetting(new SliderSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00ae\u00ac\u0117\u0108\u011a"), Deobf.decrypt(""), 0.0, 0.0, 20.0, 1.0));
    public final SliderSetting glowstoneDelay = this.addSetting(new SliderSetting(Deobf.decrypt("4@'\u0019a\u00b0\u00a3\u0084\u00ac\u015b\u012d\u0106\u012f\u0191\u01ea"), Deobf.decrypt(""), 0.0, 0.0, 20.0, 1.0));
    public final SliderSetting explodeDelay = this.addSetting(new SliderSetting(Deobf.decrypt("6T8\u0002}\u00a0\u00a9\u00ca\u008d\u011e\u0105\u0102\u013a"), Deobf.decrypt(""), 0.0, 0.0, 20.0, 1.0));
    public final SliderSetting totemSlot = this.addSetting(new SliderSetting(Deobf.decrypt("'C<\u000b\u007f\u00e4\u009f\u0086\u00a6\u010f"), Deobf.decrypt(""), 1.0, 1.0, 9.0, 1.0));
    public final BooleanSetting autoSwitchBack = this.addSetting(new BooleanSetting(Deobf.decrypt("2Y<\u00012\u0097\u00bb\u0083\u00bd\u0118\u0101\u0143\u0101\u0191\u01f0\u01df"), Deobf.decrypt(""), true));
    public final SliderSetting switchBackDelay = this.addSetting(new SliderSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00a8\u00a8\u0118\u0102\u0143\u0107\u0195\u01ff\u01d5\u01e7"), Deobf.decrypt(""), 2.0, 0.0, 20.0, 1.0));
    private int step;
    private int tickCounter;
    private class_2338 targetPos;
    private int previousSlot = -1;

    public AnchorMacroModule() {
        super(Deobf.decrypt("2B+\u0006}\u00b6\u00ec\u00a7\u00a8\u0118\u011b\u010c"), Deobf.decrypt("2Y<\u0001\u007f\u00a5\u00b8\u0083\u00aa\u011a\u0105\u010f\u013a\u01d0\u01f1\u01d8\u01f1\u020c\u0202\u0215\u0229\u0289\u02d5\u02d0\u02be\u030d\u0309\u031d\u0356\u03de\u03b5\u0397\u03b7\u03de\u0447\u0421\u041d\u047e"), Category.COMBAT);
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
        if (client.field_1724 == null || client.field_1761 == null || client.field_1687 == null) {
            return;
        }
        if (this.step > 0) {
            this.processStep(client);
        } else {
            class_3965 hit;
            class_2338 pos;
            boolean rmb;
            long handle = client.method_22683().method_4490();
            boolean bl = rmb = GLFW.glfwGetMouseButton((long)handle, (int)1) == 1;
            if (rmb && client.field_1765 != null && client.field_1765.method_17783() == class_239.class_240.field_1332 && BlockHelper.isBlockAt(pos = (hit = (class_3965)client.field_1765).method_17777(), class_2246.field_23152)) {
                this.targetPos = pos;
                this.previousSlot = client.field_1724.method_31548().method_67532();
                if (BlockHelper.isAnchorUncharged(pos)) {
                    this.step = 1;
                    this.tickCounter = 0;
                } else if (BlockHelper.isAnchorCharged(pos)) {
                    this.step = 3;
                    this.tickCounter = 0;
                }
            }
        }
    }

    private void processStep(class_310 client) {
        class_3965 bhr;
        ++this.tickCounter;
        class_239 class_2392 = client.field_1765;
        class_3965 hit = class_2392 instanceof class_3965 ? (bhr = (class_3965)class_2392) : null;
        switch (this.step) {
            case 1: {
                if (this.tickCounter < this.switchDelay.getInt()) break;
                InventoryHelper.swapToItem(class_1802.field_8801);
                this.step = 2;
                this.tickCounter = 0;
                break;
            }
            case 2: {
                if (this.tickCounter < this.glowstoneDelay.getInt()) break;
                if (hit != null && this.targetPos.equals((Object)hit.method_17777())) {
                    BlockHelper.interactBlock(hit, true);
                }
                this.step = 3;
                this.tickCounter = 0;
                break;
            }
            case 3: {
                if (this.tickCounter < this.explodeDelay.getInt()) break;
                InventoryHelper.swap(this.totemSlot.getInt() - 1);
                this.step = 4;
                this.tickCounter = 0;
                break;
            }
            case 4: {
                if (this.tickCounter < 1) break;
                if (hit != null && this.targetPos.equals((Object)hit.method_17777())) {
                    BlockHelper.interactBlock(hit, true);
                }
                if (((Boolean)this.autoSwitchBack.get()).booleanValue() && this.previousSlot >= 0) {
                    this.step = 5;
                    this.tickCounter = 0;
                    break;
                }
                this.resetState();
                break;
            }
            case 5: {
                if (this.tickCounter < this.switchBackDelay.getInt()) break;
                InventoryHelper.swap(this.previousSlot);
                this.resetState();
                break;
            }
            default: {
                this.resetState();
            }
        }
    }

    private void resetState() {
        this.step = 0;
        this.tickCounter = 0;
        this.targetPos = null;
        this.previousSlot = -1;
    }
}

