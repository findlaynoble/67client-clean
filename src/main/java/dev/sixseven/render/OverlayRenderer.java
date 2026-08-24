/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.textures.GpuTexture
 *  net.minecraft.class_10868
 *  net.minecraft.class_276
 *  net.minecraft.class_310
 *  net.minecraft.class_408
 *  org.lwjgl.opengl.GL33C
 */
package dev.sixseven.render;

import com.mojang.blaze3d.textures.GpuTexture;
import dev.sixseven.SixSevenClient;
import dev.sixseven.hud.HudDragController;
import dev.sixseven.hud.HudManager;
import dev.sixseven.module.impl.CustomCrosshairModule;
import dev.sixseven.module.impl.MotionBlurModule;
import dev.sixseven.notification.NotificationManager;
import dev.sixseven.render.GlintTextureTinter;
import dev.sixseven.render.MotionBlurRenderer;
import dev.sixseven.render.NvgDrawable;
import dev.sixseven.render.WorldNametagRenderer;
import dev.sixseven.render.nanovg.GlStateSnapshot;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.util.Colors;
import net.minecraft.class_10868;
import net.minecraft.class_276;
import net.minecraft.class_310;
import net.minecraft.class_408;
import org.lwjgl.opengl.GL33C;

public final class OverlayRenderer {
    private static HudManager hudManager;
    private static NotificationManager notifications;
    private static int fbo;
    private static boolean crashed;

    private OverlayRenderer() {
    }

    public static void init(HudManager hud, NotificationManager toasts) {
        hudManager = hud;
        notifications = toasts;
    }

    public static float uiScale() {
        class_310 mc = class_310.method_1551();
        return Math.max(1.0f, (float)mc.method_22683().method_4506() / 1080.0f);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void render() {
        boolean motionBlur;
        GpuTexture gpuTexture;
        try {
            GlintTextureTinter.tick();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (crashed || hudManager == null) {
            return;
        }
        class_310 mc = class_310.method_1551();
        class_276 target = mc.method_1522();
        if (target == null || !((gpuTexture = target.method_30277()) instanceof class_10868)) {
            return;
        }
        class_10868 colorTexture = (class_10868)gpuTexture;
        boolean guiScreen = mc.field_1755 instanceof NvgDrawable;
        boolean chatEditing = mc.field_1755 instanceof class_408;
        boolean drawHud = !mc.field_1690.field_1842 && mc.field_1687 != null && !guiScreen;
        boolean drawToasts = mc.field_1687 != null && !mc.field_1690.field_1842;
        MotionBlurModule mbModule = SixSevenClient.modules() != null ? SixSevenClient.modules().motionBlur : null;
        boolean bl = motionBlur = mbModule != null && mbModule.isEnabled() && mc.field_1687 != null && !guiScreen;
        if (!(guiScreen || drawHud || drawToasts || motionBlur)) {
            return;
        }
        try {
            GlStateSnapshot state = GlStateSnapshot.capture();
            try {
                if (!OverlayRenderer.bindOverlayFbo(colorTexture, target.field_1482, target.field_1481)) {
                    return;
                }
                NVGRenderer vg = NVGRenderer.get();
                if (motionBlur) {
                    MotionBlurRenderer.render(vg, target.field_1482, target.field_1481, mbModule);
                }
                vg.setFontMode((String)SixSevenClient.modules().clickGui.font.get());
                if (!vg.hasFont()) {
                    return;
                }
                float scale = OverlayRenderer.uiScale();
                float uiWidth = (float)target.field_1482 / scale;
                float uiHeight = (float)target.field_1481 / scale;
                vg.beginFrame(target.field_1482, target.field_1481, 1.0f);
                vg.save();
                vg.scale(scale);
                if (drawHud) {
                    CustomCrosshairModule crosshair;
                    if (chatEditing && HudDragController.isDragging()) {
                        HudDragController.updateDrag(vg);
                    }
                    if ((crosshair = SixSevenClient.modules().customCrosshair).isEnabled() && mc.field_1755 == null) {
                        crosshair.render(vg, uiWidth / 2.0f, uiHeight / 2.0f);
                    }
                    if (mc.field_1755 == null) {
                        WorldNametagRenderer.render(vg);
                    }
                    hudManager.render(vg, uiWidth, uiHeight);
                    if (chatEditing) {
                        OverlayRenderer.renderChatEditOverlay(vg, uiWidth, uiHeight);
                    }
                }
                if (drawToasts) {
                    notifications.renderToasts(vg, uiWidth, uiHeight);
                }
                if (guiScreen) {
                    ((NvgDrawable)mc.field_1755).renderNvg(vg, OverlayRenderer.uiMouseX(), OverlayRenderer.uiMouseY(), uiWidth, uiHeight);
                }
                vg.restore();
                vg.endFrame();
            }
            finally {
                state.restore();
            }
        }
        catch (Throwable t) {
            crashed = true;
            SixSevenClient.LOGGER.error(Deobf.decrypt("E\u001b\u000b\u0002{\u00a1\u00a2\u009e\u00e9\u0114\u011f\u0106\u0131\u019c\u01f2\u01cd\u01be\u0209\u0214\u025b\u0238\u029c\u0287\u02c7\u02a9\u035e\u031a\u030e\u0340\u03c3\u03fd\u0393\u03bd\u0386\u040f\u042a\u0406\u047e\u04c8\u04b5\u04eb\u04e8\u054b\u0563\u0518\u0543\u05d2\u05e3\u05c1\u05a2\u060a\u063a"), t);
        }
    }

    private static void renderChatEditOverlay(NVGRenderer vg, float uiWidth, float uiHeight) {
        Theme theme = SixSevenClient.themes().current();
        float mx = OverlayRenderer.uiMouseX();
        float my = OverlayRenderer.uiMouseY();
        String hint = Deobf.decrypt("7^)\t2\u00b0\u00a3\u00ca\u00a4\u0114\u011f\u0106\u0163\u01d0\u0124\u0194\u01be\u0208\u0212\u0247\u0233\u0295\u0299\u0282\u02af\u0311\u0359\u030e\u0344\u03c3\u03fc\u038c\u03bc");
        vg.text(hint, (uiWidth - vg.textWidth(hint, 14.0f)) / 2.0f, 22.0f, 14.0f, Colors.withAlpha(theme.textMuted(), 0.9f));
        for (HudManager.Placement p : hudManager.layout(vg, uiWidth, uiHeight, true)) {
            boolean active;
            boolean hidden = !p.component().visible();
            boolean bl = active = p.contains(mx, my) || p.component() == HudDragController.draggedComponent();
            if (hidden) {
                vg.save();
                vg.alpha(0.35f);
                hudManager.renderPlacement(vg, p);
                vg.restore();
            }
            int outline = active ? theme.accentBright() : Colors.withAlpha(theme.accent(), 0.5f);
            vg.rectOutline(p.x() - 3.0f, p.y() - 3.0f, p.w() + 6.0f, p.h() + 6.0f, 6.0f, active ? 1.6f : 1.0f, outline);
            vg.rect(p.x() + p.w() - 2.0f, p.y() + p.h() - 2.0f, 6.0f, 6.0f, 2.0f, outline);
            if (!active) continue;
            String pct = Math.round(p.component().getScale() * 100.0f) + "%";
            vg.text(pct, p.x() + p.w() + 6.0f, p.y() + p.h() / 2.0f, 11.0f, theme.accentBright());
        }
    }

    private static boolean bindOverlayFbo(class_10868 colorTexture, int width, int height) {
        if (fbo == -1) {
            fbo = GL33C.glGenFramebuffers();
        }
        GL33C.glBindFramebuffer((int)36160, (int)fbo);
        GL33C.glFramebufferTexture2D((int)36160, (int)36064, (int)3553, (int)colorTexture.method_68427(), (int)0);
        if (GL33C.glCheckFramebufferStatus((int)36160) != 36053) {
            return false;
        }
        GL33C.glViewport((int)0, (int)0, (int)width, (int)height);
        GL33C.glDisable((int)3089);
        return true;
    }

    public static float uiMouseX() {
        class_310 mc = class_310.method_1551();
        return (float)(mc.field_1729.method_1603() * (double)mc.method_22683().method_4489() / (double)Math.max(1, mc.method_22683().method_4480())) / OverlayRenderer.uiScale();
    }

    public static float uiMouseY() {
        class_310 mc = class_310.method_1551();
        return (float)(mc.field_1729.method_1604() * (double)mc.method_22683().method_4506() / (double)Math.max(1, mc.method_22683().method_4507())) / OverlayRenderer.uiScale();
    }

    public static float guiToUi(double guiCoord) {
        class_310 mc = class_310.method_1551();
        float pixels = (float)(guiCoord * (double)mc.method_22683().method_4495());
        return pixels / OverlayRenderer.uiScale();
    }

    public static float uiWidth() {
        class_310 mc = class_310.method_1551();
        return (float)mc.method_22683().method_4489() / OverlayRenderer.uiScale();
    }

    public static float uiHeight() {
        class_310 mc = class_310.method_1551();
        return (float)mc.method_22683().method_4506() / OverlayRenderer.uiScale();
    }

    static {
        fbo = -1;
    }
}

