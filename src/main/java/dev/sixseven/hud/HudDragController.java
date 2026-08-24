/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_408
 */
package dev.sixseven.hud;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.hud.HudManager;
import dev.sixseven.render.OverlayRenderer;
import dev.sixseven.render.nanovg.NVGRenderer;
import net.minecraft.class_310;
import net.minecraft.class_408;

public final class HudDragController {
    private static HudComponent dragging;
    private static float grabDx;
    private static float grabDy;

    private HudDragController() {
    }

    public static boolean isEditing() {
        return class_310.method_1551().field_1755 instanceof class_408;
    }

    public static boolean isDragging() {
        return dragging != null;
    }

    public static boolean tryStartDrag(HudManager hud) {
        NVGRenderer vg = NVGRenderer.get();
        float mx = OverlayRenderer.uiMouseX();
        float my = OverlayRenderer.uiMouseY();
        for (HudManager.Placement p : hud.layout(vg, OverlayRenderer.uiWidth(), OverlayRenderer.uiHeight(), true)) {
            if (!p.contains(mx, my)) continue;
            float scale = p.component().getScale();
            if (p.component().onEditClick((mx - p.x()) / scale, (my - p.y()) / scale)) {
                return true;
            }
            dragging = p.component();
            grabDx = mx - p.x();
            grabDy = my - p.y();
            return true;
        }
        return false;
    }

    public static boolean tryResize(HudManager hud, double scrollY) {
        NVGRenderer vg = NVGRenderer.get();
        float mx = OverlayRenderer.uiMouseX();
        float my = OverlayRenderer.uiMouseY();
        for (HudManager.Placement p : hud.layout(vg, OverlayRenderer.uiWidth(), OverlayRenderer.uiHeight(), true)) {
            if (!p.contains(mx, my)) continue;
            HudComponent component = p.component();
            component.setScale(component.getScale() + (float)scrollY * 0.06f);
            return true;
        }
        return false;
    }

    public static void updateDrag(NVGRenderer vg) {
        if (dragging == null) {
            return;
        }
        float uiWidth = OverlayRenderer.uiWidth();
        float uiHeight = OverlayRenderer.uiHeight();
        float scale = dragging.getScale();
        float w = dragging.measureWidth(vg) * scale;
        float h = dragging.measureHeight(vg) * scale;
        float freeW = Math.max(1.0f, uiWidth - w);
        float freeH = Math.max(1.0f, uiHeight - h);
        dragging.setPosition((OverlayRenderer.uiMouseX() - grabDx) / freeW, (OverlayRenderer.uiMouseY() - grabDy) / freeH);
    }

    public static void stopDrag() {
        dragging = null;
    }

    public static HudComponent draggedComponent() {
        return dragging;
    }
}

