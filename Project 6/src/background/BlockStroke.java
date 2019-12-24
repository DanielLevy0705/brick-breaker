package background;

import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Color;

/**
 * a class to draw the a frame to the block.
 */
public class BlockStroke implements BlockBackground {
    private Color c;

    /**
     * constructor.
     *
     * @param color the color of the frame.
     */
    public BlockStroke(Color color) {
        this.c = color;
    }

    /**
     * a function to draw a frame to the block.
     *
     * @param d the drawing surface to draw on.
     * @param r the rectange of the block that we need to draw.
     */
    @Override
    public void draw(DrawSurface d, Rectangle r) {
        // get the values and set the color of the frame then draw the frame.
        int i = (int) r.getUpperLeft().getX();
        int i1 = (int) r.getUpperLeft().getY();
        d.setColor(this.c);
        d.drawRectangle(i, i1, (int) r.getWidth(), (int) r.getHeight());
    }
}