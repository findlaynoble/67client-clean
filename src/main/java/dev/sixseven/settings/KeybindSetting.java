/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  org.lwjgl.glfw.GLFW
 */
package dev.sixseven.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.Setting;
import org.lwjgl.glfw.GLFW;

public class KeybindSetting
extends Setting<Integer> {
    public static final int NONE = -1;

    public KeybindSetting(String name, String description, int defaultKey) {
        super(name, description, defaultKey);
    }

    public boolean isBound() {
        return (Integer)this.get() != -1;
    }

    public boolean matches(int keyCode) {
        return this.isBound() && (Integer)this.get() == keyCode;
    }

    public String keyName() {
        int key = (Integer)this.get();
        if (key == -1) {
            return Deobf.decrypt("=C&\u000b");
        }
        if (key >= 0 && key <= 7) {
            return switch (key) {
                case 0 -> Deobf.decrypt("?a\n");
                case 1 -> Deobf.decrypt("!a\n");
                case 2 -> Deobf.decrypt(">a\n");
                default -> "MB" + (key + 1);
            };
        }
        String name = GLFW.glfwGetKeyName((int)key, (int)0);
        if (name != null) {
            return name.toUpperCase();
        }
        return switch (key) {
            case 340 -> Deobf.decrypt("?\u007f\u0000'T\u0090");
            case 344 -> Deobf.decrypt("!\u007f\u0000'T\u0090");
            case 341 -> Deobf.decrypt("?o\u001c<^");
            case 345 -> Deobf.decrypt("!o\u001c<^");
            case 342 -> Deobf.decrypt("?m\u0004:");
            case 346 -> Deobf.decrypt("!m\u0004:");
            case 32 -> Deobf.decrypt(" |\t-W");
            case 257 -> Deobf.decrypt("6b\u001c+@");
            case 258 -> Deobf.decrypt("'m\n");
            case 259 -> Deobf.decrypt("1m\u000b%");
            case 280 -> Deobf.decrypt("0m\u0018=");
            case 265 -> Deobf.decrypt("&|");
            case 264 -> Deobf.decrypt("7c\u001f ");
            case 263 -> Deobf.decrypt("?i\u000e:");
            case 262 -> Deobf.decrypt("!e\u000f&F");
            case 260 -> Deobf.decrypt(":b\u001b+@\u0090");
            case 261 -> Deobf.decrypt("7i\u0004+F\u0081");
            case 268 -> Deobf.decrypt(";c\u0005+");
            case 269 -> Deobf.decrypt("6b\f");
            case 266 -> Deobf.decrypt("#k\u001d>");
            case 267 -> Deobf.decrypt("#k\f ");
            default -> key >= 290 && key <= 314 ? "F" + (key - 290 + 1) : "KEY" + key;
        };
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

