/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.textures.GpuTexture
 *  net.minecraft.class_1011
 *  net.minecraft.class_1044
 *  net.minecraft.class_1060
 *  net.minecraft.class_2960
 *  net.minecraft.class_310
 *  net.minecraft.class_3298
 *  net.minecraft.class_918
 *  net.minecraft.class_9848
 */
package dev.sixseven.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import dev.sixseven.SixSevenClient;
import dev.sixseven.module.impl.CustomGlintModule;
import dev.sixseven.rt.Deobf;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.class_1011;
import net.minecraft.class_1044;
import net.minecraft.class_1060;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3298;
import net.minecraft.class_918;
import net.minecraft.class_9848;

public final class GlintTextureTinter {
    private static final class_2960[] TEXTURES = new class_2960[]{class_918.field_43087, class_918.field_43086};
    private static final class_1011[] originals = new class_1011[TEXTURES.length];
    private static final class_1011[] scratch = new class_1011[TEXTURES.length];
    private static final GpuTexture[] lastUploaded = new GpuTexture[TEXTURES.length];
    private static final Map<String, class_1011> customCache = new HashMap<String, class_1011>();
    private static boolean loaded;
    private static boolean written;
    private static String lastKey;

    private GlintTextureTinter() {
    }

    public static void tick() {
        CustomGlintModule module;
        CustomGlintModule customGlintModule = module = SixSevenClient.modules() == null ? null : SixSevenClient.modules().customGlint;
        if (module == null) {
            return;
        }
        if (module.isActive()) {
            if (module.usesTexture()) {
                GlintTextureTinter.applyTexture(module.textureName(), module.strengthUnit());
            } else {
                GlintTextureTinter.applyTint(module.glintColor());
            }
        } else if (written) {
            GlintTextureTinter.restore();
        }
    }

    private static void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        class_310 mc = class_310.method_1551();
        for (int i = 0; i < TEXTURES.length; ++i) {
            Optional res = mc.method_1478().method_14486(TEXTURES[i]);
            if (res.isEmpty()) continue;
            try (InputStream in = ((class_3298)res.get()).method_14482();){
                GlintTextureTinter.originals[i] = class_1011.method_4309((InputStream)in);
                continue;
            }
            catch (Exception e) {
                GlintTextureTinter.originals[i] = null;
            }
        }
    }

    private static void applyTint(int color) {
        GlintTextureTinter.ensureLoaded();
        GlintTextureTinter.upload("tint:" + color, i -> GlintTextureTinter.tintInto(i, originals[i], color));
    }

    private static void applyTexture(String name, float strength) {
        GlintTextureTinter.ensureLoaded();
        class_1011 custom = GlintTextureTinter.loadCustom(name);
        if (custom == null) {
            return;
        }
        int strq = Math.round(strength * 255.0f);
        GlintTextureTinter.upload("tex:" + name + ":" + strq, i -> GlintTextureTinter.sampleInto(i, custom, strength));
    }

    private static void upload(String key, Source source) {
        class_1044 t;
        int i;
        class_1060 tm = class_310.method_1551().method_1531();
        boolean texChanged = false;
        for (i = 0; i < TEXTURES.length; ++i) {
            t = tm.method_4619(TEXTURES[i]);
            if (t == null || t.method_68004() == lastUploaded[i]) continue;
            texChanged = true;
        }
        if (written && key.equals(lastKey) && !texChanged) {
            return;
        }
        for (i = 0; i < TEXTURES.length; ++i) {
            class_1011 img;
            GpuTexture gpu;
            if (originals[i] == null) continue;
            t = tm.method_4619(TEXTURES[i]);
            GpuTexture gpuTexture = gpu = t == null ? null : t.method_68004();
            if (gpu == null || (img = source.build(i)) == null) continue;
            RenderSystem.getDevice().createCommandEncoder().writeToTexture(gpu, img);
            GlintTextureTinter.lastUploaded[i] = gpu;
        }
        written = true;
        lastKey = key;
    }

    private static void restore() {
        class_1060 tm = class_310.method_1551().method_1531();
        for (int i = 0; i < TEXTURES.length; ++i) {
            GpuTexture gpu;
            class_1011 src = originals[i];
            if (src == null) continue;
            class_1044 t = tm.method_4619(TEXTURES[i]);
            GpuTexture gpuTexture = gpu = t == null ? null : t.method_68004();
            if (gpu == null) continue;
            RenderSystem.getDevice().createCommandEncoder().writeToTexture(gpu, src);
            GlintTextureTinter.lastUploaded[i] = gpu;
        }
        written = false;
        lastKey = null;
    }

    private static class_1011 tintInto(int i, class_1011 src, int color) {
        int w = src.method_4307();
        int h = src.method_4323();
        class_1011 dst = GlintTextureTinter.ensureScratch(i, w, h);
        int tr = class_9848.method_61327((int)color);
        int tg = class_9848.method_61329((int)color);
        int tb = class_9848.method_61331((int)color);
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int p = src.method_61940(x, y);
                int v = Math.max(class_9848.method_61327((int)p), Math.max(class_9848.method_61329((int)p), class_9848.method_61331((int)p)));
                dst.method_61941(x, y, class_9848.method_61324((int)class_9848.method_61320((int)p), (int)(tr * v / 255), (int)(tg * v / 255), (int)(tb * v / 255)));
            }
        }
        return dst;
    }

    private static class_1011 sampleInto(int i, class_1011 src, float strength) {
        int w = originals[i].method_4307();
        int h = originals[i].method_4323();
        int sw = src.method_4307();
        int sh = src.method_4323();
        class_1011 dst = GlintTextureTinter.ensureScratch(i, w, h);
        for (int y = 0; y < h; ++y) {
            float fy = ((float)y + 0.5f) * (float)sh / (float)h - 0.5f;
            for (int x = 0; x < w; ++x) {
                float fx = ((float)x + 0.5f) * (float)sw / (float)w - 0.5f;
                int p = GlintTextureTinter.bilinearWrapped(src, fx, fy, sw, sh);
                int r = Math.round((float)class_9848.method_61327((int)p) * strength);
                int g = Math.round((float)class_9848.method_61329((int)p) * strength);
                int b = Math.round((float)class_9848.method_61331((int)p) * strength);
                dst.method_61941(x, y, class_9848.method_61324((int)class_9848.method_61320((int)p), (int)r, (int)g, (int)b));
            }
        }
        return dst;
    }

    private static class_1011 ensureScratch(int i, int w, int h) {
        class_1011 dst = scratch[i];
        if (dst == null || dst.method_4307() != w || dst.method_4323() != h) {
            if (dst != null) {
                dst.close();
            }
            GlintTextureTinter.scratch[i] = dst = new class_1011(w, h, false);
        }
        return dst;
    }

    private static int bilinearWrapped(class_1011 img, float fx, float fy, int sw, int sh) {
        int x0 = Math.floorMod((int)Math.floor(fx), sw);
        int y0 = Math.floorMod((int)Math.floor(fy), sh);
        int x1 = (x0 + 1) % sw;
        int y1 = (y0 + 1) % sh;
        float dx = fx - (float)Math.floor(fx);
        float dy = fy - (float)Math.floor(fy);
        int p00 = img.method_61940(x0, y0);
        int p10 = img.method_61940(x1, y0);
        int p01 = img.method_61940(x0, y1);
        int p11 = img.method_61940(x1, y1);
        int a = GlintTextureTinter.mix(class_9848.method_61320((int)p00), class_9848.method_61320((int)p10), class_9848.method_61320((int)p01), class_9848.method_61320((int)p11), dx, dy);
        int r = GlintTextureTinter.mix(class_9848.method_61327((int)p00), class_9848.method_61327((int)p10), class_9848.method_61327((int)p01), class_9848.method_61327((int)p11), dx, dy);
        int g = GlintTextureTinter.mix(class_9848.method_61329((int)p00), class_9848.method_61329((int)p10), class_9848.method_61329((int)p01), class_9848.method_61329((int)p11), dx, dy);
        int b = GlintTextureTinter.mix(class_9848.method_61331((int)p00), class_9848.method_61331((int)p10), class_9848.method_61331((int)p01), class_9848.method_61331((int)p11), dx, dy);
        return class_9848.method_61324((int)a, (int)r, (int)g, (int)b);
    }

    private static int mix(int c00, int c10, int c01, int c11, float dx, float dy) {
        float top = (float)c00 + (float)(c10 - c00) * dx;
        float bot = (float)c01 + (float)(c11 - c01) * dx;
        return Math.round(top + (bot - top) * dy);
    }

    private static class_1011 loadCustom(String name) {
        if (customCache.containsKey(name)) {
            return customCache.get(name);
        }
        class_1011 img = null;
        class_2960 id = class_2960.method_60655((String)Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00aa\u0117\u0100\u0106\u012d\u0184"), (String)("textures/misc/glints/" + name + ".png"));
        Optional res = class_310.method_1551().method_1478().method_14486(id);
        if (res.isPresent()) {
            try (InputStream in = ((class_3298)res.get()).method_14482();){
                img = class_1011.method_4309((InputStream)in);
            }
            catch (Exception e) {
                img = null;
            }
        }
        customCache.put(name, img);
        return img;
    }

    private static interface Source {
        public class_1011 build(int var1);
    }
}

