package animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * a class to run animation type objects.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * constructor.
     *
     * @param framesPerSec how many frames to run per seconds.
     * @param gameSleeper  sleeper to make the animation "sleep"
     * @param mainGui      a gui to run the animation on.
     */
    public AnimationRunner(int framesPerSec, Sleeper gameSleeper, GUI mainGui) {
        this.gui = mainGui;
        this.framesPerSecond = framesPerSec;
        this.sleeper = gameSleeper;
    }

    /**
     * a function to run the animation.
     *
     * @param animation the animation to run.
     */
    public void run(Animation animation) {
        //a variable to hold the number of milliseconds per frame.
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        double dt = (1.0 / this.framesPerSecond);
        //loop to run while the animation shouldn't stop.
        while (!animation.shouldStop()) {
            //get the start time
            long startTime = System.currentTimeMillis();
            //get the drawing surface from the gui
            DrawSurface d = gui.getDrawSurface();
            //do one frame
            animation.doOneFrame(d, dt);
            //if the animation should stop, stop it before you show the gui.
            if (animation.shouldStop()) {
                break;
            }
            //show the gui to the user
            gui.show(d);
            //check how much time it took
            long usedTime = System.currentTimeMillis() - startTime;
            //check how much time left to sleep (if there is)
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            //if there is time left to sleep sleep for that amount of time.
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}