package gamestate;

import tilemap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Gamestate for the credits.
 */
public class CreditsState extends GameState{

    private static final String CREDITS = "Credits";
    private static final String BACK_OPTION = "back";

    private static final int X_OFFSET = 10;

    /**
     * Background image.
     */
    private Background background;
    private Color titleColor;
    private Font titleFont;
    private Color standardColor;
    private Font standardFont;
    private Font creditsFont;

    /**
     * Constructor.
     * @param gameStateManager manages states and saves
     */
    public CreditsState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        init();
    }


    /**
     * Initializes this state, loads background and fonts.
     */
    @Override
    public void init() {
        try {

            background = new Background("/backgrounds/gate_credits_small.png", 1);
            //background.setVector(-0.1, 0);
            titleColor = new Color(255, 255, 255);
            standardColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.BOLD, 30);
            standardFont = new Font("Arial", Font.BOLD, 15);
            creditsFont = new Font("Arial", Font.PLAIN, 10);

        } catch (Exception e) {
            System.out.println("Error loading background image!");
            e.printStackTrace();
        }
    }

    /**
     * Not needed here.
     */
    @Override
    public void update() {}

    /**
     * Draws all credit-screen components such as the title, the
     * back-option and the credits themselves.
     * @param graphics2D graphics to draw
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        background.draw(graphics2D);
        graphics2D.setColor(titleColor);
        graphics2D.setFont(titleFont);
        graphics2D.drawString(CREDITS, X_OFFSET, 40);

        // draw credits
        graphics2D.setFont(creditsFont);
        graphics2D.drawString("Sprites:", X_OFFSET, 60);
        graphics2D.drawString("\n", 200, 190);
        graphics2D.drawString("Stephen Challener (Redshrike) and Doudoulolita", X_OFFSET, 75);
        graphics2D.drawString("\n", 200, 190);
        graphics2D.drawString("Backgrounds:", X_OFFSET, 90);

        // draw back-option
        graphics2D.setColor(standardColor);
        graphics2D.setFont(standardFont);
        graphics2D.drawString(BACK_OPTION, 270, 220);
    }

    /**
     * Takes player back to main menu if the enter-key is pressed.
     * @param keyCode code of input key-event
     */
    @Override
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_ENTER) {
            // back to main menu
            gameStateManager.setState(GameStateManager.MENUSTATE);
        }
    }

    /**
     * Not needed here.
     * @param keyCode
     */
    @Override
    public void keyReleased(int keyCode) {}
}
