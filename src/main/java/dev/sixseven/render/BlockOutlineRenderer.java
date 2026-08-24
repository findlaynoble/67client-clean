/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_12074
 *  net.minecraft.class_12249
 *  net.minecraft.class_238
 *  net.minecraft.class_243
 *  net.minecraft.class_265
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_9974
 */
package dev.sixseven.render;

import dev.sixseven.module.Modules;
import dev.sixseven.rt.Deobf;
import dev.sixseven.util.Colors;
import net.minecraft.class_12074;
import net.minecraft.class_12249;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_265;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import net.minecraft.class_9974;

public final class BlockOutlineRenderer {
    private BlockOutlineRenderer() {
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_12074 state, class_243 camera, Modules.BlockOutlineModule module) {
        double dx = (double)state.comp_4932().method_10263() - camera.field_1352;
        double dy = (double)state.comp_4932().method_10264() - camera.field_1351;
        double dz = (double)state.comp_4932().method_10260() - camera.field_1350;
        double seconds = (double)(System.nanoTime() % 1000000000000L) / 1.0E9;
        int color = BlockOutlineRenderer.animatedColor(module, seconds);
        float pulse = BlockOutlineRenderer.pulseFactor(module, seconds);
        float fill = module.fillOpacity.getFloat() / 100.0f;
        if (fill > 0.004f) {
            class_4588 quads = bufferSource.method_73477(class_12249.method_76023());
            int fillColor = Colors.withAlpha(color, fill * (0.75f + 0.25f * pulse));
            for (class_238 box : state.comp_4935().method_1090()) {
                BlockOutlineRenderer.emitBox(poseStack, quads, box.method_1014(-0.002).method_989(dx, dy, dz), fillColor);
            }
            bufferSource.method_37104();
        }
        float thickness = module.thickness.getFloat();
        float glow = module.glow.getFloat() / 100.0f;
        if (glow > 0.02f) {
            class_4588 halo = bufferSource.method_73477(class_12249.method_76017());
            class_9974.method_62296((class_4587)poseStack, (class_4588)halo, (class_265)state.comp_4935(), (double)dx, (double)dy, (double)dz, (int)Colors.withAlpha(color, (0.16f + 0.22f * pulse) * glow), (float)(thickness * 3.2f));
            bufferSource.method_37104();
            class_4588 mid = bufferSource.method_73477(class_12249.method_76015());
            class_9974.method_62296((class_4587)poseStack, (class_4588)mid, (class_265)state.comp_4935(), (double)dx, (double)dy, (double)dz, (int)Colors.withAlpha(color, (0.3f + 0.25f * pulse) * glow), (float)(thickness * 2.0f));
            bufferSource.method_37104();
        }
        class_4588 core = bufferSource.method_73477(class_12249.method_76015());
        class_9974.method_62296((class_4587)poseStack, (class_4588)core, (class_265)state.comp_4935(), (double)dx, (double)dy, (double)dz, (int)Colors.withAlpha(color, 0.85f + 0.15f * pulse), (float)thickness);
        bufferSource.method_37104();
    }

    private static int animatedColor(Modules.BlockOutlineModule module, double seconds) {
        if (((Boolean)module.rainbow.get()).booleanValue()) {
            return Colors.hsvToRgb((float)(seconds * 42.0 % 360.0), 0.75f, 1.0f);
        }
        int base = (Integer)module.color.get() | 0xFF000000;
        if (module.animation.is(Deobf.decrypt("4^)\n{\u00a1\u00a2\u009e\u00e9\u013d\u0105\u010c\u0134"))) {
            float[] hsv = Colors.rgbToHsv(base);
            float hue = (hsv[0] + (float)(Math.sin(seconds * 1.6) * 28.0)) % 360.0f;
            if (hue < 0.0f) {
                hue += 360.0f;
            }
            return Colors.hsvToRgb(hue, Math.max(0.4f, hsv[1]), hsv[2]);
        }
        return base;
    }

    private static float pulseFactor(Modules.BlockOutlineModule module, double seconds) {
        if (module.animation.is(Deobf.decrypt("#Y$\u001dw"))) {
            return (float)(0.5 + 0.5 * Math.sin(seconds * Math.PI * 2.0 / 1.6));
        }
        return 1.0f;
    }

    private static void emitBox(class_4587 poseStack, class_4588 buffer, class_238 b, int color) {
        class_4587.class_4665 pose = poseStack.method_23760();
        float x0 = (float)b.field_1323;
        float y0 = (float)b.field_1322;
        float z0 = (float)b.field_1321;
        float x1 = (float)b.field_1320;
        float y1 = (float)b.field_1325;
        float z1 = (float)b.field_1324;
        BlockOutlineRenderer.quad(buffer, pose, color, x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1);
        BlockOutlineRenderer.quad(buffer, pose, color, x0, y1, z0, x0, y1, z1, x1, y1, z1, x1, y1, z0);
        BlockOutlineRenderer.quad(buffer, pose, color, x0, y0, z0, x0, y1, z0, x1, y1, z0, x1, y0, z0);
        BlockOutlineRenderer.quad(buffer, pose, color, x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1);
        BlockOutlineRenderer.quad(buffer, pose, color, x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0);
        BlockOutlineRenderer.quad(buffer, pose, color, x1, y0, z0, x1, y1, z0, x1, y1, z1, x1, y0, z1);
    }

    private static void quad(class_4588 buffer, class_4587.class_4665 pose, int color, float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz, float dx2, float dy2, float dz2) {
        buffer.method_56824(pose, ax, ay, az).method_39415(color);
        buffer.method_56824(pose, bx, by, bz).method_39415(color);
        buffer.method_56824(pose, cx, cy, cz).method_39415(color);
        buffer.method_56824(pose, dx2, dy2, dz2).method_39415(color);
    }
}

