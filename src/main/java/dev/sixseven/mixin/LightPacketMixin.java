/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1937
 *  net.minecraft.class_2672
 *  net.minecraft.class_2676
 *  net.minecraft.class_310
 *  net.minecraft.class_634
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.suschunk.ServerLightCache;
import net.minecraft.class_1937;
import net.minecraft.class_2672;
import net.minecraft.class_2676;
import net.minecraft.class_310;
import net.minecraft.class_634;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_634.class})
public class LightPacketMixin {
    @Inject(method={"method_11143"}, at={@At(value="TAIL")})
    private void sixsevenclient$captureLightUpdate(class_2676 packet, CallbackInfo ci) {
        ServerLightCache.get().ingest(packet.method_11558(), packet.method_11554(), packet.method_38600(), (class_1937)class_310.method_1551().field_1687);
    }

    @Inject(method={"method_11128"}, at={@At(value="TAIL")})
    private void sixsevenclient$captureChunkLight(class_2672 packet, CallbackInfo ci) {
        ServerLightCache.get().ingest(packet.method_11523(), packet.method_11524(), packet.method_38599(), (class_1937)class_310.method_1551().field_1687);
    }
}

