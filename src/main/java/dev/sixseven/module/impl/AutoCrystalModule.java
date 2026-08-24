/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1041
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1511
 *  net.minecraft.class_1657
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  net.minecraft.class_1937
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_239
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_3675
 *  net.minecraft.class_3965
 *  net.minecraft.class_3966
 *  net.minecraft.class_746
 *  org.jetbrains.annotations.Nullable
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
import java.util.List;
import net.minecraft.class_1041;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1511;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3675;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_746;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class AutoCrystalModule
extends Module {
    public final KeybindSetting activateKey = this.addSetting(new KeybindSetting(Deobf.decrypt("2O<\u0007d\u00a5\u00b8\u008f\u00e9\u0130\u010c\u011a"), Deobf.decrypt(";C$\n2\u00b0\u00a3\u00ca\u00b9\u0117\u0108\u0100\u0126\u01d0\u01b8\u0194\u01fc\u0209\u0214\u0254\u0237\u02d9\u02dd\u02c6\u02be\u0318\u0318\u0309\u034d\u03c4\u03b5\u03a4\u0394\u03ff\u0406"), 1));
    public final SliderSetting breakDelay = this.addSetting(new SliderSetting(Deobf.decrypt("1^-\u000fy\u00e4\u0088\u008f\u00a5\u011a\u0110"), Deobf.decrypt("'E+\u0005a\u00e4\u00ae\u008f\u00bd\u010c\u010c\u0106\u012d\u01d0\u01f0\u01c6\u01e7\u0208\u0205\u0254\u0230\u02d9\u0297\u02d0\u02be\u031f\u0312\u030f"), 1.0, 0.0, 20.0, 1.0, Deobf.decrypt("\u0007")));
    public final SliderSetting placeDelay = this.addSetting(new SliderSetting(Deobf.decrypt("#@)\rw\u00e4\u0088\u008f\u00a5\u011a\u0110"), Deobf.decrypt("'E+\u0005a\u00e4\u00ae\u008f\u00bd\u010c\u010c\u0106\u012d\u01d0\u01f0\u01c6\u01e7\u0208\u0205\u0254\u0230\u02d9\u0285\u02ce\u02ba\u031d\u031c\u030f"), 1.0, 0.0, 20.0, 1.0, Deobf.decrypt("\u0007")));
    public final BooleanSetting autoObsidian = this.addSetting(new BooleanSetting(Deobf.decrypt("2Y<\u00012\u008b\u00ae\u0099\u00a0\u011f\u0100\u0102\u012d"), Deobf.decrypt("?M1Ns\u00aa\u00ec\u0085\u00ab\u0108\u0100\u0107\u012a\u0191\u01fd\u0194\u01fc\u021a\u0202\u0250\u027c\u028e\u029d\u02c7\u02b5\u035e\u0300\u0313\u0354\u0397\u03e7\u0393\u03f9\u03d3\u0440\u043a\u044f\u046c\u04c0\u04ba\u04ee\u04ef\u0542\u0524\u0559\u0558\u0584\u05e9\u05dd\u05ab"), true));
    public final SliderSetting obsidianDelay = this.addSetting(new SliderSetting(Deobf.decrypt("<N;\u0007v\u00ad\u00ad\u0084\u00e9\u013f\u010c\u010f\u0122\u0189"), Deobf.decrypt("'E+\u0005a\u00e4\u00ae\u008f\u00bd\u010c\u010c\u0106\u012d\u01d0\u01fc\u01d6\u01ed\u0212\u0215\u025c\u023d\u0297\u02d5\u02d2\u02b7\u031f\u031a\u0319\u034c\u03d5\u03fb\u0382\u03aa"), 2.0, 0.0, 20.0, 1.0, Deobf.decrypt("\u0007")));
    public final SliderSetting range = this.addSetting(new SliderSetting(Deobf.decrypt("1^-\u000fy\u00e4\u009e\u008b\u00a7\u011c\u010c"), Deobf.decrypt(">M0Nv\u00ad\u00bf\u009e\u00a8\u0115\u010a\u0106\u0163\u0184\u01fc\u0194\u01ff\u025b\u0212\u0247\u0225\u028a\u0281\u02c3\u02b7\u035e\u0300\u0313\u0354\u0397\u03f9\u039a\u03f9\u03df\u045d\u042b\u040e\u0466\u0489\u04ff\u04f1\u04e0\u054b\u056d\u0554\u0540\u05c5\u05a6\u05c1\u05ab\u060a\u0620\u0650\u0614\u068c\u06aa\u0693"), 3.0, 1.0, 6.0, 0.5, Deobf.decrypt("\u001e")));
    public final BooleanSetting switchBack = this.addSetting(new BooleanSetting(Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u00a8\u00a8\u0118\u0102"), Deobf.decrypt("!I<\u001b`\u00aa\u00ec\u009e\u00a6\u015b\u0110\u010c\u0136\u0182\u01b3\u01db\u01ec\u0212\u0216\u025c\u0232\u0298\u0299\u0282\u02b3\u0311\u030d\u031e\u0340\u03c2\u03b5\u0385\u03b5\u03d2\u045b\u046e\u0418\u0465\u04cc\u04b9\u04a7\u04f3\u0540\u0568\u055d\u054d\u05d7\u05e3\u05d7"), true));
    private int breakCooldown;
    private int placeCooldown;
    private int obsidianCooldown;
    private int savedSlot = -1;
    private int lastBrokenId = -1;
    private boolean wasActive;

    public AutoCrystalModule() {
        super(Deobf.decrypt("2Y<\u00012\u0087\u00be\u0093\u00ba\u010f\u0108\u010f"), Deobf.decrypt(";C$\n2\u0096\u0081\u00a8\u00e9\u010f\u0106\u0143\u0122\u0185\u01e7\u01db\u01be\u020b\u021d\u0254\u023f\u029c\u02d5\u0289\u02fb\u031c\u030b\u0319\u0340\u03db\u03b5\u0395\u03ab\u03c4\u045c\u043a\u040e\u0461\u04da\u04fb\u04a7\u04ed\u0544\u057d\u0551\u0542\u05c3\u05a6\u05d2\u05a0\u064b\u062c\u065a\u0647\u069b\u06fd\u06d3\u06d9\u0703\u0736\u075f\u075d\u07c8\u0781\u07d3\u079f\u07a1\u0841\u0810\u084a\u0867\u08cf\u08ab\u0880\u08fd\u092e"), Category.COMBAT);
    }

    @Override
    protected void onEnable() {
        this.resetAll();
    }

    @Override
    protected void onDisable() {
        this.restoreSlot();
        this.resetAll();
    }

    @Override
    public void onTick() {
        class_3965 hit;
        class_3966 ehr;
        class_1297 class_12972;
        class_239 hitResult;
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.field_1761 == null || mc.field_1687 == null || mc.field_1755 != null || !this.isTriggerHeld(mc)) {
            this.endActiveHold();
            return;
        }
        this.wasActive = true;
        this.tickCooldowns();
        if ((Integer)this.activateKey.get() == 1) {
            mc.field_1690.field_1904.method_23481(false);
        }
        if ((hitResult = mc.field_1765) instanceof class_3966 && (class_12972 = (ehr = (class_3966)hitResult).method_17782()) instanceof class_1511) {
            class_1511 crystal = (class_1511)class_12972;
            if (this.tryBreak(mc, crystal)) {
                this.lastBrokenId = crystal.method_5628();
            }
            return;
        }
        if (hitResult instanceof class_3965 && (hit = (class_3965)hitResult).method_17783() == class_239.class_240.field_1332) {
            class_2338 clicked = hit.method_17777();
            if (AutoCrystalModule.isCrystalBase((class_1937)mc.field_1687, clicked)) {
                this.serviceBase(mc, hit, clicked);
            } else if (((Boolean)this.autoObsidian.get()).booleanValue()) {
                this.layObsidian(mc, hit, clicked);
            }
        }
    }

    private void serviceBase(class_310 mc, class_3965 hit, class_2338 base) {
        class_1268 hand;
        class_1511 crystal = AutoCrystalModule.crystalOn((class_1937)mc.field_1687, base);
        if (crystal != null) {
            if (this.tryBreak(mc, crystal)) {
                this.lastBrokenId = crystal.method_5628();
            }
            return;
        }
        this.lastBrokenId = -1;
        if (this.placeCooldown == 0 && AutoCrystalModule.canFitCrystal((class_1937)mc.field_1687, base) && (hand = this.equip(mc, class_1802.field_8301)) != null) {
            BlockHelper.interactBlock(hit, hand, true);
            this.placeCooldown = this.placeDelay.getInt();
        }
    }

    private void layObsidian(class_310 mc, class_3965 hit, class_2338 clicked) {
        class_2338 target;
        if (this.obsidianCooldown != 0) {
            return;
        }
        class_2338 class_23382 = target = mc.field_1687.method_8320(clicked).method_45474() ? clicked : clicked.method_10093(hit.method_17780());
        if (!mc.field_1687.method_8320(target).method_45474()) {
            return;
        }
        if (!AutoCrystalModule.canFitCrystal((class_1937)mc.field_1687, target)) {
            return;
        }
        class_1268 hand = this.equip(mc, class_1802.field_8281);
        if (hand == null) {
            return;
        }
        BlockHelper.interactBlock(hit, hand, true);
        this.obsidianCooldown = Math.max(1, this.obsidianDelay.getInt());
    }

    private boolean tryBreak(class_310 mc, class_1511 crystal) {
        if (this.breakCooldown != 0) {
            return false;
        }
        if (crystal.method_5628() == this.lastBrokenId) {
            return false;
        }
        if (mc.field_1724.method_33571().method_1022(crystal.method_73189()) > (Double)this.range.get()) {
            return false;
        }
        mc.field_1761.method_2918((class_1657)mc.field_1724, (class_1297)crystal);
        mc.field_1724.method_6104(class_1268.field_5808);
        this.breakCooldown = this.breakDelay.getInt();
        return true;
    }

    @Nullable
    private class_1268 equip(class_310 mc, class_1792 item) {
        class_746 player = mc.field_1724;
        if (player.method_6047().method_31574(item)) {
            return class_1268.field_5808;
        }
        if (player.method_6079().method_31574(item)) {
            return class_1268.field_5810;
        }
        int slot = InventoryHelper.getHotbarSlot(item);
        if (slot < 0) {
            return null;
        }
        if (this.savedSlot < 0) {
            this.savedSlot = player.method_31548().method_67532();
        }
        InventoryHelper.selectHotbarSlot(slot);
        return class_1268.field_5808;
    }

    private static boolean isCrystalBase(class_1937 level, class_2338 pos) {
        class_2680 state = level.method_8320(pos);
        return state.method_27852(class_2246.field_10540) || state.method_27852(class_2246.field_9987);
    }

    @Nullable
    private static class_1511 crystalOn(class_1937 level, class_2338 base) {
        class_2338 up = base.method_10084();
        class_238 region = new class_238((double)up.method_10263() + 0.125, (double)up.method_10264() - 0.1, (double)up.method_10260() + 0.125, (double)up.method_10263() + 0.875, (double)up.method_10264() + 2.5, (double)up.method_10260() + 0.875);
        List found = level.method_8390(class_1511.class, region, e -> true);
        return found.isEmpty() ? null : (class_1511)found.get(0);
    }

    private static boolean canFitCrystal(class_1937 level, class_2338 base) {
        class_2338 up = base.method_10084();
        if (!level.method_8320(up).method_26215()) {
            return false;
        }
        class_238 box = new class_238((double)up.method_10263(), (double)up.method_10264(), (double)up.method_10260(), (double)up.method_10263() + 1.0, (double)up.method_10264() + 2.0, (double)up.method_10260() + 1.0);
        return level.method_18467(class_1297.class, box).isEmpty();
    }

    private void tickCooldowns() {
        if (this.breakCooldown > 0) {
            --this.breakCooldown;
        }
        if (this.placeCooldown > 0) {
            --this.placeCooldown;
        }
        if (this.obsidianCooldown > 0) {
            --this.obsidianCooldown;
        }
    }

    private boolean isTriggerHeld(class_310 mc) {
        int key = (Integer)this.activateKey.get();
        if (key == -1) {
            return false;
        }
        if (key <= 7) {
            return GLFW.glfwGetMouseButton((long)mc.method_22683().method_4490(), (int)key) == 1;
        }
        return class_3675.method_15987((class_1041)mc.method_22683(), (int)key);
    }

    private void endActiveHold() {
        if (!this.wasActive) {
            return;
        }
        this.restoreSlot();
        this.breakCooldown = 0;
        this.placeCooldown = 0;
        this.obsidianCooldown = 0;
        this.lastBrokenId = -1;
        this.wasActive = false;
    }

    private void restoreSlot() {
        if (((Boolean)this.switchBack.get()).booleanValue() && this.savedSlot >= 0) {
            InventoryHelper.selectHotbarSlot(this.savedSlot);
        }
        this.savedSlot = -1;
    }

    private void resetAll() {
        this.breakCooldown = 0;
        this.placeCooldown = 0;
        this.obsidianCooldown = 0;
        this.savedSlot = -1;
        this.lastBrokenId = -1;
        this.wasActive = false;
    }
}

