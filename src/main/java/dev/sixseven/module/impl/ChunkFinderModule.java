/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1923
 *  net.minecraft.class_2246
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2769
 *  net.minecraft.class_2818
 *  net.minecraft.class_2826
 *  net.minecraft.class_310
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.SliderSetting;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_310;

public final class ChunkFinderModule
extends Module {
    private static final int MIN_CHUNK_AGE_TICKS = 200;
    private static final int CHUNKS_PER_TICK = 8;
    public final SliderSetting mergeRadius = this.addSetting(new SliderSetting(Deobf.decrypt(">I:\tw\u00e4\u009e\u008b\u00ad\u0112\u011c\u0110"), Deobf.decrypt(";E>\u000ba\u00e4\u00bb\u0083\u00bd\u0113\u0100\u010d\u0163\u0184\u01fb\u01dd\u01ed\u025b\u021c\u0254\u0232\u0280\u02d5\u02c1\u02b3\u030b\u0317\u0317\u0352\u0390\u03fa\u0390\u03f9\u03d8\u044e\u042d\u0407\u042d\u04c6\u04a3\u04ef\u04e4\u0557\u0524\u0555\u0549\u05d6\u05e1\u05d6\u05ee\u061f\u062c\u0618\u065b\u069c\u06fc\u069a\u06d5\u0704\u0772\u0759\u0750\u07de\u07c4\u0795\u0784\u07a8\u0843"), 4.0, 1.0, 8.0, 1.0, Deobf.decrypt("SO ")));
    private final Set<Long> beehiveChunks = ConcurrentHashMap.newKeySet();
    private final Set<Long> notifiedChunks = ConcurrentHashMap.newKeySet();
    private final Map<Long, Integer> firstLoadedTicks = new ConcurrentHashMap<Long, Integer>();
    private volatile Set<Long> displayChunks = Set.of();
    private int scanCursor;
    private int tickCounter;
    private boolean displayDirty;
    private int lastMergeRadius;

    public ChunkFinderModule() {
        super(Deobf.decrypt("0D=\u0000y\u00e4\u008a\u0083\u00a7\u011f\u010c\u0111"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u0108\u0149\u0127\u012c\u019e\u01e6\u01c0\u01be\u0228\u023c\u0265\u027c\u029a\u029d\u02d7\u02b5\u0315\u030a\u035c\u0355\u03d8\u03f4\u0382\u03f9\u03d0\u044e\u043a\u040c\u0465\u0489\u04b6\u04a7\u04f3\u0544\u0576\u055d\u050c\u05d3\u05e9\u05c1\u05a2\u060f\u0663\u064b\u065d\u0695\u06f7\u06db\u06d4\u074d\u0761\u0752\u074e\u07cf\u078c\u07d3\u078b\u07a1\u0841\u081d\u0801\u0860\u08c4\u08a9\u08ca"), Category.RENDER);
    }

    @Override
    protected void onEnable() {
        this.clear();
    }

    @Override
    protected void onDisable() {
        this.clear();
    }

    @Override
    public void onTick() {
        class_310 client = class_310.method_1551();
        if (client.field_1687 == null || client.field_1724 == null) {
            return;
        }
        ++this.tickCounter;
        int radius = (Integer)client.field_1690.method_42503().method_41753();
        class_1923 playerChunk = client.field_1724.method_31476();
        int side = radius * 2 + 1;
        int total = side * side;
        if (total <= 0) {
            return;
        }
        for (int i = 0; i < 8; ++i) {
            boolean longLoaded;
            int index = this.scanCursor % total;
            this.scanCursor = (this.scanCursor + 1) % total;
            int dx = index % side - radius;
            int dz = index / side - radius;
            int cx = playerChunk.field_9181 + dx;
            int cz = playerChunk.field_9180 + dz;
            class_2818 chunk = client.field_1687.method_2935().method_12126(cx, cz, false);
            if (chunk == null || chunk.method_12223()) continue;
            long key2 = class_1923.method_8331((int)cx, (int)cz);
            this.firstLoadedTicks.putIfAbsent(key2, this.tickCounter);
            boolean bl = longLoaded = this.tickCounter - this.firstLoadedTicks.get(key2) >= 200;
            if (longLoaded && ChunkFinderModule.hasTargetSignal(chunk)) {
                if (this.beehiveChunks.add(key2)) {
                    this.displayDirty = true;
                }
                if (!this.notifiedChunks.add(key2)) continue;
                this.showToast(new class_1923(cx, cz));
                continue;
            }
            if (!this.beehiveChunks.remove(key2)) continue;
            this.displayDirty = true;
        }
        if (this.beehiveChunks.removeIf(key -> ChunkFinderModule.outOfRange(key, playerChunk, radius))) {
            this.displayDirty = true;
        }
        this.firstLoadedTicks.keySet().removeIf(key -> ChunkFinderModule.outOfRange(key, playerChunk, radius));
        if (this.displayDirty || this.mergeRadius.getInt() != this.lastMergeRadius) {
            this.rebuildDisplay();
        }
    }

    private void rebuildDisplay() {
        this.displayDirty = false;
        this.lastMergeRadius = this.mergeRadius.getInt();
        HashSet<Long> raw = new HashSet<Long>(this.beehiveChunks);
        if (raw.size() < 2) {
            this.displayChunks = Set.copyOf(raw);
            return;
        }
        int radius = Math.max(1, this.lastMergeRadius);
        HashSet<Long> display = new HashSet<Long>();
        HashSet<Long> visited = new HashSet<Long>();
        Iterator iterator = raw.iterator();
        while (iterator.hasNext()) {
            long seed = (Long)iterator.next();
            if (!visited.add(seed)) continue;
            ArrayList<Long> cluster = new ArrayList<Long>();
            ArrayDeque<Long> frontier = new ArrayDeque<Long>();
            frontier.add(seed);
            while (!frontier.isEmpty()) {
                long current = (Long)frontier.poll();
                cluster.add(current);
                int cx = class_1923.method_8325((long)current);
                int cz = class_1923.method_8332((long)current);
                for (int dx = -radius; dx <= radius; ++dx) {
                    for (int dz = -radius; dz <= radius; ++dz) {
                        long neighbour;
                        if (dx == 0 && dz == 0 || !raw.contains(neighbour = class_1923.method_8331((int)(cx + dx), (int)(cz + dz))) || !visited.add(neighbour)) continue;
                        frontier.add(neighbour);
                    }
                }
            }
            display.add(cluster.size() == 1 ? (Long)cluster.get(0) : ChunkFinderModule.middleChunk(cluster));
        }
        this.displayChunks = Set.copyOf(display);
    }

    private static long middleChunk(List<Long> cluster) {
        long sumX = 0L;
        long sumZ = 0L;
        for (long key : cluster) {
            sumX += (long)class_1923.method_8325((long)key);
            sumZ += (long)class_1923.method_8332((long)key);
        }
        int mx = (int)Math.round((double)sumX / (double)cluster.size());
        int mz = (int)Math.round((double)sumZ / (double)cluster.size());
        return class_1923.method_8331((int)mx, (int)mz);
    }

    private static boolean hasTargetSignal(class_2818 chunk) {
        for (class_2826 section : chunk.method_12006()) {
            if (section == null || section.method_38292() || !section.method_12265().method_19526(ChunkFinderModule::isFullHoney)) continue;
            for (int lx = 0; lx < 16; ++lx) {
                for (int lz = 0; lz < 16; ++lz) {
                    for (int ly = 0; ly < 16; ++ly) {
                        if (!ChunkFinderModule.isFullHoney(section.method_12254(lx, ly, lz))) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isFullHoney(class_2680 state) {
        return (state.method_27852(class_2246.field_20422) || state.method_27852(class_2246.field_20421)) && state.method_28498((class_2769)class_2741.field_20432) && (Integer)state.method_11654((class_2769)class_2741.field_20432) == 5;
    }

    private static boolean outOfRange(long key, class_1923 player, int radius) {
        return Math.abs(class_1923.method_8325((long)key) - player.field_9181) > radius || Math.abs(class_1923.method_8332((long)key) - player.field_9180) > radius;
    }

    private void showToast(class_1923 chunk) {
        if (SixSevenClient.notifications() == null) {
            return;
        }
        SixSevenClient.notifications().pushInfo("Chunk Finder \u00b7 X " + chunk.method_33940() + " Z " + chunk.method_33942());
    }

    public Set<Long> flaggedChunks() {
        return this.displayChunks;
    }

    public void clear() {
        this.beehiveChunks.clear();
        this.notifiedChunks.clear();
        this.firstLoadedTicks.clear();
        this.displayChunks = Set.of();
        this.scanCursor = 0;
        this.tickCounter = 0;
        this.displayDirty = false;
        this.lastMergeRadius = this.mergeRadius.getInt();
    }
}

