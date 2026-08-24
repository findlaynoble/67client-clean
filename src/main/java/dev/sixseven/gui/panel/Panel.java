/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.panel;

import dev.sixseven.gui.ClickGuiState;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;

public abstract class Panel {
    public static final float WIDTH = 210.0f;
    public static final float HEADER_H = 38.0f;
    public static final float RADIUS = 12.0f;
    protected static final float CONTENT_PAD = 6.0f;
    private static final float FADE_ZONE = 16.0f;
    protected final ThemeManager themes;
    protected final ClickGuiState.PanelState ps;
    private final Animation open;
    private final Animation scroll = new Animation(200.0f, 0.0f);
    private float maxScroll;
    private boolean headerHovered;

    protected Panel(ThemeManager themes, ClickGuiState.PanelState ps) {
        this.themes = themes;
        this.ps = ps;
        this.open = new Animation(200.0f, ps.collapsed ? 0.0f : 1.0f);
    }

    protected Theme theme() {
        return this.themes.current();
    }

    public float x() {
        return this.ps.x;
    }

    public float y() {
        return this.ps.y;
    }

    public void moveTo(float x, float y) {
        this.ps.x = x;
        this.ps.y = y;
    }

    public void toggleCollapsed() {
        this.ps.collapsed = !this.ps.collapsed;
        this.open.setTarget(this.ps.collapsed ? 0.0f : 1.0f);
    }

    protected abstract String title();

    protected int icon() {
        return -1;
    }

    protected abstract float contentHeight(NVGRenderer var1);

    protected abstract void renderContent(NVGRenderer var1, float var2, float var3, float var4, float var5, float var6);

    protected float maxViewHeight(float uiHeight) {
        return Math.max(60.0f, uiHeight - this.ps.y - 38.0f - 24.0f);
    }

    protected float viewHeight(NVGRenderer vg, float uiHeight) {
        return Math.min(this.contentHeight(vg), this.maxViewHeight(uiHeight)) * this.open.value();
    }

    public float totalHeight(NVGRenderer vg, float uiHeight) {
        return 38.0f + this.viewHeight(vg, uiHeight);
    }

    protected float edgeFade(float rowTop, float rowBottom, float viewTop, float viewBottom) {
        float fadeTop = Math.clamp((rowBottom - viewTop) / 16.0f, 0.0f, 1.0f);
        float fadeBottom = Math.clamp((viewBottom - rowTop) / 16.0f, 0.0f, 1.0f);
        return Math.min(fadeTop, fadeBottom);
    }

    public void render(NVGRenderer vg, float mx, float my, float uiWidth, float uiHeight) {
        this.ps.x = Math.clamp(this.ps.x, -170.0f, uiWidth - 40.0f);
        this.ps.y = Math.clamp(this.ps.y, 0.0f, uiHeight - 38.0f);
        Theme theme = this.theme();
        float viewH = this.viewHeight(vg, uiHeight);
        boolean hasBody = viewH > 0.5f;
        boolean hoveredNow = this.headerHit(mx, my);
        if (hoveredNow && !this.headerHovered) {
            UiSounds.hover();
        }
        this.headerHovered = hoveredNow;
        vg.glow(this.ps.x, this.ps.y, 210.0f, 38.0f + viewH, 12.0f, 14.0f, Colors.withAlpha(-16777216, 0.35f));
        if (hasBody) {
            vg.rectVaryingGradient(this.ps.x, this.ps.y + 38.0f, 210.0f, viewH, 0.0f, 0.0f, 12.0f, 12.0f, theme.background(), theme.backgroundTo());
        }
        float bottomRadius = hasBody ? 0.0f : 12.0f;
        vg.rectVaryingGradient(this.ps.x, this.ps.y, 210.0f, 38.0f, 12.0f, 12.0f, bottomRadius, bottomRadius, theme.headerTop(), theme.headerBottom());
        if (hasBody) {
            vg.rectGradient(this.ps.x + 1.0f, this.ps.y + 38.0f - 1.5f, 208.0f, 1.5f, 0.75f, Colors.withAlpha(theme.accent(), 0.85f), Colors.withAlpha(theme.accentBright(), 0.5f), false);
        }
        float textX = this.ps.x + 12.0f;
        int iconHandle = this.icon();
        if (iconHandle > 0) {
            vg.image(iconHandle, this.ps.x + 11.0f, this.ps.y + 19.0f - 9.0f, 18.0f, 18.0f, theme.accentBright());
            textX = this.ps.x + 36.0f;
        }
        vg.text(this.title(), textX, this.ps.y + 19.0f, 16.5f, theme.textPrimary());
        float chevX = this.ps.x + 210.0f - 16.0f;
        float chevY = this.ps.y + 19.0f;
        vg.save();
        vg.translate(chevX, chevY);
        vg.rotate((float)((double)this.open.value() * Math.PI / 2.0));
        vg.chevron(0.0f, 0.0f, 4.5f, 1.8f, theme.textMuted(), false);
        vg.restore();
        if (hasBody) {
            float viewTop = this.ps.y + 38.0f;
            float viewBottom = viewTop + viewH;
            this.maxScroll = Math.max(0.0f, this.contentHeight(vg) - this.maxViewHeight(uiHeight));
            this.scroll.setTarget(Math.clamp(this.scroll.getTarget(), 0.0f, this.maxScroll));
            vg.save();
            vg.scissor(this.ps.x, viewTop, 210.0f, viewH);
            this.renderContent(vg, viewTop + 6.0f - this.scroll.value(), mx, my, viewTop, viewBottom);
            vg.restore();
        }
    }

    public boolean headerHit(float mx, float my) {
        return mx >= this.ps.x && mx <= this.ps.x + 210.0f && my >= this.ps.y && my <= this.ps.y + 38.0f;
    }

    public boolean bodyHit(NVGRenderer vg, float mx, float my, float uiHeight) {
        float viewH = this.viewHeight(vg, uiHeight);
        return mx >= this.ps.x && mx <= this.ps.x + 210.0f && my >= this.ps.y + 38.0f && my <= this.ps.y + 38.0f + viewH;
    }

    public void onScroll(double amount) {
        if (this.maxScroll <= 0.0f) {
            return;
        }
        this.scroll.setTarget(Math.clamp(this.scroll.getTarget() - (float)amount * 38.0f, 0.0f, this.maxScroll));
    }

    public boolean mouseClicked(float mx, float my, int button) {
        return false;
    }

    public void mouseDragged(float mx, float my) {
    }

    public void mouseReleased() {
    }

    public boolean keyPressed(int keyCode) {
        return false;
    }

    public boolean charTyped(int codepoint) {
        return false;
    }

    public boolean isListening() {
        return false;
    }
}

