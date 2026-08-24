/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.nanovg.NSVGImage
 *  org.lwjgl.nanovg.NanoSVG
 *  org.lwjgl.nanovg.NanoVG
 *  org.lwjgl.system.MemoryUtil
 */
package dev.sixseven.render.nanovg;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.render.nanovg.NVGRenderer;
import dev.sixseven.rt.Deobf;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.nanovg.NSVGImage;
import org.lwjgl.nanovg.NanoSVG;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;

public final class NVGIcons {
    private static final int RASTER_SIZE = 64;
    private static final Map<String, Integer> ICONS = new HashMap<String, Integer>();
    private static boolean loaded;

    private NVGIcons() {
    }

    public static int get(Category category) {
        NVGIcons.ensureLoaded();
        return ICONS.getOrDefault(category.getIconId(), -1);
    }

    private static void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        for (Category category : Category.values()) {
            int handle = NVGIcons.loadSvg("assets/sixsevenclient/icons/" + category.getIconId() + ".svg");
            if (handle <= 0) continue;
            ICONS.put(category.getIconId(), handle);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    private static int loadSvg(String resourcePath) {
        long ctx = NVGRenderer.get().ctx();
        try (InputStream in = NVGIcons.class.getClassLoader().getResourceAsStream(resourcePath);){
            int n;
            long rasterizer;
            NSVGImage image;
            ByteBuffer units;
            ByteBuffer svgData;
            block24: {
                ByteBuffer pixels;
                block22: {
                    int n2;
                    block23: {
                        if (in == null) {
                            SixSevenClient.LOGGER.warn(Deobf.decrypt(":O'\u00002\u00b6\u00a9\u0099\u00a6\u010e\u011b\u0100\u0126\u01d0\u01fe\u01dd\u01ed\u0208\u0218\u025b\u023b\u02c3\u02d5\u02d9\u02a6"), (Object)resourcePath);
                            int n3 = -1;
                            return n3;
                        }
                        String svg = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                        svg = svg.replaceAll(Deobf.decrypt("PwxC+\u00a5\u00e1\u008c\u0088\u0156\u012f\u013e\u0138\u01c6\u01ee"), Deobf.decrypt("PJ.\bt\u00a2\u00aa")).replaceAll(Deobf.decrypt("PwxC+\u00a5\u00e1\u008c\u0088\u0156\u012f\u013e\u0138\u01c3\u01ee\u01e8\u01fc"), Deobf.decrypt("PJ.\b")).replace(Deobf.decrypt("\u0010Y:\u001cw\u00aa\u00b8\u00a9\u00a6\u0117\u0106\u0111"), Deobf.decrypt("PJ.\bt\u00a2\u00aa")).replace(Deobf.decrypt("QN$\u000fq\u00af\u00ee"), Deobf.decrypt("Q[ \u0007f\u00a1\u00ee"));
                        svgData = MemoryUtil.memUTF8((CharSequence)svg, (boolean)true);
                        units = MemoryUtil.memASCII((CharSequence)Deobf.decrypt("\u0003T"));
                        image = null;
                        rasterizer = 0L;
                        pixels = null;
                        try {
                            image = NanoSVG.nsvgParse((ByteBuffer)svgData, (ByteBuffer)units, (float)96.0f);
                            if (image != null) break block22;
                            n2 = -1;
                            if (pixels == null) break block23;
                        }
                        catch (Throwable throwable) {
                            if (pixels != null) {
                                MemoryUtil.memFree(pixels);
                            }
                            if (rasterizer != 0L) {
                                NanoSVG.nsvgDeleteRasterizer((long)rasterizer);
                            }
                            if (image != null) {
                                NanoSVG.nsvgDelete((NSVGImage)image);
                            }
                            MemoryUtil.memFree((Buffer)svgData);
                            MemoryUtil.memFree((Buffer)units);
                            throw throwable;
                        }
                        MemoryUtil.memFree(pixels);
                    }
                    if (rasterizer != 0L) {
                        NanoSVG.nsvgDeleteRasterizer((long)rasterizer);
                    }
                    if (image != null) {
                        NanoSVG.nsvgDelete((NSVGImage)image);
                    }
                    MemoryUtil.memFree((Buffer)svgData);
                    MemoryUtil.memFree((Buffer)units);
                    return n2;
                }
                rasterizer = NanoSVG.nsvgCreateRasterizer();
                float scale = 64.0f / Math.max(image.width(), image.height());
                pixels = MemoryUtil.memAlloc((int)16384);
                NanoSVG.nsvgRasterize((long)rasterizer, (NSVGImage)image, (float)0.0f, (float)0.0f, (float)scale, (ByteBuffer)pixels, (int)64, (int)64, (int)256);
                n = NanoVG.nvgCreateImageRGBA((long)ctx, (int)64, (int)64, (int)0, (ByteBuffer)pixels);
                if (pixels == null) break block24;
                MemoryUtil.memFree((Buffer)pixels);
            }
            if (rasterizer != 0L) {
                NanoSVG.nsvgDeleteRasterizer((long)rasterizer);
            }
            if (image != null) {
                NanoSVG.nsvgDelete((NSVGImage)image);
            }
            MemoryUtil.memFree((Buffer)svgData);
            MemoryUtil.memFree((Buffer)units);
            return n;
        }
        catch (Exception e) {
            SixSevenClient.LOGGER.error(Deobf.decrypt("5M!\u0002w\u00a0\u00ec\u009e\u00a6\u015b\u011b\u0102\u0130\u0184\u01f6\u01c6\u01f7\u0201\u0214\u0215\u0235\u029a\u029a\u02cc\u02fb\u0305\u0304"), (Object)resourcePath, (Object)e);
            return -1;
        }
    }
}

