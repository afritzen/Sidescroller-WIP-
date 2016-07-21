package entity;

import com.sun.xml.internal.bind.v2.model.core.ID;
import tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *Represents the main player of the game.
 */
public class Player extends MapObject {

    /**
     * Size of the player's sprite.
     */
    private static final int PLAYER_SIZE = 80;
    /**
     * Size of the invisible box that is responsible for checking collision.
     */
    private static final int COLLISION_BOX_SIZE = 57;
    /**
     * Amount of different animations in spritesheet.
     */
    private static final int ANIMATIONS_TOTAL = 5;

    // indices of animations in main-array
    private static final int IDLE = 0;
    private static final int JUMPING = 1;
    private static final int PUNCHING = 2;
    //private static final int FALLING = 3;
    //private static final int GLIDING = 4;
    private static final int SHOOTING = 3;
    private static final int WALKING = 4;

    // abilities/stats
    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchingTimer;

    // attacks
    private boolean firing;
    private int fireCost;
    private int fireballDamage;
    //private ArrayList<Fireball> fireballs;
    private boolean punching;
    private int punchDamage;
    private int punchRange;

   // private boolean gliding;

    /**
     * Holds all sprites from the spritesheet.
     */
    private ArrayList<BufferedImage[]> sprites;
    /**
     * Number of frames for each animation.
     */
    private final int[] numFrames = {1, 3, 4, 6, 7};
    /**
     * Current animation of the player.
     */
    private Animation animation;

    /**
     * Inherits attributes from MapObject + own attrbutes and configurations.
     * @param tileMap the map the player appears in
     */
    public Player(TileMap tileMap) {
        super(tileMap);
        // set player-specific attributes
        width = PLAYER_SIZE;
        height = PLAYER_SIZE;
        collisionBoxWidth = COLLISION_BOX_SIZE;
        collisionBoxHeight = COLLISION_BOX_SIZE;

        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;
        health = maxHealth = 5;
        fire = maxFire = 2500;
        fireCost = 200;
        fireballDamage = 5;
        //fireballs = new ArrayList<Fireball>();
        punchDamage = 8;
        punchRange = 40;

        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player/player_sprites.png"));

            sprites = new ArrayList<BufferedImage[]>();
            // create new array for every animation-style
            for (int i = 0; i < ANIMATIONS_TOTAL; i++) {
                BufferedImage[] bufferedImage = new BufferedImage[numFrames[i]];

                // create sub-array for every frame of the animation
                for (int j = 0; j < numFrames[i]; j++) {
                    bufferedImage[j] = spritesheet.getSubimage(j*width, i*height, width, height);
                }

                sprites.add(bufferedImage);
            }

        } catch (Exception e) {
            System.out.println("Error loading player sprites!");
            e.printStackTrace();
        }

        // default animation when no change has been made
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
    }

    /**
     * Updates the player's position and checks for collision. Also chooses
     * animation based on which values are set (shooting, walking, etc.).
     */
    public void update() {
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemp, yTemp);

        // stop attack animation after one time to avoid repeats
        if (currentAction == PUNCHING) {
            if (animation.hasPlayedOnce()) {
                punching = false;
            }
        } else if (currentAction == SHOOTING) {
            if (animation.hasPlayedOnce()) {
                firing = false;
            }
        }

        // set animation according to action-values
        if (punching) {
            if (currentAction != PUNCHING) {
                currentAction = PUNCHING;
                animation.setFrames(sprites.get(PUNCHING));
                animation.setDelay(120);
                width = PLAYER_SIZE;
            }
        } else if (firing) {
            if (currentAction != SHOOTING) {
                currentAction = SHOOTING;
                animation.setFrames(sprites.get(SHOOTING));
                animation.setDelay(200);
                width = PLAYER_SIZE;
            }
        } else if (dY < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(280);
                width = PLAYER_SIZE;
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = PLAYER_SIZE;
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = PLAYER_SIZE;
            }
        }

        animation.update();

        // set direction to face
        if (currentAction != PUNCHING && currentAction != SHOOTING) {
            if (right) {
                facingRight = true;
            }
            if (left) {
                facingRight = false;
            }
        }
    }

    /**
     * Determines the next position of the player according to his movement.
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
        } else {
            if (dX > 0) {
                dX -= stopSpeed;
                if (dX < 0) {
                    dX = 0;
                }
            } else if (dX < 0) {
                dX += stopSpeed;
                if (dX > 0) {
                    dX = 0;
                }
            }
        }

        if ((currentAction == PUNCHING || currentAction == SHOOTING) && !(jumping || falling)){
            // no moving while attacking
            dX = 0;
        }

        // jumping
        if (jumping && !falling) {
            dY = jumpStart;
            falling = true;
        }

        // falling
        if (falling) {

            dY += fallSpeed;

            if (dY > 0) {
                // no increase of y-direction
                jumping = false;
            }
            if (dY < 0 && !jumping) {
                dY += stopJumpSpeed;
            }
            if (dY > maxFallSpeed) {
                dY = maxFallSpeed;
            }
        }
    }

    /**
     * Draws the player.
     * @param graphics2D graphics to be drawn
     */
    public void draw(Graphics2D graphics2D) {
        setMapPosition();

        // let player blink for a brief time when being hit
        if (flinching) {
            long elapsed = (System.nanoTime()-flinchingTimer)/1000000;
            if (elapsed/100%2 == 0) {
                return;
            }
        }

        if (facingRight) {
            graphics2D.drawImage(animation.getImage(),(int)(xPos+xMap-width/2), (int)(yPos+yMap-height/2), null);
        } else {
            graphics2D.drawImage(animation.getImage(), (int)(xPos+xMap-width/2+width),
                    (int)(yPos+yMap-height/2), -width, height, null);
        }
    }

    public void setFiring() {
        firing = true;
    }

    public void setPunching() {
        punching = true;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public int getFire() {
        return fire;
    }

    public int getMaxFire() {
        return maxFire;
    }
}
