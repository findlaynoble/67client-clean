/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2960
 *  net.minecraft.class_7923
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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class BlockListSetting
extends Setting<List<Target>> {
    public static final int DEFAULT_COLOR = -16711736;
    private final Set<class_2960> ids = new HashSet<class_2960>();

    public BlockListSetting(String name, String description) {
        super(name, description, new ArrayList());
    }

    public List<Target> targets() {
        return (List)this.value;
    }

    public int size() {
        return ((List)this.value).size();
    }

    public long enabledCount() {
        return ((List)this.value).stream().filter(t -> (Boolean)t.enabled.get()).count();
    }

    public boolean contains(class_2960 id) {
        return this.ids.contains(id);
    }

    public Target find(class_2248 block) {
        if (block == null) {
            return null;
        }
        class_2960 id = class_7923.field_41175.method_10221((Object)block);
        if (id == null || !this.ids.contains(id)) {
            return null;
        }
        for (Target t : (List)this.value) {
            if (!t.id().equals((Object)id)) continue;
            return t;
        }
        return null;
    }

    public boolean isActive(class_2248 block) {
        Target t = this.find(block);
        return t != null && (Boolean)t.enabled.get() != false;
    }

    public Target add(class_2248 block, boolean enabled, int color) {
        if (block == null || block == class_2246.field_10124) {
            return null;
        }
        class_2960 id = class_7923.field_41175.method_10221((Object)block);
        if (id == null || this.ids.contains(id)) {
            return null;
        }
        Target target = new Target(id, block, enabled, color);
        ((List)this.value).add(target);
        this.ids.add(id);
        return target;
    }

    public void remove(Target target) {
        if (((List)this.value).remove(target)) {
            this.ids.remove(target.id());
        }
    }

    public void clear() {
        ((List)this.value).clear();
        this.ids.clear();
    }

    public List<class_2248> searchRegistry(String rawQuery, int limit) {
        String q = rawQuery == null ? Deobf.decrypt("") : rawQuery.trim().toLowerCase(Locale.ROOT);
        ArrayList<class_2248> out = new ArrayList<class_2248>();
        if (q.isEmpty() || limit <= 0) {
            return out;
        }
        for (class_2248 block : class_7923.field_41175) {
            class_2960 id;
            if (block == class_2246.field_10124 || block == class_2246.field_10543 || block == class_2246.field_10243 || (id = class_7923.field_41175.method_10221((Object)block)) == null || this.ids.contains(id)) continue;
            String path = id.method_12832().toLowerCase(Locale.ROOT);
            String ns = id.method_12836().toLowerCase(Locale.ROOT);
            String name = BlockListSetting.displayName(block).toLowerCase(Locale.ROOT);
            if (!path.contains(q) && !ns.contains(q) && !name.contains(q)) continue;
            out.add(block);
            if (out.size() < limit) continue;
            break;
        }
        return out;
    }

    public static String displayName(class_2248 block) {
        try {
            return block.method_9518().getString();
        }
        catch (Throwable t) {
            class_2960 id = class_7923.field_41175.method_10221((Object)block);
            return id != null ? id.method_12832() : Deobf.decrypt("\u0011@'\ry");
        }
    }

    public void seedDefaults() {
        this.clear();
        this.add(class_2246.field_10442, true, -16711736);
        this.add(class_2246.field_29029, true, -16711736);
        this.add(class_2246.field_10013, false, -16711868);
        this.add(class_2246.field_29220, false, -16711868);
        this.add(class_2246.field_22109, true, -39356);
        this.add(class_2246.field_23077, false, -10496);
        this.add(class_2246.field_10571, false, -10496);
        this.add(class_2246.field_29026, false, -10496);
        this.add(class_2246.field_10212, false, -3618616);
        this.add(class_2246.field_29027, false, -3618616);
        this.add(class_2246.field_10418, false, -12303292);
        this.add(class_2246.field_29219, false, -12303292);
        this.add(class_2246.field_27120, false, -4689101);
        this.add(class_2246.field_29221, false, -4689101);
        this.add(class_2246.field_10090, false, -12490271);
        this.add(class_2246.field_29028, false, -12490271);
        this.add(class_2246.field_10080, false, -65536);
        this.add(class_2246.field_29030, false, -65536);
        this.add(class_2246.field_10260, false, -7846657);
        this.add(class_2246.field_10398, false, -12255250);
        this.add(class_2246.field_10034, false, -22016);
    }

    @Override
    public JsonElement toJson() {
        JsonArray arr = new JsonArray();
        for (Target t : (List)this.value) {
            JsonObject o = new JsonObject();
            o.addProperty(Deobf.decrypt("\u001aH"), t.id().toString());
            o.addProperty(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8"), (Boolean)t.enabled.get());
            o.addProperty(Deobf.decrypt("\u0010C$\u0001`"), (Number)t.color.get());
            arr.add((JsonElement)o);
        }
        return arr;
    }

    @Override
    public void fromJson(JsonElement element) {
        if (element == null || !element.isJsonArray()) {
            return;
        }
        this.clear();
        for (JsonElement el : element.getAsJsonArray()) {
            class_2960 id;
            JsonObject o;
            if (!el.isJsonObject() || !(o = el.getAsJsonObject()).has(Deobf.decrypt("\u001aH"))) continue;
            try {
                id = class_2960.method_60654((String)o.get(Deobf.decrypt("\u001aH")).getAsString());
            }
            catch (Exception ignored) {
                continue;
            }
            class_2248 block = (class_2248)class_7923.field_41175.method_63535(id);
            if (block == null || block == class_2246.field_10124) continue;
            boolean en = !o.has(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8")) || o.get(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8")).getAsBoolean();
            int color = o.has(Deobf.decrypt("\u0010C$\u0001`")) ? o.get(Deobf.decrypt("\u0010C$\u0001`")).getAsInt() : -16711736;
            this.add(block, en, color);
        }
    }

    public static final class Target {
        private final class_2960 id;
        private final class_2248 block;
        public final BooleanSetting enabled;
        public final ColorSetting color;

        Target(class_2960 id, class_2248 block, boolean enabled, int color) {
            this.id = id;
            this.block = block;
            this.enabled = new BooleanSetting(Deobf.decrypt("6B)\f~\u00a1\u00a8"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u011d\u010b\u012a\u0183\u01b3\u01d6\u01f2\u0214\u0212\u025e"), enabled);
            this.color = new ColorSetting(BlockListSetting.displayName(block), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u010a\u010c\u012f\u019f\u01e1"), color);
        }

        public class_2960 id() {
            return this.id;
        }

        public class_2248 block() {
            return this.block;
        }

        public String label() {
            return this.color.getName();
        }
    }
}

