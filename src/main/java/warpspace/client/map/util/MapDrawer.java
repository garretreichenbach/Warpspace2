package warpspace.client.map.util;

import api.listener.Listener;
import api.listener.events.input.MousePressEvent;
import api.listener.fastevents.FastListenerCommon;
import api.listener.fastevents.GameMapDrawListener;
import api.mod.StarLoader;
import api.mod.StarMod;
import org.schema.common.util.linAlg.Vector3i;
import org.schema.game.client.view.gamemap.GameMapDrawer;
import org.schema.game.common.data.world.VoidSystem;
import org.schema.schine.graphicsengine.forms.Sprite;
import org.schema.schine.graphicsengine.forms.gui.GUIAncor;
import org.schema.schine.graphicsengine.forms.gui.GUIElement;
import warpspace.WarpSpace;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Efficient replacement for libpackage.drawer.MapDrawer.
 * Key optimizations:
 * - No synchronized blocks (rendering is single-threaded)
 * - IdentityHashMap keyed on Sprite reference for O(1) grouping without hash allocation
 * - Cached arrays only rebuilt when dirty
 * - clearMarkers() is O(1) instead of O(n)
 */
public class MapDrawer implements GameMapDrawListener {
    private static final float sectorScale = 100f / VoidSystem.SYSTEM_SIZE;
    private static final Vector3f halfSectorOffset = new Vector3f(sectorScale / 2f, sectorScale / 2f, sectorScale / 2f);

    private final ArrayList<SimpleMapMarker> markers = new ArrayList<>();
    private final IdentityHashMap<Sprite, SpriteGroup> spriteGroups = new IdentityHashMap<>();
    private final ArrayList<GUIElement> guis = new ArrayList<>();
    private boolean dirty = true;
    public SimpleMapMarker selected;

    public MapDrawer(StarMod mod) {
        FastListenerCommon.gameMapListeners.add(this);
        StarLoader.registerListener(MousePressEvent.class, new Listener<MousePressEvent>() {
            @Override
            public void onEvent(MousePressEvent event) {
            }
        }, mod);
    }

    public void addMarker(SimpleMapMarker marker) {
        markers.add(marker);
        dirty = true;
    }

    public void removeMarker(SimpleMapMarker marker) {
        markers.remove(marker);
        dirty = true;
    }

    public void clearMarkers() {
        markers.clear();
        spriteGroups.clear();
        dirty = true;
    }

    public void rebuildInternalList() {
        if (!dirty) return;
        dirty = false;

        spriteGroups.clear();
        for (int i = 0, n = markers.size(); i < n; i++) {
            SimpleMapMarker marker = markers.get(i);
            Sprite sprite = marker.getSprite();
            if (sprite == null) continue;

            SpriteGroup group = spriteGroups.get(sprite);
            if (group == null) {
                group = new SpriteGroup(sprite);
                spriteGroups.put(sprite, group);
            }
            group.add(marker);
        }

        for (SpriteGroup group : spriteGroups.values()) {
            group.buildArray();
        }
    }

    @Override
    public void system_PreDraw(GameMapDrawer drawer, Vector3i pos, boolean explored) {
        if (dirty) {
            try {
                rebuildInternalList();
            } catch (Exception e) {
                WarpSpace.getInstance().logException("MapDrawer rebuild failed", e);
            }
            dirty = false;
        }
    }

    @Override
    public void system_PostDraw(GameMapDrawer drawer, Vector3i pos, boolean explored) {
    }

    @Override
    public void galaxy_PreDraw(GameMapDrawer drawer) {
    }

    @Override
    public void galaxy_PostDraw(GameMapDrawer drawer) {
    }

    @Override
    public void galaxy_DrawLines(GameMapDrawer drawer) {
    }

    @Override
    public void galaxy_DrawSprites(GameMapDrawer drawer) {
        for (SpriteGroup group : spriteGroups.values()) {
            SimpleMapMarker[] arr = group.array;
            for (int i = 0; i < arr.length; i++) {
                arr[i].preDraw(drawer);
            }
            GameMapDrawListener.DrawUtils.drawSprite(drawer, group.sprite, arr);
        }

        for (int i = 0, n = guis.size(); i < n; i++) {
            GUIElement gui = guis.get(i);
            gui.draw();
        }
    }

    @Override
    public void galaxy_DrawQuads(GameMapDrawer drawer) {
    }

    public void addGUIAncor(GUIAncor ancor) {
        guis.add(ancor);
    }

    public void removeGUIAncor(GUIAncor ancor) {
        guis.remove(ancor);
    }

    private static final Vector3f spriteOffset = new Vector3f(
            -VoidSystem.SYSTEM_SIZE_HALF, -VoidSystem.SYSTEM_SIZE_HALF, -VoidSystem.SYSTEM_SIZE_HALF);

    public static Vector3f posFromSector(Vector3i sector, boolean isSprite) {
        Vector3f out = sector.toVector3f();
        if (isSprite) {
            out.add(spriteOffset);
        }
        out.scale(sectorScale);
        out.add(halfSectorOffset);
        return out;
    }

    private static class SpriteGroup {
        final Sprite sprite;
        private final ArrayList<SimpleMapMarker> list = new ArrayList<>();
        SimpleMapMarker[] array;

        SpriteGroup(Sprite sprite) {
            this.sprite = sprite;
        }

        void add(SimpleMapMarker marker) {
            list.add(marker);
        }

        void buildArray() {
            array = list.toArray(new SimpleMapMarker[0]);
        }
    }
}
