package warpspace.listener;

import api.listener.Listener;
import api.listener.events.entity.ShipJumpEngageEvent;
import api.mod.StarLoader;
import warpspace.WarpJumpManager;
import warpspace.WarpMain;

/**
 * Detects vanilla FTL jumps, cancels them, and instead queues a warp entry
 * or drop depending on the ship's current dimension.
 */
public final class WarpJumpListener {

	private WarpJumpListener() {
	}

	public static void register() {
		StarLoader.registerListener(ShipJumpEngageEvent.class, new Listener<ShipJumpEngageEvent>() {
			@Override
			public void onEvent(ShipJumpEngageEvent event) {
				event.setCanceled(true);
				WarpJumpManager.invokeJumpdriveUsed(event.getController(), false);
			}
		}, WarpMain.getInstance());
	}
}
