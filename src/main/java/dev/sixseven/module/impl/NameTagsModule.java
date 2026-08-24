/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module.impl;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.SliderSetting;

public class NameTagsModule
extends Module {
    public final BooleanSetting players = this.addSetting(new BooleanSetting(Deobf.decrypt("#@)\u0017w\u00b6\u00bf"), Deobf.decrypt("'M/Nb\u00a8\u00ad\u0093\u00ac\u0109\u011a\u0143\u0134\u0199\u01e7\u01dc\u01be\u020f\u0219\u0250\u0235\u028b\u02d5\u02eb\u029c\u0330"), true));
    public final BooleanSetting self = this.addSetting(new BooleanSetting(Deobf.decrypt(" I$\b"), Deobf.decrypt("2@;\u00012\u00b0\u00ad\u008d\u00e9\u0102\u0106\u0116\u0131\u01d0\u01fc\u01c3\u01f0\u025b\u0201\u0259\u023d\u0280\u0290\u02d0\u02fb\u0356\u031b\u0319\u0352\u03c4\u03b5\u039f\u03b7\u039d\u041c\u043c\u040b\u042d\u04d9\u04b2\u04f5\u04f2\u054a\u056a\u0511"), false));
    public final BooleanSetting items = this.addSetting(new BooleanSetting(Deobf.decrypt(":X-\u0003a"), Deobf.decrypt("'M/Nv\u00b6\u00a3\u009a\u00b9\u011e\u010d\u0143\u012a\u0184\u01f6\u01d9\u01ed\u025b\u021e\u025b\u027c\u028d\u029d\u02c7\u02fb\u0319\u030b\u0313\u0354\u03de\u03f1"), true));
    public final BooleanSetting hidePlayerTags = this.addSetting(new BooleanSetting(Deobf.decrypt(";E,\u000b2\u0094\u00a0\u008b\u00b0\u011e\u011b\u0143\u0117\u0191\u01f4\u01c7"), Deobf.decrypt("!I%\u0001d\u00a1\u00ec\u009e\u00a1\u011e\u0149\u0115\u0122\u019e\u01fa\u01d8\u01f2\u021a\u0251\u025b\u023d\u0294\u0290\u02d6\u02ba\u0319\u0359\u0313\u0347\u0390\u03fa\u0382\u03b1\u03d8\u045d\u046e\u041f\u0461\u04c8\u04ae\u04e2\u04f3\u0556\u0524\u252c\u050c\u05cb\u05e8\u05df\u05b7\u064b\u0637\u0650\u0651\u06d2\u06f4\u06d5\u06dc\u0718\u077a\u0758\u071b\u07c8\u07c4\u0780\u0780\u07a6\u0853\u080d"), true));
    public final BooleanSetting hideOwnTag = this.addSetting(new BooleanSetting(Deobf.decrypt(";E,\u000b2\u008b\u00bb\u0084\u00e9\u012f\u0108\u0104"), Deobf.decrypt("!I%\u0001d\u00a1\u00ec\u0093\u00a6\u010e\u011b\u0143\u012c\u0187\u01fd\u0194\u01f1\u0209\u0218\u0252\u0235\u0297\u0294\u02ce\u02fb\u0310\u0318\u0311\u0344\u03c4\u03f4\u0391\u03f9\u23a9\u040f\u0421\u0401\u0461\u04d0\u04f7\u04f3\u04e9\u0540\u0524\u0555\u0543\u05c0\u05f3\u05df\u05ab\u064c\u0630\u0618\u0667\u0697\u06f5\u06dc\u0698\u0719\u0777\u075a\u071c\u07c8\u078c\u079c\u079f\u07ba"), true));
    public final BooleanSetting hideOtherTags = this.addSetting(new BooleanSetting(Deobf.decrypt(";E,\u000b2\u008b\u00b8\u0082\u00ac\u0109\u0149\u0137\u0122\u0197\u01e0"), Deobf.decrypt("2@;\u00012\u00b6\u00a9\u0087\u00a6\u010d\u010c\u0143\u0135\u0191\u01fd\u01dd\u01f2\u0217\u0210\u0215\u0232\u0298\u0298\u02c7\u02af\u031f\u031e\u030f\u0301\u03df\u03f3\u03d6\u03b4\u03d2\u044d\u043d\u0443\u042d\u04c8\u04a5\u04ea\u04ee\u0557\u0524\u054b\u0558\u05c5\u05e8\u05d7\u05bd\u064b\u0665\u0618\u0652\u0680\u06f8\u06d7\u06dd\u071e"), false));
    public final BooleanSetting armor = this.addSetting(new BooleanSetting(Deobf.decrypt("2^%\u0001`"), Deobf.decrypt(" D'\u00192\u00a5\u00ec\u009a\u00a5\u011a\u0110\u0106\u0131\u01d7\u01e0\u0194\u01fb\u020a\u0204\u025c\u022c\u0289\u0290\u02c6\u02fb\u031f\u030b\u0311\u034e\u03c2\u03b5\u0397\u03bb\u03d2\u0459\u042b\u044f\u0479\u04c1\u04b2\u04ee\u04f3\u0505\u0570\u0559\u054b"), true));
    public final BooleanSetting heldItem = this.addSetting(new BooleanSetting(Deobf.decrypt(";I$\n2\u008d\u00b8\u008f\u00a4"), Deobf.decrypt(" D'\u00192\u00b3\u00a4\u008b\u00bd\u015b\u0108\u0143\u0133\u019c\u01f2\u01cd\u01fb\u0209\u0251\u025c\u022f\u02d9\u029d\u02cd\u02b7\u031a\u0310\u0312\u0346\u0390\u03f4\u0394\u03b6\u03cb\u044a\u046e\u041b\u0465\u04cc\u04be\u04f5\u04a1\u0551\u0565\u055f"), true));
    public final BooleanSetting health = this.addSetting(new BooleanSetting(Deobf.decrypt(";I)\u0002f\u00ac"), Deobf.decrypt(";I)\u0002f\u00ac\u00ec\u0088\u00a8\u0109\u0149\u0116\u012d\u0194\u01f6\u01c6\u01be\u020b\u021d\u0254\u0225\u029c\u0287\u0282\u02af\u031f\u031e\u030f"), true));
    public final BooleanSetting distance = this.addSetting(new BooleanSetting(Deobf.decrypt("7E;\u001as\u00aa\u00af\u008f"), Deobf.decrypt("2\\8\u000b|\u00a0\u00ec\u009e\u00a1\u011e\u0149\u0107\u012a\u0183\u01e7\u01d5\u01f0\u0218\u0214\u0215\u0235\u0297\u02d5\u02c0\u02b7\u0311\u031a\u0317\u0352"), true));
    public final BooleanSetting itemAmount = this.addSetting(new BooleanSetting(Deobf.decrypt(":X-\u00032\u0085\u00a1\u0085\u00bc\u0115\u011d"), Deobf.decrypt(" D'\u00192\u00b0\u00a4\u008f\u00e9\u0108\u011d\u0102\u0120\u019b\u01b3\u01c7\u01f7\u0201\u0214\u0215\u0233\u0297\u02d5\u02cb\u02af\u031b\u0314\u035c\u0355\u03d1\u03f2\u0385"), true));
    public final SliderSetting scale = this.addSetting(new SliderSetting(Deobf.decrypt(" O)\u0002w"), Deobf.decrypt("'M/Na\u00ad\u00b6\u008f"), 1.0, 0.5, 2.0, 0.1, Deobf.decrypt("\u000b")));
    public final SliderSetting opacity = this.addSetting(new SliderSetting(Deobf.decrypt("<\\)\r{\u00b0\u00b5"), Deobf.decrypt("'M/Nf\u00b6\u00ad\u0084\u00ba\u010b\u0108\u0111\u0126\u019e\u01f0\u01cd"), 100.0, 10.0, 100.0, 5.0, Deobf.decrypt("V")));
    public final SliderSetting range = this.addSetting(new SliderSetting(Deobf.decrypt("!M&\tw"), Deobf.decrypt("<B$\u00172\u00b0\u00ad\u008d\u00e9\u010c\u0100\u0117\u012b\u0199\u01fd\u0194\u01ea\u0213\u0218\u0246\u027c\u029d\u029c\u02d1\u02af\u031f\u0317\u031f\u0344"), 64.0, 8.0, 256.0, 4.0, Deobf.decrypt("\u001e")));

    public NameTagsModule() {
        super(Deobf.decrypt("=M%\u000bF\u00a5\u00ab\u0099"), Deobf.decrypt(";y\fCa\u00b0\u00b5\u0086\u00ac\u011f\u0149\u010d\u0122\u019d\u01f6\u01c0\u01ff\u021c\u0202\u0215\u023a\u0296\u0287\u0282\u02ab\u0312\u0318\u0305\u0344\u03c2\u03e6\u03d6\u03ff\u039d\u0446\u043a\u040a\u0460\u04da"), Category.MISC);
    }
}

