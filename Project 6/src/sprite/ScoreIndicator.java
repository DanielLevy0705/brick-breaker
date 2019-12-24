package sprite;

import biuoop.DrawSurface;
import tools.Counter;

import java.awt.Color;

/**
 * a class that shows the score.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * constructor.
     *
     * @param currentScore the current score of this game.
     */
    public ScoreIndicator(Counter currentScore) {
        this.score = currentScore;
    }

    /**
     * a function that draws on the screen.
     *
     * @param d the drawing surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        //convert the score into string the fill a rectangle on the top and draw the score in the middle of it
        int i = 0, i1 = 0, i2 = 800, i3 = 15;
        int textI = 350, textI1 = 13, textSize = 10;
        String scoreStr = String.valueOf(this.score.getValue());
        String textStr = "Score: " + scoreStr;
        //set the filling color as light gray
        d.setColor(Color.LIGHT_GRAY);
        //fill the rectangle
        d.fillRectangle(i, i1, i2, i3);
        d.setColor(Color.black);
        d.drawText(textI, textI1, textStr, textSize);
    }

    /**
     * notify the sprite that time has passed.
     * @param dt seconds number in one frame.
     */
    @Override
    public void timePassed(double dt) {

    }
}