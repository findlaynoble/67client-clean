/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_10185
 *  net.minecraft.class_1923
 *  net.minecraft.class_241
 *  net.minecraft.class_243
 *  net.minecraft.class_310
 *  net.minecraft.class_315
 *  net.minecraft.class_3532
 *  net.minecraft.class_5498
 *  net.minecraft.class_631
 *  net.minecraft.class_746
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.mixin.ClientInputAccessor;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.module.impl.AutoWalkModule;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import net.minecraft.class_10185;
import net.minecraft.class_1923;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_310;
import net.minecraft.class_315;
import net.minecraft.class_3532;
import net.minecraft.class_5498;
import net.minecraft.class_631;
import net.minecraft.class_746;

public class FreecamModule
extends Module {
    private static FreecamModule instance;
    public final SliderSetting speed = this.addSetting(new SliderSetting(Deobf.decrypt(" \\-\u000bv"), Deobf.decrypt("0M%\u000b`\u00a5\u00ec\u008c\u00a5\u0102\u0149\u0110\u0133\u0195\u01f6\u01d0"), 1.0, 0.1, 50.0, 0.1, Deobf.decrypt("\u000b")));
    public final SliderSetting verticalMultiplier = this.addSetting(new SliderSetting(Deobf.decrypt("%I:\u001a{\u00a7\u00ad\u0086\u00e9\u0108\u0119\u0106\u0126\u0194"), Deobf.decrypt("&\\g\n}\u00b3\u00a2\u00ca\u00ba\u010b\u010c\u0106\u0127\u01d0\u01fe\u01c1\u01f2\u020f\u0218\u0245\u0230\u0290\u0290\u02d0"), 1.0, 0.2, 5.0, 0.05, Deobf.decrypt("\u000b")));
    public final BooleanSetting smoothing = this.addSetting(new BooleanSetting(Deobf.decrypt(" A'\u0001f\u00ac\u00a5\u0084\u00ae"), Deobf.decrypt("6M;\u000b2\u00a7\u00ad\u0087\u00ac\u0109\u0108\u0143\u012e\u019f\u01e5\u01d1\u01f3\u021e\u021f\u0241"), true));
    public final BooleanSetting showPlayerModel = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\u00192\u00ab\u00bb\u0084\u00e9\u0119\u0106\u0107\u013a"), Deobf.decrypt("!I&\nw\u00b6\u00ec\u0093\u00a6\u010e\u011b\u0143\u0121\u019f\u01f7\u01cd\u01be\u020c\u0219\u025c\u0230\u029c\u02d5\u02c6\u02be\u030a\u0318\u031f\u0349\u03d5\u03f1"), true));
    public final BooleanSetting showHands = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\u00192\u00ac\u00ad\u0084\u00ad\u0108"), Deobf.decrypt("8I-\u001e2\u00a2\u00a5\u0098\u00ba\u010f\u0144\u0113\u0126\u0182\u01e0\u01db\u01f0\u025b\u0219\u0254\u0232\u029d\u0286\u0282\u02ad\u0317\u030a\u0315\u0343\u03dc\u03f0"), true));
    public final BooleanSetting bodyFollowsKeys = this.addSetting(new BooleanSetting(Deobf.decrypt("1C,\u00172\u00b1\u00bf\u008f\u00ba\u015b\u0104\u010c\u0135\u0195\u01fe\u01d1\u01f0\u020f\u0251\u025e\u0239\u0280\u0286"), Deobf.decrypt("#D1\u001d{\u00a7\u00ad\u0086\u00e9\u012c\u0128\u0130\u0107\u01d0\u01f2\u01d8\u01ed\u0214\u0251\u0258\u0233\u028f\u0290\u0282\u02af\u0316\u031c\u035c\u0343\u03df\u03f1\u038f"), false));
    public final SliderSetting lookSensitivity = this.addSetting(new SliderSetting(Deobf.decrypt("?C'\u00052\u0097\u00a9\u0084\u00ba\u0112\u011d\u010a\u0135\u0199\u01e7\u01cd"), Deobf.decrypt("0M%\u000b`\u00a5\u00ec\u0086\u00a6\u0114\u0102\u0143\u0130\u0195\u01fd\u01c7\u01f7\u020f\u0218\u0243\u0235\u028d\u028c"), 0.5, 0.1, 2.0, 0.05));
    public final SliderSetting freecamChunkDistance = this.addSetting(new SliderSetting(Deobf.decrypt("0D=\u0000y\u00e4\u00a8\u0083\u00ba\u010f\u0108\u010d\u0120\u0195"), Deobf.decrypt("0D=\u0000y\u00b7\u00ec\u009e\u00a6\u015b\u0105\u010c\u0122\u0194\u01b3\u01d5\u01ec\u0214\u0204\u025b\u0238\u02d9\u0281\u02ca\u02be\u035e\u031a\u031d\u034c\u03d5\u03e7\u0397"), 12.0, 2.0, 32.0, 1.0));
    private double currentX;
    private double currentY;
    private double currentZ;
    private double prevX;
    private double prevY;
    private double prevZ;
    private float currentYaw;
    private float currentPitch;
    private float prevYaw;
    private float prevPitch;
    private double savedX;
    private double savedY;
    private double savedZ;
    private float savedYaw;
    private float savedPitch;
    private boolean savedAbilitiesFlying;
    private boolean savedSmartCull;
    private class_5498 perspectiveBeforeFreecam;
    private boolean switchedPerspectiveForBody;
    private class_1923 lastSyncedCamChunk;
    private int lastSyncedLoadDistance = Integer.MIN_VALUE;
    private boolean activationPending;
    private boolean active = false;
    private boolean latchedForward;
    private boolean latchedBack;
    private boolean latchedLeft;
    private boolean latchedRight;
    private boolean latchedJump;
    private boolean latchedSneak;
    private boolean latchedSprint;
    private int autopilotWarmupTicks;

    public FreecamModule() {
        super(Deobf.decrypt("5^-\u000bq\u00a5\u00a1"), Deobf.decrypt("7I<\u000fq\u00ac\u00a9\u008e\u00e9\u0118\u0108\u010e\u0126\u0182\u01f2\u0194\u01b6\u022c\u0230\u0266\u0218\u02d9\u02c8\u0282\u02bd\u0312\u0300\u0355\u030f\u0390\u03d7\u0399\u03bd\u03c4\u040f\u042d\u040e\u0463\u0489\u04bc\u04e2\u04e4\u0555\u0524\u054f\u054d\u05c8\u05ed\u05da\u05a0\u060c\u0678\u0618\u0659\u069b\u06f7\u06d3\u06d6\u070a\u0736\u0748\u074f\u07de\u0797\u07d3\u079a\u07ac\u0845\u0812\u084a\u0868\u08c3\u08a3\u08ca"), Category.MISC);
        instance = this;
    }

    public static FreecamModule get() {
        return instance;
    }

    @Override
    protected void onEnable() {
        this.activationPending = true;
        this.active = false;
    }

    @Override
    protected void onDisable() {
        class_310 mc = class_310.method_1551();
        this.clearMovementLatches();
        this.activationPending = false;
        this.active = false;
        if (this.switchedPerspectiveForBody) {
            mc.field_1690.method_31043(this.perspectiveBeforeFreecam);
            this.switchedPerspectiveForBody = false;
        }
        this.restoreViewOnlyClientState(mc);
        this.restoreVanillaChunkLoading(mc);
        if (mc.field_1724 != null) {
            mc.field_1724.method_31549().field_7479 = this.savedAbilitiesFlying;
        }
    }

    public void tryCompleteActivation(class_310 mc) {
        if (!this.isEnabled() || !this.activationPending) {
            return;
        }
        if (mc.field_1724 == null || mc.field_1687 == null) {
            return;
        }
        this.savedX = mc.field_1724.method_23317();
        this.savedY = mc.field_1724.method_23318();
        this.savedZ = mc.field_1724.method_23321();
        this.savedYaw = mc.field_1724.method_36454();
        this.savedPitch = mc.field_1724.method_36455();
        this.currentX = this.prevX = this.savedX;
        this.currentY = this.prevY = this.savedY + (double)mc.field_1724.method_5751();
        this.currentZ = this.prevZ = this.savedZ;
        this.currentYaw = this.prevYaw = this.savedYaw;
        this.currentPitch = this.prevPitch = this.savedPitch;
        this.savedAbilitiesFlying = mc.field_1724.method_31549().field_7479;
        this.switchedPerspectiveForBody = false;
        if (((Boolean)this.showPlayerModel.get()).booleanValue()) {
            this.perspectiveBeforeFreecam = mc.field_1690.method_31044();
            if (this.perspectiveBeforeFreecam.method_31034()) {
                mc.field_1690.method_31043(class_5498.field_26665);
                this.switchedPerspectiveForBody = true;
            }
        }
        this.activationPending = false;
        this.active = true;
        this.captureMovementLatches(mc);
        this.maybeLatchWalkFromVelocity(mc);
        this.autopilotWarmupTicks = 40;
        this.lastSyncedCamChunk = null;
        this.lastSyncedLoadDistance = Integer.MIN_VALUE;
        this.applyViewOnlyClientState(mc);
        this.syncFreecamChunkLoading(mc);
        FreecamModule.reapplyBodyInput(mc);
    }

    private void captureMovementLatches(class_310 mc) {
        class_315 o = mc.field_1690;
        class_746 player = mc.field_1724;
        class_10185 ki = player != null ? player.field_3913.field_54155 : class_10185.field_54098;
        this.latchedForward = o.field_1894.method_1434() || ki.comp_3159();
        this.latchedBack = o.field_1881.method_1434() || ki.comp_3160();
        this.latchedLeft = o.field_1913.method_1434() || ki.comp_3161();
        this.latchedRight = o.field_1849.method_1434() || ki.comp_3162();
        this.latchedJump = o.field_1903.method_1434() || ki.comp_3163();
        this.latchedSneak = o.field_1832.method_1434() || ki.comp_3164() || player != null && player.method_5715();
        this.latchedSprint = o.field_1867.method_1434() || ki.comp_3165();
        this.mergeLatchFromAutoWalk();
        this.mergeLatchFromLivingSpeed(mc.field_1724);
    }

    private void mergeLatchFromAutoWalk() {
        AutoWalkModule aw;
        AutoWalkModule autoWalkModule = aw = SixSevenClient.modules() != null ? SixSevenClient.modules().autoWalk : null;
        if (aw != null && aw.isEnabled()) {
            this.latchedForward = true;
        }
    }

    public void mergeAutopilotFromCurrentState(class_310 mc) {
        if (mc.field_1724 == null) {
            return;
        }
        this.mergeLatchFromAutoWalk();
        if (((Boolean)this.bodyFollowsKeys.get()).booleanValue()) {
            class_315 o = mc.field_1690;
            class_10185 ki = mc.field_1724.field_3913.field_54155;
            this.latchedForward |= o.field_1894.method_1434() || ki.comp_3159();
            this.latchedBack |= o.field_1881.method_1434() || ki.comp_3160();
            this.latchedLeft |= o.field_1913.method_1434() || ki.comp_3161();
            this.latchedRight |= o.field_1849.method_1434() || ki.comp_3162();
            this.latchedJump |= o.field_1903.method_1434() || ki.comp_3163();
            this.latchedSneak |= o.field_1832.method_1434() || ki.comp_3164();
            this.latchedSprint |= o.field_1867.method_1434() || ki.comp_3165();
        } else {
            this.latchedSneak |= mc.field_1724.method_5715();
        }
        this.mergeLatchFromLivingSpeed(mc.field_1724);
        this.mergeVelocityIntoLatch(mc.field_1724);
    }

    private void mergeLatchFromLivingSpeed(class_746 player) {
        if (player == null) {
            return;
        }
        class_746 le = player;
        float fs = le.field_6250;
        float ss = le.field_6212;
        if (fs > 0.015f) {
            this.latchedForward = true;
        }
        if (fs < -0.015f) {
            this.latchedBack = true;
        }
        if (ss > 0.015f) {
            this.latchedLeft = true;
        }
        if (ss < -0.015f) {
            this.latchedRight = true;
        }
    }

    private void mergeVelocityIntoLatch(class_746 player) {
        class_243 vel = player.method_18798();
        double vx = vel.field_1352;
        double vz = vel.field_1350;
        if (vx * vx + vz * vz < 1.0E-10) {
            return;
        }
        class_243 flatLook = FreecamModule.flatLook(player.method_36454());
        double dot = vx * flatLook.field_1352 + vz * flatLook.field_1350;
        double perp = vx * -flatLook.field_1350 + vz * flatLook.field_1352;
        if (Math.abs(dot) >= Math.abs(perp)) {
            if (dot > 0.008) {
                this.latchedForward = true;
            } else if (dot < -0.008) {
                this.latchedBack = true;
            }
        } else if (perp > 0.008) {
            this.latchedLeft = true;
        } else if (perp < -0.008) {
            this.latchedRight = true;
        }
    }

    private void maybeLatchWalkFromVelocity(class_310 mc) {
        if (mc.field_1724 == null) {
            return;
        }
        if (this.latchedForward || this.latchedBack || this.latchedLeft || this.latchedRight) {
            return;
        }
        class_243 vel = mc.field_1724.method_18798();
        double vx = vel.field_1352;
        double vz = vel.field_1350;
        if (vx * vx + vz * vz < 1.0E-8) {
            return;
        }
        class_243 flatLook = FreecamModule.flatLook(mc.field_1724.method_36454());
        double dot = vx * flatLook.field_1352 + vz * flatLook.field_1350;
        double perp = vx * -flatLook.field_1350 + vz * flatLook.field_1352;
        if (Math.abs(dot) > Math.abs(perp)) {
            if (dot > 0.008) {
                this.latchedForward = true;
            } else if (dot < -0.008) {
                this.latchedBack = true;
            }
        } else if (perp > 0.008) {
            this.latchedLeft = true;
        } else if (perp < -0.008) {
            this.latchedRight = true;
        }
    }

    private static class_243 flatLook(float yawDeg) {
        double yawRad = Math.toRadians(yawDeg);
        return new class_243(-Math.sin(yawRad), 0.0, Math.cos(yawRad));
    }

    private void clearMovementLatches() {
        this.latchedRight = false;
        this.latchedLeft = false;
        this.latchedBack = false;
        this.latchedForward = false;
        this.latchedSprint = false;
        this.latchedSneak = false;
        this.latchedJump = false;
        this.autopilotWarmupTicks = 0;
    }

    @Override
    public void onTick() {
        class_310 client = class_310.method_1551();
        if (!this.isEnabled()) {
            return;
        }
        this.tryCompleteActivation(client);
        if (!this.active || client.field_1724 == null) {
            return;
        }
        if (this.autopilotWarmupTicks > 0) {
            --this.autopilotWarmupTicks;
            this.mergeAutopilotFromCurrentState(client);
        }
        this.prevX = this.currentX;
        this.prevY = this.currentY;
        this.prevZ = this.currentZ;
        this.prevYaw = this.currentYaw;
        this.prevPitch = this.currentPitch;
        float spd = this.speed.getFloat();
        float vMul = this.verticalMultiplier.getFloat();
        float moveFactor = (Boolean)this.smoothing.get() != false ? 0.5f : 1.0f;
        class_315 o = client.field_1690;
        double forward = 0.0;
        double strafe = 0.0;
        double vertical = 0.0;
        if (o.field_1894.method_1434()) {
            forward += 1.0;
        }
        if (o.field_1881.method_1434()) {
            forward -= 1.0;
        }
        if (o.field_1913.method_1434()) {
            strafe += 1.0;
        }
        if (o.field_1849.method_1434()) {
            strafe -= 1.0;
        }
        if (o.field_1903.method_1434()) {
            vertical += 1.0;
        }
        if (o.field_1832.method_1434()) {
            vertical -= 1.0;
        }
        class_746 p = client.field_1724;
        if (p.method_31549().field_7477) {
            p.method_31549().field_7479 = false;
        }
        double yawRad = Math.toRadians(this.currentYaw);
        double mx = -Math.sin(yawRad) * forward * (double)spd + Math.cos(yawRad) * strafe * (double)spd;
        double mz = Math.cos(yawRad) * forward * (double)spd + Math.sin(yawRad) * strafe * (double)spd;
        double my = vertical * (double)spd * (double)vMul;
        this.currentX += mx * (double)moveFactor;
        this.currentY += my * (double)moveFactor;
        this.currentZ += mz * (double)moveFactor;
        this.syncFreecamChunkLoading(client);
    }

    public static void reapplyBodyInput(class_310 client) {
        boolean sprint;
        FreecamModule f = instance;
        if (f == null || !f.isActive() || client.field_1724 == null) {
            return;
        }
        class_746 player = client.field_1724;
        class_315 o = client.field_1690;
        boolean mergePhysical = (Boolean)f.bodyFollowsKeys.get();
        boolean fwd = f.latchedForward || mergePhysical && o.field_1894.method_1434();
        boolean back = f.latchedBack || mergePhysical && o.field_1881.method_1434();
        boolean left = f.latchedLeft || mergePhysical && o.field_1913.method_1434();
        boolean right = f.latchedRight || mergePhysical && o.field_1849.method_1434();
        boolean jump = f.latchedJump;
        boolean sneak = f.latchedSneak || mergePhysical && o.field_1832.method_1434();
        boolean bl = sprint = f.latchedSprint || mergePhysical && o.field_1867.method_1434();
        if (mergePhysical) {
            if (o.field_1881.method_1434()) {
                f.latchedForward = false;
            }
            if (o.field_1894.method_1434()) {
                f.latchedBack = false;
            }
            if (o.field_1849.method_1434()) {
                f.latchedLeft = false;
            }
            if (o.field_1913.method_1434()) {
                f.latchedRight = false;
            }
        }
        player.field_3913.field_54155 = new class_10185(fwd, back, left, right, jump, sneak, sprint);
        float sx = (left ? 1.0f : 0.0f) - (right ? 1.0f : 0.0f);
        float sz = (fwd ? 1.0f : 0.0f) - (back ? 1.0f : 0.0f);
        class_241 moveVector = new class_241(sx, sz).method_35581();
        ((ClientInputAccessor)player.field_3913).sixsevenclient$setMoveVector(moveVector);
        player.method_5660(sneak);
    }

    public boolean hasLatchedLocomotion() {
        return this.latchedForward || this.latchedBack || this.latchedLeft || this.latchedRight || this.latchedJump || this.latchedSneak;
    }

    private void syncFreecamChunkLoading(class_310 client) {
        if (!this.active || client.field_1687 == null || client.field_1724 == null) {
            return;
        }
        class_631 ccm = client.field_1687.method_2935();
        class_1923 camChunk = new class_1923((int)Math.floor(this.currentX) >> 4, (int)Math.floor(this.currentZ) >> 4);
        class_1923 bodyChunk = client.field_1724.method_31476();
        class_1923 centerChunk = camChunk;
        int sepCamBody = Math.max(Math.abs(camChunk.field_9181 - bodyChunk.field_9181), Math.abs(camChunk.field_9180 - bodyChunk.field_9180));
        if (sepCamBody + 4 > 32) {
            int mx = (camChunk.field_9181 + bodyChunk.field_9181) / 2;
            int mz = (camChunk.field_9180 + bodyChunk.field_9180) / 2;
            centerChunk = new class_1923(mx, mz);
        }
        int spread = Math.max(Math.max(Math.abs(centerChunk.field_9181 - camChunk.field_9181), Math.abs(centerChunk.field_9180 - camChunk.field_9180)), Math.max(Math.abs(centerChunk.field_9181 - bodyChunk.field_9181), Math.abs(centerChunk.field_9180 - bodyChunk.field_9180)));
        int userDist = Math.min(32, Math.max(2, Math.round(this.freecamChunkDistance.getFloat())));
        int dist = Math.min(32, Math.max(userDist, spread + 4));
        if (dist != this.lastSyncedLoadDistance) {
            ccm.method_20180(dist);
            this.lastSyncedLoadDistance = dist;
        }
        if (this.lastSyncedCamChunk == null || centerChunk.field_9181 != this.lastSyncedCamChunk.field_9181 || centerChunk.field_9180 != this.lastSyncedCamChunk.field_9180) {
            ccm.method_20317(centerChunk.field_9181, centerChunk.field_9180);
            this.lastSyncedCamChunk = centerChunk;
            if (client.field_1769 != null) {
                client.field_1769.method_3292();
            }
        }
    }

    private void restoreVanillaChunkLoading(class_310 client) {
        this.lastSyncedCamChunk = null;
        this.lastSyncedLoadDistance = Integer.MIN_VALUE;
        if (client.field_1687 == null) {
            return;
        }
        class_631 ccm = client.field_1687.method_2935();
        if (client.field_1724 != null) {
            class_1923 p = client.field_1724.method_31476();
            ccm.method_20317(p.field_9181, p.field_9180);
        }
        ccm.method_20180(client.field_1690.method_38521());
        if (client.field_1769 != null) {
            client.field_1769.method_3292();
        }
    }

    private void applyViewOnlyClientState(class_310 mc) {
        this.savedSmartCull = mc.field_1730;
        mc.field_1730 = false;
    }

    private void restoreViewOnlyClientState(class_310 mc) {
        mc.field_1730 = this.savedSmartCull;
        if (mc.field_1761 != null) {
            mc.field_1761.method_2925();
        }
    }

    public boolean isActive() {
        return this.isEnabled() && this.active;
    }

    public boolean isShowPlayerModel() {
        return (Boolean)this.showPlayerModel.get();
    }

    public boolean isShowHands() {
        return (Boolean)this.showHands.get();
    }

    public boolean renderHands() {
        return !this.isActive() || this.isShowHands();
    }

    public boolean wasHoldingSneak() {
        return this.latchedSneak;
    }

    public double getInterpolatedX(float tickDelta) {
        return class_3532.method_16436((double)tickDelta, (double)this.prevX, (double)this.currentX);
    }

    public double getInterpolatedY(float tickDelta) {
        return class_3532.method_16436((double)tickDelta, (double)this.prevY, (double)this.currentY);
    }

    public double getInterpolatedZ(float tickDelta) {
        return class_3532.method_16436((double)tickDelta, (double)this.prevZ, (double)this.currentZ);
    }

    public float getInterpolatedYaw(float tickDelta) {
        return class_3532.method_16439((float)tickDelta, (float)this.prevYaw, (float)this.currentYaw);
    }

    public float getInterpolatedPitch(float tickDelta) {
        return class_3532.method_16439((float)tickDelta, (float)this.prevPitch, (float)this.currentPitch);
    }

    public class_243 getInterpolatedPos(float tickDelta) {
        return new class_243(this.getInterpolatedX(tickDelta), this.getInterpolatedY(tickDelta), this.getInterpolatedZ(tickDelta));
    }

    public void setRotation(float yaw, float pitch) {
        this.currentYaw = yaw;
        this.currentPitch = class_3532.method_15363((float)pitch, (float)-90.0f, (float)90.0f);
    }

    public float getCurrentYaw() {
        return this.currentYaw;
    }

    public float getCurrentPitch() {
        return this.currentPitch;
    }

    public float getLookSensitivity() {
        return this.lookSensitivity.getFloat();
    }
}

