/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1657
 *  net.minecraft.class_1703
 *  net.minecraft.class_1713
 *  net.minecraft.class_1716
 *  net.minecraft.class_1799
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.class_1657;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1716;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class GambleRiggerModule
extends Module {
    private static final int GRID = 9;
    private static final int INV_START = 9;
    private static final int INV_END = 45;
    private static final int MOVE = 0;
    private static final int QMOVE = 1;
    public final SliderSetting clickDelay = this.addSetting(new SliderSetting(Deobf.decrypt("0@!\ry\u00e4\u0088\u008f\u00a5\u011a\u0110"), Deobf.decrypt("'E+\u0005a\u00e4\u00ae\u008f\u00bd\u010c\u010c\u0106\u012d\u01d0\u01f6\u01d5\u01fd\u0213\u0251\u0258\u0233\u028f\u0290\u02c6\u02fb\u030d\u030d\u031d\u0342\u03db\u03bb\u03d6\u03e9\u039d\u0412\u046e\u0406\u0463\u04da\u04a3\u04e6\u04ef\u0551\u0524\u0510\u055b\u05cc\u05e9\u05df\u05ab\u064b\u0624\u064a\u065d\u0696\u06b9\u06d3\u06d6\u074d\u0779\u0753\u0759\u079b\u0790\u079a\u078b\u07a2\u080d\u0845\u084a\u087b\u08cb\u08a7\u0897\u08fd\u096a\u092b\u0914\u0963\u0992\u0981\u0995\u09a0\u0a4e\u0a64\u0a48\u0a24\u0a9c\u0ad7\u0acc\u0ab3\u0b05\u0b0d\u0b4d\u0b23\u0bd8\u0b99\u0bea\u0bfa\u0ba9\u0c58\u0c2c\u0c11\u0c5a\u0cd9\u0cad\u0c81\u0ca3\u0d44\u0d32\u0d63\u0d73\u0dc2\u0df7\u0dcc\u0dd7\u0e48\u0e3c\u0e4a\u0e24\u0e84\u0eaa\u0ed9\u0ec9\u0f52"), 0.0, 0.0, 10.0, 1.0).withLabel(v -> (int)v <= 0 ? Deobf.decrypt(":B;\u001as\u00aa\u00b8") : (int)v + " tick"));
    public final BooleanSetting keepStays = this.addSetting(new BooleanSetting(Deobf.decrypt("8I-\u001e2\u0097\u00a0\u0085\u00bd\u015b\u013a\u0117\u0122\u0189\u01e0"), Deobf.decrypt("?I)\u0018w\u00e4\u00b8\u0082\u00ac\u015b\u0119\u010a\u0120\u019b\u01f6\u01d0\u01be\u0208\u021d\u025a\u0228\u02d9\u0280\u02cc\u02af\u0311\u030c\u031f\u0349\u03d5\u03f1\u03d6\u03b0\u03d3\u040f\u043a\u0407\u0468\u0489\u04b4\u04e8\u04ef\u0551\u0565\u0551\u0542\u05c1\u05f4\u0593\u05e6\u0618\u062c\u0655\u0651\u069d\u06f7\u06df\u0698\u0708\u077a\u074e\u0759\u079b\u0796\u0796\u078e\u07a0\u0848\u0812\u0819\u0829\u08c3\u08ba\u08cd\u08b6\u096a\u090d\u0906\u0925\u09c7\u09cf\u0989\u09ac\u0a02\u0a61\u0a0e\u0a6d\u0a89\u0ad7\u0ad0\u0aa3\u0b03\u0b5b\u0b5c\u0b3e\u0b97\u0bdf\u0be7\u0bf5\u0baa\u0c0b\u0c7e\u0c06\u0c48\u0cdd\u0cab\u0c97\u0ce6\u0d07\u0d31\u0d68\u0d66\u0dd5\u0dfb\u0dd1\u0d92\u0e53\u0e68\u0e09\u0e2d\u0e80\u0eb9\u0ec6\u0ec3\u0f52"), true));
    public final BooleanSetting chatFeedback = this.addSetting(new BooleanSetting(Deobf.decrypt("0D)\u001a2\u0082\u00a9\u008f\u00ad\u0119\u0108\u0100\u0128"), Deobf.decrypt("#^!\u0000f\u00e4\u00bb\u0082\u00a8\u010f\u0149\u0117\u012b\u0195\u01b3\u01c6\u01f7\u021c\u0216\u0250\u022e\u02d9\u029c\u02d1\u02fb\u031a\u0316\u0315\u034f\u03d7\u03b5\u0382\u03b6\u039d\u044c\u0426\u040e\u0479\u0487"), true));
    private final class_310 mc = class_310.method_1551();
    private Phase phase = Phase.IDLE;
    private int keepSlot = -1;
    private int containerId = -1;
    private final class_1799[] snapshot = new class_1799[9];
    private final int[] parked = new int[9];
    private final Deque<int[]> queue = new ArrayDeque<int[]>();
    private int delay = 0;

    public GambleRiggerModule() {
        super(Deobf.decrypt("4M%\f~\u00a1\u009e\u0083\u00ae\u011c\u010c\u0111"), Deobf.decrypt("!E/Ns\u00e4\u00a8\u0083\u00ba\u010b\u010c\u010d\u0130\u0195\u01e1\u019b\u01fa\u0209\u021e\u0245\u022c\u029c\u0287\u0282\u02bc\u031f\u0314\u031e\u034d\u03d5\u03b5\u23e2\u03f9\u03dc\u040f\u0428\u040e\u0466\u04cc\u04f7\u04f4\u04ed\u054a\u0570\u0515\u055c\u05c5\u05e8\u05d6\u05a2\u064b\u0628\u065d\u064d\u0682\u06f8\u06de\u0698\u0719\u077e\u075c\u0748\u079b\u0794\u0786\u0784\u07a5\u0857\u085e\u080f\u087f\u08cf\u08bc\u089d\u08b8\u0939\u092e\u090f\u0937\u09dd\u098d\u098c\u09ad\u0a4e\u0a79\u0a46\u0a61\u0add\u0a98\u0ad1\u0ab3\u0b57\u0b02\u0b47\u0b24\u0bd8\u0b94\u0be3\u0bfe\u0bbe\u0c07\u0c2c\u0c17\u0c53\u0ccc\u0caa\u0cc5\u0cf1\u0d42\u0d2e\u0d79\u0d68\u0dd1\u0dfb\u0dd1\u0d92\u0e4e\u0e74\u0e4c\u0e68\u0e88\u0eb1\u0ed3\u0ed9\u0f08\u0f33\u0f5c\u0f5f\u0f92\u0fc3\u0fb2\u0ff6\u0feb"), Category.MISC);
    }

    public Phase phase() {
        return this.phase;
    }

    public int keepSlot() {
        return this.keepSlot;
    }

    public int queued() {
        return this.queue.size();
    }

    public boolean busy() {
        return this.phase == Phase.EXTRACTING || this.phase == Phase.RESTORING;
    }

    public boolean canExtract() {
        return this.phase == Phase.IDLE && this.dispenserOpen();
    }

    public boolean canRestore() {
        return this.phase == Phase.EXTRACTED && this.dispenserOpen();
    }

    public void requestKeep(int slot) {
        int j;
        class_1703 class_17032;
        if (!this.canExtract() || slot < 0 || slot >= 9) {
            return;
        }
        class_746 p = this.mc.field_1724;
        if (p == null || !((class_17032 = p.field_7512) instanceof class_1716)) {
            return;
        }
        class_1716 menu = (class_1716)class_17032;
        this.containerId = menu.field_7763;
        this.keepSlot = slot;
        this.queue.clear();
        this.delay = 0;
        boolean[] reserved = new boolean[45];
        int fallback = 0;
        for (j = 0; j < 9; ++j) {
            this.snapshot[j] = menu.method_7611(j).method_7677().method_7972();
            this.parked[j] = -1;
        }
        for (j = 0; j < 9; ++j) {
            if (((Boolean)this.keepStays.get()).booleanValue() && j == this.keepSlot || this.snapshot[j].method_7960()) continue;
            int target = this.firstEmptyInv((class_1703)menu, reserved);
            if (target >= 0) {
                reserved[target] = true;
                this.parked[j] = target;
                this.queue.add(new int[]{0, j, target});
                continue;
            }
            ++fallback;
            this.queue.add(new int[]{1, j, 0});
        }
        if (this.queue.isEmpty()) {
            this.phase = Phase.EXTRACTED;
            this.feedback(Deobf.decrypt("\u00d4H\u000f\u000f\u007f\u00a6\u00a0\u008f\u009b\u0112\u010e\u0104\u0126\u0182\u01b3\u0113\u01a9\u02c0\u0251\u0252\u022e\u0290\u0291\u0282\u02be\u0313\u0309\u0308\u0358\u0390\u2381\u03d6\u03b7\u03d2\u045b\u0426\u0406\u0463\u04ce\u04f7\u04f3\u04ee\u0505\u0570\u0559\u0547\u05c1\u05aa\u0593\u05ad\u0607\u062a\u065b\u065f\u06d2\u063e\u06dc\u06ea\u0708\u0765\u0749\u0753\u07c9\u0781\u07d3\u074f\u07fe\u0853\u0816\u080f\u0867\u088a\u08aa\u088b\u08f6\u092f"));
            return;
        }
        this.phase = Phase.EXTRACTING;
        if (fallback > 0) {
            this.feedback("\u00a7eGambleRigger \u00a77\u00bb your inventory is nearly full \u2014 " + fallback + " stack(s) fall back to shift-click and may not restore exactly");
        }
    }

    public void requestRestore() {
        class_1716 menu;
        block8: {
            block7: {
                class_1703 class_17032;
                if (!this.canRestore()) {
                    return;
                }
                class_746 p = this.mc.field_1724;
                if (p == null || !((class_17032 = p.field_7512) instanceof class_1716)) break block7;
                menu = (class_1716)class_17032;
                if (menu.field_7763 == this.containerId) break block8;
            }
            this.abort(true);
            return;
        }
        this.queue.clear();
        this.delay = 0;
        boolean[] used = new boolean[45];
        int missing = 0;
        for (int j = 0; j < 9; ++j) {
            class_1799 want;
            if (j == this.keepSlot || (want = this.snapshot[j]) == null || want.method_7960()) continue;
            int src = this.findSource((class_1703)menu, want, j, used);
            if (src < 0) {
                ++missing;
                continue;
            }
            used[src] = true;
            this.queue.add(new int[]{0, src, j});
        }
        this.phase = Phase.RESTORING;
        if (missing > 0) {
            this.feedback("\u00a7eGambleRigger \u00a77\u00bb restoring, but \u00a7c" + missing + " \u00a77stack(s) were not found in your inventory");
        }
    }

    public void reset() {
        boolean had = this.phase != Phase.IDLE || this.keepSlot >= 0;
        this.resetState();
        if (had) {
            this.feedback(Deobf.decrypt("\u00d4\u001b\u000f\u000f\u007f\u00a6\u00a0\u008f\u009b\u0112\u010e\u0104\u0126\u0182\u01b3\u010f\u01be\u0209\u0214\u0246\u0239\u028d"));
        }
    }

    @Override
    public void onTick() {
        this.tickRigQueue();
    }

    private void tickRigQueue() {
        boolean instant;
        if (this.queue.isEmpty()) {
            this.finishIfDrained();
            return;
        }
        class_746 p = this.mc.field_1724;
        if (p == null || this.mc.field_1761 == null || !(p.field_7512 instanceof class_1716) || p.field_7512.field_7763 != this.containerId) {
            this.abort(true);
            return;
        }
        boolean bl = instant = this.clickDelay.getInt() <= 0;
        while (!this.queue.isEmpty()) {
            if (this.delay > 0) {
                --this.delay;
                return;
            }
            this.executeStep((class_1657)p, this.queue.poll());
            this.delay = this.clickDelay.getInt();
            if (instant) continue;
            return;
        }
        this.finishIfDrained();
    }

    private void finishIfDrained() {
        if (!this.queue.isEmpty()) {
            return;
        }
        if (this.phase == Phase.EXTRACTING) {
            this.phase = Phase.EXTRACTED;
            this.feedback("\u00a7aGambleRigger \u00a77\u00bb pulled the grid (kept \u00a7f#" + (this.keepSlot + 1) + "\u00a77) \u2014 click \u00a7fRestore \u00a77when ready");
        } else if (this.phase == Phase.RESTORING) {
            this.feedback("\u00a7aGambleRigger \u00a77\u00bb layout restored \u2014 slot \u00a7f#" + (this.keepSlot + 1) + " \u00a77left open");
            this.resetState();
        }
    }

    private void executeStep(class_1657 p, int[] step) {
        class_1703 menu = p.field_7512;
        if (step[0] == 1) {
            this.click(p, step[1], 0, class_1713.field_7794);
            return;
        }
        this.click(p, step[1], 0, class_1713.field_7790);
        if (!menu.method_34255().method_7960()) {
            this.click(p, step[2], 0, class_1713.field_7790);
        }
        if (!menu.method_34255().method_7960()) {
            this.click(p, step[1], 0, class_1713.field_7790);
        }
    }

    @Override
    protected void onDisable() {
        this.abort(false);
    }

    private boolean dispenserOpen() {
        class_746 p = this.mc.field_1724;
        return p != null && p.field_7512 instanceof class_1716;
    }

    private void click(class_1657 p, int slot, int button, class_1713 type) {
        this.mc.field_1761.method_2906(this.containerId, slot, button, type, p);
    }

    private int firstEmptyInv(class_1703 menu, boolean[] reserved) {
        for (int i = 9; i < 45; ++i) {
            if (reserved[i] || !menu.method_7611(i).method_7677().method_7960()) continue;
            return i;
        }
        return -1;
    }

    private int findSource(class_1703 menu, class_1799 want, int j, boolean[] used) {
        int i;
        int pk = this.parked[j];
        if (pk >= 9 && pk < 45 && !used[pk] && class_1799.method_7973((class_1799)menu.method_7611(pk).method_7677(), (class_1799)want)) {
            return pk;
        }
        for (i = 9; i < 45; ++i) {
            if (used[i] || !class_1799.method_7973((class_1799)menu.method_7611(i).method_7677(), (class_1799)want)) continue;
            return i;
        }
        for (i = 9; i < 45; ++i) {
            class_1799 s;
            if (used[i] || (s = menu.method_7611(i).method_7677()).method_7960() || !class_1799.method_31577((class_1799)s, (class_1799)want)) continue;
            return i;
        }
        return -1;
    }

    private void abort(boolean warn) {
        boolean wasActive = this.phase != Phase.IDLE;
        this.resetState();
        if (warn && wasActive) {
            this.feedback(Deobf.decrypt("\u00d4O\u000f\u000f\u007f\u00a6\u00a0\u008f\u009b\u0112\u010e\u0104\u0126\u0182\u01b3\u0113\u01a9\u02c0\u0251\u0254\u023e\u0296\u0287\u02d6\u02be\u031a\u0359\u0354\u0355\u03d8\u03f0\u03d6\u03ba\u03d2\u0441\u043a\u040e\u0464\u04c7\u04b2\u04f5\u04a1\u0546\u0568\u0557\u055f\u05c1\u05e2\u0593\u05a1\u0619\u0663\u065b\u065c\u0693\u06f7\u06dd\u06dd\u0709\u073f"));
        }
    }

    private void resetState() {
        this.phase = Phase.IDLE;
        this.keepSlot = -1;
        this.containerId = -1;
        this.queue.clear();
        this.delay = 0;
        for (int j = 0; j < 9; ++j) {
            this.snapshot[j] = null;
            this.parked[j] = -1;
        }
    }

    private void feedback(String msg) {
        if (!((Boolean)this.chatFeedback.get()).booleanValue()) {
            return;
        }
        class_746 p = this.mc.field_1724;
        if (p != null) {
            p.method_7353((class_2561)class_2561.method_43470((String)msg), false);
        }
    }

    public static enum Phase {
        IDLE,
        EXTRACTING,
        EXTRACTED,
        RESTORING;

    }
}

