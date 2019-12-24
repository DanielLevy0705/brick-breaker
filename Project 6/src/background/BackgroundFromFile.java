package background;

import biuoop.DrawSurface;
import sprite.Sprite;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;

/**
 * Class to draw background of a level given in a file.
 */
public class BackgroundFromFile implements Sprite {
    private Image img;
    private Color color;

    /**
     * a constructor that get an image.
     *
     * @param imageFilename the image that will be in the background
     */
    public BackgroundFromFile(String imageFilename) {
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imageFilename));
        } catch (IOException e) {
            System.err.println("Failed reading image file.");
        }
        //make the color null because we will not use it.
        this.color = null;
    }

    /**
     * a constructor that get a color.
     *
     * @param c the color of the background.
     */
    public BackgroundFromFile(Color c) {
        this.color = c;
        //make the image null because we will not use it.
        this.img = null;
    }

    /**
     * a function for drawing on the drawing surface.
     *
     * @param d the drawing surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        //if the color is not null set the color to this color and draw a rectangle all over the screen.
        if (color != null) {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            //else the color is null so "draw" the image all over the screen.
        } else {
            d.drawImage(0, 0, this.img);
        }
    }

    /**
     * a function that tells us if time has passed.
     *
     * @param dt parameter that holds how many seconds in one frame.
     */
    @Override
    public void timePassed(double dt) {

    }
}
