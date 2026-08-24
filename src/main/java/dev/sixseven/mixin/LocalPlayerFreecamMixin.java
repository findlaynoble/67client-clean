/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.module.impl.FreecamModule;
import net.minecraft.class_310;
import net.minecraft.class_746;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_746.class})
public abstract class LocalPlayerFreecamMixin {
    @Inject(method={"method_6007"}, at={@At(value="HEAD")})
    private void sixsevenclient$freecamReapplyBeforeAiStep(CallbackInfo ci) {
        this.sixsevenclient$reapply();
    }

    @Inject(method={"method_3136"}, at={@At(value="HEAD")})
    private void sixsevenclient$freecamReapplyBeforeSendPosition(CallbackInfo ci) {
        this.sixsevenclient$reapply();
    }

    private void sixsevenclient$reapply() {
        class_746 self = (class_746)this;
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 != self) {
            return;
        }
        FreecamModule f = FreecamModule.get();
        if (f == null || !f.isActive()) {
            return;
        }
        FreecamModule.reapplyBodyInput(mc);
    }
}

