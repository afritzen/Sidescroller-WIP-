package gamestate;

import main.Game;
import main.GamePanel;
import tilemap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState{

    /**
     * Text for starting option.
     */
    private static final String OPTIONS_START = "Start";
    /**
     * Text for help option.
     */
    private static final String OPTIONS_HELP = "Help";
    /**
     * Text for credit option.
     */
    private static final String OPTIONS_CREDITS = "Credits";
    /**
     * Text for quitting option.
     */
    private static final String OPTIONS_QUIT = "Quit";
    /**
     * Background image of this state.
     */
    private Background background;
    /**
     * Keeps track of what option has been selected.
     */
    private int currentChoice = 0;
    /**
     * Available options.
     */
    private String options[] = {
            OPTIONS_START,
            OPTIONS_HELP,
            OPTIONS_CREDITS,
            OPTIONS_QUIT
    };

    /**
     * Color of game title.
     */
    private  Color titleColor;
    /**
     * Font of game title.
     */
    private Font titleFont;

    /**
     * Font for everything except the title.
     */
    private Font standardFont;

    /**
     * Initializes the main menu screen with a background image and some options.
     * @param gameStateManager
     */
    public MenuState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;

        try {
            background = new Background("/backgrounds/placeholder_bg.png", 0.1);
            background.setVector(-0.1, 0);

            titleColor = new Color(128, 0, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 30);

            standardFont = new Font("Arial", Font.PLAIN, 15);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {}

    @Override
    public void update() {
        background.update();
    }

    /**
     * Draws all components of the menu screen.
     * @param graphics2D
     */
    @Override
    public void draw(Graphics2D graphics2D) {

        // draw background
        background.draw(graphics2D);

        // draw fonts and place them
        graphics2D.setColor(titleColor);
        graphics2D.setFont(titleFont);
        graphics2D.drawString(Game.GAME_TITLE, 45, 80);

        //draw options
        graphics2D.setFont(standardFont);

        for (int i = 0; i < options.length; i++) {

            if (i == currentChoice) {
                graphics2D.setColor(Color.RED);

            } else {
                graphics2D.setColor(Color.WHITE);
            }
            graphics2D.drawString(options[i], 145, 140 + i * 15);
        }
    }

    /**
     * Selects an option and executes whatever is behind it.
     */
    private void select() {
        switch (currentChoice) {
            case 0:
                // switch to first level
                gameStateManager.setState(GameStateManager.LEVEL1STATE);
                break;
            case 1:
                gameStateManager.setState(GameStateManager.HELPSTATE);
                break;
            case 2:
                gameStateManager.setState(GameStateManager.CREDITSSTATE);
                break;
            default:
                System.exit(0);
        }
    }

    /**
     * Reacts to the player pressing a key.
     * Here, the keycode determines which option will be selected.
     * @param keyCode code of the key that holds which key has been pressed
     */
    @Override
    public void keyPressed(int keyCode) {

        switch (keyCode) {
            case KeyEvent.VK_ENTER:
                select();
                break;
            case KeyEvent.VK_UP:
                currentChoice--;
                if (currentChoice == -1) {
                    currentChoice = options.length -1;
                }
                break;
            case KeyEvent.VK_DOWN:
                currentChoice++;
                if (currentChoice == options.length) {
                    currentChoice = 0;
                }
                break;
            default:
                // do nothing
                break;
        }
    }

    @Override
    public void keyReleased(int keyCode) {}
}
