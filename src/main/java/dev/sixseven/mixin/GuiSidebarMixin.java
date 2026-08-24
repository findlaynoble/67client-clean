/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_266
 *  net.minecraft.class_327
 *  net.minecraft.class_329
 *  net.minecraft.class_332
 *  net.minecraft.class_5348
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyArg
 *  org.spongepowered.asm.mixin.injection.Redirect
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package dev.sixseven.mixin;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.FakeStatsModule;
import net.minecraft.class_2561;
import net.minecraft.class_266;
import net.minecraft.class_327;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_5348;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_329.class})
public class GuiSidebarMixin {
    @Inject(method={"method_1757"}, at={@At(value="HEAD")})
    private void sixsevenclient$beginSidebar(class_332 guiGraphics, class_266 objective, CallbackInfo ci) {
        FakeStatsModule fs = GuiSidebarMixin.fakeStats();
        if (fs != null) {
            fs.beginSidebar();
        }
    }

    @Redirect(method={"method_1757"}, at=@At(value="INVOKE", target="Lnet/minecraft/class_327;method_27525(Lnet/minecraft/class_5348;)I", ordinal=1))
    private int sixsevenclient$lineWidth(class_327 font, class_5348 text) {
        FakeStatsModule fs = GuiSidebarMixin.fakeStats();
        if (fs != null && text instanceof class_2561) {
            class_2561 component = (class_2561)text;
            try {
                return font.method_27525((class_5348)fs.rewriteForWidth(component));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return font.method_27525(text);
    }

    @ModifyArg(method={"method_1757"}, at=@At(value="INVOKE", target="Lnet/minecraft/class_332;method_51439(Lnet/minecraft/class_327;Lnet/minecraft/class_2561;IIIZ)V", ordinal=1), index=1)
    private class_2561 sixsevenclient$lineText(class_2561 text) {
        FakeStatsModule fs = GuiSidebarMixin.fakeStats();
        if (fs != null) {
            try {
                return fs.rewriteForDraw(text);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return text;
    }

    private static FakeStatsModule fakeStats() {
        ModuleManager m = SixSevenClient.modules();
        if (m == null || m.fakeStats == null || !m.fakeStats.isEnabled()) {
            return null;
        }
        return m.fakeStats;
    }
}

