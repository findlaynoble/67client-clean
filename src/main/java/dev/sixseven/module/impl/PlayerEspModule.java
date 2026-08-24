/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;

public class PlayerEspModule
extends Module {
    public final ModeSetting style = this.addSetting(new ModeSetting(Deobf.decrypt(" X1\u0002w"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u011a\u0117\u013a\u019c\u01f6"), Deobf.decrypt("<Y<\u0002{\u00aa\u00a9"), Deobf.decrypt("1C0"), Deobf.decrypt("<Y<\u0002{\u00aa\u00a9"), Deobf.decrypt("4@'\u0019")));
    public final ColorSetting color = this.addSetting(new ColorSetting(Deobf.decrypt("0C$\u0001`"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u015b\u010a\u010c\u012f\u019f\u01e1"), -49508));
    public final BooleanSetting tracers = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6\u00bf"), Deobf.decrypt("7^)\u00192\u00a8\u00a5\u0084\u00ac\u0108\u0149\u0105\u0131\u019f\u01fe\u0194\u01ea\u0213\u0214\u0215\u023f\u028b\u029a\u02d1\u02a8\u0316\u0318\u0315\u0353\u0390\u03e1\u0399\u03f9\u03d8\u044e\u042d\u0407\u042d\u04d9\u04bb\u04e6\u04f8\u0540\u0576"), false));

    public PlayerEspModule() {
        super(Deobf.decrypt("#@)\u0017w\u00b6\u0089\u00b9\u0099"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u0108\u0149\u0113\u012f\u0191\u01ea\u01d1\u01ec\u0208\u0251\u0241\u0234\u028b\u029a\u02d7\u02bc\u0316\u0359\u030b\u0340\u03dc\u03f9\u0385"), Category.RENDER);
    }
}

