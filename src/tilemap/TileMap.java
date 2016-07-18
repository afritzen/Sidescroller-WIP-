package tilemap;

import main.GamePanel;

import java.awt.image.BufferedImage;

/**
 * Models a map in the game with an array of tiles and
 * the bare map itself.
 */
public class TileMap {

    private double xPos;
    private double yPos;

    /**
     * Minimum x-axis bound.
     */
    private int xMin;
    /**
     * Minimum y-axis bound.
     */
    private int yMin;
    /**
     * Maximum x-axis bound.
     */
    private int xMax;
    /**
     * Maximum y-axis bound.
     */
    private int yMax;
    /**
     * Bare map.
     */
    private int[][] map;
    /**
     * Size of a single tile.
     */
    private int tileSize;
    /**
     * Amount of rows.
     */
    private int numRows;
    /**
     * Amount of columns.
     */
    private int numCols;
    private int width;
    private int height;
    /**
     * For smooth scrolling.
     */
    private double tween;

    /**
     * Graphical components.
     */
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    /**
     * Offset for rows, so only needed rows are drawn.
     */
    private int rowOffset;
    /**
     * Offset for columns, so only needed columns are drawn.
     */
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    /**
     * Constructor for map, initializes tileSize and tween.
     * @param tileSize size for every tile of the map
     */
    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT/tileSize+2;
        numColsToDraw = GamePanel.WIDTH/tileSize+2;
        tween = 0.07;
    }

}
