/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.util.Colors;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class CustomAccessoriesModule
extends Module {
    public final ColorSetting color = this.addSetting(new ColorSetting(Deobf.decrypt("0C$\u0001`"), Deobf.decrypt("1M;\u000b2\u00b0\u00a5\u0084\u00bd\u015b\u010f\u010c\u0131\u01d0\u01f6\u01c2\u01fb\u0209\u0208\u0215\u023d\u029a\u0296\u02c7\u02a8\u030d\u0316\u030e\u0358"), -49508));
    public final BooleanSetting rainbow = this.addSetting(new BooleanSetting(Deobf.decrypt("!M!\u0000p\u00ab\u00bb"), Deobf.decrypt("0U+\u0002w\u00e4\u00a9\u009c\u00ac\u0109\u0110\u0143\u0122\u0193\u01f0\u01d1\u01ed\u0208\u021e\u0247\u0225\u02d9\u0281\u02ca\u02a9\u0311\u030c\u031b\u0349\u0390\u03e1\u039e\u03bc\u039d\u045d\u042f\u0406\u0463\u04cb\u04b8\u04f0"), false));
    public final SliderSetting glow = this.addSetting(new SliderSetting(Deobf.decrypt("4@'\u0019"), Deobf.decrypt(" C.\u001a2\u00ab\u00b9\u009e\u00ac\u0109\u0144\u010b\u0122\u019c\u01fc\u0194\u01f7\u0215\u0205\u0250\u0232\u028a\u029c\u02d6\u02a2"), 70.0, 0.0, 100.0, 5.0, Deobf.decrypt("V")));
    public final BooleanSetting firstPerson = this.addSetting(new BooleanSetting(Deobf.decrypt("5E:\u001df\u00e4\u009c\u008f\u00bb\u0108\u0106\u010d"), Deobf.decrypt("2@;\u00012\u00b7\u00a4\u0085\u00be\u015b\u0110\u010c\u0136\u0182\u01b3\u01d5\u01fd\u0218\u0214\u0246\u022f\u0296\u0287\u02cb\u02be\u030d\u0359\u0315\u034f\u0390\u03f3\u039f\u03ab\u03ce\u045b\u0463\u041f\u0468\u04db\u04a4\u04e8\u04ef\u0505\u0572\u0551\u0549\u05d3"), false));
    public final BooleanSetting cape = this.addSetting(new BooleanSetting(Deobf.decrypt("0M8\u000b"), Deobf.decrypt("2\f.\u0002}\u00b3\u00a5\u0084\u00ae\u015b\u010a\u010f\u012c\u0184\u01fb\u0194\u01fd\u021a\u0201\u0250\u027c\u029d\u029a\u02d5\u02b5\u035e\u0300\u0313\u0354\u03c2\u03b5\u0394\u03b8\u03de\u0444"), true));
    public final ModeSetting capeStyle = this.addSetting(new ModeSetting(Deobf.decrypt("0M8\u000b2\u0097\u00b8\u0093\u00a5\u011e"), Deobf.decrypt("0M8\u000b2\u00a8\u00a3\u0085\u00a2"), Deobf.decrypt("E\u001b"), Deobf.decrypt("E\u001b"), Deobf.decrypt("$M>\u000b"), Deobf.decrypt("4^!\n"), Deobf.decrypt(" C$\u0007v")));
    public final BooleanSetting capePhysics = this.addSetting(new BooleanSetting(Deobf.decrypt("0M8\u000b2\u0094\u00a4\u0093\u00ba\u0112\u010a\u0110"), Deobf.decrypt(" [)\u00172\u00e2\u00ec\u0088\u00a0\u0117\u0105\u010c\u0134\u01d0\u01e4\u01dd\u01ea\u0213\u0251\u024c\u0233\u028c\u0287\u0282\u02b6\u0311\u030f\u0319\u034c\u03d5\u03fb\u0382"), true));
    public final BooleanSetting trail = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\u0007~"), Deobf.decrypt("2\f/\u0002}\u00b3\u00a5\u0084\u00ae\u015b\u011d\u0111\u0122\u0199\u01ff\u0194\u01f2\u021e\u0217\u0241\u027c\u029b\u0290\u02ca\u02b2\u0310\u031d\u035c\u0340\u03c3\u03b5\u038f\u03b6\u03c8\u040f\u0423\u0400\u047b\u04cc"), true));
    public final ModeSetting trailStyle = this.addSetting(new ModeSetting(Deobf.decrypt("'^)\u0007~\u00e4\u009f\u009e\u00b0\u0117\u010c"), Deobf.decrypt("'^)\u0007~\u00e4\u00a0\u0085\u00a6\u0110"), Deobf.decrypt("!E*\f}\u00aa"), Deobf.decrypt("!E*\f}\u00aa"), Deobf.decrypt(" \\)\u001cy\u00a8\u00a9"), Deobf.decrypt("6O \u0001")));
    public final SliderSetting trailLength = this.addSetting(new SliderSetting(Deobf.decrypt("'^)\u0007~\u00e4\u0080\u008f\u00a7\u011c\u011d\u010b"), Deobf.decrypt(";C?N~\u00ab\u00a2\u008d\u00e9\u010f\u0101\u0106\u0163\u0184\u01e1\u01d5\u01f7\u0217\u0251\u0259\u0235\u0297\u0292\u02c7\u02a9\u030d"), 1.2, 0.2, 4.0, 0.1, Deobf.decrypt("\u0000")));
    public final BooleanSetting aura = this.addSetting(new BooleanSetting(Deobf.decrypt("2Y:\u000f"), Deobf.decrypt("2Bh\u0001`\u00a6\u00a5\u009e\u00a0\u0115\u010e\u0143\u0122\u0185\u01e1\u01d5\u01be\u021a\u0203\u025a\u0229\u0297\u0291\u0282\u02a2\u0311\u030c\u030e\u0301\u03d6\u03f0\u0393\u03ad"), false));
    public final ModeSetting auraStyle = this.addSetting(new ModeSetting(Deobf.decrypt("2Y:\u000f2\u0097\u00b8\u0093\u00a5\u011e"), Deobf.decrypt("2Y:\u000f2\u00a8\u00a3\u0085\u00a2"), Deobf.decrypt("<^*\u0007f"), Deobf.decrypt("<^*\u0007f"), Deobf.decrypt("!E&\t")));
    public final BooleanSetting crown = this.addSetting(new BooleanSetting(Deobf.decrypt("0^'\u0019|"), Deobf.decrypt("2\f.\u0002}\u00a5\u00b8\u0083\u00a7\u011c\u0145\u0143\u0130\u0180\u01fa\u01da\u01f0\u0212\u021f\u0252\u027c\u02cf\u02c2\u0282\u02ba\u031c\u0316\u030a\u0344\u0390\u03ec\u0399\u03ac\u03cf\u040f\u0426\u040a\u046c\u04cd"), false));
    private final Deque<TrailNode> trailNodes = new ArrayDeque<TrailNode>();
    private static final int MAX_TRAIL = 256;

    public CustomAccessoriesModule() {
        super(Deobf.decrypt("0Y;\u001a}\u00a9\u008d\u0089\u00aa\u011e\u011a\u0110\u012c\u0182\u01fa\u01d1\u01ed"), Deobf.decrypt("0@!\u000b|\u00b0\u00e1\u0099\u00a0\u011f\u010c\u0143\u0120\u019f\u01e0\u01d9\u01fb\u020f\u0218\u0256\u022f\u02d9\u22e1\u0282\u02b8\u031f\u0309\u0319\u030d\u0390\u03e1\u0384\u03b8\u03d4\u0443\u0462\u044f\u046c\u04dc\u04a5\u04e6\u04a1\u0503\u0524\u055b\u055e\u05cb\u05f1\u05dd"), Category.VISUALS);
        this.capeStyle.visibleWhen(this.cape::get);
        this.capePhysics.visibleWhen(this.cape::get);
        this.trailStyle.visibleWhen(this.trail::get);
        this.trailLength.visibleWhen(this.trail::get);
        this.auraStyle.visibleWhen(this.aura::get);
    }

    public Deque<TrailNode> trailNodes() {
        return this.trailNodes;
    }

    @Override
    public void onTick() {
        if (!((Boolean)this.trail.get()).booleanValue()) {
            if (!this.trailNodes.isEmpty()) {
                this.trailNodes.clear();
            }
            return;
        }
        class_746 player = class_310.method_1551().field_1724;
        if (player == null) {
            return;
        }
        double x = player.method_23317();
        double y = player.method_23318() + (double)player.method_17682() * 0.5;
        double z = player.method_23321();
        this.trailNodes.addLast(new TrailNode(x, y, z, System.nanoTime()));
        long cutoff = System.nanoTime() - (long)((double)Math.max(0.2f, this.trailLength.getFloat()) * 1.4E9);
        while (!this.trailNodes.isEmpty() && this.trailNodes.peekFirst().nanos < cutoff) {
            this.trailNodes.removeFirst();
        }
        while (this.trailNodes.size() > 256) {
            this.trailNodes.removeFirst();
        }
    }

    @Override
    protected void onDisable() {
        this.clear();
    }

    public void clear() {
        this.trailNodes.clear();
    }

    public int currentRgb() {
        if (((Boolean)this.rainbow.get()).booleanValue()) {
            float hue = (float)(System.currentTimeMillis() % 4000L) / 4000.0f * 360.0f;
            return Colors.hsvToRgb(hue, 0.8f, 1.0f) & 0xFFFFFF;
        }
        return (Integer)this.color.get() & 0xFFFFFF;
    }

    public float glowStrength() {
        return this.glow.getFloat() / 100.0f;
    }

    public static final class TrailNode {
        public final double x;
        public final double y;
        public final double z;
        public final long nanos;

        TrailNode(double x, double y, double z, long nanos) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.nanos = nanos;
        }

        public float ageSeconds(long now) {
            return (float)(now - this.nanos) / 1.0E9f;
        }
    }
}

