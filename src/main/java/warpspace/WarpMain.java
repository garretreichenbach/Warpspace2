package warpspace;

import api.config.BlockConfig;
import api.listener.events.controller.ClientInitializeEvent;
import api.listener.events.controller.ServerInitializeEvent;
import api.mod.StarLoader;
import api.mod.StarMod;
import api.utils.registry.UniversalRegistry;
import glossar.GlossarInit;
import org.schema.schine.resource.ResourceLoader;
import warpspace.beacon.BeaconManager;
import warpspace.beacon.WarpBeaconAddon;
import warpspace.client.DebugUI;
import warpspace.client.hud.HUDCore;
import warpspace.client.hud.SpriteList;
import warpspace.client.map.DropPointMapDrawer;
import warpspace.client.map.WarpSpaceMap;
import warpspace.client.rendering.WarpSkybox;
import warpspace.client.sounds.SoundQueueManager;
import warpspace.core.WSElementInfoManager;
import warpspace.manager.ConfigManager;
import warpspace.manager.EventManager;
import warpspace.manager.LoopManager;
import warpspace.manager.PacketManager;
import warpspace.util.Updater;

/**
 * Entry point for the WarpSpace mod. Each lifecycle hook delegates to the
 * managers under {@link warpspace.manager} so that the ordering of
 * initialization is visible in one place.
 */
public class WarpMain extends StarMod {

	public static WarpMain instance;

	private BeaconManager beaconManagerServer;
	private BeaconManager beaconManagerClient;
	private DropPointMapDrawer dropPointMapDrawer;

	public static WarpMain getInstance() {
		return instance;
	}

	public BeaconManager getBeaconManagerServer() {
		return beaconManagerServer;
	}

	public BeaconManager getBeaconManagerClient() {
		return beaconManagerClient;
	}

	public DropPointMapDrawer getDropPointMapDrawer() {
		return dropPointMapDrawer;
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

		EventManager.registerCommon();
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

		beaconManagerServer = BeaconManager.getSavedOrNew(getSkeleton());
		beaconManagerServer.onInit();
	}

	@Override
	public void onClientCreated(ClientInitializeEvent event) {
		super.onClientCreated(event);

		SpriteList.init();
		HUDCore.initList();

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
		WSElementInfoManager.onBlockConfigLoad();
	}

	@Override
	public void onUniversalRegistryLoad() {
		super.onUniversalRegistryLoad();
		UniversalRegistry.registerURV(UniversalRegistry.RegistryType.PLAYER_USABLE_ID, getSkeleton(), WarpBeaconAddon.UIDName);
	}
}
