package gamestate;

import java.awt.*;
import java.util.ArrayList;

/**
 * Holds and manages all (saved) states of the game.
 */
public class GameStateManager {

    /**
     * Index for menu.
     */
    public static final int MENUSTATE = 0;
    /**
     * Index for first level.
     */
    public static final int LEVEL1STATE = 1;
    /**
     * Index for credits.
     */
    public static final int CREDITSSTATE = 2;
    /**
     * Index for instructions.
     */
    public static final int HELPSTATE = 3;
    /**
     * Index for death screen.
     */
    public static final int DEATHSTATE = 4;
    /**
     * All saved states.
     */
    private ArrayList<GameState> gameStates;
    /**
     * Current state of the game.
     */
    private int currentState;

    /**
     * Holds a list of all states and the menu state. Menu state
     * is added to the list of states per default.
     */
    public GameStateManager() {
        gameStates = new ArrayList<>();
        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));
        gameStates.add(new Level1State(this));
        gameStates.add(new CreditsState(this));
        gameStates.add(new HelpState(this));
        gameStates.add(new DeathState(this));
    }

    /**
     * Sets a new game state.
     * @param state new state to be set
     */
    public void setState(int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw(Graphics2D graphics2D) {
        gameStates.get(currentState).draw(graphics2D);
    }

    public void keyPressed(int key) {
        gameStates.get(currentState).keyPressed(key);
    }

    public void keyReleased(int key) {
        gameStates.get(currentState).keyReleased(key);
    }


}
