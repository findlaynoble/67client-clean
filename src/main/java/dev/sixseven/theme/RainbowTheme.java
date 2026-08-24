/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.theme;

import dev.sixseven.rt.Deobf;
import dev.sixseven.theme.Theme;
import dev.sixseven.util.Colors;

public class RainbowTheme
extends Theme {
    public RainbowTheme() {
        super(Deobf.decrypt("!M!\u0000p\u00ab\u00bb"), -49508, false);
    }

    @Override
    public int accent() {
        double seconds = (double)(System.nanoTime() % 1000000000000L) / 1.0E9;
        return Colors.hsvToRgb((float)(seconds * 36.0 % 360.0), 0.72f, 1.0f);
    }
}

