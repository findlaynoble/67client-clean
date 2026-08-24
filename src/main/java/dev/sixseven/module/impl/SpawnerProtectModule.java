/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1268
 *  net.minecraft.class_1297
 *  net.minecraft.class_1299
 *  net.minecraft.class_1542
 *  net.minecraft.class_1657
 *  net.minecraft.class_1676
 *  net.minecraft.class_1703
 *  net.minecraft.class_1707
 *  net.minecraft.class_1713
 *  net.minecraft.class_1792
 *  net.minecraft.class_1799
 *  net.minecraft.class_2246
 *  net.minecraft.class_2248
 *  net.minecraft.class_2338
 *  net.minecraft.class_2350
 *  net.minecraft.class_2382
 *  net.minecraft.class_243
 *  net.minecraft.class_2480
 *  net.minecraft.class_2561
 *  net.minecraft.class_2680
 *  net.minecraft.class_310
 *  net.minecraft.class_3532
 *  net.minecraft.class_3965
 *  net.minecraft.class_634
 *  net.minecraft.class_638
 *  net.minecraft.class_746
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.settings.StringSetting;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1676;
import net.minecraft.class_1703;
import net.minecraft.class_1707;
import net.minecraft.class_1713;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2382;
import net.minecraft.class_243;
import net.minecraft.class_2480;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_310;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_634;
import net.minecraft.class_638;
import net.minecraft.class_746;

public class SpawnerProtectModule
extends Module {
    public final SliderSetting targetStackCount = this.addSetting(new SliderSetting(Deobf.decrypt(" X)\ry\u00b7\u00ec\u00be\u00a6\u015b\u012d\u0106\u0133\u019f\u01e0\u01dd\u01ea"), Deobf.decrypt(";C?N\u007f\u00a5\u00a2\u0093\u00e9\u011d\u011c\u010f\u012f\u01d0\u01e0\u01c0\u01ff\u0218\u021a\u0246\u027c\u0296\u0293\u0282\u02a8\u030e\u0318\u030b\u034f\u03d5\u03e7\u0385\u03f9\u03c9\u0440\u046e\u0402\u0464\u04c7\u04b2\u04a7\u04e3\u0540\u0562\u0557\u055e\u05c1\u05a6\u05c0\u05ba\u060a\u0630\u0650\u065d\u069c\u06fe\u0694"), 3.0, 1.0, 30.0, 1.0));
    public final SliderSetting scanRange = this.addSetting(new SliderSetting(Deobf.decrypt("'^!\tu\u00a1\u00be\u00ca\u009b\u011a\u0107\u0104\u0126"), Deobf.decrypt(";C:\u0007h\u00ab\u00a2\u009e\u00a8\u0117\u0149\u0107\u012a\u0183\u01e7\u01d5\u01f0\u0218\u0214\u0215\u023d\u02d9\u0286\u02d6\u02a9\u031f\u0317\u031b\u0344\u03c2\u03b5\u0382\u03ab\u03d4\u0448\u0429\u040a\u047f\u04da\u04f7\u04f3\u04e9\u0540\u0524\u054a\u0543\u05d1\u05f2\u05da\u05a0\u060e\u066d"), 64.0, 16.0, 128.0, 1.0, Deobf.decrypt("\u001e")));
    public final SliderSetting rotationSpeed = this.addSetting(new SliderSetting(Deobf.decrypt("!C<\u000ff\u00ad\u00a3\u0084\u00e9\u0128\u0119\u0106\u0126\u0194"), Deobf.decrypt("7I/\u001cw\u00a1\u00bf\u00ca\u00b9\u011e\u011b\u0143\u0137\u0199\u01f0\u01df\u01be\u020f\u0219\u0250\u027c\u0295\u029a\u02cd\u02b0\u035e\u031d\u0315\u0353\u03d5\u03f6\u0382\u03b0\u03d2\u0441\u046e\u0406\u047e\u0489\u04b9\u04f2\u04e5\u0542\u0561\u055c\u050c\u05d0\u05e9\u05c4\u05af\u0619\u0627\u0618\u065d\u0686\u06ea\u069a\u06cc\u070c\u0764\u075a\u0759\u07cf\u07ca"), 15.0, 1.0, 30.0, 1.0));
    public final BooleanSetting detectBlockUpdates = this.addSetting(new BooleanSetting(Deobf.decrypt("7I<\u000bq\u00b0\u00ec\u00a8\u00a5\u0114\u010a\u0108\u0163\u01a5\u01e3\u01d0\u01ff\u020f\u0214\u0246"), Deobf.decrypt("7I<\u000bq\u00b0\u00bf\u00ca\u00ad\u0112\u011a\u0117\u0122\u019e\u01e7\u0194\u01ee\u0217\u0210\u024c\u0239\u028b\u0286\u0282\u02b9\u0307\u0359\u0308\u0349\u03d5\u03fc\u0384\u03f9\u03d2\u045a\u043a\u0442\u0462\u04cf\u04fa\u04f5\u04e4\u054b\u0560\u055d\u055e\u0584\u05e4\u05df\u05a1\u0608\u0628\u0618\u0656\u0680\u06fc\u06db\u06d3\u0742\u0766\u0751\u075d\u07d8\u0781\u07d3\u0798\u07a8\u0847\u0815\u080f\u087d\u08d9\u08e0"), true));
    public final StringSetting whitelist = this.addSetting(new StringSetting(Deobf.decrypt("$D!\u001aw\u00a8\u00a5\u0099\u00bd"), Deobf.decrypt("0C%\u0003s\u00e9\u00bf\u008f\u00b9\u011a\u011b\u0102\u0137\u0195\u01f7\u0194\u01f0\u021a\u021c\u0250\u022f\u02d9\u0281\u02cd\u02fb\u0317\u031e\u0312\u034e\u03c2\u03f0\u03d8"), Deobf.decrypt(""), 256, Deobf.decrypt(" X-\u0018w\u00e8\u00ec\u00ab\u00a5\u011e\u0111")));
    public final SliderSetting breakRange = this.addSetting(new SliderSetting(Deobf.decrypt("1^-\u000fy\u00e4\u009e\u008b\u00a7\u011c\u010c"), Deobf.decrypt(";C?Nq\u00a8\u00a3\u0099\u00ac\u015b\u0108\u0143\u0130\u0180\u01f2\u01c3\u01f0\u021e\u0203\u0215\u0231\u028c\u0286\u02d6\u02fb\u031c\u031c\u035c\u0343\u03d5\u03f3\u0399\u03ab\u03d8\u040f\u0427\u041b\u042d\u04c0\u04a4\u04a7\u04e3\u0557\u056b\u0553\u0549\u05ca\u05a8"), 5.5, 1.0, 8.0, 0.5, Deobf.decrypt("\u001e")));
    public final BooleanSetting doubleCheck = this.addSetting(new BooleanSetting(Deobf.decrypt("7C=\f~\u00a1\u00ec\u00a9\u00a1\u011e\u010a\u0108"), Deobf.decrypt("!I9\u001b{\u00b6\u00a9\u0099\u00e9\u010f\u011e\u010c\u0163\u0194\u01fa\u01c7\u01ea\u021a\u021f\u0241\u027c\u029b\u0287\u02c7\u02ba\u0315\u030a\u035c\u0356\u03d9\u03e1\u039e\u03b0\u03d3\u040f\u043a\u0407\u0468\u0489\u04a0\u04ee\u04ef\u0541\u056b\u054f\u050c\u05c6\u05e3\u05d5\u05a1\u0619\u0626\u0618\u0640\u0680\u06f0\u06dd\u06df\u0708\u0764\u0754\u0752\u07dc\u07ca"), true));
    public final SliderSetting doubleCheckWindow = this.addSetting(new SliderSetting(Deobf.decrypt("7C=\f~\u00a1\u00ec\u00a9\u00a1\u011e\u010a\u0108\u0163\u01a7\u01fa\u01da\u01fa\u0214\u0206"), Deobf.decrypt("'E%\u000b2\u00b3\u00a5\u0084\u00ad\u0114\u011e\u0143\u0125\u019f\u01e1\u0194\u01ea\u0213\u0214\u0215\u0238\u0296\u0280\u02c0\u02b7\u031b\u0359\u031f\u0349\u03d5\u03f6\u039d\u03f7"), 60.0, 1.0, 600.0, 1.0, Deobf.decrypt("\u0000")));
    public final StringSetting webhookUrl = this.addSetting(new StringSetting(Deobf.decrypt("$I*\u0006}\u00ab\u00a7\u00ca\u009c\u0129\u0125"), Deobf.decrypt("<\\<\u0007}\u00aa\u00ad\u0086\u00e9\u013f\u0100\u0110\u0120\u019f\u01e1\u01d0\u01be\u020c\u0214\u0257\u0234\u0296\u029a\u02c9\u02fb\u0318\u0316\u030e\u0301\u03d1\u03f9\u0393\u03ab\u03c9\u045c\u0460"), Deobf.decrypt(""), 256, Deobf.decrypt("\u001bX<\u001ea\u00fe\u00e3\u00c5\u00ad\u0112\u011a\u0100\u012c\u0182\u01f7\u019a\u01fd\u0214\u021c\u021a\u023d\u0289\u029c\u028d\u02ac\u031b\u031b\u0314\u034e\u03df\u03fe\u0385\u03f6\u0393\u0401\u0460")));
    private final class_310 mc = class_310.method_1551();
    private State currentState = State.WAITING_FOR_STRANGER;
    private class_2338 targetBlock = null;
    private class_2338 targetChest = null;
    private int chestTick = 0;
    private int breakCooldown = 0;
    private int lagWaitTicks = 0;
    private boolean strangerDetected = false;
    private final Set<Integer> verifiedPlayers = new HashSet<Integer>();
    private int blockBreakCounter = 0;
    private long lastBreakTimestamp = 0L;
    private int shopSequence = 0;
    private int shopTick = 0;
    private int miningTicks = 0;

    public SpawnerProtectModule() {
        super(Deobf.decrypt(" \\)\u0019|\u00a1\u00be\u00ba\u00bb\u0114\u011d\u0106\u0120\u0184"), Deobf.decrypt("2Y<\u0001?\u00b7\u00ad\u0086\u00bf\u011a\u010e\u0106\u0130\u01d0\u01ea\u01db\u01eb\u0209\u0251\u0246\u022c\u0298\u0282\u02cc\u02be\u030c\u030a\u035c\u0356\u03d8\u03f0\u0398\u03f9\u03dc\u040f\u043d\u041b\u047f\u04c8\u04b9\u04e0\u04e4\u0557\u0524\u0559\u055c\u05d4\u05f4\u05dc\u05af\u0608\u062b\u065d\u0647\u06de\u06b9\u06ce\u06d0\u0708\u0778\u071d\u0750\u07d4\u0783\u0780\u07c8\u07a6\u0851\u080a\u0844"), Category.MISC);
        this.doubleCheckWindow.visibleWhen(this.doubleCheck::get);
    }

    @Override
    protected void onEnable() {
        this.resetModule();
    }

    @Override
    protected void onDisable() {
        this.stopMovement();
        this.updateSneak(false);
    }

    private void resetModule() {
        this.currentState = State.WAITING_FOR_STRANGER;
        this.strangerDetected = false;
        this.lagWaitTicks = 0;
        this.verifiedPlayers.clear();
        this.blockBreakCounter = 0;
        this.lastBreakTimestamp = 0L;
        this.resetState();
    }

    private void resetState() {
        this.targetChest = null;
        this.targetBlock = null;
        this.chestTick = 0;
        this.breakCooldown = 0;
        this.miningTicks = 0;
    }

    public boolean isTriggered() {
        return this.strangerDetected;
    }

    public String phase() {
        return this.currentState.name();
    }

    public boolean detectBlockUpdatesEnabled() {
        return (Boolean)this.detectBlockUpdates.get();
    }

    public void onBlockDestructionPacket(int breakerId, class_2338 pos) {
        double dz;
        double dx;
        double horizontalDist;
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (this.strangerDetected || player == null || level == null || this.isNearSpawn()) {
            return;
        }
        if (breakerId == player.method_5628()) {
            return;
        }
        class_1297 breaker = level.method_8469(breakerId);
        if (breaker instanceof class_1657) {
            class_1657 p = (class_1657)breaker;
            if (this.isWhitelisted(p.method_5477().getString())) {
                return;
            }
        } else if (breaker == null) {
            for (class_1657 p : level.method_18456()) {
                if (!(p.method_33571().method_1022(class_243.method_24953((class_2382)pos)) < 8.0) || !this.isWhitelisted(p.method_5477().getString())) continue;
                return;
            }
        }
        if ((horizontalDist = Math.sqrt((dx = player.method_23317() - (double)pos.method_10263()) * dx + (dz = player.method_23321() - (double)pos.method_10260()) * dz)) <= (Double)this.scanRange.get()) {
            this.strangerDetected = true;
            this.currentState = State.WORKING;
            String name = breaker != null ? breaker.method_5477().getString() : Deobf.decrypt(":B>\u0007a\u00ad\u00ae\u0086\u00ac\u0154\u0128\u010d\u0137\u0199\u01be\u01f1\u01cd\u022b");
            this.warn("\ud83d\udea8 PACKET DETECT: " + name + " started breaking! (Horizontal: " + (int)horizontalDist + "m)");
            this.sendWebhook("\ud83d\udea8 **PACKET DETECT:** `" + name + "` started breaking! (Horizontal: " + (int)horizontalDist + "m, Y: " + pos.method_10264() + ").");
        }
    }

    public void onServerBlockUpdate(class_2338 pos, class_2680 newState, boolean multi) {
        double dz;
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (this.strangerDetected || player == null || level == null || this.isNearSpawn()) {
            return;
        }
        for (class_1657 p : level.method_18456()) {
            if (!(p.method_33571().method_1022(class_243.method_24953((class_2382)pos)) < 8.0) || !this.isWhitelisted(p.method_5477().getString())) continue;
            return;
        }
        if (pos.method_10264() <= -64 || !newState.method_26215()) {
            return;
        }
        class_2680 oldState = level.method_8320(pos);
        if (oldState.method_26215() || oldState.method_26204() instanceof class_2480) {
            return;
        }
        double distToPlayer = player.method_33571().method_1022(class_243.method_24953((class_2382)pos));
        if (distToPlayer < 6.0) {
            return;
        }
        double dx = player.method_23317() - (double)pos.method_10263();
        double horizontalDist = Math.sqrt(dx * dx + (dz = player.method_23321() - (double)pos.method_10260()) * dz);
        if (horizontalDist > (Double)this.scanRange.get()) {
            return;
        }
        if (((Boolean)this.doubleCheck.get()).booleanValue()) {
            long now = System.currentTimeMillis();
            if (now - this.lastBreakTimestamp > (long)this.doubleCheckWindow.getInt() * 1000L) {
                this.blockBreakCounter = 0;
            }
            ++this.blockBreakCounter;
            this.lastBreakTimestamp = now;
            if (this.blockBreakCounter < 2) {
                return;
            }
        }
        this.strangerDetected = true;
        this.currentState = State.WORKING;
        String reason = multi ? Deobf.decrypt("\u001eY$\u001a{\u00e9\u00ae\u0086\u00a6\u0118\u0102\u0143\u0121\u0182\u01f6\u01d5\u01f5") : Deobf.decrypt("\u0011@'\ry\u00e4\u00ae\u0098\u00ac\u011a\u0102");
        this.warn("\ud83d\udea8 REMOTE DETECT: a block broke nearby! (Horizontal: " + (int)horizontalDist + "m, Y: " + pos.method_10264() + ")");
        this.sendWebhook("\ud83d\udea8 **REMOTE DETECT (" + reason + "):** an invisible or far-away player broke a block! (Horizontal: " + (int)horizontalDist + "m, Y: " + pos.method_10264() + ")");
    }

    @Override
    public void onTick() {
        double dx;
        double horizontalDist;
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (player == null || level == null) {
            return;
        }
        if (this.isNearSpawn()) {
            if (this.strangerDetected) {
                this.resetModule();
            }
            return;
        }
        if (!this.strangerDetected) {
            for (class_1657 p : level.method_18456()) {
                double dz;
                if (p == player || this.isWhitelisted(p.method_5477().getString()) || (horizontalDist = Math.sqrt((dx = player.method_23317() - p.method_23317()) * dx + (dz = player.method_23321() - p.method_23321()) * dz)) > (Double)this.scanRange.get()) continue;
                boolean verified = this.verifiedPlayers.contains(p.method_5628());
                if (!verified && p.method_6115()) {
                    verified = true;
                    this.verifiedPlayers.add(p.method_5628());
                }
                if (!verified) continue;
                this.strangerDetected = true;
                this.currentState = State.WORKING;
                this.warn("\u26a0 PLAYER SPOTTED (verified): " + p.method_5477().getString() + " (Horizontal: " + (int)horizontalDist + "m)");
                this.sendWebhook("\u26a0 **PLAYER SPOTTED (verified):** `" + p.method_5477().getString() + "` (Horizontal: " + (int)horizontalDist + "m, Y: " + p.method_31478() + ")!");
                break;
            }
        }
        if (!this.strangerDetected) {
            for (class_1297 entity : level.method_18112()) {
                class_1657 owner;
                class_1676 proj;
                class_1297 dz;
                if (entity.method_5864() != class_1299.field_6082 || entity instanceof class_1676 && (dz = (proj = (class_1676)entity).method_24921()) instanceof class_1657 && this.isWhitelisted((owner = (class_1657)dz).method_5477().getString()) || !((horizontalDist = Math.sqrt((dx = player.method_23317() - entity.method_23317()) * dx + (dz = player.method_23321() - entity.method_23321()) * dz)) <= (Double)this.scanRange.get())) continue;
                this.strangerDetected = true;
                this.currentState = State.WORKING;
                this.warn("\u26a0 ENDER PEARL SPOTTED! (Horizontal: " + (int)horizontalDist + "m)");
                this.sendWebhook("\u26a0 **ENDER PEARL SPOTTED!** Someone threw a pearl. (Horizontal: " + (int)horizontalDist + "m)!");
                break;
            }
        }
        if (this.currentState == State.WAITING_FOR_STRANGER) {
            return;
        }
        if (this.currentState != State.OPENING_CHEST && this.currentState != State.DEPOSITING_ITEMS && this.currentState != State.BUYING_ECHEST) {
            this.updateSneak(true);
        }
        this.handleRotation();
        switch (this.currentState.ordinal()) {
            case 1: {
                this.handleWorking();
                break;
            }
            case 2: {
                this.handleGoingToChest();
                break;
            }
            case 3: {
                this.handleOpeningChest();
                break;
            }
            case 4: {
                this.handleDepositing();
                break;
            }
            case 5: {
                this.handleFinalExit();
                break;
            }
            case 6: {
                this.handleBuyingEChest();
                break;
            }
            case 7: {
                this.handlePlacingEChest();
                break;
            }
        }
    }

    private void handleRotation() {
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (player == null || level == null) {
            return;
        }
        class_243 targetPos = null;
        if (this.currentState == State.WORKING) {
            class_1542 dropped = this.findDroppedSpawner();
            if (dropped != null) {
                targetPos = dropped.method_73189();
            } else {
                if (this.targetBlock == null || level.method_8320(this.targetBlock).method_26204() != class_2246.field_10260 || player.method_24515().method_10262((class_2382)this.targetBlock) > 256.0) {
                    this.targetBlock = this.findRandomBlock(class_2246.field_10260, 16);
                    this.miningTicks = 0;
                }
                if (this.targetBlock != null) {
                    targetPos = class_243.method_24953((class_2382)this.targetBlock);
                }
            }
        } else if ((this.currentState == State.GOING_TO_CHEST || this.currentState == State.OPENING_CHEST) && this.targetChest != null) {
            targetPos = class_243.method_24953((class_2382)this.targetChest);
        }
        if (targetPos != null) {
            this.smoothLook(targetPos);
        }
    }

    private void smoothLook(class_243 target) {
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        class_243 eyes = player.method_33571();
        double dx = target.field_1352 - eyes.field_1352;
        double dy = target.field_1351 - eyes.field_1351;
        double dz = target.field_1350 - eyes.field_1350;
        double dist = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float)Math.toDegrees(Math.atan2(-dx, dz));
        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, dist)));
        float step = this.rotationSpeed.getFloat();
        player.method_36456(player.method_36454() + class_3532.method_15363((float)class_3532.method_15393((float)(targetYaw - player.method_36454())), (float)(-step), (float)step));
        player.method_36457(player.method_36455() + class_3532.method_15363((float)class_3532.method_15393((float)(targetPitch - player.method_36455())), (float)(-step), (float)step));
    }

    private void handleWorking() {
        class_1542 dropped;
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (player == null || level == null) {
            return;
        }
        if (player.field_7512 != player.field_7498) {
            player.method_7346();
        }
        if ((dropped = this.findDroppedSpawner()) != null) {
            this.lagWaitTicks = 0;
            this.stopBreaking();
            this.mc.field_1690.field_1894.method_23481(true);
            return;
        }
        if (this.getSpawnerCount() >= this.targetStackCount.getInt() * 64) {
            this.goToChest();
            return;
        }
        if (this.targetBlock == null || level.method_8320(this.targetBlock).method_26204() != class_2246.field_10260 || player.method_24515().method_10262((class_2382)this.targetBlock) > 256.0) {
            this.targetBlock = this.findRandomBlock(class_2246.field_10260, 16);
            this.miningTicks = 0;
        }
        if (this.targetBlock == null) {
            this.stopMovement();
            if (this.lagWaitTicks < 40) {
                ++this.lagWaitTicks;
            } else if (this.getSpawnerCount() > 0) {
                this.goToChest();
            } else {
                this.currentState = State.FINAL_EXIT;
                this.lagWaitTicks = 0;
            }
            return;
        }
        this.lagWaitTicks = 0;
        double dist = player.method_33571().method_1022(class_243.method_24953((class_2382)this.targetBlock));
        if (dist <= (Double)this.breakRange.get()) {
            this.mc.field_1690.field_1894.method_23481(false);
            ++this.miningTicks;
            if (this.miningTicks > 60) {
                this.mc.field_1761.method_2896(player, class_1268.field_5808, new class_3965(class_243.method_24953((class_2382)this.targetBlock), class_2350.field_11036, this.targetBlock, false));
                player.method_6104(class_1268.field_5808);
                this.miningTicks = 0;
            }
            if (this.breakCooldown <= 0) {
                this.mc.field_1761.method_2902(this.targetBlock, class_2350.field_11036);
                player.method_6104(class_1268.field_5808);
                this.mc.field_1690.field_1886.method_23481(true);
                this.breakCooldown = 6;
            } else {
                --this.breakCooldown;
            }
        } else {
            this.stopBreaking();
            this.mc.field_1690.field_1894.method_23481(true);
        }
    }

    private void goToChest() {
        this.stopMovement();
        this.targetChest = this.findNearestBlock(class_2246.field_10443, 4);
        if (this.targetChest != null) {
            this.currentState = State.GOING_TO_CHEST;
        } else if (this.getEnderChestCount() > 0) {
            this.currentState = State.PLACING_ECHEST;
            this.shopTick = 0;
        } else {
            this.currentState = State.BUYING_ECHEST;
            this.shopSequence = 0;
            this.shopTick = 0;
        }
    }

    private void handleGoingToChest() {
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        if (this.targetChest == null) {
            this.targetChest = this.findNearestBlock(class_2246.field_10443, 4);
        }
        if (this.targetChest == null) {
            this.currentState = State.WORKING;
            return;
        }
        this.mc.field_1690.field_1894.method_23481(true);
        if (player.method_24515().method_19771((class_2382)this.targetChest, 4.0)) {
            this.stopMovement();
            this.currentState = State.OPENING_CHEST;
        }
    }

    private void handleOpeningChest() {
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        this.updateSneak(false);
        if (this.chestTick % 12 == 0 && this.targetChest != null) {
            this.mc.field_1761.method_2896(player, class_1268.field_5808, new class_3965(class_243.method_24953((class_2382)this.targetChest), class_2350.field_11036, this.targetChest, false));
        }
        ++this.chestTick;
        if (player.field_7512 instanceof class_1707) {
            this.chestTick = 0;
            this.currentState = State.DEPOSITING_ITEMS;
            this.lagWaitTicks = 0;
        }
    }

    private void handleBuyingEChest() {
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        ++this.shopTick;
        if (this.shopSequence > 0 && this.mc.field_1755 == null && this.shopTick > 60) {
            this.say(Deobf.decrypt(" D'\u001e2\u00b7\u00af\u0098\u00ac\u011e\u0107\u0143\u0120\u019c\u01fc\u01c7\u01fb\u021f\u025d\u0215\u022e\u029c\u0281\u02d0\u02a2\u0317\u0317\u031b\u030f\u039e\u03bb"));
            this.shopSequence = 0;
            this.shopTick = 0;
            return;
        }
        if (this.shopTick < 30) {
            return;
        }
        switch (this.shopSequence) {
            case 0: {
                this.say(Deobf.decrypt("<\\-\u0000{\u00aa\u00ab\u00ca\u00ba\u0113\u0106\u0113\u0179\u01d0\u01bc\u01c7\u01f6\u0214\u0201"));
                if (this.mc.method_1562() != null) {
                    this.mc.method_1562().method_45730(Deobf.decrypt("\u0000D'\u001e"));
                }
                this.shopSequence = 1;
                this.shopTick = 0;
                break;
            }
            case 1: {
                if (this.mc.field_1755 == null || !this.screenTitle().contains(Deobf.decrypt(" d\u0007>"))) break;
                this.say(Deobf.decrypt(" D'\u001e(\u00e4\u00bf\u008f\u00a5\u011e\u010a\u0117\u012a\u019e\u01f4\u0194\u01ea\u0213\u0214\u0215\u0219\u0297\u0291\u0282\u02b8\u031f\u030d\u0319\u0346\u03df\u03e7\u038f\u03f7\u0393\u0401"));
                this.clickSlot(11, class_1713.field_7790);
                this.shopSequence = 2;
                this.shopTick = 0;
                break;
            }
            case 2: {
                if (this.mc.field_1755 == null || !this.screenTitle().contains(Deobf.decrypt("6b\f"))) break;
                this.say(Deobf.decrypt(" D'\u001e(\u00e4\u00bf\u008f\u00a5\u011e\u010a\u0117\u012a\u019e\u01f4\u0194\u01db\u0215\u0215\u0250\u022e\u02d9\u02b6\u02ca\u02be\u030d\u030d\u0352\u030f\u039e"));
                this.clickSlot(9, class_1713.field_7790);
                this.shopSequence = 3;
                this.shopTick = 0;
                break;
            }
            case 3: {
                if (this.mc.field_1755 == null || !this.screenTitle().contains(Deobf.decrypt("6b\f+@\u00e4\u008f\u00a2\u008c\u0128\u013d"))) break;
                this.say(Deobf.decrypt(" D'\u001e(\u00e4\u00af\u0085\u00a7\u011d\u0100\u0111\u012e\u0199\u01fd\u01d3\u01be\u020b\u0204\u0247\u023f\u0291\u0294\u02d1\u02be\u0350\u0357\u0352"));
                this.clickSlot(25, class_1713.field_7790);
                this.shopSequence = 4;
                this.shopTick = 0;
                break;
            }
            case 4: {
                if (this.getEnderChestCount() > 0) {
                    this.say(Deobf.decrypt("6B,\u000b`\u00e4\u008f\u0082\u00ac\u0108\u011d\u0143\u0133\u0185\u01e1\u01d7\u01f6\u021a\u0202\u0250\u0238\u02d7"));
                    player.method_7346();
                    this.currentState = State.PLACING_ECHEST;
                    this.shopTick = 0;
                    break;
                }
                if (this.shopTick <= 100) break;
                this.say(Deobf.decrypt("#Y:\rz\u00a5\u00bf\u008f\u00e9\u011d\u0108\u010a\u012f\u0195\u01f7\u0194\u01b6\u020f\u0218\u0258\u0239\u0296\u0280\u02d6\u02f2\u0350"));
                player.method_7346();
                this.shopSequence = 0;
                this.shopTick = 0;
                break;
            }
        }
    }

    private void handlePlacingEChest() {
        int i;
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (player == null || level == null) {
            return;
        }
        ++this.shopTick;
        if (this.shopTick < 10) {
            return;
        }
        int slot = -1;
        for (i = 0; i < 9; ++i) {
            if (player.method_31548().method_5438(i).method_7909() != class_2246.field_10443.method_8389()) continue;
            slot = i;
            break;
        }
        if (slot == -1) {
            for (i = 9; i < 36; ++i) {
                if (player.method_31548().method_5438(i).method_7909() != class_2246.field_10443.method_8389()) continue;
                this.mc.field_1761.method_2906(player.field_7512.field_7763, i, 0, class_1713.field_7794, (class_1657)player);
                this.shopTick = 0;
                return;
            }
            this.currentState = State.BUYING_ECHEST;
            this.shopSequence = 0;
            return;
        }
        player.method_31548().method_61496(slot);
        class_2338 p = player.method_24515();
        class_2338 placePos = null;
        for (class_2350 d : class_2350.values()) {
            class_2338 bp;
            if (d == class_2350.field_11036 || d == class_2350.field_11033 || !level.method_8320(bp = p.method_10093(d)).method_45474()) continue;
            placePos = bp;
            break;
        }
        if (placePos != null) {
            this.mc.field_1761.method_2896(player, class_1268.field_5808, new class_3965(class_243.method_24953(placePos), class_2350.field_11036, placePos, false));
            player.method_6104(class_1268.field_5808);
            this.targetChest = placePos;
            this.currentState = State.GOING_TO_CHEST;
        } else {
            this.warn(Deobf.decrypt("=Ch\u001db\u00ab\u00b8\u00ca\u00bd\u0114\u0149\u0113\u012f\u0191\u01f0\u01d1\u01be\u020f\u0219\u0250\u027c\u02bc\u029b\u02c6\u02be\u030c\u0359\u033f\u0349\u03d5\u03e6\u0382\u03f8"));
            this.currentState = State.FINAL_EXIT;
            this.lagWaitTicks = 0;
        }
    }

    private void handleDepositing() {
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        if (this.lagWaitTicks < 15) {
            ++this.lagWaitTicks;
            return;
        }
        class_1703 class_17032 = player.field_7512;
        if (!(class_17032 instanceof class_1707)) {
            return;
        }
        class_1707 handler = (class_1707)class_17032;
        int chestSize = handler.field_7761.size() - 36;
        boolean hasSpace = false;
        for (int i = 0; i < chestSize; ++i) {
            class_1799 stack = handler.method_7611(i).method_7677();
            if (!stack.method_7960() && (stack.method_7909() != class_2246.field_10260.method_8389() || stack.method_7947() >= stack.method_7914())) continue;
            hasSpace = true;
            break;
        }
        int invStart = chestSize;
        for (int i = 0; i < 36; ++i) {
            int slotId = invStart + i;
            if (handler.method_7611(slotId).method_7677().method_7909() != class_2246.field_10260.method_8389()) continue;
            if (!hasSpace) {
                this.currentState = State.FINAL_EXIT;
                this.lagWaitTicks = 0;
                return;
            }
            this.mc.field_1761.method_2906(handler.field_7763, slotId, 0, class_1713.field_7794, (class_1657)player);
            return;
        }
        player.method_7346();
        if (this.findNearestBlock(class_2246.field_10260, 16) != null) {
            this.currentState = State.WORKING;
        } else {
            this.currentState = State.FINAL_EXIT;
            this.lagWaitTicks = 0;
        }
    }

    private void handleFinalExit() {
        if (this.lagWaitTicks == 0) {
            this.sendWebhook(Deobf.decrypt("\ud84f\udfedhD8\u0080\u0083\u00a4\u008c\u0155\u0143\u0149\u0163\u01a7\u01f2\u01dd\u01ea\u0212\u021f\u0252\u027c\u029f\u029a\u02d0\u02fb\u0317\u030d\u0319\u034c\u03c3\u03b5\u0382\u03b6\u039d\u045c\u042f\u0419\u0468\u0487\u04f9\u04a9"));
            this.say(Deobf.decrypt("'M;\u00052\u00a0\u00a3\u0084\u00ac\u015b\u217d\u0143\u0134\u0191\u01fa\u01c0\u01f7\u0215\u0216\u0215\u026e\u028a\u02d5\u02c4\u02b4\u030c\u0359\u0308\u0349\u03d5\u03b5\u0385\u03bc\u03cf\u0459\u042b\u041d\u042d\u04dd\u04b8\u04a7\u04f2\u0544\u0572\u055d\u0500\u0584\u05f2\u05db\u05ab\u0605\u0663\u065c\u065d\u0681\u06fa\u06d5\u06d6\u0703\u0773\u075e\u0748\u07d2\u078a\u0794\u07c6\u07e7\u080a"));
        }
        ++this.lagWaitTicks;
        if (this.lagWaitTicks > 40) {
            class_634 conn = this.mc.method_1562();
            if (conn != null) {
                conn.method_48296().method_10747((class_2561)class_2561.method_43470((String)Deobf.decrypt("(\u007f8\u000fe\u00aa\u00a9\u0098\u0099\u0109\u0106\u0117\u0126\u0193\u01e7\u01e9\u01be\u022f\u0210\u0246\u0237\u02d9\u0296\u02cd\u02b6\u030e\u0315\u0319\u0355\u03d5\u03b9\u03d6\u03aa\u03dc\u0449\u042b\u044f\u0469\u04c0\u04a4\u04e4\u04ee\u054b\u056a\u055d\u054f\u05d0\u05a8")));
            }
            this.resetModule();
        }
    }

    private void updateSneak(boolean sneak) {
        class_746 player = this.mc.field_1724;
        if (player != null) {
            player.method_5660(sneak);
        }
        this.mc.field_1690.field_1832.method_23481(sneak);
    }

    private boolean isNearSpawn() {
        class_746 player = this.mc.field_1724;
        return player != null && Math.abs(player.method_23317()) < 100.0 && Math.abs(player.method_23321()) < 100.0;
    }

    private boolean isWhitelisted(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        for (String n : ((String)this.whitelist.get()).split(Deobf.decrypt("_"))) {
            if (!n.trim().equalsIgnoreCase(name)) continue;
            return true;
        }
        return false;
    }

    private String screenTitle() {
        return this.mc.field_1755 == null ? Deobf.decrypt("") : this.mc.field_1755.method_25440().getString().toUpperCase(Locale.ROOT);
    }

    private void clickSlot(int slot, class_1713 type) {
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return;
        }
        this.mc.field_1761.method_2906(player.field_7512.field_7763, slot, 0, type, (class_1657)player);
    }

    private class_2338 findNearestBlock(class_2248 block, int r) {
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (player == null || level == null) {
            return null;
        }
        class_2338 p = player.method_24515();
        class_2338 nearest = null;
        double minDist = Double.MAX_VALUE;
        for (int x = -r; x <= r; ++x) {
            for (int y = -r; y <= r; ++y) {
                for (int z = -r; z <= r; ++z) {
                    double d;
                    class_2338 bp = p.method_10069(x, y, z);
                    if (level.method_8320(bp).method_26204() != block || !((d = p.method_10262((class_2382)bp)) < minDist)) continue;
                    minDist = d;
                    nearest = bp;
                }
            }
        }
        return nearest;
    }

    private class_2338 findRandomBlock(class_2248 block, int r) {
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (player == null || level == null) {
            return null;
        }
        class_2338 p = player.method_24515();
        ArrayList<class_2338> list = new ArrayList<class_2338>();
        double maxDistSq = (double)r * (double)r;
        for (int x = -r; x <= r; ++x) {
            for (int y = -r; y <= r; ++y) {
                for (int z = -r; z <= r; ++z) {
                    class_2338 bp = p.method_10069(x, y, z);
                    if (!(p.method_10262((class_2382)bp) <= maxDistSq) || level.method_8320(bp).method_26204() != block) continue;
                    list.add(bp);
                }
            }
        }
        return list.isEmpty() ? null : (class_2338)list.get(new Random().nextInt(list.size()));
    }

    private int getSpawnerCount() {
        return this.countItem(class_2246.field_10260.method_8389(), 36);
    }

    private int getEnderChestCount() {
        return this.countItem(class_2246.field_10443.method_8389(), 45);
    }

    private int countItem(class_1792 item, int slots) {
        class_746 player = this.mc.field_1724;
        if (player == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < slots; ++i) {
            class_1799 stack = player.method_31548().method_5438(i);
            if (stack.method_7909() != item) continue;
            count += stack.method_7947();
        }
        return count;
    }

    private class_1542 findDroppedSpawner() {
        class_746 player = this.mc.field_1724;
        class_638 level = this.mc.field_1687;
        if (player == null || level == null) {
            return null;
        }
        class_1542 best = null;
        double bestDist = Double.MAX_VALUE;
        for (class_1297 e : level.method_18112()) {
            double d;
            class_1542 item;
            if (!(e instanceof class_1542) || (item = (class_1542)e).method_6983().method_7909() != class_2246.field_10260.method_8389() || !((d = (double)player.method_5739((class_1297)item)) < 16.0) || !(d < bestDist)) continue;
            bestDist = d;
            best = item;
        }
        return best;
    }

    private void stopBreaking() {
        this.mc.field_1690.field_1886.method_23481(false);
        this.breakCooldown = 0;
    }

    private void stopMovement() {
        this.stopBreaking();
        this.mc.field_1690.field_1894.method_23481(false);
    }

    private void warn(String msg) {
        class_746 player = this.mc.field_1724;
        if (player != null) {
            player.method_7353((class_2561)class_2561.method_43470((String)("\u00a7c[SpawnerProtect] \u00a7f" + msg)), false);
        }
        try {
            SixSevenClient.notifications().pushInfo("SpawnerProtect \u00b7 " + msg.replaceAll(Deobf.decrypt("\u00d4\u0002"), Deobf.decrypt("")));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private void say(String msg) {
        class_746 player = this.mc.field_1724;
        if (player != null) {
            player.method_7353((class_2561)class_2561.method_43470((String)("\u00a77[SpawnerProtect] " + msg)), false);
        }
    }

    private void sendWebhook(String message) {
        String urlStr = ((String)this.webhookUrl.get()).trim();
        if (urlStr.isEmpty()) {
            return;
        }
        String json = "{\"content\": \"" + message.replace(Deobf.decrypt("/"), Deobf.decrypt("/p")).replace(Deobf.decrypt("Q"), Deobf.decrypt("/\u000e")) + "\"}";
        new Thread(() -> {
            try {
                HttpURLConnection conn = (HttpURLConnection)URI.create(urlStr).toURL().openConnection();
                conn.setRequestMethod(Deobf.decrypt("#c\u001b:"));
                conn.setRequestProperty(Deobf.decrypt("0C&\u001aw\u00aa\u00b8\u00c7\u009d\u0102\u0119\u0106"), Deobf.decrypt("\u0012\\8\u0002{\u00a7\u00ad\u009e\u00a0\u0114\u0107\u014c\u0129\u0183\u01fc\u01da"));
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream();){
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                }
                conn.getInputStream().close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }, Deobf.decrypt(" \\)\u0019|\u00a1\u00be\u00ba\u00bb\u0114\u011d\u0106\u0120\u0184\u01be\u01e3\u01fb\u0219\u0219\u025a\u0233\u0292")).start();
    }

    private static enum State {
        WAITING_FOR_STRANGER,
        WORKING,
        GOING_TO_CHEST,
        OPENING_CHEST,
        DEPOSITING_ITEMS,
        FINAL_EXIT,
        BUYING_ECHEST,
        PLACING_ECHEST;

    }
}

