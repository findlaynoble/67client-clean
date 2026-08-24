/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2338
 *  net.minecraft.class_2620
 *  net.minecraft.class_2622
 *  net.minecraft.class_2626
 *  net.minecraft.class_2637
 *  net.minecraft.class_2672
 *  net.minecraft.class_2680
 *  net.minecraft.class_2761
 *  net.minecraft.class_310
 *  net.minecraft.class_634
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.BlockEntityEspModule;
import dev.sixseven.module.impl.SpawnerProtectModule;
import dev.sixseven.util.TpsTracker;
import net.minecraft.class_2338;
import net.minecraft.class_2620;
import net.minecraft.class_2622;
import net.minecraft.class_2626;
import net.minecraft.class_2637;
import net.minecraft.class_2672;
import net.minecraft.class_2680;
import net.minecraft.class_2761;
import net.minecraft.class_310;
import net.minecraft.class_634;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_634.class})
public class ClientPacketListenerMixin {
    @Inject(method={"method_11079"}, at={@At(value="HEAD")})
    private void sixsevenclient$trackTps(class_2761 packet, CallbackInfo ci) {
        TpsTracker.onTimePacket();
    }

    @Inject(method={"method_45730"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$fakeCommands(String command, CallbackInfo ci) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return;
        }
        try {
            if (modules.fakePay != null && modules.fakePay.tryIntercept(command)) {
                ci.cancel();
                return;
            }
            if (modules.fakeStats != null && modules.fakeStats.tryInterceptBalance(command)) {
                ci.cancel();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Inject(method={"method_11128"}, at={@At(value="TAIL")})
    private void sixsevenclient$blockEntityChunk(class_2672 packet, CallbackInfo ci) {
        BlockEntityEspModule module = ClientPacketListenerMixin.module();
        if (module == null || !module.isEnabled() || !module.chunkPacketsEnabled()) {
            return;
        }
        try {
            packet.method_38598().method_38587(packet.method_11523(), packet.method_11524()).accept((pos, type, tag) -> module.record(pos, type));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Inject(method={"method_11094"}, at={@At(value="TAIL")})
    private void sixsevenclient$blockEntityUpdate(class_2622 packet, CallbackInfo ci) {
        BlockEntityEspModule module = ClientPacketListenerMixin.module();
        if (module == null || !module.isEnabled() || !module.beUpdatePacketsEnabled()) {
            return;
        }
        try {
            module.record(packet.method_11293(), packet.method_11291());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static BlockEntityEspModule module() {
        ModuleManager modules = SixSevenClient.modules();
        return modules != null ? modules.blockEntityEsp : null;
    }

    private static SpawnerProtectModule spawnerProtect() {
        ModuleManager modules = SixSevenClient.modules();
        return modules != null ? modules.spawnerProtect : null;
    }

    @Inject(method={"method_11116"}, at={@At(value="HEAD")})
    private void sixsevenclient$spawnerProtectDestruction(class_2620 packet, CallbackInfo ci) {
        SpawnerProtectModule sp = ClientPacketListenerMixin.spawnerProtect();
        if (sp == null || !sp.isEnabled() || !class_310.method_1551().method_18854()) {
            return;
        }
        try {
            sp.onBlockDestructionPacket(packet.method_11280(), packet.method_11277());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Inject(method={"method_11136"}, at={@At(value="HEAD")})
    private void sixsevenclient$spawnerProtectBlockUpdate(class_2626 packet, CallbackInfo ci) {
        SpawnerProtectModule sp = ClientPacketListenerMixin.spawnerProtect();
        if (!(sp != null && sp.isEnabled() && sp.detectBlockUpdatesEnabled() && class_310.method_1551().method_18854())) {
            return;
        }
        try {
            sp.onServerBlockUpdate(packet.method_11309(), packet.method_11308(), false);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Inject(method={"method_11100"}, at={@At(value="HEAD")})
    private void sixsevenclient$spawnerProtectSectionUpdate(class_2637 packet, CallbackInfo ci) {
        SpawnerProtectModule sp = ClientPacketListenerMixin.spawnerProtect();
        if (!(sp != null && sp.isEnabled() && sp.detectBlockUpdatesEnabled() && class_310.method_1551().method_18854())) {
            return;
        }
        try {
            packet.method_30621((pos, state) -> sp.onServerBlockUpdate((class_2338)pos, (class_2680)state, true));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

