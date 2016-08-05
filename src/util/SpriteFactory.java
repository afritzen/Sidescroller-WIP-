package util;

import entity.Slime;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Loads all sprites of the game at runtime.
 */
public class SpriteFactory {

    private BufferedImage[] slimeSprites = loadSprite("/sprites/enemies/slime/glooRotated.png", Slime.NUM_FRAMES_SLIME,
            Slime.SLIME_SIZE , Slime.SLIME_SIZE);

    /**
     * Default constructor.
     */
    public SpriteFactory() {}

    private BufferedImage[] loadSprite(String filename, int numFrames, int width, int height) {
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(filename));
            BufferedImage[] sprites = new BufferedImage[numFrames];

            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
            }
            return sprites;

        } catch (IOException ioe) {
            ErrorMessages.ERR_SPRITES.getText();
            ioe.printStackTrace();
        }
        return null;
    }

    public BufferedImage[] getSlimeSprites() {
        return slimeSprites;
    }
}
