/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_310
 *  net.minecraft.class_636
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import net.minecraft.class_636;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_636.class})
public class MultiPlayerGameModeAttackMixin {
    @Inject(method={"method_2918"}, at={@At(value="HEAD")})
    private void sixsevenclient$onAttack(class_1657 player, class_1297 target, CallbackInfo ci) {
        if (player != class_310.method_1551().field_1724 || target == player) {
            return;
        }
        ModuleManager modules = SixSevenClient.modules();
        if (modules != null && modules.hitParticles != null && modules.hitParticles.isEnabled()) {
            modules.hitParticles.onHit(target);
        }
    }
}

