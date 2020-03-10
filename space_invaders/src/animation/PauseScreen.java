package animation;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

/**
 * a class that pauses the game.
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     * constructor.
     */
    public PauseScreen() {
    }

    /**
     * a function that do one frame.
     *
     * @param d  the drawing surface.
     * @param dt seconds per frame.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //draw the pause image.
        Image img;
        String imageFilename = "images/pause.png";
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imageFilename));
            d.drawImage(0, 0, img);
        } catch (IOException e) {
            System.err.println("Failed reading image file.");
        }
    }

    /**
     * a function that tells us when to stop the animation.
     *
     * @return boolean value that tells us if we should stop the animation.
     */
    public boolean shouldStop() {
        return false;
    }
}