/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.SliderSetting;

public class FullbrightModule
extends Module {
    public final SliderSetting gamma = this.addSetting(new SliderSetting(Deobf.decrypt("4M%\u0003s"), Deobf.decrypt("1^!\tz\u00b0\u00a2\u008f\u00ba\u0108\u0149\u0101\u012c\u019f\u01e0\u01c0"), 12.0, 1.0, 15.0, 1.0));

    public FullbrightModule() {
        super(Deobf.decrypt("5Y$\u0002P\u00b6\u00a5\u008d\u00a1\u010f"), Deobf.decrypt(">M0\u0007\u007f\u00b1\u00a1\u00ca\u00ab\u0109\u0100\u0104\u012b\u0184\u01fd\u01d1\u01ed\u0208\u0251\u0250\u022a\u029c\u0287\u02db\u02ac\u0316\u031c\u030e\u0344"), Category.RENDER);
    }
}

