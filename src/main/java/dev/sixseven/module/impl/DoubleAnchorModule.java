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
import dev.sixseven.settings.KeybindSetting;
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

public class DoubleAnchorModule
extends Module {
    public final KeybindSetting activateKey = this.addSetting(new KeybindSetting(Deobf.decrypt("2O<\u0007d\u00a5\u00b8\u008f\u00e9\u0130\u010c\u011a"), Deobf.decrypt(";C$\n2\u00b0\u00a3\u00ca\u00bb\u010e\u0107\u0143\u0137\u0198\u01f6\u0194\u01fd\u0214\u021c\u0257\u0233"), 72));
    public final SliderSetting timing = this.addSetting(new SliderSetting(Deobf.decrypt("'E%\u0007|\u00a3"), Deobf.decrypt("7I$\u000fk\u00e4\u00ae\u008f\u00bd\u010c\u010c\u0106\u012d\u01d0\u01e7\u01dc\u01fb\u025b\u0205\u0242\u0233\u02d9\u0297\u02ce\u02b4\u0309\u030a"), 120.0, 40.0, 400.0, 10.0, Deobf.decrypt("\u001e_")));
    public final SliderSetting detonateSlot = this.addSetting(new SliderSetting(Deobf.decrypt("7I<\u0001|\u00a5\u00b8\u008f\u00e9\u0128\u0105\u010c\u0137"), Deobf.decrypt(";C<\fs\u00b6\u00ec\u0099\u00a5\u0114\u011d\u0143\u0137\u019f\u01b3\u01dc\u01f1\u0217\u0215\u0215\u022b\u0291\u029c\u02ce\u02be\u035e\u031d\u0319\u0355\u03df\u03fb\u0397\u03ad\u03d4\u0441\u0429\u044f\u0425\u04c8\u04b9\u04fe\u04f5\u054d\u056d\u0556\u054b\u0584\u05e4\u05c6\u05ba\u064b\u0624\u0654\u065b\u0685\u06ea\u06ce\u06d7\u0703\u0773\u0714"), 1.0, 1.0, 9.0, 1.0));
    public final BooleanSetting rePlace = this.addSetting(new BooleanSetting(Deobf.decrypt("!Ie>~\u00a5\u00af\u008f"), Deobf.decrypt("#@)\rw\u00e4\u00ad\u00ca\u00af\u0109\u010c\u0110\u012b\u01d0\u01f2\u01da\u01fd\u0213\u021e\u0247\u027c\u029f\u029a\u02d0\u02fb\u030a\u0311\u0319\u0301\u03c3\u03f0\u0395\u03b6\u03d3\u044b\u046e\u040d\u0461\u04c6\u04a0\u04a7\u04e8\u0543\u0524\u054c\u0544\u05c1\u05a6\u05d5\u05a7\u0619\u0630\u064c\u0614\u069b\u06ea\u069a\u06db\u0702\u0778\u074e\u0749\u07d6\u0781\u0797"), true));
    public final BooleanSetting switchBack = this.addSetting(new BooleanSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00a8\u00a8\u0118\u0102"), Deobf.decrypt("!I<\u001b`\u00aa\u00ec\u009e\u00a6\u015b\u011d\u010b\u0126\u01d0\u01e3\u01c6\u01fb\u020d\u0218\u025a\u0229\u028a\u02d5\u02ca\u02b4\u030a\u031b\u031d\u0353\u0390\u03e6\u039a\u03b6\u03c9\u040f\u0439\u0407\u0468\u04c7\u04f7\u04e3\u04ee\u054b\u0561"), true));
    private int step;
    private int tickCounter;
    private class_2338 targetPos;
    private int previousSlot = -1;

    public DoubleAnchorModule() {
        super(Deobf.decrypt("7C=\f~\u00a1\u00ec\u00ab\u00a7\u0118\u0101\u010c\u0131"), Deobf.decrypt("'['N`\u00a5\u00bc\u0083\u00ad\u015b\u0108\u010d\u0120\u0198\u01fc\u01c6\u01be\u0218\u0219\u0254\u022e\u029e\u0290\u2330\u02bf\u031b\u030d\u0313\u034f\u03d1\u03e1\u0393\u03f9\u03de\u0456\u042d\u0403\u0468\u04da"), Category.COMBAT);
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
        class_3965 hit;
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null || client.field_1687 == null || client.field_1755 != null) {
            if (this.step != 0) {
                this.resetState();
            }
            return;
        }
        if (this.step > 0) {
            this.processStep(client);
            return;
        }
        if (!this.keyDown(client)) {
            return;
        }
        class_239 class_2392 = client.field_1765;
        if (!(class_2392 instanceof class_3965) || (hit = (class_3965)class_2392).method_17783() != class_239.class_240.field_1332) {
            return;
        }
        class_2338 pos = hit.method_17777();
        if (!BlockHelper.isBlockAt(pos, class_2246.field_23152)) {
            return;
        }
        this.targetPos = pos.method_10062();
        this.previousSlot = client.field_1724.method_31548().method_67532();
        this.step = 1;
        this.tickCounter = 0;
    }

    private void processStep(class_310 client) {
        class_3965 bhr;
        ++this.tickCounter;
        class_239 class_2392 = client.field_1765;
        class_3965 live = class_2392 instanceof class_3965 ? (bhr = (class_3965)class_2392) : null;
        switch (this.step) {
            case 1: {
                if (!this.lookingAtTarget(live)) {
                    this.resetState();
                    return;
                }
                if (BlockHelper.isAnchorUncharged(this.targetPos)) {
                    InventoryHelper.swapToItem(class_1802.field_8801);
                    BlockHelper.interactBlock(live, true);
                }
                this.advance(2);
                break;
            }
            case 2: {
                if (!this.lookingAtTarget(live)) {
                    this.resetState();
                    return;
                }
                InventoryHelper.swap(this.detonateSlot.getInt() - 1);
                BlockHelper.interactBlock(live, true);
                this.advance(3);
                break;
            }
            case 3: {
                if (this.tickCounter < this.gapTicks()) break;
                this.advance(4);
                break;
            }
            case 4: {
                if (BlockHelper.isBlockAt(this.targetPos, class_2246.field_23152)) {
                    if (this.lookingAtTarget(live) && BlockHelper.isAnchorUncharged(this.targetPos)) {
                        InventoryHelper.swapToItem(class_1802.field_8801);
                        BlockHelper.interactBlock(live, true);
                    }
                    this.advance(5);
                    break;
                }
                if (((Boolean)this.rePlace.get()).booleanValue() && live != null && InventoryHelper.getHotbarSlot(class_1802.field_23141) >= 0) {
                    InventoryHelper.swapToItem(class_1802.field_23141);
                    BlockHelper.interactBlock(live, true);
                    this.targetPos = live.method_17777().method_10093(live.method_17780()).method_10062();
                    this.advance(6);
                    break;
                }
                this.finish(client);
                break;
            }
            case 5: {
                if (this.lookingAtTarget(live) && BlockHelper.isBlockAt(this.targetPos, class_2246.field_23152)) {
                    InventoryHelper.swap(this.detonateSlot.getInt() - 1);
                    BlockHelper.interactBlock(live, true);
                }
                this.finish(client);
                break;
            }
            case 6: {
                if (BlockHelper.isBlockAt(this.targetPos, class_2246.field_23152)) {
                    InventoryHelper.swapToItem(class_1802.field_8801);
                    if (live != null) {
                        BlockHelper.interactBlock(live, true);
                    }
                    this.advance(5);
                    break;
                }
                this.finish(client);
                break;
            }
            default: {
                this.finish(client);
            }
        }
    }

    private boolean lookingAtTarget(class_3965 live) {
        return live != null && live.method_17783() == class_239.class_240.field_1332 && live.method_17777().equals((Object)this.targetPos);
    }

    private void advance(int next) {
        this.step = next;
        this.tickCounter = 0;
    }

    private int gapTicks() {
        return Math.max(1, this.timing.getInt() / 50);
    }

    private void finish(class_310 client) {
        if (((Boolean)this.switchBack.get()).booleanValue() && this.previousSlot >= 0) {
            InventoryHelper.swap(this.previousSlot);
        }
        this.resetState();
    }

    private boolean keyDown(class_310 client) {
        int key = (Integer)this.activateKey.get();
        if (key == -1) {
            return false;
        }
        long handle = client.method_22683().method_4490();
        return key <= 7 ? GLFW.glfwGetMouseButton((long)handle, (int)key) == 1 : GLFW.glfwGetKey((long)handle, (int)key) == 1;
    }

    private void resetState() {
        this.step = 0;
        this.tickCounter = 0;
        this.targetPos = null;
        this.previousSlot = -1;
    }
}

