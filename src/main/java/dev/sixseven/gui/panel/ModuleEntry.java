/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.gui.panel;

import dev.sixseven.gui.ClickGuiState;
import dev.sixseven.gui.widget.BlockListWidget;
import dev.sixseven.gui.widget.BooleanWidget;
import dev.sixseven.gui.widget.ColorWidget;
import dev.sixseven.gui.widget.IconListWidget;
import dev.sixseven.gui.widget.KeybindWidget;
import dev.sixseven.gui.widget.ModeWidget;
import dev.sixseven.gui.widget.SettingWidget;
import dev.sixseven.gui.widget.SliderWidget;
import dev.sixseven.gui.widget.StringWidget;
import dev.sixseven.module.Module;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.settings.BlockListSetting;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.IconListSetting;
import dev.sixseven.settings.KeybindSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.Setting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.settings.StringSetting;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import dev.sixseven.util.UiSounds;
import java.util.ArrayList;
import java.util.List;

public class ModuleEntry {
    public static final float ROW_H = 30.0f;
    private static final float RADIUS = 8.0f;
    private static final float SETTING_INDENT = 12.0f;
    private final Module module;
    private final ThemeManager themes;
    private final ClickGuiState state;
    private final String key;
    private final List<SettingWidget> widgets = new ArrayList<SettingWidget>();
    private final Animation hover = new Animation(140.0f, 0.0f);
    private final Animation enable = new Animation(160.0f, 0.0f);
    private final Animation expand;
    private float x;
    private float y;
    private float width;

    public ModuleEntry(Module module, ThemeManager themes, ClickGuiState state) {
        this.module = module;
        this.themes = themes;
        this.state = state;
        this.key = module.getName() + "@" + module.getCategory().name();
        this.expand = new Animation(190.0f, state.isExpanded(this.key) ? 1.0f : 0.0f);
        this.enable.snapTo(module.isEnabled() ? 1.0f : 0.0f);
        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                BooleanSetting b = (BooleanSetting)setting;
                this.widgets.add(new BooleanWidget(themes, b));
                continue;
            }
            if (setting instanceof SliderSetting) {
                SliderSetting s = (SliderSetting)setting;
                this.widgets.add(new SliderWidget(themes, s));
                continue;
            }
            if (setting instanceof ModeSetting) {
                ModeSetting m = (ModeSetting)setting;
                this.widgets.add(new ModeWidget(themes, m));
                continue;
            }
            if (setting instanceof ColorSetting) {
                ColorSetting c = (ColorSetting)setting;
                this.widgets.add(new ColorWidget(themes, c));
                continue;
            }
            if (setting instanceof BlockListSetting) {
                BlockListSetting bl = (BlockListSetting)setting;
                this.widgets.add(new BlockListWidget(themes, bl));
                continue;
            }
            if (setting instanceof IconListSetting) {
                IconListSetting il = (IconListSetting)setting;
                this.widgets.add(new IconListWidget(themes, il));
                continue;
            }
            if (setting instanceof StringSetting) {
                StringSetting str = (StringSetting)setting;
                this.widgets.add(new StringWidget(themes, str));
                continue;
            }
            if (!(setting instanceof KeybindSetting)) continue;
            KeybindSetting k = (KeybindSetting)setting;
            this.widgets.add(new KeybindWidget(themes, k));
        }
        this.widgets.add(new KeybindWidget(themes, module.getKeybind()));
    }

    public Module getModule() {
        return this.module;
    }

    public void setBounds(float x, float y, float width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    private float settingsHeight(NVGRenderer vg) {
        float h = 6.0f;
        for (SettingWidget widget : this.widgets) {
            if (!widget.isVisible()) continue;
            h += widget.height(vg) + 3.0f;
        }
        return h + 3.0f;
    }

    public float height(NVGRenderer vg) {
        float t = this.expand.value();
        return 30.0f + (t <= 0.005f ? 0.0f : t * this.settingsHeight(vg));
    }

    public void render(NVGRenderer vg, float mx, float my, float fadeAlpha) {
        boolean hovered;
        Theme theme = this.theme();
        boolean bl = hovered = mx >= this.x && mx <= this.x + this.width && my >= this.y && my <= this.y + 30.0f;
        if (hovered && this.hover.getTarget() < 0.5f) {
            UiSounds.hover();
        }
        this.hover.setTarget(hovered ? 1.0f : 0.0f);
        this.enable.setTarget(this.module.isEnabled() ? 1.0f : 0.0f);
        float hoverT = this.hover.value();
        float enableT = this.enable.value();
        float expandT = this.expand.value();
        vg.save();
        vg.alpha(fadeAlpha);
        if (enableT > 0.01f) {
            vg.save();
            vg.alpha(enableT);
            vg.rect(this.x, this.y, this.width, 30.0f, 8.0f, theme.moduleActiveFill());
            vg.glow(this.x, this.y, this.width, 30.0f, 8.0f, 4.0f, Colors.withAlpha(theme.accent(), 0.16f * enableT));
            vg.restore();
        }
        if (hoverT > 0.01f) {
            vg.rect(this.x, this.y, this.width, 30.0f, 8.0f, Colors.withAlpha(theme.accent(), 0.1f * hoverT));
        }
        float textY = this.y + 15.0f;
        if (enableT > 0.01f) {
            vg.save();
            vg.alpha(enableT);
            vg.textGlow(this.module.getName(), this.x + 10.0f, textY, 14.5f, Colors.withAlpha(theme.accent(), 0.75f));
            vg.textGradient(this.module.getName(), this.x + 10.0f, textY, 14.5f, theme.accentBright(), theme.accent());
            vg.restore();
        }
        if (enableT < 0.99f) {
            vg.save();
            vg.alpha(1.0f - enableT);
            int idle = Colors.lerp(theme.textMuted(), theme.textPrimary(), hoverT);
            vg.text(this.module.getName(), this.x + 10.0f, textY, 14.5f, idle);
            vg.restore();
        }
        float dotX = this.x + this.width - 12.0f;
        int dot = Colors.lerp(theme.statusDisabled(), theme.statusEnabled(), enableT);
        if (enableT > 0.3f) {
            vg.circleGlow(dotX, textY, 3.0f, 4.0f, Colors.withAlpha(dot, 0.5f * enableT));
        }
        vg.circle(dotX, textY, 3.0f, dot);
        if (expandT > 0.005f) {
            float settingsH = this.settingsHeight(vg) * expandT;
            vg.save();
            vg.scissor(this.x, this.y + 30.0f, this.width, settingsH);
            vg.alpha(expandT);
            vg.rect(this.x + 4.0f, this.y + 30.0f - 4.0f, this.width - 8.0f, settingsH + 0.0f, 6.0f, Colors.withAlpha(-15988208, 0.55f));
            float wy = this.y + 30.0f + 6.0f;
            for (SettingWidget widget : this.widgets) {
                if (!widget.isVisible()) continue;
                widget.setBounds(this.x + 12.0f, wy, this.width - 24.0f);
                widget.render(vg, mx, my);
                wy += widget.height(vg) + 3.0f;
            }
            vg.restore();
        }
        vg.restore();
    }

    private Theme theme() {
        return this.themes.current();
    }

    public boolean mouseClicked(float mx, float my, int button) {
        if (mx >= this.x && mx <= this.x + this.width && my >= this.y && my <= this.y + 30.0f) {
            if (button == 0) {
                this.module.toggle();
                UiSounds.toggle(this.module.isEnabled());
            } else if (button == 1) {
                boolean expanded = !(this.expand.getTarget() > 0.5f);
                this.expand.setTarget(expanded ? 1.0f : 0.0f);
                this.state.setExpanded(this.key, expanded);
                UiSounds.select();
            }
            return true;
        }
        if (this.expand.getTarget() > 0.5f && my >= this.y + 30.0f && my <= this.y + this.height(null)) {
            for (SettingWidget widget : this.widgets) {
                if (!widget.isVisible() || !widget.mouseClicked(mx, my, button)) continue;
                return true;
            }
            return mx >= this.x && mx <= this.x + this.width;
        }
        return false;
    }

    public void mouseDragged(float mx, float my) {
        for (SettingWidget widget : this.widgets) {
            widget.mouseDragged(mx, my);
        }
    }

    public void mouseReleased() {
        for (SettingWidget widget : this.widgets) {
            widget.mouseReleased();
        }
    }

    public boolean keyPressed(int keyCode) {
        for (SettingWidget widget : this.widgets) {
            if (!widget.keyPressed(keyCode)) continue;
            return true;
        }
        return false;
    }

    public boolean charTyped(int codepoint) {
        for (SettingWidget widget : this.widgets) {
            if (!widget.charTyped(codepoint)) continue;
            return true;
        }
        return false;
    }

    public boolean isListening() {
        for (SettingWidget widget : this.widgets) {
            if (!widget.isListening()) continue;
            return true;
        }
        return false;
    }
}

