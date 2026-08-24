/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_338
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import net.minecraft.class_2561;
import net.minecraft.class_338;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={class_338.class})
public class ChatComponentMixin {
    @ModifyVariable(method={"method_44811(Lnet/minecraft/class_2561;Lnet/minecraft/class_7469;Lnet/minecraft/class_7591;)V"}, at=@At(value="HEAD"), argsOnly=true, ordinal=0)
    private class_2561 sixsevenclient$censorChat(class_2561 component) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return component;
        }
        class_2561 result = component;
        if (modules.fakeRoles != null) {
            result = modules.fakeRoles.decorateChat(result);
        }
        if (modules.nameProtect != null && modules.nameProtect.isEnabled()) {
            result = modules.nameProtect.censorChat(result);
        }
        return result;
    }
}

