/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.ModifyExpressionValue
 *  com.llamalad7.mixinextras.sugar.Local
 *  net.minecraft.class_10055
 *  net.minecraft.class_10197
 *  net.minecraft.class_1799
 *  net.minecraft.class_310
 *  net.minecraft.class_8053
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 */
package dev.sixseven.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.ArmorTrimHiderModule;
import net.minecraft.class_10055;
import net.minecraft.class_10197;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_8053;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value={class_10197.class})
public class EquipmentLayerRendererMixin {
    @ModifyExpressionValue(method={"method_64078(Lnet/minecraft/class_10186$class_10190;Lnet/minecraft/class_5321;Lnet/minecraft/class_3879;Ljava/lang/Object;Lnet/minecraft/class_1799;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_2960;II)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/class_1799;method_58694(Lnet/minecraft/class_9331;)Ljava/lang/Object;")})
    private Object sixsevenclient$armorTrim(Object original, @Local(argsOnly=true) class_1799 itemStack, @Local(argsOnly=true) Object renderState) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return original;
        }
        ArmorTrimHiderModule module = modules.armorTrimHider;
        if (module == null || !module.isEnabled()) {
            return original;
        }
        if (!module.affectsOwn() && EquipmentLayerRendererMixin.sixsevenclient$isLocalPlayer(renderState)) {
            return original;
        }
        return module.mapTrim(itemStack, (class_8053)original);
    }

    private static boolean sixsevenclient$isLocalPlayer(Object renderState) {
        if (!(renderState instanceof class_10055)) {
            return false;
        }
        class_10055 avatar = (class_10055)renderState;
        class_310 mc = class_310.method_1551();
        return mc.field_1724 != null && avatar.field_53528 == mc.field_1724.method_5628();
    }
}

