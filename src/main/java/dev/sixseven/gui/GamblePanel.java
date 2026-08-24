/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_327
 *  net.minecraft.class_332
 *  net.minecraft.class_465
 *  net.minecraft.class_5250
 *  net.minecraft.class_5348
 */
package dev.sixseven.gui;

import dev.sixseven.mixin.AbstractContainerScreenAccessor;
import dev.sixseven.module.impl.GambleRiggerModule;
import dev.sixseven.rt.Deobf;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_465;
import net.minecraft.class_5250;
import net.minecraft.class_5348;

public class GamblePanel {
    private static final int PAD = 8;
    private static final int CELL = 22;
    private static final int GAP = 4;
    private static final int TITLE_H = 12;
    private static final int BTN_H = 16;
    private static final int RESET_H = 12;
    private static final int PANEL_FILL = -3750202;
    private static final int PANEL_HOVER = -2697514;
    private static final int BTN_DISABLED = -5263441;
    private static final int HL_LIGHT = -1;
    private static final int HL_DARK = -11184811;
    private static final int SLOT_FILL = -7631989;
    private static final int SLOT_DARK = -13158601;
    private static final int OUTLINE = -16777216;
    private static final int LABEL = -12566464;
    private static final int TEXT_ON_BTN = -1;
    private static final int TEXT_HOVER = -96;
    private static final int TEXT_MUTED = -6250336;
    private final class_465<?> screen;
    private final AbstractContainerScreenAccessor access;
    private final GambleRiggerModule mod;

    public GamblePanel(class_465<?> screen, GambleRiggerModule mod) {
        this.screen = screen;
        this.access = (AbstractContainerScreenAccessor)screen;
        this.mod = mod;
    }

    private int gridW() {
        return 74;
    }

    private int panelW() {
        return this.gridW() + 16;
    }

    private int panelX() {
        int left;
        int right = this.access.getLeftPos() + this.access.getImageWidth() + 6;
        if (right + this.panelW() > this.screen.field_22789 && (left = this.access.getLeftPos() - this.panelW() - 6) >= 2) {
            return left;
        }
        return right;
    }

    private int panelY() {
        return this.access.getTopPos();
    }

    private int gridTop() {
        return this.panelY() + 8 + 12 + 4;
    }

    private int gridH() {
        return 74;
    }

    private int restoreTop() {
        return this.gridTop() + this.gridH() + 6;
    }

    private int resetTop() {
        return this.restoreTop() + 16 + 3;
    }

    private int statusTop() {
        return this.resetTop() + 12 + 5;
    }

    private int panelH() {
        return this.statusTop() + 16 + 7 - this.panelY();
    }

    private int cellX(int i) {
        return this.panelX() + 8 + i % 3 * 26;
    }

    private int cellY(int i) {
        return this.gridTop() + i / 3 * 26;
    }

    private int controlX() {
        return this.panelX() + 8;
    }

    private static boolean in(double mx, double my, int x, int y, int w, int h) {
        return mx >= (double)x && mx < (double)(x + w) && my >= (double)y && my < (double)(y + h);
    }

    public void render(class_332 g, int mx, int my) {
        if (!this.mod.isEnabled()) {
            return;
        }
        class_327 font = class_310.method_1551().field_1772;
        int px = this.panelX();
        int py = this.panelY();
        int pw = this.panelW();
        int ph = this.panelH();
        g.method_25294(px - 1, py - 1, px + pw + 1, py + ph + 1, -16777216);
        GamblePanel.raised(g, px, py, pw, ph, -3750202);
        this.centered(g, font, (class_2561)class_2561.method_43470((String)Deobf.decrypt("4M%\f~\u00a1\u00ec\u00b8\u00a0\u011c\u010e\u0106\u0131")), px + pw / 2, py + 8, -12566464, false);
        boolean pickable = this.mod.canExtract();
        for (int i = 0; i < 9; ++i) {
            this.drawCell(g, font, i, mx, my, pickable);
        }
        this.drawButton(g, font, this.controlX(), this.restoreTop(), this.gridW(), 16, Deobf.decrypt("!I;\u001a}\u00b6\u00a9"), this.mod.canRestore(), mx, my);
        boolean canReset = this.mod.phase() != GambleRiggerModule.Phase.IDLE || this.mod.keepSlot() >= 0;
        this.drawButton(g, font, this.controlX(), this.resetTop(), this.gridW(), 12, Deobf.decrypt("!I;\u000bf"), canReset, mx, my);
        String status = switch (this.mod.phase()) {
            case GambleRiggerModule.Phase.EXTRACTING -> "Taking... " + this.mod.queued();
            case GambleRiggerModule.Phase.EXTRACTED -> "Kept #" + (this.mod.keepSlot() + 1);
            case GambleRiggerModule.Phase.RESTORING -> "Restoring... " + this.mod.queued();
            default -> Deobf.decrypt("#E+\u00052\u00a5\u00ec\u0099\u00a5\u0114\u011d");
        };
        this.centered(g, font, (class_2561)class_2561.method_43470((String)status), px + pw / 2, this.statusTop(), -12566464, false);
    }

    private void drawCell(class_332 g, class_327 font, int i, int mx, int my, boolean pickable) {
        int x = this.cellX(i);
        int y = this.cellY(i);
        boolean selected = this.mod.keepSlot() == i && this.mod.phase() != GambleRiggerModule.Phase.IDLE;
        boolean hovered = pickable && GamblePanel.in(mx, my, x, y, 22, 22);
        class_5250 num = class_2561.method_43470((String)Integer.toString(i + 1));
        int tx = x + 11;
        int ty = y + 7;
        if (selected) {
            GamblePanel.sunken(g, x, y, 22, 22, -7631989);
            this.centered(g, font, (class_2561)num, tx, ty, -1, true);
        } else {
            GamblePanel.raised(g, x, y, 22, 22, hovered ? -2697514 : -3750202);
            int text = !pickable ? -6250336 : (hovered ? -96 : -1);
            this.centered(g, font, (class_2561)num, tx, ty, text, true);
        }
    }

    private void drawButton(class_332 g, class_327 font, int x, int y, int w, int h, String label, boolean enabled, int mx, int my) {
        boolean hovered;
        boolean bl = hovered = enabled && GamblePanel.in(mx, my, x, y, w, h);
        int fill = !enabled ? -5263441 : (hovered ? -2697514 : -3750202);
        GamblePanel.raised(g, x, y, w, h, fill);
        int text = !enabled ? -6250336 : (hovered ? -96 : -1);
        this.centered(g, font, (class_2561)class_2561.method_43470((String)label), x + w / 2, y + (h - 8) / 2, text, true);
    }

    private static void raised(class_332 g, int x, int y, int w, int h, int fill) {
        g.method_25294(x, y, x + w, y + h, fill);
        g.method_25294(x, y, x + w, y + 1, -1);
        g.method_25294(x, y, x + 1, y + h, -1);
        g.method_25294(x, y + h - 1, x + w, y + h, -11184811);
        g.method_25294(x + w - 1, y, x + w, y + h, -11184811);
    }

    private static void sunken(class_332 g, int x, int y, int w, int h, int fill) {
        g.method_25294(x, y, x + w, y + h, fill);
        g.method_25294(x, y, x + w, y + 1, -13158601);
        g.method_25294(x, y, x + 1, y + h, -13158601);
        g.method_25294(x, y + h - 1, x + w, y + h, -1);
        g.method_25294(x + w - 1, y, x + w, y + h, -1);
    }

    private void centered(class_332 g, class_327 font, class_2561 text, int cx, int y, int color, boolean shadow) {
        g.method_51439(font, text, cx - font.method_27525((class_5348)text) / 2, y, color, shadow);
    }

    public boolean handleClick(double mx, double my, int button) {
        int py;
        if (!this.mod.isEnabled()) {
            return false;
        }
        int px = this.panelX();
        if (!GamblePanel.in(mx, my, px, py = this.panelY(), this.panelW(), this.panelH())) {
            return false;
        }
        if (button == 0) {
            if (this.mod.canExtract()) {
                for (int i = 0; i < 9; ++i) {
                    if (!GamblePanel.in(mx, my, this.cellX(i), this.cellY(i), 22, 22)) continue;
                    this.mod.requestKeep(i);
                    return true;
                }
            }
            if (this.mod.canRestore() && GamblePanel.in(mx, my, this.controlX(), this.restoreTop(), this.gridW(), 16)) {
                this.mod.requestRestore();
                return true;
            }
            if (GamblePanel.in(mx, my, this.controlX(), this.resetTop(), this.gridW(), 12)) {
                this.mod.reset();
                return true;
            }
        }
        return true;
    }
}

