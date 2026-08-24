/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.hud.components;

import dev.sixseven.hud.HudComponent;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.Modules;
import dev.sixseven.render.anim.Animation;
import dev.sixseven.render.anim.Easing;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.Colors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class ArrayListHud
extends HudComponent {
    private static final float ENTRY_HEIGHT = 20.0f;
    private static final float FONT_SIZE = 13.5f;
    private static final float PAD_X = 8.0f;
    private static final float STRIP_W = 2.5f;
    private final ModuleManager modules;
    private final Modules.HudModule hudModule;
    private final ThemeManager themes;
    private final Map<Module, Animation> slide = new HashMap<Module, Animation>();

    public ArrayListHud(ModuleManager modules, Modules.HudModule hudModule, ThemeManager themes, BooleanSupplier visible) {
        super(Deobf.decrypt("\u0012^:\u000fk\u00a8\u00a5\u0099\u00bd"), 1.0f, 0.008f, visible);
        this.modules = modules;
        this.hudModule = hudModule;
        this.themes = themes;
    }

    private List<Module> animatedEntries(NVGRenderer vg) {
        ArrayList<Module> list = new ArrayList<Module>();
        for (Module module : this.modules.all()) {
            if (module.getCategory() == Category.CLIENT) continue;
            Animation anim = this.slide.computeIfAbsent(module, m -> new Animation(240.0f, m.isEnabled() ? 1.0f : 0.0f, Easing.EASE_OUT_CUBIC));
            anim.setTarget(module.isEnabled() ? 1.0f : 0.0f);
            if (!module.isEnabled() && !(anim.value() > 0.01f)) continue;
            list.add(module);
        }
        list.sort(Comparator.comparingDouble(m -> vg.textWidth(m.getName(), 13.5f)).reversed());
        return list;
    }

    @Override
    public float measureWidth(NVGRenderer vg) {
        float max = 40.0f;
        for (Module m : this.animatedEntries(vg)) {
            max = Math.max(max, vg.textWidth(m.getName(), 13.5f) + 16.0f + 2.5f + 2.0f);
        }
        return max;
    }

    @Override
    public float measureHeight(NVGRenderer vg) {
        float h = 0.0f;
        for (Module m : this.animatedEntries(vg)) {
            h += 20.0f * this.slide.get(m).value();
        }
        return Math.max(20.0f, h);
    }

    @Override
    public void render(NVGRenderer vg, float x, float y, float w, float h) {
        Theme theme = this.themes.current();
        boolean sync = (Boolean)this.hudModule.themeSync.get();
        boolean right = this.rightAnchored();
        float rowY = y;
        for (Module m : this.animatedEntries(vg)) {
            float t = this.slide.get(m).value();
            if (t <= 0.01f) continue;
            int accent = sync ? theme.accent() : ((Integer)this.hudModule.listColor.get()).intValue();
            int accentBright = sync ? theme.accentBright() : Colors.lighten(accent, 0.35f);
            float textW = vg.textWidth(m.getName(), 13.5f);
            float barW = textW + 16.0f + 2.5f + 2.0f;
            float slideOff = (1.0f - t) * (barW + 12.0f) * (float)(right ? 1 : -1);
            float barX = Math.round((right ? x + w - barW : x) + slideOff);
            float rowTop = Math.round(rowY);
            vg.save();
            vg.alpha(t);
            vg.rectGradient(barX, rowTop, barW, 20.0f, 5.0f, Colors.withAlpha(-15330788, 0.86f), Colors.withAlpha(-15791084, 0.86f), true);
            float stripX = right ? barX + barW - 2.5f : barX;
            vg.glow(stripX - 1.0f, rowTop + 2.0f, 4.5f, 16.0f, 2.0f, 3.0f, Colors.withAlpha(accent, 0.35f));
            vg.rect(stripX, rowTop + 2.0f, 2.5f, 16.0f, 1.25f, accent);
            float textX = Math.round(right ? barX + 8.0f : barX + 2.5f + 2.0f + 8.0f - 2.0f);
            vg.textGradient(m.getName(), textX, rowTop + 10.0f, 13.5f, accentBright, accent);
            vg.restore();
            rowY += 20.0f * t;
        }
    }
}

