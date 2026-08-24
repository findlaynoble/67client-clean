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
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class RegionMapModule
extends Module {
    public static final int MAP_SIZE = 9;
    private static final double REGION_SIZE = 50000.0;
    private static final double MAP_OFFSET = 225000.0;
    private static final String[] TYPE_NAMES = new String[]{Deobf.decrypt("6ye-"), Deobf.decrypt("6ye9"), Deobf.decrypt("=me+"), Deobf.decrypt("=me9"), Deobf.decrypt("2_!\u000f"), Deobf.decrypt("<O-")};
    private static final int[] TYPE_COLORS = new int[]{RegionMapModule.rgb(159, 206, 99), RegionMapModule.rgb(0, 166, 99), RegionMapModule.rgb(79, 173, 234), RegionMapModule.rgb(47, 110, 186), RegionMapModule.rgb(245, 194, 66), RegionMapModule.rgb(252, 136, 3)};
    private static final int[][] LAYOUT = new int[][]{{82, 5}, {100, 3}, {101, 3}, {102, 3}, {103, 2}, {104, 2}, {105, 2}, {106, 2}, {91, 2}, {83, 5}, {44, 3}, {75, 3}, {42, 3}, {41, 2}, {40, 2}, {39, 2}, {38, 2}, {92, 2}, {84, 5}, {45, 3}, {14, 3}, {13, 3}, {12, 2}, {11, 2}, {10, 2}, {37, 2}, {93, 2}, {85, 5}, {46, 5}, {74, 5}, {3, 3}, {2, 2}, {1, 2}, {25, 2}, {36, 2}, {94, 2}, {86, 4}, {47, 4}, {72, 4}, {71, 4}, {5, 2}, {4, 2}, {24, 2}, {35, 2}, {95, 2}, {87, 4}, {51, 1}, {17, 1}, {9, 0}, {8, 0}, {7, 0}, {23, 0}, {34, 0}, {96, 2}, {88, 4}, {54, 1}, {18, 1}, {61, 0}, {62, 0}, {21, 0}, {22, 0}, {33, 0}, {97, 0}, {89, 0}, {26, 1}, {27, 0}, {28, 0}, {29, 0}, {30, 0}, {59, 0}, {32, 0}, {98, 0}, {90, 0}, {107, 1}, {108, 1}, {109, 1}, {110, 1}, {111, 1}, {112, 1}, {113, 1}, {99, 0}};
    private static final int[] REGION_ID = new int[81];
    private static final int[] REGION_TYPE = new int[81];
    public final SliderSetting opacity = this.addSetting(new SliderSetting(Deobf.decrypt("<\\)\r{\u00b0\u00b5"), Deobf.decrypt("!I/\u0007}\u00aa\u00ec\u0089\u00ac\u0117\u0105\u0143\u0125\u0199\u01ff\u01d8\u01be\u0214\u0201\u0254\u023f\u0290\u0281\u02db\u02f5"), 90.0, 10.0, 100.0, 5.0, Deobf.decrypt("V")));
    public final BooleanSetting gridLines = this.addSetting(new BooleanSetting(Deobf.decrypt("4^!\n2\u0088\u00a5\u0084\u00ac\u0108"), Deobf.decrypt("7^)\u00192\u00b0\u00a4\u008f\u00e9\u011a\u010a\u0100\u0126\u019e\u01e7\u0194\u01f2\u0212\u021f\u0250\u022f\u02d9\u0297\u02c7\u02af\u0309\u031c\u0319\u034f\u0390\u03e7\u0393\u03be\u03d4\u0440\u0420\u044f\u046e\u04cc\u04bb\u04eb\u04f2\u050b"), true));
    public final BooleanSetting cellNumbers = this.addSetting(new BooleanSetting(Deobf.decrypt("0I$\u00022\u008a\u00b9\u0087\u00ab\u011e\u011b\u0110"), Deobf.decrypt(" D'\u00192\u00a1\u00ad\u0089\u00a1\u015b\u011b\u0106\u0124\u0199\u01fc\u01da\u01b9\u0208\u0251\u027c\u0218\u02d9\u029b\u02d7\u02b6\u031c\u031c\u030e\u0301\u03d9\u03fb\u03d6\u03b0\u03c9\u045c\u046e\u040c\u0468\u04c5\u04bb\u04a9"), true));
    public final BooleanSetting legend = this.addSetting(new BooleanSetting(Deobf.decrypt("?I/\u000b|\u00a0"), Deobf.decrypt(" D'\u00192\u00b0\u00a4\u008f\u00e9\u0109\u010c\u0104\u012a\u019f\u01fd\u0199\u01ea\u0202\u0201\u0250\u027c\u029a\u029a\u02ce\u02b4\u030b\u030b\u035c\u034d\u03d5\u03f2\u0393\u03b7\u03d9\u040f\u042c\u040a\u0461\u04c6\u04a0\u04a7\u04f5\u054d\u0561\u0518\u054b\u05d6\u05ef\u05d7\u05e0"), true));

    public RegionMapModule() {
        super(Deobf.decrypt("!I/\u0007}\u00aa\u0081\u008b\u00b9"), Deobf.decrypt("7C&\u001bf\u0097\u0081\u00ba\u00e9\u0108\u010c\u0111\u0135\u0195\u01e1\u0194\u01ec\u021e\u0216\u025c\u0233\u0297\u02d5\u02cf\u02ba\u030e\u0359\u0313\u034f\u0390\u03e1\u039e\u03bc\u039d\u0467\u041b\u042b\u0423"), Category.RENDER);
    }

    private static int rgb(int r, int g, int b) {
        return r << 16 | g << 8 | b;
    }

    public boolean hasData() {
        return true;
    }

    public int mapSize() {
        return 9;
    }

    public int regionTypeCount() {
        return TYPE_NAMES.length;
    }

    public String regionTypeName(int type) {
        return type >= 0 && type < TYPE_NAMES.length ? TYPE_NAMES[type] : Deobf.decrypt("");
    }

    public int regionTypeRgb(int type) {
        return type >= 0 && type < TYPE_COLORS.length ? TYPE_COLORS[type] : 0xFFFFFF;
    }

    public int regionTypeAt(int index) {
        return index >= 0 && index < REGION_TYPE.length ? REGION_TYPE[index] : -1;
    }

    public int regionIdAt(int index) {
        return index >= 0 && index < REGION_ID.length ? REGION_ID[index] : -1;
    }

    public int[] worldToGrid(double worldX, double worldZ) {
        int gx = (int)Math.floor((worldX + 225000.0) / 50000.0);
        int gz = (int)Math.floor((worldZ + 225000.0) / 50000.0);
        return new int[]{gx, gz};
    }

    public double[] worldToCellPosition(double worldX, double worldZ) {
        double cx = (worldX + 225000.0) % 50000.0 / 50000.0;
        double cz = (worldZ + 225000.0) % 50000.0 / 50000.0;
        if (cx < 0.0) {
            cx += 1.0;
        }
        if (cz < 0.0) {
            cz += 1.0;
        }
        return new double[]{Math.clamp(cx, 0.0, 1.0), Math.clamp(cz, 0.0, 1.0)};
    }

    public int regionIdAtWorld(double worldX, double worldZ) {
        int[] g = this.worldToGrid(worldX, worldZ);
        if (g[0] < 0 || g[0] >= 9 || g[1] < 0 || g[1] >= 9) {
            return -1;
        }
        return this.regionIdAt(g[1] * 9 + g[0]);
    }

    public int regionTypeAtWorld(double worldX, double worldZ) {
        int[] g = this.worldToGrid(worldX, worldZ);
        if (g[0] < 0 || g[0] >= 9 || g[1] < 0 || g[1] >= 9) {
            return -1;
        }
        return this.regionTypeAt(g[1] * 9 + g[0]);
    }

    public int currentRegionId() {
        class_746 player = class_310.method_1551().field_1724;
        return player == null ? -1 : this.regionIdAtWorld(player.method_23317(), player.method_23321());
    }

    static {
        for (int i = 0; i < LAYOUT.length && i < REGION_ID.length; ++i) {
            if (LAYOUT[i].length >= 2) {
                RegionMapModule.REGION_ID[i] = LAYOUT[i][0];
                RegionMapModule.REGION_TYPE[i] = Math.min(LAYOUT[i][1], TYPE_NAMES.length - 1);
                continue;
            }
            RegionMapModule.REGION_TYPE[i] = -1;
        }
    }
}

