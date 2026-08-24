/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_1297
 *  net.minecraft.class_1304
 *  net.minecraft.class_1531
 *  net.minecraft.class_1657
 *  net.minecraft.class_1923
 *  net.minecraft.class_2246
 *  net.minecraft.class_2338
 *  net.minecraft.class_243
 *  net.minecraft.class_2561
 *  net.minecraft.class_2680
 *  net.minecraft.class_408
 *  net.minecraft.class_437
 *  net.minecraft.class_490
 *  net.minecraft.class_5498
 */
package dev.sixseven.gametest;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.ClickGuiScreen;
import dev.sixseven.gui.IconPickerScreen;
import dev.sixseven.module.Modules;
import dev.sixseven.module.impl.ChunkFinderModule;
import dev.sixseven.module.impl.CustomAccessoriesModule;
import dev.sixseven.module.impl.CustomCrosshairModule;
import dev.sixseven.module.impl.CustomFovModule;
import dev.sixseven.module.impl.CustomGlintModule;
import dev.sixseven.module.impl.FreecamModule;
import dev.sixseven.module.impl.HitParticlesModule;
import dev.sixseven.module.impl.MotionBlurModule;
import dev.sixseven.module.impl.NameProtectModule;
import dev.sixseven.module.impl.NameTagsModule;
import dev.sixseven.module.impl.SpawnerNametagsModule;
import dev.sixseven.module.impl.StorageEspModule;
import dev.sixseven.notification.NotificationManager;
import dev.sixseven.render.BlockEspRenderer;
import dev.sixseven.render.MotionBlurRenderer;
import dev.sixseven.render.StorageEspRenderer;
import dev.sixseven.suschunk.ServerLightCache;
import dev.sixseven.suschunk.SusChunkScanner;
import dev.sixseven.theme.Theme;
import dev.sixseven.util.Colors;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1531;
import net.minecraft.class_1657;
import net.minecraft.class_1923;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_408;
import net.minecraft.class_437;
import net.minecraft.class_490;
import net.minecraft.class_5498;

public class SixSevenClientGameTest
implements FabricClientGameTest {
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            String[] glintGear;
            int i;
            int z;
            int x;
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1600, 900);
            context.waitTicks(2);
            TestServerContext server = world.getServer();
            for (x = -60; x <= -58; ++x) {
                for (z = 8; z <= 10; ++z) {
                    server.runCommand("setblock " + x + " 0 " + z + " minecraft:budding_amethyst");
                    server.runCommand("setblock " + x + " 1 " + z + " minecraft:amethyst_cluster");
                }
            }
            for (x = 8; x <= 12; ++x) {
                for (z = 8; z <= 11; ++z) {
                    server.runCommand("setblock " + x + " 0 " + z + " minecraft:budding_amethyst");
                }
            }
            for (x = 20; x <= 23; ++x) {
                for (z = 8; z <= 10; ++z) {
                    server.runCommand("setblock " + x + " 0 " + z + " minecraft:budding_amethyst");
                }
            }
            for (x = 14; x <= 17; ++x) {
                server.runCommand("setblock " + x + " 0 64 minecraft:budding_amethyst");
            }
            for (x = 97; x <= 99; ++x) {
                for (z = 9; z <= 11; ++z) {
                    server.runCommand("setblock " + x + " -1 " + z + " minecraft:dirt");
                    server.runCommand("setblock " + x + " 0 " + z + " minecraft:sweet_berry_bush[age=3]");
                }
            }
            server.runCommand("setblock 40 0 40 minecraft:beehive[honey_level=5]");
            server.runCommand("setblock 40 0 72 minecraft:beehive[honey_level=5]");
            server.runCommand("setblock 72 0 40 minecraft:beehive[honey_level=0]");
            context.waitTicks(10);
            context.runOnClient(mc -> {
                Modules.SusChunkFinderModule finder = SixSevenClient.modules().susChunkFinder;
                finder.sensitivity.set(3.0);
                finder.notifications.set("Toast");
                finder.smartMode.set(true);
                finder.outline.set(true);
                finder.showOnRadar.set(true);
                finder.renderY.set(-59.0);
                finder.setEnabled(true);
            });
            server.runCommand("tp @a 3000 -58 3000");
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(-90.0f);
                    mc.field_1724.method_36457(0.0f);
                }
            });
            context.waitTicks(60);
            context.runOnClient(mc -> {
                SusChunkScanner scanner = SixSevenClient.modules().susChunkFinder.scanner;
                SixSevenClientGameTest.assertThat(scanner.flags().isEmpty(), "fresh area must produce zero flags, got " + scanner.flags().size());
            });
            context.takeScreenshot("01-fresh-area-clean");
            server.runCommand("tp @a -14 -58 -10");
            world.getClientWorld().waitForChunksRender();
            context.waitTicks(80);
            context.runOnClient(mc -> {
                SusChunkScanner scanner = SixSevenClient.modules().susChunkFinder.scanner;
                boolean realGeodeFlagged = scanner.flags().stream().anyMatch(f -> f.chunkKey() == class_1923.method_8331((int)-4, (int)0));
                SixSevenClientGameTest.assertThat(realGeodeFlagged, "REAL geode (visible clusters, packet light) must flag after chunk reload");
            });
            context.runOnClient(mc -> {
                int z;
                int x;
                for (x = 8; x <= 12; ++x) {
                    for (z = 8; z <= 11; ++z) {
                        ServerLightCache.get().injectForTest(x, 1, z, 5);
                    }
                }
                for (x = 20; x <= 23; ++x) {
                    for (z = 8; z <= 10; ++z) {
                        ServerLightCache.get().injectForTest(x, 1, z, 5);
                    }
                }
            });
            context.waitTicks(30);
            context.runOnClient(mc -> {
                SusChunkScanner scanner = SixSevenClient.modules().susChunkFinder.scanner;
                SixSevenClientGameTest.assertThat(scanner.flags().size() == 3, "real geode + two synth chunks must flag, got " + scanner.flags().size());
                SixSevenClientGameTest.assertThat(scanner.zones().size() == 2, "expected 2 zones (real geode; merged synth pair), got " + scanner.zones().size());
            });
            server.runCommand("tp @a 16 -58 64");
            world.getClientWorld().waitForChunksRender();
            context.waitTicks(25);
            context.runOnClient(mc -> {
                for (int x = 14; x <= 17; ++x) {
                    ServerLightCache.get().injectForTest(x, 1, 64, 5);
                }
            });
            context.waitTicks(30);
            context.runOnClient(mc -> {
                SusChunkScanner scanner = SixSevenClient.modules().susChunkFinder.scanner;
                boolean left = scanner.flags().stream().anyMatch(f -> f.chunkKey() == class_1923.method_8331((int)0, (int)4));
                boolean right = scanner.flags().stream().anyMatch(f -> f.chunkKey() == class_1923.method_8331((int)1, (int)4));
                SixSevenClientGameTest.assertThat(left && right, "split geode: BOTH 2-cluster sub-chunks must flag via per-geode counting");
                boolean sameZone = scanner.zones().stream().anyMatch(z -> z.members().contains(class_1923.method_8331((int)0, (int)4)) && z.members().contains(class_1923.method_8331((int)1, (int)4)));
                SixSevenClientGameTest.assertThat(sameZone, "split geode's two chunks must share one zone");
            });
            server.runCommand("tp @a 16 -33 92");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(180.0f);
                    mc.field_1724.method_36457(46.0f);
                }
            });
            context.waitTicks(3);
            context.takeScreenshot("02b-split-geode-both-chunks");
            server.runCommand("tp @a -58 -42 35");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(180.0f);
                    mc.field_1724.method_36457(36.0f);
                }
            });
            context.waitTicks(3);
            context.takeScreenshot("02-real-geode-flagged");
            server.runCommand("tp @a -20 -35 -8");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(-66.0f);
                    mc.field_1724.method_36457(33.0f);
                }
            });
            context.waitTicks(3);
            context.takeScreenshot("03-smart-merged-quad");
            context.runOnClient(mc -> SixSevenClient.modules().susChunkFinder.smartMode.set(false));
            server.runCommand("tp @a -20 -35 -8");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(-66.0f);
                    mc.field_1724.method_36457(33.0f);
                }
            });
            context.waitTicks(3);
            context.takeScreenshot("04-per-chunk");
            context.runOnClient(mc -> SixSevenClient.modules().susChunkFinder.smartMode.set(true));
            server.runCommand("tp @a -20 -58 -8");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(-66.0f);
                    mc.field_1724.method_36457(10.0f);
                }
            });
            context.waitTicks(10);
            context.takeScreenshot("05-radar-inrange");
            server.runCommand("tp @a 100 -58 8");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(-90.0f);
                    mc.field_1724.method_36457(5.0f);
                }
            });
            context.waitTicks(25);
            context.takeScreenshot("06-radar-clamped-label");
            context.runOnClient(mc -> {
                Modules.SusChunkFinderModule finder = SixSevenClient.modules().susChunkFinder;
                finder.notifications.set("Chat");
                finder.sensitivity.set(2.0);
            });
            context.waitTicks(30);
            context.runOnClient(mc -> {
                SusChunkScanner scanner = SixSevenClient.modules().susChunkFinder.scanner;
                boolean berryFlagged = scanner.flags().stream().anyMatch(f -> f.chunkKey() == class_1923.method_8331((int)6, (int)0));
                SixSevenClientGameTest.assertThat(berryFlagged, "berry chunk must join at sensitivity 2");
            });
            context.takeScreenshot("07-berries-sens2-chat");
            context.runOnClient(mc -> {
                Modules.SusChunkFinderModule finder = SixSevenClient.modules().susChunkFinder;
                finder.sensitivity.set(3.0);
                finder.notifications.set("Toast");
                ClickGuiScreen.state().setExpanded("SusChunkFinder@RENDER", true);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(10);
            context.takeScreenshot("08-settings");
            context.runOnClient(mc -> {
                SixSevenClient.modules().susChunkFinder.setEnabled(false);
                ClickGuiScreen.state().setExpanded("SusChunkFinder@RENDER", false);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(null);
            String[] containers = new String[]{"chest", "trapped_chest", "barrel", "white_shulker_box", "furnace", "blast_furnace", "smoker", "hopper", "dropper", "dispenser", "ender_chest", "brewing_stand", "spawner"};
            for (int i2 = 0; i2 < containers.length; ++i2) {
                server.runCommand("setblock " + (2 + i2) + " -59 25 minecraft:" + containers[i2]);
            }
            server.runCommand("tp @a 3000 -58 3000");
            world.getClientWorld().waitForChunksRender();
            server.runCommand("tp @a 8 -57 18");
            world.getClientWorld().waitForChunksRender();
            context.waitTicks(10);
            context.runOnClient(mc -> {
                StorageEspModule esp = SixSevenClient.modules().storageEsp;
                esp.tracers.set(false);
                esp.setEnabled(true);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(12.0f);
                }
            });
            context.waitTicks(40);
            context.runOnClient(mc -> {
                int count = StorageEspRenderer.cachedCount();
                long shulkers = StorageEspRenderer.cachedShulkerCount();
                StringBuilder seen = new StringBuilder();
                if (mc.field_1687 != null) {
                    for (int i = 0; i < containers.length; ++i) {
                        seen.append(mc.field_1687.method_8320(new class_2338(2 + i, -59, 25)).method_26204().method_9518().getString()).append(' ');
                    }
                }
                String ctx = " [cache=" + count + "/" + containers.length + " shulkers=" + shulkers + " playerChunk=" + String.valueOf(mc.field_1724 != null ? mc.field_1724.method_31476() : "null") + " clientBlocks=" + seen.toString().trim() + "]";
                SixSevenClientGameTest.assertThat(count >= containers.length, "StorageESP must detect every container type," + ctx);
                SixSevenClientGameTest.assertThat(shulkers >= 1L, "StorageESP must detect the shulker box," + ctx);
            });
            context.takeScreenshot("10-storage-esp-all-types");
            context.runOnClient(mc -> SixSevenClient.modules().storageEsp.setEnabled(false));
            server.runCommand("tp @a 8 -57 18");
            context.runOnClient(mc -> {
                StorageEspModule esp = SixSevenClient.modules().storageEsp;
                esp.tracers.set(true);
                esp.setEnabled(true);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(8.0f);
                }
            });
            context.waitTicks(20);
            context.takeScreenshot("10b-tracers-front-crosshair");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(90.0f);
                }
            });
            context.waitTicks(20);
            context.takeScreenshot("10c-tracers-offcamera-side");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(180.0f);
                }
            });
            context.waitTicks(20);
            context.takeScreenshot("10d-tracers-behind");
            context.runOnClient(mc -> {
                StorageEspModule esp = SixSevenClient.modules().storageEsp;
                esp.tracers.set(false);
                esp.setEnabled(false);
            });
            server.runCommand("tp @a 8 -57 18");
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(0.0f);
                }
                CustomCrosshairModule xhair = SixSevenClient.modules().customCrosshair;
                xhair.hideVanilla.set(true);
                xhair.rainbow.set(false);
                xhair.setEnabled(true);
            });
            String[] xhairStyles = new String[]{"Cross", "Dot", "Circle", "T-Shape", "Brackets", "Chevron", "67"};
            for (int i3 = 0; i3 < xhairStyles.length; ++i3) {
                String s = xhairStyles[i3];
                context.runOnClient(mc -> SixSevenClient.modules().customCrosshair.style.set(s));
                context.waitTicks(2);
                context.takeScreenshot(String.format("11%c-crosshair-%s", Character.valueOf((char)(97 + i3)), s.toLowerCase()));
            }
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(SixSevenClient.modules().customCrosshair.style.is("67"), "crosshair style must be the 67 logo for the final shot"));
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.method_1507((class_437)new class_490((class_1657)mc.field_1724));
                }
            });
            context.waitTicks(2);
            context.takeScreenshot("11h-crosshair-hidden-in-inventory");
            context.runOnClient(mc -> {
                mc.method_1507(null);
                SixSevenClient.modules().customCrosshair.setEnabled(false);
            });
            server.runCommand("tp @a 8 -57 18");
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(0.0f);
                }
            });
            context.waitTicks(4);
            context.takeScreenshot("12a-zoom-off");
            context.runOnClient(mc -> SixSevenClient.modules().zoom.setEnabled(true));
            context.waitTicks(40);
            context.takeScreenshot("12b-zoom-on-4x");
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(SixSevenClient.modules().zoom.currentFactor() > 3.9, "Zoom must ease to the 4x target, was " + SixSevenClient.modules().zoom.currentFactor()));
            context.runOnClient(mc -> SixSevenClient.modules().zoom.setEnabled(false));
            context.runOnClient(mc -> {
                Modules.SpotifyModule s = SixSevenClient.modules().spotify;
                s.source.set("Demo");
                s.controls.set(true);
                s.volume.set(true);
                s.setEnabled(true);
            });
            context.waitTicks(5);
            context.takeScreenshot("13-spotify-volume-slider");
            context.runOnClient(mc -> SixSevenClient.modules().spotify.setEnabled(false));
            server.runCommand("tp @a 8 -57 18");
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(0.0f);
                }
            });
            context.waitTicks(4);
            context.takeScreenshot("14a-fov-off");
            context.runOnClient(mc -> {
                CustomFovModule f = SixSevenClient.modules().customFov;
                f.fov.set(140.0);
                f.setEnabled(true);
            });
            context.waitTicks(40);
            context.takeScreenshot("14b-fov-140-fisheye");
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(SixSevenClient.modules().customFov.currentFov() > 135.0, "CustomFOV must ease to 140, was " + SixSevenClient.modules().customFov.currentFov()));
            context.runOnClient(mc -> SixSevenClient.modules().customFov.fov.set(40.0));
            context.waitTicks(40);
            context.takeScreenshot("14c-fov-40-tunnel");
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(SixSevenClient.modules().customFov.currentFov() < 45.0, "CustomFOV must ease to 40, was " + SixSevenClient.modules().customFov.currentFov()));
            context.runOnClient(mc -> SixSevenClient.modules().customFov.setEnabled(false));
            server.runCommand("tp @a 8 -57 18");
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(7.0f);
                }
                HitParticlesModule hp = SixSevenClient.modules().hitParticles;
                hp.amount.set(20.0);
                hp.size.set(1.4);
                hp.spread.set(1.1);
                hp.lifetime.set(1.0);
                hp.glow.set(80.0);
                hp.shockwave.set(true);
                hp.rainbow.set(false);
                hp.setEnabled(true);
            });
            context.waitTicks(15);
            String[] hitStyles = new String[]{"Sparks", "Hearts", "Lightning", "67"};
            for (i = 0; i < hitStyles.length; ++i) {
                String s = hitStyles[i];
                context.runOnClient(mc -> {
                    HitParticlesModule hp = SixSevenClient.modules().hitParticles;
                    hp.clear();
                    hp.style.set(s);
                    if (mc.field_1724 != null) {
                        class_243 eye = mc.field_1724.method_5836(1.0f);
                        class_243 look = mc.field_1724.method_5828(1.0f);
                        class_243 a = eye.method_1019(look.method_1021(3.0));
                        class_243 b = eye.method_1019(look.method_1021(3.9));
                        hp.spawnAt(a.field_1352 - 0.4, a.field_1351, a.field_1350);
                        hp.spawnAt(b.field_1352 + 0.4, b.field_1351 - 0.3, b.field_1350);
                    }
                });
                context.runOnClient(mc -> SixSevenClientGameTest.assertThat(!SixSevenClient.modules().hitParticles.particles().isEmpty(), "HitParticles must spawn particles for style " + s));
                context.waitTicks(3);
                context.takeScreenshot(String.format("15%c-hitparticles-%s", Character.valueOf((char)(97 + i)), s.toLowerCase()));
            }
            context.runOnClient(mc -> {
                SixSevenClient.modules().hitParticles.clear();
                SixSevenClient.modules().hitParticles.style.set("Sparks");
            });
            server.runCommand("summon minecraft:armor_stand 8.5 -57 22 {NoGravity:1b}");
            context.waitTicks(10);
            context.runOnClient(mc -> {
                if (mc.field_1724 == null || mc.field_1687 == null || mc.field_1761 == null) {
                    return;
                }
                class_1297 target = null;
                for (class_1297 e : mc.field_1687.method_18112()) {
                    if (!(e instanceof class_1531)) continue;
                    target = e;
                    break;
                }
                if (target != null) {
                    mc.field_1761.method_2918((class_1657)mc.field_1724, target);
                    SixSevenClientGameTest.assertThat(!SixSevenClient.modules().hitParticles.particles().isEmpty(), "attack() mixin must feed HitParticles a burst on a real hit");
                }
            });
            context.waitTicks(3);
            context.takeScreenshot("15e-hitparticles-real-hit");
            context.runOnClient(mc -> {
                HitParticlesModule hp = SixSevenClient.modules().hitParticles;
                hp.style.set("67");
                ClickGuiScreen.state().setExpanded("HitParticles@VISUALS", true);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("15f-hitparticles-settings");
            context.runOnClient(mc -> {
                SixSevenClient.modules().hitParticles.setEnabled(false);
                ClickGuiScreen.state().setExpanded("HitParticles@VISUALS", false);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(null);
            context.runOnClient(mc -> {
                Modules.HudModule hudModule = SixSevenClient.modules().hud;
                hudModule.notifications.set(true);
                hudModule.notifyDuration.set(6.0);
                NotificationManager toasts = SixSevenClient.notifications();
                toasts.setPosition(0.99f, 0.71f);
                toasts.setScale(1.0f);
                toasts.push("SpeedMine", true);
                toasts.pushInfo("Sus chunk found  \u00b7  1,247 blocks");
                toasts.pushWeather("Thunderstorm", "brewing overhead", NotificationManager.Weather.THUNDER, true);
            });
            context.waitTicks(12);
            context.takeScreenshot("16a-toasts-default-anchor");
            context.runOnClient(mc -> mc.method_1507((class_437)new class_408("", false)));
            context.waitForScreen(class_408.class);
            context.waitTicks(5);
            context.takeScreenshot("16b-toasts-editor-outline");
            context.runOnClient(mc -> {
                NotificationManager toasts = SixSevenClient.notifications();
                toasts.setPosition(0.1f, 0.32f);
                toasts.setScale(1.3f);
            });
            context.waitTicks(8);
            context.takeScreenshot("16c-toasts-moved-scaled");
            context.runOnClient(mc -> {
                NotificationManager toasts = SixSevenClient.notifications();
                SixSevenClientGameTest.assertThat(Math.abs(toasts.getFx() - 0.1f) < 0.001f && Math.abs(toasts.getScale() - 1.3f) < 0.001f, "notification anchor must hold its dragged position & scale");
            });
            context.runOnClient(mc -> mc.method_1507(null));
            context.waitForScreen(null);
            context.runOnClient(mc -> {
                SixSevenClient.notifications().setPosition(0.99f, 0.71f);
                SixSevenClient.notifications().setScale(1.0f);
            });
            server.runCommand("tp @a 8 -57 18");
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(0.0f);
                }
                MotionBlurModule mb = SixSevenClient.modules().motionBlur;
                mb.strength.set(85.0);
                mb.pinkTrails.set(true);
                mb.tint.set(45.0);
                mb.fpsCompensated.set(false);
                mb.setEnabled(true);
            });
            context.waitTicks(6);
            context.takeScreenshot("17a-motionblur-static");
            for (i = 0; i < 12; ++i) {
                float yaw = (float)i * 22.0f;
                context.runOnClient(mc -> {
                    if (mc.field_1724 != null) {
                        mc.field_1724.method_36456(yaw);
                    }
                });
                context.waitTicks(1);
            }
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(MotionBlurRenderer.framesRendered() > 0 && MotionBlurRenderer.lastRetention() > 0.0f, "MotionBlur render path must run with retention > 0, frames=" + MotionBlurRenderer.framesRendered() + " retention=" + MotionBlurRenderer.lastRetention()));
            context.takeScreenshot("17b-motionblur-spin-trails");
            context.runOnClient(mc -> ClickGuiScreen.state().setExpanded("MotionBlur@VISUALS", true));
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("17c-motionblur-settings");
            context.runOnClient(mc -> {
                SixSevenClient.modules().motionBlur.setEnabled(false);
                ClickGuiScreen.state().setExpanded("MotionBlur@VISUALS", false);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(null);
            server.runCommand("setblock 6 -59 30 minecraft:spawner{SpawnData:{entity:{id:\"minecraft:zombie\"}}}");
            server.runCommand("setblock 11 -59 30 minecraft:trial_spawner");
            server.runCommand("tp @a 3000 -58 3000");
            world.getClientWorld().waitForChunksRender();
            server.runCommand("tp @a 8 -57 22");
            world.getClientWorld().waitForChunksRender();
            context.waitTicks(10);
            server.runCommand("summon item 7 -58 28 {Item:{id:\"minecraft:diamond\",count:32}}");
            server.runCommand("summon item 9 -58 28 {Item:{id:\"minecraft:golden_apple\",count:3}}");
            server.runCommand("summon item 8 -58 27 {Item:{id:\"minecraft:netherite_ingot\",count:1}}");
            context.waitTicks(10);
            context.runOnClient(mc -> {
                NameTagsModule nt = SixSevenClient.modules().nameTags;
                nt.items.set(true);
                nt.distance.set(true);
                nt.setEnabled(true);
                SpawnerNametagsModule sn = SixSevenClient.modules().spawnerNametags;
                sn.nametag.set(true);
                sn.setEnabled(true);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(12.0f);
                }
            });
            context.waitTicks(20);
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(!SixSevenClient.modules().spawnerNametags.scan.get().isEmpty(), "SpawnerNametags scan must find the placed spawners"));
            context.takeScreenshot("18a-nametags-items-spawners");
            context.runOnClient(mc -> {
                NameProtectModule np = SixSevenClient.modules().nameProtect;
                np.ownName.set("SixSeven");
                np.setEnabled(true);
                ClickGuiScreen.state().setExpanded("NameTags@MISC", true);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("18b-nametags-settings");
            context.runOnClient(mc -> ClickGuiScreen.state().setExpanded("NameTags@MISC", false));
            context.getInput().pressKey(344);
            context.waitForScreen(null);
            context.runOnClient(mc -> {
                SixSevenClient.modules().nameTags.setEnabled(false);
                SixSevenClient.modules().spawnerNametags.setEnabled(false);
                NameProtectModule np = SixSevenClient.modules().nameProtect;
                np.selfOnly.set(false);
                np.setEnabled(true);
                if (mc.field_1724 != null) {
                    String real = mc.field_1724.method_7334().name();
                    String out = np.censorChat((class_2561)class_2561.method_43470((String)(real + " left the game"))).getString();
                    SixSevenClientGameTest.assertThat(!out.contains(real) && out.contains("SixSeven"), "NameProtect must censor the player's name in chat, got: " + out);
                    mc.field_1705.method_1743().method_1812((class_2561)class_2561.method_43470((String)("<" + real + "> gg wp")));
                    mc.field_1705.method_1743().method_1812((class_2561)class_2561.method_43470((String)(real + " joined the game")));
                }
            });
            context.waitTicks(6);
            context.takeScreenshot("18c-nameprotect-chat");
            context.runOnClient(mc -> SixSevenClient.modules().nameProtect.setEnabled(false));
            server.runCommand("item replace entity @a armor.head with minecraft:diamond_helmet");
            server.runCommand("item replace entity @a armor.chest with minecraft:netherite_chestplate");
            server.runCommand("item replace entity @a armor.legs with minecraft:iron_leggings");
            server.runCommand("item replace entity @a armor.feet with minecraft:golden_boots");
            server.runCommand("item replace entity @a weapon.mainhand with minecraft:diamond_sword");
            server.runCommand("item replace entity @a weapon.offhand with minecraft:shield");
            context.waitTicks(6);
            context.runOnClient(mc -> {
                NameTagsModule nt = SixSevenClient.modules().nameTags;
                nt.setEnabled(true);
                nt.self.set(true);
                nt.armor.set(true);
                nt.heldItem.set(true);
                mc.field_1690.method_31043(class_5498.field_26666);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(0.0f);
                    SixSevenClientGameTest.assertThat(!mc.field_1724.method_6047().method_7960() && !mc.field_1724.method_6118(class_1304.field_6169).method_7960(), "self player must be geared up for the armor/held-item tag");
                }
            });
            context.waitTicks(15);
            context.takeScreenshot("18d-self-tag-armor-held-item");
            context.runOnClient(mc -> mc.field_1690.method_31043(class_5498.field_26664));
            context.waitTicks(15);
            context.takeScreenshot("18d2-self-tag-hidden-first-person");
            context.runOnClient(mc -> {
                SixSevenClient.modules().spawnerNametags.setEnabled(true);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(0.0f);
                    mc.field_1724.method_36457(12.0f);
                }
            });
            context.waitTicks(6);
            context.runOnClient(mc -> mc.method_1507((class_437)new class_490((class_1657)mc.field_1724)));
            context.waitForScreen(class_490.class);
            context.waitTicks(5);
            context.takeScreenshot("18e-nametags-hidden-in-inventory");
            context.runOnClient(mc -> {
                mc.method_1507(null);
                SixSevenClient.modules().nameTags.setEnabled(false);
                SixSevenClient.modules().spawnerNametags.setEnabled(false);
            });
            context.waitForScreen(null);
            context.runOnClient(mc -> SixSevenClient.modules().customGlint.setEnabled(false));
            for (String item : glintGear = new String[]{"netherite_sword", "diamond_chestplate", "diamond_pickaxe", "bow", "golden_apple", "elytra"}) {
                server.runCommand("give @a minecraft:" + item + "[enchantment_glint_override=true] 1");
            }
            context.waitTicks(10);
            context.runOnClient(mc -> mc.method_1507((class_437)new class_490((class_1657)mc.field_1724)));
            context.waitForScreen(class_490.class);
            context.waitTicks(5);
            context.takeScreenshot("19a-glint-vanilla-off");
            context.runOnClient(mc -> {
                CustomGlintModule cg = SixSevenClient.modules().customGlint;
                cg.mode.set("Solid");
                cg.color.set(-49508);
                cg.strength.set(100.0);
                cg.setEnabled(true);
            });
            context.waitTicks(3);
            context.runOnClient(mc -> {
                CustomGlintModule cg = SixSevenClient.modules().customGlint;
                SixSevenClientGameTest.assertThat(cg.isActive(), "CustomGlint must report active once enabled");
                SixSevenClientGameTest.assertThat(cg.glintColor() == -49508, "solid pink @100% must compute to PINK, got " + Integer.toHexString(cg.glintColor()));
            });
            context.takeScreenshot("19b-glint-solid-pink");
            context.runOnClient(mc -> {
                CustomGlintModule cg = SixSevenClient.modules().customGlint;
                cg.strength.set(0.0);
                SixSevenClientGameTest.assertThat(cg.glintColor() == Colors.rgb(0, 0, 0), "0% strength must zero the glint RGB, got " + Integer.toHexString(cg.glintColor()));
                cg.strength.set(100.0);
            });
            context.runOnClient(mc -> {
                for (Theme t : SixSevenClient.themes().getThemes()) {
                    if (!t.getName().equals("Blue")) continue;
                    SixSevenClient.themes().select(t);
                }
                SixSevenClient.modules().customGlint.mode.set("Theme");
            });
            context.waitTicks(3);
            context.runOnClient(mc -> {
                int accent = SixSevenClient.themes().current().accent();
                SixSevenClientGameTest.assertThat(SixSevenClient.modules().customGlint.glintColor() == accent, "theme mode @100% must equal the live accent, got " + Integer.toHexString(SixSevenClient.modules().customGlint.glintColor()));
            });
            context.takeScreenshot("19c-glint-theme-blue");
            context.runOnClient(mc -> {
                for (Theme t : SixSevenClient.themes().getThemes()) {
                    if (!t.getName().equals("Pink")) continue;
                    SixSevenClient.themes().select(t);
                }
            });
            context.runOnClient(mc -> SixSevenClient.modules().customGlint.mode.set("Rainbow"));
            context.waitTicks(3);
            context.takeScreenshot("19d-glint-rainbow");
            context.runOnClient(mc -> mc.method_1507(null));
            context.waitForScreen(null);
            context.runOnClient(mc -> {
                SixSevenClient.modules().customGlint.mode.set("Solid");
                ClickGuiScreen.state().setExpanded("CustomGlint@MISC", true);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("19e-glint-settings");
            context.runOnClient(mc -> {
                SixSevenClient.modules().customGlint.setEnabled(false);
                ClickGuiScreen.state().setExpanded("CustomGlint@MISC", false);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(null);
            server.runCommand("tp @a 500 -60 500 -90 0");
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                SixSevenClient.modules().susChunkFinder.setEnabled(false);
                SixSevenClient.modules().blockOutline.setEnabled(false);
                SixSevenClient.modules().motionBlur.setEnabled(false);
                mc.field_1690.method_31043(class_5498.field_26665);
                if (mc.field_1724 != null) {
                    mc.field_1724.method_36456(-90.0f);
                    mc.field_1724.method_5636(-90.0f);
                    mc.field_1724.method_36457(0.0f);
                }
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.color.set(-49508);
                acc.rainbow.set(false);
                acc.glow.set(75.0);
                acc.cape.set(true);
                acc.capePhysics.set(true);
                acc.trail.set(false);
                acc.aura.set(false);
                acc.crown.set(false);
                acc.setEnabled(true);
            });
            context.waitTicks(20);
            for (String cs : new String[]{"67", "Wave", "Grid", "Solid"}) {
                context.runOnClient(mc -> SixSevenClient.modules().customAccessories.capeStyle.set(cs));
                context.waitTicks(6);
                context.takeScreenshot("20-cape-" + cs.toLowerCase());
            }
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.cape.set(false);
                acc.trail.set(true);
                acc.trailLength.set(2.0);
                acc.trailStyle.set("Ribbon");
            });
            SixSevenClientGameTest.walkEast(context, 500.0);
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(!SixSevenClient.modules().customAccessories.trailNodes().isEmpty(), "CustomAccessories must record trail nodes while moving"));
            context.takeScreenshot("20-trail-ribbon");
            context.runOnClient(mc -> SixSevenClient.modules().customAccessories.trailStyle.set("Sparkle"));
            SixSevenClientGameTest.walkEast(context, 510.0);
            context.takeScreenshot("20-trail-sparkle");
            context.runOnClient(mc -> SixSevenClient.modules().customAccessories.trailStyle.set("Echo"));
            SixSevenClientGameTest.walkEast(context, 520.0);
            context.takeScreenshot("20-trail-echo");
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.trail.set(false);
                acc.aura.set(true);
                acc.auraStyle.set("Orbit");
            });
            context.waitTicks(10);
            context.takeScreenshot("20-aura-orbit");
            context.runOnClient(mc -> SixSevenClient.modules().customAccessories.auraStyle.set("Ring"));
            context.waitTicks(10);
            context.takeScreenshot("20-aura-ring");
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.aura.set(false);
                acc.crown.set(true);
            });
            context.waitTicks(12);
            context.takeScreenshot("20-crown");
            context.runOnClient(mc -> {
                CustomAccessoriesModule acc = SixSevenClient.modules().customAccessories;
                acc.cape.set(true);
                acc.capeStyle.set("67");
                acc.trail.set(true);
                acc.trailStyle.set("Ribbon");
                acc.aura.set(true);
                acc.auraStyle.set("Orbit");
                acc.crown.set(true);
            });
            SixSevenClientGameTest.walkEast(context, 500.0);
            context.takeScreenshot("20-all-combined");
            context.runOnClient(mc -> {
                mc.field_1690.method_31043(class_5498.field_26664);
                ClickGuiScreen.state().setExpanded("CustomAccessories@VISUALS", true);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("20-accessories-settings");
            context.runOnClient(mc -> {
                SixSevenClient.modules().customAccessories.setEnabled(false);
                ClickGuiScreen.state().setExpanded("CustomAccessories@VISUALS", false);
            });
            context.getInput().pressKey(344);
            context.waitForScreen(null);
            int[] savedRenderDistance = new int[]{12};
            context.runOnClient(mc -> {
                SixSevenClient.modules().susChunkFinder.setEnabled(false);
                SixSevenClient.modules().blockOutline.setEnabled(false);
                SixSevenClient.modules().motionBlur.setEnabled(false);
                mc.field_1690.method_31043(class_5498.field_26664);
                savedRenderDistance[0] = (Integer)mc.field_1690.method_42503().method_41753();
                mc.field_1690.method_42503().method_41748((Object)8);
            });
            server.runCommand("tp @a 34 -59 40 0 0");
            world.getClientWorld().waitForChunksRender();
            context.waitTicks(10);
            context.runOnClient(mc -> {
                class_2680 bs = mc.field_1687.method_8320(new class_2338(40, 0, 40));
                class_2680 bs2 = mc.field_1687.method_8320(new class_2338(40, 0, 72));
                boolean loaded = mc.field_1687.method_2935().method_12126(2, 2, false) != null;
                boolean loaded2 = mc.field_1687.method_2935().method_12126(2, 4, false) != null;
                SixSevenClientGameTest.assertThat(bs.method_27852(class_2246.field_20422), "test setup: client must see the beehive at (40,0,40); saw " + String.valueOf(bs) + " (chunk loaded=" + loaded + ")");
                SixSevenClientGameTest.assertThat(bs2.method_27852(class_2246.field_20422), "test setup: client must see the second beehive at (40,0,72); saw " + String.valueOf(bs2) + " (chunk loaded=" + loaded2 + ")");
            });
            context.runOnClient(mc -> {
                ChunkFinderModule cf = SixSevenClient.modules().chunkFinder;
                cf.mergeRadius.set(1.0);
                cf.clear();
                cf.setEnabled(true);
            });
            context.waitTicks(260);
            context.runOnClient(mc -> {
                ChunkFinderModule cf = SixSevenClient.modules().chunkFinder;
                long fullA = class_1923.method_8331((int)2, (int)2);
                long fullB = class_1923.method_8331((int)2, (int)4);
                long empty = class_1923.method_8331((int)4, (int)2);
                SixSevenClientGameTest.assertThat(cf.flaggedChunks().contains(fullA) && cf.flaggedChunks().contains(fullB), "radius 1 must leave BOTH full-honey beehive chunks flagged (unmerged); flags=" + String.valueOf(cf.flaggedChunks()));
                SixSevenClientGameTest.assertThat(!cf.flaggedChunks().contains(empty), "an empty (honey 0) beehive must NOT flag");
            });
            server.runCommand("tp @a 40 78 26 0 42");
            context.waitTicks(20);
            context.takeScreenshot("21-chunkfinder-fullhoney");
            context.runOnClient(mc -> SixSevenClient.modules().chunkFinder.mergeRadius.set(4.0));
            context.waitTicks(10);
            context.runOnClient(mc -> {
                ChunkFinderModule cf = SixSevenClient.modules().chunkFinder;
                long middle = class_1923.method_8331((int)2, (int)3);
                long fullA = class_1923.method_8331((int)2, (int)2);
                long fullB = class_1923.method_8331((int)2, (int)4);
                SixSevenClientGameTest.assertThat(cf.flaggedChunks().contains(middle), "Smart must flag the middle chunk (2,3) between the two hives; flags=" + String.valueOf(cf.flaggedChunks()));
                SixSevenClientGameTest.assertThat(!cf.flaggedChunks().contains(fullA) && !cf.flaggedChunks().contains(fullB), "Smart must merge the hive chunks away, leaving only the middle; flags=" + String.valueOf(cf.flaggedChunks()));
            });
            server.runCommand("tp @a 40 78 26 0 42");
            context.waitTicks(20);
            context.takeScreenshot("21b-chunkfinder-smart");
            context.runOnClient(mc -> {
                FreecamModule fc = SixSevenClient.modules().freecam;
                fc.showPlayerModel.set(false);
                fc.smoothing.set(false);
                fc.setEnabled(true);
            });
            context.waitTicks(30);
            context.runOnClient(mc -> {
                SixSevenClientGameTest.assertThat(SixSevenClient.modules().freecam.isActive(), "Freecam must be active for the regression shot");
                SixSevenClientGameTest.assertThat(!SixSevenClient.modules().chunkFinder.flaggedChunks().isEmpty(), "chunk finder must still hold its flag under freecam; flags=" + String.valueOf(SixSevenClient.modules().chunkFinder.flaggedChunks()));
            });
            context.takeScreenshot("21c-chunkfinder-freecam");
            context.runOnClient(mc -> SixSevenClient.modules().freecam.setEnabled(false));
            context.waitTicks(5);
            context.runOnClient(mc -> {
                SixSevenClient.modules().chunkFinder.setEnabled(false);
                mc.field_1690.method_42503().method_41748((Object)savedRenderDistance[0]);
            });
            context.runOnClient(mc -> {
                SixSevenClient.modules().blockOutline.setEnabled(false);
                mc.field_1690.method_31043(class_5498.field_26664);
            });
            server.runCommand("tp @a 2 -58 0 0 0");
            context.waitTicks(20);
            for (int bx = 1; bx <= 3; ++bx) {
                for (int by = -59; by <= -57; ++by) {
                    server.runCommand("setblock " + bx + " " + by + " 6 minecraft:diamond_ore");
                }
            }
            server.runCommand("setblock 6 -58 6 minecraft:emerald_ore");
            context.waitTicks(5);
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(mc.field_1687.method_8320(new class_2338(2, -58, 6)).method_27852(class_2246.field_10442), "test setup: client must see the placed diamond ore"));
            context.runOnClient(mc -> SixSevenClient.modules().blockEsp.setEnabled(true));
            context.waitTicks(20);
            context.runOnClient(mc -> SixSevenClientGameTest.assertThat(BlockEspRenderer.cachedCount() > 0, "BlockESP must detect the enabled diamond ore (emerald is off); cached=" + BlockEspRenderer.cachedCount()));
            server.runCommand("tp @a 2 -55 -4 0 22");
            context.waitTicks(20);
            context.takeScreenshot("30-blockesp-boxes");
            context.runOnClient(mc -> ClickGuiScreen.state().setExpanded("BlockESP@RENDER", true));
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("31-blockesp-settings");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof ClickGuiScreen) {
                    ClickGuiScreen cg = (ClickGuiScreen)patt0$temp;
                    cg.openBlockPicker(SixSevenClient.modules().blockEsp.targets);
                }
            });
            context.waitForScreen(IconPickerScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("32-blockpicker-grid");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof IconPickerScreen) {
                    IconPickerScreen picker = (IconPickerScreen)patt0$temp;
                    picker.debugSetSearch("ore");
                }
            });
            context.waitTicks(8);
            context.takeScreenshot("33-blockpicker-search");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof IconPickerScreen) {
                    IconPickerScreen picker = (IconPickerScreen)patt0$temp;
                    picker.debugOpenColor(0);
                }
            });
            context.waitTicks(8);
            context.takeScreenshot("34-blockpicker-color");
            context.runOnClient(mc -> {
                mc.method_1507(null);
                SixSevenClient.modules().blockEsp.setEnabled(false);
                ClickGuiScreen.state().setExpanded("BlockESP@RENDER", false);
            });
            context.waitForScreen(null);
        }
    }

    private static void walkEast(ClientGameTestContext context, double startX) {
        for (int i = 0; i < 10; ++i) {
            double x = startX + (double)i * 0.45;
            context.runOnClient(mc -> {
                if (mc.field_1724 != null) {
                    mc.field_1724.method_5814(x, -60.0, 500.0);
                }
            });
            context.waitTicks(1);
        }
        context.waitTicks(4);
    }

    private static void assertThat(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError((Object)message);
        }
    }
}

