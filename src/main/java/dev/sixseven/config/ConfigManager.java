/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.fabricmc.loader.api.FabricLoader
 */
package dev.sixseven.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Module;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.Setting;
import dev.sixseven.theme.ThemeManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path file = FabricLoader.getInstance().getConfigDir().resolve(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01bd\u01de\u01ed\u0214\u021f"));
    private final ModuleManager modules;
    private final ThemeManager themes;
    private final Map<String, Section> sections = new LinkedHashMap<String, Section>();

    public ConfigManager(ModuleManager modules, ThemeManager themes) {
        this.modules = modules;
        this.themes = themes;
    }

    public void addSection(String key, Supplier<JsonObject> save, Consumer<JsonObject> load) {
        this.sections.put(key, new Section(save, load));
    }

    public JsonObject captureState() {
        JsonObject root = new JsonObject();
        root.add(Deobf.decrypt("\u0007D-\u0003w"), (JsonElement)this.themes.toJson());
        JsonObject moduleJson = new JsonObject();
        for (Module module : this.modules.all()) {
            JsonObject m = new JsonObject();
            m.addProperty(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8"), Boolean.valueOf(module.isEnabled()));
            m.add(Deobf.decrypt("\u0018I1\f{\u00aa\u00a8"), module.getKeybind().toJson());
            JsonObject settings = new JsonObject();
            for (Setting<?> setting : module.getSettings()) {
                settings.add(setting.getName(), setting.toJson());
            }
            m.add(Deobf.decrypt("\u0000I<\u001a{\u00aa\u00ab\u0099"), (JsonElement)settings);
            moduleJson.add(module.getName() + "@" + module.getCategory().name(), (JsonElement)m);
        }
        root.add(Deobf.decrypt("\u001eC,\u001b~\u00a1\u00bf"), (JsonElement)moduleJson);
        for (Map.Entry entry : this.sections.entrySet()) {
            root.add((String)entry.getKey(), (JsonElement)((Section)entry.getValue()).save().get());
        }
        return root;
    }

    public void applyState(JsonObject root) {
        if (root == null) {
            return;
        }
        if (root.has(Deobf.decrypt("\u0007D-\u0003w")) && root.get(Deobf.decrypt("\u0007D-\u0003w")).isJsonObject()) {
            this.themes.fromJson(root.getAsJsonObject(Deobf.decrypt("\u0007D-\u0003w")));
        }
        if (root.has(Deobf.decrypt("\u001eC,\u001b~\u00a1\u00bf")) && root.get(Deobf.decrypt("\u001eC,\u001b~\u00a1\u00bf")).isJsonObject()) {
            JsonObject moduleJson = root.getAsJsonObject(Deobf.decrypt("\u001eC,\u001b~\u00a1\u00bf"));
            for (Module module : this.modules.all()) {
                JsonObject settings;
                JsonObject m = moduleJson.getAsJsonObject(module.getName() + "@" + module.getCategory().name());
                if (m == null) continue;
                if (m.has(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8")) && m.get(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8")).getAsBoolean() != module.isEnabled()) {
                    module.setEnabled(m.get(Deobf.decrypt("\u0016B)\f~\u00a1\u00a8")).getAsBoolean());
                }
                if (m.has(Deobf.decrypt("\u0018I1\f{\u00aa\u00a8"))) {
                    module.getKeybind().fromJson(m.get(Deobf.decrypt("\u0018I1\f{\u00aa\u00a8")));
                }
                if ((settings = m.getAsJsonObject(Deobf.decrypt("\u0000I<\u001a{\u00aa\u00ab\u0099"))) == null) continue;
                for (Setting<?> setting : module.getSettings()) {
                    if (!settings.has(setting.getName())) continue;
                    setting.fromJson(settings.get(setting.getName()));
                }
            }
        }
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            if (!root.has(entry.getKey()) || !root.get(entry.getKey()).isJsonObject()) continue;
            entry.getValue().load().accept(root.getAsJsonObject(entry.getKey()));
        }
    }

    public synchronized void save() {
        try {
            Files.createDirectories(this.file.getParent(), new FileAttribute[0]);
            Files.writeString(this.file, (CharSequence)GSON.toJson((JsonElement)this.captureState()), new OpenOption[0]);
        }
        catch (IOException e) {
            SixSevenClient.LOGGER.error(Deobf.decrypt("5M!\u0002w\u00a0\u00ec\u009e\u00a6\u015b\u011a\u0102\u0135\u0195\u01b3\u01d7\u01f1\u0215\u0217\u025c\u023b"), (Throwable)e);
        }
    }

    public synchronized void load() {
        JsonObject root;
        if (!Files.exists(this.file, new LinkOption[0])) {
            return;
        }
        try {
            root = JsonParser.parseString((String)Files.readString(this.file)).getAsJsonObject();
        }
        catch (Exception e) {
            SixSevenClient.LOGGER.error(Deobf.decrypt("5M!\u0002w\u00a0\u00ec\u009e\u00a6\u015b\u011b\u0106\u0122\u0194\u01b3\u01d7\u01f1\u0215\u0217\u025c\u023b\u02d5\u02d5\u02d7\u02a8\u0317\u0317\u031b\u0301\u03d4\u03f0\u0390\u03b8\u03c8\u0443\u043a\u041c"), (Throwable)e);
            return;
        }
        this.applyState(root);
    }

    public record Section(Supplier<JsonObject> save, Consumer<JsonObject> load) {
    }
}

