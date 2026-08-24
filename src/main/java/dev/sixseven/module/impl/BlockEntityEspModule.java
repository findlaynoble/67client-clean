/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2338$class_2339
 *  net.minecraft.class_2343
 *  net.minecraft.class_2586
 *  net.minecraft.class_2591
 *  net.minecraft.class_2680
 *  net.minecraft.class_2818
 *  net.minecraft.class_2826
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_4970$class_4971
 *  net.minecraft.class_638
 *  net.minecraft.class_746
 *  net.minecraft.class_7923
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.IconListSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2343;
import net.minecraft.class_2586;
import net.minecraft.class_2591;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4970;
import net.minecraft.class_638;
import net.minecraft.class_746;
import net.minecraft.class_7923;

public class BlockEntityEspModule
extends Module {
    private static final String OTHER = "other";
    private static final int MAX_ENTRIES = 8192;
    public final IconListSetting blockEntities = this.addSetting(new IconListSetting(Deobf.decrypt("1@'\ry\u00e4\u0089\u0084\u00bd\u0112\u011d\u010a\u0126\u0183"), Deobf.decrypt("$D!\rz\u00e4\u00ae\u0086\u00a6\u0118\u0102\u014e\u0126\u019e\u01e7\u01dd\u01ea\u0202\u0251\u0241\u0225\u0289\u0290\u02d1\u02fb\u030a\u0316\u035c\u0349\u03d9\u03f2\u039e\u03b5\u03d4\u0448\u0426\u041b")));
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt("1C0Na\u00b0\u00b5\u0086\u00ac\u015b\u217d\u0143\u012b\u019f\u01ff\u01d8\u01f1\u020c\u0251\u025a\u0229\u028d\u0299\u02cb\u02b5\u031b\u0359\u0313\u0353\u0390\u03e1\u0384\u03b8\u03d3\u045c\u0422\u041a\u046e\u04cc\u04b9\u04f3\u04a1\u0543\u056d\u0554\u0540"), Deobf.decrypt("5Y$\u0002"), Deobf.decrypt("5Y$\u0002"), Deobf.decrypt("<Y<\u0002{\u00aa\u00a9")));
    public final SliderSetting range = this.addSetting(new SliderSetting(Deobf.decrypt("!M&\tw"), Deobf.decrypt(">M0Nv\u00ad\u00bf\u009e\u00a8\u0115\u010a\u0106\u0163\u0191\u01b3\u01d6\u01f2\u0214\u0212\u025e\u027c\u029c\u029b\u02d6\u02b2\u030a\u0300\u035c\u0348\u03c3\u03b5\u039e\u03b0\u03da\u0447\u0422\u0406\u046a\u04c1\u04a3\u04e2\u04e5"), 128.0, 16.0, 512.0, 8.0));
    public final SliderSetting highlightAlpha = this.addSetting(new SliderSetting(Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u0128\u010f\u0133\u0198\u01f2"), Deobf.decrypt("1C0N}\u00b4\u00ad\u0089\u00a0\u010f\u0110\u0143\u016b\u01c0\u01be\u0186\u01ab\u024e\u0258"), 180.0, 0.0, 255.0, 1.0));
    public final BooleanSetting tracers = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6\u00bf"), Deobf.decrypt("7^)\u00192\u00a8\u00a5\u0084\u00ac\u0108\u0149\u0105\u0131\u019f\u01fe\u0194\u01ea\u0213\u0214\u0215\u023f\u028b\u029a\u02d1\u02a8\u0316\u0318\u0315\u0353\u0390\u03e1\u0399\u03f9\u03d8\u044e\u042d\u0407\u042d\u04cb\u04bb\u04e8\u04e2\u054e\u0524\u055d\u0542\u05d0\u05ef\u05c7\u05b7"), false));
    public final BooleanSetting showGhosts = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\u00192\u0083\u00a4\u0085\u00ba\u010f\u011a"), Deobf.decrypt("8I-\u001e2\u00a1\u00a2\u009e\u00bb\u0112\u010c\u0110\u0163\u0184\u01fb\u01d1\u01be\u0208\u0214\u0247\u022a\u029c\u0287\u0282\u02a8\u031b\u0317\u0308\u0301\u03d2\u03e0\u0382\u03f9\u03c9\u0447\u042f\u041b\u042d\u04c8\u04a5\u04e2\u04a1\u0542\u056b\u0556\u0549\u0584\u05e5\u05df\u05a7\u060e\u062d\u064c\u0619\u0681\u06f0\u06de\u06dd"), true));
    public final ColorSetting ghostTint = this.addSetting(new ColorSetting(Deobf.decrypt("4D'\u001df\u00e4\u0098\u0083\u00a7\u010f"), Deobf.decrypt("0C$\u0001`\u00e4\u00ae\u0086\u00ac\u0115\u010d\u0106\u0127\u01d0\u01fa\u01da\u01ea\u0214\u0251\u0252\u0234\u0296\u0286\u02d6\u02fb\u031b\u0317\u0308\u0353\u03d9\u03f0\u0385"), -922791856));
    public final BooleanSetting chunkPackets = this.addSetting(new BooleanSetting(Deobf.decrypt("0D=\u0000y\u00e4\u009c\u008b\u00aa\u0110\u010c\u0117\u0130"), Deobf.decrypt("!I)\n2\u00a6\u00a0\u0085\u00aa\u0110\u0149\u0106\u012d\u0184\u01fa\u01c0\u01f7\u021e\u0202\u0215\u023a\u028b\u029a\u02cf\u02fb\u031d\u0311\u0309\u034f\u03db\u03b8\u0392\u03b8\u03c9\u044e\u046e\u041f\u046c\u04ca\u04bc\u04e2\u04f5\u0556"), true));
    public final BooleanSetting beUpdatePackets = this.addSetting(new BooleanSetting(Deobf.decrypt("1ih;b\u00a0\u00ad\u009e\u00ac\u015b\u0139\u0102\u0120\u019b\u01f6\u01c0\u01ed"), Deobf.decrypt("!I)\n2\u00a6\u00a0\u0085\u00aa\u0110\u0149\u0106\u012d\u0184\u01fa\u01c0\u01f7\u021e\u0202\u0215\u023a\u028b\u029a\u02cf\u02fb\u031c\u0315\u0313\u0342\u03db\u03b8\u0393\u03b7\u03c9\u0446\u043a\u0416\u042d\u04dc\u04a7\u04e3\u04e0\u0551\u0561\u0518\u055c\u05c5\u05e5\u05d8\u05ab\u061f\u0630"), true));
    public final BooleanSetting worldRescan = this.addSetting(new BooleanSetting(Deobf.decrypt("$C:\u0002v\u00e4\u009e\u008f\u00ba\u0118\u0108\u010d"), Deobf.decrypt("2@;\u00012\u00b7\u00a2\u008b\u00b9\u0108\u0101\u010c\u0137\u01d0\u01f2\u01d8\u01ec\u021e\u0210\u0251\u0225\u02d4\u0299\u02cd\u02ba\u031a\u031c\u0318\u0301\u03d3\u03fd\u0383\u03b7\u03d6\u045c\u046e\u0418\u0465\u04cc\u04b9\u04a7\u04e4\u054b\u0565\u055a\u0540\u05c1\u05e2"), true));
    private final Map<String, String> aliases = new HashMap<String, String>();
    private final Map<Long, Cached> cache = new ConcurrentHashMap<Long, Cached>();

    public BlockEntityEspModule() {
        super(Deobf.decrypt("1@'\ry\u0081\u00a2\u009e\u00a0\u010f\u0110\u0126\u0110\u01a0"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u0108\u0149\u0101\u012f\u019f\u01f0\u01df\u01be\u021e\u021f\u0241\u0235\u028d\u029c\u02c7\u02a8\u035e\u031f\u030e\u034e\u03dd\u03b5\u0384\u03b8\u03ca\u040f\u043e\u040e\u046e\u04c2\u04b2\u04f3\u04f2"), Category.RENDER);
        this.ghostTint.visibleWhen(this.showGhosts::get);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010a\u010b\u0126\u0183\u01e7"), Deobf.decrypt("0D-\u001df"), class_1802.field_8106, -22016, true);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011d\u0111\u0122\u0180\u01e3\u01d1\u01fa\u0224\u0212\u025d\u0239\u028a\u0281"), Deobf.decrypt("'^)\u001eb\u00a1\u00a8\u00ca\u008a\u0113\u010c\u0110\u0137"), class_1802.field_8247, -65536, true);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010c\u010d\u0127\u0195\u01e1\u01eb\u01fd\u0213\u0214\u0246\u0228"), Deobf.decrypt("6B,\u000b`\u00e4\u008f\u0082\u00ac\u0108\u011d"), class_1802.field_8466, -8912641, true);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011a\u010b\u0136\u019c\u01f8\u01d1\u01ec\u0224\u0213\u025a\u0224"), Deobf.decrypt(" D=\u0002y\u00a1\u00be\u00ca\u008b\u0114\u0111"), class_1802.field_8545, -47873, true);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010b\u0102\u0131\u0182\u01f6\u01d8"), Deobf.decrypt("1M:\u001cw\u00a8"), class_1802.field_16307, -7842560, true);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u0104\u010c\u0121\u01af\u01e0\u01c4\u01ff\u020c\u021f\u0250\u022e"), Deobf.decrypt(" \\)\u0019|\u00a1\u00be"), class_1802.field_8849, -16711936, true);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u0101\u010c\u0133\u0180\u01f6\u01c6"), Deobf.decrypt(";C8\u001ew\u00b6"), class_1802.field_8239, -7829368, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010f\u0116\u0131\u019e\u01f2\u01d7\u01fb"), Deobf.decrypt("5Y:\u0000s\u00a7\u00a9"), class_1802.field_8732, -7566196, false);
        this.alias(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010b\u010f\u0122\u0183\u01e7\u01eb\u01f8\u020e\u0203\u025b\u023d\u029a\u0290"), Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010f\u0116\u0131\u019e\u01f2\u01d7\u01fb"));
        this.alias(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011a\u010e\u012c\u019b\u01f6\u01c6"), Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010f\u0116\u0131\u019e\u01f2\u01d7\u01fb"));
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010d\u010a\u0130\u0180\u01f6\u01da\u01ed\u021e\u0203"), Deobf.decrypt("7E;\u001ew\u00aa\u00bf\u008f\u00bb"), class_1802.field_8357, -10066330, false);
        this.alias(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010d\u0111\u012c\u0180\u01e3\u01d1\u01ec"), Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010d\u010a\u0130\u0180\u01f6\u01da\u01ed\u021e\u0203"));
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010b\u0111\u0126\u0187\u01fa\u01da\u01f9\u0224\u0202\u0241\u023d\u0297\u0291"), Deobf.decrypt("1^-\u0019{\u00aa\u00ab\u00ca\u009a\u010f\u0108\u010d\u0127"), class_1802.field_8740, -3372801, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010b\u0106\u0126\u0198\u01fa\u01c2\u01fb"), Deobf.decrypt("1I-\u0006{\u00b2\u00a9"), class_1802.field_20416, -13312, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010c\u010d\u0120\u0198\u01f2\u01da\u01ea\u0212\u021f\u0252\u0203\u028d\u0294\u02c0\u02b7\u031b"), Deobf.decrypt("6B+\u0006s\u00aa\u00b8\u0083\u00a7\u011c\u0149\u0137\u0122\u0192\u01ff\u01d1"), class_1802.field_8657, -7864065, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011a\u010a\u0124\u019e"), Deobf.decrypt(" E/\u0000"), class_1802.field_8788, -3355444, false);
        this.alias(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u0101\u0102\u012d\u0197\u01fa\u01da\u01f9\u0224\u0202\u025c\u023b\u0297"), Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011a\u010a\u0124\u019e"));
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010b\u0106\u0127"), Deobf.decrypt("1I,"), class_1802.field_8789, -30584, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011a\u0108\u0136\u019c\u01ff"), Deobf.decrypt(" G=\u0002~"), class_1802.field_8398, -2236963, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010b\u0102\u012d\u019e\u01f6\u01c6"), Deobf.decrypt("1M&\u0000w\u00b6"), class_1802.field_8539, -1118482, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u010a\u0111\u0122\u0196\u01e7\u01d1\u01ec"), Deobf.decrypt("0^)\bf\u00a1\u00be"), class_1802.field_46791, -12276993, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011f\u0102\u0136\u019c\u01e7"), Deobf.decrypt("%M=\u0002f"), class_1802.field_48847, -10496, false);
        this.type(Deobf.decrypt("\u001eE&\u000bq\u00b6\u00ad\u008c\u00bd\u0141\u011d\u0111\u012a\u0191\u01ff\u01eb\u01ed\u020b\u0210\u0242\u0232\u029c\u0287"), Deobf.decrypt("'^!\u000f~\u00e4\u009f\u009a\u00a8\u010c\u0107\u0106\u0131"), class_1802.field_47314, -16711766, false);
        this.type(Deobf.decrypt("\u001cX \u000b`"), Deobf.decrypt("<X \u000b`"), class_1802.field_8542, -5592406, true);
    }

    private void type(String id, String label, class_1792 icon, int color, boolean on) {
        this.blockEntities.add(id, label, icon, on, color);
    }

    private void alias(String from, String to) {
        this.aliases.put(from, to);
    }

    private String canonicalKey(String typeId) {
        if (this.blockEntities.get(typeId) != null) {
            return typeId;
        }
        String aliased = this.aliases.get(typeId);
        if (aliased != null) {
            return aliased;
        }
        int slash = typeId.indexOf(58);
        if (slash >= 0) {
            String path = typeId.substring(slash + 1);
            String full = "minecraft:" + path;
            if (this.blockEntities.get(full) != null) {
                return full;
            }
            if (this.aliases.containsKey(full)) {
                return this.aliases.get(full);
            }
        }
        return Deobf.decrypt("\u001cX \u000b`");
    }

    public boolean chunkPacketsEnabled() {
        return (Boolean)this.chunkPackets.get();
    }

    public boolean beUpdatePacketsEnabled() {
        return (Boolean)this.beUpdatePackets.get();
    }

    public void record(class_2338 pos, class_2591<?> type) {
        if (pos == null || type == null) {
            return;
        }
        class_2960 id = class_7923.field_41181.method_10221(type);
        String key = this.canonicalKey(id != null ? id.toString() : String.valueOf(type));
        this.cache.put(pos.method_10063(), new Cached(pos.method_10062(), key, System.currentTimeMillis()));
        if (this.cache.size() > 8192) {
            this.pruneOldest();
        }
    }

    private void pruneOldest() {
        long oldestTime = Long.MAX_VALUE;
        Long oldestKey = null;
        for (Map.Entry<Long, Cached> e : this.cache.entrySet()) {
            if (e.getValue().lastSeenMs() >= oldestTime) continue;
            oldestTime = e.getValue().lastSeenMs();
            oldestKey = e.getKey();
        }
        if (oldestKey != null) {
            this.cache.remove(oldestKey);
        }
    }

    public Collection<Cached> entries() {
        return this.cache.values();
    }

    public void clear() {
        this.cache.clear();
    }

    public int cachedCount() {
        return this.cache.size();
    }

    @Override
    protected void onEnable() {
        this.cache.clear();
        if (((Boolean)this.worldRescan.get()).booleanValue()) {
            this.rescanLoadedChunks();
        }
    }

    @Override
    protected void onDisable() {
        this.cache.clear();
    }

    @Override
    public void onTick() {
        class_310 mc = class_310.method_1551();
        class_746 player = mc.field_1724;
        if (mc.field_1687 == null || player == null) {
            return;
        }
        double r = (Double)this.range.get();
        double maxSq = r * r;
        double px = player.method_23317();
        double py = player.method_23318();
        double pz = player.method_23321();
        this.cache.values().removeIf(c -> {
            double dz;
            double dy;
            class_2338 p = c.pos();
            double dx = (double)p.method_10263() + 0.5 - px;
            return dx * dx + (dy = (double)p.method_10264() + 0.5 - py) * dy + (dz = (double)p.method_10260() + 0.5 - pz) * dz > maxSq;
        });
    }

    private void rescanLoadedChunks() {
        class_310 mc = class_310.method_1551();
        class_638 level = mc.field_1687;
        class_746 player = mc.field_1724;
        if (level == null || player == null) {
            return;
        }
        int radius = Math.min(32, (int)Math.ceil((Double)this.range.get() / 16.0) + 2);
        int pcx = player.method_31476().field_9181;
        int pcz = player.method_31476().field_9180;
        class_2338.class_2339 p = new class_2338.class_2339();
        for (int cx = pcx - radius; cx <= pcx + radius; ++cx) {
            for (int cz = pcz - radius; cz <= pcz + radius; ++cz) {
                if (!level.method_2935().method_12123(cx, cz)) continue;
                class_2818 chunk = level.method_8497(cx, cz);
                class_2826[] sections = chunk.method_12006();
                int minSectionY = chunk.method_32891();
                int baseX = chunk.method_12004().method_8326();
                int baseZ = chunk.method_12004().method_8328();
                for (int s = 0; s < sections.length; ++s) {
                    class_2826 section = sections[s];
                    if (section.method_38292() || !section.method_19523(class_4970.class_4971::method_31709)) continue;
                    int baseY = minSectionY + s << 4;
                    for (int y = 0; y < 16; ++y) {
                        for (int z = 0; z < 16; ++z) {
                            for (int x = 0; x < 16; ++x) {
                                class_2248 class_22482;
                                class_2680 state = section.method_12254(x, y, z);
                                if (!state.method_31709() || !((class_22482 = state.method_26204()) instanceof class_2343)) continue;
                                class_2343 eb = (class_2343)class_22482;
                                p.method_10103(baseX + x, baseY + y, baseZ + z);
                                try {
                                    class_2586 be = eb.method_10123(p.method_10062(), state);
                                    if (be == null) continue;
                                    this.record((class_2338)p, be.method_11017());
                                    continue;
                                }
                                catch (Exception exception) {
                                    // empty catch block
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public record Cached(class_2338 pos, String typeKey, long lastSeenMs) {
    }
}

