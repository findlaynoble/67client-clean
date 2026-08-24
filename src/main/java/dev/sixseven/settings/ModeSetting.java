/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 */
package dev.sixseven.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.sixseven.settings.Setting;
import java.util.List;

public class ModeSetting
extends Setting<String> {
    private final List<String> modes;

    public ModeSetting(String name, String description, String defaultValue, String ... modes) {
        super(name, description, defaultValue);
        this.modes = List.of(modes);
        if (!this.modes.contains(defaultValue)) {
            throw new IllegalArgumentException("Default mode '" + defaultValue + "' not in modes for " + name);
        }
    }

    public List<String> getModes() {
        return this.modes;
    }

    public boolean is(String mode) {
        return ((String)this.get()).equals(mode);
    }

    public void cycle() {
        int next = (this.modes.indexOf(this.get()) + 1) % this.modes.size();
        this.set(this.modes.get(next));
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((String)this.value);
    }

    @Override
    public void fromJson(JsonElement element) {
        if (element != null && element.isJsonPrimitive() && this.modes.contains(element.getAsString())) {
            this.value = element.getAsString();
        }
    }
}

