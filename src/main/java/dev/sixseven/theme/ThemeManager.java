/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package dev.sixseven.theme;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.RainbowTheme;
import dev.sixseven.theme.Theme;
import java.util.ArrayList;
import java.util.List;

public class ThemeManager {
    public static final int PINK = -49508;
    private final List<Theme> themes = new ArrayList<Theme>();
    private Theme current;

    public ThemeManager() {
        this.themes.add(new Theme(Deobf.decrypt("#E&\u0005"), -49508, false));
        this.themes.add(new Theme(Deobf.decrypt("#Y:\u001e~\u00a1"), -5743361, false));
        this.themes.add(new Theme(Deobf.decrypt("1@=\u000b"), -11689985, false));
        this.themes.add(new Theme(Deobf.decrypt("!I,"), -45715, false));
        this.themes.add(new Theme(Deobf.decrypt("6A-\u001cs\u00a8\u00a8"), -12654960, false));
        this.themes.add(new RainbowTheme());
        this.current = this.themes.getFirst();
    }

    public Theme current() {
        return this.current;
    }

    public List<Theme> getThemes() {
        return this.themes;
    }

    public void select(Theme theme) {
        if (this.themes.contains(theme)) {
            this.current = theme;
        }
    }

    public Theme addCustom(int accent) {
        int n = 1;
        for (Theme t : this.themes) {
            if (!t.isCustom()) continue;
            ++n;
        }
        Theme theme = new Theme("Custom " + n, accent, true);
        this.themes.add(theme);
        return theme;
    }

    public void removeCustom(Theme theme) {
        if (theme.isCustom() && this.themes.remove(theme) && this.current == theme) {
            this.current = this.themes.getFirst();
        }
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty(Deobf.decrypt("\u0010Y:\u001cw\u00aa\u00b8"), this.current.getName());
        JsonArray customs = new JsonArray();
        for (Theme t : this.themes) {
            if (!t.isCustom()) continue;
            JsonObject entry = new JsonObject();
            entry.addProperty(Deobf.decrypt("\u001dM%\u000b"), t.getName());
            entry.addProperty(Deobf.decrypt("\u0012O+\u000b|\u00b0"), (Number)t.accent());
            customs.add((JsonElement)entry);
        }
        json.add(Deobf.decrypt("\u0010Y;\u001a}\u00a9"), (JsonElement)customs);
        return json;
    }

    public void fromJson(JsonObject json) {
        if (json == null) {
            return;
        }
        this.themes.removeIf(Theme::isCustom);
        if (json.has(Deobf.decrypt("\u0010Y;\u001a}\u00a9"))) {
            for (JsonElement el : json.getAsJsonArray(Deobf.decrypt("\u0010Y;\u001a}\u00a9"))) {
                JsonObject entry = el.getAsJsonObject();
                this.themes.add(new Theme(entry.get(Deobf.decrypt("\u001dM%\u000b")).getAsString(), entry.get(Deobf.decrypt("\u0012O+\u000b|\u00b0")).getAsInt(), true));
            }
        }
        if (json.has(Deobf.decrypt("\u0010Y:\u001cw\u00aa\u00b8"))) {
            String name = json.get(Deobf.decrypt("\u0010Y:\u001cw\u00aa\u00b8")).getAsString();
            for (Theme t : this.themes) {
                if (!t.getName().equals(name)) continue;
                this.current = t;
                break;
            }
        }
    }
}

