/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1309
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import net.minecraft.class_1309;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1309.class})
public class LivingEntityJumpMixin {
    @Inject(method={"method_6043"}, at={@At(value="HEAD")})
    private void sixsevenclient$onJump(CallbackInfo ci) {
        ModuleManager modules;
        class_746 player;
        LivingEntityJumpMixin livingEntityJumpMixin = this;
        if (livingEntityJumpMixin instanceof class_746 && (player = (class_746)livingEntityJumpMixin) == class_310.method_1551().field_1724 && (modules = SixSevenClient.modules()) != null && modules.jumpCircles != null && modules.jumpCircles.isEnabled()) {
            modules.jumpCircles.onPlayerJump(player);
        }
    }
}

