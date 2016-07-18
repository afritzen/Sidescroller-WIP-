package entity;

import tilemap.TileMap;

/**
 *Represents the main player of the game.
 */
public class Player extends MapObject {

    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchingTimer;

    private boolean firing;
    private int fireCost;
    private int fireballDamage;
    //private ArrayList<Fireball> fireballs;

    private boolean punching;
    private int punchDamage;
    private int punchRange;

    private boolean gliding;

    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int PUNCHING = 6;


    /**
     * Inherits attributes from MapObject + own attrbutes and configurations.
     * @param tileMap the map the player appears in
     */
    public Player(TileMap tileMap) {
        super(tileMap);
        width = 30;
        height = 30;
        collisionBoxWidth = 20;
        collisionBoxHeight = 20;

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

    }

    //TODO: load sprites, draw and update

    public void setFiring() {
        firing = true;
    }

    public void setGliding() {
        gliding = true;
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
