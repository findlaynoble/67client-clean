/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyReturnValue
 *  net.minecraft.class_1309
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import net.minecraft.class_1309;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_1309.class})
public class LivingEntitySwingMixin {
    @ModifyReturnValue(method={"method_6028"}, at={@At(value="RETURN")})
    private int sixsevenclient$swingSpeed(int original) {
        class_1309 self = (class_1309)this;
        if (class_310.method_1551().field_1724 != self) {
            return original;
        }
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null || modules.swingSpeed == null || !modules.swingSpeed.isEnabled()) {
            return original;
        }
        float multiplier = modules.swingSpeed.multiplier();
        if (multiplier <= 0.01f) {
            return original;
        }
        return Math.max(1, Math.round((float)original / multiplier));
    }
}

