/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.hud;

import dev.sixseven.render.nanovg.NVGRenderer;
import java.util.function.BooleanSupplier;

public abstract class HudComponent {
    public static final float MIN_SCALE = 0.5f;
    public static final float MAX_SCALE = 2.0f;
    private final String id;
    private final BooleanSupplier visibility;
    private float fx;
    private float fy;
    private float scale = 1.0f;

    protected HudComponent(String id, float defaultFx, float defaultFy, BooleanSupplier visibility) {
        this.id = id;
        this.fx = defaultFx;
        this.fy = defaultFy;
        this.visibility = visibility;
    }

    public String getId() {
        return this.id;
    }

    public float getFx() {
        return this.fx;
    }

    public float getFy() {
        return this.fy;
    }

    public void setPosition(float fx, float fy) {
        this.fx = Math.clamp(fx, 0.0f, 1.0f);
        this.fy = Math.clamp(fy, 0.0f, 1.0f);
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = Math.clamp(scale, 0.5f, 2.0f);
    }

    public final boolean visible() {
        return this.visibility.getAsBoolean();
    }

    public abstract float measureWidth(NVGRenderer var1);

    public abstract float measureHeight(NVGRenderer var1);

    public abstract void render(NVGRenderer var1, float var2, float var3, float var4, float var5);

    public boolean onEditClick(float localX, float localY) {
        return false;
    }

    public boolean rightAnchored() {
        return this.fx > 0.5f;
    }
}

