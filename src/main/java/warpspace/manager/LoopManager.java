package warpspace.manager;

import warpspace.WarpSpace;
import warpspace.client.WarpProcess;
import warpspace.client.hud.HUDCore;
import warpspace.server.WarpCheckLoop;

/**
 * Central registry for all timed update loops used by the mod.
 * Each hook in {@link WarpSpace} delegates to a single entry
 * point here so every running loop is visible in one place.
 */
public final class LoopManager {

    private LoopManager() {
    }

    public static void startServerLoops() {
        WarpCheckLoop.loop();
        WarpProcess.initUpdateLoop();
    }

	public static void startClientLoops() {
        HUDCore.HUDLoop();
    }
}
