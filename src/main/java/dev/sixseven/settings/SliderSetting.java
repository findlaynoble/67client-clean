/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 */
package dev.sixseven.settings;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.Setting;
import java.util.function.DoubleFunction;

public class SliderSetting
extends Setting<Double> {
    private final double min;
    private final double max;
    private final double step;
    private final String suffix;
    private DoubleFunction<String> labelFn;

    public SliderSetting(String name, String description, double defaultValue, double min, double max, double step) {
        this(name, description, defaultValue, min, max, step, Deobf.decrypt(""));
    }

    public SliderSetting(String name, String description, double defaultValue, double min, double max, double step, String suffix) {
        super(name, description, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
        this.suffix = suffix;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public float getFloat() {
        return ((Double)this.get()).floatValue();
    }

    public int getInt() {
        return (int)Math.round((Double)this.get());
    }

    public double getNormalized() {
        return ((Double)this.get() - this.min) / (this.max - this.min);
    }

    public void setNormalized(double t) {
        this.set(this.min + (this.max - this.min) * Math.clamp(t, 0.0, 1.0));
    }

    @Override
    public void set(Double newValue) {
        double snapped = (double)Math.round(newValue / this.step) * this.step;
        snapped = (double)Math.round(snapped * 1000000.0) / 1000000.0;
        super.set(Math.clamp(snapped, this.min, this.max));
    }

    public SliderSetting withLabel(DoubleFunction<String> labelFn) {
        this.labelFn = labelFn;
        return this;
    }

    public String formatValue() {
        String s;
        if (this.labelFn != null) {
            return this.labelFn.apply((Double)this.get());
        }
        double v = (Double)this.get();
        if (this.step >= 1.0 && v == Math.floor(v)) {
            s = Long.toString((long)v);
        } else {
            s = String.valueOf((double)Math.round(v * 100.0) / 100.0);
            if (s.endsWith(Deobf.decrypt("]\u001c"))) {
                s = s.substring(0, s.length() - 2);
            }
        }
        return s + this.suffix;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive((Number)this.value);
    }

    @Override
    public void fromJson(JsonElement element) {
        if (element != null && element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            this.set(element.getAsDouble());
        }
    }
}

