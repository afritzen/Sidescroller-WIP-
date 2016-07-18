package entity;

import java.awt.image.BufferedImage;

/**
 * Manages an animation with a list of frames.
 */
public class Animation {

    /**
     * All frames of the animation.
     */
    private BufferedImage[] frames;
    /**
     * Current frame of the animation.
     */
    private int currentFrame;

    private long startTime;
    private long delay;
    /**
     * True if animation has already been played.
     */
    private boolean playedOnce;


    public Animation() {
        playedOnce = false;
    }

    /**
     * Updates the elapsed time and goes into the next
     * frame if neccessary.
     */
    public void update() {
        if (delay == -1) {
            return;
        }

        long elapsed = (System.nanoTime()-startTime)/1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        // start over
        if (currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setFrame(int idx) {
        currentFrame = idx;
    }

    public int getFrame() {
        return currentFrame;
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    public boolean hasPlayedOnce() {
        return playedOnce;
    }

}
