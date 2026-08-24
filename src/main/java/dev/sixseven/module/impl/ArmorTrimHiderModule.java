/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1799
 *  net.minecraft.class_310
 *  net.minecraft.class_5455
 *  net.minecraft.class_6880
 *  net.minecraft.class_7924
 *  net.minecraft.class_8053
 *  net.minecraft.class_8054
 *  net.minecraft.class_8056
 *  org.jetbrains.annotations.Nullable
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_5455;
import net.minecraft.class_6880;
import net.minecraft.class_7924;
import net.minecraft.class_8053;
import net.minecraft.class_8054;
import net.minecraft.class_8056;
import org.jetbrains.annotations.Nullable;

public class ArmorTrimHiderModule
extends Module {
    public final ModeSetting mode = this.addSetting(new ModeSetting(Deobf.decrypt(">C,\u000b"), Deobf.decrypt(";E,\u000b2\u00b3\u00a5\u009a\u00ac\u0108\u0149\u0114\u012c\u0182\u01fd\u0194\u01ea\u0209\u0218\u0258\u022f\u02c2\u02d5\u02f0\u02ba\u0310\u031d\u0313\u034c\u0390\u03f2\u039f\u03af\u03d8\u045c\u046e\u040a\u047b\u04cc\u04a5\u04fe\u04a1\u0555\u056d\u055d\u054f\u05c1\u05a6\u05d2\u05ee\u0619\u0622\u0656\u0650\u069d\u06f4\u069a\u06d7\u0703\u0773"), Deobf.decrypt(";E,\u000b"), Deobf.decrypt(";E,\u000b"), Deobf.decrypt("!M&\n}\u00a9")));
    public final BooleanSetting ownArmor = this.addSetting(new BooleanSetting(Deobf.decrypt("<[&NS\u00b6\u00a1\u0085\u00bb"), Deobf.decrypt("2@;\u00012\u00a5\u00aa\u008c\u00ac\u0118\u011d\u0143\u013a\u019f\u01e6\u01c6\u01be\u0214\u0206\u025b\u027c\u028e\u029a\u02d0\u02b5\u035e\u0318\u030e\u034c\u03df\u03e7\u03d6\u03f1\u03fb\u041a\u046e\u0440\u042d\u04c0\u04b9\u04f1\u04e4\u054b\u0570\u0557\u055e\u05dd\u05af"), true));
    private class_5455 cachedAccess;
    private final List<class_6880<class_8054>> materials = new ArrayList<class_6880<class_8054>>();
    private final List<class_6880<class_8056>> patterns = new ArrayList<class_6880<class_8056>>();

    public ArmorTrimHiderModule() {
        super(Deobf.decrypt("2^%\u0001`\u0090\u00be\u0083\u00a4\u0133\u0100\u0107\u0126\u0182"), Deobf.decrypt(";E,\u000ba\u00e4\u00a3\u0098\u00e9\u0109\u0108\u010d\u0127\u019f\u01fe\u01dd\u01e4\u021e\u0202\u0215\u022b\u0296\u0287\u02cc\u02fb\u031f\u030b\u0311\u034e\u03c2\u03b5\u0382\u03ab\u03d4\u0442\u043d"), Category.MISC);
    }

    public boolean affectsOwn() {
        return (Boolean)this.ownArmor.get();
    }

    @Nullable
    public class_8053 mapTrim(class_1799 stack, @Nullable class_8053 original) {
        if (this.mode.is(Deobf.decrypt("!M&\n}\u00a9"))) {
            class_8053 random = this.randomTrim(stack);
            return random != null ? random : original;
        }
        return null;
    }

    @Nullable
    private class_8053 randomTrim(class_1799 stack) {
        class_310 mc = class_310.method_1551();
        if (mc.field_1687 == null) {
            return null;
        }
        class_5455 access = mc.field_1687.method_30349();
        if (access != this.cachedAccess) {
            this.rebuildCache(access);
        }
        if (this.materials.isEmpty() || this.patterns.isEmpty()) {
            return null;
        }
        Random rng = new Random(stack.method_7909().method_7876().hashCode());
        class_6880<class_8054> material = this.materials.get(rng.nextInt(this.materials.size()));
        class_6880<class_8056> pattern = this.patterns.get(rng.nextInt(this.patterns.size()));
        return new class_8053(material, pattern);
    }

    private void rebuildCache(class_5455 access) {
        this.cachedAccess = access;
        this.materials.clear();
        this.patterns.clear();
        try {
            access.method_30530(class_7924.field_42083).method_42017().forEach(this.materials::add);
            access.method_30530(class_7924.field_42082).method_42017().forEach(this.patterns::add);
        }
        catch (Exception ignored) {
            this.materials.clear();
            this.patterns.clear();
        }
    }

    @Override
    protected void onDisable() {
        this.cachedAccess = null;
        this.materials.clear();
        this.patterns.clear();
    }
}

