/*
 * Decompiled with CFR 0.152.
 */
package dev.sixseven.module;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.render.SusChunkRenderer;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ColorSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.Setting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.suschunk.SusChunkScanner;

public final class Modules {
    private Modules() {
    }

    public static class SpotifyModule
    extends Module {
        public final ModeSetting source = this.addSetting(new ModeSetting(Deobf.decrypt(" C=\u001cq\u00a1"), Deobf.decrypt("2Y<\u00012\u00b1\u00bf\u008f\u00ba\u015b\u011d\u010b\u0126\u01d0\u01e1\u01d1\u01ff\u0217\u0251\u0266\u022c\u0296\u0281\u02cb\u02bd\u0307\u0359\u030f\u0344\u03c3\u03e6\u039f\u03b6\u03d3\u040f\u0466\u0438\u0464\u04c7\u04b3\u04e8\u04f6\u0556\u052d\u0503\u050c\u05e0\u05e3\u05de\u05a1\u064b\u0630\u0650\u065b\u0685\u06ea\u069a\u06cb\u070c\u077b\u074d\u0750\u07de\u07c4\u0797\u0789\u07bd\u0845"), Deobf.decrypt("2Y<\u0001"), Deobf.decrypt("2Y<\u0001"), Deobf.decrypt("7I%\u0001")));
        public final BooleanSetting controls = this.addSetting(new BooleanSetting(Deobf.decrypt("0C&\u001a`\u00ab\u00a0\u0099"), Deobf.decrypt(" D'\u00192\u00b4\u00be\u008f\u00bf\u015b\u0146\u0143\u0133\u019c\u01f2\u01cd\u01be\u0254\u0251\u025b\u0239\u0281\u0281\u0282\u02b9\u030b\u030d\u0308\u034e\u03de\u03e6"), true));
        public final BooleanSetting volume = this.addSetting(new BooleanSetting(Deobf.decrypt("%C$\u001b\u007f\u00a1"), Deobf.decrypt(" D'\u00192\u00a5\u00ec\u0099\u00a5\u0112\u010d\u0106\u0131\u01d0\u01f5\u01db\u01ec\u025b\u0222\u0245\u0233\u028d\u029c\u02c4\u02a2\u0359\u030a\u035c\u0340\u03c0\u03e5\u03d6\u03af\u03d2\u0443\u043b\u0402\u0468\u0489\u04ff\u04d0\u04e8\u054b\u0560\u0557\u055b\u05d7\u05af"), true));
        public final BooleanSetting hideWhenIdle = this.addSetting(new BooleanSetting(Deobf.decrypt(";E,\u000b2\u0093\u00a4\u008f\u00a7\u015b\u0120\u0107\u012f\u0195"), Deobf.decrypt(";E,\u000b2\u00b0\u00a4\u008f\u00e9\u0118\u0108\u0111\u0127\u01d0\u01e4\u01dc\u01fb\u0215\u0251\u025b\u0233\u028d\u029d\u02cb\u02b5\u0319\u0359\u030c\u034d\u03d1\u03ec\u0385"), true));

        public SpotifyModule() {
            super(Deobf.decrypt(" \\'\u001a{\u00a2\u00b5\u00a2\u009c\u013f"), Deobf.decrypt("=C?Nb\u00a8\u00ad\u0093\u00a0\u0115\u010e\u0143\u2157\u01d0\u01e0\u01df\u01f7\u020b\u0251\u0254\u0232\u029d\u02d5\u02d1\u02be\u031b\u0312\u035c\u0347\u03c2\u03fa\u039b\u03f9\u03c9\u0447\u042b\u044f\u0445\u04fc\u0493"), Category.CLIENT);
            this.setEnabled(true);
        }
    }

    public static class HudModule
    extends Module {
        public final BooleanSetting watermark = this.addSetting(new BooleanSetting(Deobf.decrypt("$M<\u000b`\u00a9\u00ad\u0098\u00a2"), Deobf.decrypt("'D-N$\u00f3\u00ec\u0088\u00a8\u011f\u010e\u0106"), true));
        public final BooleanSetting arrayList = this.addSetting(new BooleanSetting(Deobf.decrypt("2^:\u000fk\u0088\u00a5\u0099\u00bd"), Deobf.decrypt("6B)\f~\u00a1\u00a8\u00ca\u00a4\u0114\u010d\u0116\u012f\u0195\u01e0\u0194\u01f2\u0212\u0202\u0241"), true));
        public final BooleanSetting fps = this.addSetting(new BooleanSetting(Deobf.decrypt("5|\u001b"), Deobf.decrypt("5^)\u0003w\u00b6\u00ad\u009e\u00ac\u015b\u011b\u0106\u0122\u0194\u01fc\u01c1\u01ea"), true));
        public final BooleanSetting ping = this.addSetting(new BooleanSetting(Deobf.decrypt("#E&\t"), Deobf.decrypt("?M<\u000b|\u00a7\u00b5\u00ca\u00bb\u011e\u0108\u0107\u012c\u0185\u01e7"), false));
        public final BooleanSetting coordinates = this.addSetting(new BooleanSetting(Deobf.decrypt("0C'\u001cv\u00ad\u00a2\u008b\u00bd\u011e\u011a"), Deobf.decrypt("1@'\ry\u00e4\u00bc\u0085\u00ba\u0112\u011d\u010a\u012c\u019e\u01b3\u01c6\u01fb\u021a\u0215\u025a\u0229\u028d"), true));
        public final BooleanSetting direction = this.addSetting(new BooleanSetting(Deobf.decrypt("7E:\u000bq\u00b0\u00a5\u0085\u00a7"), Deobf.decrypt("5M+\u0007|\u00a3\u00ec\u0098\u00ac\u011a\u010d\u010c\u0136\u0184"), true));
        public final BooleanSetting tps = this.addSetting(new BooleanSetting(Deobf.decrypt("'|\u001b"), Deobf.decrypt(" I:\u0018w\u00b6\u00ec\u009e\u00a0\u0118\u0102\u0143\u0131\u0191\u01e7\u01d1\u01be\u021e\u0202\u0241\u0235\u0294\u0294\u02d6\u02be"), false));
        public final BooleanSetting cps = this.addSetting(new BooleanSetting(Deobf.decrypt("0|\u001b"), Deobf.decrypt("0@!\ry\u00b7\u00ec\u009a\u00ac\u0109\u0149\u0110\u0126\u0193\u01fc\u01da\u01fa"), false));
        public final BooleanSetting armor = this.addSetting(new BooleanSetting(Deobf.decrypt("2^%\u0001`"), Deobf.decrypt("6]=\u0007b\u00b4\u00a9\u008e\u00e9\u011a\u011b\u010e\u012c\u0182\u01b3\u019f\u01be\u021f\u0204\u0247\u023d\u029b\u029c\u02ce\u02b2\u030a\u0300"), false));
        public final BooleanSetting potions = this.addSetting(new BooleanSetting(Deobf.decrypt("#C<\u0007}\u00aa\u00bf"), Deobf.decrypt("2O<\u0007d\u00a1\u00ec\u008f\u00af\u011d\u010c\u0100\u0137\u0183\u01b3\u01c3\u01f7\u020f\u0219\u0215\u0228\u0290\u0298\u02c7\u02a9\u030d"), false));
        public final BooleanSetting keystrokes = this.addSetting(new BooleanSetting(Deobf.decrypt("8I1\u001df\u00b6\u00a3\u0081\u00ac\u0108"), Deobf.decrypt("$m\u001b*2\u00ef\u00ec\u0087\u00a6\u010e\u011a\u0106\u0163\u01db\u01b3\u01c7\u01ee\u021a\u0212\u0250\u027c\u029d\u029c\u02d1\u02ab\u0312\u0318\u0305"), false));
        public final BooleanSetting radar = this.addSetting(new BooleanSetting(Deobf.decrypt("!M,\u000f`"), Deobf.decrypt("0E:\rg\u00a8\u00ad\u0098\u00e9\u010b\u0105\u0102\u013a\u0195\u01e1\u0194\u01ec\u021a\u0215\u0254\u022e"), true));
        public final BooleanSetting themeSync = this.addSetting(new BooleanSetting(Deobf.decrypt("?E;\u001a2\u0090\u00a4\u008f\u00a4\u011e\u0149\u0130\u013a\u019e\u01f0"), Deobf.decrypt("2^:\u000fk\u0088\u00a5\u0099\u00bd\u015b\u010f\u010c\u012f\u019c\u01fc\u01c3\u01ed\u025b\u0205\u025d\u0239\u02d9\u0281\u02ca\u02be\u0313\u031c\u035c\u0342\u03df\u03f9\u0399\u03ab"), true));
        public final ColorSetting listColor = this.addSetting(new ColorSetting(Deobf.decrypt("?E;\u001a2\u0087\u00a3\u0086\u00a6\u0109"), Deobf.decrypt("2^:\u000fk\u0088\u00a5\u0099\u00bd\u015b\u010a\u010c\u012f\u019f\u01e1\u0194\u01e9\u0213\u0214\u025b\u027c\u02ad\u029d\u02c7\u02b6\u031b\u0359\u032f\u0358\u03de\u03f6\u03d6\u03b0\u03ce\u040f\u0421\u0409\u046b"), -49508));
        public final SliderSetting radarRange = this.addSetting(new SliderSetting(Deobf.decrypt("!M,\u000f`\u00e4\u009e\u008b\u00a7\u011c\u010c"), Deobf.decrypt(" O)\u00002\u00b6\u00ad\u008e\u00a0\u010e\u011a\u0143\u012a\u019e\u01b3\u01d6\u01f2\u0214\u0212\u025e\u022f"), 48.0, 16.0, 128.0, 4.0, Deobf.decrypt("\u001e")));
        public final BooleanSetting radarHeads = this.addSetting(new BooleanSetting(Deobf.decrypt("!M,\u000f`\u00e4\u0084\u008f\u00a8\u011f\u011a"), Deobf.decrypt(" G!\u00002\u00a2\u00ad\u0089\u00ac\u0108\u0149\u010a\u012d\u0183\u01e7\u01d1\u01ff\u021f\u0251\u025a\u023a\u02d9\u0291\u02cd\u02af\u030d"), true));
        public final BooleanSetting notifications = this.addSetting(new BooleanSetting(Deobf.decrypt("=C<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d\u0130"), Deobf.decrypt("'D-\u0003w\u00a0\u00ec\u009e\u00a6\u011a\u011a\u0117\u0130\u01d0\u01e4\u01dc\u01fb\u0215\u0251\u0258\u0233\u029d\u0280\u02ce\u02be\u030d\u0359\u0308\u034e\u03d7\u03f2\u039a\u03bc\u039d\u0409\u046e\u041b\u0465\u04cc\u04f7\u04f0\u04e4\u0544\u0570\u0550\u0549\u05d6\u05a6\u05d0\u05a6\u060a\u062d\u065f\u0651\u0681"), true));
        public final SliderSetting notifyDuration = this.addSetting(new SliderSetting(Deobf.decrypt("=C<\u0007t\u00bd\u00ec\u00ae\u00bc\u0109\u0108\u0117\u012a\u019f\u01fd"), Deobf.decrypt(";C?N~\u00ab\u00a2\u008d\u00e9\u011a\u0149\u0117\u012c\u0191\u01e0\u01c0\u01be\u0217\u0218\u025b\u023b\u029c\u0287\u02d1\u02fb\u031c\u031c\u031a\u034e\u03c2\u03f0\u03d6\u03bf\u03dc\u044b\u0427\u0401\u046a\u0489\u04b8\u04f2\u04f5"), 2.5, 1.0, 6.0, 0.5, Deobf.decrypt("\u0000")));

        public HudModule() {
            super(Deobf.decrypt(";y\f"), Deobf.decrypt("2@$NZ\u0091\u0088\u00ca\u00ac\u0117\u010c\u010e\u0126\u019e\u01e7\u01c7\u01be\u226f\u0251\u0258\u0233\u028f\u0290\u0282\u02fd\u035e\u030b\u0319\u0352\u03d9\u03ef\u0393\u03f9\u03cb\u0446\u042f\u044f\u046e\u04c1\u04b6\u04f3\u04a1\u050d\u0550\u0511"), Category.CLIENT);
            this.setEnabled(true);
            this.notifyDuration.visibleWhen(this.notifications::get);
        }
    }

    public static class BlockOutlineModule
    extends Module {
        public final ColorSetting color = this.addSetting(new ColorSetting(Deobf.decrypt("0C$\u0001`"), Deobf.decrypt("<Y<\u0002{\u00aa\u00a9\u00ca\u00aa\u0114\u0105\u010c\u0131"), -49508));
        public final BooleanSetting rainbow = this.addSetting(new BooleanSetting(Deobf.decrypt("!M!\u0000p\u00ab\u00bb"), Deobf.decrypt("0U+\u0002w\u00e4\u00b8\u0082\u00ac\u015b\u0106\u0116\u0137\u019c\u01fa\u01da\u01fb\u025b\u0205\u025d\u022e\u0296\u0280\u02c5\u02b3\u035e\u030d\u0314\u0344\u0390\u03e7\u0397\u03b0\u03d3\u044d\u0421\u0418"), false));
        public final SliderSetting glow = this.addSetting(new SliderSetting(Deobf.decrypt("4@'\u0019"), Deobf.decrypt("<Y<\u000b`\u00e4\u00ab\u0086\u00a6\u010c\u0149\u010a\u012d\u0184\u01f6\u01da\u01ed\u0212\u0205\u024c"), 60.0, 0.0, 100.0, 5.0, Deobf.decrypt("V")));
        public final SliderSetting thickness = this.addSetting(new SliderSetting(Deobf.decrypt("'D!\ry\u00aa\u00a9\u0099\u00ba"), Deobf.decrypt("0C:\u000b2\u00a8\u00a5\u0084\u00ac\u015b\u011e\u010a\u0127\u0184\u01fb"), 2.5, 1.0, 6.0, 0.5, Deobf.decrypt("\u0003T")));
        public final SliderSetting fillOpacity = this.addSetting(new SliderSetting(Deobf.decrypt("5E$\u0002"), Deobf.decrypt("'^)\u0000a\u00b4\u00ad\u0098\u00ac\u0115\u011d\u0143\u0125\u0199\u01ff\u01d8\u01be\u0212\u021f\u0246\u0235\u029d\u0290\u0282\u02af\u0316\u031c\u035c\u0343\u03dc\u03fa\u0395\u03b2"), 12.0, 0.0, 60.0, 2.0, Deobf.decrypt("V")));
        public final ModeSetting animation = this.addSetting(new ModeSetting(Deobf.decrypt("2B!\u0003s\u00b0\u00a5\u0085\u00a7"), Deobf.decrypt("<Y<\u0002{\u00aa\u00a9\u00ca\u00a8\u0115\u0100\u010e\u0122\u0184\u01fa\u01db\u01f0\u025b\u0202\u0241\u0225\u0295\u0290"), Deobf.decrypt("#Y$\u001dw"), Deobf.decrypt("#Y$\u001dw"), Deobf.decrypt("4^)\n{\u00a1\u00a2\u009e\u00e9\u013d\u0105\u010c\u0134"), Deobf.decrypt(" X)\u001a{\u00a7")));

        public BlockOutlineModule() {
            super(Deobf.decrypt("0Y;\u001a}\u00a9\u008e\u0086\u00a6\u0118\u0102\u012c\u0136\u0184\u01ff\u01dd\u01f0\u021e"), Deobf.decrypt("4@'\u0019{\u00aa\u00ab\u00ca\u00a8\u0115\u0100\u010e\u0122\u0184\u01f6\u01d0\u01be\u0214\u0204\u0241\u0230\u0290\u029b\u02c7\u02fb\u0311\u0317\u035c\u0355\u03d8\u03f0\u03d6\u03ad\u03dc\u045d\u0429\u040a\u0479\u04cc\u04b3\u04a7\u04e3\u0549\u056b\u055b\u0547"), Category.VISUALS);
            this.setEnabled(true);
        }
    }

    public static class SusChunkFinderModule
    extends Module {
        public final SliderSetting sensitivity = this.addSetting(new SliderSetting(Deobf.decrypt(" I&\u001d{\u00b0\u00a5\u009c\u00a0\u010f\u0110"), Deobf.decrypt(";E/\u0006w\u00b6\u00ec\u00d7\u00e9\u0108\u011d\u0111\u012a\u0193\u01e7\u01d1\u01ec\u0241\u0251\u0258\u0233\u028b\u0290\u0282\u02ac\u031b\u0310\u031b\u0349\u03c4\u03f0\u0392\u03f9\u03d8\u0459\u0427\u040b\u0468\u04c7\u04b4\u04e2\u04a1\u0547\u0561\u055e\u0543\u05d6\u05e3\u0593\u05af\u064b\u0620\u0650\u0641\u069c\u06f2\u069a\u06de\u0701\u0777\u075a\u074f"), 3.0, 1.0, 10.0, 1.0).withLabel(v -> (int)v + " (" + (int)v * 5 + ")"));
        public final BooleanSetting amethyst = this.addSetting(new BooleanSetting(Deobf.decrypt("2A-\u001az\u00bd\u00bf\u009e"), Deobf.decrypt(";E,\nw\u00aa\u00ec\u008d\u00bb\u0114\u011e\u010d\u0163\u0193\u01ff\u01c1\u01ed\u020f\u0214\u0247\u022f\u02d9\u0283\u02cb\u02ba\u035e\u030a\u0319\u0353\u03c6\u03f0\u0384\u03f9\u03df\u0443\u0421\u040c\u0466\u0489\u04bb\u04ee\u04e6\u054d\u0570\u0518\u0519\u0584\u2592\u0593\u05ba\u0603\u0626\u0618\u0647\u0686\u06eb\u06d5\u06d6\u070a\u0773\u074e\u0748\u079b\u0797\u079a\u078f\u07a7\u0845\u0812"), true));
        public final BooleanSetting kelp = this.addSetting(new BooleanSetting(Deobf.decrypt("8I$\u001e"), Deobf.decrypt("5Y$\u0002k\u00e4\u00ab\u0098\u00a6\u010c\u0107\u0143\u016c\u01d0\u01e6\u01da\u01eb\u0208\u0204\u0254\u0230\u0295\u028c\u0282\u02af\u031f\u0315\u0310\u0301\u03db\u03f0\u039a\u03a9\u039d\u044c\u0421\u0403\u0478\u04c4\u04b9\u04f4"), true));
        public final BooleanSetting bamboo = this.addSetting(new BooleanSetting(Deobf.decrypt("1M%\f}\u00ab"), Deobf.decrypt("5Y$\u0002k\u00e4\u00ab\u0098\u00a6\u010c\u0107\u014f\u0163\u019d\u01f2\u01cc\u01b3\u0213\u0214\u025c\u023b\u0291\u0281\u0282\u02b9\u031f\u0314\u031e\u034e\u03df"), true));
        public final BooleanSetting berries = this.addSetting(new BooleanSetting(Deobf.decrypt("1I:\u001c{\u00a1\u00bf"), Deobf.decrypt(" [-\u000bf\u00e4\u00ae\u008f\u00bb\u0109\u0110\u0143\u0121\u0185\u01e0\u01dc\u01fb\u0208\u0251\u0254\u0228\u02d9\u0298\u02c3\u02a3\u035e\u031e\u030e\u034e\u03c7\u03e1\u039e\u03f9\u03ce\u045b\u042f\u0408\u0468"), true));
        public final BooleanSetting vines = this.addSetting(new BooleanSetting(Deobf.decrypt("%E&\u000ba"), Deobf.decrypt("%E&\u000ba\u00e4\u00ab\u0098\u00a6\u010c\u0107\u0143\u0125\u0191\u01e1\u0194\u01fa\u0214\u0206\u025b\u027c\u029f\u0287\u02cd\u02b6\u035e\u030d\u0314\u0344\u03d9\u03e7\u03d6\u03aa\u03c8\u045f\u043e\u0400\u047f\u04dd"), true));
        public final BooleanSetting dripstone = this.addSetting(new BooleanSetting(Deobf.decrypt("7^!\u001ea\u00b0\u00a3\u0084\u00ac"), Deobf.decrypt("7^!\u001ea\u00b0\u00a3\u0084\u00ac\u015b\u011a\u0113\u012a\u019b\u01f6\u01c7\u01be\u0217\u021e\u025b\u023b\u029c\u0287\u0282\u02af\u0316\u0318\u0312\u0301\u03de\u03f4\u0382\u03ac\u03cf\u044e\u0422\u044f\u046a\u04cc\u04b9\u04e2\u04f3\u0544\u0570\u0551\u0543\u05ca"), true));
        public final SliderSetting scanSpeed = this.addSetting(new SliderSetting(Deobf.decrypt(" O)\u00002\u0097\u00bc\u008f\u00ac\u011f"), Deobf.decrypt("0D=\u0000y\u00b7\u00ec\u0099\u00aa\u011a\u0107\u010d\u0126\u0194\u01b3\u01c4\u01fb\u0209\u0251\u0241\u0235\u029a\u029e"), 10.0, 2.0, 24.0, 1.0));
        public final SliderSetting renderY = this.addSetting(new SliderSetting(Deobf.decrypt("!I&\nw\u00b6\u00ec\u00b3"), Deobf.decrypt(";I!\tz\u00b0\u00ec\u009e\u00a1\u011e\u0149\u0105\u012f\u0191\u01e7\u0194\u01fd\u0213\u0204\u025b\u0237\u02d9\u029d\u02cb\u02bc\u0316\u0315\u0315\u0346\u03d8\u03e1\u0385\u03f9\u03cf\u044a\u0420\u040b\u0468\u04db\u04f7\u04e6\u04f5"), 100.0, -64.0, 320.0, 1.0));
        public final SliderSetting fillOpacity = this.addSetting(new SliderSetting(Deobf.decrypt("5E$\u00022\u008b\u00bc\u008b\u00aa\u0112\u011d\u011a"), Deobf.decrypt("5E$\u00022\u00ab\u00bc\u008b\u00aa\u0112\u011d\u011a\u0163\u019f\u01f5\u0194\u01ea\u0213\u0214\u0215\u023f\u0291\u0280\u02cc\u02b0\u035e\u0308\u0309\u0340\u03d4\u03e6"), 90.0, 0.0, 255.0, 1.0));
        public final BooleanSetting outline = this.addSetting(new BooleanSetting(Deobf.decrypt("<Y<\u0002{\u00aa\u00a9"), Deobf.decrypt("0^!\u001db\u00e4\u00ae\u0085\u00bb\u011f\u010c\u0111\u0163\u0191\u01ff\u01db\u01f0\u021c\u0251\u0241\u0234\u029c\u02d5\u02c1\u02b3\u030b\u0317\u0317\u0301\u03d5\u03f1\u0391\u03bc\u03ce"), true));
        public final SliderSetting outlineOpacity = this.addSetting(new SliderSetting(Deobf.decrypt("<Y<\u0002{\u00aa\u00a9\u00ca\u0086\u010b\u0108\u0100\u012a\u0184\u01ea"), Deobf.decrypt("1C:\nw\u00b6\u00ec\u0085\u00b9\u011a\u010a\u010a\u0137\u0189"), 200.0, 0.0, 255.0, 1.0));
        public final BooleanSetting smartMode = this.addSetting(new BooleanSetting(Deobf.decrypt(" A)\u001cf\u00e4\u0081\u0085\u00ad\u011e"), Deobf.decrypt(">I:\tw\u00e4\u00a2\u008f\u00a8\u0109\u010b\u011a\u0163\u0196\u01ff\u01d5\u01f9\u0208\u0251\u025c\u0232\u028d\u029a\u0282\u02a1\u0311\u0317\u0319\u0352\u0390\u03e2\u039f\u03ad\u03d5\u040f\u042f\u044f\u046e\u04cc\u04b9\u04f3\u04f3\u054a\u056d\u055c\u050c\u05c9\u05e7\u05c1\u05a5\u060e\u0631"), true));
        public final SliderSetting mergeRadius = this.addSetting(new SliderSetting(Deobf.decrypt(">I:\tw\u00e4\u009e\u008b\u00ad\u0112\u011c\u0110"), Deobf.decrypt("5@)\ta\u00e4\u00bb\u0083\u00bd\u0113\u0100\u010d\u0163\u0184\u01fb\u01dd\u01ed\u025b\u021c\u0254\u0232\u0280\u02d5\u02c1\u02b3\u030b\u0317\u0317\u0352\u0390\u03f8\u0393\u03ab\u03da\u044a\u046e\u0406\u0463\u04dd\u04b8\u04a7\u04ee\u054b\u0561\u0518\u0556\u05cb\u05e8\u05d6"), 3.0, 1.0, 8.0, 1.0, Deobf.decrypt("SO ")));
        public final BooleanSetting centroidMarker = this.addSetting(new BooleanSetting(Deobf.decrypt("0I&\u001a`\u00ab\u00a5\u008e\u00e9\u0136\u0108\u0111\u0128\u0195\u01e1"), Deobf.decrypt(">M:\u00052\u00a1\u00ad\u0089\u00a1\u015b\u0113\u010c\u012d\u0195\u01b4\u01c7\u01be\u020c\u0214\u025c\u023b\u0291\u0281\u02c7\u02bf\u035e\u031a\u0319\u034f\u03c4\u03e7\u0393\u03f9\u23a9\u040f\u043a\u0407\u0468\u0489\u04bb\u04ee\u04ea\u0540\u0568\u0541\u050c\u05c6\u05e7\u05c0\u05ab\u064b\u0630\u0648\u065b\u0686"), true));
        public final BooleanSetting showOnRadar = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\u00192\u00ab\u00a2\u00ca\u009b\u011a\u010d\u0102\u0131"), Deobf.decrypt("#Y$\u001dw\u00e4\u00bf\u009f\u00ba\u015b\u0113\u010c\u012d\u0195\u01e0\u0194\u01f1\u0215\u0251\u0241\u0234\u029c\u02d5\u02f0\u02ba\u031a\u0318\u030e\u031a\u0390\u03f3\u0397\u03ab\u039d\u0455\u0421\u0401\u0468\u04da\u04f7\u04e4\u04ed\u0544\u0569\u0548\u050c\u05d0\u05e9\u0593\u05ba\u0603\u0626\u0618\u0651\u0696\u06fe\u06df"), true));
        public final ModeSetting notifications = this.addSetting(new ModeSetting(Deobf.decrypt("=C<\u0007t\u00ad\u00af\u008b\u00bd\u0112\u0106\u010d\u0130"), Deobf.decrypt("2B&\u0001g\u00aa\u00af\u008f\u00e9\u011e\u0108\u0100\u012b\u01d0\u01fd\u01d1\u01e9\u025b\u020b\u025a\u0232\u029c\u02d5\u02cd\u02b5\u031d\u031c\u0350\u0301\u03c7\u03fc\u0382\u03b1\u039d\u044c\u0421\u0400\u047f\u04cd\u04a4\u04a7\u04e0\u054b\u0560\u0518\u0548\u05cd\u05f5\u05c7\u05af\u0605\u0620\u065d"), Deobf.decrypt("'C)\u001df"), Deobf.decrypt("'C)\u001df"), Deobf.decrypt("0D)\u001a"), Deobf.decrypt("<J.")));
        public final SusChunkScanner scanner = new SusChunkScanner(this);

        public SusChunkFinderModule() {
            super(Deobf.decrypt(" Y;-z\u00b1\u00a2\u0081\u008f\u0112\u0107\u0107\u0126\u0182"), Deobf.decrypt("5E&\na\u00e4\u00a0\u0085\u00a7\u011c\u0144\u010f\u012c\u0191\u01f7\u01d1\u01fa\u025b\u0212\u025d\u0229\u0297\u029e\u02d1\u02fb\u236a\u0359\u031e\u0340\u03c3\u03f0\u0385\u03f9\u23a9\u040f\u0438\u0406\u046c\u0489\u04b6\u04ea\u04e4\u0551\u056c\u0541\u055f\u05d0\u05a6\u05df\u05a7\u060c\u062b\u064c\u0614\u06d4\u06b9\u06dd\u06ca\u0702\u0761\u0749\u0754"), Category.RENDER);
            this.outlineOpacity.visibleWhen(this.outline::get);
        }

        @Override
        public void onTick() {
            this.scanner.tick();
        }

        @Override
        protected void onDisable() {
            this.scanner.clear();
            SusChunkRenderer.reset();
        }
    }

    public static class ClickGuiModule
    extends Module {
        public final BooleanSetting blur = this.addSetting(new BooleanSetting(Deobf.decrypt("1@=\u001c"), Deobf.decrypt("4M=\u001da\u00ad\u00ad\u0084\u00e4\u0119\u0105\u0116\u0131\u01d0\u01e7\u01dc\u01fb\u025b\u0206\u025a\u022e\u0295\u0291\u0282\u02b9\u031b\u0311\u0315\u034f\u03d4\u03b5\u0382\u03b1\u03d8\u040f\u0409\u043a\u0444"), true));
        public final SliderSetting blurStrength = this.addSetting(new SliderSetting(Deobf.decrypt("1@=\u001c2\u0097\u00b8\u0098\u00ac\u0115\u010e\u0117\u012b"), Deobf.decrypt(";C?Na\u00b0\u00be\u0085\u00a7\u011c\u0149\u0117\u012b\u0195\u01b3\u01d6\u01ff\u0218\u021a\u0252\u022e\u0296\u0280\u02cc\u02bf\u035e\u031b\u0310\u0354\u03c2\u03b5\u039f\u03aa"), 6.0, 1.0, 10.0, 1.0));
        public final ModeSetting font = this.addSetting(new ModeSetting(Deobf.decrypt("5C&\u001a"), Deobf.decrypt("4y\u0001Nt\u00ab\u00a2\u009e\u00e9\u0153\u0131\u0116\u012c\u019e\u01f4\u0194\u01ca\u022f\u0237\u0215\u0233\u028b\u02d5\u02d4\u02ba\u0310\u0310\u0310\u034d\u03d1\u03b8\u0385\u03ad\u03c4\u0443\u042b\u0446"), Deobf.decrypt("+Y'\u0000u"), Deobf.decrypt("+Y'\u0000u"), Deobf.decrypt("%M&\u0007~\u00a8\u00ad")));

        public ClickGuiModule() {
            super(Deobf.decrypt("0@!\ry\u0083\u0099\u00a3"), Deobf.decrypt("'D-N$\u00f3\u008f\u0086\u00a0\u011e\u0107\u0117\u0163\u019d\u01f6\u01da\u01eb\u0255\u0251\u027c\u0232\u02d9\u0298\u02c7\u02b5\u030b\u030a\u0350\u0301\u03e3\u03fd\u039f\u03bf\u03c9\u040f\u0421\u041f\u0468\u04c7\u04a4\u04a7\u04e8\u0551\u0524\u054c\u0543\u05cb\u05a8"), Category.CLIENT);
            this.getKeybind().set(344);
        }
    }

    public static class Placeholder
    extends Module {
        public Placeholder(String name, String description, Category category, Setting<?> ... settings) {
            super(name, description, category);
            for (Setting<?> setting : settings) {
                this.addSetting(setting);
            }
        }
    }
}

