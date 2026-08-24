/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 */
package dev.sixseven.gui.config;

import dev.sixseven.SixSevenClient;
import dev.sixseven.config.ConfigStore;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_310;

public class ConfigPanel {
    private static final float CARD_W = 600.0f;
    private static final float HEADER_H = 64.0f;
    private static final float FOOTER_H = 38.0f;
    private static final float ROW_H = 60.0f;
    private static final float ROW_GAP = 8.0f;
    private static final float PAD = 18.0f;
    private static final float BTN_INSET = 16.0f;
    private static final int DANGER = -45730;
    private static final int DANGER_BRIGHT = -37252;
    private final Animation openAnim = new Animation(160.0f, 0.0f);
    private boolean open;
    private float cardX;
    private float cardY;
    private float cardH;
    private final List<Hit> hits = new ArrayList<Hit>();
    private float closeX;
    private float closeY;
    private float closeSize;
    private int renamingSlot = -1;
    private final StringBuilder renameBuffer = new StringBuilder();
    private Confirm pendingConfirm;
    private float confirmOkX;
    private float confirmOkY;
    private float confirmOkW;
    private float confirmOkH;
    private float confirmCancelX;
    private float confirmCancelY;
    private float confirmCancelW;
    private float confirmCancelH;
    private float confirmCardX;
    private float confirmCardY;
    private float confirmCardW;
    private float confirmCardH;

    public boolean isOpen() {
        return this.open;
    }

    public void open() {
        this.open = true;
        this.openAnim.setTarget(1.0f);
    }

    public void close() {
        this.open = false;
        this.openAnim.setTarget(0.0f);
        this.cancelRename();
        this.pendingConfirm = null;
    }

    public boolean isListening() {
        return this.open && this.renamingSlot >= 0;
    }

    private ConfigStore store() {
        return SixSevenClient.configStore();
    }

    public void render(NVGRenderer vg, float mx, float my, float uiWidth, float uiHeight) {
        float t = this.openAnim.value();
        if (t <= 0.002f && !this.open) {
            return;
        }
        Theme theme = SixSevenClient.themes().current();
        vg.rect(0.0f, 0.0f, uiWidth, uiHeight, 0.0f, Colors.withAlpha(-16316918, 0.55f * t));
        vg.save();
        vg.alpha(t);
        float scale = 0.97f + 0.03f * t;
        vg.translate(uiWidth / 2.0f, uiHeight / 2.0f);
        vg.scale(scale);
        vg.translate(-uiWidth / 2.0f, -uiHeight / 2.0f);
        ConfigStore store = this.store();
        this.cardH = 434.0f;
        this.cardX = (uiWidth - 600.0f) / 2.0f;
        this.cardY = (uiHeight - this.cardH) / 2.0f;
        vg.glow(this.cardX, this.cardY, 600.0f, this.cardH, 18.0f, 22.0f, Colors.withAlpha(-16777216, 0.45f));
        vg.rectVaryingGradient(this.cardX, this.cardY, 600.0f, this.cardH, 18.0f, 18.0f, 18.0f, 18.0f, theme.background(), theme.backgroundTo());
        vg.rectOutline(this.cardX, this.cardY, 600.0f, this.cardH, 18.0f, 1.2f, Colors.withAlpha(theme.accent(), 0.3f));
        this.renderHeader(vg, theme, mx, my);
        this.hits.clear();
        float rowY = this.cardY + 64.0f;
        for (int i = 0; i < 5; ++i) {
            this.renderSlot(vg, theme, store.slot(i), this.cardX + 18.0f, rowY, 564.0f, mx, my, store.activeIndex() == i);
            rowY += 68.0f;
        }
        this.renderFooter(vg, theme);
        if (this.pendingConfirm != null) {
            this.renderConfirm(vg, theme, mx, my, uiWidth, uiHeight);
        }
        vg.restore();
    }

    private void renderHeader(NVGRenderer vg, Theme theme, float mx, float my) {
        boolean hover;
        float gx = this.cardX + 18.0f;
        float gy = this.cardY + 22.0f;
        vg.rect(gx + 4.0f, gy + 3.0f, 15.0f, 12.0f, 3.0f, Colors.withAlpha(theme.accent(), 0.45f));
        vg.rect(gx, gy, 15.0f, 12.0f, 3.0f, theme.accentBright());
        vg.text(Deobf.decrypt("0C&\b{\u00a3\u00bf"), gx + 28.0f, this.cardY + 26.0f, 19.0f, theme.textPrimary());
        vg.text(Deobf.decrypt(" M>\u000b>\u00e4\u00ad\u0089\u00bd\u0112\u011f\u0102\u0137\u0195\u01b3\u0192\u01be\u0208\u0219\u0254\u022e\u029c\u02d5\u02db\u02b4\u030b\u030b\u035c\u0347\u03c5\u03f9\u039a\u03f9\u03de\u0443\u0427\u040a\u0463\u04dd\u04f7\u04f4\u04e4\u0551\u0571\u0548"), gx + 28.0f, this.cardY + 44.0f, 12.0f, theme.textMuted());
        this.closeSize = 16.0f;
        this.closeX = this.cardX + 600.0f - 18.0f - this.closeSize;
        this.closeY = this.cardY + 20.0f;
        boolean bl = hover = mx >= this.closeX - 4.0f && mx <= this.closeX + this.closeSize + 4.0f && my >= this.closeY - 4.0f && my <= this.closeY + this.closeSize + 4.0f;
        if (hover) {
            vg.rect(this.closeX - 5.0f, this.closeY - 5.0f, this.closeSize + 10.0f, this.closeSize + 10.0f, 6.0f, Colors.withAlpha(theme.accent(), 0.18f));
        }
        vg.cross(this.closeX, this.closeY, this.closeSize, 1.8f, hover ? theme.accentBright() : theme.textMuted());
        vg.rect(this.cardX + 18.0f, this.cardY + 64.0f - 2.0f, 564.0f, 1.0f, 0.5f, Colors.withAlpha(theme.accent(), 0.2f));
    }

    private void renderSlot(NVGRenderer vg, Theme theme, ConfigStore.Slot slot, float x, float y, float w, float mx, float my, boolean active) {
        boolean rowHover;
        boolean bl = rowHover = mx >= x && mx <= x + w && my >= y && my <= y + 60.0f;
        int fill = active ? theme.moduleActiveFill() : Colors.withAlpha(-16777216, rowHover ? 0.32f : 0.22f);
        vg.rect(x, y, w, 60.0f, 12.0f, fill);
        if (active) {
            vg.rect(x, y + 10.0f, 3.0f, 40.0f, 1.5f, theme.accentBright());
            vg.rectOutline(x, y, w, 60.0f, 12.0f, 1.1f, Colors.withAlpha(theme.accent(), 0.5f));
        }
        float dotX = x + 16.0f;
        float dotY = y + 30.0f;
        if (active) {
            vg.circleGlow(dotX, dotY, 4.0f, 5.0f, theme.accent());
            vg.circle(dotX, dotY, 4.0f, theme.accentBright());
        } else if (slot.filled()) {
            vg.circle(dotX, dotY, 3.5f, theme.statusEnabled());
        } else {
            vg.circleOutline(dotX, dotY, 3.5f, 1.2f, theme.statusDisabled());
        }
        float nameX = x + 32.0f;
        if (this.renamingSlot == slot.index()) {
            this.renderRenameField(vg, theme, nameX, y + 12.0f, 190.0f);
        } else {
            vg.textTruncated(slot.name(), nameX, y + 22.0f, 15.0f, theme.textPrimary(), 200.0f);
            if (active) {
                float nameW = vg.textWidth(slot.name(), 15.0f);
                this.drawTag(vg, theme, nameX + Math.min(nameW, 200.0f) + 8.0f, y + 22.0f, Deobf.decrypt("2o\u001c'D\u0081"));
            }
        }
        String status = this.renamingSlot == slot.index() ? Deobf.decrypt("6B<\u000b`\u00e4\u00b8\u0085\u00e9\u0118\u0106\u010d\u0125\u0199\u01e1\u01d9\u01be\u02cc\u0251\u0270\u022f\u029a\u02d5\u02d6\u02b4\u035e\u031a\u031d\u034f\u03d3\u03f0\u039a") : (slot.filled() ? "Saved \u00b7 " + ConfigPanel.relativeTime(slot.savedAt()) : Deobf.decrypt("6A8\u001ak\u00e4\u00bf\u0086\u00a6\u010f"));
        vg.text(status, nameX, y + 40.0f, 11.5f, theme.textMuted());
        this.layoutButtons(vg, theme, slot, x + w - 16.0f, y, mx, my);
    }

    private void renderRenameField(NVGRenderer vg, Theme theme, float x, float y, float w) {
        float h = 20.0f;
        vg.rect(x, y, w, h, h / 2.0f, Colors.withAlpha(-16777216, 0.5f));
        vg.rectOutline(x, y, w, h, h / 2.0f, 1.2f, Colors.withAlpha(theme.accentBright(), 0.9f));
        float tx = x + 8.0f;
        float ty = y + h / 2.0f;
        float tw = vg.text(this.renameBuffer.toString(), tx, ty, 12.5f, theme.textPrimary());
        if (System.nanoTime() / 400000000L % 2L == 0L) {
            vg.rect(tx + tw + 1.5f, ty - 6.0f, 1.4f, 12.0f, 0.7f, theme.accentBright());
        }
    }

    private void drawTag(NVGRenderer vg, Theme theme, float x, float y, String label) {
        float tw = vg.textWidth(label, 9.5f);
        vg.rect(x, y - 7.0f, tw + 12.0f, 14.0f, 7.0f, Colors.withAlpha(theme.accent(), 0.22f));
        vg.text(label, x + 6.0f, y, 9.5f, theme.accentBright());
    }

    private void layoutButtons(NVGRenderer vg, Theme theme, ConfigStore.Slot slot, float rowRight, float rowY, float mx, float my) {
        record Spec(Action action, String label, boolean primary, boolean danger) {
        }
        ArrayList<Spec> specs = new ArrayList<Spec>();
        if (slot.filled()) {
            specs.add(new Spec(Action.ACTIVATE, Deobf.decrypt("2O<\u0007d\u00a5\u00b8\u008f"), true, false));
            specs.add(new Spec(Action.SAVE, Deobf.decrypt(" M>\u000b"), false, false));
            specs.add(new Spec(Action.RENAME, Deobf.decrypt("!I&\u000f\u007f\u00a1"), false, false));
            specs.add(new Spec(Action.EXPORT, Deobf.decrypt("6T8\u0001`\u00b0"), false, false));
            specs.add(new Spec(Action.IMPORT, Deobf.decrypt(":A8\u0001`\u00b0"), false, false));
            specs.add(new Spec(Action.DELETE, Deobf.decrypt("7I$\u000bf\u00a1"), false, true));
        } else {
            specs.add(new Spec(Action.SAVE, Deobf.decrypt(" M>\u000b"), true, false));
            specs.add(new Spec(Action.IMPORT, Deobf.decrypt(":A8\u0001`\u00b0"), false, false));
        }
        float h = 26.0f;
        float padX = 11.0f;
        float gap = 6.0f;
        float font = 12.0f;
        float total = 0.0f;
        float[] widths = new float[specs.size()];
        for (int i = 0; i < specs.size(); ++i) {
            widths[i] = vg.textWidth(((Spec)specs.get(i)).label(), font) + padX * 2.0f;
            total += widths[i] + (i > 0 ? gap : 0.0f);
        }
        float bx = rowRight - total;
        float by = rowY + (60.0f - h) / 2.0f;
        for (int i = 0; i < specs.size(); ++i) {
            Spec spec = (Spec)specs.get(i);
            float bw = widths[i];
            boolean hover = mx >= bx && mx <= bx + bw && my >= by && my <= by + h;
            this.drawButton(vg, theme, bx, by, bw, h, spec.label(), font, spec.primary(), hover, spec.danger());
            this.hits.add(new Hit(spec.action(), slot.index(), bx, by, bw, h, spec.primary()));
            bx += bw + gap;
        }
    }

    private void drawButton(NVGRenderer vg, Theme theme, float x, float y, float w, float h, String label, float font, boolean primary, boolean hover, boolean danger) {
        int accentBright;
        int accent = danger ? -45730 : theme.accent();
        int n = accentBright = danger ? -37252 : theme.accentBright();
        if (primary) {
            int top = hover ? Colors.lighten(accentBright, 0.1f) : accentBright;
            vg.rectGradient(x, y, w, h, h / 2.0f, top, accent, true);
            if (hover) {
                vg.glow(x, y, w, h, h / 2.0f, 5.0f, Colors.withAlpha(accent, 0.35f));
            }
            vg.text(label, x + (w - vg.textWidth(label, font)) / 2.0f, y + h / 2.0f, font, -15593706);
        } else {
            vg.rect(x, y, w, h, h / 2.0f, Colors.withAlpha(-1, hover ? 0.12f : 0.06f));
            vg.rectOutline(x, y, w, h, h / 2.0f, 1.0f, Colors.withAlpha(hover ? accentBright : accent, hover ? 0.7f : 0.28f));
            int rest = danger ? Colors.withAlpha(accent, 0.85f) : theme.textMuted();
            vg.text(label, x + (w - vg.textWidth(label, font)) / 2.0f, y + h / 2.0f, font, hover ? (danger ? accentBright : theme.textPrimary()) : rest);
        }
    }

    private void renderFooter(NVGRenderer vg, Theme theme) {
        float y = this.cardY + this.cardH - 19.0f;
        vg.text(Deobf.decrypt("6T8\u0001`\u00b0\u00ec\u0089\u00a6\u010b\u0100\u0106\u0130\u01d0\u01e7\u01db\u01be\u0202\u021e\u0240\u022e\u02d9\u0296\u02ce\u02b2\u030e\u031b\u0313\u0340\u03c2\u03f1\u03d6\u03ff\u039d\u044e\u046e\u0409\u0464\u04c5\u04b2\u04a7\u0436\u0505\u054d\u0555\u055c\u05cb\u05f4\u05c7\u05ee\u061b\u0622\u064b\u0640\u0697\u06ea\u069a\u06d1\u0719"), this.cardX + 18.0f, y, 11.0f, theme.textMuted());
        String ver = Deobf.decrypt("\u0005\u001d");
        vg.text(ver, this.cardX + 600.0f - 18.0f - vg.textWidth(ver, 11.0f), y, 11.0f, theme.textDisabled());
    }

    private void renderConfirm(NVGRenderer vg, Theme theme, float mx, float my, float uiWidth, float uiHeight) {
        vg.rect(this.cardX, this.cardY, 600.0f, this.cardH, 18.0f, Colors.withAlpha(-16316918, 0.55f));
        this.confirmCardW = 380.0f;
        this.confirmCardH = 148.0f;
        this.confirmCardX = (uiWidth - this.confirmCardW) / 2.0f;
        this.confirmCardY = (uiHeight - this.confirmCardH) / 2.0f;
        boolean danger = this.pendingConfirm.action() == Action.DELETE;
        vg.glow(this.confirmCardX, this.confirmCardY, this.confirmCardW, this.confirmCardH, 16.0f, 20.0f, Colors.withAlpha(-16777216, 0.5f));
        vg.rectVaryingGradient(this.confirmCardX, this.confirmCardY, this.confirmCardW, this.confirmCardH, 16.0f, 16.0f, 16.0f, 16.0f, theme.headerTop(), theme.background());
        vg.rectOutline(this.confirmCardX, this.confirmCardY, this.confirmCardW, this.confirmCardH, 16.0f, 1.2f, Colors.withAlpha(danger ? -45730 : theme.accent(), 0.45f));
        vg.text(this.pendingConfirm.title(), this.confirmCardX + 22.0f, this.confirmCardY + 34.0f, 16.0f, theme.textPrimary());
        vg.text(this.pendingConfirm.body(), this.confirmCardX + 22.0f, this.confirmCardY + 58.0f, 12.0f, theme.textMuted());
        float h = 30.0f;
        float gap = 10.0f;
        float by = this.confirmCardY + this.confirmCardH - h - 20.0f;
        this.confirmOkW = 118.0f;
        this.confirmCancelW = 92.0f;
        this.confirmOkH = h;
        this.confirmCancelH = h;
        this.confirmOkX = this.confirmCardX + this.confirmCardW - 22.0f - this.confirmOkW;
        this.confirmOkY = by;
        this.confirmCancelX = this.confirmOkX - gap - this.confirmCancelW;
        this.confirmCancelY = by;
        boolean okHover = mx >= this.confirmOkX && mx <= this.confirmOkX + this.confirmOkW && my >= by && my <= by + h;
        boolean cancelHover = mx >= this.confirmCancelX && mx <= this.confirmCancelX + this.confirmCancelW && my >= by && my <= by + h;
        this.drawButton(vg, theme, this.confirmCancelX, this.confirmCancelY, this.confirmCancelW, this.confirmCancelH, Deobf.decrypt("0M&\rw\u00a8"), 12.5f, false, cancelHover, false);
        this.drawButton(vg, theme, this.confirmOkX, this.confirmOkY, this.confirmOkW, this.confirmOkH, this.confirmLabel(), 12.5f, true, okHover, danger);
    }

    private String confirmLabel() {
        return switch (this.pendingConfirm.action().ordinal()) {
            case 4 -> Deobf.decrypt(":A8\u0001`\u00b0");
            case 5 -> Deobf.decrypt("7I$\u000bf\u00a1");
            default -> Deobf.decrypt("<Z-\u001ce\u00b6\u00a5\u009e\u00ac");
        };
    }

    public boolean mouseClicked(float mx, float my, int button) {
        if (!this.open) {
            return false;
        }
        if (this.renamingSlot >= 0) {
            this.commitRename();
        }
        if (this.pendingConfirm != null) {
            if (this.hit(mx, my, this.confirmOkX, this.confirmOkY, this.confirmOkW, this.confirmOkH)) {
                this.runConfirm();
            } else if (this.hit(mx, my, this.confirmCancelX, this.confirmCancelY, this.confirmCancelW, this.confirmCancelH) || !this.hit(mx, my, this.confirmCardX, this.confirmCardY, this.confirmCardW, this.confirmCardH)) {
                this.pendingConfirm = null;
                UiSounds.select();
            }
            return true;
        }
        if (mx >= this.closeX - 5.0f && mx <= this.closeX + this.closeSize + 5.0f && my >= this.closeY - 5.0f && my <= this.closeY + this.closeSize + 5.0f) {
            this.close();
            UiSounds.guiClose();
            return true;
        }
        for (Hit h : this.hits) {
            if (!h.contains(mx, my)) continue;
            this.dispatch(h.action, h.slot);
            return true;
        }
        if (mx < this.cardX || mx > this.cardX + 600.0f || my < this.cardY || my > this.cardY + this.cardH) {
            this.close();
            UiSounds.guiClose();
        }
        return true;
    }

    public boolean keyPressed(int keyCode) {
        if (!this.open) {
            return false;
        }
        if (this.renamingSlot >= 0) {
            switch (keyCode) {
                case 257: 
                case 335: {
                    this.commitRename();
                    break;
                }
                case 256: {
                    this.cancelRename();
                    break;
                }
                case 259: {
                    if (this.renameBuffer.length() <= 0) break;
                    this.renameBuffer.deleteCharAt(this.renameBuffer.length() - 1);
                    break;
                }
            }
            return true;
        }
        if (this.pendingConfirm != null) {
            switch (keyCode) {
                case 257: 
                case 335: {
                    this.runConfirm();
                    break;
                }
                case 256: {
                    this.pendingConfirm = null;
                    UiSounds.select();
                    break;
                }
            }
            return true;
        }
        if (keyCode == 256) {
            this.close();
            UiSounds.guiClose();
            return true;
        }
        return true;
    }

    public boolean charTyped(int codepoint) {
        if (!this.open || this.renamingSlot < 0) {
            return false;
        }
        if (this.renameBuffer.length() >= 24) {
            return true;
        }
        char c = (char)codepoint;
        if (c >= ' ' && c < '\u007f') {
            this.renameBuffer.append(c);
        }
        return true;
    }

    private void dispatch(Action action, int slot) {
        ConfigStore store = this.store();
        switch (action.ordinal()) {
            case 0: {
                this.doActivate(slot);
                break;
            }
            case 2: {
                this.beginRename(slot);
                break;
            }
            case 3: {
                this.doExport(slot);
                break;
            }
            case 5: {
                this.pendingConfirm = new Confirm(Action.DELETE, slot, "Delete \"" + store.slot(slot).name() + "\"?", "This permanently removes slot " + (slot + 1) + ".", null);
                UiSounds.select();
                break;
            }
            case 1: {
                if (store.slot(slot).filled()) {
                    this.pendingConfirm = new Confirm(Action.SAVE, slot, "Overwrite \"" + store.slot(slot).name() + "\"?", "This replaces the config saved in slot " + (slot + 1) + ".", null);
                    UiSounds.select();
                    break;
                }
                this.doSave(slot);
                break;
            }
            case 4: {
                String clip = this.readClipboard();
                if (clip == null || clip.isBlank()) {
                    this.toast(Deobf.decrypt("=C<\u0006{\u00aa\u00ab\u00ca\u00a6\u0115\u0149\u0117\u012b\u0195\u01b3\u01d7\u01f2\u0212\u0201\u0257\u0233\u0298\u0287\u02c6\u02fb\u030a\u0316\u035c\u0348\u03dd\u03e5\u0399\u03ab\u03c9"));
                    UiSounds.select();
                    return;
                }
                if (store.slot(slot).filled()) {
                    this.pendingConfirm = new Confirm(Action.IMPORT, slot, "Import over \"" + store.slot(slot).name() + "\"?", "This replaces slot " + (slot + 1) + " with the pasted config.", clip);
                    UiSounds.select();
                    break;
                }
                this.doImport(slot, clip);
            }
        }
    }

    private void runConfirm() {
        Confirm c = this.pendingConfirm;
        this.pendingConfirm = null;
        if (c == null) {
            return;
        }
        if (c.action() == Action.SAVE) {
            this.doSave(c.slot());
        } else if (c.action() == Action.IMPORT) {
            this.doImport(c.slot(), c.payload());
        } else if (c.action() == Action.DELETE) {
            this.doDelete(c.slot());
        }
    }

    private void doDelete(int slot) {
        String name = this.store().slot(slot).name();
        if (this.store().delete(slot)) {
            this.toast("Deleted \"" + name + "\"");
            UiSounds.select();
        } else {
            this.toast(Deobf.decrypt("0C=\u0002v\u00aa\u00eb\u009e\u00e9\u011f\u010c\u010f\u0126\u0184\u01f6\u0194\u01ea\u0213\u0214\u0215\u023f\u0296\u029b\u02c4\u02b2\u0319"));
        }
    }

    private void doSave(int slot) {
        if (this.store().save(slot)) {
            this.toast("Saved to \"" + this.store().slot(slot).name() + "\"");
            UiSounds.toggle(true);
        } else {
            this.toast(Deobf.decrypt("0C=\u0002v\u00aa\u00eb\u009e\u00e9\u0108\u0108\u0115\u0126\u01d0\u01e7\u01dc\u01fb\u025b\u0212\u025a\u0232\u029f\u029c\u02c5"));
        }
    }

    private void doActivate(int slot) {
        if (this.store().activate(slot)) {
            this.toast("Activated \"" + this.store().slot(slot).name() + "\"");
            UiSounds.toggle(true);
        } else {
            this.toast(Deobf.decrypt("'D)\u001a2\u00b7\u00a0\u0085\u00bd\u015b\u0100\u0110\u0163\u0195\u01fe\u01c4\u01ea\u0202"));
        }
    }

    private void doExport(int slot) {
        String json = this.store().export(slot);
        if (json == null) {
            this.toast(Deobf.decrypt("'D)\u001a2\u00b7\u00a0\u0085\u00bd\u015b\u0100\u0110\u0163\u0195\u01fe\u01c4\u01ea\u0202"));
            return;
        }
        this.setClipboard(json);
        this.toast("Copied \"" + this.store().slot(slot).name() + "\" to clipboard");
        UiSounds.select();
    }

    private void doImport(int slot, String payload) {
        ConfigStore.ImportResult result = this.store().importInto(slot, payload);
        this.toast(result.message());
        UiSounds.toggle(true);
    }

    private void beginRename(int slot) {
        this.renamingSlot = slot;
        this.renameBuffer.setLength(0);
        this.renameBuffer.append(this.store().slot(slot).name());
        UiSounds.select();
    }

    private void commitRename() {
        if (this.renamingSlot < 0) {
            return;
        }
        this.store().rename(this.renamingSlot, this.renameBuffer.toString());
        this.renamingSlot = -1;
    }

    private void cancelRename() {
        this.renamingSlot = -1;
    }

    private boolean hit(float mx, float my, float x, float y, float w, float h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    private void toast(String message) {
        if (SixSevenClient.notifications() != null) {
            SixSevenClient.notifications().pushInfo(message);
        }
    }

    private String readClipboard() {
        try {
            return class_310.method_1551().field_1774.method_1460();
        }
        catch (Exception e) {
            return null;
        }
    }

    private void setClipboard(String text) {
        try {
            class_310.method_1551().field_1774.method_1455(text);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static String relativeTime(long savedAt) {
        if (savedAt <= 0L) {
            return Deobf.decrypt("\u0019Y;\u001a2\u00aa\u00a3\u009d");
        }
        long diff = System.currentTimeMillis() - savedAt;
        if (diff < 60000L) {
            return Deobf.decrypt("\u0019Y;\u001a2\u00aa\u00a3\u009d");
        }
        long minutes = diff / 60000L;
        if (minutes < 60L) {
            return minutes + "m ago";
        }
        long hours = minutes / 60L;
        if (hours < 24L) {
            return hours + "h ago";
        }
        return hours / 24L + "d ago";
    }

    public void debugBeginRename(int slot) {
        this.open();
        this.beginRename(slot);
    }

    public void debugConfirmOverwrite(int slot) {
        this.open();
        this.pendingConfirm = new Confirm(Action.SAVE, slot, "Overwrite \"" + this.store().slot(slot).name() + "\"?", "This replaces the config saved in slot " + (slot + 1) + ".", null);
    }

    public void debugConfirmDelete(int slot) {
        this.open();
        this.pendingConfirm = new Confirm(Action.DELETE, slot, "Delete \"" + this.store().slot(slot).name() + "\"?", "This permanently removes slot " + (slot + 1) + ".", null);
    }

    private record Confirm(Action action, int slot, String title, String body, String payload) {
    }

    private static enum Action {
        ACTIVATE,
        SAVE,
        RENAME,
        EXPORT,
        IMPORT,
        DELETE;

    }

    private static final class Hit {
        final Action action;
        final int slot;
        final float x;
        final float y;
        final float w;
        final float h;
        final boolean primary;

        Hit(Action action, int slot, float x, float y, float w, float h, boolean primary) {
            this.action = action;
            this.slot = slot;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.primary = primary;
        }

        boolean contains(float mx, float my) {
            return mx >= this.x && mx <= this.x + this.w && my >= this.y && my <= this.y + this.h;
        }
    }
}

