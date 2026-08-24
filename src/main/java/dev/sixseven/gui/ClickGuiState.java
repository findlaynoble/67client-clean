/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package dev.sixseven.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sixseven.module.Category;
import dev.sixseven.rt.Deobf;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ClickGuiState {
    public static final String THEMES_PANEL = "__themes__";
    private final Map<String, PanelState> panels = new LinkedHashMap<String, PanelState>();
    private final Set<String> expandedModules = new HashSet<String>();
    private boolean laidOut;
    private boolean customized;
    private float lastLayoutWidth = -1.0f;
    private float lastLayoutHeight = -1.0f;
    private static final int LAYOUT_VERSION = 3;
    private static final float PANEL_W = 210.0f;
    private static final float SEARCH_W = 280.0f;

    public void markCustomized() {
        this.customized = true;
    }

    public ClickGuiState() {
        float x = 16.0f;
        for (Category category : Category.values()) {
            this.panels.put(category.name(), new PanelState(x, 16.0f));
            x += 222.0f;
        }
        this.panels.put(Deobf.decrypt(",s<\u0006w\u00a9\u00a9\u0099\u0096\u0124"), new PanelState(x, 320.0f));
    }

    public void ensureDefaultLayout(float uiWidth, float uiHeight) {
        if (this.customized || this.laidOut && uiWidth == this.lastLayoutWidth && uiHeight == this.lastLayoutHeight) {
            return;
        }
        this.laidOut = true;
        this.lastLayoutWidth = uiWidth;
        this.lastLayoutHeight = uiHeight;
        String[] order = new String[]{Category.COMBAT.name(), Category.MISC.name(), Category.RENDER.name(), Category.VISUALS.name(), Category.CLIENT.name(), Deobf.decrypt(",s<\u0006w\u00a9\u00a9\u0099\u0096\u0124")};
        float needed = 1572.0f;
        if (uiWidth >= needed) {
            float gap;
            float x = gap = (uiWidth - 1260.0f - 280.0f) / 8.0f;
            for (int i = 0; i < order.length; ++i) {
                PanelState ps = this.panel(order[i]);
                ps.x = x;
                ps.y = 16.0f;
                ps.collapsed = false;
                x += 210.0f + gap;
                if (i != 2) continue;
                x += 280.0f + gap;
            }
        } else {
            float spacing = 222.0f;
            int perRow = Math.max(1, (int)((uiWidth - 24.0f) / spacing));
            for (int i = 0; i < order.length; ++i) {
                PanelState ps = this.panel(order[i]);
                ps.x = 12.0f + (float)(i % perRow) * spacing;
                ps.y = 58.0f + (float)(i / perRow) * (uiHeight * 0.44f);
                ps.collapsed = i / perRow > 0;
            }
        }
    }

    public PanelState panel(String key) {
        return this.panels.computeIfAbsent(key, k -> new PanelState(16.0f, 16.0f));
    }

    public boolean isExpanded(String moduleKey) {
        return this.expandedModules.contains(moduleKey);
    }

    public void setExpanded(String moduleKey, boolean expanded) {
        if (expanded) {
            this.expandedModules.add(moduleKey);
        } else {
            this.expandedModules.remove(moduleKey);
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty(Deobf.decrypt("\u0005"), (Number)3);
        json.addProperty(Deobf.decrypt("\u0010Y;\u001a}\u00a9"), Boolean.valueOf(this.customized));
        for (Map.Entry<String, PanelState> entry : this.panels.entrySet()) {
            JsonObject p = new JsonObject();
            p.addProperty(Deobf.decrypt("\u000b"), (Number)Float.valueOf(entry.getValue().x));
            p.addProperty(Deobf.decrypt("\n"), (Number)Float.valueOf(entry.getValue().y));
            p.addProperty(Deobf.decrypt("\u0010C$\u0002s\u00b4\u00bf\u008f\u00ad"), Boolean.valueOf(entry.getValue().collapsed));
            json.add(entry.getKey(), (JsonElement)p);
        }
        return json;
    }

    public void fromJson(JsonObject json) {
        if (!json.has(Deobf.decrypt("\u0005")) || json.get(Deobf.decrypt("\u0005")).getAsInt() < 3) {
            return;
        }
        if (!json.has(Deobf.decrypt("\u0010Y;\u001a}\u00a9")) || !json.get(Deobf.decrypt("\u0010Y;\u001a}\u00a9")).getAsBoolean()) {
            return;
        }
        this.laidOut = true;
        this.customized = true;
        for (Map.Entry<String, PanelState> entry : this.panels.entrySet()) {
            JsonObject p = json.getAsJsonObject(entry.getKey());
            if (p == null) continue;
            if (p.has(Deobf.decrypt("\u000b"))) {
                entry.getValue().x = p.get(Deobf.decrypt("\u000b")).getAsFloat();
            }
            if (p.has(Deobf.decrypt("\n"))) {
                entry.getValue().y = p.get(Deobf.decrypt("\n")).getAsFloat();
            }
            if (!p.has(Deobf.decrypt("\u0010C$\u0002s\u00b4\u00bf\u008f\u00ad"))) continue;
            entry.getValue().collapsed = p.get(Deobf.decrypt("\u0010C$\u0002s\u00b4\u00bf\u008f\u00ad")).getAsBoolean();
        }
    }

    public static class PanelState {
        public float x;
        public float y;
        public boolean collapsed;

        PanelState(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}

