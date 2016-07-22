package gamestate;

import tilemap.Background;
import util.ErrorMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Screen that appears when player has died.
 */
public class DeathState extends GameState{

    private Background background;
    private Font standardFont;

    /**
     * Constructor.
     * @param gameStateManager manages states and saves
     */
    public DeathState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        init();
    }

    /**
     * Load background image and set font.
     */
    @Override
    public void init() {
        try {
            background = new Background("/backgrounds/death_screen.png", 1);
            standardFont = new Font("Arial", Font.PLAIN, 15);
        } catch(Exception e) {
            ErrorMessages.ERR_BG.getText();
            e.printStackTrace();
        }
    }

    /**
     * Draws background and main menu option.
     * @param graphics2D graphics to be drawn
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        // draw background
        background.draw(graphics2D);
        // draw text
        graphics2D.setFont(standardFont);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("> back to main menu", 90, 170);
    }

    /**
     * Returns to main menu when enter is pressed.
     * @param keyCode code of typed key
     */
    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            gameStateManager.setState(GameStateManager.MENUSTATE);
        }

    }

    /**
     * Not needed here.
     * @param keyCode
     */
    @Override
    public void keyReleased(int keyCode) {}
    /**
     * Not needed here.
     */
    @Override
    public void update() {}

}
