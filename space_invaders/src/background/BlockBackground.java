package background;

import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * an interface for block backgrounds.
 */
public interface BlockBackground {
    /**
     * a function that draws the background of the block.
     *
     * @param d the drawing surface to draw on.
     * @param r the rectange of the block that we need to draw.
     */
    void draw(DrawSurface d, Rectangle r);
}