package tilemap;

import java.awt.image.BufferedImage;

/**
 * Represents a single tile of the map with type and graphic.
 */
public class Tile {

    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    /**
     * Tile graphic.
     */
    private BufferedImage image;
    /**
     * Type of the tile.
     */
    private int type;

    public Tile(BufferedImage image, int type) {
        this.image = image;
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getType() {
        return type;
    }
}
