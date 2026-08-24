/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_2561
 *  net.minecraft.class_5250
 */
package dev.sixseven.gametest;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.staff.StaffDetector;
import dev.sixseven.staff.StaffEntry;
import dev.sixseven.staff.StaffTracker;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_2561;
import net.minecraft.class_5250;

public class StaffListGameTest
implements FabricClientGameTest {
    public void runTest(ClientGameTestContext context) {
        StaffListGameTest.verifyDetection();
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1600, 900);
            context.waitTicks(2);
            context.runOnClient(mc -> {
                ModuleManager m = SixSevenClient.modules();
                StaffListGameTest.require(m.staffList != null, "StaffList registered");
                m.staffList.setEnabled(true);
                m.staffList.showRank.set(true);
                m.staffList.showPing.set(true);
                StaffTracker.injectForTest(StaffListGameTest.roster());
            });
            context.waitTicks(10);
            context.takeScreenshot("stafflist-roster");
            context.runOnClient(mc -> StaffTracker.injectForTest(StaffListGameTest.bigRoster()));
            context.waitTicks(10);
            context.takeScreenshot("stafflist-overflow");
            context.runOnClient(mc -> StaffTracker.injectForTest(List.of()));
            context.waitTicks(10);
            context.takeScreenshot("stafflist-empty");
            context.runOnClient(mc -> StaffTracker.clearInject());
        }
    }

    private static void verifyDetection() {
        StaffDetector.DetectConfig cfg = new StaffDetector.DetectConfig("Star + Rank", Set.of("knownstaff"), StaffDetector.DEFAULT_RANK_KEYWORDS, "\u2605\u2606\u2726\u2727\u272a\u2729\u272b\u272c\u272d\u272e\u272f\u2b50\u2730\u2742\u269d\u2734\u2735\u2736\u2737\u2738\u2739\u235f", true, true);
        class_5250 starName = class_2561.method_43473().method_10852((class_2561)class_2561.method_43470((String)"\u2605 ").method_54663(0xFF5555)).method_10852((class_2561)class_2561.method_43470((String)"StaffGuy"));
        StaffEntry star = StaffDetector.classify("StaffGuy", (class_2561)starName, null, null, null, false, 30, cfg);
        StaffListGameTest.require(star != null, "star-marked player detected");
        StaffListGameTest.require(star.color() == -43691, "star colour captured (got " + Integer.toHexString(star == null ? 0 : star.color()) + ")");
        StaffListGameTest.require(star.rankLabel().isEmpty(), "star-only entry has no rank label");
        StaffEntry admin = StaffDetector.classify("AdminDude", (class_2561)class_2561.method_43470((String)"Admin | AdminDude"), null, null, null, false, 40, cfg);
        StaffListGameTest.require(admin != null, "text-rank player detected");
        StaffListGameTest.require("Admin".equals(admin.rankLabel()), "rank label resolves to Admin (got " + (admin == null ? "null" : admin.rankLabel()) + ")");
        StaffEntry known = StaffDetector.classify("KnownStaff", (class_2561)class_2561.method_43470((String)"KnownStaff"), null, null, null, false, 50, cfg);
        StaffListGameTest.require(known != null, "allowlisted player detected");
        StaffEntry pua = StaffDetector.classify("PuaMod", (class_2561)class_2561.method_43470((String)"\ue001 PuaMod"), null, null, null, false, 60, cfg);
        StaffListGameTest.require(pua != null, "PUA font-icon staff detected");
        StaffEntry none = StaffDetector.classify("RandomKid", (class_2561)class_2561.method_43470((String)"RandomKid"), null, null, null, false, 70, cfg);
        StaffListGameTest.require(none == null, "ordinary player is not flagged");
        StaffEntry tricky = StaffDetector.classify("xX_admin_Xx", (class_2561)class_2561.method_43470((String)"xX_admin_Xx"), null, null, null, false, 70, cfg);
        StaffListGameTest.require(tricky == null, "username containing 'admin' is not flagged as staff");
        StaffDetector.DetectConfig starOnly = new StaffDetector.DetectConfig("Star Only", Set.of(), StaffDetector.DEFAULT_RANK_KEYWORDS, "\u2605\u2606\u2726\u2727\u272a\u2729\u272b\u272c\u272d\u272e\u272f\u2b50\u2730\u2742\u269d\u2734\u2735\u2736\u2737\u2738\u2739\u235f", true, true);
        StaffListGameTest.require(StaffDetector.classify("AdminDude", (class_2561)class_2561.method_43470((String)"Admin | AdminDude"), null, null, null, false, 40, starOnly) == null, "Star Only ignores text ranks");
        StaffListGameTest.require(StaffDetector.classify("StaffGuy", (class_2561)starName, null, null, null, false, 30, starOnly) != null, "Star Only still detects the star");
    }

    private static List<StaffEntry> roster() {
        return List.of(new StaffEntry("Owner_Jeff", "Owner", -45747, false, 22, 19), new StaffEntry("Admin_Kate", "Admin", -26368, false, 48, 17), new StaffEntry("ModSquad", "Mod", -11141291, false, 130, 11), new StaffEntry("HelperHank", "Helper", -11141121, false, 250, 8), new StaffEntry("StealthWatch", "Mod", -11141291, true, 90, 11), new StaffEntry("Twinkles", "", 0, false, 70, 1));
    }

    private static List<StaffEntry> bigRoster() {
        ArrayList<StaffEntry> l = new ArrayList<StaffEntry>(StaffListGameTest.roster());
        l.add(new StaffEntry("BuilderBob", "Builder", -11143, false, 40, 4));
        l.add(new StaffEntry("SupportSue", "Support", -6584321, false, 55, 3));
        l.add(new StaffEntry("TrialTom", "Trial", -5197648, false, 300, 2));
        return l;
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError((Object)message);
        }
    }
}

