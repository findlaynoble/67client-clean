/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_310
 *  net.minecraft.class_640
 *  net.minecraft.class_746
 */
package dev.sixseven.hud;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sixseven.hud.HudComponent;
import dev.sixseven.hud.components.ArmorHud;
import dev.sixseven.hud.components.ArrayListHud;
import dev.sixseven.hud.components.InfoHud;
import dev.sixseven.hud.components.KeystrokesHud;
import dev.sixseven.hud.components.PotionsHud;
import dev.sixseven.hud.components.RadarHud;
import dev.sixseven.hud.components.RegionMapHud;
import dev.sixseven.hud.components.SpotifyHud;
import dev.sixseven.hud.components.StaffListHud;
import dev.sixseven.hud.components.WatermarkHud;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.Modules;
import dev.sixseven.notification.NotificationManager;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.spotify.SpotifyService;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.CpsTracker;
import dev.sixseven.util.TpsTracker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_310;
import net.minecraft.class_640;
import net.minecraft.class_746;

public class HudManager {
    private final List<HudComponent> components = new ArrayList<HudComponent>();

    public HudManager(ModuleManager modules, ThemeManager themes, SpotifyService spotify, NotificationManager notifications) {
        Modules.HudModule hud = modules.hud;
        this.components.add(new WatermarkHud(themes, () -> hud.isEnabled() && (Boolean)hud.watermark.get() != false));
        this.components.add(new ArrayListHud(modules, hud, themes, () -> hud.isEnabled() && (Boolean)hud.arrayList.get() != false));
        this.components.add(new InfoHud(Deobf.decrypt("\u0015\\;"), themes, Deobf.decrypt("5|\u001b"), () -> Integer.toString(class_310.method_1551().method_47599()), 0.006f, 0.985f, () -> hud.isEnabled() && (Boolean)hud.fps.get() != false));
        this.components.add(new InfoHud(Deobf.decrypt("\u0003E&\t"), themes, Deobf.decrypt("#E&\t"), HudManager::pingString, 0.055f, 0.985f, () -> hud.isEnabled() && (Boolean)hud.ping.get() != false));
        this.components.add(new InfoHud(Deobf.decrypt("\u0010C'\u001cv\u00b7"), themes, Deobf.decrypt("+u\u0012"), HudManager::coordsString, 0.115f, 0.985f, () -> hud.isEnabled() && (Boolean)hud.coordinates.get() != false));
        this.components.add(new InfoHud(Deobf.decrypt("\u0017E:\u000bq\u00b0\u00a5\u0085\u00a7"), themes, Deobf.decrypt("5M+\u0007|\u00a3"), HudManager::directionString, 0.24f, 0.985f, () -> hud.isEnabled() && (Boolean)hud.direction.get() != false));
        this.components.add(new InfoHud(Deobf.decrypt("\u0007\\;"), themes, Deobf.decrypt("'|\u001b"), () -> String.format(Deobf.decrypt("V\u0002y\b"), Float.valueOf(TpsTracker.get())), 0.33f, 0.985f, () -> hud.isEnabled() && (Boolean)hud.tps.get() != false));
        this.components.add(new InfoHud(Deobf.decrypt("\u0010\\;"), themes, Deobf.decrypt("0|\u001b"), () -> CpsTracker.get(0) + " | " + CpsTracker.get(1), 0.4f, 0.985f, () -> hud.isEnabled() && (Boolean)hud.cps.get() != false));
        this.components.add(new ArmorHud(themes, () -> hud.isEnabled() && (Boolean)hud.armor.get() != false));
        this.components.add(new PotionsHud(themes, () -> hud.isEnabled() && (Boolean)hud.potions.get() != false));
        this.components.add(new KeystrokesHud(themes, () -> hud.isEnabled() && (Boolean)hud.keystrokes.get() != false));
        this.components.add(new RadarHud(hud, modules.susChunkFinder, themes, () -> hud.isEnabled() && (Boolean)hud.radar.get() != false));
        this.components.add(new RegionMapHud(modules.regionMap, themes));
        this.components.add(new StaffListHud(modules.staffList, themes));
        this.components.add(new SpotifyHud(modules.spotify, spotify, themes));
        this.components.add(notifications);
    }

    private static String coordsString() {
        class_746 player = class_310.method_1551().field_1724;
        if (player == null) {
            return Deobf.decrypt("C\u0000h^>\u00e4\u00fc");
        }
        class_2338 pos = player.method_24515();
        return pos.method_10263() + ", " + pos.method_10264() + ", " + pos.method_10260();
    }

    private static String pingString() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.method_1562() == null) {
            return Deobf.decrypt("CA;");
        }
        class_640 info = mc.method_1562().method_2871(mc.field_1724.method_5667());
        return info == null ? Deobf.decrypt("CA;") : info.method_2959() + "ms";
    }

    private static String directionString() {
        class_746 player = class_310.method_1551().field_1724;
        if (player == null) {
            return Deobf.decrypt("=");
        }
        class_2350 dir = player.method_5735();
        return switch (dir) {
            case class_2350.field_11043 -> Deobf.decrypt("=\fhCH");
            case class_2350.field_11035 -> Deobf.decrypt(" \fhEH");
            case class_2350.field_11039 -> Deobf.decrypt("$\fhCJ");
            case class_2350.field_11034 -> Deobf.decrypt("6\fhEJ");
            default -> dir.method_10151().toUpperCase();
        };
    }

    public List<HudComponent> getComponents() {
        return this.components;
    }

    public List<Placement> layout(NVGRenderer vg, float uiWidth, float uiHeight, boolean includeHidden) {
        ArrayList<Placement> placements = new ArrayList<Placement>();
        for (HudComponent component : this.components) {
            if (!includeHidden && !component.visible()) continue;
            float scale = component.getScale();
            float w = component.measureWidth(vg) * scale;
            float h = component.measureHeight(vg) * scale;
            float x = component.getFx() * (uiWidth - w);
            float y = component.getFy() * (uiHeight - h);
            placements.add(new Placement(component, x, y, w, h));
        }
        return placements;
    }

    public void render(NVGRenderer vg, float uiWidth, float uiHeight) {
        for (Placement p : this.layout(vg, uiWidth, uiHeight, false)) {
            this.renderPlacement(vg, p);
        }
    }

    public void renderPlacement(NVGRenderer vg, Placement p) {
        float scale = p.component().getScale();
        vg.save();
        vg.translate(Math.round(p.x()), Math.round(p.y()));
        vg.scale(scale);
        p.component().render(vg, 0.0f, 0.0f, p.w() / scale, p.h() / scale);
        vg.restore();
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        for (HudComponent component : this.components) {
            JsonObject entry = new JsonObject();
            entry.addProperty(Deobf.decrypt("\u0015T"), (Number)Float.valueOf(component.getFx()));
            entry.addProperty(Deobf.decrypt("\u0015U"), (Number)Float.valueOf(component.getFy()));
            entry.addProperty(Deobf.decrypt("\u0000O)\u0002w"), (Number)Float.valueOf(component.getScale()));
            json.add(component.getId(), (JsonElement)entry);
        }
        return json;
    }

    public void fromJson(JsonObject json) {
        for (HudComponent component : this.components) {
            JsonObject entry = json.getAsJsonObject(component.getId());
            if (entry == null || !entry.has(Deobf.decrypt("\u0015T")) || !entry.has(Deobf.decrypt("\u0015U"))) continue;
            component.setPosition(entry.get(Deobf.decrypt("\u0015T")).getAsFloat(), entry.get(Deobf.decrypt("\u0015U")).getAsFloat());
            if (!entry.has(Deobf.decrypt("\u0000O)\u0002w"))) continue;
            component.setScale(entry.get(Deobf.decrypt("\u0000O)\u0002w")).getAsFloat());
        }
    }

    public record Placement(HudComponent component, float x, float y, float w, float h) {
        public boolean contains(float px, float py) {
            return px >= this.x && px <= this.x + this.w && py >= this.y && py <= this.y + this.h;
        }
    }
}

