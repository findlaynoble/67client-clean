/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.render;

public final class BlurHook {
    private static volatile float override = -1.0f;

    private BlurHook() {
    }

    public static void set(float radius) {
        override = radius;
    }

    public static void clear() {
        override = -1.0f;
    }

    public static int apply(int vanillaValue) {
        float value = override;
        return value < 0.0f ? vanillaValue : Math.round(Math.clamp(value, 0.0f, 10.0f));
    }

    public static boolean isActive() {
        return override >= 0.0f;
    }
}

