/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyExpressionValue
 *  net.minecraft.class_329
 *  net.minecraft.class_332
 *  net.minecraft.class_9779
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.FreecamModule;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_9779;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_329.class})
public class GuiCrosshairMixin {
    @Inject(method={"method_1736"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$hideVanillaCrosshair(class_332 guiGraphics, class_9779 deltaTracker, CallbackInfo ci) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules != null && modules.customCrosshair != null && modules.customCrosshair.shouldHideVanilla()) {
            ci.cancel();
        }
    }

    @ModifyExpressionValue(method={"method_1736"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_5498;method_31034()Z")})
    private boolean sixsevenclient$freecamCrosshairInThirdPerson(boolean isFirstPerson) {
        FreecamModule freecam = FreecamModule.get();
        if (freecam != null && freecam.isActive() && freecam.isShowPlayerModel() && !isFirstPerson) {
            return true;
        }
        return isFirstPerson;
    }
}

