/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.render.HoleEspRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;

public class DebugHoleEspModule
extends Module {
    public final ModeSetting depth = this.addSetting(new ModeSetting(Deobf.decrypt("7I8\u001az"), Deobf.decrypt(";C$\u000b2\u00a0\u00a9\u009a\u00bd\u0113\u0149\u0100\u012b\u0195\u01f0\u01df"), Deobf.decrypt("A"), Deobf.decrypt("A"), Deobf.decrypt("@"), Deobf.decrypt("2B1")));
    public final ColorSetting safe = this.addSetting(new ColorSetting(Deobf.decrypt(" M.\u000b"), Deobf.decrypt(" M.\u000b2\u00ac\u00a3\u0086\u00ac\u015b\u010a\u010c\u012f\u019f\u01e1"), -12654960));
    public final ColorSetting unsafe = this.addSetting(new ColorSetting(Deobf.decrypt("&B;\u000ft\u00a1"), Deobf.decrypt("&B;\u000ft\u00a1\u00ec\u0082\u00a6\u0117\u010c\u0143\u0120\u019f\u01ff\u01db\u01ec"), -45715));
    public final BooleanSetting tracers = this.addSetting(new BooleanSetting(Deobf.decrypt("'^)\rw\u00b6\u00bf"), Deobf.decrypt("7^)\u00192\u00a8\u00a5\u0084\u00ac\u0108\u0149\u0105\u0131\u019f\u01fe\u0194\u01ea\u0213\u0214\u0215\u023f\u028b\u029a\u02d1\u02a8\u0316\u0318\u0315\u0353\u0390\u03e1\u0399\u03f9\u03d8\u044e\u042d\u0407\u042d\u04c1\u04b8\u04eb\u04e4"), false));

    public DebugHoleEspModule() {
        super(Deobf.decrypt("7I*\u001bu\u008c\u00a3\u0086\u00ac\u013e\u013a\u0133"), Deobf.decrypt(">M:\u0005a\u00e4\u00bf\u008b\u00af\u011e\u0149\u0100\u0131\u0189\u01e0\u01c0\u01ff\u0217\u025c\u0245\u022a\u0289\u02d5\u02ca\u02b4\u0312\u031c\u030f"), Category.RENDER);
    }

    @Override
    public void onTick() {
        HoleEspRenderer.scan(this);
    }

    @Override
    protected void onDisable() {
        HoleEspRenderer.clear();
    }
}

