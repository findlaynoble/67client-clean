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

public class AccessoryCapeDepthGameTest
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
                AccessoryCapeDepthGameTest.require((acc = SixSevenClient.modules().customAccessories) != null, "CustomAccessories registered");
                acc.firstPerson.set(false);
                acc.color.set(-49508);
                acc.rainbow.set(false);
                acc.glow.set(75.0);
                acc.cape.set(true);
                acc.capeStyle.set("Solid");
                acc.capePhysics.set(false);
                acc.trail.set(false);
                acc.aura.set(false);
                acc.crown.set(false);
                acc.setEnabled(true);
            });
            context.waitTicks(20);
            context.runOnClient(mc -> mc.field_1690.method_31043(class_5498.field_26665));
            context.waitTicks(6);
            context.takeScreenshot("cape-depth-1-solid-back");
            context.runOnClient(mc -> mc.field_1690.method_31043(class_5498.field_26666));
            context.waitTicks(6);
            context.takeScreenshot("cape-depth-2-solid-front");
            context.runOnClient(mc -> SixSevenClient.modules().customAccessories.capeStyle.set("67"));
            context.waitTicks(4);
            context.runOnClient(mc -> mc.field_1690.method_31043(class_5498.field_26665));
            context.waitTicks(6);
            context.takeScreenshot("cape-depth-3-67-back");
            context.runOnClient(mc -> mc.field_1690.method_31043(class_5498.field_26666));
            context.waitTicks(6);
            context.takeScreenshot("cape-depth-4-67-front");
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.cape.set(false);
                acc.aura.set(true);
                acc.auraStyle.set("Orbit");
                acc.firstPerson.set(false);
                mc.field_1690.method_31043(class_5498.field_26664);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36457(60.0f);
                }
            });
            context.waitTicks(6);
            context.takeScreenshot("cape-depth-5-firstperson-aura");
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.firstPerson.set(false);
                acc.aura.set(false);
                acc.cape.set(true);
                acc.capeStyle.set("67");
                acc.capePhysics.set(true);
                acc.setEnabled(false);
                mc.field_1690.method_31043(class_5498.field_26664);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36457(0.0f);
                }
            });
        }
    }

    private static void require(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError((Object)("FAILED: " + what));
        }
    }
}

