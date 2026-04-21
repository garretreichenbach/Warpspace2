package warpspace.client.map;

import api.listener.fastevents.FastListenerCommon;
import api.mod.StarMod;
import org.schema.common.util.linAlg.Vector3i;
import org.schema.game.client.view.gamemap.GameMapDrawer;
import org.schema.schine.graphicsengine.forms.Sprite;
import warpspace.WarpSpace;
import warpspace.client.map.util.MapDrawer;
import warpspace.client.map.util.SimpleMapMarker;
import warpspace.client.map.util.SpriteLoader;
import warpspace.core.WarpJumpManager;
import warpspace.core.WarpManager;
import warpspace.manager.ConfigManager;

import javax.vecmath.Vector4f;

public class DropPointMapDrawer extends MapDrawer {
	private final Vector3i lastSector = new Vector3i();
	private final Vector4f markerColor = new Vector4f(0, 1, 1, 0.8f);
	private Sprite mapSprite;
	private long nextRefresh;
	private boolean updateFlag;
	private final Vector3i tempPoint = new Vector3i();

	public DropPointMapDrawer(StarMod mod) {
		super(mod);
		FastListenerCommon.gameMapListeners.remove(this);
	}

	public void activate() {
		FastListenerCommon.gameMapListeners.add(this);
	}

	public void flagForUpdate() {
		synchronized(this) {
			updateFlag = true;
		}
	}

	public void loadSprite() {
		synchronized(DropPointMapDrawer.class) {
			SpriteLoader sl = new SpriteLoader("resources/image/", "mapsprite.png", 30, 30, 2, 2);
			sl.loadSprite(WarpSpace.instance);
			mapSprite = sl.getSprite();
		}
	}

	/**
	 * create new markers around selected sector, marking where the dropsectors are.
	 *
	 * @param currentPos
	 */
	private void updateDropMarkers(Vector3i currentPos) {
		if(mapSprite == null) return;
		synchronized(this) {
			//only update if the camera pos has changed.
			Vector3i currentWarpPos = WarpManager.getWarpSpacePos(currentPos);
			if(lastSector.equals(currentWarpPos) && !updateFlag && System.currentTimeMillis() < nextRefresh) return;
			nextRefresh = System.currentTimeMillis() + 2000;
			updateFlag = false;
		}
		lastSector.set(WarpManager.getWarpSpacePos(currentPos));

		clearMarkers();

		if(WarpManager.isInWarp(currentPos)) return;

		Vector3i warpPos = WarpManager.getWarpSpacePos(currentPos);
		Vector3i tempDrop;
		Vector3i tempWarp = new Vector3i();
		int range = ConfigManager.getMapDrawDropPointsRange();
		for(int x = -range; x <= range; x++) {
			for(int y = -range; y <= range; y++) {
				for(int z = -range; z <= range; z++) {
					tempWarp.set(x, y, z);
					if(tempWarp.length() > range) continue;
					tempWarp.add(warpPos);
					tempDrop = WarpJumpManager.getDropPoint(tempWarp);
					boolean dropShift = (WarpJumpManager.isDroppointShifted(tempWarp));
					SimpleMapMarker drop = new SimpleMapMarker(mapSprite, dropShift ? 1 : 0, markerColor, posFromSector(tempDrop, true));
					drop.setScale(0.2f);
					addMarker(drop);
				}
			}
		}
	}

	@Override
	public void galaxy_DrawSprites(GameMapDrawer gameMapDrawer) {
		Vector3i currentCenteredSector = gameMapDrawer.getGameMapPosition().get(tempPoint); //should be current sector
		updateDropMarkers(currentCenteredSector);
		super.galaxy_DrawSprites(gameMapDrawer);
	}
}
