package dev.sixseven.module;

import dev.sixseven.module.Category;
import dev.sixseven.module.Module;
import dev.sixseven.module.Modules;
import dev.sixseven.module.impl.AnchorMacroModule;
import dev.sixseven.module.impl.ArmorTrimHiderModule;
import dev.sixseven.module.impl.AutoClickerModule;
import dev.sixseven.module.impl.AutoCrystalModule;
import dev.sixseven.module.impl.AutoInventoryTotemModule;
import dev.sixseven.module.impl.AutoTotemModule;
import dev.sixseven.module.impl.AutoTpaModule;
import dev.sixseven.module.impl.AutoWalkModule;
import dev.sixseven.module.impl.BlockEntityEspModule;
import dev.sixseven.module.impl.BlockEspModule;
import dev.sixseven.module.impl.ChatMacroModule;
import dev.sixseven.module.impl.ChunkFinderModule;
import dev.sixseven.module.impl.CoordSnapperModule;
import dev.sixseven.module.impl.CustomAccessoriesModule;
import dev.sixseven.module.impl.CustomCrosshairModule;
import dev.sixseven.module.impl.CustomFovModule;
import dev.sixseven.module.impl.CustomGlintModule;
import dev.sixseven.module.impl.DebugHoleEspModule;
import dev.sixseven.module.impl.DoubleAnchorModule;
import dev.sixseven.module.impl.ElytraSwapModule;
import dev.sixseven.module.impl.FakePayModule;
import dev.sixseven.module.impl.FakeRolesModule;
import dev.sixseven.module.impl.FakeStatsModule;
import dev.sixseven.module.impl.FastUseModule;
import dev.sixseven.module.impl.FreeLookModule;
import dev.sixseven.module.impl.FreecamModule;
import dev.sixseven.module.impl.FullbrightModule;
import dev.sixseven.module.impl.GambleRiggerModule;
import dev.sixseven.module.impl.HitBoxModule;
import dev.sixseven.module.impl.HitParticlesModule;
import dev.sixseven.module.impl.HoverTotemModule;
import dev.sixseven.module.impl.JumpCirclesModule;
import dev.sixseven.module.impl.MaceBomberModule;
import dev.sixseven.module.impl.MaceSwapModule;
import dev.sixseven.module.impl.MobEspModule;
import dev.sixseven.module.impl.MotionBlurModule;
import dev.sixseven.module.impl.NameProtectModule;
import dev.sixseven.module.impl.NameTagsModule;
import dev.sixseven.module.impl.PlayerEspModule;
import dev.sixseven.module.impl.RegionMapModule;
import dev.sixseven.module.impl.ShieldBreakerModule;
import dev.sixseven.module.impl.SkinProtectModule;
import dev.sixseven.module.impl.SpawnerNametagsModule;
import dev.sixseven.module.impl.SpawnerProtectModule;
import dev.sixseven.module.impl.StaffListModule;
import dev.sixseven.module.impl.StorageEspModule;
import dev.sixseven.module.impl.SwingSpeedModule;
import dev.sixseven.module.impl.TriggerbotModule;
import dev.sixseven.module.impl.WeatherNotifierModule;
import dev.sixseven.module.impl.ZoomModule;
import dev.sixseven.rt.Deobf;
import dev.sixseven.settings.BooleanSetting;
import dev.sixseven.settings.ModeSetting;
import dev.sixseven.settings.Setting;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<Module>();
    private final Map<Category, List<Module>> byCategory = new LinkedHashMap<Category, List<Module>>();
    public final Modules.ClickGuiModule clickGui;
    public final Modules.HudModule hud;
    public final Modules.SpotifyModule spotify;
    public final Modules.BlockOutlineModule blockOutline;
    public final Modules.SusChunkFinderModule susChunkFinder;
    public FullbrightModule fullbright;
    public AutoWalkModule autoWalk;
    public WeatherNotifierModule weatherNotifier;
    public SwingSpeedModule swingSpeed;
    public StorageEspModule storageEsp;
    public BlockEspModule blockEsp;
    public AutoTotemModule autoTotem;
    public MaceSwapModule maceSwap;
    public AnchorMacroModule anchorMacro;
    public AutoCrystalModule autoCrystal;
    public HitBoxModule hitBox;
    public ElytraSwapModule elytraSwap;
    public HoverTotemModule hoverTotem;
    public ShieldBreakerModule shieldBreaker;
    public TriggerbotModule triggerbot;
    public DoubleAnchorModule doubleAnchor;
    public MaceBomberModule maceBomber;
    public NameProtectModule nameProtect;
    public SkinProtectModule skinProtect;
    public NameTagsModule nameTags;
    public FastUseModule fastUse;
    public AutoInventoryTotemModule autoInventoryTotem;
    public PlayerEspModule playerEsp;
    public MobEspModule mobEsp;
    public BlockEntityEspModule blockEntityEsp;
    public SpawnerNametagsModule spawnerNametags;
    public DebugHoleEspModule debugHoleEsp;
    public FreecamModule freecam;
    public AutoTpaModule autoTpa;
    public JumpCirclesModule jumpCircles;
    public CustomCrosshairModule customCrosshair;
    public ZoomModule zoom;
    public CustomFovModule customFov;
    public HitParticlesModule hitParticles;
    public MotionBlurModule motionBlur;
    public CustomGlintModule customGlint;
    public CustomAccessoriesModule customAccessories;
    public ChunkFinderModule chunkFinder;
    public FreeLookModule freeLook;
    public AutoClickerModule autoClicker;
    public CoordSnapperModule coordSnapper;
    public RegionMapModule regionMap;
    public ChatMacroModule chatMacro;
    public FakePayModule fakePay;
    public FakeStatsModule fakeStats;
    public FakeRolesModule fakeRoles;
    public StaffListModule staffList;
    public ArmorTrimHiderModule armorTrimHider;
    public SpawnerProtectModule spawnerProtect;
    public GambleRiggerModule gambleRigger;
    private Runnable openGuiAction = () -> {};
    private BiConsumer<Module, Boolean> toggleListener = (m, e) -> {};

    public ModuleManager() {
        for (Category category : Category.values()) {
            this.byCategory.put(category, new ArrayList());
        }
        this.susChunkFinder = new Modules.SusChunkFinderModule();
        this.registerPlaceholders();
        this.blockOutline = new Modules.BlockOutlineModule();
        this.register(this.blockOutline);
        this.hitParticles = new HitParticlesModule();
        this.register(this.hitParticles);
        this.customAccessories = new CustomAccessoriesModule();
        this.register(this.customAccessories);
        this.motionBlur = new MotionBlurModule();
        this.register(this.motionBlur);
        this.hud = new Modules.HudModule();
        this.register(this.hud);
        this.spotify = new Modules.SpotifyModule();
        this.register(this.spotify);
        this.chatMacro = new ChatMacroModule();
        this.register(this.chatMacro);
        this.ph(Deobf.decrypt("0C&\b{\u00a3\u009f\u0082\u00a8\u0109\u010c"), Deobf.decrypt(":A8\u0001`\u00b0\u00e3\u008f\u00b1\u010b\u0106\u0111\u0137\u01d0\u01f0\u01db\u01f0\u021d\u0218\u0252\u022f\u02d9\u0283\u02cb\u02ba\u035e\u031a\u0313\u0345\u03d5\u03e6"), Category.CLIENT, new BooleanSetting(Deobf.decrypt(":B+\u0002g\u00a0\u00a9\u00ca\u0081\u012e\u012d\u0143\u010f\u0191\u01ea\u01db\u01eb\u020f"), Deobf.decrypt(" D)\u001cw\u00e4\u0084\u00bf\u008d\u015b\u0119\u010c\u0130\u0199\u01e7\u01dd\u01f1\u0215\u0202\u0215\u0228\u0296\u029a"), true));
        this.swingSpeed = new SwingSpeedModule();
        this.register(this.swingSpeed);
        this.jumpCircles = new JumpCirclesModule();
        this.register(this.jumpCircles);
        this.clickGui = new Modules.ClickGuiModule();
        this.register(this.clickGui);
    }

    private void registerPlaceholders() {
        this.autoTotem = new AutoTotemModule();
        this.register(this.autoTotem);
        this.autoCrystal = new AutoCrystalModule();
        this.register(this.autoCrystal);
        this.anchorMacro = new AnchorMacroModule();
        this.register(this.anchorMacro);
        this.doubleAnchor = new DoubleAnchorModule();
        this.register(this.doubleAnchor);
        this.autoInventoryTotem = new AutoInventoryTotemModule();
        this.register(this.autoInventoryTotem);
        this.maceSwap = new MaceSwapModule();
        this.register(this.maceSwap);
        this.hitBox = new HitBoxModule();
        this.register(this.hitBox);
        this.elytraSwap = new ElytraSwapModule();
        this.register(this.elytraSwap);
        this.hoverTotem = new HoverTotemModule();
        this.register(this.hoverTotem);
        this.shieldBreaker = new ShieldBreakerModule();
        this.register(this.shieldBreaker);
        this.triggerbot = new TriggerbotModule();
        this.register(this.triggerbot);
        this.maceBomber = new MaceBomberModule();
        this.register(this.maceBomber);
        this.skinProtect = new SkinProtectModule();
        this.register(this.skinProtect);
        this.nameProtect = new NameProtectModule();
        this.register(this.nameProtect);
        this.freecam = new FreecamModule();
        this.register(this.freecam);
        this.autoTpa = new AutoTpaModule();
        this.register(this.autoTpa);
        this.autoClicker = new AutoClickerModule();
        this.register(this.autoClicker);
        this.fastUse = new FastUseModule();
        this.register(this.fastUse);
        this.nameTags = new NameTagsModule();
        this.register(this.nameTags);
        this.fakePay = new FakePayModule();
        this.register(this.fakePay);
        this.weatherNotifier = new WeatherNotifierModule();
        this.register(this.weatherNotifier);
        this.fakeStats = new FakeStatsModule();
        this.register(this.fakeStats);
        this.fakeRoles = new FakeRolesModule();
        this.register(this.fakeRoles);
        this.armorTrimHider = new ArmorTrimHiderModule();
        this.register(this.armorTrimHider);
        this.customCrosshair = new CustomCrosshairModule();
        this.register(this.customCrosshair);
        this.ph(Deobf.decrypt(">I,\u0007s\u00eb\u009f\u009e\u00a8\u011d\u010f\u012d\u0122\u019d\u01f6\u01c7\u01b1\u0232\u0212\u025a\u0232\u028a"), Deobf.decrypt(">M:\u0005a\u00e4\u00a1\u008f\u00ad\u0112\u0108\u0143\u0165\u01d0\u01e0\u01c0\u01ff\u021d\u0217\u0215\u022c\u0295\u0294\u02db\u02be\u030c\u030a\u035c\u0356\u03d9\u03e1\u039e\u03f9\u03d4\u044c\u0421\u0401\u047e"), Category.MISC, new BooleanSetting(Deobf.decrypt(">I,\u0007s"), Deobf.decrypt(" D'\u00192\u00a9\u00a9\u008e\u00a0\u011a\u0149\u010a\u0120\u019f\u01fd\u01c7"), true), new BooleanSetting(Deobf.decrypt(" X)\bt"), Deobf.decrypt(" D'\u00192\u00b7\u00b8\u008b\u00af\u011d\u0149\u010a\u0120\u019f\u01fd\u01c7"), true), new ModeSetting(Deobf.decrypt("#C;\u0007f\u00ad\u00a3\u0084"), Deobf.decrypt(":O'\u00002\u00b4\u00a3\u0099\u00a0\u010f\u0100\u010c\u012d"), Deobf.decrypt("#^-\b{\u00bc"), Deobf.decrypt("#^-\b{\u00bc"), Deobf.decrypt(" Y.\b{\u00bc")));
        this.staffList = new StaffListModule();
        this.register(this.staffList);
        this.customGlint = new CustomGlintModule();
        this.register(this.customGlint);
        this.customFov = new CustomFovModule();
        this.register(this.customFov);
        this.coordSnapper = new CoordSnapperModule();
        this.register(this.coordSnapper);
        this.autoWalk = new AutoWalkModule();
        this.register(this.autoWalk);
        this.zoom = new ZoomModule();
        this.register(this.zoom);
        this.freeLook = new FreeLookModule();
        this.register(this.freeLook);
        this.spawnerProtect = new SpawnerProtectModule();
        this.register(this.spawnerProtect);
        this.gambleRigger = new GambleRiggerModule();
        this.register(this.gambleRigger);
        this.blockEsp = new BlockEspModule();
        this.register(this.blockEsp);
        this.storageEsp = new StorageEspModule();
        this.register(this.storageEsp);
        this.blockEntityEsp = new BlockEntityEspModule();
        this.register(this.blockEntityEsp);
        this.debugHoleEsp = new DebugHoleEspModule();
        this.register(this.debugHoleEsp);
        this.fullbright = new FullbrightModule();
        this.register(this.fullbright);
        this.playerEsp = new PlayerEspModule();
        this.register(this.playerEsp);
        this.mobEsp = new MobEspModule();
        this.register(this.mobEsp);
        this.spawnerNametags = new SpawnerNametagsModule();
        this.register(this.spawnerNametags);
        this.register(this.susChunkFinder);
        this.chunkFinder = new ChunkFinderModule();
        this.register(this.chunkFinder);
        this.regionMap = new RegionMapModule();
        this.register(this.regionMap);
    }

    private void ph(String name, String description, Category category, Setting<?> ... settings) {
        this.register(new Modules.Placeholder(name, description, category, settings));
    }

    public void register(Module module) {
        this.modules.add(module);
        this.byCategory.get((Object)module.getCategory()).add(module);
        module.setToggleCallback(this::notifyToggle);
    }

    public List<Module> all() {
        return this.modules;
    }

    public List<Module> inCategory(Category category) {
        return this.byCategory.get((Object)category);
    }

    public void setOpenGuiAction(Runnable action) {
        this.openGuiAction = action;
    }

    public void setToggleListener(BiConsumer<Module, Boolean> listener) {
        this.toggleListener = listener;
    }

    public void notifyToggle(Module module, boolean enabled) {
        this.toggleListener.accept(module, enabled);
    }

    public boolean onKeyPressed(int keyCode) {
        if (this.clickGui.getKeybind().matches(keyCode)) {
            this.openGuiAction.run();
            return true;
        }
        boolean handled = false;
        for (Module module : this.modules) {
            if (module == this.clickGui || !module.getKeybind().matches(keyCode)) continue;
            module.toggle();
            handled = true;
        }
        for (Module module : this.modules) {
            if (!module.isEnabled() || !module.onKeyPress(keyCode)) continue;
            handled = true;
        }
        return handled;
    }

    public void onTick() {
        for (Module module : this.modules) {
            if (!module.isEnabled() || module.getCategory() == Category.COMBAT) continue;
            module.onTick();
        }
    }

    public void onCombatTick() {
        for (Module module : this.modules) {
            if (!module.isEnabled() || module.getCategory() != Category.COMBAT) continue;
            module.onTick();
        }
    }
}
