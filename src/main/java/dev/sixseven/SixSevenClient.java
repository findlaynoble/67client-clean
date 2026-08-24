package dev.sixseven;

import dev.sixseven.config.ConfigManager;
import dev.sixseven.config.ConfigStore;
import dev.sixseven.gui.ClickGuiScreen;
import dev.sixseven.gui.GambleRiggerOverlay;
import dev.sixseven.hud.HudManager;
import dev.sixseven.module.Category;
import dev.sixseven.module.ModuleManager;
import dev.sixseven.module.impl.FreecamModule;
import dev.sixseven.notification.NotificationManager;
import dev.sixseven.render.MotionBlurRenderer;
import dev.sixseven.render.OverlayRenderer;
import dev.sixseven.render.SusChunkRenderer;
import dev.sixseven.spotify.SpotifyService;
import dev.sixseven.suschunk.ServerLightCache;
import dev.sixseven.theme.SoundSettings;
import dev.sixseven.theme.ThemeManager;
import dev.sixseven.util.UiSoundEvents;
import dev.sixseven.util.UiSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.class_310;
import net.minecraft.class_437;
import net.minecraft.class_442;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SixSevenClient implements ClientModInitializer {
    public static final String MOD_ID = "sixsevenclient";
    public static final String NAME = "67Client";
    public static final String VERSION = "1.6.2";
    public static final Logger LOGGER = LoggerFactory.getLogger((String)"67Client");
    private static ModuleManager modules;
    private static ThemeManager themes;
    private static ConfigManager config;
    private static ConfigStore configStore;
    private static HudManager hud;
    private static NotificationManager notifications;
    private static SpotifyService spotify;
    private static SoundSettings soundSettings;
    private static boolean startupSoundPlayed;

    public static ModuleManager modules() {
        return modules;
    }

    public static ThemeManager themes() {
        return themes;
    }

    public static ConfigManager config() {
        return config;
    }

    public static ConfigStore configStore() {
        return configStore;
    }

    public static HudManager hud() {
        return hud;
    }

    public static SpotifyService spotify() {
        return spotify;
    }

    public static SoundSettings sounds() {
        return soundSettings;
    }

    public static NotificationManager notifications() {
        return notifications;
    }

    public void onInitializeClient() {
        LOGGER.info("{} {} initializing", (Object)NAME, (Object)VERSION);
        UiSoundEvents.bootstrap();
        themes = new ThemeManager();
        soundSettings = new SoundSettings();
        modules = new ModuleManager();
        spotify = new SpotifyService();
        notifications = new NotificationManager(themes, SixSevenClient.modules.hud);
        hud = new HudManager(modules, themes, spotify, notifications);
        config = new ConfigManager(modules, themes);
        config.addSection("hud", hud::toJson, hud::fromJson);
        config.addSection("panels", ClickGuiScreen.state()::toJson, ClickGuiScreen.state()::fromJson);
        config.addSection("sounds", soundSettings::toJson, soundSettings::fromJson);
        config.load();
        configStore = new ConfigStore(config);
        configStore.loadAll();
        UiSounds.init(soundSettings);
        modules.setOpenGuiAction(() -> class_310.method_1551().method_1507((class_437)new ClickGuiScreen()));
        modules.setToggleListener((module, enabled) -> {
            if (ConfigStore.applying) {
                return;
            }
            if (module.getCategory() == Category.CLIENT) {
                return;
            }
            if (class_310.method_1551().field_1687 == null) {
                return;
            }
            if (!((Boolean)SixSevenClient.modules.hud.notifications.get()).booleanValue()) {
                return;
            }
            notifications.push(module.getName(), (boolean)enabled);
            UiSounds.notification(enabled);
        });
        OverlayRenderer.init(hud, notifications);
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof class_442 && !startupSoundPlayed) {
                startupSoundPlayed = true;
                UiSounds.playStartup();
            }
        });
        GambleRiggerOverlay.register();
        spotify.start();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> SixSevenClient.clearSusState());
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> SixSevenClient.clearSusState());
        ClientTickEvents.END_CLIENT_TICK.register(client -> modules.onTick());
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            FreecamModule f = SixSevenClient.modules.freecam;
            if (f != null && f.isActive()) {
                FreecamModule.reapplyBodyInput(client);
            }
            modules.onCombatTick();
        });
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            config.save();
            spotify.stop();
        });
    }

    private static void clearSusState() {
        ServerLightCache.get().clear();
        SixSevenClient.modules.susChunkFinder.scanner.clear();
        SusChunkRenderer.reset();
        if (SixSevenClient.modules.chunkFinder != null) {
            SixSevenClient.modules.chunkFinder.clear();
        }
        if (SixSevenClient.modules.blockEntityEsp != null) {
            SixSevenClient.modules.blockEntityEsp.clear();
        }
        if (SixSevenClient.modules.jumpCircles != null) {
            SixSevenClient.modules.jumpCircles.clear();
        }
        if (SixSevenClient.modules.hitParticles != null) {
            SixSevenClient.modules.hitParticles.clear();
        }
        if (SixSevenClient.modules.customAccessories != null) {
            SixSevenClient.modules.customAccessories.clear();
        }
        MotionBlurRenderer.reset();
    }
}
