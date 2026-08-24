/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyReturnValue
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.class_2561
 *  net.minecraft.class_355
 *  net.minecraft.class_640
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import net.minecraft.class_2561;
import net.minecraft.class_355;
import net.minecraft.class_640;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_355.class})
public class PlayerTabOverlayMixin {
    @ModifyReturnValue(method={"method_1918"}, at={@At(value="RETURN")})
    private class_2561 sixsevenclient$protectTabName(class_2561 original, class_640 playerInfo) {
        String replacement;
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return original;
        }
        GameProfile profile = playerInfo.method_2966();
        String name = profile == null ? null : profile.name();
        class_2561 result = original;
        if (modules.nameProtect != null && modules.nameProtect.isEnabled() && name != null && !name.isEmpty() && (replacement = modules.nameProtect.replacementForDisplay(name)) != null) {
            result = class_2561.method_43470((String)replacement);
        }
        if (modules.fakeRoles != null && name != null) {
            result = modules.fakeRoles.decorateTab(result, name);
        }
        return result;
    }
}

