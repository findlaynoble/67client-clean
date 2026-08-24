/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest
 *  net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext
 *  net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext
 *  net.minecraft.class_437
 */
package dev.sixseven.gametest;

import dev.sixseven.SixSevenClient;
import dev.sixseven.gui.ClickGuiScreen;
import dev.sixseven.gui.widget.StringWidget;
import dev.sixseven.module.impl.FakePayModule;
import dev.sixseven.settings.StringSetting;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.class_437;

public class StringInputGameTest
implements FabricClientGameTest {
    private static final String EURO = StringInputGameTest.codePoint(8364);
    private static final String POUND = StringInputGameTest.codePoint(163);
    private static final String YEN = StringInputGameTest.codePoint(165);

    public void runTest(ClientGameTestContext context) {
        try (TestSingleplayerContext world = context.worldBuilder().create();){
            world.getClientWorld().waitForChunksRender();
            context.runOnClient(mc -> {
                mc.field_1690.field_1837 = false;
            });
            context.getInput().resizeWindow(1600, 900);
            context.waitTicks(2);
            context.runOnClient(mc -> {
                FakePayModule fp = SixSevenClient.modules().fakePay;
                StringInputGameTest.require(fp != null, "FakePay registered");
                StringSetting currency = fp.currency;
                StringWidget widget = new StringWidget(SixSevenClient.themes(), currency);
                widget.setBounds(0.0f, 0.0f, 200.0f);
                StringInputGameTest.require(widget.mouseClicked(5.0f, 5.0f, 0), "left click focuses the field");
                StringInputGameTest.require(widget.isListening(), "focused field listens for typing");
                currency.set("");
                StringInputGameTest.type(widget, EURO);
                StringInputGameTest.require(((String)currency.get()).equals(EURO), "euro sign typed, got '" + (String)currency.get() + "'");
                currency.set("");
                StringInputGameTest.type(widget, POUND + YEN);
                StringInputGameTest.require(((String)currency.get()).equals(POUND + YEN), "pound+yen typed, got '" + (String)currency.get() + "'");
                currency.set("$");
                widget.keyPressed(259);
                StringInputGameTest.require(((String)currency.get()).isEmpty(), "backspace cleared the '$'");
                StringInputGameTest.type(widget, "$");
                StringInputGameTest.require(((String)currency.get()).equals("$"), "'$' retyped after clearing, got '" + (String)currency.get() + "'");
                currency.set("");
                StringInputGameTest.type(widget, "USD");
                StringInputGameTest.require(((String)currency.get()).equals("USD"), "letters still accepted, got '" + (String)currency.get() + "'");
                currency.set("");
                StringInputGameTest.type(widget, "$$$$$$");
                StringInputGameTest.require(((String)currency.get()).length() == 4, "maxLength caps at 4, got " + ((String)currency.get()).length());
                currency.set(EURO);
            });
            context.runOnClient(mc -> mc.method_1507((class_437)new ClickGuiScreen()));
            context.waitTicks(3);
            context.takeScreenshot("stringinput-clickgui-open");
            context.runOnClient(mc -> mc.method_1507(null));
            context.waitTicks(2);
            context.runOnClient(mc -> {
                FakePayModule fp = SixSevenClient.modules().fakePay;
                fp.feedback.set("Both");
                fp.setEnabled(true);
                StringInputGameTest.require(((String)fp.currency.get()).equals(EURO), "currency persisted as euro");
                mc.field_1724.field_3944.method_45730("pay Notch 250k");
            });
            context.waitTicks(2);
            context.takeScreenshot("stringinput-fakepay-euro-receipt");
        }
    }

    private static String codePoint(int cp) {
        return new String(Character.toChars(cp));
    }

    private static void type(StringWidget widget, String text) {
        text.codePoints().forEach(widget::charTyped);
    }

    private static void require(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError((Object)("FAILED: " + what));
        }
    }
}

