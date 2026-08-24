/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_437
 */
package dev.sixseven.gui.widget;

import dev.sixseven.gui.ClickGuiScreen;
import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BlockListSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;
import net.minecraft.class_310;
import net.minecraft.class_437;

public class BlockListWidget
extends SettingWidget {
    private static final float ROW = 26.0f;
    private final BlockListSetting setting;

    public BlockListWidget(ThemeManager themes, BlockListSetting setting) {
        super(themes, setting);
        this.setting = setting;
    }

    @Override
    public float height(NVGRenderer vg) {
        return 26.0f;
    }

    @Override
    public void render(NVGRenderer vg, float mx, float my) {
        Theme theme = this.theme();
        float cy = this.y + 13.0f;
        vg.text(this.setting.getName(), this.x, cy, 12.5f, theme.textMuted());
        float bw = 88.0f;
        float bh = 18.0f;
        float bx = this.x + this.width - bw;
        float by = cy - bh / 2.0f;
        boolean hovered = mx >= bx && mx <= bx + bw && my >= by && my <= by + bh;
        vg.rectGradient(bx, by, bw, bh, bh / 2.0f, theme.headerTop(), theme.headerBottom(), true);
        vg.rectOutline(bx, by, bw, bh, bh / 2.0f, 1.1f, Colors.withAlpha(hovered ? theme.accentBright() : theme.accent(), hovered ? 0.9f : 0.4f));
        String label = Deobf.decrypt("#E+\u00052\u0086\u00a0\u0085\u00aa\u0110");
        float tw = vg.textWidth(label, 12.0f);
        vg.textGradient(label, bx + (bw - tw) / 2.0f, by + bh / 2.0f, 12.0f, theme.accentBright(), theme.accent());
        String count = this.setting.enabledCount() + "/" + this.setting.size();
        vg.text(count, bx - 8.0f - vg.textWidth(count, 11.5f), cy, 11.5f, theme.textDisabled());
    }

    @Override
    public boolean mouseClicked(float mx, float my, int button) {
        if (button != 0 || !this.contains(mx, my)) {
            return false;
        }
        class_437 class_4372 = class_310.method_1551().field_1755;
        if (class_4372 instanceof ClickGuiScreen) {
            ClickGuiScreen gui = (ClickGuiScreen)class_4372;
            gui.openBlockPicker(this.setting);
            UiSounds.select();
            return true;
        }
        return false;
    }
}

