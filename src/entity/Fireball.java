package entity;

import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Single fireball used as an attack by the player.
 */
public class Fireball extends MapObject{

    public static final int FIREBALL_WIDTH = 30;
    public static final int FIREBALL_HEIGHT = 20;
    public static final int COLLISION_BOX_HEIGHT = 15;
    public static final int COLLISION_BOX_WIDTH = 15;
    /**
     * Amount of frames for normal animation.
     */
    private final int numFrames = 4;
    /**
     * Amount of frames for hit animation.
     */
    private final int numFramesHit = 2;
    /**
     * Determines whether fireball has hit something.
     */
    private boolean hit;
    /**
     * Determines whether fireball has to be removed from the game.
     */
    private boolean remove;
    /**
     * Fireball spritesheet.
     */
    private BufferedImage[] sprites;
    /**
     * Sprites for fireballs that hit a target.
     */
    private BufferedImage[] spritesHit;
    /**
     * Animation that is played when fireball hits something.
     */
    private BufferedImage[] hitSprites;
    /**
     * Animation for fireball sprites.
      */
    private Animation animation;

    /**
     * Initializes fireball and loads needed sprites.
     * @param tileMap map the fireball is active in
     * @param right determines which direction the fireball has to face
     */
    public Fireball(TileMap tileMap, boolean right) {
        super(tileMap);
        width = FIREBALL_WIDTH;
        height = FIREBALL_HEIGHT;
        collisionBoxWidth = COLLISION_BOX_WIDTH;
        collisionBoxHeight = COLLISION_BOX_HEIGHT;

        moveSpeed = 3.8;
        // direction the fireball is flying in
        if (right) {
            dX = moveSpeed;
        } else {
            dX = -moveSpeed;
        }

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/fireball/fireball_sprites.png"));
            sprites = new BufferedImage[numFrames];

            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }

            BufferedImage spritesheetHit = ImageIO.read(getClass().getResourceAsStream("/sprites/fireball/fireball_hit_sprites.png"));
            spritesHit = new BufferedImage[numFramesHit];

            // same for hit animation (different spritesheet)
            for (int i = 0; i < spritesHit.length; i++) {
                spritesHit[i] = spritesheetHit.getSubimage(i*width, 0, width, height);
            }

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);


        } catch (Exception e) {
            System.out.println("Error loading fireball sprites!");
            e.printStackTrace();
        }
    }

    /**
     * Checks if the fireball has hit something (block, enemy, etc.) and
     * sets it's properties accordingly.
     */
    public void update() {
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        // NOTE: everytime ball hits something on the tilemap, dX is set to 0
        if (dX == 0 && !hit) {
            setHit();
        }

        animation.update();
        // fireball's lifespan is over
        if (hit && animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    /**
     * Draws a fireball depending on in which direction it is flying.
     * @param graphics2D graphics to be drawn
     */
    public void draw(Graphics2D graphics2D) {
        setMapPosition();

        if (right) {
            graphics2D.drawImage(animation.getImage(),(int)(xPos+xMap-width/2), (int)(yPos+yMap-height/2), null);
        } else {
            graphics2D.drawImage(animation.getImage(), (int)(xPos+xMap-width/2+width), (int)(yPos+yMap-height/2), -width, height, null);
        }
    }

    /**
     * Changes to hit animation when fireball has hit a target.
     */
    public void setHit() {
        if (hit) {
            return;
        }
        hit = true;
        animation.setFrames(spritesHit);
        animation.setDelay(70);
        dX = 0;
    }

    public boolean shouldRemove() {
        return remove;
    }
}
