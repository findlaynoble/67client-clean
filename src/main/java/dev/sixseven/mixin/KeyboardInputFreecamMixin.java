/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_743
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.module.impl.FreecamModule;
import net.minecraft.class_310;
import net.minecraft.class_743;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_743.class})
public class KeyboardInputFreecamMixin {
    @Inject(method={"method_3129"}, at={@At(value="TAIL")})
    private void sixsevenclient$freecamReapplyCachedBodyInput(CallbackInfo ci) {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.field_1724.field_3913 != this) {
            return;
        }
        FreecamModule f = FreecamModule.get();
        if (f == null || !f.isActive()) {
            return;
        }
        FreecamModule.reapplyBodyInput(mc);
    }
}

