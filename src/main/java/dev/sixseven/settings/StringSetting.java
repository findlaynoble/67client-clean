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
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.Setting;

public class StringSetting
extends Setting<String> {
    private final int maxLength;
    private final String placeholder;

    public StringSetting(String name, String description, String defaultValue) {
        this(name, description, defaultValue, 32, Deobf.decrypt(""));
    }

    public StringSetting(String name, String description, String defaultValue, int maxLength, String placeholder) {
        super(name, description, defaultValue);
        this.maxLength = maxLength;
        this.placeholder = placeholder;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((String)this.value);
    }

    @Override
    public void fromJson(JsonElement element) {
        if (element != null && element.isJsonPrimitive()) {
            String s = element.getAsString();
            this.value = s.length() > this.maxLength ? s.substring(0, this.maxLength) : s;
        }
    }
}

