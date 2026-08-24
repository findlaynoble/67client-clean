/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyExpressionValue
 *  net.minecraft.class_11910
 *  net.minecraft.class_310
 *  net.minecraft.class_312
 *  net.minecraft.class_408
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.Slice
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sixseven.SixSevenClient;
import dev.sixseven.hud.HudDragController;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.util.CpsTracker;
import net.minecraft.class_11910;
import net.minecraft.class_310;
import net.minecraft.class_312;
import net.minecraft.class_408;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_312.class})
public class MouseHandlerMixin {
    @Shadow
    private double field_1789;
    @Shadow
    private double field_1787;

    @Inject(method={"method_1606"}, at={@At(value="HEAD")})
    private void sixsevenclient$aimAssist(double d, CallbackInfo ci) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null || modules.aimAssist == null || !modules.aimAssist.isEnabled()) {
            return;
        }
        double[] add = modules.aimAssist.computePixels(d, this.field_1789, this.field_1787);
        if (add != null) {
            this.field_1789 += add[0];
            this.field_1787 += add[1];
        }
    }

    @Inject(method={"method_1601(JLnet/minecraft/class_11910;I)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$onButton(long window, class_11910 buttonInfo, int action, CallbackInfo ci) {
        class_310 minecraft = class_310.method_1551();
        if (window != minecraft.method_22683().method_4490()) {
            return;
        }
        if (action == 1 && minecraft.field_1755 == null) {
            CpsTracker.onClick(buttonInfo.comp_4801());
        }
        if (minecraft.field_1755 instanceof class_408 && buttonInfo.comp_4801() == 0 && SixSevenClient.hud() != null) {
            if (action == 1) {
                if (HudDragController.tryStartDrag(SixSevenClient.hud())) {
                    ci.cancel();
                }
            } else if (action == 0 && HudDragController.isDragging()) {
                HudDragController.stopDrag();
                SixSevenClient.config().save();
                ci.cancel();
            }
        }
    }

    @ModifyExpressionValue(method={"method_1606"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_7172;method_41753()Ljava/lang/Object;")}, slice={@Slice(from=@At(value="INVOKE", target="Lnet/minecraft/class_315;method_42495()Lnet/minecraft/class_7172;"))})
    private Object sixsevenclient$zoomSensitivity(Object original) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null || modules.zoom == null || !(original instanceof Double)) {
            return original;
        }
        Double s = (Double)original;
        double factor = modules.zoom.currentFactor();
        if (factor <= 1.0001) {
            return original;
        }
        double e = s * 0.6 + 0.2;
        double scaled = e / Math.cbrt(factor);
        double substitute = (scaled - 0.2) / 0.6;
        return Math.max(0.0, substitute);
    }

    @Inject(method={"method_1598(JDD)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$onScroll(long window, double xOffset, double yOffset, CallbackInfo ci) {
        class_310 minecraft = class_310.method_1551();
        if (window == minecraft.method_22683().method_4490() && minecraft.field_1755 instanceof class_408 && SixSevenClient.hud() != null && HudDragController.tryResize(SixSevenClient.hud(), yOffset)) {
            ci.cancel();
        }
    }
}

