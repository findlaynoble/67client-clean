/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1657
 *  net.minecraft.class_1743
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_310
 *  net.minecraft.class_3966
 *  org.lwjgl.glfw.GLFW
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.InventoryHelper;
import net.minecraft.class_1268;
import net.minecraft.class_1657;
import net.minecraft.class_1743;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_239;
import net.minecraft.class_310;
import net.minecraft.class_3966;
import org.lwjgl.glfw.GLFW;

public class MaceSwapModule
extends Module {
    public final BooleanSetting windBurst = this.addSetting(new BooleanSetting(Deobf.decrypt("$E&\n2\u0086\u00b9\u0098\u00ba\u010f"), Deobf.decrypt(""), true));
    public final BooleanSetting breach = this.addSetting(new BooleanSetting(Deobf.decrypt("1^-\u000fq\u00ac"), Deobf.decrypt(""), true));
    public final BooleanSetting onlySword = this.addSetting(new BooleanSetting(Deobf.decrypt("<B$\u00172\u0097\u00bb\u0085\u00bb\u011f"), Deobf.decrypt(""), false));
    public final BooleanSetting onlyAxe = this.addSetting(new BooleanSetting(Deobf.decrypt("<B$\u00172\u0085\u00b4\u008f"), Deobf.decrypt(""), false));
    public final BooleanSetting switchBack = this.addSetting(new BooleanSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00a8\u00a8\u0118\u0102"), Deobf.decrypt(""), true));
    public final SliderSetting switchDelay = this.addSetting(new SliderSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00ae\u00ac\u0117\u0108\u011a"), Deobf.decrypt(""), 0.0, 0.0, 20.0, 1.0));
    private int previousSlot = -1;
    private int tickCounter;
    private boolean waitingToSwapBack;
    private boolean attackedThisTick;

    public MaceSwapModule() {
        super(Deobf.decrypt(">M+\u000b2\u0097\u00bb\u008b\u00b9"), Deobf.decrypt(" [!\u001aq\u00ac\u00a9\u0099\u00e9\u010f\u0106\u0143\u012e\u0191\u01f0\u01d1\u01be\u020c\u0219\u0250\u0232\u02d9\u0294\u02d6\u02af\u031f\u031a\u0317\u0348\u03de\u03f2"), Category.COMBAT);
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
        boolean lmb;
        class_310 client = class_310.method_1551();
        if (client.field_1724 == null || client.field_1761 == null) {
            return;
        }
        if (this.waitingToSwapBack) {
            ++this.tickCounter;
            if (this.tickCounter >= this.switchDelay.getInt()) {
                InventoryHelper.swap(this.previousSlot);
                this.resetState();
            }
            return;
        }
        long handle = client.method_22683().method_4490();
        boolean bl = lmb = GLFW.glfwGetMouseButton((long)handle, (int)0) == 1;
        if (!lmb || client.field_1755 != null) {
            return;
        }
        if (client.field_1765 == null || client.field_1765.method_17783() != class_239.class_240.field_1331) {
            return;
        }
        if (client.field_1724.method_7261(0.5f) < 1.0f) {
            return;
        }
        if (((Boolean)this.onlySword.get()).booleanValue() && !this.isSword(client.field_1724.method_6047().method_7909())) {
            return;
        }
        if (((Boolean)this.onlyAxe.get()).booleanValue() && !(client.field_1724.method_6047().method_7909() instanceof class_1743)) {
            return;
        }
        if (client.field_1724.method_6047().method_31574(class_1802.field_49814)) {
            return;
        }
        if (InventoryHelper.getHotbarSlot(class_1802.field_49814) == -1) {
            return;
        }
        this.previousSlot = client.field_1724.method_31548().method_67532();
        InventoryHelper.swapToItem(class_1802.field_49814);
        class_3966 entityHit = (class_3966)client.field_1765;
        client.field_1761.method_2918((class_1657)client.field_1724, entityHit.method_17782());
        client.field_1724.method_6104(class_1268.field_5808);
        if (((Boolean)this.switchBack.get()).booleanValue()) {
            this.waitingToSwapBack = true;
            this.tickCounter = 0;
        } else {
            this.previousSlot = -1;
        }
    }

    private boolean isSword(class_1792 item) {
        return item == class_1802.field_8091 || item == class_1802.field_8528 || item == class_1802.field_8371 || item == class_1802.field_8845 || item == class_1802.field_8802 || item == class_1802.field_22022;
    }

    private void resetState() {
        this.previousSlot = -1;
        this.tickCounter = 0;
        this.waitingToSwapBack = false;
        this.attackedThisTick = false;
    }
}

