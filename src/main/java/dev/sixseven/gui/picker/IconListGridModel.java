/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  net.minecraft.class_1935
 */
package dev.sixseven.gui.picker;

import dev.sixseven.gui.picker.PickerGrid;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.IconListSetting;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1799;
import net.minecraft.class_1935;

public final class IconListGridModel
implements PickerGrid {
    private final IconListSetting setting;
    private List<PickerGrid.Cell> cells;

    public IconListGridModel(IconListSetting setting) {
        this.setting = setting;
    }

    @Override
    public String title() {
        return this.setting.getName();
    }

    @Override
    public long activeCount() {
        return this.setting.enabledCount();
    }

    @Override
    public List<PickerGrid.Cell> cells() {
        if (this.cells == null) {
            ArrayList<PickerGrid.Cell> out = new ArrayList<PickerGrid.Cell>(this.setting.entries().size());
            for (IconListSetting.Entry entry : this.setting.entries()) {
                out.add(new EntryCell(entry));
            }
            this.cells = out;
        }
        return this.cells;
    }

    private static final class EntryCell
    implements PickerGrid.Cell {
        private final IconListSetting.Entry entry;
        private final class_1799 icon;

        EntryCell(IconListSetting.Entry entry) {
            this.entry = entry;
            this.icon = new class_1799((class_1935)entry.icon());
        }

        @Override
        public class_1799 icon() {
            return this.icon;
        }

        @Override
        public String label() {
            return this.entry.label();
        }

        @Override
        public boolean matches(String lowerQuery) {
            return this.entry.matches(lowerQuery);
        }

        @Override
        public boolean tracked() {
            return true;
        }

        @Override
        public boolean enabled() {
            return (Boolean)this.entry.enabled.get();
        }

        @Override
        public boolean selected() {
            return (Boolean)this.entry.enabled.get();
        }

        @Override
        public int color() {
            return (Integer)this.entry.color.get();
        }

        @Override
        public void toggle() {
            this.entry.enabled.toggle();
        }

        @Override
        public ColorSetting colorTarget() {
            return this.entry.color;
        }
    }
}

