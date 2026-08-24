/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.notification;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.hud.HudDragController;
import dev.sixseven.module.Modules;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.anim.Easing;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NotificationManager
extends HudComponent {
    private static final float WIDTH_PAD = 12.0f;
    private static final float HEIGHT = 30.0f;
    private static final float HEIGHT_WEATHER = 42.0f;
    private static final float GAP = 6.0f;
    private static final float FONT = 13.5f;
    private static final float FONT_SUB = 11.0f;
    private static final float GLYPH = 26.0f;
    private static final float ANCHOR_W = 168.0f;
    private static final float ANCHOR_H = 30.0f;
    private final ThemeManager themes;
    private final Modules.HudModule hud;
    private final List<Toast> toasts = new ArrayList<Toast>();

    public NotificationManager(ThemeManager themes, Modules.HudModule hud) {
        super(Deobf.decrypt("\u001dC<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d\u0130"), 0.99f, 0.71f, () -> hud.isEnabled() && (Boolean)hud.notifications.get() != false);
        this.themes = themes;
        this.hud = hud;
    }

    private boolean enabled() {
        return this.hud.isEnabled() && (Boolean)this.hud.notifications.get() != false;
    }

    private float lifeSeconds() {
        return this.hud.notifyDuration.getFloat();
    }

    public void push(String moduleName, boolean enabled) {
        this.add(new Toast(moduleName + (enabled ? Deobf.decrypt("SI&\u000fp\u00a8\u00a9\u008e") : Deobf.decrypt("SH!\u001ds\u00a6\u00a0\u008f\u00ad")), null, null, enabled));
    }

    public void pushInfo(String text) {
        this.add(new Toast(text, null, null, true));
    }

    public void pushWeather(String title, String subtitle, Weather weather, boolean starting) {
        this.add(new Toast(title, subtitle, weather, starting));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void add(Toast toast) {
        if (!this.enabled()) {
            return;
        }
        List<Toast> list = this.toasts;
        synchronized (list) {
            this.toasts.add(toast);
            if (this.toasts.size() > 6) {
                this.toasts.removeFirst();
            }
        }
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        return 168.0f;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        return 30.0f;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void renderToasts(NVGRenderer vg, float uiWidth, float uiHeight) {
        Theme theme = this.themes.current();
        float life = this.lifeSeconds();
        float lifeNanos = life * 1.0E9f;
        List<Toast> list = this.toasts;
        synchronized (list) {
            Iterator<Toast> it = this.toasts.iterator();
            ArrayList<Toast> alive = new ArrayList<Toast>();
            while (it.hasNext()) {
                Toast toast = it.next();
                if ((float)(System.nanoTime() - toast.bornNanos) > lifeNanos + 3.0E8f) {
                    it.remove();
                    continue;
                }
                alive.add(toast);
            }
            boolean editing = HudDragController.isEditing();
            if (alive.isEmpty() && !editing) {
                return;
            }
            float scale = this.getScale();
            float w = 168.0f * scale;
            float h = 30.0f * scale;
            float boxX = this.getFx() * (uiWidth - w);
            float boxY = this.getFy() * (uiHeight - h);
            vg.save();
            vg.translate(Math.round(boxX), Math.round(boxY));
            vg.scale(scale);
            float rightEdge = 168.0f;
            float yCursor = 30.0f;
            for (int i = alive.size() - 1; i >= 0; --i) {
                Toast toast = (Toast)alive.get(i);
                float age = (float)(System.nanoTime() - toast.bornNanos) / 1.0E9f;
                float fadeOut = Math.clamp((life + 0.3f - age) / 0.3f, 0.0f, 1.0f);
                float slide = toast.slide.value();
                float toastH = this.drawToast(vg, theme, rightEdge, yCursor, slide, fadeOut, toast.title, toast.subtitle, toast.weather, toast.accent);
                yCursor -= toastH + 6.0f;
            }
            if (alive.isEmpty() && editing) {
                this.drawToast(vg, theme, rightEdge, yCursor, 1.0f, 0.5f, Deobf.decrypt("=C<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d"), null, null, true);
            }
            vg.restore();
        }
    }

    private float drawToast(NVGRenderer vg, Theme theme, float rightEdge, float bottom, float slide, float fadeOut, String title, String subtitle, Weather weather, boolean accent) {
        boolean twoLine = subtitle != null;
        float h = twoLine ? 42.0f : 30.0f;
        float glyphW = weather != null ? 26.0f : 0.0f;
        float textStart = 16.0f + glyphW;
        float textW = Math.max(vg.textWidth(title, 13.5f), twoLine ? vg.textWidth(subtitle, 11.0f) : 0.0f);
        float w = textStart + textW + 12.0f;
        float x = rightEdge - w + (1.0f - slide) * (w + 10.0f);
        float y = bottom - h;
        int strip = accent ? theme.accent() : theme.textDisabled();
        vg.save();
        vg.alpha(fadeOut);
        vg.rectGradient(x, y, w, h, 9.0f, theme.background(), theme.backgroundTo(), true);
        vg.rect(x + 3.0f, y + 5.0f, 3.0f, h - 10.0f, 1.5f, strip);
        vg.glow(x, y, w, h, 9.0f, 5.0f, Colors.withAlpha(strip, 0.2f * fadeOut));
        if (weather != null) {
            this.drawWeatherGlyph(vg, x + 12.0f + 2.0f + glyphW / 2.0f - 4.0f, y + h / 2.0f, weather, strip, theme.accentBright());
        }
        if (twoLine) {
            if (accent) {
                vg.textGradient(title, x + textStart, y + h / 2.0f - 8.0f, 13.5f, theme.accentBright(), theme.accent());
            } else {
                vg.text(title, x + textStart, y + h / 2.0f - 8.0f, 13.5f, theme.textPrimary());
            }
            vg.text(subtitle, x + textStart, y + h / 2.0f + 8.0f, 11.0f, theme.textMuted());
        } else if (accent) {
            vg.textGradient(title, x + textStart, y + h / 2.0f, 13.5f, theme.accentBright(), theme.accent());
        } else {
            vg.text(title, x + textStart, y + h / 2.0f, 13.5f, theme.textMuted());
        }
        vg.restore();
        return h;
    }

    private void drawWeatherGlyph(NVGRenderer vg, float cx, float cy, Weather weather, int color, int boltColor) {
        switch (weather.ordinal()) {
            case 0: {
                this.drawCloud(vg, cx, cy - 3.0f, color);
                for (int k = -1; k <= 1; ++k) {
                    float dx = cx + (float)k * 4.0f;
                    vg.line(dx + 1.0f, cy + 4.0f, dx - 1.0f, cy + 9.0f, 1.6f, color);
                }
                break;
            }
            case 1: {
                this.drawCloud(vg, cx, cy - 3.0f, color);
                vg.line(cx + 1.5f, cy + 2.0f, cx - 2.5f, cy + 6.0f, 1.9f, boltColor);
                vg.line(cx - 2.5f, cy + 6.0f, cx + 1.5f, cy + 6.0f, 1.9f, boltColor);
                vg.line(cx + 1.5f, cy + 6.0f, cx - 2.5f, cy + 11.0f, 1.9f, boltColor);
                break;
            }
            case 2: {
                vg.circle(cx, cy, 4.5f, boltColor);
                for (int k = 0; k < 8; ++k) {
                    double ang = (double)k * Math.PI / 4.0;
                    float dxu = (float)Math.cos(ang);
                    float dyu = (float)Math.sin(ang);
                    vg.line(cx + dxu * 6.5f, cy + dyu * 6.5f, cx + dxu * 9.0f, cy + dyu * 9.0f, 1.6f, color);
                }
                break;
            }
        }
    }

    private void drawCloud(NVGRenderer vg, float cx, float cy, int color) {
        vg.rect(cx - 7.0f, cy - 1.0f, 14.0f, 5.0f, 2.5f, color);
        vg.circle(cx - 4.5f, cy + 1.0f, 4.0f, color);
        vg.circle(cx + 4.5f, cy + 1.0f, 4.0f, color);
        vg.circle(cx, cy - 2.5f, 5.0f, color);
    }

    private static class Toast {
        final String title;
        final String subtitle;
        final Weather weather;
        final boolean accent;
        final long bornNanos = System.nanoTime();
        final Animation slide = new Animation(220.0f, 0.0f, Easing.EASE_OUT_CUBIC);

        Toast(String title, String subtitle, Weather weather, boolean accent) {
            this.title = title;
            this.subtitle = subtitle;
            this.weather = weather;
            this.accent = accent;
            this.slide.setTarget(1.0f);
        }
    }

    public static enum Weather {
        RAIN,
        THUNDER,
        CLEAR;

    }
}

