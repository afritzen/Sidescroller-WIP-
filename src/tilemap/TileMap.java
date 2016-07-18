package tilemap;

import main.GamePanel;

import javax.imageio.ImageIO;
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
    private int width;
    private int numCols;
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

    /**
     * Loads a tileset image and fills the map with different tile types.
     * @param filename file that holds the tileset image
     */
    public void loadTiles(String filename) {

        try {
            tileset = ImageIO.read(getClass().getResourceAsStream(filename));
            numTilesAcross = tileset.getWidth()/tileSize;
            // new 2d array with two rows to store the map
            tiles = new Tile[2][numTilesAcross];

            // current subimage of tileset graphic
            BufferedImage subImage;

            // create two rows and fill them with subimages from the tileset
            for (int col = 0; col < numTilesAcross; col++) {
                subImage = tileset.getSubimage(col*tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subImage, Tile.NORMAL);
                subImage = tileset.getSubimage(col*tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
