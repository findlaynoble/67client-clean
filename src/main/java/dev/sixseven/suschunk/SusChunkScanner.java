/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1923
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2338$class_2339
 *  net.minecraft.class_2350
 *  net.minecraft.class_2561
 *  net.minecraft.class_2680
 *  net.minecraft.class_2741
 *  net.minecraft.class_2769
 *  net.minecraft.class_2818
 *  net.minecraft.class_2826
 *  net.minecraft.class_310
 *  net.minecraft.class_4076
 */
package dev.sixseven.suschunk;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Modules;
import dev.sixseven.rt.Deobf;
import dev.sixseven.suschunk.ServerLightCache;
import dev.sixseven.util.UiSounds;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_310;
import net.minecraft.class_4076;

public class SusChunkScanner {
    public static final int POINTS_PER_SENSITIVITY = 5;
    private static final int GEODE_LINK_DISTANCE = 5;
    private static final boolean DEBUG_LOG = Boolean.getBoolean(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00e7\u0108\u011c\u0110\u016d\u0194\u01f6\u01d6\u01eb\u021c"));
    private final Modules.SusChunkFinderModule module;
    private final Map<Long, ChunkScore> scores = new ConcurrentHashMap<Long, ChunkScore>();
    private final Deque<Long> queue = new ArrayDeque<Long>();
    private final Set<Long> lightRescans = ConcurrentHashMap.newKeySet();
    private final Set<Long> alertedChunks = new HashSet<Long>();
    private volatile List<Flag> flags = List.of();
    private volatile List<Zone> zones = List.of();
    private class_1923 lastQueueCenter;
    private int tickCounter;

    public SusChunkScanner(Modules.SusChunkFinderModule module) {
        this.module = module;
        ServerLightCache.get().addDirtyListener(this.lightRescans::add);
    }

    public List<Flag> flags() {
        return this.flags;
    }

    public List<Zone> zones() {
        return this.zones;
    }

    public int threshold() {
        return this.module.sensitivity.getInt() * 5;
    }

    public void clear() {
        this.scores.clear();
        this.queue.clear();
        this.lightRescans.clear();
        this.alertedChunks.clear();
        this.flags = List.of();
        this.zones = List.of();
        this.lastQueueCenter = null;
    }

    public void tick() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1687 == null || mc.field_1724 == null) {
            return;
        }
        try {
            this.refillQueueIfNeeded(mc);
            int budget = this.module.scanSpeed.getInt();
            long deadline = System.nanoTime() + 2000000L;
            int scanned = this.scanLightRescans(mc, budget, deadline);
            this.scanQueue(mc, budget - scanned, deadline);
            if (++this.tickCounter % 10 == 0) {
                this.rebuild(mc);
            }
        }
        catch (Exception e) {
            SixSevenClient.LOGGER.warn(Deobf.decrypt(" Y;-z\u00b1\u00a2\u0081\u008f\u0112\u0107\u0107\u0126\u0182\u01b3\u01c7\u01fd\u021a\u021f\u0215\u0239\u028b\u0287\u02cd\u02a9\u0344\u0359\u0307\u035c"), (Object)e.toString());
        }
    }

    private int scanLightRescans(class_310 mc, int budget, long deadline) {
        if (this.lightRescans.isEmpty()) {
            return 0;
        }
        int scanned = 0;
        Iterator<Long> iterator = this.lightRescans.iterator();
        while (iterator.hasNext() && scanned < budget && System.nanoTime() < deadline) {
            long key = iterator.next();
            iterator.remove();
            class_2818 chunk = mc.field_1687.method_2935().method_12126(class_1923.method_8325((long)key), class_1923.method_8332((long)key), false);
            if (chunk == null || !this.scores.containsKey(key)) continue;
            this.scores.put(key, this.scanChunk(mc, chunk));
            ++scanned;
        }
        return scanned;
    }

    private void scanQueue(class_310 mc, int budget, long deadline) {
        int scanned = 0;
        for (int polled = 0; scanned < budget && polled < 128 && !this.queue.isEmpty() && System.nanoTime() < deadline; ++polled) {
            class_2818 chunk;
            long key = this.queue.pollFirst();
            if (this.scores.containsKey(key) || (chunk = mc.field_1687.method_2935().method_12126(class_1923.method_8325((long)key), class_1923.method_8332((long)key), false)) == null) continue;
            this.scores.put(key, this.scanChunk(mc, chunk));
            ++scanned;
        }
    }

    private void refillQueueIfNeeded(class_310 mc) {
        class_1923 center = mc.field_1724.method_31476();
        if (!this.queue.isEmpty() && this.lastQueueCenter != null && Math.max(Math.abs(center.field_9181 - this.lastQueueCenter.field_9181), Math.abs(center.field_9180 - this.lastQueueCenter.field_9180)) < 3) {
            return;
        }
        this.lastQueueCenter = center;
        this.queue.clear();
        int radius = Math.min((Integer)mc.field_1690.method_42503().method_41753() + 1, 16);
        ArrayList<Long> order = new ArrayList<Long>();
        for (int dx = -radius; dx <= radius; ++dx) {
            for (int dz = -radius; dz <= radius; ++dz) {
                long key = class_1923.method_8331((int)(center.field_9181 + dx), (int)(center.field_9180 + dz));
                if (this.scores.containsKey(key)) continue;
                order.add(key);
            }
        }
        order.sort(Comparator.comparingDouble(k -> Math.hypot(class_1923.method_8325((long)k) - center.field_9181, class_1923.method_8332((long)k) - center.field_9180)));
        this.queue.addAll(order);
    }

    private static boolean isPlantTarget(class_2680 state) {
        class_2248 block = state.method_26204();
        return block == class_2246.field_9993 || block == class_2246.field_10463 || block == class_2246.field_10211 || block == class_2246.field_16999 || block == class_2246.field_10597 || block == class_2246.field_28048;
    }

    private static boolean isAmethystStructure(class_2680 state) {
        return state.method_27852(class_2246.field_27160) || state.method_27852(class_2246.field_27159);
    }

    private ChunkScore scanChunk(class_310 mc, class_2818 chunk) {
        ChunkScore result = new ChunkScore(chunk.method_12004().method_8324());
        if (((Boolean)this.module.amethyst.get()).booleanValue()) {
            this.detectAmethyst(mc, chunk, result);
        }
        this.detectGrowth(mc, chunk, result);
        result.computeScore();
        if (DEBUG_LOG && result.score > 0.0) {
            SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u00af\u0082\u00bc\u0115\u0102\u0143\u0138\u018d\u01b3\u01c7\u01fd\u0214\u0203\u0250\u0261\u0282\u0288\u0282\u02b3\u0317\u030d\u030f\u031c\u03cb\u03e8"), new Object[]{chunk.method_12004(), result.score, result.hits});
        }
        return result;
    }

    private void detectAmethyst(class_310 mc, class_2818 chunk, ChunkScore result) {
        List<class_2338> cells = ServerLightCache.get().light5Positions(chunk.method_12004().field_9181, chunk.method_12004().field_9180);
        if (cells.isEmpty()) {
            return;
        }
        class_2338.class_2339 cursor = new class_2338.class_2339();
        for (class_2338 cell : cells) {
            class_2680 state = mc.field_1687.method_8320(cell);
            if (!state.method_26215() && !state.method_27852(class_2246.field_27161) || !SusChunkScanner.hasAmethystNeighbour(mc, cell, cursor)) continue;
            result.amethystCells.add(cell.method_10062());
            if (!DEBUG_LOG) continue;
            SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u008d\u00a7\u008c\u012f\u0121\u013a\u0110\u01a4\u01b3\u01d8\u01f7\u021c\u0219\u0241\u0271\u02cc\u02d5\u02c1\u02be\u0312\u0315\u035c\u0361\u03cb\u03e8\u03d6\u03f1\u03c6\u0452\u0467"), (Object)cell, (Object)(state.method_26215() ? Deobf.decrypt("\u001bE,\nw\u00aa") : Deobf.decrypt("\u0005E;\u0007p\u00a8\u00a9")));
        }
    }

    private static boolean hasAmethystNeighbour(class_310 mc, class_2338 cell, class_2338.class_2339 cursor) {
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    if (dx == 0 && dy == 0 && dz == 0) continue;
                    cursor.method_10103(cell.method_10263() + dx, cell.method_10264() + dy, cell.method_10260() + dz);
                    if (!SusChunkScanner.isAmethystStructure(mc.field_1687.method_8320((class_2338)cursor))) continue;
                    return true;
                }
            }
        }
        return false;
    }

    private void detectGrowth(class_310 mc, class_2818 chunk, ChunkScore result) {
        class_2826[] sections = chunk.method_12006();
        int minSectionY = chunk.method_32891();
        int loSection = class_4076.method_18675((int)-12);
        int hiSection = class_4076.method_18675((int)80);
        int baseX = chunk.method_12004().method_8326();
        int baseZ = chunk.method_12004().method_8328();
        class_2338.class_2339 cursor = new class_2338.class_2339();
        for (int s = 0; s < sections.length; ++s) {
            class_2826 section;
            int sectionY = minSectionY + s;
            if (sectionY < loSection || sectionY > hiSection || (section = sections[s]).method_38292() || !section.method_12265().method_19526(SusChunkScanner::isPlantTarget)) continue;
            int baseY = sectionY << 4;
            for (int y = 0; y < 16; ++y) {
                int worldY = baseY + y;
                if (worldY < -12 || worldY > 80) continue;
                for (int z = 0; z < 16; ++z) {
                    for (int x = 0; x < 16; ++x) {
                        class_2680 state = section.method_12254(x, y, z);
                        if (!SusChunkScanner.isPlantTarget(state)) continue;
                        cursor.method_10103(baseX + x, worldY, baseZ + z);
                        this.inspectPlant(mc, state, (class_2338)cursor, result);
                    }
                }
            }
        }
    }

    private void inspectPlant(class_310 mc, class_2680 state, class_2338 pos, ChunkScore result) {
        class_2248 block = state.method_26204();
        if ((block == class_2246.field_9993 || block == class_2246.field_10463) && ((Boolean)this.module.kelp.get()).booleanValue()) {
            boolean maxAge;
            class_2680 above = mc.field_1687.method_8320(pos.method_10084());
            if (above.method_27852(class_2246.field_9993) || above.method_27852(class_2246.field_10463)) {
                return;
            }
            int height = SusChunkScanner.columnLength(mc, pos, class_2350.field_11033, class_2246.field_9993, class_2246.field_10463);
            boolean bl = maxAge = block == class_2246.field_9993 && state.method_28498((class_2769)class_2741.field_12517) && (Integer)state.method_11654((class_2769)class_2741.field_12517) == 25;
            if (maxAge && height >= 8 || height >= 14) {
                result.add(SignalType.KELP, pos);
                if (DEBUG_LOG) {
                    SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u0087\u00af\u0085\u012b\u0149\u0123\u0138\u018d\u01b3\u01dc\u01fb\u0212\u0216\u025d\u0228\u02c4\u028e\u02df\u02fb\u0313\u0318\u0304\u0360\u03d7\u03f0\u03cb\u03a2\u03c0"), new Object[]{pos, height, maxAge});
                }
            }
        } else if (block == class_2246.field_10211 && ((Boolean)this.module.bamboo.get()).booleanValue()) {
            if (mc.field_1687.method_8320(pos.method_10074()).method_27852(class_2246.field_10211)) {
                return;
            }
            int height = SusChunkScanner.columnLength(mc, pos, class_2350.field_11036, class_2246.field_10211);
            if (height >= 12) {
                result.add(SignalType.BAMBOO, pos);
                if (DEBUG_LOG) {
                    SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u008e\u00ab\u0084\u0139\u0126\u012c\u0163\u01b0\u01e8\u01c9\u01be\u0213\u0214\u025c\u023b\u0291\u0281\u029f\u02a0\u0303"), (Object)pos, (Object)height);
                }
            }
        } else if (block == class_2246.field_16999 && ((Boolean)this.module.berries.get()).booleanValue()) {
            if (state.method_28498((class_2769)class_2741.field_12497) && (Integer)state.method_11654((class_2769)class_2741.field_12497) == 3) {
                result.add(SignalType.BERRIES, pos);
                if (DEBUG_LOG) {
                    SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u008e\u00af\u009b\u0129\u0120\u0126\u0110\u01d0\u01fe\u01d5\u01e6\u025b\u0210\u0252\u0239\u02d9\u02b5\u02d9\u02a6"), (Object)pos);
                }
            }
        } else if (block == class_2246.field_10597 && ((Boolean)this.module.vines.get()).booleanValue()) {
            if (mc.field_1687.method_8320(pos.method_10084()).method_27852(class_2246.field_10597)) {
                return;
            }
            int length = SusChunkScanner.columnLength(mc, pos, class_2350.field_11033, class_2246.field_10597);
            if (length >= 7) {
                result.add(SignalType.VINES, pos);
                if (DEBUG_LOG) {
                    SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u009a\u00a3\u0087\u013e\u013a\u0143\u0103\u018b\u01ee\u0194\u01f6\u021a\u021f\u0252\u0261\u0282\u0288"), (Object)pos, (Object)length);
                }
            }
        } else if (block == class_2246.field_28048 && ((Boolean)this.module.dripstone.get()).booleanValue()) {
            if (mc.field_1687.method_8320(pos.method_10084()).method_27852(class_2246.field_28048) || !mc.field_1687.method_8320(pos.method_10074()).method_27852(class_2246.field_28048)) {
                return;
            }
            int length = SusChunkScanner.columnLength(mc, pos, class_2350.field_11033, class_2246.field_28048);
            if (length >= 5) {
                result.add(SignalType.DRIPSTONE, pos);
                if (DEBUG_LOG) {
                    SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u0088\u00b8\u0080\u012b\u013a\u0137\u010c\u01be\u01d6\u0194\u01de\u0200\u020c\u0215\u0230\u029c\u029b\u02c5\u02af\u0316\u0344\u0307\u035c"), (Object)pos, (Object)length);
                }
            }
        }
    }

    private static int columnLength(class_310 mc, class_2338 start, class_2350 dir, class_2248 ... blocks) {
        int length;
        class_2338.class_2339 cursor = start.method_25503();
        block0: for (length = 1; length < 40; ++length) {
            cursor.method_10098(dir);
            class_2680 state = mc.field_1687.method_8320((class_2338)cursor);
            for (class_2248 block : blocks) {
                if (!state.method_27852(block)) continue;
                continue block0;
            }
        }
        return length;
    }

    private void rebuild(class_310 mc) {
        this.scores.keySet().removeIf(key -> mc.field_1687.method_2935().method_12126(class_1923.method_8325((long)key), class_1923.method_8332((long)key), false) == null);
        int threshold = this.threshold();
        HashMap<Long, FlagAggregate> aggregates = new HashMap<Long, FlagAggregate>();
        for (ChunkScore score : this.scores.values()) {
            if (!(score.score >= (double)threshold)) continue;
            FlagAggregate agg = aggregates.computeIfAbsent(score.chunkKey, FlagAggregate::new);
            agg.score = Math.max(agg.score, score.score);
            agg.hitWeight += score.hitWeight;
            agg.hitX += score.hitX;
            agg.hitZ += score.hitZ;
        }
        for (Geode geode : this.clusterGeodes()) {
            if (geode.score() < (double)threshold) continue;
            for (long chunkKey : geode.chunks()) {
                FlagAggregate agg = aggregates.computeIfAbsent(chunkKey, FlagAggregate::new);
                agg.score = Math.max(agg.score, geode.score());
            }
            double weight = SignalType.AMETHYST.weight;
            for (class_2338 cell : geode.cells()) {
                FlagAggregate agg = (FlagAggregate)aggregates.get(class_1923.method_8331((int)(cell.method_10263() >> 4), (int)(cell.method_10260() >> 4)));
                if (agg == null) continue;
                agg.hitWeight += weight;
                agg.hitX += ((double)cell.method_10263() + 0.5) * weight;
                agg.hitZ += ((double)cell.method_10260() + 0.5) * weight;
            }
            if (!DEBUG_LOG) continue;
            SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001dO\u00e4\u008b\u00af\u0086\u013f\u012c\u0143\u0138\u018d\u01b3\u01d7\u01fb\u0217\u021d\u0246\u027c\u02d6\u02d5\u02d9\u02a6\u035e\u031a\u0314\u0354\u03de\u03fe\u0385\u03f5\u039d\u045c\u042d\u0400\u047f\u04cc\u04f7\u04fc\u04fc\u0505\u0529\u0506\u050c\u05e2\u05ca\u05f2\u0589"), new Object[]{geode.cells().size(), geode.chunks().size(), geode.score()});
        }
        ArrayList<Flag> flagged = new ArrayList<Flag>();
        for (FlagAggregate agg : aggregates.values()) {
            flagged.add(new Flag(agg.chunkKey, agg.score));
        }
        this.flags = List.copyOf(flagged);
        this.zones = this.buildZones(flagged, aggregates);
        this.fireAlerts(mc);
    }

    private List<Geode> clusterGeodes() {
        int i;
        ArrayList<class_2338> cells = new ArrayList<class_2338>();
        for (ChunkScore score : this.scores.values()) {
            cells.addAll(score.amethystCells);
        }
        int n = cells.size();
        if (n == 0) {
            return List.of();
        }
        int[] parent = new int[n];
        for (i = 0; i < n; ++i) {
            parent[i] = i;
        }
        for (i = 0; i < n; ++i) {
            class_2338 a = (class_2338)cells.get(i);
            for (int j = i + 1; j < n; ++j) {
                class_2338 b = (class_2338)cells.get(j);
                int chebyshev = Math.max(Math.abs(a.method_10263() - b.method_10263()), Math.max(Math.abs(a.method_10264() - b.method_10264()), Math.abs(a.method_10260() - b.method_10260())));
                if (chebyshev > 5) continue;
                parent[SusChunkScanner.find((int[])parent, (int)i)] = SusChunkScanner.find(parent, j);
            }
        }
        HashMap<Integer, List> groups = new HashMap<Integer, List>();
        for (int i2 = 0; i2 < n; ++i2) {
            groups.computeIfAbsent(SusChunkScanner.find(parent, i2), k -> new ArrayList()).add((class_2338)cells.get(i2));
        }
        ArrayList<Geode> geodes = new ArrayList<Geode>();
        for (List group : groups.values()) {
            HashSet<Long> chunks = new HashSet<Long>();
            double sx = 0.0;
            double sz = 0.0;
            for (class_2338 c : group) {
                chunks.add(class_1923.method_8331((int)(c.method_10263() >> 4), (int)(c.method_10260() >> 4)));
                sx += (double)c.method_10263() + 0.5;
                sz += (double)c.method_10260() + 0.5;
            }
            double score = SignalType.AMETHYST.weight * (double)Math.min(group.size(), SignalType.AMETHYST.cap);
            geodes.add(new Geode(List.copyOf(group), Set.copyOf(chunks), score, sx / (double)group.size(), sz / (double)group.size()));
        }
        return geodes;
    }

    private static int find(int[] parent, int i) {
        while (parent[i] != i) {
            parent[i] = parent[parent[i]];
            i = parent[i];
        }
        return i;
    }

    private List<Zone> buildZones(List<Flag> flagged, Map<Long, FlagAggregate> aggregates) {
        int radius = Math.max(1, this.module.mergeRadius.getInt());
        HashMap<Long, Flag> byKey = new HashMap<Long, Flag>();
        for (Flag flag : flagged) {
            byKey.put(flag.chunkKey(), flag);
        }
        ArrayList<Zone> built = new ArrayList<Zone>();
        HashSet<Long> visited = new HashSet<Long>();
        for (Flag seed : flagged) {
            if (!visited.add(seed.chunkKey())) continue;
            ArrayList<Flag> group = new ArrayList<Flag>();
            ArrayDeque<Flag> frontier = new ArrayDeque<Flag>(List.of(seed));
            while (!frontier.isEmpty()) {
                Flag current = (Flag)frontier.poll();
                group.add(current);
                int cx = class_1923.method_8325((long)current.chunkKey());
                int cz = class_1923.method_8332((long)current.chunkKey());
                for (int dx = -radius; dx <= radius; ++dx) {
                    for (int dz = -radius; dz <= radius; ++dz) {
                        Flag neighbour;
                        if (dx == 0 && dz == 0 || (neighbour = (Flag)byKey.get(class_1923.method_8331((int)(cx + dx), (int)(cz + dz)))) == null || !visited.add(neighbour.chunkKey())) continue;
                        frontier.add(neighbour);
                    }
                }
            }
            built.add(this.makeZone(group, aggregates));
        }
        built.sort(Comparator.comparingDouble(Zone::totalScore).reversed());
        return built;
    }

    private Zone makeZone(List<Flag> group, Map<Long, FlagAggregate> aggregates) {
        HashSet<Long> members = new HashSet<Long>();
        double totalScore = 0.0;
        double maxScore = 0.0;
        double hitWeight = 0.0;
        double hitX = 0.0;
        double hitZ = 0.0;
        double chunkX = 0.0;
        double chunkZ = 0.0;
        for (Flag flag : group) {
            members.add(flag.chunkKey());
            totalScore += flag.score();
            maxScore = Math.max(maxScore, flag.score());
            FlagAggregate agg = aggregates.get(flag.chunkKey());
            if (agg != null && agg.hitWeight > 0.0) {
                hitWeight += agg.hitWeight;
                hitX += agg.hitX;
                hitZ += agg.hitZ;
            }
            chunkX += (double)(class_1923.method_8325((long)flag.chunkKey()) * 16 + 8) * flag.score();
            chunkZ += (double)(class_1923.method_8332((long)flag.chunkKey()) * 16 + 8) * flag.score();
        }
        double cx = hitWeight > 0.0 ? hitX / hitWeight : chunkX / totalScore;
        double cz = hitWeight > 0.0 ? hitZ / hitWeight : chunkZ / totalScore;
        return new Zone(Set.copyOf(members), cx, cz, totalScore, maxScore);
    }

    private void fireAlerts(class_310 mc) {
        for (Zone zone : this.zones) {
            boolean isNew = true;
            for (long member : zone.members()) {
                if (!this.alertedChunks.contains(member)) continue;
                isNew = false;
                break;
            }
            if (!isNew) {
                this.alertedChunks.addAll(zone.members());
                continue;
            }
            this.alertedChunks.addAll(zone.members());
            if (this.module.notifications.is(Deobf.decrypt("<J."))) continue;
            int bx = (int)Math.round(zone.centroidX());
            int bz = (int)Math.round(zone.centroidZ());
            int dist = (int)Math.hypot((double)bx - mc.field_1724.method_23317(), (double)bz - mc.field_1724.method_23321());
            String message = "Sus zone \u00b7 " + dist + "m \u00b7 " + bx + ", " + bz;
            if (this.module.notifications.is(Deobf.decrypt("'C)\u001df")) && SixSevenClient.notifications() != null) {
                SixSevenClient.notifications().pushInfo(message);
                UiSounds.notification(true);
                continue;
            }
            if (!this.module.notifications.is(Deobf.decrypt("0D)\u001a"))) continue;
            mc.field_1724.method_7353((class_2561)class_2561.method_43470((String)("\u00a7d[67] \u00a7f" + message)), false);
        }
    }

    public static final class ChunkScore {
        public final long chunkKey;
        public final EnumMap<SignalType, Integer> hits = new EnumMap(SignalType.class);
        public final List<class_2338> amethystCells = new ArrayList<class_2338>();
        public double score;
        double hitWeight;
        double hitX;
        double hitZ;

        ChunkScore(long chunkKey) {
            this.chunkKey = chunkKey;
        }

        void add(SignalType type, class_2338 pos) {
            this.hits.merge(type, 1, Integer::sum);
            this.hitWeight += type.weight;
            this.hitX += ((double)pos.method_10263() + 0.5) * type.weight;
            this.hitZ += ((double)pos.method_10260() + 0.5) * type.weight;
        }

        void computeScore() {
            double total = 0.0;
            for (Map.Entry<SignalType, Integer> entry : this.hits.entrySet()) {
                total += entry.getKey().weight * (double)Math.min(entry.getValue(), entry.getKey().cap);
            }
            this.score = total;
        }
    }

    public static enum SignalType {
        AMETHYST(6.0, 16),
        KELP(2.0, 6),
        BAMBOO(2.0, 6),
        BERRIES(2.0, 5),
        VINES(2.0, 6),
        DRIPSTONE(2.0, 5);

        public final double weight;
        public final int cap;

        private SignalType(double weight, int cap) {
            this.weight = weight;
            this.cap = cap;
        }
    }

    private static final class FlagAggregate {
        final long chunkKey;
        double score;
        double hitWeight;
        double hitX;
        double hitZ;

        FlagAggregate(long chunkKey) {
            this.chunkKey = chunkKey;
        }
    }

    public record Geode(List<class_2338> cells, Set<Long> chunks, double score, double centroidX, double centroidZ) {
    }

    public record Flag(long chunkKey, double score) {
    }

    public record Zone(Set<Long> members, double centroidX, double centroidZ, double totalScore, double maxScore) {
    }
}

