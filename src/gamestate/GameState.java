package gamestate;

import java.awt.*;

/**
 * Abstract class for all methods concerning game states.
 */
public abstract class GameState {

    /**
     * Manager that holds the game state.
     */
    protected GameStateManager gameStateManager;

    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D graphics2D);
    public abstract void keyPressed(int keyCode);
    public abstract void keyReleased(int keyCode);

}
