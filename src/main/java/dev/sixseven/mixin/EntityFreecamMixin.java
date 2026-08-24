/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1657
 *  net.minecraft.class_310
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package dev.sixseven.mixin;

import dev.sixseven.module.impl.FreecamModule;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1297.class})
public class EntityFreecamMixin {
    @Inject(method={"method_5756"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$freecamSeeOwnBody(class_1657 viewer, CallbackInfoReturnable<Boolean> cir) {
        class_1297 self = (class_1297)this;
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || self != mc.field_1724 || viewer != mc.field_1724) {
            return;
        }
        FreecamModule freecam = FreecamModule.get();
        if (freecam == null || !freecam.isActive() || !freecam.isShowPlayerModel()) {
            return;
        }
        cir.setReturnValue((Object)false);
    }
}

