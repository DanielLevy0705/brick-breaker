package sprite;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * a sprite that displays the name of the level on the screen.
 */
public class LevelNameIndicator implements Sprite {
    private String level;

    /**
     * constructor.
     *
     * @param levelName the name of the level.
     */
    public LevelNameIndicator(String levelName) {
        this.level = levelName;
    }

    /**
     * a function that draw on the screen.
     *
     * @param d the drawing surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        //draw the name of the level on the top of the screen and on the left.
        int textI = 600, textI1 = 13, textSize = 10;
        String textStr = "Level Name: " + this.level;
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