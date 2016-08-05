package entity;

import tilemap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Potion extends MapObject {

    /**
     * Height and width of a single potion.
     */
    public static final int POTION_SIZE = 20;
    /**
     * Height-tolerance of collision detection.
     */
    public static final int COLLISION_BOX_WIDTH = 20;
    /**
     * Width-tolerance of collision detection.
     */
    public static final int COLLISION_BOX_HEIGHT = 80;
    /**
     * Determines whether this is a life- or mana-potion.
     */
    private int type;
    /**
     * Amount of life/mana the potions brings back.
     */
    private int power;
    /**
     * Sprite of the potion.
     */
    private BufferedImage potionImg;

    /**
     * Init attributes.
     * @param tileMap map the object is located in
     * @param type 0 = life, 1 = mana
     */
    public Potion(TileMap tileMap, int type) {
        super(tileMap);
        this.type = type;
        width = height = POTION_SIZE;
        collisionBoxHeight = COLLISION_BOX_HEIGHT;
        collisionBoxWidth = COLLISION_BOX_WIDTH;

        switch (type) {
            case 0: case 2:
                // small potion
                power = 5;
                break;
            case 1:case 3:
                // big potion
                power = 10;
                break;
            }
    }

    /**
     * Draw single potion image.
     * @param graphics2D graphics to be drawn
     */
    public void draw(Graphics2D graphics2D) {
        setMapPosition();
        graphics2D.drawImage(potionImg, (int)(xPos+xMap-width/2), (int)(yPos+yMap-height/2), null);
    }

    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setMapPosition(int xMap, int yMap) {
        this.xMap = xMap;
        this.yMap = yMap;
    }

    public void setPotionImg(BufferedImage potionImg) {
        this.potionImg = potionImg;
    }

    public int getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

}
