package warpspace.mixin;

import org.schema.common.util.linAlg.Vector3i;
import org.schema.game.client.data.ClientGameData;
import org.schema.game.client.data.GameClientState;
import org.schema.schine.common.language.Lng;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import warpspace.core.WarpManager;

/**
 * Waypoint logic patches for {@link ClientGameData}: when the player is in
 * warpspace, convert waypoint coordinates between warpspace and realspace so
 * that nav arrows keep pointing at the intended destination regardless of
 * which dimension the player is currently in.
 *
 * <p>Replaces the pre-mixin {@code org/schema/game/client/data/ClientGameData.java}
 * class overwrite.
 */
@Mixin(value = ClientGameData.class, remap = false)
public abstract class ClientGameDataMixin {

	@Shadow
	@Final
	private GameClientState state;

	@Shadow
	private Vector3i waypoint;

	@Shadow
	private Vector3i nearestToWayPoint;
	/**
	 * Cached warpspace-equivalent of the current waypoint. Mod-introduced.
	 */
	@Unique
	private Vector3i warpspace$warpWP = new Vector3i();
	/**
	 * Scratch buffer for the current player sector during {@link #warpspace$getWaypoint}. Mod-introduced.
	 */
	@Unique
	private final Vector3i warpspace$playerPosTmp = new Vector3i();

	@Shadow
	public abstract void updateNearest(int currentSectorId);

	@Inject(method = "getWaypoint()Lorg/schema/common/util/linAlg/Vector3i;", at = @At("HEAD"), cancellable = true)
	private void warpspace$getWaypoint(CallbackInfoReturnable<Vector3i> cir) {
		warpspace$playerPosTmp.set(GameClientState.instance.getPlayer().getCurrentSector());

		// Player reached the warpspace equivalent of their waypoint: clear it so the nav arrow stops.
		if(waypoint != null && warpspace$playerPosTmp.equals(warpspace$warpWP)) {
			GameClientState.instance.message(Lng.astr("dropout point reached."), 3);
			cir.setReturnValue(null);
			return;
		}

		// While in warp, return the warpspace-projected waypoint instead of the realspace one.
		if(waypoint != null && WarpManager.isInWarp(warpspace$playerPosTmp)) {
			cir.setReturnValue(warpspace$warpWP);
		}
		// otherwise fall through to vanilla body (returns this.waypoint)
	}

	@Inject(method = "setWaypoint(Lorg/schema/common/util/linAlg/Vector3i;)V", at = @At("HEAD"), cancellable = true)
	private void warpspace$setWaypoint(Vector3i newWaypoint, CallbackInfo ci) {
		// Never allow a raw warpspace waypoint to be stored — always convert to realspace first.
		if(WarpManager.isInWarp(newWaypoint)) {
			newWaypoint = WarpManager.getRealSpacePos(newWaypoint);
		}

		waypoint = newWaypoint;
		if(waypoint != null) {
			warpspace$warpWP = WarpManager.getWarpSpacePos(waypoint);
		}
		nearestToWayPoint = null;
		updateNearest(state.getCurrentSectorId());
		ci.cancel();
	}
}
