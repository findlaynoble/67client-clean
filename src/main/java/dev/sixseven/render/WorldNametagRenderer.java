/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1309
 *  net.minecraft.class_1542
 *  net.minecraft.class_1799
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_2586
 *  net.minecraft.class_2636
 *  net.minecraft.class_310
 *  net.minecraft.class_332
 *  net.minecraft.class_3532
 *  net.minecraft.class_638
 *  net.minecraft.class_742
 *  net.minecraft.class_746
 *  net.minecraft.class_8961
 *  org.joml.Matrix3x2fStack
 */
package dev.sixseven.render;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.Modules;
import dev.sixseven.module.impl.NameProtectModule;
import dev.sixseven.module.impl.NameTagsModule;
import dev.sixseven.module.impl.SpawnerNametagsModule;
import dev.sixseven.render.OverlayRenderer;
import dev.sixseven.render.WorldProjection;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.util.Colors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1542;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2636;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3532;
import net.minecraft.class_638;
import net.minecraft.class_742;
import net.minecraft.class_746;
import net.minecraft.class_8961;
import org.joml.Matrix3x2fStack;

public final class WorldNametagRenderer {
    private static final int SPAWNER_ACCENT = -22733;
    private static final int MAX_TAGS = 80;

    private WorldNametagRenderer() {
    }

    public static void render(NVGRenderer vg) {
        boolean doSpawners;
        if (!WorldProjection.isValid()) {
            return;
        }
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return;
        }
        class_310 mc = class_310.method_1551();
        class_638 level = mc.field_1687;
        class_746 self = mc.field_1724;
        if (level == null || self == null) {
            return;
        }
        NameTagsModule names = modules.nameTags;
        SpawnerNametagsModule spawners = modules.spawnerNametags;
        boolean doNames = names != null && names.isEnabled();
        boolean bl = doSpawners = spawners != null && spawners.isEnabled() && (Boolean)spawners.nametag.get() != false;
        if (!doNames && !doSpawners) {
            return;
        }
        float pt = WorldProjection.partialTick();
        Theme theme = SixSevenClient.themes().current();
        Modules.HudModule hud = modules.hud;
        int accent = hud != null && (Boolean)hud.themeSync.get() == false ? ((Integer)hud.listColor.get()).intValue() : theme.accent();
        ArrayList<Tag> tags = new ArrayList<Tag>();
        if (doNames) {
            double range = (Double)names.range.get();
            double rangeSq = range * range;
            float nameScale = names.scale.getFloat();
            float nameOpacity = (float)((Double)names.opacity.get() / 100.0);
            if (((Boolean)names.players.get()).booleanValue()) {
                NameProtectModule protect = modules.nameProtect;
                boolean protecting = protect != null && protect.isEnabled();
                boolean showSelf = (Boolean)names.self.get() != false && !mc.field_1690.method_31044().method_31034();
                for (class_742 player : level.method_18456()) {
                    String suffix;
                    float max;
                    String replaced;
                    boolean isSelf;
                    boolean bl2 = isSelf = player == self;
                    if (isSelf && !showSelf || player.method_7325() || !player.method_5805()) continue;
                    double distSq = self.method_5858((class_1297)player);
                    if (!isSelf && distSq > rangeSq) continue;
                    String shown = player.method_7334().name();
                    if (protecting && (replaced = protect.replacementForDisplay(shown)) != null) {
                        shown = replaced;
                    }
                    float healthFrac = -1.0f;
                    if (((Boolean)names.health.get()).booleanValue() && (max = player.method_6063()) > 0.0f) {
                        healthFrac = class_3532.method_15363((float)(player.method_6032() / max), (float)0.0f, (float)1.0f);
                    }
                    String string = suffix = !isSelf && (Boolean)names.distance.get() != false ? (int)Math.sqrt(distSq) + "m" : null;
                    if (shown.isEmpty() && suffix == null && healthFrac < 0.0f) continue;
                    tags.add(WorldNametagRenderer.entityTag((class_1297)player, pt, Math.sqrt(distSq), shown, suffix, accent, healthFrac, nameScale, nameOpacity));
                }
            }
            if (((Boolean)names.items.get()).booleanValue()) {
                for (class_1297 entity : level.method_18112()) {
                    class_1799 stack;
                    double distSq;
                    class_1542 item;
                    if (!(entity instanceof class_1542) || !(item = (class_1542)entity).method_5805() || (distSq = self.method_5858((class_1297)item)) > rangeSq || (stack = item.method_6983()).method_7960()) continue;
                    String suffix = WorldNametagRenderer.itemSuffix(stack.method_7947(), (Boolean)names.itemAmount.get(), (Boolean)names.distance.get() != false ? (int)Math.sqrt(distSq) + "m" : null);
                    tags.add(WorldNametagRenderer.entityTag((class_1297)item, pt, Math.sqrt(distSq), stack.method_7964().getString(), suffix, accent, -1.0f, nameScale, nameOpacity));
                }
            }
        }
        if (doSpawners) {
            boolean showDist = (Boolean)spawners.distance.get();
            float spawnerOpacity = (float)((Double)spawners.opacity.get() / 100.0);
            for (class_2338 pos : spawners.scan.get()) {
                double cx = (double)pos.method_10263() + 0.5;
                double cy = (double)pos.method_10264() + 1.35;
                double cz = (double)pos.method_10260() + 0.5;
                double distSq = self.method_5649(cx, (double)pos.method_10264() + 0.5, cz);
                String suffix = showDist ? (int)Math.sqrt(distSq) + "m" : null;
                tags.add(new Tag(Math.sqrt(distSq), cx, cy, cz, WorldNametagRenderer.spawnerName(level, pos), suffix, -22733, -1.0f, 1.0f, spawnerOpacity));
            }
        }
        if (tags.isEmpty()) {
            return;
        }
        tags.sort(Comparator.comparingDouble(Tag::dist));
        int count = Math.min(tags.size(), 80);
        for (int i = count - 1; i >= 0; --i) {
            Tag tag = (Tag)tags.get(i);
            float[] screen = WorldProjection.project(tag.wx, tag.wy, tag.wz);
            if (screen == null) continue;
            WorldNametagRenderer.drawTag(vg, theme, screen[0], screen[1], tag);
        }
    }

    private static Tag entityTag(class_1297 e, float pt, double dist, String name, String suffix, int accent, float healthFrac, float scale, float opacity) {
        double x = class_3532.method_16436((double)pt, (double)e.field_6038, (double)e.method_23317());
        double y = class_3532.method_16436((double)pt, (double)e.field_5971, (double)e.method_23318()) + (double)e.method_17682() + 0.5;
        double z = class_3532.method_16436((double)pt, (double)e.field_5989, (double)e.method_23321());
        return new Tag(dist, x, y, z, name, suffix, accent, healthFrac, scale, opacity);
    }

    private static String itemSuffix(int amount, boolean showAmount, String distance) {
        StringBuilder sb = new StringBuilder();
        if (showAmount && amount > 1) {
            sb.append('x').append(amount);
        }
        if (distance != null) {
            if (sb.length() > 0) {
                sb.append(Deobf.decrypt("S\f"));
            }
            sb.append(distance);
        }
        return sb.length() == 0 ? null : sb.toString();
    }

    private static String spawnerName(class_638 level, class_2338 pos) {
        try {
            class_2586 be = level.method_8321(pos);
            if (be instanceof class_2636) {
                class_2636 spawner = (class_2636)be;
                class_1297 display = spawner.method_11390().method_8283((class_1937)level, pos);
                return display != null ? display.method_5864().method_5897().getString() + " Spawner" : Deobf.decrypt(" \\)\u0019|\u00a1\u00be");
            }
            if (be instanceof class_8961) {
                class_8961 trial = (class_8961)be;
                class_1297 display = trial.method_55150().method_55174().method_55190(trial.method_55150(), (class_1937)level, trial.method_55151());
                return display != null ? "Trial: " + display.method_5864().method_5897().getString() : Deobf.decrypt("'^!\u000f~\u00e4\u009f\u009a\u00a8\u010c\u0107\u0106\u0131");
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return Deobf.decrypt(" \\)\u0019|\u00a1\u00be");
    }

    private static float pillHeight(float s, boolean hasHealth) {
        float textRowH = 12.5f * s + 3.5f * s * 2.0f;
        return textRowH + (hasHealth ? 3.0f * s + 3.5f * s : 0.0f);
    }

    public static void renderEquipment(class_332 gg) {
        if (!WorldProjection.isValid()) {
            return;
        }
        ModuleManager modules = SixSevenClient.modules();
        if (modules == null) {
            return;
        }
        NameTagsModule names = modules.nameTags;
        if (names == null || !names.isEnabled() || !((Boolean)names.players.get()).booleanValue()) {
            return;
        }
        boolean showArmor = (Boolean)names.armor.get();
        boolean showHand = (Boolean)names.heldItem.get();
        if (!showArmor && !showHand) {
            return;
        }
        class_310 mc = class_310.method_1551();
        if (mc.field_1755 != null || mc.field_1690.field_1842) {
            return;
        }
        class_638 level = mc.field_1687;
        class_746 self = mc.field_1724;
        if (level == null || self == null) {
            return;
        }
        float pt = WorldProjection.partialTick();
        double range = (Double)names.range.get();
        double rangeSq = range * range;
        float s = names.scale.getFloat();
        float pillH = WorldNametagRenderer.pillHeight(s, (Boolean)names.health.get());
        float uiScale = OverlayRenderer.uiScale();
        double guiScale = mc.method_22683().method_4495();
        boolean showSelf = (Boolean)names.self.get() != false && !mc.field_1690.method_31044().method_31034();
        ArrayList<class_1799> row = new ArrayList<class_1799>();
        for (class_742 player : level.method_18456()) {
            double wz;
            double wy;
            double wx;
            float[] px;
            boolean isSelf;
            boolean bl = isSelf = player == self;
            if (isSelf && !showSelf || player.method_7325() || !player.method_5805() || !isSelf && self.method_5858((class_1297)player) > rangeSq) continue;
            row.clear();
            if (showArmor) {
                WorldNametagRenderer.addItem(row, player.method_6118(class_1304.field_6169));
                WorldNametagRenderer.addItem(row, player.method_6118(class_1304.field_6174));
                WorldNametagRenderer.addItem(row, player.method_6118(class_1304.field_6172));
                WorldNametagRenderer.addItem(row, player.method_6118(class_1304.field_6166));
            }
            if (showHand) {
                WorldNametagRenderer.addItem(row, player.method_6047());
                WorldNametagRenderer.addItem(row, player.method_6118(class_1304.field_6171));
            }
            if (row.isEmpty() || (px = WorldProjection.projectRaw(wx = class_3532.method_16436((double)pt, (double)player.field_6038, (double)player.method_23317()), wy = class_3532.method_16436((double)pt, (double)player.field_5971, (double)player.method_23318()) + (double)player.method_17682() + 0.5, wz = class_3532.method_16436((double)pt, (double)player.field_5989, (double)player.method_23321()))) == null) continue;
            float guiX = (float)((double)px[0] / guiScale);
            float rowBottomY = (float)((double)(px[1] - pillH * uiScale) / guiScale) - 3.0f;
            float icon = 11.0f * s;
            float step = icon + 1.5f;
            float totalW = (float)row.size() * icon + (float)(row.size() - 1) * 1.5f;
            float startX = guiX - totalW / 2.0f;
            float rowTopY = rowBottomY - icon;
            Matrix3x2fStack pose = gg.method_51448();
            for (int i = 0; i < row.size(); ++i) {
                class_1799 stack = (class_1799)row.get(i);
                pose.pushMatrix();
                pose.translate(startX + (float)i * step, rowTopY);
                pose.scale(icon / 16.0f, icon / 16.0f);
                gg.method_51423((class_1309)player, stack, 0, 0, 0);
                gg.method_51431(mc.field_1772, stack, 0, 0);
                pose.popMatrix();
            }
        }
    }

    private static void addItem(List<class_1799> list, class_1799 stack) {
        if (stack != null && !stack.method_7960()) {
            list.add(stack);
        }
    }

    private static void drawTag(NVGRenderer vg, Theme theme, float uiX, float uiY, Tag tag) {
        boolean fade;
        float s = tag.scale;
        int accentBright = Colors.lighten(tag.accent, 0.35f);
        float fontMain = 12.5f * s;
        float fontSub = 10.0f * s;
        float padX = 6.0f * s;
        float padY = 3.5f * s;
        float gap = 5.0f * s;
        boolean hasHealth = tag.healthFrac >= 0.0f;
        float barH = 3.0f * s;
        float nameW = vg.textWidth(tag.name, fontMain);
        float suffixW = tag.suffix != null ? gap + vg.textWidth(tag.suffix, fontSub) : 0.0f;
        float w = nameW + suffixW + padX * 2.0f;
        float textRowH = fontMain + padY * 2.0f;
        float h = textRowH + (hasHealth ? barH + padY : 0.0f);
        float x = uiX - w / 2.0f;
        float y = uiY - h;
        float radius = Math.min(6.0f * s, h / 2.0f);
        boolean bl = fade = tag.opacity < 0.999f;
        if (fade) {
            vg.save();
            vg.alpha(tag.opacity);
        }
        vg.glow(x, y, w, h, radius, 4.0f, Colors.withAlpha(tag.accent, 0.12f));
        vg.rectGradient(x, y, w, h, radius, theme.background(), theme.backgroundTo(), true);
        float cy = y + padY + fontMain / 2.0f;
        float tx = x + padX;
        tx += vg.textGradient(tag.name, tx, cy, fontMain, accentBright, tag.accent);
        if (tag.suffix != null) {
            vg.text(tag.suffix, tx += gap, cy, fontSub, theme.textMuted());
        }
        if (hasHealth) {
            float barY = y + textRowH;
            float barX = x + padX;
            float barW = w - padX * 2.0f;
            vg.rect(barX, barY, barW, barH, barH / 2.0f, Colors.withAlpha(-16777216, 0.55f));
            int hpColor = Colors.lerp(-2080450, -11671924, tag.healthFrac);
            vg.rect(barX, barY, Math.max(barH, barW * tag.healthFrac), barH, barH / 2.0f, hpColor);
        }
        if (fade) {
            vg.restore();
        }
    }

    private record Tag(double dist, double wx, double wy, double wz, String name, String suffix, int accent, float healthFrac, float scale, float opacity) {
    }
}

