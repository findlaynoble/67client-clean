/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.textures.GpuTexture
 *  net.minecraft.class_1044
 *  net.minecraft.class_10868
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3298
 *  org.lwjgl.nanovg.NanoVG
 *  org.lwjgl.nanovg.NanoVGGL3
 *  org.lwjgl.system.MemoryUtil
 */
package dev.sixseven.render.nanovg;

import com.mojang.blaze3d.textures.GpuTexture;
import dev.sixseven.SixSevenClient;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.class_1044;
import net.minecraft.class_10868;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.system.MemoryUtil;

public final class NVGImages {
    private static final Map<class_2960, Integer> RESOURCE_CACHE = new HashMap<class_2960, Integer>();
    private static final Map<Integer, Integer> GL_HANDLE_CACHE = new HashMap<Integer, Integer>();
    private static int fileImageHandle = -1;
    private static int fileImageVersion = -1;
    private static Path fileImagePath;

    private NVGImages() {
    }

    public static int fromResource(class_2960 location) {
        return RESOURCE_CACHE.computeIfAbsent(location, NVGImages::loadResource);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    private static int loadResource(class_2960 location) {
        long ctx = NVGRenderer.get().ctx();
        Optional resource = class_310.method_1551().method_1478().method_14486(location);
        if (resource.isEmpty()) {
            return -1;
        }
        try (InputStream in = ((class_3298)resource.get()).method_14482();){
            int n;
            byte[] bytes = in.readAllBytes();
            ByteBuffer buffer = MemoryUtil.memAlloc((int)bytes.length);
            try {
                buffer.put(bytes).flip();
                n = NanoVG.nvgCreateImageMem((long)ctx, (int)32, (ByteBuffer)buffer);
            }
            catch (Throwable throwable) {
                MemoryUtil.memFree((Buffer)buffer);
                throw throwable;
            }
            MemoryUtil.memFree((Buffer)buffer);
            return n;
        }
        catch (Exception e) {
            SixSevenClient.LOGGER.warn(Deobf.decrypt("5M!\u0002w\u00a0\u00ec\u009e\u00a6\u015b\u0105\u010c\u0122\u0194\u01b3\u01dd\u01f3\u021a\u0216\u0250\u027c\u0282\u0288"), (Object)location, (Object)e);
            return -1;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int fromFile(Path path, int version) {
        if (version == fileImageVersion && path.equals(fileImagePath)) {
            return fileImageHandle;
        }
        long ctx = NVGRenderer.get().ctx();
        if (fileImageHandle > 0) {
            NanoVG.nvgDeleteImage((long)ctx, (int)fileImageHandle);
            fileImageHandle = -1;
        }
        fileImageVersion = version;
        fileImagePath = path;
        try {
            byte[] bytes = Files.readAllBytes(path);
            ByteBuffer buffer = MemoryUtil.memAlloc((int)bytes.length);
            try {
                buffer.put(bytes).flip();
                fileImageHandle = NanoVG.nvgCreateImageMem((long)ctx, (int)0, (ByteBuffer)buffer);
            }
            finally {
                MemoryUtil.memFree((Buffer)buffer);
            }
        }
        catch (Exception e) {
            fileImageHandle = -1;
        }
        return fileImageHandle;
    }

    public static int wrapGlTexture(class_2960 textureId, int width, int height) {
        GpuTexture gpuTexture;
        class_1044 texture = class_310.method_1551().method_1531().method_4619(textureId);
        if (texture == null || !((gpuTexture = texture.method_68004()) instanceof class_10868)) {
            return -1;
        }
        class_10868 glTexture = (class_10868)gpuTexture;
        int glId = glTexture.method_68427();
        return GL_HANDLE_CACHE.computeIfAbsent(glId, id -> NanoVGGL3.nvglCreateImageFromHandle((long)NVGRenderer.get().ctx(), (int)id, (int)width, (int)height, (int)65536));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int createDynamic(int width, int height) {
        long ctx = NVGRenderer.get().ctx();
        ByteBuffer zero = MemoryUtil.memCalloc((int)(width * height * 4));
        try {
            int n = NanoVG.nvgCreateImageRGBA((long)ctx, (int)width, (int)height, (int)32, (ByteBuffer)zero);
            return n;
        }
        finally {
            MemoryUtil.memFree((Buffer)zero);
        }
    }

    public static void updateDynamic(int handle, ByteBuffer rgba) {
        if (handle <= 0) {
            return;
        }
        NanoVG.nvgUpdateImage((long)NVGRenderer.get().ctx(), (int)handle, (ByteBuffer)rgba);
    }

    public static void deleteImage(int handle) {
        if (handle <= 0) {
            return;
        }
        NanoVG.nvgDeleteImage((long)NVGRenderer.get().ctx(), (int)handle);
    }

    public static void drawSubImage(NVGRenderer vg, int image, float texW, float texH, float u0, float v0, float u1, float v1, float x, float y, float w, float h, float alpha) {
        if (image <= 0) {
            return;
        }
        float scaleX = w / (u1 - u0);
        float scaleY = h / (v1 - v0);
        vg.imagePattern(image, x - u0 * scaleX, y - v0 * scaleY, texW * scaleX, texH * scaleY, x, y, w, h, alpha);
    }
}

