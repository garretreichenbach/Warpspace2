package warpspace.client.hud;

import api.listener.Listener;
import api.listener.events.gui.HudCreateEvent;
import api.mod.StarLoader;
import api.utils.StarRunnable;
import org.schema.common.util.linAlg.Vector3i;
import org.schema.game.client.data.GameClientState;
import org.schema.game.client.view.gui.shiphud.HudIndicatorOverlay;
import org.schema.game.common.data.player.PlayerState;
import org.schema.game.common.data.world.SimpleTransformableSendableObject;
import org.schema.game.server.data.GameServerState;
import org.schema.schine.graphicsengine.forms.font.FontLibrary;
import org.schema.schine.graphicsengine.forms.gui.GUITextOverlay;
import warpspace.WarpMain;
import warpspace.client.WarpProcess;
import warpspace.core.WarpJumpManager;
import warpspace.core.WarpManager;
import warpspace.util.TimedRunnable;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HUDCore {

	public static List<HUDElement> elementList = new ArrayList<>();
	public static HashMap<SpriteList, Integer> drawList = new HashMap<>();
	/**
	 * some general HUD element placements to use a position references. any element built with these will move + scale with them
	 */
	public static HUDElement console = new HUDElement(
			new Vector3f((float) 1700 / 1920, (float) 1000 / 1080, 0f),
			new Vector3f((float) 0.75 / 1080, (float) 0.75 / 1080, (float) 1 / 1080),
			new Vector3f(1, 1, 1),
			SpriteList.PEARL,
			HUDElement.ElementType.BACKGROUND);

	/**
	 * init method for HUD stuff.
	 */
	public static void HUDLoop() {
		new StarRunnable() {
			PlayerState player = GameClientState.instance.getPlayer();

			int tenthSeconds = 0;
			long lastTime = System.currentTimeMillis();

			@Override
			public void run() {
				if(player == null || player.getCurrentSector() == null) { //nullpointer check to avoid drawing before player spawns.
					player = GameClientState.instance.getPlayer();
				} else {
					if(GameServerState.isShutdown()) {
						cancel();
					}
					updateHUD();

					SimpleTransformableSendableObject playerShip = player.getFirstControlledTransformableWOExc();

					//turn of HUD if player is not controlling a ship
					if(null == playerShip || !playerShip.isSegmentController() || GameClientState.instance.isInAnyBuildMode()) {
						for(HUDElement.ElementType type : HUDElement.ElementType.values()) {
							HUDElementController.drawType(type, 0);
						}
						return;
					}

					//not server situation dependent, 100% passive
					HUDElementController.drawType(HUDElement.ElementType.BACKGROUND, 1);
					HUDElementController.drawElement(SpriteList.SPIRAL, true);

					//situation dependend HUD, imperative
					if(WarpProcess.IS_IN_WARP.isTrue()) {
						HUDElementController.drawElement(SpriteList.PEARL, true);
						HUDElementController.drawElement(SpriteList.ARROW_TO_RSP, true);
					} else {
						HUDElementController.drawElement(SpriteList.ARROW_TO_WARP, true);
						HUDElementController.clearType(HUDElement.ElementType.PEARL);
					}

					boolean queuedForExitJump = WarpProcess.JUMPEXIT.isTrue();
					boolean isSpeedDropping = WarpProcess.WARP_STABILITY.getCurrentValue() < 95 && WarpProcess.WARP_STABILITY.isDecreasing();
					if(
							WarpProcess.IS_IN_WARP.isTrue() &&
									(queuedForExitJump || isSpeedDropping) &&
									((tenthSeconds % 8) <= 4)) {
						//do blinking drop icon
						HUDElementController.drawElement(SpriteList.ARROW_TO_RSP_JUMP, true);
					}

					if(WarpProcess.JUMPENTRY.isTrue() && ((tenthSeconds % 8) <= 4)) {
						HUDElementController.drawElement(SpriteList.ARROW_TO_WARP_JUMP, true);
					}

					if(WarpProcess.WARPSECTORBLOCKED.isTrue()) {
						if(WarpProcess.IS_IN_WARP.isTrue()) { //sector locked down
							HUDElementController.drawElement(SpriteList.PEARL_BLOCKED, true);
							HUDElementController.drawElement(SpriteList.SPIRAL_BLOCKED, true);

						} else { //no jump upwards
							HUDElementController.drawElement(SpriteList.ARROW_TO_WARP_BLOCKED, true);
						}
					}

					if(WarpProcess.RSPSECTORBLOCKED.isTrue()) {
						if(WarpProcess.IS_IN_WARP.isTrue()) {
							HUDElementController.drawElement(SpriteList.ARROW_TO_RSP_BLOCKED, true);
						} else {
							HUDElementController.drawElement(SpriteList.SPIRAL_BLOCKED, true);
						}
					}
				}

				//precise timer handling (not super precise but better than serverticks)
				if(System.currentTimeMillis() - lastTime > 100) {
					//0.1 second passed
					tenthSeconds++;
					lastTime = System.currentTimeMillis();
				}
				if(tenthSeconds > 1000) {
					tenthSeconds = 0;
				}
			}
		}.runTimer(WarpMain.instance, 1);

		StarLoader.registerListener(HudCreateEvent.class, new Listener<HudCreateEvent>() {
			@Override
			public void onEvent(HudCreateEvent hudCreateEvent) {
				new TimedRunnable(500, WarpMain.instance, -1) {
					@Override
					public void onRun() {
						updateVanillaHUD();
					}
				};
				initRadarSectorGUI();
			}
		}, WarpMain.instance);


	}

	/**
	 * Invoked by {@link warpspace.listener.HUDDrawListener} when the engine
	 * (re)creates the HUD. Registers every custom HUD element and its text
	 * overlay with the event so they get drawn.
	 */
	public static void onHUDCreated(HudCreateEvent hudCreateEvent) {
		for(HUDElement el : elementList) {
			hudCreateEvent.addElement(new CustomHudImage(hudCreateEvent.getInputState(), el));
			TextElement textElement = new TextElement(FontLibrary.getBlenderProMedium16(), hudCreateEvent.getInputState());
			el.setTextElement(textElement);
			hudCreateEvent.addElement(textElement);
			textElement.text = "";
		}
	}

	/**
	 * initialize the list of hud elements, add all entries into the drawList.
	 */
	public static void initList() {
		elementList.add(new HUDElement(console, SpriteList.BORDER, HUDElement.ElementType.BACKGROUND));

		elementList.add(new HUDElement(console, SpriteList.SPIRAL, HUDElement.ElementType.SPIRAL));
		elementList.add(new HUDElement(console, SpriteList.SPIRAL_BLOCKED, HUDElement.ElementType.SPIRAL));


		elementList.add(new HUDElement(console, SpriteList.PEARL, HUDElement.ElementType.PEARL));
		elementList.add(new HUDElement(console, SpriteList.PEARL_BLOCKED, HUDElement.ElementType.PEARL));

		elementList.add(new HUDElement(console, SpriteList.ARROW_TO_RSP, HUDElement.ElementType.ARROW));
		elementList.add(new HUDElement(console, SpriteList.ARROW_TO_RSP_JUMP, HUDElement.ElementType.ARROW));
		elementList.add(new HUDElement(console, SpriteList.ARROW_TO_RSP_BLOCKED, HUDElement.ElementType.ARROW));
		elementList.add(new HUDElement(console, SpriteList.ARROW_TO_WARP, HUDElement.ElementType.ARROW));
		elementList.add(new HUDElement(console, SpriteList.ARROW_TO_WARP_BLOCKED, HUDElement.ElementType.ARROW));
		elementList.add(new HUDElement(console, SpriteList.ARROW_TO_WARP_JUMP, HUDElement.ElementType.ARROW));


		for(HUDElement e : elementList) {
			drawList.put(e.enumValue, 0);
		}
	}

	/**
	 * overwrite custom radar if necessary
	 */
	public static void updateHUD() {
		if(GameClientState.instance == null)
			return;
		if(GameClientState.instance.getPlayer().getCurrentSector().length() < 5000 || WarpManager.isInWarp(GameClientState.instance.getPlayer().getCurrentSector()))
			initRadarSectorGUI();
	}

	/**
	 * update neighbour sector names.
	 */
	private static void updateVanillaHUD() {
		if(GameClientState.instance == null || GameClientState.instance.getPlayer() == null)
			return;

		if(!WarpManager.isInWarp(GameClientState.instance.getPlayer().getCurrentSector()))
			return;

		HudIndicatorOverlay overlay = GameClientState.instance.getWorldDrawer().getGuiDrawer().getHud().getIndicator();
		for(int i = 0; i < overlay.neighborSectorsNames.length; i++) {
			overlay.neighborSectorsNames[i] = "[WARP]\n" + WarpManager.getRealSpacePos(overlay.neighborSectorsPos[i]);
		}

	}

	/**
	 * replaces the text of GUIText under radar so that "warp" is shown when in warp.
	 */
	public static void initRadarSectorGUI() {
		try {
			GUITextOverlay sectorPosGUI = GameClientState.instance.getWorldDrawer().getGuiDrawer().getHud().getRadar().getLocation();
			sectorPosGUI.setTextSimple(new Object() {
				@Override
				public String toString() {
					try {
						Vector3i sector = GameClientState.instance.getPlayer().getCurrentSector();
						boolean inWarp = WarpManager.isInWarp(sector);

						//im funny
						if(sector.equals(69, 69, 69))
							return "nice.";
						Vector3i drop = WarpJumpManager.getDropPoint(sector);
						boolean isBeacon = WarpJumpManager.isDroppointShifted(sector);
						return inWarp ? "[WARP]\n" + (isBeacon ? "B " : "") + drop.toStringPure() : sector.toStringPure();
					} catch(Exception e) {
						return "error";
					}
				}
			});
		} catch(NullPointerException ignored) {

		}
	}
}
