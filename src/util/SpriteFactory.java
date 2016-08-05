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
    private BufferedImage potionLifeImage = loadImage("/sprites/items/potions/potion_life_sprite.png");
    private BufferedImage potionManaImage = loadImage("/sprites/items/potions/potion_mana_sprite.png");

    /**
     * Default constructor.
     */
    public SpriteFactory() {}

    /**
     * Loads a sprite and creates an array for the single frames of
     * the animation.
     * @param filename where the sprite is located
     * @param numFrames amount of frames for the animation
     * @param width widht of a single sprite
     * @param height height of a single sprite
     * @return array of sprites for animation
     */
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

    /**
     * Loads a single non-animated image.
     * @param filename where the image is located
     * @return the image
     */
    private BufferedImage loadImage(String filename) {

        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(filename));
            return image;

        } catch (IOException ioe) {
            ErrorMessages.ERR_IMG.getText();
            ioe.printStackTrace();
        }

        return null;
    }

    public BufferedImage[] getSlimeSprites() {
        return slimeSprites;
    }

    public BufferedImage getPotionLifeImage() {
        return potionLifeImage;
    }

    public BufferedImage getPotionManaImage() {
        return potionManaImage;
    }
}
