package tilemap;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    /**
     * Loads a .map file and parses all information such as tile types and
     * dimensions.
     * @param filename name of the .map file
     */
    public void loadMap(String filename) {

        try {

            InputStream inputStream = getClass().getResourceAsStream(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // set dimensions from first two rows
            numCols = Integer.parseInt(bufferedReader.readLine());
            numRows = Integer.parseInt(bufferedReader.readLine());
            map = new int[numRows][numCols];
            width = numCols*tileSize;
            height = numRows*tileSize;

            String delimiters = "\\s+";
            // get sub-arrays as array of token
            for (int row = 0; row < numRows; row++) {
                String line = bufferedReader.readLine();
                String tokens[] = line.split(delimiters);

                // get value for every coordinate
                for(int col =  0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the type of a tile in the graphical array.
     * @param row x-position of tile
     * @param col y-position of tile
     * @return  type of the tile
     */
    public int getType(int row, int col) {
        int value = map[row][col];
        int r = value/numTilesAcross;
        int c = value%numTilesAcross;
        return tiles[r][c].getType();
    }

    /**
     * Sets the player's position in the map and adds a smooth scrolling effect.
     * Also, the offsets of row and column are calculated.
     * @param xPos new x-position
     * @param yPos new y-position
     */
    public void setPosition(double xPos, double yPos) {
        this.xPos += (xPos - this.xPos)*tween;
        this.yPos += (yPos - this.yPos)*tween;

        fixBounds();
        colOffset = (int)-this.xPos/tileSize;
        rowOffset = (int)-this.yPos/tileSize;
    }

    /**
     * Makes sure the bounds of the screen are not crossed.
     */
    public void fixBounds() {
        if (xPos < xMin) {
            xPos = xMin;
        }
        if (yPos < yMin) {
            yPos = yMin;
        }
        if (xPos > xMax) {
            xPos = xMax;
        }
        if (yPos > yMax) {
            yPos = yMax;
        }
    }

    /**
     * Draws all components of the map.
     * @param graphics2D graphics to be drawn
     */
    public void draw(Graphics2D graphics2D) {
        for (int row = rowOffset; row < rowOffset+numRowsToDraw; row++) {

            if (row >= numRows) {
                break;
            }

            for (int col = colOffset; col < colOffset+numColsToDraw; col++) {

                if (col >= numCols) {
                    break;
                }

                if (map[row][col] == 0) {
                    continue;
                }

                int value = map[row][col];
                int r = value/numTilesAcross;
                int c = value%numTilesAcross;

                graphics2D.drawImage(tiles[r][c].getImage(), (int)xPos+col*tileSize, (int)yPos+row*tileSize, null);
            }
        }
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTileSize() {
        return tileSize;
    }
}
