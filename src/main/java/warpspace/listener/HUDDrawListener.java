package warpspace.listener;

import api.listener.Listener;
import api.listener.events.gui.HudCreateEvent;
import api.mod.StarLoader;
import warpspace.WarpMain;
import warpspace.client.HUD_core;

/**
 * Registers all warpspace HUD elements on the client when the HUD is
 * (re)created by the engine.
 */
public final class HUDDrawListener {

	private HUDDrawListener() {
	}

	public static void register() {
		StarLoader.registerListener(HudCreateEvent.class, new Listener<HudCreateEvent>() {
			@Override
			public void onEvent(HudCreateEvent hudCreateEvent) {
				HUD_core.onHUDCreated(hudCreateEvent);
			}
		}, WarpMain.getInstance());
	}
}
