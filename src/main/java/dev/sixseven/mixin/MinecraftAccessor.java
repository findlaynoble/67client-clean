/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package dev.sixseven.mixin;

import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_310.class})
public interface MinecraftAccessor {
    @Accessor(value="field_1752")
    public void sixsevenclient$setRightClickDelay(int var1);

    @Invoker(value="method_1536")
    public boolean sixsevenclient$startAttack();

    @Invoker(value="method_1583")
    public void sixsevenclient$startUseItem();
}

