/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1735
 *  net.minecraft.class_465
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package dev.sixseven.mixin;

import net.minecraft.class_1735;
import net.minecraft.class_465;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_465.class})
public interface AbstractContainerScreenAccessor {
    @Accessor(value="field_2787")
    public class_1735 getHoveredSlot();

    @Accessor(value="field_2776")
    public int getLeftPos();

    @Accessor(value="field_2800")
    public int getTopPos();

    @Accessor(value="field_2792")
    public int getImageWidth();

    @Accessor(value="field_2779")
    public int getImageHeight();
}

