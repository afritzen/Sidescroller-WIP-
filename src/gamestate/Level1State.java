package gamestate;

import entity.Player;
import main.GamePanel;
import tilemap.Background;
import tilemap.TileMap;

import java.awt.*;
import java.awt.event.KeyEvent;

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
     * The player appearing in this level.
     */
    private Player player;

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

        // init player
        player = new Player(tileMap);
        player.setPosition(100, 100);
    }

    @Override
    public void update() {
        // update player and set camera
        player.update();
        tileMap.setPosition(GamePanel.WIDTH/2 - player.getxPos(),
                GamePanel.HEIGHT/2 - player.getyPos());
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // draw background
        background.draw(graphics2D);
        // draw map
        tileMap.draw(graphics2D);
        // draw player
        player.draw(graphics2D);
    }

    /**
     * Sets basic movement of the player (left, right, jumping, etc.).
     * @param keyCode code of the pressed key
     */
    @Override
    public void keyPressed(int keyCode) {
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                player.setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight(true);
                break;
            case KeyEvent.VK_UP:
                player.setUp(true);
                break;
            case KeyEvent.VK_DOWN:
                player.setDown(true);
                player.setDucking(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJumping(true);
                break;
            case KeyEvent.VK_W:
                player.setPunching();
                break;
            case KeyEvent.VK_Q:
                player.setIce();
                break;
            case KeyEvent.VK_E:
                player.setFiring();
                break;
            default:
                // do nothing
                break;
        }

    }

    /**
     * Sets player's state back to normal after key has been released.
     * @param keyCode code of the released key
     */
    @Override
    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;
            case KeyEvent.VK_UP:
                player.setUp(false);
                break;
            case KeyEvent.VK_DOWN:
                player.setDown(false);
                player.setDucking(false);
                break;
            case KeyEvent.VK_W:
                player.setJumping(false);
                break;
            default:
                // do nothing
                break;
        }
    }



}
