/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_437
 *  net.minecraft.class_5498
 */
package dev.sixseven.gametest;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.ClickGuiScreen;
import dev.sixseven.module.impl.CustomGlintModule;
import dev.sixseven.theme.Theme;
import dev.sixseven.util.Colors;
import java.util.Locale;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_437;
import net.minecraft.class_5498;

public class CustomGlintGameTest
implements FabricClientGameTest {
    private static final String GLINT = "[enchantment_glint_override=true]";
    private static final String[] STYLES = new String[]{"Ender", "Void", "Galaxy", "Toxic", "Amethyst", "Cyber", "Solar", "Prismatic", "Abyss"};

    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1600, 900);
            context.waitTicks(2);
            TestServerContext server = world.getServer();
            context.runOnClient(mc -> CustomGlintGameTest.require(SixSevenClient.modules().customGlint != null, "CustomGlint registered"));
            server.runCommand("gamemode creative @a");
            server.runCommand("time set day");
            server.runCommand("weather clear");
            server.runCommand("fill -8 0 -8 8 0 8 minecraft:white_concrete");
            server.runCommand("tp @a 0 1 0 0 0");
            server.runCommand("item replace entity @a armor.head with minecraft:netherite_helmet[enchantment_glint_override=true]");
            server.runCommand("item replace entity @a armor.chest with minecraft:netherite_chestplate[enchantment_glint_override=true]");
            server.runCommand("item replace entity @a armor.legs with minecraft:netherite_leggings[enchantment_glint_override=true]");
            server.runCommand("item replace entity @a armor.feet with minecraft:netherite_boots[enchantment_glint_override=true]");
            server.runCommand("item replace entity @a weapon.mainhand with minecraft:netherite_sword[enchantment_glint_override=true]");
            context.runOnClient(mc -> {
                mc.field_1690.method_31043(class_5498.field_26665);
                SixSevenClient.modules().blockOutline.setEnabled(false);
                SixSevenClient.modules().customGlint.setEnabled(false);
            });
            context.waitTicks(10);
            context.takeScreenshot("glint-00-vanilla-off");
            context.runOnClient(mc -> {
                CustomGlintModule cg = SixSevenClient.modules().customGlint;
                cg.style.set("Default");
                cg.mode.set("Solid");
                cg.color.set(-49508);
                cg.strength.set(100.0);
                cg.setEnabled(true);
            });
            context.waitTicks(3);
            context.runOnClient(mc -> {
                CustomGlintModule cg = SixSevenClient.modules().customGlint;
                CustomGlintGameTest.require(cg.isActive(), "CustomGlint must report active once enabled");
                CustomGlintGameTest.require(!cg.usesTexture(), "Default style must not be a texture style");
                CustomGlintGameTest.require(cg.glintColor() == -49508, "solid pink @100% must compute to PINK, got " + Integer.toHexString(cg.glintColor()));
            });
            context.takeScreenshot("glint-01-solid-pink");
            context.runOnClient(mc -> {
                CustomGlintModule cg = SixSevenClient.modules().customGlint;
                cg.strength.set(0.0);
                CustomGlintGameTest.require(cg.glintColor() == Colors.rgb(0, 0, 0), "0% strength must zero the glint RGB, got " + Integer.toHexString(cg.glintColor()));
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
                CustomGlintGameTest.require(SixSevenClient.modules().customGlint.glintColor() == accent, "theme mode @100% must equal the live accent");
            });
            context.takeScreenshot("glint-02-theme-blue");
            context.runOnClient(mc -> {
                for (Theme t : SixSevenClient.themes().getThemes()) {
                    if (!t.getName().equals("Pink")) continue;
                    SixSevenClient.themes().select(t);
                }
                SixSevenClient.modules().customGlint.mode.set("Rainbow");
            });
            context.waitTicks(3);
            context.takeScreenshot("glint-03-rainbow");
            for (int i = 0; i < STYLES.length; ++i) {
                String styleName = STYLES[i];
                context.runOnClient(mc -> {
                    CustomGlintModule cg = SixSevenClient.modules().customGlint;
                    cg.style.set(styleName);
                    cg.strength.set(100.0);
                });
                context.waitTicks(4);
                context.runOnClient(mc -> {
                    CustomGlintModule cg = SixSevenClient.modules().customGlint;
                    CustomGlintGameTest.require(cg.usesTexture(), "style " + styleName + " must report a custom texture");
                    CustomGlintGameTest.require(cg.textureName().equals(styleName.toLowerCase(Locale.ROOT)), "textureName must match the selected style, got " + cg.textureName());
                });
                context.takeScreenshot(String.format("glint-1%d-%s", i, styleName.toLowerCase(Locale.ROOT)));
            }
            context.runOnClient(mc -> {
                CustomGlintModule cg = SixSevenClient.modules().customGlint;
                cg.style.set("Default");
                cg.mode.set("Solid");
                ClickGuiScreen.state().setExpanded("CustomGlint@MISC", true);
                mc.method_1507((class_437)new ClickGuiScreen());
            });
            context.waitForScreen(ClickGuiScreen.class);
            context.waitTicks(8);
            context.takeScreenshot("glint-20-settings");
            context.runOnClient(mc -> {
                SixSevenClient.modules().customGlint.setEnabled(false);
                ClickGuiScreen.state().setExpanded("CustomGlint@MISC", false);
                mc.method_1507(null);
            });
            context.waitForScreen(null);
        }
    }

    private static void require(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError((Object)("FAILED: " + what));
        }
    }
}

