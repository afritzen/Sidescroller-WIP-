package gamestate;

import entity.*;
import main.GamePanel;
import tilemap.Background;
import tilemap.TileMap;
import util.ErrorMessages;
import util.SpriteFactory;

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
     * Explosion taking place on the screen.
     */
    private ArrayList<Explosion> explosions;
    /**
     * All checkpoints in the level.
     */
    private ArrayList<Checkpoint> checkpoints;
    /**
     * All potions in the level.
     */
    private ArrayList<Potion> potions;
    /**
     * HUD for this level.
     */
    private HUD hud;
    /**
     * Provides all sprites for this level.
     */
    private SpriteFactory spriteFactory;

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

        // for laoding sprites at runtime
        spriteFactory = new SpriteFactory();

        // init player
        player = new Player(tileMap);
        player.setPosition(100, 100);

        // add enemies, checkpoints and items
        populateEnemies();
        explosions = new ArrayList<>();
        checkpoints = new ArrayList<>();
        addCheckpoints();
        potions = new ArrayList<>();
        addPotions();

        hud = new HUD(player);
    }

    /**
     * Adds some enemies to the level.
     */
    private void populateEnemies() {
        enemies = new ArrayList<>();
        Slime slime;
        Point[] points = new Point[] {
                new Point(200, 200),
                new Point(860, 200),
                new Point(1525, 200),
                new Point(1680, 200),
                new Point(1800, 200)
        };
        for (Point point : points) {
            // create new enemy for this point
            slime = new Slime(tileMap);
            slime.getAnimation().setFrames(spriteFactory.getSlimeSprites());
            slime.getAnimation().setDelay(400);
            slime.setPosition(point.x, point.y);
            enemies.add(slime);
        }
    }

    /**
     * Fills the level with some checkpoints at specific coordinates.
     */
    private void addCheckpoints() {
       checkpoints = new ArrayList<>();
        Checkpoint checkpoint;
        Point[] points = new Point[] {
            new Point(1020, 105)
        };
        for (Point point : points) {
            checkpoint = new Checkpoint(tileMap);
            checkpoint.setPosition(point.x, point.y);
            checkpoint.setMapPosition(point.x, point.y);
            checkpoints.add(checkpoint);
        }
    }

    /**
     * Fills the level with some life-/mana-potions.
     */
    private void addPotions() {
        Point[] pointsLife = new Point[] {
          new Point(290, 210)
        };
        for (Point point : pointsLife) {
            Potion potion = new Potion(tileMap, 0);
            potion.setPosition(point.x, point.y);
            potion.setMapPosition(point.x, point.y);
            potions.add(potion);
        }

        Point[] pointsMana = new Point[] {
          new Point(1000, 210)
        };
        for (Point point : pointsMana) {
            Potion potion = new Potion(tileMap, 2);
            potion.setPosition(point.x, point.y);
            potion.setMapPosition(point.x, point.y);
            potions.add(potion);
        }
    }

    /**
     * Checks whether the player has passed a checkpoint.
     */
    private void checkForCheckpoints() {
        for (Checkpoint checkpoint : checkpoints) {
            if (player.intersects(checkpoint)) {
                player.setCheckpointX(checkpoint.getxPos());
                player.setCheckpointY(checkpoint.getyPos());
            }
        }
    }

    /**
     * Checks whether the player has found a potion and increases life/mana
     * accordingly.
     */
    private void checkForPotions() {
        for (Potion potion : potions) {
            if (player.intersects(potion)) {
                if (potion.getType() == 0 || potion.getType() == 1) {
                    if (player.getHealth() == player.getMaxHealth()) {
                        return;
                    }
                    player.increaseLife(potion.getPower());
                    potions.remove(potion);
                } else if (potion.getType() == 2 || potion.getType() == 3) {
                    if (player.getFire() == player.getMaxFire()) {

                    }
                    player.increaseFire(potion.getPower());
                    potions.remove(potion);
                }
            }
        }
    }

    @Override
    public void update() {
        // update player and set camera
        player.update();
        checkForCheckpoints();
        checkForPotions();
        // check for game-over
        if (player.lostAllLives()) {
            try {
                // small break between game an death-screen
                Thread.sleep(2000);
            } catch(InterruptedException ie) {
                ie.printStackTrace();
            }
            gameStateManager.setState(GameStateManager.DEATHSTATE);
        }
        tileMap.setPosition(GamePanel.WIDTH/2 - player.getxPos(),
                GamePanel.HEIGHT/2 - player.getyPos());

        // make background scroll
        background.setPosition(tileMap.getxPos(), tileMap.getyPos());

        // check attacking for all enemies
        player.checkAttack(enemies);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            if (enemies.get(i).isDead()) {
                // new explosion with it's origin where the enemy once was
                explosions.add(new Explosion(enemies.get(i).getxPos(), enemies.get(i).getyPos()));
                enemies.remove(i);
                i--;
            }
        }

        // update explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }

        // update checkpoints
        for(Checkpoint checkpoint : checkpoints) {
            checkpoint.update();
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
        for (Enemy enemy : enemies) {
            enemy.draw(graphics2D);
        }
        // draw all explosions
        for (Explosion explosion : explosions) {
            explosion.setMapPosition((int)tileMap.getxPos(), (int)tileMap.getyPos());
            explosion.draw(graphics2D);
        }
        // draw checkpoints
        for (Checkpoint checkpoint : checkpoints) {
            checkpoint.setMapPosition((int)tileMap.getxPos(), (int)tileMap.getyPos());
            checkpoint.draw(graphics2D);
        }
        for (Potion potion : potions) {
            potion.setMapPosition((int)tileMap.getxPos(), (int)tileMap.getyPos());
            potion.draw(graphics2D);
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
