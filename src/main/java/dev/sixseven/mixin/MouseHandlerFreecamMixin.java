/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_312
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.module.impl.FreecamModule;
import net.minecraft.class_310;
import net.minecraft.class_312;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_312.class})
public abstract class MouseHandlerFreecamMixin {
    @Shadow
    private double field_1789;
    @Shadow
    private double field_1787;

    @Inject(method={"method_1606"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$freecamMouse(double d, CallbackInfo ci) {
        FreecamModule freecam = FreecamModule.get();
        if (freecam == null || !freecam.isActive()) {
            return;
        }
        class_310 mc = class_310.method_1551();
        if (mc.field_1755 != null) {
            return;
        }
        double sensitivity = (Double)mc.field_1690.method_42495().method_41753() * 0.6 + 0.2;
        double factor = sensitivity * sensitivity * sensitivity * 8.0;
        double dx = this.field_1789 * factor * (double)freecam.getLookSensitivity();
        double dy = this.field_1787 * factor * (double)freecam.getLookSensitivity();
        float newYaw = freecam.getCurrentYaw() + (float)dx * 0.15f;
        float newPitch = freecam.getCurrentPitch() + (float)dy * 0.15f;
        freecam.setRotation(newYaw, newPitch);
        ci.cancel();
    }
}

