package sprite;

import biuoop.DrawSurface;
import tools.Counter;

import java.awt.Color;

/**
 * an object that displays the lives count on the screen.
 */
public class LivesIndicator implements Sprite {
    private Counter lives;

    /**
     * constructor.
     *
     * @param numOfLives a counter of the current number of lives
     */
    public LivesIndicator(Counter numOfLives) {
        this.lives = numOfLives;
    }

    /**
     * a function that draw on the screen.
     *
     * @param d the drawing surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draw the lives in the top right side of the screen.
        int textI = 100, textI1 = 13, textSize = 10;
        String livesStr = String.valueOf(this.lives.getValue());
        String textStr = "lives: " + livesStr;
        //set the filling color as the block color
        d.setColor(Color.BLACK);
        d.drawText(textI, textI1, textStr, textSize);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt seconds number per frame.
     */
    @Override
    public void timePassed(double dt) {

    }
}