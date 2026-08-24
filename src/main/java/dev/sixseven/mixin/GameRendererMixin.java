/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyExpressionValue
 *  com.llamalad7.mixinextras.injector.ModifyReturnValue
 *  com.llamalad7.mixinextras.sugar.Local
 *  net.minecraft.class_757
 *  net.minecraft.class_9779
 *  org.joml.Matrix4f
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.At$Shift
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyArg
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.FreecamModule;
import dev.sixseven.render.BlurHook;
import dev.sixseven.render.OverlayRenderer;
import dev.sixseven.render.WorldProjection;
import net.minecraft.class_757;
import net.minecraft.class_9779;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_757.class})
public class GameRendererMixin {
    @Inject(method={"method_3192(Lnet/minecraft/class_9779;Z)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_11228;method_70879()V", shift=At.Shift.AFTER)})
    private void sixsevenclient$renderOverlay(class_9779 deltaTracker, boolean bl, CallbackInfo ci) {
        OverlayRenderer.render();
    }

    @Inject(method={"method_3188(Lnet/minecraft/class_9779;)V"}, at={@At(value="INVOKE", target="Lcom/mojang/blaze3d/systems/RenderSystem;setProjectionMatrix(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/class_10366;)V")})
    private void sixsevenclient$captureProjection(class_9779 deltaTracker, CallbackInfo ci, @Local(ordinal=0) Matrix4f matrix4f) {
        WorldProjection.capture(matrix4f, deltaTracker.method_60637(false));
    }

    @ModifyArg(method={"method_3192(Lnet/minecraft/class_9779;Z)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/class_11284;method_71116(IIDJLnet/minecraft/class_9779;ILnet/minecraft/class_4184;Z)V"), index=5)
    private int sixsevenclient$overrideBlurRadius(int blurriness) {
        return BlurHook.apply(blurriness);
    }

    @ModifyReturnValue(method={"method_3196(Lnet/minecraft/class_4184;FZ)F"}, at={@At(value="RETURN")})
    private float sixsevenclient$zoomFov(float original) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null || modules.zoom == null) {
            return original;
        }
        double factor = modules.zoom.currentFactor();
        return factor > 1.0001 ? (float)((double)original / factor) : original;
    }

    @ModifyExpressionValue(method={"method_3196(Lnet/minecraft/class_4184;FZ)F"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_3532;method_16439(FFF)F", ordinal=0)})
    private float sixsevenclient$customFov(float sprintMultiplier) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null || modules.customFov == null) {
            return sprintMultiplier;
        }
        return modules.customFov.fovMultiplier(sprintMultiplier);
    }

    @ModifyExpressionValue(method={"method_3172"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_5498;method_31034()Z")})
    private boolean sixsevenclient$freecamRenderFirstPersonHands(boolean isFirstPerson) {
        FreecamModule freecam = FreecamModule.get();
        if (freecam != null && freecam.isActive() && freecam.renderHands()) {
            return true;
        }
        return isFirstPerson;
    }
}

