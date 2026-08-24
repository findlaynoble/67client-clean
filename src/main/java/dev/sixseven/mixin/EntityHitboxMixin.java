/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyReturnValue
 *  net.minecraft.class_1297
 *  net.minecraft.class_238
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.class_1297;
import net.minecraft.class_238;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_1297.class})
public class EntityHitboxMixin {
    @ModifyReturnValue(method={"method_5829"}, at={@At(value="RETURN")})
    private class_238 sixsevenclient$expandHitbox(class_238 original) {
        return original;
    }
}

