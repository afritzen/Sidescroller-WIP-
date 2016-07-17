package tilemap;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {

    /**
     * Background image.
     */
    private BufferedImage image;

    /**
     * X coordinate of the background.
     */
    private double xPos;
    /**
     * Y coordinate of the background.
     */
    private double yPos;
    /**
     * Movement on x-axis.
     */
    private double dX;
    /**
     * Movement on y-axis.
     */
    private double dY;
    private double moveScale;

    /**
     * Loads a background image with a given scale and filename.
     * @param filename name of the file that holds the image
     * @param moveScale
     */
    public Background(String filename, double moveScale) {

        try {
            image = ImageIO.read(getClass().getResourceAsStream(filename));
            this.moveScale = moveScale;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a new position on the game's panel.
     * @param xPos
     * @param yPos
     */
    public void setPosition(double xPos, double yPos) {
        xPos = (xPos*moveScale) % GamePanel.WIDTH;
        yPos = (yPos*moveScale) % GamePanel.HEIGHT;
    }

    /**
     * Sets a new vector for movement.
     * @param dX progress on x-axis
     * @param dY progress on y-axis
     */
    public void setVector(double dX, double dY) {
        dX = dX;
        dY = dY;
    }

    /**
     * Updates x- and y-position.
     */
    public void update() {
        xPos += dX;
        yPos += dY;
    }

    /**
     * Draws the image for the background on the screen.
     * @param graphics2D graphics to draw
     */
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, (int)xPos, (int)yPos, null);

        // draw new image if x increases or decreases too far
        if (xPos < 0) {
            graphics2D.drawImage(image, (int)xPos+GamePanel.WIDTH, (int)yPos, null);
        }
        if (xPos > 0) {
            graphics2D.drawImage(image, (int)xPos - GamePanel.WIDTH, (int)yPos, null);
        }
    }


}
