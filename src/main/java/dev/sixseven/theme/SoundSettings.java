/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 */
package dev.sixseven.theme;

import com.google.gson.JsonObject;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.Setting;
import dev.sixseven.settings.SliderSetting;
import java.util.List;

public class SoundSettings {
    public final SliderSetting masterVolume = new SliderSetting(Deobf.decrypt(">M;\u001aw\u00b6\u00ec\u00bc\u00a6\u0117\u011c\u010e\u0126"), Deobf.decrypt("%C$\u001b\u007f\u00a1\u00ec\u008c\u00a6\u0109\u0149\u0102\u012f\u019c\u01b3\u01d7\u01f2\u0212\u0214\u025b\u0228\u02d9\u0286\u02cd\u02ae\u0310\u031d\u030f"), 60.0, 0.0, 100.0, 5.0, Deobf.decrypt("V"));
    public final BooleanSetting guiSounds = new BooleanSetting(Deobf.decrypt("4y\u0001N]\u00b4\u00a9\u0084\u00e6\u0138\u0105\u010c\u0130\u0195"), Deobf.decrypt("$D'\u0001a\u00ac\u00ec\u009d\u00a1\u011e\u0107\u0143\u0137\u0198\u01f6\u0194\u01f3\u021e\u021f\u0240\u027c\u0296\u0285\u02c7\u02b5\u030d\u0359\u031d\u034f\u03d4\u03b5\u0395\u03b5\u03d2\u045c\u042b\u041c"), true);
    public final BooleanSetting hoverSounds = new BooleanSetting(Deobf.decrypt(";C>\u000b`"), Deobf.decrypt(" C.\u001a2\u00b0\u00a5\u0089\u00a2\u0108\u0149\u0114\u012b\u0195\u01fd\u0194\u01f6\u0214\u0207\u0250\u022e\u0290\u029b\u02c5\u02fb\u031b\u0315\u0319\u034c\u03d5\u03fb\u0382\u03aa"), true);
    public final BooleanSetting clickSounds = new BooleanSetting(Deobf.decrypt("0@!\ry\u00b7\u00ec\u00cc\u00e9\u012f\u0106\u0104\u0124\u019c\u01f6\u01c7"), Deobf.decrypt("#C8\u001d2\u00a2\u00a3\u0098\u00e9\u010f\u0106\u0104\u0124\u019c\u01f6\u01c7\u01b2\u025b\u0202\u0259\u0235\u029d\u0290\u02d0\u02a8\u035e\u0318\u0312\u0345\u0390\u03fe\u0393\u03a0\u03df\u0446\u0420\u040b\u047e"), true);
    public final BooleanSetting notificationSounds = new BooleanSetting(Deobf.decrypt("=C<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d\u0130"), Deobf.decrypt("0D!\u0003w\u00b7\u00ec\u009d\u00a0\u010f\u0101\u0143\u0137\u019f\u01f4\u01d3\u01f2\u021e\u0251\u0241\u0233\u0298\u0286\u02d6\u02a8"), true);
    public final BooleanSetting startup67 = new BooleanSetting(Deobf.decrypt("E\u001b"), Deobf.decrypt(" X)\u001cf\u00b1\u00bc\u00ca\u00bd\u0109\u0108\u0100\u0128\u01d0\u01f0\u01d5\u01f0\u021f\u0218\u0251\u023d\u028d\u0290"), true);
    public final BooleanSetting startupSad = new BooleanSetting(Deobf.decrypt("E\u001bh=s\u00a0\u00ec\u00b9\u00a6\u0115\u010e"), Deobf.decrypt(" X)\u001cf\u00b1\u00bc\u00ca\u00bd\u0109\u0108\u0100\u0128\u01d0\u01f0\u01d5\u01f0\u021f\u0218\u0251\u023d\u028d\u0290"), false);
    public final BooleanSetting startupSong = new BooleanSetting(Deobf.decrypt("E\u001bh=}\u00aa\u00ab"), Deobf.decrypt(" X)\u001cf\u00b1\u00bc\u00ca\u00bd\u0109\u0108\u0100\u0128\u01d0\u01f0\u01d5\u01f0\u021f\u0218\u0251\u023d\u028d\u0290"), false);
    public final BooleanSetting startupTiki = new BooleanSetting(Deobf.decrypt("E\u001bh:{\u00af\u00a5\u00ca\u0099\u0113\u0106\u010d\u0128"), Deobf.decrypt(" X)\u001cf\u00b1\u00bc\u00ca\u00bd\u0109\u0108\u0100\u0128\u01d0\u01f0\u01d5\u01f0\u021f\u0218\u0251\u023d\u028d\u0290"), false);
    private final List<Setting<?>> all = List.of(this.masterVolume, this.guiSounds, this.hoverSounds, this.clickSounds, this.notificationSounds, this.startup67, this.startupSad, this.startupSong, this.startupTiki);

    public List<Setting<?>> all() {
        return this.all;
    }

    public float volume() {
        return this.masterVolume.getFloat() / 100.0f;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        for (Setting<?> setting : this.all) {
            json.add(setting.getName(), setting.toJson());
        }
        return json;
    }

    public void fromJson(JsonObject json) {
        for (Setting<?> setting : this.all) {
            if (!json.has(setting.getName())) continue;
            setting.fromJson(json.get(setting.getName()));
        }
    }
}

