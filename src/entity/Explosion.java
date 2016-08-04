package entity;

import util.ErrorMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A standard explosion in the game.
 */
public class Explosion {

    public static final int EXPLOSION_SIZE = 30;

    /**
     * Animation for explosion.
     */
    private Animation animation;
    /**
     * The spritesheet.
     */
    private BufferedImage[] sprites;
    /**
     * Amount of frames for explosion animation.
     */
    private final int numFrames = 3;
    /**
     * X- coordinate in the game.
     */
    private int xPos;
    /**
     * Y-coordinate in the game.
     */
    private int yPos;
    /**
     * X-coordinate in tilemap.
     */
    private int xMap;
    /**
     * Y-coordinate in tilemap.
     */
    private int yMap;

    private int width;
    private int height;

    /**
     * Determines whether the explosion is no longer active and can be
     * removed from the game.
     */
    private boolean remove;

    /**
     * Loads sprites and initializes attributes.
     * @param xPos {@link #xPos}
     * @param yPos {@link #yPos}
     */
    public Explosion(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        width = height = EXPLOSION_SIZE;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/explosions/explosions_hit.png"));
            sprites = new BufferedImage[numFrames];

            for (int i = 0; i < numFrames; i++) {
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }
        } catch (Exception e) {
            ErrorMessages.ERR_SPRITES.getText();
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);
    }

    public void update() {
        animation.update();
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(animation.getImage(), xPos+xMap-width/2,
                yPos+yMap-height/2, null);
    }

    public boolean shouldRemove() {
        return remove;
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
