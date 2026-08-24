/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_746
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.module.impl.RegionMapModule;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import net.minecraft.class_310;
import net.minecraft.class_746;

public class RegionMapHud
extends HudComponent {
    private static final float PAD = 8.0f;
    private static final float CELL = 12.0f;
    private static final float GRID = 108.0f;
    private static final float HEADER_H = 15.0f;
    private static final float GAP_HEADER = 5.0f;
    private static final float GAP_LEGEND = 8.0f;
    private static final float LEGEND_ROW = 12.0f;
    private static final int LEGEND_COLS = 2;
    private final RegionMapModule module;
    private final ThemeManager themes;

    public RegionMapHud(RegionMapModule module, ThemeManager themes) {
        super(Deobf.decrypt("\u0001I/\u0007}\u00aa\u0081\u008b\u00b9"), 0.008f, 0.05f, module::isEnabled);
        this.module = module;
        this.themes = themes;
    }

    private float legendRows() {
        return (float)Math.ceil((double)this.module.regionTypeCount() / 2.0);
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 124.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        float h = 144.0f;
        if (((Boolean)this.module.legend.get()).booleanValue()) {
            h += 8.0f + this.legendRows() * 12.0f;
        }
        return h;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        class_746 player = class_310.method_1551().field_1724;
        if (player == null) {
            return;
        }
        float radius = 10.0f;
        vg.glow(x, y, w, h, 13.0f, 8.0f, Colors.withAlpha(-16777216, 0.3f));
        vg.rectGradient(x, y, w, h, radius, theme.background(), theme.backgroundTo(), true);
        int currentId = this.module.currentRegionId();
        int currentType = this.module.regionTypeAtWorld(player.method_23317(), player.method_23321());
        this.drawHeader(vg, theme, x, y, w, currentId);
        float gridX = x + 8.0f;
        float gridY = y + 8.0f + 15.0f + 5.0f;
        this.drawGrid(vg, theme, player, gridX, gridY);
        if (((Boolean)this.module.legend.get()).booleanValue()) {
            this.drawLegend(vg, theme, gridX, gridY + 108.0f + 8.0f, currentType);
        }
    }

    private void drawHeader(NVGRenderer vg, Theme theme, float x, float y, float w, int currentId) {
        float cy = y + 8.0f + 7.5f;
        vg.text(Deobf.decrypt("!i\u000f']\u008a\u00ec\u00a7\u0088\u012b"), x + 8.0f, cy, 9.0f, theme.textPrimary());
        Object reg = currentId >= 0 ? "#" + currentId : Deobf.decrypt("=\u0003\t");
        float rw = vg.textWidth((String)reg, 8.5f);
        float bw = rw + 9.0f;
        float bh = 12.5f;
        float bx = x + w - 8.0f - bw;
        float by = cy - bh / 2.0f;
        boolean on = currentId >= 0;
        vg.rect(bx, by, bw, bh, 6.0f, Colors.withAlpha(theme.accent(), on ? 0.18f : 0.1f));
        vg.rectOutline(bx, by, bw, bh, 6.0f, 1.0f, Colors.withAlpha(theme.accent(), on ? 0.45f : 0.2f));
        vg.text((String)reg, bx + 4.5f, cy, 8.5f, on ? theme.accentBright() : theme.textMuted());
    }

    private void drawGrid(NVGRenderer vg, Theme theme, class_746 player, float gridX, float gridY) {
        int[] g;
        boolean onMap;
        int n = this.module.mapSize();
        int alpha = (int)(Math.clamp(this.module.opacity.getFloat() / 100.0f, 0.0f, 1.0f) * 255.0f);
        vg.rect(gridX, gridY, 108.0f, 108.0f, 4.0f, Colors.withAlpha(-16054000, 0.92f));
        vg.save();
        vg.scissor(gridX, gridY, 108.0f, 108.0f);
        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col) {
                int type = this.module.regionTypeAt(row * n + col);
                if (type < 0) continue;
                int argb = Colors.withAlpha(0xFF000000 | this.module.regionTypeRgb(type), alpha);
                float cx = gridX + (float)col * 12.0f;
                float cyc = gridY + (float)row * 12.0f;
                vg.rect(cx + 1.0f, cyc + 1.0f, 10.0f, 10.0f, 1.5f, argb);
            }
        }
        if (((Boolean)this.module.gridLines.get()).booleanValue()) {
            int line = Colors.withAlpha(theme.accent(), 0.14f);
            for (int i = 0; i <= n; ++i) {
                float p = (float)i * 12.0f;
                vg.line(gridX + p, gridY, gridX + p, gridY + 108.0f, 1.0f, line);
                vg.line(gridX, gridY + p, gridX + 108.0f, gridY + p, 1.0f, line);
            }
        }
        boolean bl = onMap = (g = this.module.worldToGrid(player.method_23317(), player.method_23321()))[0] >= 0 && g[0] < n && g[1] >= 0 && g[1] < n;
        if (onMap) {
            float cx = gridX + (float)g[0] * 12.0f;
            float cyc = gridY + (float)g[1] * 12.0f;
            vg.glow(cx, cyc, 12.0f, 12.0f, 2.0f, 3.0f, Colors.withAlpha(theme.accent(), 0.35f));
            vg.rectOutline(cx + 0.5f, cyc + 0.5f, 11.0f, 11.0f, 2.0f, 1.2f, theme.accentBright());
        }
        if (((Boolean)this.module.cellNumbers.get()).booleanValue()) {
            for (int row = 0; row < n; ++row) {
                for (int col = 0; col < n; ++col) {
                    int type = this.module.regionTypeAt(row * n + col);
                    if (type < 0) continue;
                    String num = String.valueOf(this.module.regionIdAt(row * n + col));
                    float fs = num.length() >= 3 ? 5.5f : 6.5f;
                    float tw = vg.textWidth(num, fs);
                    float tx = gridX + (float)col * 12.0f + (12.0f - tw) / 2.0f;
                    float ty = gridY + (float)row * 12.0f + 6.0f;
                    vg.text(num, tx + 0.5f, ty + 0.5f, fs, Colors.withAlpha(-16777216, 0.55f));
                    vg.text(num, tx, ty, fs, -790280);
                }
            }
        }
        if (onMap) {
            double[] cp = this.module.worldToCellPosition(player.method_23317(), player.method_23321());
            float ax = gridX + (float)(((double)g[0] + cp[0]) * 12.0);
            float ay = gridY + (float)(((double)g[1] + cp[1]) * 12.0);
            vg.circleGlow(ax, ay, 2.2f, 3.5f, Colors.withAlpha(theme.accent(), 0.75f));
            vg.save();
            vg.translate(ax, ay);
            vg.rotate((float)Math.toRadians(player.method_36454() + 180.0f));
            vg.triangle(0.0f, -4.9f, 3.3f, 2.9f, -3.3f, 2.9f, Colors.withAlpha(-16777216, 0.55f));
            vg.triangle(0.0f, -4.0f, 2.6f, 2.2f, -2.6f, 2.2f, theme.accentBright());
            vg.restore();
        }
        vg.restore();
        vg.rectOutline(gridX, gridY, 108.0f, 108.0f, 4.0f, 1.0f, Colors.withAlpha(theme.accent(), 0.5f));
    }

    private void drawLegend(NVGRenderer vg, Theme theme, float lx, float ly, int currentType) {
        float colW = 54.0f;
        for (int type = 0; type < this.module.regionTypeCount(); ++type) {
            int col = type % 2;
            int row = type / 2;
            float ex = lx + (float)col * colW;
            float ey = ly + (float)row * 12.0f + 6.0f;
            vg.rect(ex, ey - 2.5f, 5.0f, 5.0f, 1.2f, 0xFF000000 | this.module.regionTypeRgb(type));
            boolean here = type == currentType;
            vg.text(this.module.regionTypeName(type), ex + 8.0f, ey, 7.5f, here ? theme.accentBright() : theme.textMuted());
        }
    }
}

