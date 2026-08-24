/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_742
 *  net.minecraft.class_746
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.module.Modules;
import dev.sixseven.render.nanovg.NVGImages;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.suschunk.SusChunkScanner;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import java.util.function.BooleanSupplier;
import net.minecraft.class_310;
import net.minecraft.class_742;
import net.minecraft.class_746;

public class RadarHud
extends HudComponent {
    private static final float SIZE = 110.0f;
    private final Modules.HudModule module;
    private final Modules.SusChunkFinderModule susFinder;
    private final ThemeManager themes;

    public RadarHud(Modules.HudModule module, Modules.SusChunkFinderModule susFinder, ThemeManager themes, BooleanSupplier visible) {
        super(Deobf.decrypt("\u0001M,\u000f`"), 0.006f, 0.45f, visible);
        this.module = module;
        this.susFinder = susFinder;
        this.themes = themes;
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 110.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        return 110.0f;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        class_746 self = class_310.method_1551().field_1724;
        if (self == null) {
            return;
        }
        float radius = w / 2.0f;
        float cx = x + radius;
        float cy = y + radius;
        float bgAlpha = 0.55f;
        vg.circle(cx, cy, radius, Colors.withAlpha(-15856621, bgAlpha));
        vg.circleOutline(cx, cy, radius * 0.5f, 1.0f, Colors.withAlpha(theme.accent(), 0.22f));
        int cross = Colors.withAlpha(theme.accent(), 0.18f);
        vg.line(cx - radius, cy, cx + radius, cy, 1.0f, cross);
        vg.line(cx, cy - radius, cx, cy + radius, 1.0f, cross);
        float yaw = self.method_36454();
        float facing = (float)Math.toRadians(yaw + 90.0f);
        String[] names = new String[]{Deobf.decrypt("="), Deobf.decrypt("6"), Deobf.decrypt(" "), Deobf.decrypt("$")};
        float[][] dirs = new float[][]{{0.0f, -1.0f}, {1.0f, 0.0f}, {0.0f, 1.0f}, {-1.0f, 0.0f}};
        for (int i = 0; i < 4; ++i) {
            float sx = RadarHud.screenX(dirs[i][0], dirs[i][1], facing);
            float sy = RadarHud.screenY(dirs[i][0], dirs[i][1], facing);
            float mx = cx + sx * (radius - 9.0f);
            float my = cy + sy * (radius - 9.0f);
            boolean north = i == 0;
            vg.text(names[i], mx - vg.textWidth(names[i], 11.0f) / 2.0f, my, 11.0f, north ? theme.accentBright() : theme.textMuted());
        }
        float range = this.module.radarRange.getFloat();
        if (this.susFinder.isEnabled() && ((Boolean)this.susFinder.showOnRadar.get()).booleanValue()) {
            int threshold = this.susFinder.scanner.threshold();
            for (SusChunkScanner.Zone zone : this.susFinder.scanner.zones()) {
                this.drawZoneBlip(vg, theme, cx, cy, radius, range, facing, (float)(zone.centroidX() - self.method_23317()), (float)(zone.centroidZ() - self.method_23321()), RadarHud.strength(zone.maxScore(), threshold));
            }
        }
        vg.circleGlow(cx, cy, 3.0f, 4.0f, Colors.withAlpha(theme.accent(), 0.6f));
        vg.circle(cx, cy, 3.0f, theme.accentBright());
        float headSize = Math.max(10.0f, radius * 0.17f);
        for (class_742 other : class_310.method_1551().field_1687.method_18456()) {
            int skin;
            float dz;
            float dx;
            float dist;
            if (other == self || (dist = (float)Math.sqrt((dx = (float)(other.method_23317() - self.method_23317())) * dx + (dz = (float)(other.method_23321() - self.method_23321())) * dz)) > range) continue;
            float t = dist / range;
            float px = cx + RadarHud.screenX(dx, dz, facing) / Math.max(dist, 0.001f) * t * (radius - headSize / 2.0f - 3.0f);
            float py = cy + RadarHud.screenY(dx, dz, facing) / Math.max(dist, 0.001f) * t * (radius - headSize / 2.0f - 3.0f);
            if (((Boolean)this.module.radarHeads.get()).booleanValue() && (skin = NVGImages.wrapGlTexture(other.method_52814().comp_1626().comp_3627(), 64, 64)) > 0) {
                float hx = px - headSize / 2.0f;
                float hy = py - headSize / 2.0f;
                NVGImages.drawSubImage(vg, skin, 64.0f, 64.0f, 8.0f, 8.0f, 16.0f, 16.0f, hx, hy, headSize, headSize, 1.0f);
                NVGImages.drawSubImage(vg, skin, 64.0f, 64.0f, 40.0f, 8.0f, 48.0f, 16.0f, hx, hy, headSize, headSize, 1.0f);
                vg.rectOutline(hx - 1.0f, hy - 1.0f, headSize + 2.0f, headSize + 2.0f, 3.0f, 1.0f, Colors.withAlpha(-1, 0.35f));
                continue;
            }
            vg.circleGlow(px, py, 2.5f, 3.0f, Colors.withAlpha(-1, 0.4f));
            vg.circle(px, py, 2.5f, -1);
        }
    }

    private static float strength(double maxScore, int threshold) {
        if (threshold <= 0) {
            return 1.0f;
        }
        return Math.clamp((float)((maxScore - (double)threshold) / ((double)threshold * 2.0)), 0.0f, 1.0f);
    }

    private void drawZoneBlip(NVGRenderer vg, Theme theme, float cx, float cy, float radius, float range, float facing, float dx, float dz, float strength) {
        float dist = (float)Math.sqrt(dx * dx + dz * dz);
        if (dist < 0.5f) {
            return;
        }
        boolean clamped = dist > range;
        float t = Math.min(dist / range, 1.0f);
        float inset = clamped ? 6.0f : 9.0f;
        float bx = cx + RadarHud.screenX(dx, dz, facing) / dist * t * (radius - inset);
        float by = cy + RadarHud.screenY(dx, dz, facing) / dist * t * (radius - inset);
        double seconds = (double)(System.nanoTime() % 1000000000000L) / 1.0E9;
        float pulse = (float)(0.5 + 0.5 * Math.sin(seconds * Math.PI * 2.0 / 1.8));
        float half = (clamped ? 2.4f : 3.0f + 1.5f * strength) * (1.0f + 0.1f * pulse);
        float alpha = (0.72f + 0.28f * strength) * (0.88f + 0.12f * pulse);
        int accent = theme.accent();
        vg.glow(bx - half, by - half, half * 2.0f, half * 2.0f, half * 0.6f, 3.5f + 2.5f * pulse, Colors.withAlpha(accent, (0.3f + 0.25f * strength) * alpha));
        vg.rect(bx - half, by - half, half * 2.0f, half * 2.0f, half * 0.6f, Colors.withAlpha(accent, alpha));
        vg.rectOutline(bx - half, by - half, half * 2.0f, half * 2.0f, half * 0.6f, 1.0f, Colors.withAlpha(theme.accentBright(), 0.55f * alpha));
        if (clamped && dist <= range * 2.5f) {
            String label = (int)dist + "m";
            float lw = vg.textWidth(label, 8.5f);
            float tx = bx + (cx - bx) * 0.24f;
            float ty = by + (cy - by) * 0.24f;
            vg.rect(tx - lw / 2.0f - 3.0f, ty - 5.5f, lw + 6.0f, 11.0f, 5.5f, Colors.withAlpha(-15856621, 0.72f));
            vg.text(label, tx - lw / 2.0f, ty, 8.5f, Colors.withAlpha(theme.accentBright(), 0.95f));
        }
    }

    private static float screenX(float dx, float dz, float facing) {
        return (float)((double)(-dx) * Math.sin(facing) + (double)dz * Math.cos(facing));
    }

    private static float screenY(float dx, float dz, float facing) {
        return (float)(-((double)dx * Math.cos(facing) + (double)dz * Math.sin(facing)));
    }
}

