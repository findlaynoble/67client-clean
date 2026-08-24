/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_310
 *  net.minecraft.class_3414
 */
package dev.sixseven.util;

import dev.sixseven.theme.SoundSettings;
import dev.sixseven.util.UiSoundEvents;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_310;
import net.minecraft.class_3414;

public final class UiSounds {
    private static SoundSettings settings = new SoundSettings();
    private static long lastHoverNanos;
    private static long lastSliderNanos;

    private UiSounds() {
    }

    public static void init(SoundSettings soundSettings) {
        settings = soundSettings;
    }

    private static void play(class_3414 event, float pitch, float volume) {
        float master = settings.volume();
        if (master <= 0.01f) {
            return;
        }
        class_310.method_1551().method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)event, (float)pitch, (float)(volume * master)));
    }

    public static void guiOpen() {
        if (((Boolean)UiSounds.settings.guiSounds.get()).booleanValue()) {
            UiSounds.play(UiSoundEvents.GUI_OPEN, 1.0f, 0.85f);
        }
    }

    public static void guiClose() {
        if (((Boolean)UiSounds.settings.guiSounds.get()).booleanValue()) {
            UiSounds.play(UiSoundEvents.GUI_CLOSE, 1.0f, 0.75f);
        }
    }

    public static void hover() {
        if (!((Boolean)UiSounds.settings.hoverSounds.get()).booleanValue()) {
            return;
        }
        long now = System.nanoTime();
        if (now - lastHoverNanos < 45000000L) {
            return;
        }
        lastHoverNanos = now;
        UiSounds.play(UiSoundEvents.HOVER, 0.95f + (float)(now % 7L) * 0.015f, 0.5f);
    }

    public static void toggle(boolean enabled) {
        if (((Boolean)UiSounds.settings.clickSounds.get()).booleanValue()) {
            UiSounds.play(enabled ? UiSoundEvents.TOGGLE_ON : UiSoundEvents.TOGGLE_OFF, 1.0f, 0.8f);
        }
    }

    public static void checkbox(boolean checked) {
        if (((Boolean)UiSounds.settings.clickSounds.get()).booleanValue()) {
            UiSounds.play(checked ? UiSoundEvents.TOGGLE_ON : UiSoundEvents.TOGGLE_OFF, 1.25f, 0.55f);
        }
    }

    public static void select() {
        if (((Boolean)UiSounds.settings.clickSounds.get()).booleanValue()) {
            UiSounds.play(UiSoundEvents.SELECT, 1.0f, 0.65f);
        }
    }

    public static void sliderTick(float normalized) {
        if (!((Boolean)UiSounds.settings.clickSounds.get()).booleanValue()) {
            return;
        }
        long now = System.nanoTime();
        if (now - lastSliderNanos < 60000000L) {
            return;
        }
        lastSliderNanos = now;
        UiSounds.play(UiSoundEvents.SLIDER, 0.85f + normalized * 0.55f, 0.5f);
    }

    public static void keybindListen() {
        if (((Boolean)UiSounds.settings.clickSounds.get()).booleanValue()) {
            UiSounds.play(UiSoundEvents.KEYBIND, 0.8f, 0.6f);
        }
    }

    public static void keybindSet() {
        if (((Boolean)UiSounds.settings.clickSounds.get()).booleanValue()) {
            UiSounds.play(UiSoundEvents.KEYBIND, 1.1f, 0.6f);
        }
    }

    public static void notification(boolean enabled) {
        if (((Boolean)UiSounds.settings.notificationSounds.get()).booleanValue()) {
            UiSounds.play(enabled ? UiSoundEvents.NOTIFY_ON : UiSoundEvents.NOTIFY_OFF, 1.0f, 0.7f);
        }
    }

    public static void panelCollapse() {
        if (((Boolean)UiSounds.settings.clickSounds.get()).booleanValue()) {
            UiSounds.play(UiSoundEvents.SELECT, 0.8f, 0.5f);
        }
    }

    public static void playStartup() {
        ArrayList<class_3414> pool = new ArrayList<class_3414>();
        if (((Boolean)UiSounds.settings.startup67.get()).booleanValue()) {
            pool.add(UiSoundEvents.STARTUP_67);
        }
        if (((Boolean)UiSounds.settings.startupSad.get()).booleanValue()) {
            pool.add(UiSoundEvents.STARTUP_SAD);
        }
        if (((Boolean)UiSounds.settings.startupSong.get()).booleanValue()) {
            pool.add(UiSoundEvents.STARTUP_SONG);
        }
        if (((Boolean)UiSounds.settings.startupTiki.get()).booleanValue()) {
            pool.add(UiSoundEvents.STARTUP_TIKI);
        }
        if (pool.isEmpty()) {
            return;
        }
        class_3414 pick = (class_3414)pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
        UiSounds.play(pick, 1.0f, 0.9f);
    }
}

