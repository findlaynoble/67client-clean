/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2248
 *  net.minecraft.class_243
 *  net.minecraft.class_2818
 *  net.minecraft.class_2826
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 *  org.joml.Vector3fc
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.BlockEspModule;
import dev.sixseven.render.EspBoxRenderer;
import dev.sixseven.render.IncrementalScan;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BlockListSetting;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.class_2248;
import net.minecraft.class_243;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import org.joml.Vector3fc;

public final class BlockEspRenderer {
    private static final int MAX_CHUNK_RADIUS = 12;
    private static final int MAX_RESULTS = 8000;
    private static final double INSET = 0.002;
    private static final IncrementalScan<Hit> SCAN = new IncrementalScan(48, 80000, 20);
    private static Set<class_2248> lastWanted = Set.of();

    private BlockEspRenderer() {
    }

    public static void clear() {
        SCAN.clear();
        lastWanted = Set.of();
    }

    public static int cachedCount() {
        return SCAN.get().size();
    }

    public static void scan(BlockEspModule module) {
        HashSet<class_2248> wanted = new HashSet<class_2248>();
        for (BlockListSetting.Target target : module.targets.targets()) {
            if (!((Boolean)target.enabled.get()).booleanValue() || target.block() == null) continue;
            wanted.add(target.block());
        }
        if (wanted.isEmpty()) {
            SCAN.clear();
            lastWanted = Set.of();
            return;
        }
        if (!wanted.equals(lastWanted)) {
            lastWanted = wanted;
            SCAN.markDirty();
        }
        int extra = module.rangeExtraChunks.getInt();
        int chunkRadius = Math.min(12, (Integer)class_310.method_1551().field_1690.method_42503().method_41753() + extra);
        SCAN.tick(chunkRadius, (chunk, out) -> BlockEspRenderer.scanChunk(chunk, wanted, out));
    }

    private static int scanChunk(class_2818 chunk, Set<class_2248> wanted, List<Hit> out) {
        class_2826[] sections = chunk.method_12006();
        int minSectionY = chunk.method_32891();
        int baseX = chunk.method_12004().method_8326();
        int baseZ = chunk.method_12004().method_8328();
        int blocks = 0;
        if (out.size() >= 8000) {
            return 0;
        }
        for (int s = 0; s < sections.length; ++s) {
            class_2826 section = sections[s];
            if (section.method_38292() || !section.method_19523(st -> wanted.contains(st.method_26204()))) continue;
            int baseY = minSectionY + s << 4;
            blocks += 4096;
            for (int y = 0; y < 16; ++y) {
                for (int z = 0; z < 16; ++z) {
                    for (int x = 0; x < 16; ++x) {
                        class_2248 block = section.method_12254(x, y, z).method_26204();
                        if (!wanted.contains(block)) continue;
                        out.add(new Hit(baseX + x, baseY + y, baseZ + z, block));
                        if (out.size() < 8000) continue;
                        return blocks;
                    }
                }
            }
        }
        return blocks;
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, BlockEspModule module) {
        List<Hit> snapshot = SCAN.get();
        if (snapshot.isEmpty()) {
            return;
        }
        HashMap<class_2248, Integer> colorByBlock = new HashMap<class_2248, Integer>();
        for (BlockListSetting.Target target : module.targets.targets()) {
            if (target.block() == null) continue;
            colorByBlock.put(target.block(), (Integer)target.color.get());
        }
        int fallback = (Integer)module.lineColor.get();
        int alpha = Math.clamp((long)module.highlightAlpha.getInt(), 0, 255);
        boolean drawLines = module.shapeMode.is(Deobf.decrypt("1C<\u0006")) || module.shapeMode.is(Deobf.decrypt("?E&\u000ba"));
        boolean drawFill = module.shapeMode.is(Deobf.decrypt("1C<\u0006")) || module.shapeMode.is(Deobf.decrypt(" E,\u000ba"));
        boolean drawTracers = (Boolean)module.tracers.get() != false && (Boolean)module.tracer.get() != false;
        for (Hit hit : snapshot) {
            int rgb = colorByBlock.getOrDefault(hit.block(), fallback) & 0xFFFFFF;
            int argb = rgb | alpha << 24;
            if (drawFill) {
                EspBoxRenderer.fill(bufferSource, poseStack, cam, (double)hit.x() + 0.002, (double)hit.y() + 0.002, (double)hit.z() + 0.002, (double)(hit.x() + 1) - 0.002, (double)(hit.y() + 1) - 0.002, (double)(hit.z() + 1) - 0.002, argb);
            }
            if (!drawLines) continue;
            EspBoxRenderer.outline(bufferSource, poseStack, cam, (double)hit.x() + 0.002, (double)hit.y() + 0.002, (double)hit.z() + 0.002, (double)(hit.x() + 1) - 0.002, (double)(hit.y() + 1) - 0.002, (double)(hit.z() + 1) - 0.002, argb, 1.6f);
        }
        if (drawTracers) {
            int tracerAlpha = (Integer)module.tracerColor.get() >>> 24 & 0xFF;
            if (tracerAlpha == 0) {
                tracerAlpha = 200;
            }
            Vector3fc forward = class_310.method_1551().field_1773.method_19418().method_19335();
            for (Hit hit : snapshot) {
                int rgb = colorByBlock.getOrDefault(hit.block(), fallback) & 0xFFFFFF;
                int col = rgb | tracerAlpha << 24;
                EspBoxRenderer.tracer(bufferSource, poseStack, cam, forward, (double)hit.x() + 0.5, (double)hit.y() + 0.5, (double)hit.z() + 0.5, col, 1.2f);
            }
        }
        EspBoxRenderer.flush(bufferSource);
    }

    private record Hit(int x, int y, int z, class_2248 block) {
    }
}

