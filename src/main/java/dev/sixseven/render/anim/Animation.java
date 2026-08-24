/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.render.anim;

import dev.sixseven.render.anim.Easing;

public class Animation {
    private final float durationMs;
    private final Easing easing;
    private float from;
    private float target;
    private long startNanos;

    public Animation(float durationMs, float initial) {
        this(durationMs, initial, Easing.EASE_OUT_CUBIC);
    }

    public Animation(float durationMs, float initial, Easing easing) {
        this.durationMs = durationMs;
        this.easing = easing;
        this.from = initial;
        this.target = initial;
        this.startNanos = System.nanoTime();
    }

    public void setTarget(float newTarget) {
        if (newTarget == this.target) {
            return;
        }
        this.from = this.value();
        this.target = newTarget;
        this.startNanos = System.nanoTime();
    }

    public void snapTo(float v) {
        this.from = v;
        this.target = v;
    }

    public float getTarget() {
        return this.target;
    }

    public float value() {
        float t = (float)(System.nanoTime() - this.startNanos) / 1000000.0f / this.durationMs;
        if (t >= 1.0f) {
            return this.target;
        }
        return this.from + (this.target - this.from) * this.easing.apply(Math.max(t, 0.0f));
    }

    public boolean isDone() {
        return (float)(System.nanoTime() - this.startNanos) / 1000000.0f >= this.durationMs;
    }
}

