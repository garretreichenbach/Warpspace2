package warpspace.listener;

import api.listener.fastevents.FastListenerCommon;
import api.listener.fastevents.ThrusterElementManagerListener;
import api.utils.game.SegmentControllerUtils;
import org.schema.game.common.controller.ManagedUsableSegmentController;
import org.schema.game.common.controller.SegmentController;
import org.schema.game.common.controller.elements.power.reactor.tree.ReactorElement;
import org.schema.game.common.controller.elements.thrust.ThrusterElementManager;
import org.schema.game.common.data.element.ElementInformation;
import org.schema.game.common.data.element.ElementKeyMap;
import warpspace.WarpManager;
import warpspace.manager.ConfigManager;

import javax.vecmath.Vector3f;

/**
 * Modifies max ship speed while in warpspace based on the installed warp
 * flight chamber level.
 */
public final class WarpThrusterListener implements ThrusterElementManagerListener {

	private WarpThrusterListener() {
	}

	public static void register() {
		FastListenerCommon.thrusterElementManagerListeners.add(new WarpThrusterListener());
	}

	@Override
	public void instantiate(ThrusterElementManager thrusterElementManager) {
	}

	@Override
	public float getSingleThrust(ThrusterElementManager thrusterElementManager, float v) {
		return v;
	}

	@Override
	public float getSharedThrust(ThrusterElementManager thrusterElementManager, float v) {
		return v;
	}

	@Override
	public float getThrustMassRatio(ThrusterElementManager thrusterElementManager, float v) {
		return v;
	}

	@Override
	public float getMaxSpeed(ThrusterElementManager thrusterElementManager, float v) {
		SegmentController sc = thrusterElementManager.getSegmentController();
		if(!WarpManager.isInWarp(sc)) {
			return v;
		}
		if(!(sc instanceof ManagedUsableSegmentController<?> musc)) {
			return v;
		}

		ElementInformation jumpDist1 = ElementKeyMap.getInfo(119);
		ElementInformation jumpDist2 = ElementKeyMap.getInfo(118);
		ElementInformation jumpDist3 = ElementKeyMap.getInfo(117);

		ReactorElement jd1Chamber = SegmentControllerUtils.getChamberFromElement(musc, jumpDist1);
		ReactorElement jd2Chamber = SegmentControllerUtils.getChamberFromElement(musc, jumpDist2);
		ReactorElement jd3Chamber = SegmentControllerUtils.getChamberFromElement(musc, jumpDist3);
		if(jd3Chamber != null && jd3Chamber.isAllValid()) {
			return (float) (v * ConfigManager.getWarpSpeedChamberLvl3Multiplier());
		}
		if(jd2Chamber != null && jd2Chamber.isAllValid()) {
			return (float) (v * ConfigManager.getWarpSpeedChamberLvl2Multiplier());
		}
		if(jd1Chamber != null && jd1Chamber.isAllValid()) {
			return (float) (v * ConfigManager.getWarpSpeedChamberLvl1Multiplier());
		}
		return (float) (v * ConfigManager.getWarpSpeedNoChamberMultiplier());
	}

	@Override
	public float getMaxSpeedAbsolute(ThrusterElementManager thrusterElementManager, float v) {
		return v;
	}

	@Override
	public Vector3f getOrientationPower(ThrusterElementManager thrusterElementManager, Vector3f vector3f) {
		return vector3f;
	}

	@Override
	public void handle(ThrusterElementManager thrusterElementManager) {
	}

	@Override
	public double getPowerConsumptionResting(ThrusterElementManager thrusterElementManager, double v) {
		return v;
	}

	@Override
	public double getPowerConsumptionCharging(ThrusterElementManager thrusterElementManager, double v) {
		return v;
	}
}
