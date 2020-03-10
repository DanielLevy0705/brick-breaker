package sprite;

import biuoop.DrawSurface;

/**
 * interface of sprites.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the drawing surface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time passed.
     * @param dt seconds number in one frame.
     */
    void timePassed(double dt);
}