package warpspace.core;

import api.listener.events.Event;
import org.schema.common.util.linAlg.Vector3i;
import org.schema.game.common.data.world.SimpleTransformableSendableObject;
import org.schema.schine.common.language.Lng;
import org.schema.schine.network.server.ServerMessage;
import warpspace.WarpMain;

/**
 * mod-owned event
 * fired whenever an entity enters or leaves warp
 */
public class WarpJumpEvent extends Event {
	private final SimpleTransformableSendableObject<?> ship;

	private final WarpJumpType type;

	private final Vector3i start;

	private final Vector3i end;

	/**
	 * constructor
	 *
	 * @param ship  ship
	 * @param type  jumptype
	 * @param start start sector
	 * @param end   end sector
	 */
	public WarpJumpEvent(SimpleTransformableSendableObject<?> ship, WarpJumpType type, Vector3i start, Vector3i end) {
		this.ship = ship;
		this.start = start;
		this.end = end;
		this.type = type;
		WarpMain.getInstance().logInfo("warpspace performed jump for " + ship.getName() + " start: " + start + " end " + end + " of type " + type);
	}

	/**
	 * get segmentcontroller that is warping
	 *
	 * @return ship
	 */
	public SimpleTransformableSendableObject<?> getShip() {
		return ship;
	}

	/**
	 * get type of warpevent
	 *
	 * @return type
	 */
	public WarpJumpType getType() {
		return type;
	}

	/**
	 * get origin sector of jump
	 *
	 * @return sector
	 */
	public Vector3i getStart() {
		return start;
	}

	/**
	 * get target sector of jump
	 *
	 * @return sector
	 */
	public Vector3i getEnd() {
		return end;
	}

	/**
	 * NOT IN USE; WARPJUMPS CAN NOT BE ABORTED BY PLAYER
	 * cancel warpevent
	 *
	 * @param pilotMessage message to be displayed to pilots for cause of failed warp
	 */
	public void cancel(String pilotMessage) {
		//display string to pilots
		ship.sendControllingPlayersServerMessage(Lng.astr(pilotMessage), ServerMessage.MESSAGE_TYPE_WARNING);
		setCanceled(true);
	}

	public String toString() {
		return "WarpJumpEvent," + type + "from: " + start + "to " + end + "for" + ship.getName() + "at " + System.currentTimeMillis();
	}

	public enum WarpJumpType {
		ENTRY,
		DROP,
		EXIT,
		IN_WARP,
		TRANSWARP
	}
}
