package entity;

import util.ErrorMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Heads-up-display for health and firepower.
 */
public class HUD {

    /**
     * The player whose stats are displayed.
     */
    private Player player;
    /**
     * Image of the HUD.
     */
    private BufferedImage hudImg;
    /**
     * Icon representing one life of the player.
     */
    private BufferedImage lifeIcon;
    /**
     * Font that displays stats.
     */
    private Font hudFont;

    /**
     * Loads the image and sets a font.
     * @param player {@link #player}
     */
    public HUD (Player player) {
        this.player = player;

        // load image
        try {
            hudImg = ImageIO.read(getClass().getResourceAsStream("/hud/hud_small.png"));
            lifeIcon = ImageIO.read(getClass().getResourceAsStream("/hud/life_icon.png"));
            hudFont = new Font("Arial", Font.PLAIN, 15);
        } catch(Exception e) {
            ErrorMessages.ERR_IMG.getText();
            e.printStackTrace();
        }
    }

    /**
     * Draw hud image and player-stats.
     * @param graphics2D graphics to be drawn
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(hudImg, 5, 5, null);
        graphics2D.drawImage(lifeIcon, 120, 10, null);
        graphics2D.setFont(hudFont);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(player.getHealth() + "/" + player.getMaxHealth(), 40, 25);
        graphics2D.drawString(player.getFire()/100 + "/" + player.getMaxFire()/100, 40, 50);
        graphics2D.drawString("x" + player.getLives(), 145, 25);
    }
}
