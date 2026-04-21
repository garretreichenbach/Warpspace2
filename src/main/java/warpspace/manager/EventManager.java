package warpspace.manager;

import warpspace.beacon.WarpBeaconAddon;
import warpspace.listener.HUDDrawListener;
import warpspace.listener.WarpJumpListener;
import warpspace.listener.WarpThrusterListener;

/**
 * Central registry for all event listeners used by the mod.
 * Listener registration is split by lifecycle phase so each hook in
 * {@link warpspace.WarpMain} delegates to a single entry point here.
 *
 * <p>Some listeners are still owned by their subsystems (beacon, map, HUD
 * loop) and will be inlined here when those subsystems are restructured.
 */
public final class EventManager {

    private EventManager() {
    }

    /**
     * Listeners shared between client and server, registered on mod enable.
     */
    public static void registerCommon() {
        WarpBeaconAddon.registerAddonAddEventListener();
        WarpThrusterListener.register();
    }

    /**
     * Listeners that require the server to be initialized.
     */
    public static void registerServer() {
        WarpJumpListener.register();
    }

    /**
     * Listeners that require the client to be initialized.
     */
    public static void registerClient() {
        HUDDrawListener.register();
    }
}
