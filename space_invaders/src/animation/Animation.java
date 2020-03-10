package animation;

import biuoop.DrawSurface;

/**
 * interface for animations.
 */
public interface Animation {
    /**
     * a function that do any actions that need to be done in one frame of the animation.
     *
     * @param d  the drawing surface.
     * @param dt seconds per frame.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * tells us when the animation should stop.
     *
     * @return true or false for knowing if we should stop.
     */
    boolean shouldStop();
}