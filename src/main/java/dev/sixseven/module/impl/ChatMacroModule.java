/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_408
 *  net.minecraft.class_437
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.KeybindSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.StringSetting;
import net.minecraft.class_310;
import net.minecraft.class_408;
import net.minecraft.class_437;

public class ChatMacroModule
extends Module {
    private static final int SLOTS = 3;
    public final ModeSetting slot = this.addSetting(new ModeSetting(Deobf.decrypt(" @'\u001a"), Deobf.decrypt(">M+\u001c}\u00e4\u00bf\u0086\u00a6\u010f\u0149\u0117\u012c\u01d0\u01f6\u01d0\u01f7\u020f\u025f"), Deobf.decrypt("B"), Deobf.decrypt("B"), Deobf.decrypt("A"), Deobf.decrypt("@")));
    public final BooleanSetting sendInstantly = this.addSetting(new BooleanSetting(Deobf.decrypt(" I&\n2\u008d\u00a2\u0099\u00bd\u011a\u0107\u0117\u012f\u0189"), Deobf.decrypt(" G!\u001e2\u00b0\u00a4\u008f\u00e9\u0118\u0101\u0102\u0137\u01d0\u01e3\u01c6\u01fb\u020d\u0218\u0250\u022b\u02d9\u0294\u02cc\u02bf\u035e\u030a\u0319\u034f\u03d4\u03b5\u039f\u03b4\u03d0\u044a\u042a\u0406\u046c\u04dd\u04b2\u04eb\u04f8\u050b"), true));
    private final StringSetting[] messages = new StringSetting[3];
    private final KeybindSetting[] keys = new KeybindSetting[3];

    public ChatMacroModule() {
        super(Deobf.decrypt("0D)\u001a_\u00a5\u00af\u0098\u00a6"), Deobf.decrypt("1E&\ns\u00a6\u00a0\u008f\u00e9\u0118\u0101\u0102\u0137\u01d0\u01f0\u01db\u01f3\u0216\u0210\u025b\u0238\u02d9\u0298\u02c3\u02b8\u030c\u0316\u030f\u030f"), Category.CLIENT);
        for (int i = 0; i < 3; ++i) {
            String num = Integer.toString(i + 1);
            this.messages[i] = this.addSetting(new StringSetting("Message " + num, "Message or /command for slot " + num, Deobf.decrypt(""), 256, Deobf.decrypt("\\_)\u00172\u00ac\u00a5")));
            this.keys[i] = this.addSetting(new KeybindSetting("Key " + num, "Key that runs slot " + num, -1));
            this.messages[i].visibleWhen(() -> this.slot.is(num));
            this.keys[i].visibleWhen(() -> this.slot.is(num));
        }
    }

    @Override
    public boolean onKeyPress(int keyCode) {
        boolean handled = false;
        for (int i = 0; i < 3; ++i) {
            if (!this.keys[i].matches(keyCode)) continue;
            this.run((String)this.messages[i].get());
            handled = true;
        }
        return handled;
    }

    private void run(String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || mc.field_1724.field_3944 == null) {
            return;
        }
        if (!((Boolean)this.sendInstantly.get()).booleanValue()) {
            mc.method_1507((class_437)new class_408(message, false));
            return;
        }
        if (message.startsWith(Deobf.decrypt("\\"))) {
            mc.field_1724.field_3944.method_45730(message.substring(1));
        } else {
            mc.field_1724.field_3944.method_45729(message);
        }
    }
}

