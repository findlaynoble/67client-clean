/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_10185
 *  net.minecraft.class_241
 *  net.minecraft.class_744
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package dev.sixseven.mixin;

import net.minecraft.class_10185;
import net.minecraft.class_241;
import net.minecraft.class_744;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_744.class})
public interface ClientInputAccessor {
    @Accessor(value="field_54155")
    public void sixsevenclient$setKeyPresses(class_10185 var1);

    @Accessor(value="field_55868")
    public void sixsevenclient$setMoveVector(class_241 var1);
}

