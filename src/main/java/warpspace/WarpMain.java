package warpspace;

import org.schema.schine.resource.ResourceLoader;

import api.config.BlockConfig;
import api.listener.events.controller.ClientInitializeEvent;
import api.listener.events.controller.ServerInitializeEvent;
import api.mod.StarLoader;
import api.mod.StarMod;
import api.utils.registry.UniversalRegistry;
import glossar.GlossarInit;
import warpspace.beacon.BeaconManager;
import warpspace.beacon.WarpBeaconAddon;
import warpspace.client.DebugUI;
import warpspace.client.HUD_core;
import warpspace.client.SpriteList;
import warpspace.client.map.DropPointMapDrawer;
import warpspace.client.sounds.SoundQueueManager;
import warpspace.manager.EventManager;
import warpspace.manager.LoopManager;
import warpspace.manager.PacketManager;
import warpspace.manager.ConfigManager;
import warpspace.taswin.WarpSpaceMap;
import warpspace.visuals.WarpSkybox;

/**
 * Entry point for the WarpSpace mod. Each lifecycle hook delegates to the
 * managers under {@link warpspace.manager} so that the ordering of
 * initialization is visible in one place.
 */
public class WarpMain extends StarMod {

    public static WarpMain instance;

    public BeaconManager beaconManagerServer;
    public BeaconManager beaconManagerClient;
    public DropPointMapDrawer dropPointMapDrawer;
    public WarpThrusterListener warpThrusterListener;

    public static WarpMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        ConfigManager.initialize(this);
        new Updater(getSkeleton().getModVersion()).runUpdate();

        StarLoader.registerCommand(new DebugUI());

        PacketManager.registerAll();

        WarpSpaceMap.enable(instance);
        WarpSkybox.registerForRegistration();

        dropPointMapDrawer = new DropPointMapDrawer(this);

        EventManager.registerCommon(this);
    }

    @Override
    public void onDisable() {
        WarpSpaceMap.disable();
    }

    @Override
    public void onServerCreated(ServerInitializeEvent event) {
        super.onServerCreated(event);

        EventManager.registerServer();
        LoopManager.startServerLoops();

        beaconManagerServer = BeaconManager.getSavedOrNew(this.getSkeleton());
        beaconManagerServer.onInit();
    }

    @Override
    public void onClientCreated(ClientInitializeEvent event) {
        super.onClientCreated(event);

        SpriteList.init();
        HUD_core.initList();

        EventManager.registerClient();
        LoopManager.startClientLoops();

        beaconManagerClient = new BeaconManager();
        beaconManagerClient.onInit();
        dropPointMapDrawer.activate();

        GlossarInit.initGlossar(this);
        GlossarInit.addCategory(WarpGlossar.buildCategory());

        new SoundQueueManager();
    }

    @Override
    public void onResourceLoad(ResourceLoader loader) {
        super.onResourceLoad(loader);
        WarpSkybox.loadResources(loader.getMeshLoader(), this);
        dropPointMapDrawer.loadSprite();
    }

    @Override
    public void onBlockConfigLoad(BlockConfig blockConfig) {
        super.onBlockConfigLoad(blockConfig);
        WarpBeaconAddon.registerChamberBlock();
        WSElementInfoManager.onBlockConfigLoad(instance, blockConfig);
    }

    @Override
    public void onUniversalRegistryLoad() {
        super.onUniversalRegistryLoad();
        UniversalRegistry.registerURV(
                UniversalRegistry.RegistryType.PLAYER_USABLE_ID,
                this.getSkeleton(),
                WarpBeaconAddon.UIDName);
    }
}
