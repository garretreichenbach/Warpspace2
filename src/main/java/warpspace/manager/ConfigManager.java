package warpspace.manager;

import api.utils.simpleconfig.SimpleConfigBool;
import api.utils.simpleconfig.SimpleConfigContainer;
import api.utils.simpleconfig.SimpleConfigDouble;
import warpspace.WarpSpace;

/**
 * Static configuration access for the mod.
 *
 * <p>Entries are split between two {@link SimpleConfigContainer}s:
 * <ul>
 *   <li>{@code warpspaceConfig} &mdash; server-authoritative, automatically
 *       synced to clients on login. All gameplay and balance entries live
 *       here.</li>
 *   <li>{@code warpspaceClientConfig} &mdash; client-local only. Holds
 *       per-user preferences such as audio/visual toggles.</li>
 * </ul>
 *
 * <p>Entry paths are preserved from the pre-migration layout so existing
 * {@code warpspaceConfig.yaml} files keep their server-side values intact.
 * Client-local preferences that used to share the same file will reset to
 * defaults on first launch after the migration.
 */
public final class ConfigManager {

	private static final String SERVER_CONFIG_NAME = "warpspaceConfig";
	private static final String CLIENT_CONFIG_NAME = "warpspaceClientConfig";
	// Large ceiling to allow legacy float-max entries without overflow.
	private static final double LARGE_MAX = 100000000.0d;
	// --- server-authoritative entries ---------------------------------------
	private static SimpleConfigBool killSwitchAstronautDrop;
	private static SimpleConfigBool killSwitchSpeedDrop;
	private static SimpleConfigBool warpBeaconDisableOnHomebase;
	private static SimpleConfigDouble dropPointRandomOffset;
	private static SimpleConfigDouble warpToRspRatio;
	private static SimpleConfigDouble secondsUntilSpeedDrop;
	private static SimpleConfigDouble minimumWarpSpeed;
	private static SimpleConfigDouble secondsWarpJumpDelay;
	private static SimpleConfigDouble warpSpeedNoChamberMultiplier;
	private static SimpleConfigDouble warpSpeedChamberLvl1Multiplier;
	private static SimpleConfigDouble warpSpeedChamberLvl2Multiplier;
	private static SimpleConfigDouble warpSpeedChamberLvl3Multiplier;
	private static SimpleConfigDouble warpBeaconChamberPercent;
	// --- client-local entries -----------------------------------------------
	private static SimpleConfigBool sfxEffectsEnable;
	private static SimpleConfigBool vfxUseWarpShader;
	private static SimpleConfigDouble mapDrawDropPointsRange;

	private ConfigManager() {
	}

	public static void initialize(WarpSpace mod) {
		SimpleConfigContainer serverConfig = new SimpleConfigContainer(mod, SERVER_CONFIG_NAME, false);
		SimpleConfigContainer clientConfig = new SimpleConfigContainer(mod, CLIENT_CONFIG_NAME, true);

		killSwitchAstronautDrop = new SimpleConfigBool(serverConfig, "kill_switch_astronaut_drop", true, "killswitch to disable automated astronaut drop. Disable if players crash with null pointers in warp.");
		killSwitchSpeedDrop = new SimpleConfigBool(serverConfig, "kill_switch_speed_drop", true, "killswitch to disable automated speed-drop from flying slowly in warp.");
		warpBeaconDisableOnHomebase = new SimpleConfigBool(serverConfig, "warp_beacon_disable_on_homebase", true, "Disable warp beacons on homebase stations.");

		dropPointRandomOffset = new SimpleConfigDouble(serverConfig, "droppoint_random_offset_sectors", 2.25d, "Natural droppoints are randomly shifted up to this many sectors in each axis.");
		warpToRspRatio = new SimpleConfigDouble(serverConfig, "warp_to_rsp_ratio", 10.0d, "Travelling 1 sector in warpspace covers this many sectors in realspace. Droppoints are spaced this far apart.");
		secondsUntilSpeedDrop = new SimpleConfigDouble(serverConfig, "seconds_until_speeddrop", 30.0d, "Seconds spent flying too slowly in warpspace before speed-dropping.");
		minimumWarpSpeed = new SimpleConfigDouble(serverConfig, "minimum_warp_speed", 50.0d, "Required speed in m/s to remain stable in warpspace.");
		secondsWarpJumpDelay = new SimpleConfigDouble(serverConfig, "seconds_warpjump_delay", 9.5d, "Delay in seconds between activating the jumpdrive and the actual jump.");
		warpSpeedNoChamberMultiplier = new SimpleConfigDouble(serverConfig, "warp_speed_no_chamber_multiplier", 1.0d, "Max warpspace flight speed multiplier when no warp flight chamber is installed.");
		warpSpeedChamberLvl1Multiplier = new SimpleConfigDouble(serverConfig, "warp_speed_chamber_lvl_1_multiplier", 1.3d, "Max warpspace flight speed multiplier for warp flight chamber level 1.");
		warpSpeedChamberLvl2Multiplier = new SimpleConfigDouble(serverConfig, "warp_speed_chamber_lvl_2_multiplier", 1.6d, "Max warpspace flight speed multiplier for warp flight chamber level 2.");
		warpSpeedChamberLvl3Multiplier = new SimpleConfigDouble(serverConfig, "warp_speed_chamber_lvl_3_multiplier", 2.0d, "Max warpspace flight speed multiplier for warp flight chamber level 3.");
		warpBeaconChamberPercent = new SimpleConfigDouble(serverConfig, "warp_beacon_chamber_percent", 0.25d, "Fraction of the reactor tree consumed by the beacon chamber.");

		sfxEffectsEnable = new SimpleConfigBool(clientConfig, "sfx_effects_enable", true, "Enable warp sound effects.");
		vfxUseWarpShader = new SimpleConfigBool(clientConfig, "vfx_use_warp_shader", true, "Enable the colorful shader in warpspace. Disable if your GPU can't handle it.");

		mapDrawDropPointsRange = new SimpleConfigDouble(clientConfig, "map_draw_droppoints_range", 1.0d, "Draw drop points on the realspace map for warp sectors within this distance.");
	}

	// --- server-authoritative getters ---------------------------------------

	public static boolean isAstronautDropKillSwitchOn() {
		return killSwitchAstronautDrop.getValue();
	}

	public static boolean isSpeedDropKillSwitchOn() {
		return killSwitchSpeedDrop.getValue();
	}

	public static boolean isWarpBeaconDisabledOnHomebase() {
		return warpBeaconDisableOnHomebase.getValue();
	}

	public static double getDropPointRandomOffset() {
		return clamp(dropPointRandomOffset, 0.0d, LARGE_MAX);
	}

	public static int getWarpToRspRatio() {
		return (int) clamp(warpToRspRatio, 2.0d, LARGE_MAX);
	}

	public static double getSecondsUntilSpeedDrop() {
		return clamp(secondsUntilSpeedDrop, 0.0d, LARGE_MAX);
	}

	public static double getMinimumWarpSpeed() {
		return clamp(minimumWarpSpeed, 0.0d, LARGE_MAX);
	}

	public static double getSecondsWarpJumpDelay() {
		return clamp(secondsWarpJumpDelay, 9.5d, LARGE_MAX);
	}

	public static double getWarpSpeedNoChamberMultiplier() {
		return clamp(warpSpeedNoChamberMultiplier, 0.0d, 1000.0d);
	}

	public static double getWarpSpeedChamberLvl1Multiplier() {
		return clamp(warpSpeedChamberLvl1Multiplier, 0.0d, 1000.0d);
	}

	public static double getWarpSpeedChamberLvl2Multiplier() {
		return clamp(warpSpeedChamberLvl2Multiplier, 0.0d, 1000.0d);
	}

	public static double getWarpSpeedChamberLvl3Multiplier() {
		return clamp(warpSpeedChamberLvl3Multiplier, 0.0d, 1000.0d);
	}

	public static double getWarpBeaconChamberPercent() {
		return clamp(warpBeaconChamberPercent, 0.0d, 1.0d);
	}

	// --- client-local getters -----------------------------------------------

	public static boolean isSfxEffectsEnabled() {
		return sfxEffectsEnable.getValue();
	}

	public static boolean isWarpShaderEnabled() {
		return vfxUseWarpShader.getValue();
	}

	public static int getMapDrawDropPointsRange() {
		return (int) clamp(mapDrawDropPointsRange, 0.0d, 10.0d);
	}

	private static double clamp(SimpleConfigDouble entry, double min, double max) {
		double raw = entry.getValue();
		if(raw < min) {
			return min;
		}
		return Math.min(raw, max);
	}
}
