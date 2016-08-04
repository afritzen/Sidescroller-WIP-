package entity;

import tilemap.TileMap;
import util.ErrorMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Potion extends MapObject {

    public static final int POTION_SIZE = 20;
    public static final int COLLISION_BOX_SIZE = 15;

    /**
     * Determines whether this is a life- or mana-potion.
     */
    private int type;
    /**
     * Sprite of the potion.
     */
    private BufferedImage potionImg;

    /**
     * Init attributes and load sprites.
     * @param tileMap map the object is located in
     * @param type 0 = life, 1 = mana
     */
    public Potion(TileMap tileMap, int type) {
        super(tileMap);
        this.type = type;
        width = height = POTION_SIZE;
        collisionBoxHeight = collisionBoxWidth = COLLISION_BOX_SIZE;

        // load sprites according to type
        if (type == 0) {

            try {
                potionImg = ImageIO.read(getClass().getResourceAsStream("/sprites/items/potions/potion_life_sprite.png"));
            } catch(Exception e) {
                ErrorMessages.ERR_IMG.getText();
                e.printStackTrace();
            }

        } else if (type == 1) {
            try {
                potionImg = ImageIO.read(getClass().getResourceAsStream("/sprites/items/potions/potion_mana_sprite.png"));
            } catch (Exception e) {
                ErrorMessages.ERR_IMG.getText();
                e.printStackTrace();
            }
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

    public int getType() {
        return type;
    }

}
