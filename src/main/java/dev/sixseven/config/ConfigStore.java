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
import dev.sixseven.config.ConfigManager;
import dev.sixseven.rt.Deobf;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigStore {
    public static final int SLOT_COUNT = 5;
    public static final int CONFIG_VERSION = 1;
    public static final String FORMAT = "67client-config";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static volatile boolean applying;
    private final ConfigManager config;
    private final Path dir;
    private final Slot[] slots = new Slot[5];
    private int active = -1;

    public ConfigStore(ConfigManager config) {
        this.config = config;
        this.dir = FabricLoader.getInstance().getConfigDir().resolve(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01be\u01d7\u01f1\u0215\u0217\u025c\u023b\u028a"));
        for (int i = 0; i < 5; ++i) {
            this.slots[i] = new Slot(i);
        }
    }

    public static String defaultName(int index) {
        return "Config " + (index + 1);
    }

    public Slot slot(int index) {
        return this.slots[index];
    }

    public Slot[] slots() {
        return this.slots;
    }

    public int activeIndex() {
        return this.active;
    }

    public Path directory() {
        return this.dir;
    }

    public void loadAll() {
        int a;
        for (int i = 0; i < 5; ++i) {
            this.refreshSlot(i);
        }
        JsonObject index = this.readJson(this.dir.resolve(Deobf.decrypt("\u001aB,\u000bj\u00ea\u00a6\u0099\u00a6\u0115")));
        if (index != null && index.has(Deobf.decrypt("\u0012O<\u0007d\u00a1")) && (a = index.get(Deobf.decrypt("\u0012O<\u0007d\u00a1")).getAsInt()) >= 0 && a < 5 && this.slots[a].filled) {
            this.active = a;
        }
    }

    private void refreshSlot(int i) {
        Slot slot = this.slots[i];
        JsonObject file = this.readJson(this.slotPath(i));
        JsonObject state = this.extractState(file);
        if (state == null) {
            slot.filled = false;
            slot.savedAt = 0L;
            slot.name = ConfigStore.defaultName(i);
            return;
        }
        slot.filled = true;
        slot.name = file.has(Deobf.decrypt("\u001dM%\u000b")) && !file.get(Deobf.decrypt("\u001dM%\u000b")).getAsString().isBlank() ? file.get(Deobf.decrypt("\u001dM%\u000b")).getAsString() : ConfigStore.defaultName(i);
        slot.savedAt = file.has(Deobf.decrypt("\u0000M>\u000bv\u0085\u00b8")) ? file.get(Deobf.decrypt("\u0000M>\u000bv\u0085\u00b8")).getAsLong() : 0L;
    }

    public boolean save(int i) {
        Slot slot = this.slots[i];
        JsonObject file = new JsonObject();
        file.addProperty(Deobf.decrypt("\u0015C:\u0003s\u00b0"), Deobf.decrypt("E\u001b+\u0002{\u00a1\u00a2\u009e\u00e4\u0118\u0106\u010d\u0125\u0199\u01f4"));
        file.addProperty(Deobf.decrypt("\u0005I:\u001d{\u00ab\u00a2"), (Number)1);
        file.addProperty(Deobf.decrypt("\u001dM%\u000b"), slot.name);
        file.addProperty(Deobf.decrypt("\u0000M>\u000bv\u0085\u00b8"), (Number)System.currentTimeMillis());
        file.addProperty(Deobf.decrypt("\u0010@!\u000b|\u00b0"), Deobf.decrypt("B\u0002~@ "));
        file.add(Deobf.decrypt("\u0000X)\u001aw"), (JsonElement)this.config.captureState());
        if (!this.write(this.slotPath(i), file)) {
            return false;
        }
        this.refreshSlot(i);
        return true;
    }

    public boolean activate(int i) {
        JsonObject state = this.extractState(this.readJson(this.slotPath(i)));
        if (state == null) {
            return false;
        }
        applying = true;
        try {
            this.config.applyState(state);
        }
        finally {
            applying = false;
        }
        this.active = i;
        this.writeIndex();
        return true;
    }

    public boolean delete(int i) {
        try {
            Files.deleteIfExists(this.slotPath(i));
        }
        catch (IOException e) {
            SixSevenClient.LOGGER.error(Deobf.decrypt("5M!\u0002w\u00a0\u00ec\u009e\u00a6\u015b\u010d\u0106\u012f\u0195\u01e7\u01d1\u01be\u0218\u021e\u025b\u023a\u0290\u0292\u0282\u02bd\u0317\u0315\u0319\u0301\u03cb\u03e8"), (Object)this.slotPath(i).getFileName(), (Object)e);
            return false;
        }
        if (this.active == i) {
            this.active = -1;
            this.writeIndex();
        }
        this.refreshSlot(i);
        return true;
    }

    public boolean rename(int i, String name) {
        Slot slot = this.slots[i];
        String clean = this.sanitizeName(name);
        if (clean.isEmpty()) {
            clean = ConfigStore.defaultName(i);
        }
        slot.name = clean;
        if (!slot.filled) {
            return true;
        }
        JsonObject file = this.readJson(this.slotPath(i));
        if (file == null) {
            return false;
        }
        file.addProperty(Deobf.decrypt("\u001dM%\u000b"), clean);
        return this.write(this.slotPath(i), file);
    }

    public String export(int i) {
        JsonObject file = this.readJson(this.slotPath(i));
        if (this.extractState(file) == null) {
            return null;
        }
        String json = GSON.toJson((JsonElement)file);
        String base = this.sanitizeFileName(this.slots[i].name);
        this.write(this.dir.resolve(base + ".json"), file);
        return json;
    }

    public ImportResult importInto(int i, String raw) {
        JsonObject parsed;
        if (raw == null || raw.isBlank()) {
            return new ImportResult(false, Deobf.decrypt("0@!\u001ep\u00ab\u00ad\u0098\u00ad\u015b\u0100\u0110\u0163\u0195\u01fe\u01c4\u01ea\u0202"));
        }
        try {
            parsed = JsonParser.parseString((String)raw).getAsJsonObject();
        }
        catch (Exception e) {
            return new ImportResult(false, Deobf.decrypt("=C<Nd\u00a5\u00a0\u0083\u00ad\u015b\u010a\u010c\u012d\u0196\u01fa\u01d3\u01be\u0231\u0222\u027a\u0212"));
        }
        if (parsed.has(Deobf.decrypt("\u0005I:\u001d{\u00ab\u00a2")) && parsed.get(Deobf.decrypt("\u0005I:\u001d{\u00ab\u00a2")).isJsonPrimitive() && parsed.get(Deobf.decrypt("\u0005I:\u001d{\u00ab\u00a2")).getAsInt() > 1) {
            return new ImportResult(false, Deobf.decrypt("0C&\b{\u00a3\u00ec\u0083\u00ba\u015b\u010f\u0111\u012c\u019d\u01b3\u01d5\u01be\u0215\u0214\u0242\u0239\u028b\u02d5\u02c1\u02b7\u0317\u031c\u0312\u0355"));
        }
        JsonObject state = this.extractState(parsed);
        if (state == null) {
            return new ImportResult(false, Deobf.decrypt("=Ch\r}\u00aa\u00aa\u0083\u00ae\u015b\u010d\u0102\u0137\u0191\u01b3\u01d2\u01f1\u020e\u021f\u0251"));
        }
        Slot slot = this.slots[i];
        String name = parsed.has(Deobf.decrypt("\u001dM%\u000b")) && !parsed.get(Deobf.decrypt("\u001dM%\u000b")).getAsString().isBlank() ? this.sanitizeName(parsed.get(Deobf.decrypt("\u001dM%\u000b")).getAsString()) : slot.name;
        JsonObject file = new JsonObject();
        file.addProperty(Deobf.decrypt("\u0015C:\u0003s\u00b0"), Deobf.decrypt("E\u001b+\u0002{\u00a1\u00a2\u009e\u00e4\u0118\u0106\u010d\u0125\u0199\u01f4"));
        file.addProperty(Deobf.decrypt("\u0005I:\u001d{\u00ab\u00a2"), (Number)1);
        file.addProperty(Deobf.decrypt("\u001dM%\u000b"), name);
        file.addProperty(Deobf.decrypt("\u0000M>\u000bv\u0085\u00b8"), (Number)(parsed.has(Deobf.decrypt("\u0000M>\u000bv\u0085\u00b8")) ? parsed.get(Deobf.decrypt("\u0000M>\u000bv\u0085\u00b8")).getAsLong() : System.currentTimeMillis()));
        if (parsed.has(Deobf.decrypt("\u0010@!\u000b|\u00b0"))) {
            file.addProperty(Deobf.decrypt("\u0010@!\u000b|\u00b0"), parsed.get(Deobf.decrypt("\u0010@!\u000b|\u00b0")).getAsString());
        }
        file.add(Deobf.decrypt("\u0000X)\u001aw"), (JsonElement)state);
        if (!this.write(this.slotPath(i), file)) {
            return new ImportResult(false, Deobf.decrypt("0C=\u0002v\u00aa\u00eb\u009e\u00e9\u010c\u011b\u010a\u0137\u0195\u01b3\u01c0\u01f6\u021e\u0251\u0246\u0230\u0296\u0281\u0282\u02bd\u0317\u0315\u0319"));
        }
        this.refreshSlot(i);
        return new ImportResult(true, "Imported \"" + slot.name + "\"");
    }

    private JsonObject extractState(JsonObject file) {
        if (file == null) {
            return null;
        }
        if (file.has(Deobf.decrypt("\u0000X)\u001aw")) && file.get(Deobf.decrypt("\u0000X)\u001aw")).isJsonObject()) {
            return file.getAsJsonObject(Deobf.decrypt("\u0000X)\u001aw"));
        }
        if (file.has(Deobf.decrypt("\u001eC,\u001b~\u00a1\u00bf")) && file.get(Deobf.decrypt("\u001eC,\u001b~\u00a1\u00bf")).isJsonObject()) {
            return file;
        }
        return null;
    }

    private Path slotPath(int i) {
        return this.dir.resolve("slot" + (i + 1) + ".json");
    }

    private void writeIndex() {
        JsonObject index = new JsonObject();
        index.addProperty(Deobf.decrypt("\u0012O<\u0007d\u00a1"), (Number)this.active);
        this.write(this.dir.resolve(Deobf.decrypt("\u001aB,\u000bj\u00ea\u00a6\u0099\u00a6\u0115")), index);
    }

    private JsonObject readJson(Path path) {
        if (!Files.exists(path, new LinkOption[0])) {
            return null;
        }
        try {
            return JsonParser.parseString((String)Files.readString(path)).getAsJsonObject();
        }
        catch (Exception e) {
            SixSevenClient.LOGGER.warn(Deobf.decrypt(":K&\u0001`\u00ad\u00a2\u008d\u00e9\u010e\u0107\u0111\u0126\u0191\u01f7\u01d5\u01fc\u0217\u0214\u0215\u023f\u0296\u029b\u02c4\u02b2\u0319\u0359\u031a\u0348\u03dc\u03f0\u03d6\u03a2\u03c0"), (Object)path.getFileName(), (Object)e);
            return null;
        }
    }

    private boolean write(Path path, JsonObject json) {
        try {
            Files.createDirectories(this.dir, new FileAttribute[0]);
            Files.writeString(path, (CharSequence)GSON.toJson((JsonElement)json), new OpenOption[0]);
            return true;
        }
        catch (IOException e) {
            SixSevenClient.LOGGER.error(Deobf.decrypt("5M!\u0002w\u00a0\u00ec\u009e\u00a6\u015b\u011e\u0111\u012a\u0184\u01f6\u0194\u01fd\u0214\u021f\u0253\u0235\u029e\u02d5\u02c4\u02b2\u0312\u031c\u035c\u035a\u03cd"), (Object)path.getFileName(), (Object)e);
            return false;
        }
    }

    private String sanitizeName(String name) {
        if (name == null) {
            return Deobf.decrypt("");
        }
        String clean = name.replaceAll(Deobf.decrypt("(p:2|\u0098\u00b8\u00b7"), Deobf.decrypt("S")).trim();
        return clean.length() > 24 ? clean.substring(0, 24) : clean;
    }

    private String sanitizeFileName(String name) {
        String clean = name.replaceAll(Deobf.decrypt("(r)Ch\u0085\u00e1\u00b0\u00f9\u0156\u0150\u014e\u011c\u01d0\u01ce"), Deobf.decrypt("")).trim().replace(' ', '_');
        return clean.isEmpty() ? Deobf.decrypt("\u0010C&\b{\u00a3") : clean;
    }

    public static final class Slot {
        private final int index;
        private String name;
        private boolean filled;
        private long savedAt;

        Slot(int index) {
            this.index = index;
            this.name = ConfigStore.defaultName(index);
        }

        public int index() {
            return this.index;
        }

        public String name() {
            return this.name;
        }

        public boolean filled() {
            return this.filled;
        }

        public long savedAt() {
            return this.savedAt;
        }
    }

    public record ImportResult(boolean ok, String message) {
    }
}

