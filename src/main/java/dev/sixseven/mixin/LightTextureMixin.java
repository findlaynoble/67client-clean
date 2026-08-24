/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyExpressionValue
 *  net.minecraft.class_765
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Slice
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import net.minecraft.class_765;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value={class_765.class})
public class LightTextureMixin {
    @ModifyExpressionValue(method={"method_3313"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_7172;method_41753()Ljava/lang/Object;")}, slice={@Slice(from=@At(value="INVOKE", target="Lnet/minecraft/class_315;method_42473()Lnet/minecraft/class_7172;"))})
    private Object sixsevenclient$fullbrightGamma(Object original) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules != null && modules.fullbright != null && modules.fullbright.isEnabled()) {
            return (double)modules.fullbright.gamma.getFloat();
        }
        return original;
    }
}

