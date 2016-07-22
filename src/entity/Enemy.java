package entity;

import tilemap.TileMap;

import java.awt.*;

/**
 * Superclass for all enemy-types.
 */
public class Enemy extends MapObject {

    protected int health;
    protected int maxHealth;
    protected int damage;
    protected boolean dead;

    protected boolean flinching;
    protected long flinchTimer;

    public Enemy(TileMap tileMap) {
        super(tileMap);
    }

    /**
     * Performs a hit to an enemy, sets all values depending on the
     * incoming damage.
     * @param damage the damage dealt
     */
    public void hit(int damage) {
        if (dead || flinching) {
            return;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            dead = true;
        }
        // blinks when hit
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    // update and draw are overwritten by subclasses
    public void update(){}
    public void draw(Graphics2D graphics2D){}

    public int getDamage() {
        return damage;
    }

    public boolean isDead() {
        return dead;
    }
}
