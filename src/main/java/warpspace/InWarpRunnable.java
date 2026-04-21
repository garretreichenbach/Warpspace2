package warpspace;

import org.schema.game.common.controller.Ship;
import org.schema.game.common.data.player.AbstractCharacter;
import org.schema.game.common.data.world.SimpleTransformableSendableObject;
import warpspace.client.WarpProcess;
import warpspace.manager.ConfigManager;

/**
 * STARMADE MOD
 * CREATOR: Max1M
 * DATE: 30.09.2022
 * TIME: 14:45
 */
public class InWarpRunnable extends TimedRunnable {
	private final int entityId;
	private SimpleTransformableSendableObject<?> entity;
	private float countdown_millis = countdownMax();

	public InWarpRunnable(SimpleTransformableSendableObject<?> entity) {
		super(1000, WarpMain.instance, -1);
		entityId = entity.getId();
		this.entity = entity;
	}

	private static int countdownMax() {
		return (int) (ConfigManager.getSecondsUntilSpeeddrop() * 1000);
	}

	@Override
	public void onRun() {
		super.onRun();
		updateEntity();
		if(entity != null) {
			//skip attached players
			if(entity instanceof AbstractCharacter && entity.getGravity().source != null) {
				return;
			}

			//skip docked ships
			if(entity instanceof Ship && !((Ship) entity).railController.isRoot()) {
				return; //Object is docked to something else
			}

			//update object
			if(WarpManager.isInWarp(entity)) {
				updateWarp();
			} else {
				updateRSP();
			}
		} else {
			WarpEntityManager.RemoveWarpEntity(entityId);
		}
	}

	private void updateEntity() {
		if(entity != null && entity.isMarkedForDeleteVolatile()) entity = null;
	}

	private void updateWarp() {
		//update value for synching
		int max = countdownMax();
		int stability = (int) ((100 * countdown_millis) / max);
		WarpProcess.setProcess(entity, WarpProcess.WARP_STABILITY, stability);

		if(entity.getSpeedCurrent() < ConfigManager.getMinimumWarpSpeed()) {
			//ship is to slow, dropping out of warp!
			countdown_millis -= getTimeout(); //runs once a second
		} else {
			if(countdown_millis < max) {
				countdown_millis += 2 * getTimeout();
			}
		}

		if(countdown_millis > max) { //essentially caps the countdown to max_val, while allowing a start buffer of extra seconds
			countdown_millis -= getTimeout();
		}
		if(countdown_millis <= 0) {
			//drop entity out of warp.
			System.out.println("dropping entity" + entity.getUniqueIdentifier() + " type " + entity.getClass().getName() + " CAUSE: SPEED_DROP");
			WarpJumpManager.invokeDrop(0, entity, false, false);
		}
	}

	@Override
	public void doStop() {
		super.doStop();
	}

	private void updateRSP() {
		//set to full, so that entering warp starts with f
		WarpProcess.setProcess(entity, WarpProcess.WARP_STABILITY, 100);
		countdown_millis = countdownMax();
	}
}
