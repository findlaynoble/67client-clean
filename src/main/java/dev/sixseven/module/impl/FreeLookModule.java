/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_5498
 *  org.lwjgl.glfw.GLFW
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_5498;
import org.lwjgl.glfw.GLFW;

public class FreeLookModule
extends Module {
    private static FreeLookModule instance;
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt("$D!\rz\u00e4\u00a9\u0084\u00bd\u0112\u011d\u011a\u0163\u0184\u01fb\u01d1\u01be\u0216\u021e\u0240\u022f\u029c\u02d5\u02d0\u02b4\u030a\u0318\u0308\u0344\u03c3\u03bb"), Deobf.decrypt("#@)\u0017w\u00b6"), Deobf.decrypt("#@)\u0017w\u00b6"), Deobf.decrypt("0M%\u000b`\u00a5")));
    public final BooleanSetting togglePerspective = this.addSetting(new BooleanSetting(Deobf.decrypt("'C/\t~\u00a1\u00ec\u00ba\u00ac\u0109\u011a\u0113\u0126\u0193\u01e7\u01dd\u01e8\u021e"), Deobf.decrypt(" [!\u001aq\u00ac\u00ec\u009e\u00a6\u015b\u011d\u010b\u012a\u0182\u01f7\u0194\u01ee\u021e\u0203\u0246\u0233\u0297\u02d5\u02cd\u02b5\u035e\u030d\u0313\u0346\u03d7\u03f9\u0393\u03f7"), true));
    public final BooleanSetting throughWalls = this.addSetting(new BooleanSetting(Deobf.decrypt("'D:\u0001g\u00a3\u00a4\u00ca\u009e\u011a\u0105\u010f\u0130"), Deobf.decrypt(" I-Nf\u00ac\u00be\u0085\u00bc\u011c\u0101\u0143\u0134\u0191\u01ff\u01d8\u01ed\u025b\u2265\u0215\u0228\u0291\u0290\u0282\u02af\u0316\u0310\u030e\u0345\u039d\u03e5\u0393\u03ab\u03ce\u0440\u0420\u044f\u046e\u04c8\u04ba\u04e2\u04f3\u0544\u0524\u0551\u054b\u05ca\u05e9\u05c1\u05ab\u0618\u0663\u064f\u0655\u069e\u06f5\u069a\u06db\u0702\u077a\u0751\u0755\u07c8\u078d\u079c\u0786\u07e7"), false));
    public final SliderSetting sensitivity = this.addSetting(new SliderSetting(Deobf.decrypt("0M%\u000b`\u00a5\u00ec\u00b9\u00ac\u0115\u011a\u010a\u0137\u0199\u01e5\u01dd\u01ea\u0202"), Deobf.decrypt(";C?Nt\u00a5\u00bf\u009e\u00e9\u010f\u0101\u0106\u0163\u0193\u01f2\u01d9\u01fb\u0209\u0210\u0215\u0231\u0296\u0283\u02c7\u02a8\u035e\u0310\u0312\u0301\u03f3\u03f4\u039b\u03bc\u03cf\u044e\u046e\u0402\u0462\u04cd\u04b2\u04a9"), 8.0, 0.0, 10.0, 0.1));
    public final BooleanSetting arrows = this.addSetting(new BooleanSetting(Deobf.decrypt("2^:\u0001e\u00b7\u00ec\u00a9\u00a6\u0115\u011d\u0111\u012c\u019c\u01b3\u01fb\u01ee\u020b\u021e\u0246\u0235\u028d\u0290"), Deobf.decrypt("0C&\u001a`\u00ab\u00a0\u00ca\u00bd\u0113\u010c\u0143\u012c\u0184\u01fb\u01d1\u01ec\u025b\u0214\u025b\u0228\u0290\u0281\u02db\u02fc\u030d\u0359\u030e\u034e\u03c4\u03f4\u0382\u03b0\u03d2\u0441\u046e\u0418\u0464\u04dd\u04bf\u04a7\u04f5\u054d\u0561\u0518\u054d\u05d6\u05f4\u05dc\u05b9\u064b\u0628\u065d\u064d\u0681\u06b7"), true));
    public final SliderSetting arrowSpeed = this.addSetting(new SliderSetting(Deobf.decrypt("2^:\u0001e\u00e4\u009f\u009a\u00ac\u011e\u010d"), Deobf.decrypt("!C<\u000ff\u00ad\u00a3\u0084\u00e9\u0108\u0119\u0106\u0126\u0194\u01b3\u01c3\u01f7\u020f\u0219\u0215\u0228\u0291\u0290\u0282\u02ba\u030c\u030b\u0313\u0356\u0390\u03fe\u0393\u03a0\u03ce\u0401"), 4.0, 0.0, 10.0, 0.5));
    private float cameraYaw;
    private float cameraPitch;
    private class_5498 prePers;

    public FreeLookModule() {
        super(Deobf.decrypt("5^-\u000b^\u00ab\u00a3\u0081"), Deobf.decrypt("2@$\u0001e\u00b7\u00ec\u0087\u00a6\u0109\u010c\u0143\u0131\u019f\u01e7\u01d5\u01ea\u0212\u021e\u025b\u027c\u0296\u0285\u02d6\u02b2\u0311\u0317\u030f\u0301\u03d9\u03fb\u03d6\u03ad\u03d5\u0446\u043c\u040b\u042d\u04d9\u04b2\u04f5\u04f2\u054a\u056a\u0516"), Category.MISC);
        instance = this;
    }

    public static FreeLookModule get() {
        return instance;
    }

    @Override
    protected void onEnable() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null) {
            return;
        }
        this.cameraYaw = mc.field_1724.method_36454();
        this.cameraPitch = mc.field_1724.method_36455();
        this.prePers = mc.field_1690.method_31044();
        if (this.prePers != class_5498.field_26665 && ((Boolean)this.togglePerspective.get()).booleanValue()) {
            mc.field_1690.method_31043(class_5498.field_26665);
        }
    }

    @Override
    protected void onDisable() {
        class_310 mc = class_310.method_1551();
        if (this.prePers != null && mc.field_1690.method_31044() != this.prePers && ((Boolean)this.togglePerspective.get()).booleanValue()) {
            mc.field_1690.method_31043(this.prePers);
        }
    }

    @Override
    public void onTick() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null) {
            return;
        }
        if (((Boolean)this.arrows.get()).booleanValue()) {
            long win = mc.method_22683().method_4490();
            boolean left = GLFW.glfwGetKey((long)win, (int)263) == 1;
            boolean right = GLFW.glfwGetKey((long)win, (int)262) == 1;
            boolean up = GLFW.glfwGetKey((long)win, (int)265) == 1;
            boolean down = GLFW.glfwGetKey((long)win, (int)264) == 1;
            int iterations = (int)((Double)this.arrowSpeed.get() * 2.0);
            for (int i = 0; i < iterations; ++i) {
                if (this.mode.is(Deobf.decrypt("#@)\u0017w\u00b6"))) {
                    if (left) {
                        this.cameraYaw -= 0.5f;
                    }
                    if (right) {
                        this.cameraYaw += 0.5f;
                    }
                    if (up) {
                        this.cameraPitch -= 0.5f;
                    }
                    if (!down) continue;
                    this.cameraPitch += 0.5f;
                    continue;
                }
                float yaw = mc.field_1724.method_36454();
                float pitch = mc.field_1724.method_36455();
                if (left) {
                    yaw -= 0.5f;
                }
                if (right) {
                    yaw += 0.5f;
                }
                if (up) {
                    pitch -= 0.5f;
                }
                if (down) {
                    pitch += 0.5f;
                }
                mc.field_1724.method_36456(yaw);
                mc.field_1724.method_36457(pitch);
            }
        }
        mc.field_1724.method_36457(class_3532.method_15363((float)mc.field_1724.method_36455(), (float)-90.0f, (float)90.0f));
        this.cameraPitch = class_3532.method_15363((float)this.cameraPitch, (float)-90.0f, (float)90.0f);
    }

    public boolean isActive() {
        return this.isEnabled() && class_310.method_1551().field_1724 != null;
    }

    public boolean seeThroughWalls() {
        return this.isActive() && (Boolean)this.throughWalls.get() != false;
    }

    public boolean cameraMode() {
        return this.isActive() && this.mode.is(Deobf.decrypt("0M%\u000b`\u00a5"));
    }

    public boolean playerMode() {
        return this.isActive() && class_310.method_1551().field_1690.method_31044() == class_5498.field_26665 && this.mode.is(Deobf.decrypt("#@)\u0017w\u00b6"));
    }

    public void addCameraLook(double deltaX, double deltaY) {
        float sens = this.sensitivity.getFloat();
        if (sens <= 0.0f) {
            sens = 1.0f;
        }
        this.cameraYaw += (float)(deltaX / (double)sens);
        this.cameraPitch += (float)(deltaY / (double)sens);
        if (Math.abs(this.cameraPitch) > 90.0f) {
            this.cameraPitch = this.cameraPitch > 0.0f ? 90.0f : -90.0f;
        }
    }

    public float getCameraYaw() {
        return this.cameraYaw;
    }

    public float getCameraPitch() {
        return this.cameraPitch;
    }
}

