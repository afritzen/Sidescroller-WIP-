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

    /**
     * Space between left screen border and credits.
     */
    private static final int X_OFFSET = 10;
    /**
     * Space between lines in credits.
     */
    private static final int V_SPACE = 15;

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
     * All names in the credits.
     */
    private String[] credits = {
            "ForeignGuyMike",
            "Stephen Challener (Redshrike) and Doudoulolita",
            "JAP"
    };

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
        drawCredits(graphics2D);

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
     * Dynamically draws all the names listed in the credits-array. Adds a x-offset of
     * 10 and a vertical space of 15 to every line.
     * @param graphics2D graphics to be drawn
     */
    private void drawCredits(Graphics2D graphics2D) {

        graphics2D.setFont(creditsFont);
        graphics2D.drawString("Thanks to:", X_OFFSET, 60);

        for (int i = 0; i < credits.length; i++) {
            graphics2D.drawString("\n", 200, 190);
            graphics2D.drawString(credits[i], X_OFFSET, 60+(V_SPACE*(i+1)));
        }

    }

    /**
     * Not needed here.
     * @param keyCode
     */
    @Override
    public void keyReleased(int keyCode) {}

}

