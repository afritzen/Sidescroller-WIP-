package main;

import gamestate.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Title screen.
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{

    /**
     * Height of the screeen.
     */
    public static final int HEIGHT = 240;
    /**
     * Width of the screen.
     */
    public static final int WIDTH = 320;
    /**
     * Amount of scale.
     */
    public static final int SCALE = 2;
    private Thread gameThread;
    private boolean running;
    /**
     * Frames per second.
     */
    private int fps = 60;
    private long targetTime = 1000/fps;
    /**
     * Image shown on title screen.
     */
    private BufferedImage titleImg;
    /**
     * Graphics to be rendered.
     */
    private Graphics2D g2d;
    /**
     * Manages saved states.
     */
    private GameStateManager gameStateManager;

    /**
     * Initializes the title screen.
     */
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setFocusable(true);
        requestFocus();
    }

    /**
     * When window is fully loaded, add a thread and key listeners.
     */
    public void addNotify () {
        super.addNotify();

        if (gameThread != null) {
            gameThread = new Thread(this);
            addKeyListener(this);
            gameThread.start();
        }
    }

    /**
     * Initialize game loop and title image.
     */
    private void init() {
        titleImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2d = (Graphics2D) titleImg.getGraphics();
        running = true;
        gameStateManager = new GameStateManager();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //TODO
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameStateManager.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameStateManager.keyReleased(e.getKeyCode());
    }

    /**
     * Main game loop.
     */
    @Override
    public void run() {

        init();
        long start, elapsed, wait;

        while (running) {

            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;
            //convert to nano-seconds
            wait = targetTime - elapsed / 1000000;
            if (wait < 0) {
                wait = 5;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void update () {
        gameStateManager.update();
    }

    private void draw() {
        gameStateManager.draw(g2d);
    }

    /**
     * Draws all graphics on the title screen.
     */
    private void drawToScreen() {
        Graphics graphics = getGraphics();
        graphics.drawImage(titleImg, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        graphics.dispose();
    }
}
