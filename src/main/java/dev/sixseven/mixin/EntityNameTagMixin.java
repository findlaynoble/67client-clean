/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.class_10017
 *  net.minecraft.class_11659
 *  net.minecraft.class_12075
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_640
 *  net.minecraft.class_746
 *  net.minecraft.class_897
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import com.mojang.authlib.GameProfile;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.FakeRolesModule;
import dev.sixseven.module.impl.NameTagsModule;
import net.minecraft.class_10017;
import net.minecraft.class_11659;
import net.minecraft.class_12075;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_640;
import net.minecraft.class_746;
import net.minecraft.class_897;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_897.class})
public class EntityNameTagMixin {
    @Inject(method={"method_3926"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$nameTag(class_10017 state, class_4587 poseStack, class_11659 collector, class_12075 cameraRenderState, CallbackInfo ci) {
        FakeRolesModule fakeRoles;
        String replacement;
        boolean nameTagsOn;
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null || state.field_53337 == null) {
            return;
        }
        NameTagsModule nameTags = modules.nameTags;
        boolean bl = nameTagsOn = nameTags != null && nameTags.isEnabled();
        if (nameTagsOn) {
            String display = state.field_53337.getString();
            if (EntityNameTagMixin.isLocalPlayer(display)) {
                if (((Boolean)nameTags.hideOwnTag.get()).booleanValue() || ((Boolean)nameTags.players.get()).booleanValue() && ((Boolean)nameTags.self.get()).booleanValue()) {
                    ci.cancel();
                    return;
                }
            } else if (EntityNameTagMixin.isOnlinePlayer(display)) {
                if (((Boolean)nameTags.players.get()).booleanValue() || ((Boolean)nameTags.hidePlayerTags.get()).booleanValue()) {
                    ci.cancel();
                    return;
                }
            } else if (((Boolean)nameTags.hideOtherTags.get()).booleanValue()) {
                ci.cancel();
                return;
            }
        }
        if (modules.nameProtect != null && modules.nameProtect.isEnabled() && (replacement = modules.nameProtect.replacementForDisplay(state.field_53337.getString())) != null) {
            state.field_53337 = class_2561.method_43470((String)replacement);
        }
        if ((fakeRoles = modules.fakeRoles) != null && EntityNameTagMixin.isLocalPlayer(state.field_53337.getString())) {
            state.field_53337 = fakeRoles.decorateNametag(state.field_53337);
        }
    }

    private static boolean isLocalPlayer(String display) {
        if (display == null || display.isEmpty()) {
            return false;
        }
        class_746 self = class_310.method_1551().field_1724;
        if (self == null) {
            return false;
        }
        String name = self.method_7334().name();
        return name != null && !name.isEmpty() && display.contains(name);
    }

    private static boolean isOnlinePlayer(String display) {
        if (display == null || display.isEmpty()) {
            return false;
        }
        class_310 mc = class_310.method_1551();
        if (mc.method_1562() == null) {
            return false;
        }
        for (class_640 info : mc.method_1562().method_2880()) {
            GameProfile profile = info.method_2966();
            String name = profile == null ? null : profile.name();
            if (name == null || name.isEmpty() || !display.contains(name)) continue;
            return true;
        }
        return false;
    }
}

