package warpspace.server;

import org.schema.common.util.linAlg.Vector3i;
import org.schema.game.common.controller.SegmentController;
import org.schema.game.common.controller.Ship;
import org.schema.game.common.data.player.PlayerState;
import org.schema.game.common.data.world.SimpleTransformableSendableObject;
import warpspace.client.WarpProcess;
import warpspace.core.WarpJumpManager;
import warpspace.core.WarpManager;

/**
 * Server-side helpers that recompute {@link WarpProcess} flags which can't
 * be driven purely by game events &mdash; specifically interdiction status
 * (changes based on nearby inhibitors) and shifted-droppoint status
 * (changes when beacons toggle).
 *
 * <p>{@link #updatePlayer(PlayerState)} is invoked once per HUD-sync tick
 * from {@code WarpProcess.preSynchServer}.
 */
public final class WarpStateUpdater {

	private WarpStateUpdater() {
	}

	public static void updatePlayer(PlayerState player) {
		SimpleTransformableSendableObject<?> controlled = player.getFirstControlledTransformableWOExc();
		if(!(controlled instanceof Ship ship)) {
			return;
		}

		Vector3i playerPos = ship.getSector(new Vector3i());
		Vector3i rspPos = WarpManager.getRealSpacePos(playerPos);
		Vector3i warpPos = WarpManager.getWarpSpacePos(playerPos);

		updateInterdiction(warpPos, rspPos, player, ship);
		updateBeacons(warpPos, player);
	}

	private static void updateBeacons(Vector3i warpPos, PlayerState p) {
		WarpProcess.setProcess(p, WarpProcess.DROPPOINTSHIFTED, WarpJumpManager.isDroppointShifted(warpPos) ? 1 : 0);
	}

	private static void updateInterdiction(Vector3i warpPos, Vector3i rspPos, PlayerState playerState, SegmentController ship) {
		boolean warpInterdicted = WarpJumpManager.isInterdicted(ship, warpPos);
		boolean rspInterdicted = WarpJumpManager.isInterdicted(ship, rspPos);
		WarpProcess.setProcess(playerState, WarpProcess.WARPSECTORBLOCKED, warpInterdicted ? 1 : 0);
		WarpProcess.setProcess(playerState, WarpProcess.RSPSECTORBLOCKED, rspInterdicted ? 1 : 0);
	}
}
