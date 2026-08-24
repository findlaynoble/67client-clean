/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.class_1792
 */
package dev.sixseven.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.Setting;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.class_1792;

public class IconListSetting
extends Setting<List<Entry>> {
    private final Map<String, Entry> byKey = new LinkedHashMap<String, Entry>();

    public IconListSetting(String name, String description) {
        super(name, description, new ArrayList());
    }

    public Entry add(String key, String label, class_1792 icon, boolean enabled, int color) {
        Entry entry = new Entry(key, label, icon, enabled, color);
        ((List)this.value).add(entry);
        this.byKey.put(key, entry);
        return entry;
    }

    public List<Entry> entries() {
        return (List)this.value;
    }

    public Entry get(String key) {
        return this.byKey.get(key);
    }

    public boolean isEnabled(String key) {
        Entry e = this.byKey.get(key);
        return e != null && (Boolean)e.enabled.get() != false;
    }

    public int color(String key) {
        Entry e = this.byKey.get(key);
        return e != null ? (Integer)e.color.get() : -1;
    }

    public int size() {
        return ((List)this.value).size();
    }

    public long enabledCount() {
        return ((List)this.value).stream().filter(e -> (Boolean)e.enabled.get()).count();
    }

    @Override
    public JsonElement toJson() {
        JsonArray arr = new JsonArray();
        for (Entry e : (List)this.value) {
            JsonObject o = new JsonObject();
            o.addProperty(Deobf.decrypt("\u0018I1"), e.key);
            o.addProperty(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8"), (Boolean)e.enabled.get());
            o.addProperty(Deobf.decrypt("\u0010C$\u0001`"), (Number)e.color.get());
            arr.add((JsonElement)o);
        }
        return arr;
    }

    @Override
    public void fromJson(JsonElement element) {
        if (element == null || !element.isJsonArray()) {
            return;
        }
        for (JsonElement el : element.getAsJsonArray()) {
            Entry e;
            JsonObject o;
            if (!el.isJsonObject() || !(o = el.getAsJsonObject()).has(Deobf.decrypt("\u0018I1")) || (e = this.byKey.get(o.get(Deobf.decrypt("\u0018I1")).getAsString())) == null) continue;
            if (o.has(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8"))) {
                e.enabled.set(o.get(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8")).getAsBoolean());
            }
            if (!o.has(Deobf.decrypt("\u0010C$\u0001`"))) continue;
            e.color.set(o.get(Deobf.decrypt("\u0010C$\u0001`")).getAsInt());
        }
    }

    public static final class Entry {
        private final String key;
        private final String label;
        private final class_1792 icon;
        public final BooleanSetting enabled;
        public final ColorSetting color;

        Entry(String key, String label, class_1792 icon, boolean enabled, int color) {
            this.key = key;
            this.label = label;
            this.icon = icon;
            this.enabled = new BooleanSetting(Deobf.decrypt("6B)\f~\u00a1\u00a8"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u011d\u010b\u012a\u0183\u01b3\u01c0\u01e7\u020b\u0214"), enabled);
            this.color = new ColorSetting(label, Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u010a\u010c\u012f\u019f\u01e1"), color);
        }

        public String key() {
            return this.key;
        }

        public String label() {
            return this.label;
        }

        public class_1792 icon() {
            return this.icon;
        }

        public boolean matches(String lowerQuery) {
            return this.label.toLowerCase(Locale.ROOT).contains(lowerQuery) || this.key.contains(lowerQuery);
        }
    }
}

