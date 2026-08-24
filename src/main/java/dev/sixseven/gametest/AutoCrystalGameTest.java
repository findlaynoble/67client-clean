/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_1041
 *  net.minecraft.class_1511
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_238
 *  net.minecraft.class_239
 *  net.minecraft.class_239$class_240
 *  net.minecraft.class_243
 *  net.minecraft.class_3675
 *  net.minecraft.class_3965
 *  net.minecraft.class_746
 */
package dev.sixseven.gametest;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.widget.KeybindWidget;
import dev.sixseven.module.impl.AutoCrystalModule;
import dev.sixseven.settings.KeybindSetting;
import java.util.List;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_1041;
import net.minecraft.class_1511;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3675;
import net.minecraft.class_3965;
import net.minecraft.class_746;

public class AutoCrystalGameTest
implements FabricClientGameTest {
    private static final int TRIGGER_KEY = 71;
    private static final class_2338 BASE = new class_2338(0, 1, 0);
    private static final class_238 CRYSTAL_BOX = new class_238(-0.5, 1.5, -0.5, 1.5, 5.5, 2.5);

    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1280, 720);
            context.waitTicks(2);
            TestServerContext server = world.getServer();
            context.runOnClient(mc -> AutoCrystalGameTest.require(SixSevenClient.modules().autoCrystal != null, "AutoCrystal registered"));
            this.buildArena(context, server, "obsidian");
            context.runOnClient(mc -> {
                AutoCrystalModule ac = SixSevenClient.modules().autoCrystal;
                ac.activateKey.set(71);
                ac.placeDelay.set(0.0);
                ac.breakDelay.set(0.0);
                ac.obsidianDelay.set(0.0);
                ac.autoObsidian.set(true);
                ac.switchBack.set(false);
                ac.range.set(1.0);
                ac.setEnabled(true);
            });
            context.takeScreenshot("autocrystal-aim");
            context.runOnClient(mc -> AutoCrystalGameTest.require(AutoCrystalGameTest.isAimingAtBase(mc.field_1765), "aim lands on the base, got " + AutoCrystalGameTest.describeHit(mc.field_1765)));
            context.getInput().holdKey(71);
            context.waitTicks(6);
            int[] capturedId = new int[1];
            context.runOnClient(mc -> {
                List crystals = mc.field_1687.method_18467(class_1511.class, CRYSTAL_BOX);
                if (crystals.size() != 1) {
                    String diag = "hand=" + String.valueOf(mc.field_1724.method_6047().method_7909()) + " off=" + String.valueOf(mc.field_1724.method_6079().method_7909()) + " sel=" + mc.field_1724.method_31548().method_67532() + " base=" + String.valueOf(mc.field_1687.method_8320(new class_2338(0, 1, 0))) + " above=" + String.valueOf(mc.field_1687.method_8320(new class_2338(0, 2, 0))) + " trigHeld=" + class_3675.method_15987((class_1041)mc.method_22683(), (int)71) + " enabled=" + SixSevenClient.modules().autoCrystal.isEnabled() + " hit=" + AutoCrystalGameTest.describeHit(mc.field_1765);
                    AutoCrystalGameTest.require(false, "one crystal placed on the obsidian base, got " + crystals.size() + " | " + diag);
                }
                capturedId[0] = ((class_1511)crystals.get(0)).method_5628();
            });
            context.takeScreenshot("autocrystal-placed");
            context.runOnClient(mc -> SixSevenClient.modules().autoCrystal.range.set(3.0));
            context.waitTicks(6);
            context.runOnClient(mc -> {
                List<class_1511> survivors = mc.field_1687.method_18467(class_1511.class, CRYSTAL_BOX).stream().filter(c -> c.method_5628() == capturedId[0]).toList();
                if (!survivors.isEmpty()) {
                    double dist = mc.field_1724.method_33571().method_1022(survivors.get(0).method_73189());
                    AutoCrystalGameTest.require(false, "crystal id " + capturedId[0] + " should have been broken | dist=" + String.format(Locale.ROOT, "%.2f", dist) + " hit=" + AutoCrystalGameTest.describeHit(mc.field_1765) + " hand=" + String.valueOf(mc.field_1724.method_6047().method_7909()));
                }
            });
            context.takeScreenshot("autocrystal-broken");
            context.getInput().releaseKey(71);
            context.waitTicks(2);
            this.buildArena(context, server, "stone");
            context.runOnClient(mc -> SixSevenClient.modules().autoCrystal.range.set(1.0));
            context.runOnClient(mc -> AutoCrystalGameTest.require(AutoCrystalGameTest.isAimingAtBase(mc.field_1765), "aim lands on the stone block, got " + AutoCrystalGameTest.describeHit(mc.field_1765)));
            context.getInput().holdKey(71);
            context.waitTicks(8);
            context.runOnClient(mc -> {
                boolean obiTop = mc.field_1687.method_8320(new class_2338(0, 2, 0)).method_27852(class_2246.field_10540);
                boolean obiSide = mc.field_1687.method_8320(new class_2338(0, 1, 1)).method_27852(class_2246.field_10540);
                AutoCrystalGameTest.require(obiTop || obiSide, "auto-obsidian laid a base (expected at 0,2,0 or 0,1,1)");
                int crystals = mc.field_1687.method_18467(class_1511.class, new class_238(-0.5, 1.5, -0.5, 1.5, 6.5, 2.5)).size();
                AutoCrystalGameTest.require(crystals >= 1, "a crystal was placed on the auto-laid obsidian, got " + crystals);
            });
            context.takeScreenshot("autocrystal-obsidian");
            context.getInput().releaseKey(71);
            context.runOnClient(mc -> SixSevenClient.modules().autoCrystal.setEnabled(false));
            context.waitTicks(2);
            context.runOnClient(mc -> {
                KeybindSetting kb = new KeybindSetting("Test", "", -1);
                KeybindWidget widget = new KeybindWidget(SixSevenClient.themes(), kb);
                widget.setBounds(0.0f, 0.0f, 100.0f);
                AutoCrystalGameTest.require(widget.mouseClicked(10.0f, 10.0f, 0), "arming left-click is consumed");
                AutoCrystalGameTest.require(widget.isListening(), "widget listens after the arming click");
                AutoCrystalGameTest.require(widget.mouseClicked(10.0f, 10.0f, 1), "the bind right-click is consumed");
                AutoCrystalGameTest.require(!widget.isListening(), "widget stops listening once bound");
                AutoCrystalGameTest.require((Integer)kb.get() == 1, "right-click bound RMB, got " + String.valueOf(kb.get()));
                AutoCrystalGameTest.require(kb.keyName().equals("RMB"), "the bind shows as 'RMB', got " + kb.keyName());
                widget.mouseClicked(10.0f, 10.0f, 0);
                AutoCrystalGameTest.require(widget.keyPressed(86), "a keyboard key still binds");
                AutoCrystalGameTest.require((Integer)kb.get() == 86, "V bound, got " + String.valueOf(kb.get()));
                widget.mouseClicked(10.0f, 10.0f, 0);
                widget.keyPressed(256);
                AutoCrystalGameTest.require((Integer)kb.get() == -1, "ESC clears the bind");
            });
        }
    }

    private void buildArena(ClientGameTestContext context, TestServerContext server, String baseBlock) {
        server.runCommand("kill @e[type=minecraft:end_crystal]");
        server.runCommand("fill -20 0 -20 20 0 20 minecraft:stone");
        server.runCommand("fill -20 1 -20 20 8 20 minecraft:air");
        server.runCommand("setblock 0 1 0 minecraft:" + baseBlock);
        server.runCommand("gamemode creative @a");
        server.runCommand("clear @a");
        server.runCommand("give @a minecraft:end_crystal 64");
        server.runCommand("give @a minecraft:obsidian 64");
        server.runCommand("tp @a 0.5 1 2.5 180 15");
        context.waitTicks(5);
        context.runOnClient(mc -> AutoCrystalGameTest.aimAt(mc.field_1724, 0.5, 2.0, 0.5));
        context.waitTicks(3);
    }

    private static void aimAt(class_746 player, double tx, double ty, double tz) {
        class_243 eye = player.method_33571();
        double dx = tx - eye.field_1352;
        double dy = ty - eye.field_1351;
        double dz = tz - eye.field_1350;
        double horiz = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float)Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float)(-Math.toDegrees(Math.atan2(dy, horiz)));
        player.method_36456(yaw);
        player.method_36457(pitch);
        player.method_5847(yaw);
    }

    private static boolean isAimingAtBase(class_239 hit) {
        class_3965 bhr;
        return hit instanceof class_3965 && (bhr = (class_3965)hit).method_17783() == class_239.class_240.field_1332 && bhr.method_17777().equals((Object)BASE);
    }

    private static String describeHit(class_239 hit) {
        class_3965 bhr;
        if (hit instanceof class_3965 && (bhr = (class_3965)hit).method_17783() == class_239.class_240.field_1332) {
            return "block " + String.valueOf(bhr.method_17777()) + " face " + String.valueOf(bhr.method_17780());
        }
        return hit == null ? "null" : hit.method_17783().toString();
    }

    private static void require(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError((Object)("FAILED: " + what));
        }
    }
}

