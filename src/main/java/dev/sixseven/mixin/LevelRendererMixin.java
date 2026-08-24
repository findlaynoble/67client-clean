/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_11658
 *  net.minecraft.class_12074
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_761
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.render.AccessoryRenderer;
import dev.sixseven.render.BlockEntityEspRenderer;
import dev.sixseven.render.BlockEspRenderer;
import dev.sixseven.render.BlockOutlineRenderer;
import dev.sixseven.render.ChunkFinderRenderer;
import dev.sixseven.render.EntityEspRenderer;
import dev.sixseven.render.HitParticleRenderer;
import dev.sixseven.render.HoleEspRenderer;
import dev.sixseven.render.JumpCircleRenderer;
import dev.sixseven.render.MiscBlockEspRenderer;
import dev.sixseven.render.StorageEspRenderer;
import dev.sixseven.render.SusChunkRenderer;
import net.minecraft.class_11658;
import net.minecraft.class_12074;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_761.class})
public class LevelRendererMixin {
    @Inject(method={"method_62210(Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_4587;ZLnet/minecraft/class_11658;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$customBlockOutline(class_4597.class_4598 bufferSource, class_4587 poseStack, boolean translucentPass, class_11658 levelRenderState, CallbackInfo ci) {
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return;
        }
        if (translucentPass && modules.susChunkFinder.isEnabled()) {
            SusChunkRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.susChunkFinder);
        }
        if (translucentPass && modules.chunkFinder != null && modules.chunkFinder.isEnabled()) {
            ChunkFinderRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.chunkFinder);
        }
        if (translucentPass && modules.storageEsp != null && modules.storageEsp.isEnabled()) {
            StorageEspRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.storageEsp);
        }
        if (translucentPass && modules.blockEsp != null && modules.blockEsp.isEnabled()) {
            BlockEspRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.blockEsp);
        }
        if (translucentPass && modules.blockEntityEsp != null && modules.blockEntityEsp.isEnabled()) {
            BlockEntityEspRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.blockEntityEsp);
        }
        if (translucentPass && modules.spawnerNametags != null && modules.spawnerNametags.isEnabled()) {
            MiscBlockEspRenderer.renderSpawners(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.spawnerNametags);
        }
        if (translucentPass && modules.debugHoleEsp != null && modules.debugHoleEsp.isEnabled()) {
            HoleEspRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.debugHoleEsp);
        }
        if (translucentPass && modules.playerEsp != null && modules.playerEsp.isEnabled()) {
            EntityEspRenderer.renderPlayers(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.playerEsp);
        }
        if (translucentPass && modules.mobEsp != null && modules.mobEsp.isEnabled()) {
            EntityEspRenderer.renderMobs(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.mobEsp);
        }
        if (translucentPass && modules.jumpCircles != null && modules.jumpCircles.isEnabled()) {
            JumpCircleRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.jumpCircles);
        }
        if (translucentPass && modules.hitParticles != null && modules.hitParticles.isEnabled()) {
            HitParticleRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.hitParticles);
        }
        if (translucentPass && modules.customAccessories != null && modules.customAccessories.isEnabled()) {
            AccessoryRenderer.render(bufferSource, poseStack, levelRenderState.field_63082.field_63078, modules.customAccessories);
        }
        if (modules.freecam != null && modules.freecam.isActive()) {
            ci.cancel();
            return;
        }
        if (!modules.blockOutline.isEnabled()) {
            return;
        }
        ci.cancel();
        class_12074 state = levelRenderState.field_63083;
        if (state == null || state.comp_4933() != translucentPass) {
            return;
        }
        BlockOutlineRenderer.render(bufferSource, poseStack, state, levelRenderState.field_63082.field_63078, modules.blockOutline);
    }
}

