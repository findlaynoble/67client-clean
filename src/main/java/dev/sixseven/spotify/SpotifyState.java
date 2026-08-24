/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.spotify;

import dev.sixseven.rt.Deobf;

public record SpotifyState(boolean active, String title, String artist, long posMs, long durMs, boolean playing, boolean canSeek, int artVersion, int volume, long receivedNanos) {
    public static final SpotifyState INACTIVE = new SpotifyState(false, Deobf.decrypt(""), Deobf.decrypt(""), 0L, 0L, false, false, 0, -1, 0L);

    public long livePosMs() {
        if (!this.playing || this.durMs <= 0L) {
            return this.posMs;
        }
        long elapsed = (System.nanoTime() - this.receivedNanos) / 1000000L;
        return Math.min(this.posMs + elapsed, this.durMs);
    }
}

