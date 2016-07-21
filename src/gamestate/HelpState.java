package gamestate;

import tilemap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Models the help-screen that gives instructions on how to play.
 */
public class HelpState extends GameState{

    private static final String HELP = "Help";
    private static final String BACK_OPTION = "back";
    private static final int V_SPACE = 20;
    private static final int X_OFFSET = 120;

    private Background background;
    private Color titleColor;
    private Font titleFont;
    private Color standardColor;
    private Font standardFont;

    /**
     * All instructions listed on the screen.
     */
    private final String[] instructions = {
            " to move left and right",
            " to jump",
            " to duck",
            " to shoot a fireball",
            " to freeze enemies"
    };

    /**
     * Constructor.
     * @param gameStateManager manages states and saves
     */
    public HelpState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {

        try {
            background = new Background("/backgrounds/guardtower_help_small.png", 1);
            titleColor = new Color(255, 255, 255);
            standardColor = new Color(255, 255, 255);
            titleFont = new Font("Century Gothic", Font.BOLD, 30);
            standardFont = new Font("Arial", Font.PLAIN, 15);
        } catch(Exception e) {
            System.out.println("Error loading background image!");
            e.printStackTrace();
        }

    }

    /**
     * Draws all help-screen options such as the title, the back-option and
     * the instructions.
     * @param graphics2D graphics to be drawn
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        background.draw(graphics2D);
        graphics2D.setColor(titleColor);
        graphics2D.setFont(titleFont);
        graphics2D.drawString(HELP, 45, 45);

        graphics2D.setColor(standardColor);
        graphics2D.setFont(standardFont);
        drawInstructions(graphics2D);

        // draw back-option
        graphics2D.drawString(BACK_OPTION, 270, 220);

    }

    /**
     * Draws every line of the instructions-array dynamically.
     * @param graphics2D graphics to be drawn
     */
    private void drawInstructions(Graphics2D graphics2D) {
        for (int i = 0; i < instructions.length; i++) {
            graphics2D.drawString("\n", 200, 190);
            graphics2D.drawString(instructions[i], X_OFFSET, 90+(V_SPACE*(i+1)));
        }
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
    /**
     * Not needed here.
     */
    @Override
    public void update() {}

}
