/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.util;

public final class TpsTracker {
    private static long lastPacketNanos = -1L;
    private static float tps = 20.0f;

    private TpsTracker() {
    }

    public static void onTimePacket() {
        float seconds;
        long now = System.nanoTime();
        if (lastPacketNanos > 0L && (seconds = (float)(now - lastPacketNanos) / 1.0E9f) > 0.05f) {
            float measured = Math.clamp(20.0f / seconds, 0.0f, 20.0f);
            tps = tps * 0.7f + measured * 0.3f;
        }
        lastPacketNanos = now;
    }

    public static void reset() {
        lastPacketNanos = -1L;
        tps = 20.0f;
    }

    public static float get() {
        return tps;
    }
}

