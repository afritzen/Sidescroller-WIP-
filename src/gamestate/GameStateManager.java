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
    //TODO: create more!

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
