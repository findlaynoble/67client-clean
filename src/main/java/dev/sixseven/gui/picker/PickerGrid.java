/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 */
package dev.sixseven.gui.picker;

import dev.sixseven.settings.ColorSetting;
import java.util.List;
import net.minecraft.class_1799;

public interface PickerGrid {
    public String title();

    public long activeCount();

    public List<Cell> cells();

    public static interface Cell {
        public class_1799 icon();

        public String label();

        public boolean matches(String var1);

        public boolean tracked();

        public boolean enabled();

        public boolean selected();

        public int color();

        public void toggle();

        public ColorSetting colorTarget();
    }
}

