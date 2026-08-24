/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  net.minecraft.class_2281
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2586
 *  net.minecraft.class_2595
 *  net.minecraft.class_2680
 *  net.minecraft.class_2745
 *  net.minecraft.class_2769
 *  net.minecraft.class_310
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.render.StorageEspRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.IconListSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2281;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2586;
import net.minecraft.class_2595;
import net.minecraft.class_2680;
import net.minecraft.class_2745;
import net.minecraft.class_2769;
import net.minecraft.class_310;

public class StorageEspModule
extends Module {
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt("1C0Na\u00b0\u00b5\u0086\u00ac\u015b\u217d\u0143\u012b\u019f\u01ff\u01d8\u01f1\u020c\u0251\u025a\u0229\u028d\u0299\u02cb\u02b5\u031b\u0359\u0313\u0353\u0390\u03e1\u0384\u03b8\u03d3\u045c\u0422\u041a\u046e\u04cc\u04b9\u04f3\u04a1\u0543\u056d\u0554\u0540"), Deobf.decrypt("<Y<\u0002{\u00aa\u00a9"), Deobf.decrypt("<Y<\u0002{\u00aa\u00a9"), Deobf.decrypt("5Y$\u0002")));
    public final SliderSetting range = this.addSetting(new SliderSetting(Deobf.decrypt("!M&\tw"), Deobf.decrypt(">M0Nv\u00ad\u00bf\u009e\u00a8\u0115\u010a\u0106\u0163\u0191\u01b3\u01d7\u01f1\u0215\u0205\u0254\u0235\u0297\u0290\u02d0\u02fb\u0317\u030a\u035c\u0349\u03d9\u03f2\u039e\u03b5\u03d4\u0448\u0426\u041b\u0468\u04cd"), 128.0, 16.0, 256.0, 8.0));
    public final SliderSetting highlightAlpha = this.addSetting(new SliderSetting(Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u0128\u010f\u0133\u0198\u01f2"), Deobf.decrypt("1C0N}\u00b4\u00ad\u0089\u00a0\u010f\u0110\u0143\u016b\u01c0\u01be\u0186\u01ab\u024e\u0258"), 200.0, 0.0, 255.0, 1.0));
    public final BooleanSetting tracers = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6\u00bf"), Deobf.decrypt("7^)\u00192\u00a8\u00a5\u0084\u00ac\u0108\u0149\u0105\u0131\u019f\u01fe\u0194\u01ea\u0213\u0214\u0215\u023f\u028b\u029a\u02d1\u02a8\u0316\u0318\u0315\u0353\u0390\u03e1\u0399\u03f9\u03d8\u044e\u042d\u0407\u042d\u04ca\u04b8\u04e9\u04f5\u0544\u056d\u0556\u0549\u05d6"), false));
    public final IconListSetting containers = this.addSetting(new IconListSetting(Deobf.decrypt("0C&\u001as\u00ad\u00a2\u008f\u00bb\u0108"), Deobf.decrypt("$D!\rz\u00e4\u00af\u0085\u00a7\u010f\u0108\u010a\u012d\u0195\u01e1\u0194\u01ea\u0202\u0201\u0250\u022f\u02d9\u0281\u02cd\u02fb\u0316\u0310\u031b\u0349\u03dc\u03fc\u0391\u03b1\u03c9")));
    public final BooleanSetting hideOpened;
    private final Set<class_2338> interactedBlocks = new HashSet<class_2338>();

    public StorageEspModule() {
        super(Deobf.decrypt(" X'\u001cs\u00a3\u00a9\u00af\u009a\u012b"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u0108\u0149\u0100\u012b\u0195\u01e0\u01c0\u01ed\u0257\u0251\u0257\u023d\u028b\u0287\u02c7\u02b7\u030d\u0355\u035c\u0352\u03d8\u03e0\u039a\u03b2\u03d8\u045d\u043d"), Category.RENDER);
        for (StorageType type : StorageType.values()) {
            this.containers.add(type.key, type.label, type.icon, type.defaultEnabled, type.defaultColor);
        }
        this.hideOpened = this.addSetting(new BooleanSetting(Deobf.decrypt(";E,\u000b2\u008b\u00bc\u008f\u00a7\u011e\u010d"), Deobf.decrypt("7C&If\u00e4\u00a4\u0083\u00ae\u0113\u0105\u010a\u0124\u0198\u01e7\u0194\u01fd\u0214\u021f\u0241\u023d\u0290\u029b\u02c7\u02a9\u030d\u0359\u0305\u034e\u03c5\u03b2\u0380\u03bc\u039d\u044e\u0422\u041d\u0468\u04c8\u04b3\u04fe\u04a1\u054a\u0574\u055d\u0542\u05c1\u05e2"), false));
    }

    public boolean isTypeEnabled(StorageType type) {
        return this.containers.isEnabled(type.key);
    }

    public int colorFor(StorageType type) {
        return this.containers.color(type.key);
    }

    public boolean hideOpened() {
        return (Boolean)this.hideOpened.get();
    }

    public boolean isInteracted(int x, int y, int z) {
        return !this.interactedBlocks.isEmpty() && this.interactedBlocks.contains(new class_2338(x, y, z));
    }

    public void trackInteraction(class_2338 pos) {
        class_2745 ct;
        class_2680 state;
        if (pos == null) {
            return;
        }
        class_310 mc = class_310.method_1551();
        if (mc.field_1687 == null) {
            return;
        }
        this.interactedBlocks.add(pos.method_10062());
        class_2586 be = mc.field_1687.method_8321(pos);
        if (be instanceof class_2595 && (state = mc.field_1687.method_8320(pos)).method_26204() instanceof class_2281 && (ct = (class_2745)state.method_11654((class_2769)class_2281.field_10770)) != class_2745.field_12569) {
            class_2350 facing = (class_2350)state.method_11654((class_2769)class_2281.field_10768);
            class_2338 other = pos.method_10093(ct == class_2745.field_12574 ? facing.method_10170() : facing.method_10160());
            this.interactedBlocks.add(other);
        }
    }

    @Override
    protected void onEnable() {
        this.interactedBlocks.clear();
    }

    @Override
    public void onTick() {
        StorageEspRenderer.scan(this);
    }

    @Override
    protected void onDisable() {
        StorageEspRenderer.clear();
    }

    public static enum StorageType {
        CHEST(Deobf.decrypt("\u0010D-\u001df"), Deobf.decrypt("0D-\u001df"), class_1802.field_8106, -22016, true),
        TRAPPED(Deobf.decrypt("\u0007^)\u001eb\u00a1\u00a8"), Deobf.decrypt("'^)\u001eb\u00a1\u00a8\u00ca\u008a\u0113\u010c\u0110\u0137"), class_1802.field_8247, -65536, true),
        ENDER(Deobf.decrypt("\u0016B,\u000b`"), Deobf.decrypt("6B,\u000b`\u00e4\u008f\u0082\u00ac\u0108\u011d"), class_1802.field_8466, -8912641, true),
        SHULKER(Deobf.decrypt("\u0000D=\u0002y\u00a1\u00be"), Deobf.decrypt(" D=\u0002y\u00a1\u00be\u00ca\u008b\u0114\u0111"), class_1802.field_8545, -47873, true),
        BARREL(Deobf.decrypt("\u0011M:\u001cw\u00a8"), Deobf.decrypt("1M:\u001cw\u00a8"), class_1802.field_16307, -7842560, true),
        SPAWNER(Deobf.decrypt("\u0000\\)\u0019|\u00a1\u00be"), Deobf.decrypt(" \\)\u0019|\u00a1\u00be"), class_1802.field_8849, -16711936, true),
        HOPPER(Deobf.decrypt("\u001bC8\u001ew\u00b6"), Deobf.decrypt(";C8\u001ew\u00b6"), class_1802.field_8239, -7829368, false),
        FURNACE(Deobf.decrypt("\u0015Y:\u0000s\u00a7\u00a9"), Deobf.decrypt("5Y:\u0000s\u00a7\u00a9"), class_1802.field_8732, -7566196, false);

        final String key;
        final String label;
        final class_1792 icon;
        final int defaultColor;
        final boolean defaultEnabled;

        private StorageType(String key, String label, class_1792 icon, int defaultColor, boolean defaultEnabled) {
            this.key = key;
            this.label = label;
            this.icon = icon;
            this.defaultColor = defaultColor;
            this.defaultEnabled = defaultEnabled;
        }
    }
}

