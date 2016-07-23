package entity;

import main.GamePanel;
import tilemap.Tile;
import tilemap.TileMap;

import java.awt.*;

/**
 * Superclass for all objects on the map (player, enemies, projectiles, etc.).
 */
public abstract class MapObject {

    protected TileMap tileMap;
    protected int tileSize;
    protected double xMap;
    protected double yMap;

    protected double xPos;
    protected double yPos;
    protected double dX;
    protected double dY;

    protected int width;
    protected int height;

    protected int collisionBoxHeight;
    protected int collisionBoxWidth;

    protected int currRow;
    protected int currCol;
    protected double xDest;
    protected double yDest;
    protected double xTemp;
    protected double yTemp;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

//    protected Animation animation;
    protected int currentAction;
    protected int previousAction;
    protected boolean facingRight;

    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean ducking;
    protected boolean falling;

    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    public MapObject(TileMap tileMap) {
        this.tileMap = tileMap;
        tileSize = tileMap.getTileSize();
    }

    /**
     * Checks whether two objects are colliding (rectangle-method).
     * @param other other object on the map
     * @return collision?
     */
    public boolean intersects(MapObject other) {
        Rectangle meRect = getRectangle();
        Rectangle otherObjectRect = other.getRectangle();
        return meRect.intersects(otherObjectRect);
    }

    /**
     * Returns a rectangle as hitbox for a map object (needed for collison detection).
     * @return hitbox
     */
    public Rectangle getRectangle() {
            return new Rectangle((int)xPos-collisionBoxWidth, (int)yPos-collisionBoxHeight,
                    collisionBoxWidth, collisionBoxHeight);
    }

    /**
     * Calculates where blocked tiles are for all four corners.
     * @param xPos x-position of the object
     * @param yPos y-position of the object
     */
    public void calculateCorners(double xPos, double yPos) {

        // get tiles at all corners of the object
        int leftTile = (int)(xPos - collisionBoxWidth / 2) / tileSize;
        int rightTile = (int)(xPos + collisionBoxWidth / 2 - 1) / tileSize;
        int topTile = (int)(yPos - collisionBoxHeight / 2) / tileSize;
        int bottomTile = (int)(yPos + collisionBoxHeight / 2 - 1) / tileSize;

        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
            topLeft = topRight = bottomLeft = bottomRight = false;
            return;
        }

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        // set values according to block-types
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }

    /**
     * Checks for blocked paths or floor to give feedback to the player.
     */
    public void checkTileMapCollision() {

        currCol = (int)xPos/tileSize;
        currRow = (int)yPos/tileSize;

        xDest = xPos + dX;
        yDest = yPos + dY;

        xTemp = xPos;
        yTemp = yPos;

        calculateCorners(xPos, yDest);

        if (dY < 0) {
            // object is jumping
            if (topLeft || topRight) {
                // stop jumping
                dY = 0;
                yTemp = currRow*tileSize+collisionBoxHeight/2;

            } else {
                yTemp += dY;
            }
        }

        if (dY > 0) {
            // object is falling
            if (bottomLeft || bottomRight) {
                // stop falling, land
                dY = 0;
                falling = false;
                yTemp = (currRow+1)*tileSize-collisionBoxHeight/2;

            } else {
                yTemp += dY;
            }
        }

        calculateCorners(xDest, yPos);

        if (dX < 0) {
            // walking to the left
            if (topLeft || bottomLeft) {
                // stop right before blocked path
                dX = 0;
                xTemp = currCol*tileSize+collisionBoxWidth/2;

            } else {
                xTemp += dX;
            }
        }

        if (dX > 0) {
            // walking to the right
            if (topRight || bottomRight) {
                // stop right before blocked path
                dX = 0;
                xTemp = (currCol+1)*tileSize-collisionBoxWidth/2;

            } else {
                xTemp += dX;
            }
        }

        // test if there is solid ground beneath the object
        if (!falling) {
            calculateCorners(xPos, yDest+1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
    }

    public void setPosition(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setVector(double dX, double dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public void setMapPosition() {
        xMap = tileMap.getxPos();
        yMap = tileMap.getyPos();
    }

    /**
     * Determines whether an object has to be drawn in the first place.
     * @return objects has to be drawn?
     */
    public boolean notOnScreen() {
        return xPos+xMap+width < 0 || xPos + xMap - width > GamePanel.WIDTH ||
                yPos + yMap + height < 0 ||
                yPos + yMap - height > GamePanel.HEIGHT;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setDucking(boolean ducking) {
        this.ducking = ducking;
    }

    public int getxPos() {
        return (int)xPos;
    }

    public int getyPos() {
        return (int)yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCollisionBoxHeight() {
        return collisionBoxHeight;
    }

    public int getCollisionBoxWidth() {
        return collisionBoxWidth;
    }
}
