/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1923
 *  net.minecraft.class_243
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 */
package dev.sixseven.render;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.impl.ChunkFinderModule;
import dev.sixseven.render.FlatOverlay;
import dev.sixseven.util.Colors;
import net.minecraft.class_1923;
import net.minecraft.class_243;
import net.minecraft.class_4587;
import net.minecraft.class_4597;

public final class ChunkFinderRenderer {
    private static final float RENDER_Y = 55.0f;
    private static final int FILL_ALPHA = 190;

    private ChunkFinderRenderer() {
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 camera, ChunkFinderModule module) {
        if (module.flaggedChunks().isEmpty()) {
            return;
        }
        int accent = SixSevenClient.themes().current().accent();
        int fill = Colors.withAlpha(Colors.darken(accent, 0.58f), 190);
        int outline = Colors.withAlpha(accent, 255);
        for (long key : module.flaggedChunks()) {
            double x0 = (double)class_1923.method_8325((long)key) * 16.0;
            double z0 = (double)class_1923.method_8332((long)key) * 16.0;
            double x1 = x0 + 16.0;
            double z1 = z0 + 16.0;
            FlatOverlay.fillQuad(bufferSource, poseStack, camera, x0, z0, x1, z1, 55.0, fill);
            FlatOverlay.edge(bufferSource, poseStack, camera, x0, z0, x1, z0, 55.0, outline, 2.0f);
            FlatOverlay.edge(bufferSource, poseStack, camera, x1, z0, x1, z1, 55.0, outline, 2.0f);
            FlatOverlay.edge(bufferSource, poseStack, camera, x1, z1, x0, z1, 55.0, outline, 2.0f);
            FlatOverlay.edge(bufferSource, poseStack, camera, x0, z1, x0, z0, 55.0, outline, 2.0f);
        }
        FlatOverlay.flush(bufferSource);
    }
}

