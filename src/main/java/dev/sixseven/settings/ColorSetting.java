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

public class ColorSetting
extends Setting<Integer> {
    public ColorSetting(String name, String description, int defaultArgb) {
        super(name, description, defaultArgb);
    }

    public int red() {
        return (Integer)this.get() >> 16 & 0xFF;
    }

    public int green() {
        return (Integer)this.get() >> 8 & 0xFF;
    }

    public int blue() {
        return (Integer)this.get() & 0xFF;
    }

    public int alpha() {
        return (Integer)this.get() >>> 24 & 0xFF;
    }

    public void setRed(int r) {
        this.set((Integer)this.get() & 0xFF00FFFF | (r & 0xFF) << 16);
    }

    public void setGreen(int g) {
        this.set((Integer)this.get() & 0xFFFF00FF | (g & 0xFF) << 8);
    }

    public void setBlue(int b) {
        this.set((Integer)this.get() & 0xFFFFFF00 | b & 0xFF);
    }

    public String hex() {
        return String.format(Deobf.decrypt("P\txXJ"), (Integer)this.get() & 0xFFFFFF);
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((Number)this.value);
    }

    @Override
    public void fromJson(JsonElement element) {
        if (element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            this.value = element.getAsInt();
        }
    }
}

