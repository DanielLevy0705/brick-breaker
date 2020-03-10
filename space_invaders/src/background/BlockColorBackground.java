package background;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Color;

/**
 * a class to draw block background with color.
 */
public class BlockColorBackground implements BlockBackground {
    private Color c;

    /**
     * constructor.
     *
     * @param color the color of the block.
     */
    public BlockColorBackground(Color color) {
        this.c = color;
    }

    /**
     * a function to draw the block with color.
     *
     * @param d the drawing surface to draw on.
     * @param r the rectange of the block that we need to draw.
     */
    @Override
    public void draw(DrawSurface d, Rectangle r) {
        //get the rectangle values and draw the block in the right place.
        int i = (int) r.getUpperLeft().getX();
        int i1 = (int) r.getUpperLeft().getY();
        d.setColor(this.c);
        d.fillRectangle(i, i1, (int) r.getWidth(), (int) r.getHeight());
    }
}