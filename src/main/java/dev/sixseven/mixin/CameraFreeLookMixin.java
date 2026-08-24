/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_4184
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyArgs
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  org.spongepowered.asm.mixin.injection.invoke.arg.Args
 */
package dev.sixseven.mixin;

import dev.sixseven.module.impl.FreeLookModule;
import dev.sixseven.module.impl.FreecamModule;
import net.minecraft.class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value={class_4184.class})
public abstract class CameraFreeLookMixin {
    @ModifyArgs(method={"method_19321"}, at=@At(value="INVOKE", target="Lnet/minecraft/class_4184;method_19325(FF)V"))
    private void sixsevenclient$freeLookRotation(Args args) {
        FreeLookModule freeLook = FreeLookModule.get();
        if (freeLook == null || !freeLook.isActive()) {
            return;
        }
        FreecamModule freecam = FreecamModule.get();
        if (freecam != null && freecam.isActive()) {
            return;
        }
        args.set(0, (Object)Float.valueOf(freeLook.getCameraYaw()));
        args.set(1, (Object)Float.valueOf(freeLook.getCameraPitch()));
    }

    @Inject(method={"method_19318"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$freeLookThroughWalls(float distance, CallbackInfoReturnable<Float> cir) {
        FreeLookModule freeLook = FreeLookModule.get();
        if (freeLook == null || !freeLook.seeThroughWalls()) {
            return;
        }
        FreecamModule freecam = FreecamModule.get();
        if (freecam != null && freecam.isActive()) {
            return;
        }
        cir.setReturnValue((Object)Float.valueOf(distance));
    }
}

