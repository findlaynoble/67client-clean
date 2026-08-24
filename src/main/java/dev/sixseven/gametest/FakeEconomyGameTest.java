/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_2561
 */
package dev.sixseven.gametest;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.FakePayModule;
import dev.sixseven.module.impl.FakeStatsModule;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_2561;

public class FakeEconomyGameTest
implements FabricClientGameTest {
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1600, 900);
            context.waitTicks(2);
            TestServerContext server = world.getServer();
            context.runOnClient(mc -> {
                ModuleManager m = SixSevenClient.modules();
                FakeEconomyGameTest.require(m.fakePay != null, "FakePay registered");
                FakeEconomyGameTest.require(m.fakeStats != null, "FakeStats registered");
            });
            server.runCommand("scoreboard objectives add donut dummy {\"text\":\"DonutSMP\",\"color\":\"gold\"}");
            server.runCommand("scoreboard objectives modify donut numberformat blank");
            server.runCommand("scoreboard objectives setdisplay sidebar donut");
            FakeEconomyGameTest.addLine(server, "t1", "$ ", "green", "100", 6);
            FakeEconomyGameTest.addLine(server, "t2", "* ", "light_purple", "200", 5);
            FakeEconomyGameTest.addLine(server, "t3", "# ", "red", "300", 4);
            FakeEconomyGameTest.addLine(server, "t4", "% ", "gray", "400", 3);
            FakeEconomyGameTest.addLine(server, "t5", "= ", "yellow", "5m5s", 2);
            server.runCommand("scoreboard players set play.donutsmp.net donut 1");
            context.waitTicks(5);
            context.runOnClient(mc -> {
                FakeStatsModule fs = SixSevenClient.modules().fakeStats;
                fs.money.set("777m");
                fs.moneyLine.set(1.0);
                fs.shards.set("9,999");
                fs.shardsLine.set(2.0);
                fs.kills.set("1,000");
                fs.killsLine.set(3.0);
                fs.deaths.set("0");
                fs.deathsLine.set(4.0);
                fs.playtime.set("365d 12h");
                fs.playtimeLine.set(5.0);
                fs.setEnabled(true);
                fs.beginSidebar();
                FakeEconomyGameTest.require(FakeEconomyGameTest.draw(fs, "$ 100").contains("777M"), "line 1 = money abbreviated");
                FakeEconomyGameTest.require(FakeEconomyGameTest.draw(fs, "* 200").equals("* 9,999"), "line 2 = shards");
                FakeEconomyGameTest.require(FakeEconomyGameTest.draw(fs, "# 300").equals("# 1,000"), "line 3 = kills");
                FakeEconomyGameTest.require(FakeEconomyGameTest.draw(fs, "% 400").equals("% 0"), "line 4 = deaths");
                FakeEconomyGameTest.require(FakeEconomyGameTest.draw(fs, "= 5m5s").equals("= 365d 12h"), "line 5 = playtime");
                FakeEconomyGameTest.require(FakeEconomyGameTest.draw(fs, "play.donutsmp.net").equals("play.donutsmp.net"), "line 6 = footer untouched");
            });
            context.waitTicks(2);
            context.takeScreenshot("fakestats-icon-sidebar");
            context.runOnClient(mc -> {
                FakeStatsModule fs = SixSevenClient.modules().fakeStats;
                FakePayModule fp = SixSevenClient.modules().fakePay;
                fs.money.set("1m");
                fp.currency.set("$");
                fp.feedback.set("Both");
                fp.setEnabled(true);
                double before = fs.getLiveBalance();
                FakeEconomyGameTest.require(before == 1000000.0, "live balance seeded to 1m, got " + before);
                mc.field_1724.field_3944.method_45730("pay Notch 250k");
                FakeEconomyGameTest.require(fs.getLiveBalance() == 750000.0, "pay deducted 250k: 1m -> " + fs.getLiveBalance());
            });
            context.waitTicks(2);
            context.takeScreenshot("fakepay-paid-newcolors");
            context.runOnClient(mc -> {
                FakeStatsModule fs = SixSevenClient.modules().fakeStats;
                mc.field_1724.field_3944.method_45730("pay Notch 999m");
                FakeEconomyGameTest.require(fs.getLiveBalance() == 750000.0, "insufficient pay left balance untouched");
                mc.field_1724.field_3944.method_45730("pay " + mc.field_1724.method_7334().name() + " 1");
                FakeEconomyGameTest.require(fs.getLiveBalance() == 750000.0, "self-pay blocked");
            });
            context.runOnClient(mc -> mc.field_1724.field_3944.method_45730("bal"));
            context.waitTicks(2);
            context.takeScreenshot("fakestats-bal-command");
        }
    }

    private static void addLine(TestServerContext server, String team, String prefix, String color, String holder, int score) {
        server.runCommand("team add " + team);
        server.runCommand("team modify " + team + " prefix {\"text\":\"" + prefix + "\",\"color\":\"" + color + "\"}");
        server.runCommand("team join " + team + " " + holder);
        server.runCommand("scoreboard players set " + holder + " donut " + score);
    }

    private static String draw(FakeStatsModule fs, String line) {
        return fs.rewriteForDraw((class_2561)class_2561.method_43470((String)line)).getString();
    }

    private static void require(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError((Object)("FAILED: " + what));
        }
    }
}

