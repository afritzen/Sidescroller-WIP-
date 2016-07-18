package gamestate;

import main.GamePanel;
import tilemap.Background;
import tilemap.TileMap;

import java.awt.*;

/**
 * Represents the first level of the game.
 */
public class Level1State extends GameState{

    /**
     * The map of this level.
     */
    private TileMap tileMap;
    /**
     * Background image for this level.
     */
    private Background background;

    /**
     * Initializes the first level by loading the map and setting up the
     * gamestate manager.
     * @param gameStateManager manages current state
     */
    public Level1State(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        init();
    }

    @Override
    public void init() {
        // init background
        background = new Background("/backgrounds/swamp_lvl1_bg.png", 0.1);

        // init map
        tileMap = new TileMap(30);
        tileMap.loadTiles("/tilesets/grasstileset.gif");
        tileMap.loadMap("/maps/level1-1.map");
        tileMap.setPosition(0, 0);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // draw background
        background.draw(graphics2D);

        // draw map
        tileMap.draw(graphics2D);
    }

    @Override
    public void keyPressed(int keyCode) {

    }

    @Override
    public void keyReleased(int keyCode) {

    }



}
