package gamestate;

import entity.Enemy;
import entity.HUD;
import entity.Player;
import entity.Slime;
import main.GamePanel;
import tilemap.Background;
import tilemap.TileMap;
import util.ErrorMessages;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
     * All enemies in the level.
     */
    private ArrayList<Enemy> enemies;
    /**
     * HUD for this level.
     */
    private HUD hud;

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
        try {
            // init background
            background = new Background("/backgrounds/swamp_lvl1_bg.png", 0.1);

        } catch (Exception e) {
            ErrorMessages.ERR_BG.getText();
            e.printStackTrace();
        }

        try {
            // init map
            tileMap = new TileMap(30);
            tileMap.loadTiles("/tilesets/grasstileset.gif");
            tileMap.loadMap("/maps/level1-1.map");
            tileMap.setPosition(0, 0);
        } catch (Exception e) {
            ErrorMessages.ERR_MAP.getText();
            e.printStackTrace();
        }

        // init player
        player = new Player(tileMap);
        player.setPosition(100, 100);

        populateEnemies();

        hud = new HUD(player);
    }

    /**
     * Adds some enemies to the level.
     */
    private void populateEnemies() {
        enemies = new ArrayList<>();
        Slime slime;
        Point[] points = new Point[] {
                new Point(200, 100),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200)
        };
        for (Point point : points) {
            // create new enemy for this point
            slime = new Slime(tileMap);
            slime.setPosition(point.getX(), point.getY());
            enemies.add(slime);
        }
    }

    @Override
    public void update() {
        // update player and set camera
        player.update();
        tileMap.setPosition(GamePanel.WIDTH/2 - player.getxPos(),
                GamePanel.HEIGHT/2 - player.getyPos());

        // make background scroll
        background.setPosition(tileMap.getxPos(), tileMap.getyPos());

        // check attacking for all enemies
        player.checkAttack(enemies);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            if (enemies.get(i).isDead()) {
                enemies.remove(i);
                i--;
            }
        }

        // update all enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        // draw background
        background.draw(graphics2D);
        // draw map
        tileMap.draw(graphics2D);
        // draw player
        player.draw(graphics2D);
        // draw all enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(graphics2D);
        }
        // draw HUD
        hud.draw(graphics2D);
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
            case KeyEvent.VK_ESCAPE:
                gameStateManager.setState(GameStateManager.MENUSTATE);
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
