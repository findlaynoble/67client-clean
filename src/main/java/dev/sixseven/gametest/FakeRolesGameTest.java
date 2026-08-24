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
import dev.sixseven.gui.ClickGuiScreen;
import dev.sixseven.module.impl.FakeRolesModule;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_2561;

public class FakeRolesGameTest
implements FabricClientGameTest {
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1280, 720);
            context.waitTicks(2);
            TestServerContext server = world.getServer();
            context.runOnClient(mc -> FakeRolesGameTest.require(SixSevenClient.modules().fakeRoles != null, "FakeRoles registered"));
            context.runOnClient(mc -> {
                FakeRolesModule fr = SixSevenClient.modules().fakeRoles;
                fr.setEnabled(true);
                String me = mc.field_1724.method_7334().name();
                fr.role.set("SR.MOD");
                String srmod = fr.decorateChat((class_2561)class_2561.method_43470((String)(me + ": gg"))).getString();
                FakeRolesGameTest.require(srmod.equals("[SR.MOD] " + me + ": gg"), "SR.MOD chat, got: " + srmod);
                fr.role.set("MEDIA");
                FakeRolesGameTest.require(fr.decorateChat((class_2561)class_2561.method_43470((String)(me + ": gg"))).getString().startsWith("[MEDIA] "), "MEDIA chat tag");
                fr.role.set("SR.ADMIN");
                FakeRolesGameTest.require(fr.decorateChat((class_2561)class_2561.method_43470((String)(me + ": gg"))).getString().startsWith("[SR.ADMIN] "), "SR.ADMIN chat tag");
                fr.role.set("MEDIA");
                FakeRolesGameTest.require(fr.decorateChat((class_2561)class_2561.method_43470((String)("<" + me + "> hi"))).getString().equals("<[MEDIA] " + me + "> hi"), "tag spliced right before a mid-line name");
                FakeRolesGameTest.require(fr.decorateTab((class_2561)class_2561.method_43470((String)me), me).getString().equals("[MEDIA] " + me), "tab decorated for self");
                FakeRolesGameTest.require(fr.decorateTab((class_2561)class_2561.method_43470((String)"Notch"), "Notch").getString().equals("Notch"), "tab NOT decorated for others");
                fr.role.set("None");
                FakeRolesGameTest.require(fr.decorateChat((class_2561)class_2561.method_43470((String)(me + ": gg"))).getString().equals(me + ": gg"), "None role adds nothing");
            });
            context.runOnClient(mc -> {
                FakeRolesModule fr = SixSevenClient.modules().fakeRoles;
                String me = mc.field_1724.method_7334().name();
                fr.role.set("SR.MOD");
                mc.field_1705.method_1743().method_1812((class_2561)class_2561.method_43470((String)(me + ": sr.mod flex")));
                fr.role.set("MEDIA");
                mc.field_1705.method_1743().method_1812((class_2561)class_2561.method_43470((String)(me + ": media flex")));
                fr.role.set("SR.ADMIN");
                mc.field_1705.method_1743().method_1812((class_2561)class_2561.method_43470((String)(me + ": sr.admin flex")));
            });
            context.waitTicks(4);
            context.takeScreenshot("fakeroles-chat-all-roles");
            server.runCommand("scoreboard objectives add tabinfo dummy {\"text\":\"Players\"}");
            server.runCommand("scoreboard objectives modify tabinfo numberformat blank");
            server.runCommand("scoreboard objectives setdisplay list tabinfo");
            context.waitTicks(3);
            FakeRolesGameTest.shootTab(context, "SR.MOD", "fakeroles-tab-srmod-green");
            FakeRolesGameTest.shootTab(context, "MEDIA", "fakeroles-tab-media-pink");
            FakeRolesGameTest.shootTab(context, "SR.ADMIN", "fakeroles-tab-sradmin-red");
            context.runOnClient(mc -> mc.field_1690.field_1907.method_23481(false));
            context.runOnClient(mc -> {
                SixSevenClient.modules().fakeRoles.role.set("MEDIA");
                ClickGuiScreen.state().setExpanded("FakeRoles@MISC", true);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("fakeroles-settings-panel");
            context.runOnClient(mc -> ClickGuiScreen.state().setExpanded("FakeRoles@MISC", false));
            context.getInput().pressKey(344);
            context.waitForScreen(null);
        }
    }

    private static void shootTab(ClientGameTestContext context, String role, String shot) {
        context.runOnClient(mc -> {
            FakeRolesModule fr = SixSevenClient.modules().fakeRoles;
            fr.role.set(role);
            fr.setEnabled(true);
            mc.field_1690.field_1907.method_23481(true);
        });
        context.waitTicks(3);
        context.takeScreenshot(shot);
    }

    private static void require(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError((Object)("FAILED: " + what));
        }
    }
}

