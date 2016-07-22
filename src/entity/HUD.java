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
        graphics2D.setFont(hudFont);
        //TODO: display acrtual stats
    }
}
