/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2378
 *  net.minecraft.class_2960
 *  net.minecraft.class_3414
 *  net.minecraft.class_7923
 */
package dev.sixseven.util;

import dev.sixseven.rt.Deobf;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_7923;

public final class UiSoundEvents {
    public static final class_3414 GUI_OPEN = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\tg\u00ad\u0093\u0085\u00b9\u011e\u0107"));
    public static final class_3414 GUI_CLOSE = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\tg\u00ad\u0093\u0089\u00a5\u0114\u011a\u0106"));
    public static final class_3414 HOVER = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u0006}\u00b2\u00a9\u0098"));
    public static final class_3414 TOGGLE_ON = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u001a}\u00a3\u00ab\u0086\u00ac\u0124\u0106\u010d"));
    public static final class_3414 TOGGLE_OFF = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u001a}\u00a3\u00ab\u0086\u00ac\u0124\u0106\u0105\u0125"));
    public static final class_3414 SLIDER = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u001d~\u00ad\u00a8\u008f\u00bb"));
    public static final class_3414 SELECT = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u001dw\u00a8\u00a9\u0089\u00bd"));
    public static final class_3414 KEYBIND = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u0005w\u00bd\u00ae\u0083\u00a7\u011f"));
    public static final class_3414 NOTIFY_ON = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u0000}\u00b0\u00a5\u008c\u00b0\u0124\u0106\u010d"));
    public static final class_3414 NOTIFY_OFF = UiSoundEvents.register(Deobf.decrypt("\u0006Ef\u0000}\u00b0\u00a5\u008c\u00b0\u0124\u0106\u0105\u0125"));
    public static final class_3414 STARTUP_SAD = UiSoundEvents.register(Deobf.decrypt("\u0000X)\u001cf\u00b1\u00bc\u00c4\u00ba\u011a\u010d"));
    public static final class_3414 STARTUP_SONG = UiSoundEvents.register(Deobf.decrypt("\u0000X)\u001cf\u00b1\u00bc\u00c4\u00ba\u0114\u0107\u0104"));
    public static final class_3414 STARTUP_TIKI = UiSoundEvents.register(Deobf.decrypt("\u0000X)\u001cf\u00b1\u00bc\u00c4\u00bd\u0112\u0102\u010a"));
    public static final class_3414 STARTUP_67 = UiSoundEvents.register(Deobf.decrypt("\u0000X)\u001cf\u00b1\u00bc\u00c4\u00ff\u014c"));

    private UiSoundEvents() {
    }

    private static class_3414 register(String name) {
        class_2960 id = class_2960.method_60655((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184"), (String)name);
        return (class_3414)class_2378.method_10230((class_2378)class_7923.field_41172, (class_2960)id, (Object)class_3414.method_47908((class_2960)id));
    }

    public static void bootstrap() {
    }
}

