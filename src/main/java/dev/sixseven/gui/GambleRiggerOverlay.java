/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents
 *  net.minecraft.class_437
 *  net.minecraft.class_465
 *  net.minecraft.class_480
 */
package dev.sixseven.gui;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.GamblePanel;
import dev.sixseven.module.impl.GambleRiggerModule;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.class_437;
import net.minecraft.class_465;
import net.minecraft.class_480;

public final class GambleRiggerOverlay {
    private GambleRiggerOverlay() {
    }

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            GambleRiggerModule mod;
            if (!(screen instanceof class_480)) {
                return;
            }
            class_480 container = (class_480)screen;
            GambleRiggerModule gambleRiggerModule = mod = SixSevenClient.modules() == null ? null : SixSevenClient.modules().gambleRigger;
            if (mod == null || !mod.isEnabled()) {
                return;
            }
            GamblePanel panel = new GamblePanel((class_465<?>)container, mod);
            ScreenEvents.afterRender((class_437)screen).register((s, graphics, mouseX, mouseY, tickDelta) -> panel.render(graphics, mouseX, mouseY));
            ScreenMouseEvents.allowMouseClick((class_437)screen).register((s, ctx) -> !panel.handleClick(ctx.comp_4798(), ctx.comp_4799(), ctx.method_74245()));
        });
    }
}

