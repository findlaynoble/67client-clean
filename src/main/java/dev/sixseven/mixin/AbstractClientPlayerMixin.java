/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_742
 *  net.minecraft.class_8685
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.SkinProtectModule;
import net.minecraft.class_742;
import net.minecraft.class_8685;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_742.class})
public class AbstractClientPlayerMixin {
    @Inject(method={"method_52814"}, at={@At(value="RETURN")}, cancellable=true)
    private void sixsevenclient$replaceSkin(CallbackInfoReturnable<class_8685> cir) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return;
        }
        SkinProtectModule skinProtect = modules.skinProtect;
        if (skinProtect == null || !skinProtect.isEnabled()) {
            return;
        }
        class_8685 replacement = skinProtect.replacementSkin();
        if (replacement == null) {
            return;
        }
        class_742 self = (class_742)this;
        if (skinProtect.shouldReplace(self.method_5667())) {
            cir.setReturnValue((Object)replacement);
        }
    }
}

