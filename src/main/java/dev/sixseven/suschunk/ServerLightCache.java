/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1923
 *  net.minecraft.class_1937
 *  net.minecraft.class_2338
 *  net.minecraft.class_4076
 *  net.minecraft.class_6606
 */
package dev.sixseven.suschunk;

import dev.sixseven.SixSevenClient;
import dev.sixseven.rt.Deobf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.LongConsumer;
import net.minecraft.class_1923;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_4076;
import net.minecraft.class_6606;

public final class ServerLightCache {
    public static final int SCAN_Y_MIN = -12;
    public static final int SCAN_Y_MAX = 80;
    private static final boolean DEBUG_LOG = Boolean.getBoolean(Deobf.decrypt("\u0000E0\u001dw\u00b2\u00a9\u0084\u00e7\u0108\u011c\u0110\u016d\u0194\u01f6\u01d6\u01eb\u021c"));
    private static final int TARGET_MIN_SECTION = class_4076.method_18675((int)-12);
    private static final int TARGET_MAX_SECTION = class_4076.method_18675((int)80);
    private static final ServerLightCache INSTANCE = new ServerLightCache();
    private final ConcurrentHashMap<Long, byte[]> sections = new ConcurrentHashMap();
    private final ConcurrentHashMap<Long, short[]> light5Cells = new ConcurrentHashMap();
    private final Set<Long> dirtyChunks = ConcurrentHashMap.newKeySet();
    private final List<LongConsumer> dirtyListeners = new CopyOnWriteArrayList<LongConsumer>();

    private ServerLightCache() {
    }

    public static ServerLightCache get() {
        return INSTANCE;
    }

    public void clear() {
        this.sections.clear();
        this.light5Cells.clear();
        this.dirtyChunks.clear();
    }

    public boolean consumeDirty(int chunkX, int chunkZ) {
        return this.dirtyChunks.remove(class_1923.method_8331((int)chunkX, (int)chunkZ));
    }

    public void addDirtyListener(LongConsumer listener) {
        this.dirtyListeners.add(listener);
    }

    public void markDirty(int chunkX, int chunkZ) {
        long key = class_1923.method_8331((int)chunkX, (int)chunkZ);
        this.dirtyChunks.add(key);
        for (LongConsumer listener : this.dirtyListeners) {
            listener.accept(key);
        }
    }

    public void ingest(int chunkX, int chunkZ, class_6606 data, class_1937 level) {
        if (level == null) {
            return;
        }
        int minLightSection = level.method_32891() - 1;
        BitSet blockMask = data.method_38608();
        BitSet emptyMask = data.method_38609();
        List updates = data.method_38610();
        int updateIndex = 0;
        boolean changed = false;
        int i = blockMask.nextSetBit(0);
        while (i >= 0) {
            byte[] bytes = updateIndex < updates.size() ? (byte[])updates.get(updateIndex) : null;
            ++updateIndex;
            int sectionY = minLightSection + i;
            if (bytes != null && bytes.length == 2048 && sectionY >= TARGET_MIN_SECTION && sectionY <= TARGET_MAX_SECTION) {
                long sectionKey = class_4076.method_18685((int)chunkX, (int)sectionY, (int)chunkZ);
                this.sections.put(sectionKey, (byte[])bytes.clone());
                int light5Count = this.cacheLight5(sectionKey, bytes);
                if (DEBUG_LOG && light5Count > 0) {
                    SixSevenClient.LOGGER.info(Deobf.decrypt("(_=\u001d?\u00a8\u00a5\u008d\u00a1\u010f\u0134\u0143\u0130\u0195\u01f0\u01c0\u01f7\u0214\u021f\u0215\u0274\u0282\u0288\u028e\u02a0\u0303\u0355\u0307\u035c\u0399\u03af\u03d6\u03a2\u03c0\u040f\u0422\u0406\u046a\u04c1\u04a3\u04aa\u04b4\u0505\u0574\u0557\u055f\u05cd\u05f2\u05da\u05a1\u0605\u0630"), new Object[]{chunkX, sectionY, chunkZ, light5Count});
                }
                changed = true;
            }
            i = blockMask.nextSetBit(i + 1);
        }
        i = emptyMask.nextSetBit(0);
        while (i >= 0) {
            int sectionY = minLightSection + i;
            if (sectionY >= TARGET_MIN_SECTION && sectionY <= TARGET_MAX_SECTION) {
                long sectionKey = class_4076.method_18685((int)chunkX, (int)sectionY, (int)chunkZ);
                this.light5Cells.remove(sectionKey);
                if (this.sections.remove(sectionKey) != null) {
                    changed = true;
                }
            }
            i = emptyMask.nextSetBit(i + 1);
        }
        if (changed) {
            this.markDirty(chunkX, chunkZ);
        }
    }

    public int serverBlockLight(int x, int y, int z) {
        if (y < -12 || y > 80) {
            return -1;
        }
        byte[] data = this.sections.get(class_4076.method_18685((int)class_4076.method_18675((int)x), (int)class_4076.method_18675((int)y), (int)class_4076.method_18675((int)z)));
        if (data == null) {
            return -1;
        }
        int index = (y & 0xF) << 8 | (z & 0xF) << 4 | x & 0xF;
        int b = data[index >> 1] & 0xFF;
        return (index & 1) == 0 ? b & 0xF : b >> 4 & 0xF;
    }

    public boolean isServerLight5(int x, int y, int z) {
        return this.serverBlockLight(x, y, z) == 5;
    }

    private int cacheLight5(long sectionKey, byte[] nibbles) {
        short[] buffer = null;
        int count = 0;
        for (int index = 0; index < 4096; ++index) {
            int light;
            int b = nibbles[index >> 1] & 0xFF;
            int n = light = (index & 1) == 0 ? b & 0xF : b >> 4 & 0xF;
            if (light != 5) continue;
            if (buffer == null) {
                buffer = new short[16];
            } else if (count == buffer.length) {
                buffer = Arrays.copyOf(buffer, buffer.length * 2);
            }
            buffer[count++] = (short)index;
        }
        if (count == 0) {
            this.light5Cells.remove(sectionKey);
        } else {
            this.light5Cells.put(sectionKey, Arrays.copyOf(buffer, count));
        }
        return count;
    }

    public List<class_2338> light5Positions(int chunkX, int chunkZ) {
        ArrayList<class_2338> out = null;
        int baseX = chunkX << 4;
        int baseZ = chunkZ << 4;
        for (int sy = TARGET_MIN_SECTION; sy <= TARGET_MAX_SECTION; ++sy) {
            short[] cells = this.light5Cells.get(class_4076.method_18685((int)chunkX, (int)sy, (int)chunkZ));
            if (cells == null) continue;
            int baseY = sy << 4;
            for (short cell : cells) {
                int index = cell & 0xFFFF;
                int y = baseY + (index >> 8);
                if (y < -12 || y > 80) continue;
                if (out == null) {
                    out = new ArrayList<class_2338>();
                }
                out.add(new class_2338(baseX + (index & 0xF), y, baseZ + (index >> 4 & 0xF)));
            }
        }
        return out == null ? List.of() : out;
    }

    public void injectForTest(int x, int y, int z, int level) {
        int cx = class_4076.method_18675((int)x);
        int cz = class_4076.method_18675((int)z);
        long key = class_4076.method_18685((int)cx, (int)class_4076.method_18675((int)y), (int)cz);
        byte[] data = this.sections.computeIfAbsent(key, k -> new byte[2048]);
        int index = (y & 0xF) << 8 | (z & 0xF) << 4 | x & 0xF;
        int bi = index >> 1;
        int shift = (index & 1) * 4;
        data[bi] = (byte)(data[bi] & ~(15 << shift) | (level & 0xF) << shift);
        this.cacheLight5(key, data);
        this.markDirty(cx, cz);
    }

    public boolean hasChunk(int chunkX, int chunkZ) {
        for (int sy = TARGET_MIN_SECTION; sy <= TARGET_MAX_SECTION; ++sy) {
            if (!this.sections.containsKey(class_4076.method_18685((int)chunkX, (int)sy, (int)chunkZ))) continue;
            return true;
        }
        return false;
    }
}

