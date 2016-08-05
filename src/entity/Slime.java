package entity;

import tilemap.TileMap;
import util.ErrorMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

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
     * Animation of the spritesheet's frames.
     */
    private Animation animation;

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

    /**
     * Determines next position on the map.
     */
    private void getNextPosition() {

        // basic movement
        if (left) {
            dX -= moveSpeed;
            if (dX < -maxSpeed) {
                dX = -maxSpeed;
            }
        } else if (right) {
            dX += moveSpeed;
            if (dX > maxSpeed) {
                dX = maxSpeed;
            }
        }

        if (falling) {
            dY += fallSpeed;
        }
    }

    @Override
    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        // check blinking time after hit
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer)/1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }

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
        setMapPosition();

        if (facingRight) {
            graphics2D.drawImage(animation.getImage(),(int)(xPos+xMap-width/2), (int)(yPos+yMap-height/2), null);
        } else {
            graphics2D.drawImage(animation.getImage(), (int)(xPos+xMap-width/2+width),
                    (int)(yPos+yMap-height/2), -width, height, null);
        }
    }

    public Animation getAnimation() {
        return animation;
    }


}
