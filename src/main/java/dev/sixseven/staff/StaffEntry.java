/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.staff;

public record StaffEntry(String name, String rankLabel, int color, boolean vanished, int latency, int priority) {
    public boolean hasColor() {
        return (this.color & 0xFFFFFF) != 0;
    }
}

