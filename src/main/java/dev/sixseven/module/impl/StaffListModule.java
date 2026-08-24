/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.class_1109
 *  net.minecraft.class_1113
 *  net.minecraft.class_2561
 *  net.minecraft.class_310
 *  net.minecraft.class_3414
 *  net.minecraft.class_3417
 */
package dev.sixseven.module.impl;

import dev.sixseven.SixSevenClient;
import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.SliderSetting;
import dev.sixseven.settings.StringSetting;
import dev.sixseven.staff.StaffDetector;
import dev.sixseven.staff.StaffEntry;
import dev.sixseven.staff.StaffTracker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.class_1109;
import net.minecraft.class_1113;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_3414;
import net.minecraft.class_3417;

public class StaffListModule
extends Module {
    public final ModeSetting detectBy = this.addSetting(new ModeSetting(Deobf.decrypt("7I<\u000bq\u00b0\u00ec\u00a8\u00b0"), Deobf.decrypt(";C?Na\u00b0\u00ad\u008c\u00af\u015b\u0108\u0111\u0126\u01d0\u01e1\u01d1\u01fd\u0214\u0216\u025b\u0235\u028a\u0290\u02c6\u02e1\u035e\u030d\u0314\u0344\u0390\u03d1\u0399\u03b7\u03c8\u045b\u041d\u0422\u045d\u0489\u04b4\u04e8\u04ed\u054a\u0571\u054a\u0549\u05c0\u05a6\u05c0\u05ba\u060a\u0631\u0614\u0614\u0693\u06b9\u06ce\u06dd\u0715\u0762\u071d\u074e\u07da\u078a\u0798\u07c8\u07b9\u0856\u081b\u080c\u0860\u08d2\u08e2\u08c4\u08f7\u0938\u0962\u0902\u092c\u0989\u0987"), Deobf.decrypt(" X)\u001c2\u00ef\u00ec\u00b8\u00a8\u0115\u0102"), Deobf.decrypt(" X)\u001c2\u00ef\u00ec\u00b8\u00a8\u0115\u0102"), Deobf.decrypt(" X)\u001c2\u008b\u00a2\u0086\u00b0"), Deobf.decrypt("!M&\u00052\u008b\u00a2\u0086\u00b0"), Deobf.decrypt("=M%\u000ba\u00e4\u0083\u0084\u00a5\u0102")));
    public final StringSetting staffNames = this.addSetting(new StringSetting(Deobf.decrypt(" X)\bt\u00e4\u0082\u008b\u00a4\u011e\u011a"), Deobf.decrypt("6T<\u001cs\u00e4\u00a7\u0084\u00a6\u010c\u0107\u0143\u0130\u0184\u01f2\u01d2\u01f8\u025b\u0204\u0246\u0239\u028b\u029b\u02c3\u02b6\u031b\u030a\u035c\u0309\u03d3\u03fa\u039b\u03b4\u03dc\u0402\u043d\u040a\u047d\u04c8\u04a5\u04e6\u04f5\u0540\u0560\u0511\u050c\u25b0\u05a6\u05d2\u05a2\u061c\u0622\u0641\u0647\u06d2\u06ea\u06d2\u06d7\u071a\u0778\u071d\u074b\u07d3\u0781\u079d\u07c8\u07a6\u084a\u0812\u0803\u0867\u08cf"), Deobf.decrypt(""), 220, Deobf.decrypt("\u0016\u0002/@2\u008a\u00a3\u009e\u00aa\u0113\u0145\u0143\u0129\u0195\u01f1\u01eb")));
    public final StringSetting rankKeywords = this.addSetting(new StringSetting(Deobf.decrypt("!M&\u00052\u008f\u00a9\u0093\u00be\u0114\u011b\u0107\u0130"), Deobf.decrypt("$C:\na\u00e4\u00a5\u0084\u00e9\u011a\u0149\u010d\u0122\u019d\u01f6\u0194\u01ea\u021a\u0216\u0215\u0228\u0291\u0294\u02d6\u02fb\u0313\u0318\u030e\u034a\u0390\u03e6\u0382\u03b8\u03db\u0449\u046e\u0447\u046e\u04c6\u04ba\u04ea\u04e0\u0508\u0577\u055d\u055c\u05c5\u05f4\u05d2\u05ba\u060e\u0627\u0611\u0618\u06d2\u06f4\u06d5\u06cb\u0719\u0736\u074e\u0759\u07d5\u078d\u079c\u079a\u07e9\u0842\u0817\u0818\u087a\u08de"), StaffDetector.DEFAULT_RANK_KEYWORDS_STRING, 256, Deobf.decrypt("\u001c[&\u000b`\u00e8\u00ec\u008b\u00ad\u0116\u0100\u010d\u016f\u01d0\u01fe\u01db\u01fa\u225d")));
    public final StringSetting starSymbols = this.addSetting(new StringSetting(Deobf.decrypt(" X)\u001c2\u0097\u00b5\u0087\u00ab\u0114\u0105\u0110"), Deobf.decrypt(">M:\u0005w\u00b6\u00ec\u008d\u00a5\u0102\u0119\u010b\u0130\u01d0\u01e7\u01dc\u01ff\u020f\u0251\u0258\u0239\u0298\u029b\u0282\u02fc\u030d\u030d\u031d\u0347\u03d6\u03b2\u03d8\u03f9\u03ed\u044e\u043d\u041b\u0468\u0489\u04a3\u04ef\u04e4\u0505\u0577\u055d\u055e\u05d2\u05e3\u05c1\u05e9\u0618\u0663\u064b\u0640\u0693\u06eb\u069a\u06d0\u0708\u0764\u0758\u071c\u07d2\u0782\u07d3\u078c\u07ac\u0850\u081b\u0809\u087d\u08c3\u08a1\u088a\u08b8\u0927\u092b\u0913\u0930\u0998\u099c"), Deobf.decrypt("\u2676\u262a\u276e\u2749\u2738\u27ed\u27e7\u27c6\u27e4\u2655\u2646\u2a33\u2673\u26b2\u270e\u2680\u26ab\u254d\u2546\u250d\u2565\u21a6"), 96, Deobf.decrypt("\u2676\f\u276eN\u2b42")));
    public final BooleanSetting fontIcons = this.addSetting(new BooleanSetting(Deobf.decrypt("5C&\u001a2\u008d\u00af\u0085\u00a7\u0108"), Deobf.decrypt("2@;\u00012\u00b0\u00be\u008f\u00a8\u010f\u0149\u0100\u0136\u0183\u01e7\u01db\u01f3\u025b\u0203\u0250\u022f\u0296\u0280\u02d0\u02b8\u031b\u0354\u030c\u0340\u03d3\u03fe\u03d6\u03b0\u03de\u0440\u0420\u041c\u042d\u0481\u04a7\u04f5\u04e8\u0553\u0565\u054c\u0549\u0589\u05f3\u05c0\u05ab\u064b\u0624\u0654\u064d\u0682\u06f1\u06c9\u0691\u074d\u0777\u074e\u071c\u07c8\u0790\u0792\u078e\u07af\u0804\u080d\u081e\u0868\u08d8\u08bd"), true));
    public final BooleanSetting showRank = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\u00192\u0096\u00ad\u0084\u00a2"), Deobf.decrypt(" D'\u00192\u00a1\u00ad\u0089\u00a1\u015b\u011a\u0117\u0122\u0196\u01f5\u0194\u01f3\u021e\u021c\u0257\u0239\u028b\u02d2\u02d1\u02fb\u030c\u0318\u0312\u034a\u0390\u03f9\u0397\u03bb\u03d8\u0443\u046e\u0418\u0465\u04cc\u04b9\u04a7\u04f5\u054d\u0561\u0518\u055f\u05c1\u05f4\u05c5\u05ab\u0619\u0663\u065d\u064c\u0682\u06f6\u06c9\u06dd\u071e\u0736\u0752\u0752\u07de"), true));
    public final BooleanSetting showPing = this.addSetting(new BooleanSetting(Deobf.decrypt(" D'\u00192\u0094\u00a5\u0084\u00ae"), Deobf.decrypt(" D'\u00192\u00a1\u00ad\u0089\u00a1\u015b\u011a\u0117\u0122\u0196\u01f5\u0194\u01f3\u021e\u021c\u0257\u0239\u028b\u02d2\u02d1\u02fb\u0312\u0318\u0308\u0344\u03de\u03f6\u038f"), false));
    public final BooleanSetting vanished = this.addSetting(new BooleanSetting(Deobf.decrypt("%M&\u0007a\u00ac\u00a9\u008e\u00e9\u0128\u011d\u0102\u0125\u0196"), Deobf.decrypt(":B+\u0002g\u00a0\u00a9\u00ca\u00ba\u0114\u010f\u0117\u016e\u0186\u01f2\u01da\u01f7\u0208\u0219\u0250\u0238\u02d9\u0286\u02d6\u02ba\u0318\u031f\u035c\u0309\u03c3\u03e5\u0393\u03ba\u03c9\u044e\u043a\u0400\u047f\u0489\u04f8\u04a7\u04e9\u054c\u0560\u055c\u0549\u05ca\u05a6\u05d5\u05bc\u0604\u062e\u0618\u0640\u0693\u06fb\u0693\u0694\u074d\u077b\u075c\u074e\u07d0\u0781\u0797\u07c8\u07ba\u0841\u080e\u080b\u087b\u08cb\u08ba\u0881\u08f4\u0933"), true));
    public final SliderSetting maxRows = this.addSetting(new SliderSetting(Deobf.decrypt(">M0N@\u00ab\u00bb\u0099"), Deobf.decrypt(">C;\u001a2\u00b7\u00b8\u008b\u00af\u011d\u0149\u0111\u012c\u0187\u01e0\u0194\u01ea\u0214\u0251\u0246\u0234\u0296\u0282\u0282\u02b9\u031b\u031f\u0313\u0353\u03d5\u03b5\u0395\u03b6\u03d1\u0443\u042f\u041f\u047e\u04c0\u04b9\u04e0\u04a1\u054c\u056a\u054c\u0543\u0584\u05e7\u0593\u05e9\u0640\u060d\u0618\u0659\u069d\u06eb\u06df\u069f\u074d\u077a\u0754\u0752\u07de"), 6.0, 1.0, 20.0, 1.0));
    public final ModeSetting alerts = this.addSetting(new ModeSetting(Deobf.decrypt("2@-\u001cf\u00b7"), Deobf.decrypt("2B&\u0001g\u00aa\u00af\u008f\u00e9\u010c\u0101\u0106\u012d\u01d0\u01f2\u0194\u01f0\u021e\u0206\u0215\u022f\u028d\u0294\u02c4\u02bd\u035e\u0314\u0319\u034c\u03d2\u03f0\u0384\u03f9\u03dc\u045f\u043e\u040a\u046c\u04db\u04a4\u04a7\u04e8\u054b\u0524\u0541\u0543\u05d1\u05f4\u0593\u05ba\u060a\u0621"), Deobf.decrypt("'C)\u001df"), Deobf.decrypt("'C)\u001df"), Deobf.decrypt("0D)\u001a"), Deobf.decrypt("<J.")));
    public final BooleanSetting alertSound = this.addSetting(new BooleanSetting(Deobf.decrypt("2@-\u001cf\u00e4\u009f\u0085\u00bc\u0115\u010d"), Deobf.decrypt("#@)\u00172\u00a5\u00ec\u0088\u00ac\u0117\u0105\u0143\u0134\u0198\u01f6\u01da\u01be\u021a\u0251\u0246\u0228\u0298\u0293\u02c4\u02fb\u031f\u0315\u0319\u0353\u03c4\u03b5\u0390\u03b0\u03cf\u044a\u043d"), true));
    public final StaffTracker tracker = new StaffTracker(this);

    public StaffListModule() {
        super(Deobf.decrypt(" X)\bt\u0088\u00a5\u0099\u00bd"), Deobf.decrypt("?E;\u001aa\u00e4\u00a3\u0084\u00a5\u0112\u0107\u0106\u0163\u0183\u01e7\u01d5\u01f8\u021d\u0251\u025a\u0232\u02d9\u0281\u02ca\u02be\u035e\u0331\u0329\u0365\u0390\u03bd\u03b2\u03b6\u03d3\u045a\u043a\u043c\u0440\u04f9\u04f7\u04e4\u04ee\u0549\u056b\u054d\u055e\u05c1\u05e2\u059e\u05bd\u061f\u0622\u064a\u0614\u0696\u06fc\u06ce\u06dd\u070e\u0762\u0754\u0753\u07d5\u07cd"), Category.MISC);
        this.rankKeywords.visibleWhen(this::usesRank);
        this.starSymbols.visibleWhen(this::usesStar);
        this.fontIcons.visibleWhen(this::usesStar);
        this.alertSound.visibleWhen(() -> !this.alerts.is(Deobf.decrypt("<J.")));
    }

    private boolean usesStar() {
        return this.detectBy.is(Deobf.decrypt(" X)\u001c2\u00ef\u00ec\u00b8\u00a8\u0115\u0102")) || this.detectBy.is(Deobf.decrypt(" X)\u001c2\u008b\u00a2\u0086\u00b0"));
    }

    private boolean usesRank() {
        return this.detectBy.is(Deobf.decrypt(" X)\u001c2\u00ef\u00ec\u00b8\u00a8\u0115\u0102")) || this.detectBy.is(Deobf.decrypt("!M&\u00052\u008b\u00a2\u0086\u00b0"));
    }

    @Override
    protected void onEnable() {
        this.tracker.reset();
    }

    @Override
    protected void onDisable() {
        this.tracker.clear();
    }

    @Override
    public void onTick() {
        this.tracker.tick();
    }

    public List<StaffEntry> staff() {
        return this.tracker.current();
    }

    public StaffDetector.DetectConfig detectConfig() {
        List<String> keywords = StaffListModule.parseKeywords((String)this.rankKeywords.get());
        return new StaffDetector.DetectConfig((String)this.detectBy.get(), StaffListModule.parseNames((String)this.staffNames.get()), keywords.isEmpty() ? StaffDetector.DEFAULT_RANK_KEYWORDS : keywords, (String)this.starSymbols.get(), (Boolean)this.fontIcons.get(), (Boolean)this.vanished.get());
    }

    public void onStaffAppear(StaffEntry e) {
        String tail;
        class_310 mc = class_310.method_1551();
        if (mc.field_1724 == null || this.alerts.is(Deobf.decrypt("<J."))) {
            return;
        }
        String rank = e.rankLabel().isEmpty() ? Deobf.decrypt(" X)\bt") : e.rankLabel();
        String string = tail = e.vanished() ? Deobf.decrypt("S\u0004>\u000f|\u00ad\u00bf\u0082\u00ac\u011f\u0140") : Deobf.decrypt("");
        if (this.alerts.is(Deobf.decrypt("'C)\u001df"))) {
            if (SixSevenClient.notifications() != null) {
                SixSevenClient.notifications().pushInfo(rank + " " + e.name() + " online" + tail);
            }
        } else if (this.alerts.is(Deobf.decrypt("0D)\u001a"))) {
            mc.field_1724.method_7353((class_2561)class_2561.method_43470((String)("\u00a7d[67] \u00a7f" + rank + " \u00a7b" + e.name() + "\u00a77 is online" + tail)), false);
        }
        if (((Boolean)this.alertSound.get()).booleanValue()) {
            mc.method_1483().method_4873((class_1113)class_1109.method_4757((class_3414)((class_3414)class_3417.field_14793.comp_349()), (float)1.5f, (float)0.6f));
        }
    }

    private static Set<String> parseNames(String raw) {
        HashSet<String> out = new HashSet<String>();
        for (String part : raw.split(Deobf.decrypt("(\u0000\u0014\u001dO\u00ef"))) {
            if (part.isEmpty()) continue;
            out.add(part.toLowerCase(Locale.ROOT));
        }
        return out;
    }

    private static List<String> parseKeywords(String raw) {
        ArrayList<String> out = new ArrayList<String>();
        for (String part : raw.split(Deobf.decrypt("(\u0000\u0014\u001dO\u00ef"))) {
            String kw = part.trim().toLowerCase(Locale.ROOT);
            if (kw.isEmpty()) continue;
            out.add(kw);
        }
        return out;
    }
}

