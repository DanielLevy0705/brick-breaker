package background;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Image;

/**
 * a class to draw a block background with an image.
 */
public class BlockImageBackground implements BlockBackground {
    private Image img;

    /**
     * constructor.
     *
     * @param image an image to draw as the block background.
     */
    public BlockImageBackground(Image image) {
        this.img = image;
    }

    /**
     * a function to draw a block with an image background.
     *
     * @param d the drawing surface to draw on.
     * @param r the rectange of the block that we need to draw.
     */
    @Override
    public void draw(DrawSurface d, Rectangle r) {
        //get the rectangle values and draw the image in the right place.
        int i = (int) r.getUpperLeft().getX();
        int i1 = (int) r.getUpperLeft().getY();
        d.drawImage(i, i1, this.img);
    }
}
