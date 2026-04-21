package warpspace.manager;

import warpspace.WarpMain;
import warpspace.WarpThrusterListener;
import warpspace.beacon.WarpBeaconAddon;
import warpspace.client.GUIeventhandler;
import warpspace.server.WarpJumpListener;

/**
 * Central registry for all event listeners used by the mod.
 * Listener registration is split by lifecycle phase so each hook in
 * {@link WarpMain} delegates to a single entry point here.
 *
 * <p>Many listeners are currently implemented as self-registering static
 * methods scattered across subpackages. This manager is the single index
 * that points to all of them; future phases will inline the listener
 * bodies here.
 */
public final class EventManager {

    private EventManager() {
    }

    /**
     * Listeners shared between client and server, registered on mod enable.
     */
    public static void registerCommon(WarpMain mod) {
        WarpBeaconAddon.registerAddonAddEventListener();
        mod.warpThrusterListener = new WarpThrusterListener(mod);
    }

    /**
     * Listeners that require the server to be initialized.
     */
    public static void registerServer() {
        WarpJumpListener.createListener();
    }

    /**
     * Listeners that require the client to be initialized.
     */
    public static void registerClient() {
        GUIeventhandler.addHUDDrawListener();
    }
}
