package warpspace.manager;

import warpspace.Interdiction.ExtraEventLoop;
import warpspace.client.HUD_core;
import warpspace.client.WarpProcess;
import warpspace.server.WarpCheckLoop;

/**
 * Central registry for all timed update loops used by the mod.
 * Each hook in {@link warpspace.WarpMain} delegates to a single entry
 * point here so every running loop is visible in one place.
 */
public final class LoopManager {

    private LoopManager() {
    }

    public static void startServerLoops() {
        WarpCheckLoop.loop();
        ExtraEventLoop.CreateServerLoop();
    }

    public static void startClientLoops() {
        WarpProcess.initUpdateLoop();
        HUD_core.HUDLoop();
    }
}
