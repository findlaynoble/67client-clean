/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.nanovg.NVGColor
 *  org.lwjgl.nanovg.NVGPaint
 *  org.lwjgl.nanovg.NanoVG
 *  org.lwjgl.system.MemoryStack
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.module.Modules;
import dev.sixseven.render.nanovg.NVGImages;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.spotify.SpotifyService;
import dev.sixseven.spotify.SpotifyState;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;

public class SpotifyHud
extends HudComponent {
    public static final float WIDTH = 252.0f;
    public static final float HEIGHT = 74.0f;
    private static final float ART = 54.0f;
    private static final float TEXT_X = 76.0f;
    private static final float VOL_ROW = 18.0f;
    private static final float VBAR_X = 36.0f;
    private static final float VBAR_TRIM = 46.0f;
    private static final float VOL_CY = 80.0f;
    private final Modules.SpotifyModule module;
    private final SpotifyService service;
    private final ThemeManager themes;
    private static final long DEMO_START = System.nanoTime();

    public SpotifyHud(Modules.SpotifyModule module, SpotifyService service, ThemeManager themes) {
        super(Deobf.decrypt("\u0000\\'\u001a{\u00a2\u00b5"), 0.5f, 0.965f, module::isEnabled);
        this.module = module;
        this.service = service;
        this.themes = themes;
    }

    private boolean demo() {
        return this.module.source.is(Deobf.decrypt("7I%\u0001"));
    }

    private SpotifyState state() {
        if (this.demo()) {
            long pos = (System.nanoTime() - DEMO_START) / 1000000L % 227000L;
            return new SpotifyState(true, Deobf.decrypt("=I'\u00002\u008a\u00a5\u008d\u00a1\u010f\u011a"), Deobf.decrypt("E\u001bh=}\u00b1\u00a2\u008e"), pos, 227000L, true, true, 0, 70, System.nanoTime());
        }
        return this.service.state();
    }

    private boolean showVolume() {
        return (Boolean)this.module.volume.get() != false && this.state().active();
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 252.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        return this.showVolume() ? 92.0f : 74.0f;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        int art;
        SpotifyState state = this.state();
        Theme theme = this.themes.current();
        if (!state.active() && ((Boolean)this.module.hideWhenIdle.get()).booleanValue()) {
            return;
        }
        vg.glow(x, y, w, h, 13.0f, 8.0f, Colors.withAlpha(-16777216, 0.3f));
        vg.rectGradient(x, y, w, h, 13.0f, Colors.withAlpha(-15264995, 0.92f), Colors.withAlpha(-15856878, 0.92f), true);
        float ax = x + 10.0f;
        float ay = y + 10.0f;
        int n = art = this.demo() ? -1 : NVGImages.fromFile(this.service.artPath(), state.artVersion());
        if (!this.demo() && art > 0) {
            this.drawRoundedImage(vg, art, ax, ay, 54.0f, 8.0f);
        } else {
            this.drawVinyl(vg, theme, ax, ay, 54.0f);
        }
        if (!state.active()) {
            vg.text(Deobf.decrypt("=C<\u0006{\u00aa\u00ab\u00ca\u00b9\u0117\u0108\u011a\u012a\u019e\u01f4"), x + 76.0f, y + h / 2.0f, 13.5f, theme.textMuted());
            return;
        }
        float controlsX = (Boolean)this.module.controls.get() != false ? x + w - 84.0f : x + w - 12.0f;
        vg.textTruncated(state.title(), x + 76.0f, y + 20.0f, 14.0f, theme.textPrimary(), controlsX - (x + 76.0f) - 6.0f);
        vg.textTruncated(state.artist(), x + 76.0f, y + 38.0f, 11.5f, theme.textMuted(), controlsX - (x + 76.0f) - 6.0f);
        if (((Boolean)this.module.controls.get()).booleanValue()) {
            float cy = y + 22.0f;
            int idle = theme.textMuted();
            this.drawPrev(vg, x + w - 76.0f, cy, idle);
            this.drawPlayPause(vg, x + w - 52.0f, cy, theme.accentBright(), state.playing());
            this.drawNext(vg, x + w - 28.0f, cy, idle);
        }
        float barX = x + 76.0f;
        float barW = w - 76.0f - 12.0f;
        float barY = y + 58.0f;
        float frac = state.durMs() > 0L ? Math.clamp((float)state.livePosMs() / (float)state.durMs(), 0.0f, 1.0f) : 0.0f;
        vg.text(SpotifyHud.time(state.livePosMs()), barX, y + 49.0f, 9.5f, theme.textDisabled());
        String total = SpotifyHud.time(state.durMs());
        vg.text(total, barX + barW - vg.textWidth(total, 9.5f), y + 49.0f, 9.5f, theme.textDisabled());
        vg.rect(barX, barY, barW, 4.0f, 2.0f, Colors.withAlpha(-16777216, 0.5f));
        vg.rectGradient(barX, barY, Math.max(4.0f, barW * frac), 4.0f, 2.0f, theme.accent(), theme.accentBright(), false);
        vg.circle(barX + barW * frac, barY + 2.0f, 4.0f, -1);
        if (((Boolean)this.module.volume.get()).booleanValue()) {
            int vol = state.volume();
            float vFrac = Math.clamp((float)(vol < 0 ? 0 : vol) / 100.0f, 0.0f, 1.0f);
            float vBarX = x + 36.0f;
            float vBarW = w - 36.0f - 46.0f;
            float vBarY = y + 80.0f - 2.0f;
            this.drawSpeaker(vg, x + 18.0f, y + 80.0f, theme.textMuted(), vol == 0);
            vg.rect(vBarX, vBarY, vBarW, 4.0f, 2.0f, Colors.withAlpha(-16777216, 0.5f));
            vg.rectGradient(vBarX, vBarY, Math.max(4.0f, vBarW * vFrac), 4.0f, 2.0f, theme.accent(), theme.accentBright(), false);
            vg.circle(vBarX + vBarW * vFrac, y + 80.0f, 4.0f, -1);
            Object label = vol < 0 ? Deobf.decrypt("^\u0001") : vol + "%";
            vg.text((String)label, x + w - vg.textWidth((String)label, 9.5f) - 12.0f, y + 80.0f, 9.5f, theme.textDisabled());
        }
    }

    private void drawSpeaker(NVGRenderer vg, float cx, float cy, int color, boolean muted) {
        long ctx = vg.ctx();
        try (MemoryStack stack = MemoryStack.stackPush();){
            NVGColor col = NanoVG.nvgRGBA((byte)((byte)Colors.red(color)), (byte)((byte)Colors.green(color)), (byte)((byte)Colors.blue(color)), (byte)((byte)Colors.alpha(color)), (NVGColor)NVGColor.malloc((MemoryStack)stack));
            vg.rect(cx - 7.0f, cy - 3.0f, 5.0f, 6.0f, 1.0f, color);
            NanoVG.nvgFillColor((long)ctx, (NVGColor)col);
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgMoveTo((long)ctx, (float)(cx - 6.0f), (float)cy);
            NanoVG.nvgLineTo((long)ctx, (float)(cx + 2.0f), (float)(cy - 7.0f));
            NanoVG.nvgLineTo((long)ctx, (float)(cx + 2.0f), (float)(cy + 7.0f));
            NanoVG.nvgClosePath((long)ctx);
            NanoVG.nvgFill((long)ctx);
            NanoVG.nvgStrokeColor((long)ctx, (NVGColor)col);
            NanoVG.nvgStrokeWidth((long)ctx, (float)1.7f);
            NanoVG.nvgLineCap((long)ctx, (int)1);
            if (muted) {
                NanoVG.nvgBeginPath((long)ctx);
                NanoVG.nvgMoveTo((long)ctx, (float)(cx + 5.0f), (float)(cy - 3.5f));
                NanoVG.nvgLineTo((long)ctx, (float)(cx + 10.0f), (float)(cy + 3.5f));
                NanoVG.nvgMoveTo((long)ctx, (float)(cx + 10.0f), (float)(cy - 3.5f));
                NanoVG.nvgLineTo((long)ctx, (float)(cx + 5.0f), (float)(cy + 3.5f));
                NanoVG.nvgStroke((long)ctx);
            } else {
                for (float r : new float[]{4.5f, 8.0f}) {
                    NanoVG.nvgBeginPath((long)ctx);
                    NanoVG.nvgArc((long)ctx, (float)(cx + 2.0f), (float)cy, (float)r, (float)-0.6f, (float)0.6f, (int)2);
                    NanoVG.nvgStroke((long)ctx);
                }
            }
        }
    }

    private static String time(long ms) {
        long s = ms / 1000L;
        return String.format(Deobf.decrypt("VHrK\"\u00f6\u00a8"), s / 60L, s % 60L);
    }

    private void drawRoundedImage(NVGRenderer vg, int image, float x, float y, float size, float radius) {
        try (MemoryStack stack = MemoryStack.stackPush();){
            long ctx = vg.ctx();
            NVGPaint paint = NVGPaint.malloc((MemoryStack)stack);
            NanoVG.nvgImagePattern((long)ctx, (float)x, (float)y, (float)size, (float)size, (float)0.0f, (int)image, (float)1.0f, (NVGPaint)paint);
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgRoundedRect((long)ctx, (float)x, (float)y, (float)size, (float)size, (float)radius);
            NanoVG.nvgFillPaint((long)ctx, (NVGPaint)paint);
            NanoVG.nvgFill((long)ctx);
        }
    }

    private void drawVinyl(NVGRenderer vg, Theme theme, float x, float y, float size) {
        float cx = x + size / 2.0f;
        float cy = y + size / 2.0f;
        vg.rect(x, y, size, size, 8.0f, Colors.withAlpha(-16119795, 0.9f));
        vg.circle(cx, cy, size * 0.4f, -15330789);
        vg.circleOutline(cx, cy, size * 0.3f, 1.0f, Colors.withAlpha(theme.accent(), 0.35f));
        vg.circleOutline(cx, cy, size * 0.22f, 1.0f, Colors.withAlpha(theme.accent(), 0.25f));
        vg.circle(cx, cy, size * 0.12f, theme.accent());
        vg.textGradient(Deobf.decrypt("E\u001b"), cx - vg.textWidth(Deobf.decrypt("E\u001b"), 9.0f) / 2.0f, cy, 9.0f, -1, -1122834);
    }

    private void drawPrev(NVGRenderer vg, float cx, float cy, int color) {
        this.triangle(vg, cx + 4.0f, cy, -7.0f, color);
        vg.rect(cx - 7.0f, cy - 5.5f, 2.0f, 11.0f, 1.0f, color);
    }

    private void drawNext(NVGRenderer vg, float cx, float cy, int color) {
        this.triangle(vg, cx - 4.0f, cy, 7.0f, color);
        vg.rect(cx + 5.0f, cy - 5.5f, 2.0f, 11.0f, 1.0f, color);
    }

    private void drawPlayPause(NVGRenderer vg, float cx, float cy, int color, boolean playing) {
        vg.circleOutline(cx, cy, 10.0f, 1.4f, color);
        if (playing) {
            vg.rect(cx - 3.5f, cy - 4.5f, 2.4f, 9.0f, 1.2f, color);
            vg.rect(cx + 1.1f, cy - 4.5f, 2.4f, 9.0f, 1.2f, color);
        } else {
            this.triangle(vg, cx - 2.5f, cy, 7.0f, color);
        }
    }

    private void triangle(NVGRenderer vg, float x, float cy, float dir, int color) {
        try (MemoryStack stack = MemoryStack.stackPush();){
            long ctx = vg.ctx();
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgMoveTo((long)ctx, (float)x, (float)(cy - 5.5f));
            NanoVG.nvgLineTo((long)ctx, (float)x, (float)(cy + 5.5f));
            NanoVG.nvgLineTo((long)ctx, (float)(x + dir), (float)cy);
            NanoVG.nvgClosePath((long)ctx);
            NanoVG.nvgFillColor((long)ctx, (NVGColor)NanoVG.nvgRGBA((byte)((byte)Colors.red(color)), (byte)((byte)Colors.green(color)), (byte)((byte)Colors.blue(color)), (byte)((byte)Colors.alpha(color)), (NVGColor)NVGColor.malloc((MemoryStack)stack)));
            NanoVG.nvgFill((long)ctx);
        }
    }

    @Override
    public boolean onEditClick(float lx, float ly) {
        SpotifyState state = this.state();
        if (!state.active()) {
            return false;
        }
        if (((Boolean)this.module.controls.get()).booleanValue() && ly >= 10.0f && ly <= 34.0f) {
            if (SpotifyHud.hit(lx, 176.0f)) {
                if (this.demo()) {
                    return true;
                }
                this.service.previous();
                return true;
            }
            if (SpotifyHud.hit(lx, 200.0f)) {
                if (this.demo()) {
                    return true;
                }
                this.service.togglePlay();
                return true;
            }
            if (SpotifyHud.hit(lx, 224.0f)) {
                if (this.demo()) {
                    return true;
                }
                this.service.next();
                return true;
            }
        }
        float barX = 76.0f;
        float barW = 164.0f;
        if (ly >= 52.0f && ly <= 66.0f && lx >= barX && lx <= barX + barW && state.canSeek()) {
            long target = (long)((lx - barX) / barW * (float)state.durMs());
            if (!this.demo()) {
                this.service.seekTo(target);
            }
            return true;
        }
        float vBarX = 36.0f;
        float vBarW = 170.0f;
        if (((Boolean)this.module.volume.get()).booleanValue() && ly >= 73.0f && ly <= 87.0f && lx >= vBarX && lx <= vBarX + vBarW) {
            int pct = Math.round((lx - vBarX) / vBarW * 100.0f);
            if (!this.demo()) {
                this.service.setVolume(pct);
            }
            return true;
        }
        return false;
    }

    private static boolean hit(float lx, float centerX) {
        return Math.abs(lx - centerX) <= 11.0f;
    }
}

