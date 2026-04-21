package warpspace.client.map.util;

import api.mod.StarMod;
import api.utils.textures.StarLoaderTexture;
import org.schema.schine.graphicsengine.forms.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class SpriteLoader {
    private final String resourcePath;
    private final String name;
    private Sprite sprite;
    private final int width;
    private final int height;
    private final int cols;
    private final int rows;

    public SpriteLoader(String folder, String name, int width, int height, int cols, int rows) {
        this.resourcePath = folder;
        this.name = name;
        this.width = width;
        this.height = height;
        this.cols = cols;
        this.rows = rows;
    }

    public boolean loadSprite(StarMod mod) {
        try {
            InputStream stream = mod.getJarResource(resourcePath + name);
            BufferedImage img = ImageIO.read(stream);
            sprite = StarLoaderTexture.newSprite(img, mod, name);
            sprite.setHeight(height);
            sprite.setWidth(width);
            sprite.setPositionCenter(true);
            sprite.setMultiSpriteMax(cols, rows);
            return true;
        } catch (Exception e) {
            mod.logException("Failed to load map sprite " + resourcePath + name, e);
            return false;
        }
    }

    public Sprite getSprite() {
        return sprite;
    }
}
