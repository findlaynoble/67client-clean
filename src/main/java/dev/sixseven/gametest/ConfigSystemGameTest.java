/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_437
 */
package dev.sixseven.gametest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.sixseven.SixSevenClient;
import dev.sixseven.config.ConfigStore;
import dev.sixseven.gui.ClickGuiScreen;
import dev.sixseven.module.Modules;
import dev.sixseven.module.impl.FullbrightModule;
import dev.sixseven.theme.Theme;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_437;

public class ConfigSystemGameTest
implements FabricClientGameTest {
    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1600, 900);
            context.waitTicks(2);
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                ConfigSystemGameTest.clearDir(store.directory());
                store.loadAll();
                for (int i = 0; i < 5; ++i) {
                    ConfigSystemGameTest.assertThat(!store.slot(i).filled(), "slot " + i + " must start empty");
                }
            });
            String[] exported = new String[]{null};
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                ConfigSystemGameTest.selectTheme("Blue");
                FullbrightModule fullbright = SixSevenClient.modules().fullbright;
                Modules.SusChunkFinderModule finder = SixSevenClient.modules().susChunkFinder;
                fullbright.setEnabled(true);
                fullbright.getKeybind().set(71);
                finder.sensitivity.set(7.0);
                ConfigSystemGameTest.assertThat(store.save(0), "save(0) must succeed");
                ConfigSystemGameTest.assertThat(store.slot(0).filled(), "slot 0 must be filled after save");
                ConfigSystemGameTest.assertThat(Files.exists(store.directory().resolve("slot1.json"), new LinkOption[0]), "slot1.json must exist on disk after save");
                ConfigSystemGameTest.assertThat(store.rename(0, "PvP"), "rename(0) must succeed");
                ConfigSystemGameTest.assertThat(store.slot(0).name().equals("PvP"), "slot 0 name must be PvP");
            });
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                ConfigSystemGameTest.selectTheme("Pink");
                SixSevenClient.modules().fullbright.setEnabled(false);
                SixSevenClient.modules().fullbright.getKeybind().set(ConfigSystemGameTest.KeybindNone());
                SixSevenClient.modules().susChunkFinder.sensitivity.set(1.0);
                ConfigSystemGameTest.assertThat(store.activate(0), "activate(0) must succeed");
                ConfigSystemGameTest.assertThat(SixSevenClient.modules().fullbright.isEnabled(), "activate must re-enable Fullbright");
                ConfigSystemGameTest.assertThat((Integer)SixSevenClient.modules().fullbright.getKeybind().get() == 71, "activate must restore the keybind");
                ConfigSystemGameTest.assertThat(Math.abs(SixSevenClient.modules().susChunkFinder.sensitivity.getFloat() - 7.0f) < 0.001f, "activate must restore the slider value, got " + SixSevenClient.modules().susChunkFinder.sensitivity.getFloat());
                ConfigSystemGameTest.assertThat(SixSevenClient.themes().current().getName().equals("Blue"), "activate must restore the Blue theme");
                ConfigSystemGameTest.assertThat(store.activeIndex() == 0, "slot 0 must be marked active");
            });
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                String json = store.export(0);
                ConfigSystemGameTest.assertThat(json != null, "export(0) must return a string");
                JsonObject parsed = JsonParser.parseString((String)json).getAsJsonObject();
                ConfigSystemGameTest.assertThat("67client-config".equals(parsed.get("format").getAsString()), "export must carry the format tag");
                ConfigSystemGameTest.assertThat(parsed.get("version").getAsInt() == 1, "export must carry the version");
                ConfigSystemGameTest.assertThat(parsed.has("state") && parsed.getAsJsonObject("state").has("modules"), "export must embed the full state snapshot");
                exported[0] = json;
            });
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                ConfigStore.ImportResult bad = store.importInto(1, "{ this is not json ]");
                ConfigSystemGameTest.assertThat(!bad.ok(), "corrupt import must fail");
                ConfigSystemGameTest.assertThat(!store.slot(1).filled(), "failed import must leave slot 1 empty");
                ConfigStore.ImportResult notConfig = store.importInto(1, "{\"hello\":\"world\"}");
                ConfigSystemGameTest.assertThat(!notConfig.ok(), "non-config JSON must fail import");
                ConfigSystemGameTest.assertThat(!store.slot(1).filled(), "slot 1 must still be empty");
                ConfigStore.ImportResult tooNew = store.importInto(1, "{\"format\":\"67client-config\",\"version\":2,\"state\":{\"modules\":{}}}");
                ConfigSystemGameTest.assertThat(!tooNew.ok(), "a newer-version config must be refused");
            });
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                ConfigStore.ImportResult good = store.importInto(2, exported[0]);
                ConfigSystemGameTest.assertThat(good.ok(), "valid import must succeed: " + good.message());
                ConfigSystemGameTest.assertThat(store.slot(2).filled(), "slot 2 must be filled after import");
                ConfigSystemGameTest.assertThat(store.slot(2).name().equals("PvP"), "imported slot must carry the exported name, got " + store.slot(2).name());
                store.rename(2, "Legit");
            });
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                store.save(3);
                store.rename(3, "Building");
            });
            context.getInput().pressKey(344);
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("config-01-menu-with-fidget");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof ClickGuiScreen) {
                    ClickGuiScreen cg = (ClickGuiScreen)patt0$temp;
                    cg.configPanel().open();
                }
            });
            context.waitTicks(8);
            context.takeScreenshot("config-02-panel-slots");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof ClickGuiScreen) {
                    ClickGuiScreen cg = (ClickGuiScreen)patt0$temp;
                    cg.configPanel().debugBeginRename(1);
                }
            });
            context.waitTicks(6);
            context.takeScreenshot("config-03-rename-field");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof ClickGuiScreen) {
                    ClickGuiScreen cg = (ClickGuiScreen)patt0$temp;
                    cg.configPanel().debugConfirmOverwrite(3);
                }
            });
            context.waitTicks(6);
            context.takeScreenshot("config-04-overwrite-confirm");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof ClickGuiScreen) {
                    ClickGuiScreen cg = (ClickGuiScreen)patt0$temp;
                    cg.configPanel().debugConfirmDelete(2);
                }
            });
            context.waitTicks(6);
            context.takeScreenshot("config-05-delete-confirm");
            context.runOnClient(mc -> {
                class_437 patt0$temp = mc.field_1755;
                if (patt0$temp instanceof ClickGuiScreen) {
                    ClickGuiScreen cg = (ClickGuiScreen)patt0$temp;
                    cg.configPanel().close();
                }
            });
            context.getInput().pressKey(344);
            context.waitForScreen(null);
            context.runOnClient(mc -> {
                ConfigStore store = SixSevenClient.configStore();
                ConfigSystemGameTest.assertThat(store.slot(3).filled(), "slot 3 must be filled before delete");
                ConfigSystemGameTest.assertThat(store.delete(3), "delete(3) must succeed");
                ConfigSystemGameTest.assertThat(!store.slot(3).filled(), "slot 3 must be empty after delete");
                ConfigSystemGameTest.assertThat(!Files.exists(store.directory().resolve("slot4.json"), new LinkOption[0]), "slot4.json must be gone after delete");
                ConfigSystemGameTest.assertThat(store.slot(3).name().equals(ConfigStore.defaultName(3)), "deleted slot name must reset to default, got " + store.slot(3).name());
                ConfigSystemGameTest.assertThat(store.activeIndex() == 0, "slot 0 must still be active before its delete");
                ConfigSystemGameTest.assertThat(store.delete(0), "delete(0) must succeed");
                ConfigSystemGameTest.assertThat(store.activeIndex() == -1, "deleting the active slot must clear active");
                ConfigSystemGameTest.assertThat(!store.slot(0).filled(), "slot 0 must be empty after delete");
            });
        }
    }

    private static int KeybindNone() {
        return -1;
    }

    private static void selectTheme(String name) {
        for (Theme t : SixSevenClient.themes().getThemes()) {
            if (!t.getName().equals(name)) continue;
            SixSevenClient.themes().select(t);
            return;
        }
    }

    private static void clearDir(Path dir) {
        try {
            if (!Files.exists(dir, new LinkOption[0])) {
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir);){
                for (Path p : stream) {
                    Files.deleteIfExists(p);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to clear config test dir", e);
        }
    }

    private static void assertThat(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError((Object)message);
        }
    }
}

