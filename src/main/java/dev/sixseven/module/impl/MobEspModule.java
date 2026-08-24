/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;

public class MobEspModule
extends Module {
    public final ColorSetting hostile = this.addSetting(new ColorSetting(Deobf.decrypt(";C;\u001a{\u00a8\u00a9"), Deobf.decrypt(";C;\u001a{\u00a8\u00a9\u00ca\u00aa\u0114\u0105\u010c\u0131"), -45715));
    public final ColorSetting passive = this.addSetting(new ColorSetting(Deobf.decrypt("#M;\u001d{\u00b2\u00a9"), Deobf.decrypt("#M;\u001d{\u00b2\u00a9\u00ca\u00aa\u0114\u0105\u010c\u0131"), -12654960));
    public final BooleanSetting passiveToo = this.addSetting(new BooleanSetting(Deobf.decrypt("#M;\u001d{\u00b2\u00a9\u00ca\u009d\u0114\u0106"), Deobf.decrypt(":B+\u0002g\u00a0\u00a9\u00ca\u00b9\u011a\u011a\u0110\u012a\u0186\u01f6\u0194\u01f3\u0214\u0213\u0246"), false));
    public final BooleanSetting tracers = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6\u00bf"), Deobf.decrypt("7^)\u00192\u00a8\u00a5\u0084\u00ac\u0108\u0149\u0105\u0131\u019f\u01fe\u0194\u01ea\u0213\u0214\u0215\u023f\u028b\u029a\u02d1\u02a8\u0316\u0318\u0315\u0353\u0390\u03e1\u0399\u03f9\u03d8\u044e\u042d\u0407\u042d\u04c4\u04b8\u04e5"), false));

    public MobEspModule() {
        super(Deobf.decrypt(">C*+A\u0094"), Deobf.decrypt(";E/\u0006~\u00ad\u00ab\u0082\u00bd\u0108\u0149\u010b\u012c\u0183\u01e7\u01dd\u01f2\u021e\u0251\u0258\u0233\u029b\u0286"), Category.RENDER);
    }
}

