/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.util;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CpsTracker {
    private static final Deque<Long> LEFT = new ArrayDeque<Long>();
    private static final Deque<Long> RIGHT = new ArrayDeque<Long>();

    private CpsTracker() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onClick(int button) {
        Deque<Long> deque;
        Deque<Long> deque2 = button == 0 ? LEFT : (deque = button == 1 ? RIGHT : null);
        if (deque != null) {
            Deque<Long> deque3 = deque;
            synchronized (deque3) {
                deque.addLast(System.nanoTime());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int get(int button) {
        Deque<Long> deque = button == 0 ? LEFT : RIGHT;
        long cutoff = System.nanoTime() - 1000000000L;
        Deque<Long> deque2 = deque;
        synchronized (deque2) {
            while (!deque.isEmpty() && deque.peekFirst() < cutoff) {
                deque.pollFirst();
            }
            return deque.size();
        }
    }
}

