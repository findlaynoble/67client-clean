/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_5498
 */
package dev.sixseven.gametest;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.impl.CustomAccessoriesModule;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_5498;

public class AccessoryFirstPersonGameTest
implements FabricClientGameTest {
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1600, 900);
            context.waitTicks(2);
            world.getServer().runCommand("tp @a 500 -60 500 -90 0");
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc;
                SixSevenClient.modules().susChunkFinder.setEnabled(false);
                SixSevenClient.modules().blockOutline.setEnabled(false);
                SixSevenClient.modules().motionBlur.setEnabled(false);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(-90.0f);
                    mc.field_1724.method_5636(-90.0f);
                    mc.field_1724.method_36457(0.0f);
                }
                AccessoryFirstPersonGameTest.require((acc = SixSevenClient.modules().customAccessories) != null, "CustomAccessories registered");
                acc.firstPerson.set(false);
                acc.color.set(-49508);
                acc.rainbow.set(false);
                acc.glow.set(75.0);
                acc.cape.set(true);
                acc.capeStyle.set("67");
                acc.capePhysics.set(true);
                acc.trail.set(false);
                acc.aura.set(true);
                acc.auraStyle.set("Orbit");
                acc.crown.set(true);
                acc.setEnabled(true);
            });
            context.waitTicks(20);
            context.runOnClient(mc -> mc.field_1690.method_31043(class_5498.field_26665));
            context.waitTicks(6);
            context.takeScreenshot("accessories-fp-1-thirdperson-control");
            context.runOnClient(mc -> mc.field_1690.method_31043(class_5498.field_26664));
            context.waitTicks(6);
            context.takeScreenshot("accessories-fp-2-firstperson-hidden");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36457(60.0f);
                }
            });
            context.waitTicks(6);
            context.takeScreenshot("accessories-fp-3-firstperson-feet-aura-toggleoff");
            context.runOnClient(mc -> SixSevenClient.modules().customAccessories.firstPerson.set(true));
            context.waitTicks(6);
            context.takeScreenshot("accessories-fp-4-firstperson-feet-toggleon");
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.firstPerson.set(false);
                acc.setEnabled(false);
            });
        }
    }

    private static void require(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError((Object)("FAILED: " + what));
        }
    }
}

