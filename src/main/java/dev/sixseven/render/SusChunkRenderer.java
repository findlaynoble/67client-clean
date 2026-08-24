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
import dev.sixseven.module.Modules;
import dev.sixseven.render.FlatOverlay;
import dev.sixseven.rt.Deobf;
import dev.sixseven.suschunk.SusChunkScanner;
import dev.sixseven.util.Colors;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.class_1923;
import net.minecraft.class_243;
import net.minecraft.class_4587;
import net.minecraft.class_4597;

public final class SusChunkRenderer {
    private static final float FADE_IN_SECONDS = 0.3f;
    private static final float FADE_OUT_SECONDS = 0.45f;
    private static final float MOVE_RATE = 7.0f;
    private static final Map<Long, ChunkFade> chunkFades = new HashMap<Long, ChunkFade>();
    private static final Map<Long, ZoneFade> zoneFades = new HashMap<Long, ZoneFade>();
    private static long lastFrameNanos;

    private SusChunkRenderer() {
    }

    public static void reset() {
        chunkFades.clear();
        zoneFades.clear();
    }

    public static String debugState() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Long, ChunkFade> entry : chunkFades.entrySet()) {
            ChunkFade fade = entry.getValue();
            sb.append(new class_1923(entry.getKey().longValue())).append(Deobf.decrypt("\bMu")).append(fade.alpha).append(Deobf.decrypt("_Xu")).append(fade.tier).append(Deobf.decrypt("_Ju")).append(fade.flagged).append(Deobf.decrypt("\u000e\f"));
        }
        for (Map.Entry<Long, Object> entry : zoneFades.entrySet()) {
            ZoneFade zone = (ZoneFade)entry.getValue();
            sb.append(Deobf.decrypt("\tC&\u000b")).append(new class_1923(entry.getKey().longValue())).append(Deobf.decrypt("\bMu")).append(zone.alpha).append(Deobf.decrypt("__u")).append(zone.size).append(Deobf.decrypt("_Ju")).append(zone.flagged).append(Deobf.decrypt("\u000e\f"));
        }
        return sb.toString();
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 camera, Modules.SusChunkFinderModule module) {
        SusChunkScanner scanner = module.scanner;
        double y = (Double)module.renderY.get();
        int accent = SixSevenClient.themes().current().accent();
        float fillAlpha = module.fillOpacity.getFloat() / 255.0f;
        float outlineAlpha = module.outlineOpacity.getFloat() / 255.0f;
        boolean outline = (Boolean)module.outline.get();
        boolean smart = (Boolean)module.smartMode.get();
        int threshold = scanner.threshold();
        SusChunkRenderer.updateTargets(scanner, smart, threshold);
        float dt = SusChunkRenderer.frameDelta();
        float move = 1.0f - (float)Math.exp(-dt * 7.0f);
        Iterator<Map.Entry<Long, ChunkFade>> chunks = chunkFades.entrySet().iterator();
        while (chunks.hasNext()) {
            Map.Entry<Long, ChunkFade> entry = chunks.next();
            ChunkFade fade = entry.getValue();
            fade.alpha = fade.alpha + (fade.flagged ? dt / 0.3f : -dt / 0.45f);
            fade.alpha = Math.clamp(fade.alpha, 0.0f, 1.0f);
            if (!fade.flagged && fade.alpha <= 0.0f) {
                chunks.remove();
                continue;
            }
            double x0 = (double)class_1923.method_8325((long)entry.getKey()) * 16.0;
            double z0 = (double)class_1923.method_8332((long)entry.getKey()) * 16.0;
            SusChunkRenderer.drawQuad(bufferSource, poseStack, camera, x0, z0, x0 + 16.0, z0 + 16.0, y, accent, fillAlpha * fade.tier * fade.alpha, outline ? outlineAlpha * fade.alpha : 0.0f);
        }
        Iterator<Map.Entry<Long, ZoneFade>> zones = zoneFades.entrySet().iterator();
        while (zones.hasNext()) {
            ZoneFade zone = zones.next().getValue();
            zone.alpha = zone.alpha + (zone.flagged ? dt / 0.3f : -dt / 0.45f);
            zone.alpha = Math.clamp(zone.alpha, 0.0f, 1.0f);
            if (!zone.flagged && zone.alpha <= 0.0f) {
                zones.remove();
                continue;
            }
            zone.centerX += (zone.targetX - zone.centerX) * (double)move;
            zone.centerZ += (zone.targetZ - zone.centerZ) * (double)move;
            zone.size += (zone.targetSize - zone.size) * move;
            double half = (double)zone.size / 2.0;
            SusChunkRenderer.drawQuad(bufferSource, poseStack, camera, zone.centerX - half, zone.centerZ - half, zone.centerX + half, zone.centerZ + half, y, accent, fillAlpha * zone.tier * zone.alpha, outline ? outlineAlpha * zone.alpha : 0.0f);
            if (!((Boolean)module.centroidMarker.get()).booleanValue()) continue;
            FlatOverlay.marker(bufferSource, poseStack, camera, zone.centerX, zone.centerZ, y + 0.05, 2.0, Colors.withAlpha(accent, 0.95f * zone.alpha));
        }
        FlatOverlay.flush(bufferSource);
    }

    private static void drawQuad(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 camera, double x0, double z0, double x1, double z1, double y, int accent, float fill, float border) {
        FlatOverlay.fillQuad(bufferSource, poseStack, camera, x0, z0, x1, z1, y, Colors.withAlpha(accent, fill));
        if (border > 0.004f) {
            int color = Colors.withAlpha(accent, border);
            FlatOverlay.edge(bufferSource, poseStack, camera, x0, z0, x1, z0, y, color, 2.5f);
            FlatOverlay.edge(bufferSource, poseStack, camera, x0, z1, x1, z1, y, color, 2.5f);
            FlatOverlay.edge(bufferSource, poseStack, camera, x0, z0, x0, z1, y, color, 2.5f);
            FlatOverlay.edge(bufferSource, poseStack, camera, x1, z0, x1, z1, y, color, 2.5f);
        }
    }

    private static void updateTargets(SusChunkScanner scanner, boolean smart, int threshold) {
        for (ChunkFade chunkFade : chunkFades.values()) {
            chunkFade.flagged = false;
        }
        for (ZoneFade zoneFade : zoneFades.values()) {
            zoneFade.flagged = false;
        }
        if (smart) {
            for (SusChunkScanner.Zone zone : scanner.zones()) {
                Object fade;
                long key;
                if (zone.members().size() == 1) {
                    key = zone.members().iterator().next();
                    fade = chunkFades.computeIfAbsent(key, k -> new ChunkFade());
                    ((ChunkFade)fade).flagged = true;
                    ((ChunkFade)fade).tier = SusChunkRenderer.confidence(zone.maxScore(), threshold);
                    continue;
                }
                key = Long.MAX_VALUE;
                fade = zone.members().iterator();
                while (fade.hasNext()) {
                    long member = fade.next();
                    key = Math.min(key, member);
                }
                fade = zoneFades.get(key);
                if (fade == null) {
                    fade = new ZoneFade(zone.centroidX(), zone.centroidZ());
                    zoneFades.put(key, (ZoneFade)fade);
                }
                ((ZoneFade)fade).flagged = true;
                ((ZoneFade)fade).targetX = zone.centroidX();
                ((ZoneFade)fade).targetZ = zone.centroidZ();
                ((ZoneFade)fade).targetSize = Math.min(48.0f, 16.0f + (float)(zone.members().size() - 1) * 8.0f);
                ((ZoneFade)fade).tier = SusChunkRenderer.confidence(zone.maxScore(), threshold);
            }
        } else {
            for (SusChunkScanner.Flag flag : scanner.flags()) {
                ChunkFade fade = chunkFades.computeIfAbsent(flag.chunkKey(), k -> new ChunkFade());
                fade.flagged = true;
                fade.tier = 1.0f;
            }
        }
    }

    private static float confidence(double score, int threshold) {
        if (threshold <= 0) {
            return 1.0f;
        }
        float over = (float)((score - (double)threshold) / ((double)threshold * 2.0));
        return 0.55f + 0.45f * Math.clamp(over, 0.0f, 1.0f);
    }

    private static float frameDelta() {
        long now = System.nanoTime();
        float dt = lastFrameNanos == 0L ? 0.016f : (float)(now - lastFrameNanos) / 1.0E9f;
        lastFrameNanos = now;
        return Math.min(dt, 0.1f);
    }

    private static final class ChunkFade {
        float alpha;
        float tier = 1.0f;
        boolean flagged;

        private ChunkFade() {
        }
    }

    private static final class ZoneFade {
        double centerX;
        double centerZ;
        double targetX;
        double targetZ;
        float size = 16.0f;
        float targetSize = 16.0f;
        float alpha;
        float tier = 1.0f;
        boolean flagged;

        ZoneFade(double x, double z) {
            this.centerX = this.targetX = x;
            this.centerZ = this.targetZ = z;
        }
    }
}

