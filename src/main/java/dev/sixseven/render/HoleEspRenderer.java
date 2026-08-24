/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2338$class_2339
 *  net.minecraft.class_243
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_4587
 *  net.minecraft.class_4597$class_4598
 *  net.minecraft.class_638
 *  net.minecraft.class_746
 *  org.joml.Vector3fc
 */
package dev.sixseven.render;

import dev.sixseven.module.impl.DebugHoleEspModule;
import dev.sixseven.render.EspBoxRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.util.Colors;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_638;
import net.minecraft.class_746;
import org.joml.Vector3fc;

public final class HoleEspRenderer {
    private static final int SCAN_INTERVAL_TICKS = 10;
    private static final int RADIUS = 16;
    private static final int VERTICAL = 6;
    private static final Set<class_2248> UNBREAKABLE = Set.of(class_2246.field_10540, class_2246.field_22423, class_2246.field_9987, class_2246.field_23152, class_2246.field_38420);
    private static volatile List<Hole> cache = List.of();
    private static int tickCounter;

    private HoleEspRenderer() {
    }

    public static void clear() {
        cache = List.of();
        tickCounter = 0;
    }

    public static void scan(DebugHoleEspModule module) {
        if (tickCounter++ % 10 != 0) {
            return;
        }
        class_310 mc = class_310.method_1551();
        class_638 level = mc.field_1687;
        class_746 player = mc.field_1724;
        if (level == null || player == null) {
            return;
        }
        int need = module.depth.is(Deobf.decrypt("2B1")) ? 1 : Integer.parseInt((String)module.depth.get());
        class_2338 origin = player.method_24515();
        class_2338.class_2339 p = new class_2338.class_2339();
        ArrayList<Hole> found = new ArrayList<Hole>();
        for (int dx = -16; dx <= 16; ++dx) {
            for (int dz = -16; dz <= 16; ++dz) {
                for (int dy = -6; dy <= 6; ++dy) {
                    int z;
                    int y;
                    int x = origin.method_10263() + dx;
                    if (!HoleEspRenderer.isHole(level, p, x, y = origin.method_10264() + dy, z = origin.method_10260() + dz, need)) continue;
                    found.add(new Hole(x, y, z, HoleEspRenderer.wallsSafe(level, p, x, y, z)));
                }
            }
        }
        cache = found;
    }

    public static void render(class_4597.class_4598 bufferSource, class_4587 poseStack, class_243 cam, DebugHoleEspModule module) {
        List<Hole> snapshot = cache;
        if (snapshot.isEmpty()) {
            return;
        }
        int safeColor = (Integer)module.safe.get();
        int unsafeColor = (Integer)module.unsafe.get();
        boolean tracers = (Boolean)module.tracers.get();
        Vector3fc forward = tracers ? class_310.method_1551().field_1773.method_19418().method_19335() : null;
        for (Hole hole : snapshot) {
            int color = hole.safe() ? safeColor : unsafeColor;
            EspBoxRenderer.outline(bufferSource, poseStack, cam, hole.x(), hole.y(), hole.z(), hole.x() + 1, hole.y() + 1, hole.z() + 1, color, 2.0f);
            if (!tracers) continue;
            EspBoxRenderer.tracer(bufferSource, poseStack, cam, forward, (double)hole.x() + 0.5, (double)hole.y() + 0.5, (double)hole.z() + 0.5, Colors.withAlpha(color, 0.72f), 1.2f);
        }
        EspBoxRenderer.flush(bufferSource);
    }

    private static boolean isHole(class_638 level, class_2338.class_2339 p, int x, int y, int z, int need) {
        if (!level.method_8320((class_2338)p.method_10103(x, y - 1, z)).method_51366()) {
            return false;
        }
        for (int i = 0; i < need; ++i) {
            if (level.method_8320((class_2338)p.method_10103(x, y + i, z)).method_26215()) continue;
            return false;
        }
        return level.method_8320((class_2338)p.method_10103(x + 1, y, z)).method_51366() && level.method_8320((class_2338)p.method_10103(x - 1, y, z)).method_51366() && level.method_8320((class_2338)p.method_10103(x, y, z + 1)).method_51366() && level.method_8320((class_2338)p.method_10103(x, y, z - 1)).method_51366();
    }

    private static boolean wallsSafe(class_638 level, class_2338.class_2339 p, int x, int y, int z) {
        return HoleEspRenderer.isUnbreakable(level.method_8320((class_2338)p.method_10103(x, y - 1, z))) && HoleEspRenderer.isUnbreakable(level.method_8320((class_2338)p.method_10103(x + 1, y, z))) && HoleEspRenderer.isUnbreakable(level.method_8320((class_2338)p.method_10103(x - 1, y, z))) && HoleEspRenderer.isUnbreakable(level.method_8320((class_2338)p.method_10103(x, y, z + 1))) && HoleEspRenderer.isUnbreakable(level.method_8320((class_2338)p.method_10103(x, y, z - 1)));
    }

    private static boolean isUnbreakable(class_2680 state) {
        return UNBREAKABLE.contains(state.method_26204());
    }

    private record Hole(int x, int y, int z, boolean safe) {
    }
}

