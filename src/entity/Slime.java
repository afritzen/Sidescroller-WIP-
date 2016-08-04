package entity;

import tilemap.TileMap;
import util.ErrorMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Slime extends Enemy {

    private static final int SLIME_SIZE = 32;
    private static final int COLLISION_BOX_SIZE = 20;

    /**
     * Subimages from spritesheet.
     */
    private BufferedImage[] sprites;
    /**
     * Amount of frames for slime animation.
     */
    private final int numFrames = 2;
    /**
     * Animation of the spritesheet's frames.
     */
    private Animation animation;

    /**
     * Sets basic attributes, sprites and animation.
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

        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/enemies/slime/glooRotated.png"));
            sprites = new BufferedImage[numFrames];

            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(400);

            // set starting direction
            right = true;
            facingRight = true;

        } catch (Exception e) {
            ErrorMessages.ERR_SPRITES.getText();
            e.printStackTrace();
        }
    }

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


}
