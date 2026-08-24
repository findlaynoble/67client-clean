/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1792
 *  net.minecraft.class_1802
 *  net.minecraft.class_310
 */
package dev.sixseven.module.impl;

import dev.sixseven.mixin.MinecraftAccessor;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.ModeSetting;
import net.minecraft.class_1792;
import net.minecraft.class_1802;
import net.minecraft.class_310;

public class FastUseModule
extends Module {
    public final ModeSetting items = this.addSetting(new ModeSetting(Deobf.decrypt(":X-\u0003a"), Deobf.decrypt("$D)\u001a2\u00b0\u00a3\u00ca\u00ba\u010b\u010c\u0106\u0127\u01d0\u01e6\u01c4"), Deobf.decrypt("2@$"), Deobf.decrypt("2@$"), Deobf.decrypt("#I)\u001c~\u00b7"), Deobf.decrypt("+|h,}\u00b0\u00b8\u0086\u00ac\u0108")));

    public FastUseModule() {
        super(Deobf.decrypt("5M;\u001aG\u00b7\u00a9"), Deobf.decrypt("!I%\u0001d\u00a1\u00bf\u00ca\u00a0\u010f\u010c\u010e\u0163\u0185\u01e0\u01d1\u01be\u0218\u021e\u025a\u0230\u029d\u029a\u02d5\u02b5\u030d"), Category.MISC);
    }

    @Override
    public void onTick() {
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null) {
            return;
        }
        if (!this.appliesTo(mc)) {
            return;
        }
        ((MinecraftAccessor)mc).sixsevenclient$setRightClickDelay(0);
    }

    private boolean appliesTo(class_310 mc) {
        if (this.items.is(Deobf.decrypt("2@$"))) {
            return true;
        }
        class_1792 target = this.items.is(Deobf.decrypt("#I)\u001c~\u00b7")) ? class_1802.field_8634 : class_1802.field_8287;
        return mc.field_1724.method_6047().method_31574(target) || mc.field_1724.method_6079().method_31574(target);
    }
}

