package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprite.SpriteCollection;

import java.awt.*;

/**
 * an animation type class that is counting down from 3 to 1.
 */
public class CountDownAnimation implements Animation {
    private double secondsNum;
    private int countFromThis;
    private SpriteCollection screenOfGame;
    private Boolean stop;
    private int milliseconds;
    private Sleeper sleeper;

    /**
     * constructor.
     *
     * @param numOfSeconds the number of seconds that the animation should run.
     * @param countFrom    the number to count down from.
     * @param gameScreen   the screen to show the countdown on.
     */
    public CountDownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.screenOfGame = gameScreen;
        this.countFromThis = countFrom;
        this.secondsNum = numOfSeconds;
        this.sleeper = new Sleeper();
        //initialize this.stop to false
        this.stop = false;
        /* initialize the milliseconds to the amount of milliseconds should be running,
         * divided to the number of seconds + one (for the word "GO").
         */
        this.milliseconds = (int) ((1000 * this.secondsNum) / this.countFromThis + 1);
    }

    /**
     * a function that tells us what to do in one frame of the animation.
     *
     * @param d  the drawing surface.
     * @param dt seconds per frame.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //draw on the drawing surface so that the user will see what is about to happen.
        this.screenOfGame.drawAllOn(d);
        //convert the number that need to be shown to a string.
        String numStr = String.valueOf(this.countFromThis);
        //set the color that you would like the numbers will appear in.
        d.setColor(Color.RED);
        //if the numbers are bigger than 0 draw the number in the middle of the screent.
        if (this.countFromThis > 0) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, numStr + "...", 32);
            //if the counter got to 0 draw the string "GO!" in the middle of the screen.
        } else if (this.countFromThis == 0) {
            d.drawText(d.getWidth() / 2, d.getHeight() / 2, "GO!", 32);
        } else {
            //then stop the animation
            this.stop = true;
        }
        //create new sleeper and tell him how much to sleep.
        sleeper.sleepFor(this.milliseconds);
        //reduce the number of seconds to count from.
        this.countFromThis--;
    }

    /**
     * a boolean function that tells us when the animation should stop.
     *
     * @return a boolean member that tells us if we should stop.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}