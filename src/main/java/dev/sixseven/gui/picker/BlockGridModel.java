/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_1802
 *  net.minecraft.class_1935
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
 */
package dev.sixseven.gui.picker;

import dev.sixseven.gui.picker.PickerGrid;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BlockListSetting;
import dev.sixseven.settings.ColorSetting;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.IntSupplier;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1935;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public final class BlockGridModel
implements PickerGrid {
    private static List<class_2248> allBlocks;
    private final BlockListSetting setting;
    private final IntSupplier defaultColor;
    private final String title;
    private List<PickerGrid.Cell> cells;

    public BlockGridModel(BlockListSetting setting, IntSupplier defaultColor, String title) {
        this.setting = setting;
        this.defaultColor = defaultColor;
        this.title = title;
    }

    private static List<class_2248> allBlocks() {
        if (allBlocks == null) {
            ArrayList<class_2248> list = new ArrayList<class_2248>();
            for (class_2248 block : class_7923.field_41175) {
                if (block == class_2246.field_10124 || block == class_2246.field_10543 || block == class_2246.field_10243) continue;
                list.add(block);
            }
            allBlocks = list;
        }
        return allBlocks;
    }

    @Override
    public String title() {
        return this.title;
    }

    @Override
    public long activeCount() {
        return this.setting.enabledCount();
    }

    @Override
    public List<PickerGrid.Cell> cells() {
        if (this.cells == null) {
            ArrayList<PickerGrid.Cell> out = new ArrayList<PickerGrid.Cell>(BlockGridModel.allBlocks().size());
            for (class_2248 block : BlockGridModel.allBlocks()) {
                out.add(new BlockCell(block));
            }
            this.cells = out;
        }
        return this.cells;
    }

    private final class BlockCell
    implements PickerGrid.Cell {
        private final class_2248 block;
        private final String search;
        private class_1799 icon;

        BlockCell(class_2248 block) {
            this.block = block;
            class_2960 id = class_7923.field_41175.method_10221((Object)block);
            String path = id != null ? id.method_12832() : Deobf.decrypt("");
            String ns = id != null ? id.method_12836() : Deobf.decrypt("");
            this.search = (path + " " + ns + " " + BlockListSetting.displayName(block)).toLowerCase(Locale.ROOT);
        }

        @Override
        public class_1799 icon() {
            if (this.icon == null) {
                class_1792 item = this.block.method_8389();
                this.icon = new class_1799((class_1935)(item == class_1802.field_8162 ? class_1802.field_8077 : item));
            }
            return this.icon;
        }

        @Override
        public String label() {
            return BlockListSetting.displayName(this.block);
        }

        @Override
        public boolean matches(String lowerQuery) {
            return this.search.contains(lowerQuery);
        }

        @Override
        public boolean tracked() {
            return BlockGridModel.this.setting.find(this.block) != null;
        }

        @Override
        public boolean enabled() {
            BlockListSetting.Target t = BlockGridModel.this.setting.find(this.block);
            return t != null && (Boolean)t.enabled.get() != false;
        }

        @Override
        public boolean selected() {
            return BlockGridModel.this.setting.find(this.block) != null;
        }

        @Override
        public int color() {
            BlockListSetting.Target t = BlockGridModel.this.setting.find(this.block);
            return t != null ? ((Integer)t.color.get()).intValue() : BlockGridModel.this.defaultColor.getAsInt();
        }

        @Override
        public void toggle() {
            BlockListSetting.Target t = BlockGridModel.this.setting.find(this.block);
            if (t == null) {
                BlockGridModel.this.setting.add(this.block, true, BlockGridModel.this.defaultColor.getAsInt());
            } else {
                t.enabled.toggle();
            }
        }

        @Override
        public ColorSetting colorTarget() {
            BlockListSetting.Target t = BlockGridModel.this.setting.find(this.block);
            if (t == null) {
                t = BlockGridModel.this.setting.add(this.block, true, BlockGridModel.this.defaultColor.getAsInt());
            }
            return t != null ? t.color : null;
        }
    }
}

