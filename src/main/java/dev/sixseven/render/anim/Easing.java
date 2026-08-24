/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.render.anim;

public enum Easing {
    LINEAR{

        @Override
        public float apply(float t) {
            return t;
        }
    }
    ,
    EASE_OUT_CUBIC{

        @Override
        public float apply(float t) {
            float inv = 1.0f - t;
            return 1.0f - inv * inv * inv;
        }
    }
    ,
    EASE_IN_OUT_QUAD{

        @Override
        public float apply(float t) {
            return t < 0.5f ? 2.0f * t * t : 1.0f - (float)Math.pow(-2.0f * t + 2.0f, 2.0) / 2.0f;
        }
    };


    public abstract float apply(float var1);
}

