package warpspace.client.map.util;

import org.schema.game.client.view.gamemap.GameMapDrawer;
import org.schema.schine.graphicsengine.forms.PositionableSubColorSprite;
import org.schema.schine.graphicsengine.forms.SelectableSprite;
import org.schema.schine.graphicsengine.forms.Sprite;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class SimpleMapMarker implements PositionableSubColorSprite, SelectableSprite {
    private int subSprite;
    private Sprite sprite;
    private Vector4f color;
    private Vector3f pos;
    private float scale;

    public SimpleMapMarker(Sprite sprite, int subSprite, Vector4f color, Vector3f pos) {
        this.sprite = sprite;
        this.subSprite = subSprite;
        this.color = color;
        this.pos = pos;
        this.scale = 1.0f;
    }

    @Override
    public Vector4f getColor() {
        return color;
    }

    @Override
    public float getScale(long l) {
        return scale;
    }

    @Override
    public int getSubSprite(Sprite sprite) {
        return subSprite;
    }

    @Override
    public boolean canDraw() {
        return true;
    }

    public void preDraw(GameMapDrawer drawer) {
    }

    @Override
    public Vector3f getPos() {
        return pos;
    }

    public int getSubSprite() {
        return subSprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public float getSelectionDepth() {
        return 0;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public void onSelect(float v) {
    }

    @Override
    public void onUnSelect() {
    }
}
