/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.module.impl.FreeLookModule;
import net.minecraft.class_1297;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1297.class})
public class EntityFreeLookMixin {
    @Inject(method={"method_5872(DD)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$freeLookTurn(double d, double e, CallbackInfo ci) {
        class_310 mc = class_310.method_1551();
        if (this != mc.field_1724) {
            return;
        }
        FreeLookModule freeLook = FreeLookModule.get();
        if (freeLook != null && freeLook.cameraMode()) {
            freeLook.addCameraLook(d, e);
            ci.cancel();
        }
    }
}

