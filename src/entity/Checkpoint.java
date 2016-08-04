package entity;

import tilemap.TileMap;
import util.ErrorMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A checkpoint in the level which serves as a returning point
 * in case the player dies.
 */
public class Checkpoint extends MapObject{

    public static final int CHECKPOINT_SIZE = 20;
    public static final int COLLISION_BOX_WIDTH = 20;
    public static final int COLLISION_BOX_HEIGHT = 80;
    /**
     * All sprites of the checkpoint animation.
     */
    private BufferedImage[] sprites;
    /**
     * Animation updating all sprites.
     */
    private Animation animation;
    /**
     * Amount of frames for animation.
     */
    private final int numFrames = 8;
    /**
     * The checkpoint's x-coordinate.
     */
    /**
     * Loads sprites and initializes attributes.
     */
    public Checkpoint(TileMap tileMap) {
        super(tileMap);
        width = height = CHECKPOINT_SIZE;
        collisionBoxHeight = COLLISION_BOX_HEIGHT;
        collisionBoxWidth = COLLISION_BOX_WIDTH;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/checkpoint/checkpoint_sprites.png"));
            sprites = new BufferedImage[numFrames];

            for (int i = 0; i < numFrames; i++) {
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(100);

        } catch (Exception e) {
            ErrorMessages.ERR_SPRITES.getText();
            e.printStackTrace();
        }
    }

    public void update() {
        animation.update();
    }

    public void draw(Graphics2D graphics2D) {
        setMapPosition();
        graphics2D.drawImage(animation.getImage(), (int)(xPos+xMap-width/2), (int)(yPos+yMap-height/2), null);
    }

    public void setMapPosition(int xPos, int yPos) {
        xMap = xPos;
        yMap = yPos;
    }

    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }


}
