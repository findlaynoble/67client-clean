/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderPipeline
 *  com.mojang.blaze3d.pipeline.RenderPipeline$Snippet
 *  com.mojang.blaze3d.platform.DepthTestFunction
 *  net.minecraft.class_10799
 *  net.minecraft.class_12247
 *  net.minecraft.class_1921
 *  net.minecraft.class_243
 *  net.minecraft.class_4587
 *  net.minecraft.class_4587$class_4665
 *  net.minecraft.class_4588
 *  net.minecraft.class_4597$class_4598
 *  org.joml.Vector3f
 */
package dev.sixseven.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import dev.sixseven.rt.Deobf;
import dev.sixseven.util.Colors;
import net.minecraft.class_10799;
import net.minecraft.class_12247;
import net.minecraft.class_1921;
import net.minecraft.class_243;
import net.minecraft.class_4587;
import net.minecraft.class_4588;
import net.minecraft.class_4597;
import org.joml.Vector3f;

public final class FlatOverlay {
    private static final RenderPipeline FILL_PIPELINE = RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{class_10799.field_56860}).withLocation(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01bc\u01c4\u01f7\u020b\u0214\u0259\u0235\u0297\u0290\u028d\u02bd\u0312\u0318\u0308\u037e\u03d6\u03fc\u039a\u03b5")).withCull(false).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build();
    public static final class_1921 FILL = class_1921.method_75940((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01a9\u01d2\u01f2\u021a\u0205\u026a\u023a\u0290\u0299\u02ce"), (class_12247)class_12247.method_75927((RenderPipeline)FILL_PIPELINE).method_75937().method_75938());
    private static final RenderPipeline LINE_PIPELINE = RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{class_10799.field_56859}).withLocation(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01bc\u01c4\u01f7\u020b\u0214\u0259\u0235\u0297\u0290\u028d\u02bd\u0312\u0318\u0308\u037e\u03dc\u03fc\u0398\u03bc\u03ce")).withDepthWrite(false).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build();
    public static final class_1921 LINES = class_1921.method_75940((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184\u01a9\u01d2\u01f2\u021a\u0205\u026a\u0230\u0290\u029b\u02c7\u02a8"), (class_12247)class_12247.method_75927((RenderPipeline)LINE_PIPELINE).method_75938());

    private FlatOverlay() {
    }

    public static void fillQuad(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, double x0, double z0, double x1, double z1, double y, int color) {
        class_4588 buffer = bufferSource.method_73477(FILL);
        class_4587.class_4665 pose = poseStack.method_23760();
        float ax = (float)(x0 - cam.field_1352);
        float az = (float)(z0 - cam.field_1350);
        float bx = (float)(x1 - cam.field_1352);
        float bz = (float)(z1 - cam.field_1350);
        float fy = (float)(y - cam.field_1351);
        buffer.method_56824(pose, ax, fy, az).method_39415(color);
        buffer.method_56824(pose, ax, fy, bz).method_39415(color);
        buffer.method_56824(pose, bx, fy, bz).method_39415(color);
        buffer.method_56824(pose, bx, fy, az).method_39415(color);
    }

    public static void edge(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, double x0, double z0, double x1, double z1, double y, int color, float width) {
        class_4588 buffer = bufferSource.method_73477(LINES);
        class_4587.class_4665 pose = poseStack.method_23760();
        Vector3f normal = new Vector3f((float)(x1 - x0), 0.0f, (float)(z1 - z0)).normalize();
        buffer.method_56824(pose, (float)(x0 - cam.field_1352), (float)(y - cam.field_1351), (float)(z0 - cam.field_1350)).method_39415(color).method_61959(pose, normal).method_75298(width);
        buffer.method_56824(pose, (float)(x1 - cam.field_1352), (float)(y - cam.field_1351), (float)(z1 - cam.field_1350)).method_39415(color).method_61959(pose, normal).method_75298(width);
    }

    public static void marker(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, double x, double z, double y, double r, int color) {
        FlatOverlay.fillQuadRot(bufferSource, poseStack, cam, x, z, y, r, color);
        int outline = Colors.withAlpha(color, 1.0f);
        FlatOverlay.edge(bufferSource, poseStack, cam, x - r, z, x, z - r, y, outline, 2.0f);
        FlatOverlay.edge(bufferSource, poseStack, cam, x, z - r, x + r, z, y, outline, 2.0f);
        FlatOverlay.edge(bufferSource, poseStack, cam, x + r, z, x, z + r, y, outline, 2.0f);
        FlatOverlay.edge(bufferSource, poseStack, cam, x, z + r, x - r, z, y, outline, 2.0f);
    }

    private static void fillQuadRot(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, double cx, double cz, double y, double r, int color) {
        class_4588 buffer = bufferSource.method_73477(FILL);
        class_4587.class_4665 pose = poseStack.method_23760();
        float fy = (float)(y - cam.field_1351);
        buffer.method_56824(pose, (float)(cx - r - cam.field_1352), fy, (float)(cz - cam.field_1350)).method_39415(color);
        buffer.method_56824(pose, (float)(cx - cam.field_1352), fy, (float)(cz - r - cam.field_1350)).method_39415(color);
        buffer.method_56824(pose, (float)(cx + r - cam.field_1352), fy, (float)(cz - cam.field_1350)).method_39415(color);
        buffer.method_56824(pose, (float)(cx - cam.field_1352), fy, (float)(cz + r - cam.field_1350)).method_39415(color);
    }

    public static void flush(class_4597.class_4598 bufferSource) {
        bufferSource.method_22994(FILL);
        bufferSource.method_22994(LINES);
    }
}

