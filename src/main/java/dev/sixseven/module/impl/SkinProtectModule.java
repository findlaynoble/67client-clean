/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.LinkedHashMultimap
 *  com.google.common.collect.Multimap
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  com.mojang.authlib.properties.PropertyMap
 *  net.minecraft.class_310
 *  net.minecraft.class_8685
 */
package dev.sixseven.module.impl;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.StringSetting;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.class_310;
import net.minecraft.class_8685;

public class SkinProtectModule
extends Module {
    private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8L)).build();
    public final StringSetting ign = this.addSetting(new StringSetting(Deobf.decrypt(" G!\u00002\u008d\u008b\u00a4"), Deobf.decrypt("&_-\u001c|\u00a5\u00a1\u008f\u00e9\u010c\u0101\u010c\u0130\u0195\u01b3\u01c7\u01f5\u0212\u021f\u0215\u0235\u028a\u02d5\u02c3\u02ab\u030e\u0315\u0315\u0344\u03d4"), Deobf.decrypt("E\u001b;\u0005{\u00a6\u00a5\u008e\u00a0"), 16, Deobf.decrypt("'U8\u000b2\u00a5\u00ec\u009f\u00ba\u011e\u011b\u010d\u0122\u019d\u01f6\u2192")));
    public final ModeSetting applyTo = this.addSetting(new ModeSetting(Deobf.decrypt("2\\8\u0002k\u00e4\u0098\u0085"), Deobf.decrypt("$D'\u001dw\u00e4\u00bf\u0081\u00a0\u0115\u0149\u0104\u0126\u0184\u01e0\u0194\u01ec\u021e\u0201\u0259\u023d\u029a\u0290\u02c6"), Deobf.decrypt("6Z-\u001ck\u00ab\u00a2\u008f"), Deobf.decrypt("6Z-\u001ck\u00ab\u00a2\u008f"), Deobf.decrypt("<X \u000b`\u00b7"), Deobf.decrypt(" I$\b")));
    private volatile class_8685 replacement;
    private volatile String fetchedFor = Deobf.decrypt("");
    private volatile boolean fetching;

    public SkinProtectModule() {
        super(Deobf.decrypt(" G!\u0000B\u00b6\u00a3\u009e\u00ac\u0118\u011d"), Deobf.decrypt("!I8\u0002s\u00a7\u00a9\u0099\u00e9\u0108\u0102\u010a\u012d\u0183\u01b3\u01c7\u01f1\u025b\u0212\u0259\u0235\u0289\u0286\u0282\u02b8\u031f\u0317\u035b\u0355\u0390\u03f1\u0399\u03a1\u039d\u045c\u0425\u0406\u0463\u04da"), Category.MISC);
    }

    @Override
    protected void onEnable() {
        if (this.replacement == null) {
            this.fetchedFor = Deobf.decrypt("");
        }
        this.ensureFetched();
    }

    @Override
    public void onTick() {
        this.ensureFetched();
    }

    public class_8685 replacementSkin() {
        return this.replacement;
    }

    public boolean shouldReplace(UUID uuid) {
        UUID self;
        if (uuid == null) {
            return false;
        }
        class_310 mc = class_310.method_1551();
        UUID uUID = self = mc.field_1724 == null ? null : mc.field_1724.method_5667();
        if (this.applyTo.is(Deobf.decrypt("6Z-\u001ck\u00ab\u00a2\u008f"))) {
            return true;
        }
        if (this.applyTo.is(Deobf.decrypt(" I$\b"))) {
            return self != null && self.equals(uuid);
        }
        return self == null || !self.equals(uuid);
    }

    private void ensureFetched() {
        String want = ((String)this.ign.get()).trim();
        if (want.isEmpty() || this.fetching || want.equalsIgnoreCase(this.fetchedFor)) {
            return;
        }
        this.fetching = true;
        this.fetchedFor = want;
        CompletableFuture.runAsync(() -> this.resolve(want)).whenComplete((v, t) -> {
            this.fetching = false;
        });
    }

    private void resolve(String username) {
        try {
            JsonObject profile = SkinProtectModule.getJson("https://api.mojang.com/users/profiles/minecraft/" + username);
            if (profile == null || !profile.has(Deobf.decrypt("\u001aH"))) {
                SixSevenClient.LOGGER.warn(Deobf.decrypt("(\u007f#\u0007|\u0094\u00be\u0085\u00bd\u011e\u010a\u0117\u011e\u01d0\u01c6\u01da\u01f5\u0215\u021e\u0242\u0232\u02d9\u0280\u02d1\u02be\u030c\u0317\u031d\u034c\u03d5\u03af\u03d6\u03a2\u03c0"), (Object)username);
                return;
            }
            UUID uuid = SkinProtectModule.dashify(profile.get(Deobf.decrypt("\u001aH")).getAsString());
            String name = profile.has(Deobf.decrypt("\u001dM%\u000b")) ? profile.get(Deobf.decrypt("\u001dM%\u000b")).getAsString() : username;
            JsonObject full = SkinProtectModule.getJson("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace(Deobf.decrypt("^"), Deobf.decrypt("")) + "?unsigned=false");
            if (full == null || !full.has(Deobf.decrypt("\u0003^'\u001ew\u00b6\u00b8\u0083\u00ac\u0108"))) {
                SixSevenClient.LOGGER.warn(Deobf.decrypt("(\u007f#\u0007|\u0094\u00be\u0085\u00bd\u011e\u010a\u0117\u011e\u01d0\u01dd\u01db\u01be\u020b\u0203\u025a\u023a\u0290\u0299\u02c7\u02fb\u030a\u031c\u0304\u0355\u03c5\u03e7\u0393\u03aa\u039d\u0449\u0421\u041d\u042d\u04d2\u04aa"), (Object)username);
                return;
            }
            LinkedHashMultimap props = LinkedHashMultimap.create();
            for (JsonElement el : full.getAsJsonArray(Deobf.decrypt("\u0003^'\u001ew\u00b6\u00b8\u0083\u00ac\u0108"))) {
                JsonObject prop = el.getAsJsonObject();
                if (!Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099").equals(prop.get(Deobf.decrypt("\u001dM%\u000b")).getAsString())) continue;
                String value = prop.get(Deobf.decrypt("\u0005M$\u001bw")).getAsString();
                String signature = prop.has(Deobf.decrypt("\u0000E/\u0000s\u00b0\u00b9\u0098\u00ac")) ? prop.get(Deobf.decrypt("\u0000E/\u0000s\u00b0\u00b9\u0098\u00ac")).getAsString() : null;
                props.put((Object)Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099"), (Object)(signature == null ? new Property(Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099"), value) : new Property(Deobf.decrypt("\u0007I0\u001ag\u00b6\u00a9\u0099"), value, signature)));
            }
            GameProfile gameProfile = new GameProfile(uuid, name, new PropertyMap((Multimap)props));
            class_310.method_1551().method_1582().method_52863(gameProfile).thenAccept(opt -> {
                if (opt.isPresent()) {
                    this.replacement = (class_8685)opt.get();
                    SixSevenClient.LOGGER.info(Deobf.decrypt("(\u007f#\u0007|\u0094\u00be\u0085\u00bd\u011e\u010a\u0117\u011e\u01d0\u01df\u01db\u01ff\u021f\u0214\u0251\u027c\u028a\u029e\u02cb\u02b5\u035e\u031f\u0313\u0353\u0390\u03ee\u038b"), (Object)name);
                } else {
                    SixSevenClient.LOGGER.warn(Deobf.decrypt("(\u007f#\u0007|\u0094\u00be\u0085\u00bd\u011e\u010a\u0117\u011e\u01d0\u01d0\u01db\u01eb\u0217\u0215\u0215\u0232\u0296\u0281\u0282\u02b7\u0311\u0318\u0318\u0301\u03c3\u03fe\u039f\u03b7\u039d\u045b\u042b\u0417\u0479\u04dc\u04a5\u04e2\u04a1\u0543\u056b\u054a\u050c\u05df\u05fb"), (Object)name);
                }
            });
        }
        catch (Exception e) {
            SixSevenClient.LOGGER.warn(Deobf.decrypt("(\u007f#\u0007|\u0094\u00be\u0085\u00bd\u011e\u010a\u0117\u011e\u01d0\u01d5\u01d5\u01f7\u0217\u0214\u0251\u027c\u028d\u029a\u0282\u02bd\u031b\u030d\u031f\u0349\u0390\u03e6\u039d\u03b0\u03d3\u040f\u0428\u0400\u047f\u0489\u04ac\u04fa\u04bb\u0505\u057f\u0545"), (Object)username, (Object)e.toString());
        }
    }

    private static JsonObject getJson(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(8L)).header(Deobf.decrypt("2O+\u000bb\u00b0"), Deobf.decrypt("\u0012\\8\u0002{\u00a7\u00ad\u009e\u00a0\u0114\u0107\u014c\u0129\u0183\u01fc\u01da")).GET().build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 || response.body() == null || response.body().isBlank()) {
            return null;
        }
        return JsonParser.parseString((String)response.body()).getAsJsonObject();
    }

    private static UUID dashify(String undashed) {
        return UUID.fromString(undashed.replaceFirst(Deobf.decrypt("[p?\u0015*\u00b9\u00e5\u00c2\u0095\u010c\u0112\u0157\u013e\u01d9\u01bb\u01e8\u01e9\u0200\u0245\u0248\u0275\u02d1\u02a9\u02d5\u02a0\u034a\u0304\u0355\u0309\u03ec\u03e2\u038d\u03e8\u038f\u0452\u0467"), Deobf.decrypt("W\u001deJ \u00e9\u00e8\u00d9\u00e4\u015f\u015d\u014e\u0167\u01c5")));
    }
}

