/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;

public class HitBoxModule
extends Module {
    public final SliderSetting expand = this.addSetting(new SliderSetting(Deobf.decrypt("6T8\u000f|\u00a0"), Deobf.decrypt("7E;\u000fp\u00a8\u00a9\u008e\u00e9\u010d\u011a\u0143\u0133\u0182\u01f6\u01d0\u01f7\u0218\u0205\u025c\u0233\u0297\u02d5\u02c3\u02b5\u030a\u0310\u031f\u0349\u03d5\u03f4\u0382\u03aa\u039d\u243b\u046e\u0404\u0468\u04d9\u04a3\u04a7\u04e7\u054a\u0576\u0518\u054f\u05cb\u05e8\u05d5\u05a7\u060c\u0663\u065b\u065b\u069f\u06e9\u06db\u06cc\u0704\u0774\u0754\u0750\u07d2\u0790\u078a"), 0.5, 0.0, 2.0, 0.05));
    public final BooleanSetting enableRender = this.addSetting(new BooleanSetting(Deobf.decrypt("6B)\f~\u00a1\u00ec\u00b8\u00ac\u0115\u010d\u0106\u0131"), Deobf.decrypt(""), true));

    public HitBoxModule() {
        super(Deobf.decrypt(";E<,}\u00bc"), Deobf.decrypt(";E<\f}\u00bc\u00ec\u008f\u00b1\u010b\u0108\u010d\u0127\u0195\u01e1\u0194\u01b6\u0215\u0214\u0240\u0228\u029c\u0287\u02c7\u02bf\u0344\u0359\u0309\u034f\u03d4\u03f0\u0382\u03bc\u03de\u045b\u042f\u040d\u0461\u04cc\u04f7\u04ba\u04a1\u0541\u056b\u055d\u055f\u0584\u05e8\u05dc\u05ba\u064b\u0634\u0651\u0650\u0697\u06f7\u069a\u06cb\u0708\u0764\u074b\u0759\u07c9\u07c4\u079b\u0781\u07bd\u0846\u0811\u0812\u086c\u08d9\u08e7"), Category.COMBAT);
    }

    public float getHitboxExpansion() {
        return this.isEnabled() ? this.expand.getFloat() : 0.0f;
    }

    public boolean shouldRender() {
        return this.isEnabled() && (Boolean)this.enableRender.get() != false;
    }
}

