/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_11908
 *  net.minecraft.class_309
 *  net.minecraft.class_310
 *  net.minecraft.class_342
 *  net.minecraft.class_364
 *  net.minecraft.class_408
 *  net.minecraft.class_437
 *  net.minecraft.class_465
 *  net.minecraft.class_473
 *  net.minecraft.class_7743
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.ClickGuiScreen;
import net.minecraft.class_11908;
import net.minecraft.class_309;
import net.minecraft.class_310;
import net.minecraft.class_342;
import net.minecraft.class_364;
import net.minecraft.class_408;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_473;
import net.minecraft.class_7743;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_309.class})
public class KeyboardHandlerMixin {
    @Inject(method={"method_1466(JILnet/minecraft/class_11908;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void sixsevenclient$dispatchModuleKeybinds(long window, int action, class_11908 keyEvent, CallbackInfo ci) {
        class_310 minecraft = class_310.method_1551();
        if (SixSevenClient.modules() == null || action != 1) {
            return;
        }
        if (minecraft.field_1755 == null && minecraft.field_1687 != null) {
            if (SixSevenClient.modules().onKeyPressed(keyEvent.comp_4795())) {
                ci.cancel();
            }
            return;
        }
        if ((keyEvent.comp_4795() == 340 || keyEvent.comp_4795() == 344) && minecraft.field_1755 != null && KeyboardHandlerMixin.shiftOpensGui(minecraft.field_1755)) {
            minecraft.method_1507((class_437)new ClickGuiScreen(minecraft.field_1755));
            ci.cancel();
        }
    }

    private static boolean shiftOpensGui(class_437 screen) {
        class_342 editBox;
        if (screen instanceof ClickGuiScreen || screen instanceof class_408 || screen instanceof class_465 || screen instanceof class_7743 || screen instanceof class_473) {
            return false;
        }
        class_364 class_3642 = screen.method_25399();
        return !(class_3642 instanceof class_342) || !(editBox = (class_342)class_3642).method_25370();
    }
}

