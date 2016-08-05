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
    /**
     * Animation of the spritesheet's frames.
     */
    protected Animation animation;


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
    }

    public void draw(Graphics2D graphics2D){
        setMapPosition();
        if (facingRight) {
            graphics2D.drawImage(animation.getImage(),(int)(xPos+xMap-width/2), (int)(yPos+yMap-height/2), null);
        } else {
            graphics2D.drawImage(animation.getImage(), (int)(xPos+xMap-width/2+width),
                    (int)(yPos+yMap-height/2), -width, height, null);
        }
    }

    /**
     * Determines next position on the map.
     */
    protected void getNextPosition() {

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


    public int getDamage() {
        return damage;
    }

    public boolean isDead() {
        return dead;
    }

    public Animation getAnimation() {
        return animation;
    }

}
