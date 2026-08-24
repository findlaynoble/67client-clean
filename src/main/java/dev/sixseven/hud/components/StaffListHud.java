/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.module.impl.StaffListModule;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.anim.Easing;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.staff.StaffEntry;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StaffListHud
extends HudComponent {
    private static final float PAD = 8.0f;
    private static final float HEADER_H = 15.0f;
    private static final float GAP_HEADER = 5.0f;
    private static final float ROW_H = 18.0f;
    private static final float EMPTY_H = 16.0f;
    private static final float OVERFLOW_H = 13.0f;
    private static final float MARK_W = 15.0f;
    private static final float PING_W = 11.0f;
    private static final float FONT_TITLE = 9.0f;
    private static final float FONT_NAME = 12.5f;
    private static final float FONT_RANK = 9.0f;
    private static final float FONT_EMPTY = 11.0f;
    private static final float MIN_CONTENT_W = 92.0f;
    private static final float MAX_NAME_W = 128.0f;
    private static final int GREEN = -11870592;
    private static final int YELLOW = -340971;
    private static final int RED = -495247;
    private final StaffListModule module;
    private final ThemeManager themes;
    private final Map<String, RowAnim> rows = new LinkedHashMap<String, RowAnim>();

    public StaffListHud(StaffListModule module, ThemeManager themes) {
        super(Deobf.decrypt("\u0000X)\bt\u0088\u00a5\u0099\u00bd"), 0.008f, 0.27f, module::isEnabled);
        this.module = module;
        this.themes = themes;
    }

    private int shownCount(List<StaffEntry> staff) {
        return Math.min(staff.size(), Math.max(1, this.module.maxRows.getInt()));
    }

    private List<RowAnim> layoutRows(List<StaffEntry> staff) {
        RowAnim r;
        int shown = this.shownCount(staff);
        HashSet<String> visible = new HashSet<String>();
        for (int i = 0; i < shown && i < staff.size(); ++i) {
            StaffEntry e = staff.get(i);
            visible.add(e.name());
            r = this.rows.get(e.name());
            if (r == null) {
                r = new RowAnim(e);
                this.rows.put(e.name(), r);
            } else {
                r.entry = e;
            }
            r.anim.setTarget(1.0f);
        }
        ArrayList<RowAnim> out = new ArrayList<RowAnim>();
        for (int i = 0; i < shown && i < staff.size(); ++i) {
            out.add(this.rows.get(staff.get(i).name()));
        }
        Iterator<RowAnim> it = this.rows.values().iterator();
        while (it.hasNext()) {
            r = it.next();
            if (visible.contains(r.entry.name())) continue;
            r.anim.setTarget(0.0f);
            if (r.anim.value() <= 0.01f) {
                it.remove();
                continue;
            }
            out.add(r);
        }
        return out;
    }

    private float rowWidth(NVGRenderer vg, StaffEntry e) {
        float w = 15.0f + Math.min(vg.textWidth(e.name(), 12.5f), 128.0f);
        if (((Boolean)this.module.showRank.get()).booleanValue() && !e.rankLabel().isEmpty()) {
            w += 6.0f + vg.textWidth(e.rankLabel(), 9.0f);
        }
        if (((Boolean)this.module.showPing.get()).booleanValue()) {
            w += 19.0f;
        }
        return w;
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        List<StaffEntry> staff = this.module.staff();
        float content = 92.0f;
        String count = Integer.toString(staff.size());
        content = Math.max(content, vg.textWidth(Deobf.decrypt(" x\t(T"), 9.0f) + 10.0f + vg.textWidth(count, 8.5f) + 9.0f);
        for (RowAnim r : this.layoutRows(staff)) {
            content = Math.max(content, this.rowWidth(vg, r.entry));
        }
        if (staff.isEmpty()) {
            content = Math.max(content, 14.0f + vg.textWidth(Deobf.decrypt("=Ch\u001df\u00a5\u00aa\u008c\u00e9\u0114\u0107\u010f\u012a\u019e\u01f6"), 11.0f));
        }
        return 16.0f + content;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        List<StaffEntry> staff = this.module.staff();
        float listH = 0.0f;
        for (RowAnim r : this.layoutRows(staff)) {
            listH += 18.0f * r.anim.value();
        }
        if (staff.isEmpty() && listH < 0.5f) {
            listH = 16.0f;
        }
        float h = 28.0f + listH + 8.0f;
        if (staff.size() > this.shownCount(staff)) {
            h += 13.0f;
        }
        return h;
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        List<StaffEntry> staff = this.module.staff();
        vg.glow(x, y, w, h, 13.0f, 8.0f, Colors.withAlpha(-16777216, 0.3f));
        vg.rectGradient(x, y, w, h, 10.0f, theme.background(), theme.backgroundTo(), true);
        this.drawHeader(vg, theme, x, y, w, staff.size());
        float rowY = y + 8.0f + 15.0f + 5.0f;
        if (staff.isEmpty()) {
            float cy = rowY + 8.0f;
            vg.checkmark(x + 8.0f, cy - 4.0f, 8.0f, 1.7f, -11870592);
            vg.text(Deobf.decrypt("=Ch\u001df\u00a5\u00aa\u008c\u00e9\u0114\u0107\u010f\u012a\u019e\u01f6"), x + 8.0f + 14.0f, cy, 11.0f, theme.textMuted());
            return;
        }
        for (RowAnim r : this.layoutRows(staff)) {
            float t = Math.clamp(r.anim.value(), 0.0f, 1.0f);
            if (t <= 0.01f) continue;
            this.drawRow(vg, theme, r.entry, x, rowY, w, t);
            rowY += 18.0f * t;
        }
        int overflow = staff.size() - this.shownCount(staff);
        if (overflow > 0) {
            vg.text("+" + overflow + " more", x + 8.0f + 15.0f, rowY + 6.5f, 9.0f, theme.textDisabled());
        }
    }

    private void drawHeader(NVGRenderer vg, Theme theme, float x, float y, float w, int count) {
        float cy = y + 8.0f + 7.5f;
        vg.text(Deobf.decrypt(" x\t(T"), x + 8.0f, cy, 9.0f, theme.textPrimary());
        String s = Integer.toString(count);
        float sw = vg.textWidth(s, 8.5f);
        float bw = sw + 9.0f;
        float bh = 12.5f;
        float bx = x + w - 8.0f - bw;
        float by = cy - bh / 2.0f;
        boolean any = count > 0;
        vg.rect(bx, by, bw, bh, 6.0f, Colors.withAlpha(theme.accent(), any ? 0.2f : 0.1f));
        vg.rectOutline(bx, by, bw, bh, 6.0f, 1.0f, Colors.withAlpha(theme.accent(), any ? 0.5f : 0.22f));
        vg.text(s, bx + 4.5f, cy, 8.5f, any ? theme.accentBright() : theme.textMuted());
    }

    private void drawRow(NVGRenderer vg, Theme theme, StaffEntry e, float x, float rowY, float w, float t) {
        float cy = rowY + 9.0f;
        int color = e.hasColor() ? e.color() : theme.accent();
        vg.save();
        vg.alpha(t);
        vg.translate((1.0f - t) * -10.0f, 0.0f);
        this.drawStar(vg, x + 8.0f + 7.5f - 2.0f, cy, 4.2f, color, e.vanished());
        float nameX = x + 8.0f + 15.0f;
        int nameColor = e.vanished() ? theme.textMuted() : theme.textPrimary();
        float adv = vg.textTruncated(e.name(), nameX, cy, 12.5f, nameColor, 128.0f);
        if (((Boolean)this.module.showRank.get()).booleanValue() && !e.rankLabel().isEmpty()) {
            int rankColor = e.hasColor() ? Colors.lighten(color, 0.25f) : theme.accent();
            vg.text(e.rankLabel(), nameX + adv + 6.0f, cy + 0.5f, 9.0f, Colors.withAlpha(rankColor, e.vanished() ? 0.6f : 0.95f));
        }
        if (((Boolean)this.module.showPing.get()).booleanValue()) {
            this.drawPingBars(vg, x + w - 8.0f - 11.0f, cy, e.latency(), theme);
        }
        vg.restore();
    }

    private void drawStar(NVGRenderer vg, float cx, float cy, float r, int color, boolean vanished) {
        if (vanished) {
            vg.save();
            vg.translate(cx, cy);
            vg.rotate(0.7853982f);
            vg.rectOutline(-r * 0.55f, -r * 0.55f, r * 1.1f, r * 1.1f, r * 0.25f, 1.2f, Colors.withAlpha(color, 0.55f));
            vg.restore();
            return;
        }
        int bright = Colors.lighten(color, 0.4f);
        vg.circleGlow(cx, cy, r * 0.5f, r * 1.3f, Colors.withAlpha(color, 0.5f));
        int ray = Colors.withAlpha(bright, 0.9f);
        vg.line(cx, cy - r, cx, cy + r, 1.2f, ray);
        vg.line(cx - r, cy, cx + r, cy, 1.2f, ray);
        vg.save();
        vg.translate(cx, cy);
        vg.rotate(0.7853982f);
        vg.rect(-r * 0.5f, -r * 0.5f, r, r, r * 0.22f, color);
        vg.restore();
    }

    private void drawPingBars(NVGRenderer vg, float x, float cy, int latency, Theme theme) {
        int bars;
        int n = latency < 0 ? 0 : (latency <= 80 ? 4 : (latency <= 150 ? 3 : (latency <= 300 ? 2 : (bars = latency <= 600 ? 1 : 0))));
        int col = bars >= 3 ? -11870592 : (bars == 2 ? -340971 : -495247);
        for (int i = 0; i < 4; ++i) {
            float bh = 2.0f + (float)i * 2.0f;
            float bx = x + (float)i * 3.0f;
            float by = cy + 4.0f - bh;
            int c = i < bars ? col : Colors.withAlpha(theme.textDisabled(), 0.45f);
            vg.rect(bx, by, 2.0f, bh, 0.5f, c);
        }
    }

    private static final class RowAnim {
        StaffEntry entry;
        final Animation anim = new Animation(220.0f, 0.0f, Easing.EASE_OUT_CUBIC);

        RowAnim(StaffEntry entry) {
            this.entry = entry;
        }
    }
}

