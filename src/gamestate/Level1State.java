package gamestate;

import main.GamePanel;
import tilemap.TileMap;

import java.awt.*;

/**
 * Represents the first level of the game.
 */
public class Level1State extends GameState{

    private TileMap tileMap;

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
        //clear screen to be safe
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
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
