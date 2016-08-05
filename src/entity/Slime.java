package entity;

import tilemap.TileMap;
import java.awt.*;

/**
 * A weak enemy, occurs mostly in the first two levels of the game.
 */
public class Slime extends Enemy {

    /**
     * Width and height of the slime.
     */
    public static final int SLIME_SIZE = 32;
    /**
     * Size for tolerance of collision detection.
     */
    public static final int COLLISION_BOX_SIZE = 20;
    /**
     * Amount of frames for sprite-animation.
     */
    public static final int NUM_FRAMES_SLIME = 2;
     /**
     * Sets basic attributes.
     * @param tileMap the map the slime appears in
     */
    public Slime(TileMap tileMap) {
        super(tileMap);

        // set slime-specific attributes
        width = height = SLIME_SIZE;
        collisionBoxHeight = COLLISION_BOX_SIZE;
        collisionBoxWidth = COLLISION_BOX_SIZE;

        moveSpeed = 0.2;
        maxSpeed = 0.2;
        fallSpeed = 0.2;
        maxFallSpeed = 10.2;
        health = maxHealth = 2;
        damage = 1;

        animation = new Animation();
        right = true;
        facingRight = true;
    }

    @Override
    public void update() {

        super.update();
        // go other direction after running into an obstacle
        if (right && dX == 0) {
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dX == 0) {
            right = true;
            left = false;
            facingRight = true;
        }
        animation.update();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
    }


}
