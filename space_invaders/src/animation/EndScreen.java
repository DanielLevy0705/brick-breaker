package animation;

import biuoop.DrawSurface;
import tools.Counter;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

/**
 * a function that is an animation displayed when the user has finished the game (won or lost).
 */
public class EndScreen implements Animation {
    private Boolean didIWin;
    private Counter endScore;

    /**
     * constructor.
     *
     * @param winOrLose boolean that tells us if the user won or lost.
     * @param score     the final score of the game.
     */
    public EndScreen(Boolean winOrLose, Counter score) {
        //initialize this.stop as false
        this.didIWin = winOrLose;
        this.endScore = score;
    }

    /**
     * a function that do one frame of the animation.
     *
     * @param d  the drawing surface.
     * @param dt seconds per frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //convert the score into string.
        String strScore = String.valueOf(this.endScore.getValue());
        //if the user won draw on the screen a winning message.
        Image img;
        String winner = "images/you_win.jpg";
        String loser = "images/game_over.jpg";
        if (this.didIWin) {
            try {
                img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(winner));
                d.drawImage(0, 0, img);
            } catch (IOException e) {
                System.err.println("Failed reading image file.");
            }
            d.drawText(270, d.getHeight() - 100, "Your score is " + strScore, 32);
            //else the user lost so draw a "game over' message and stop the animation when the space key is pressed.
        } else {
            try {
                img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(loser));
                d.drawImage(0, 0, img);
            } catch (IOException e) {
                System.err.println("Failed reading image file.");
            }
            d.drawText(270, d.getHeight() - 100, "Your score is " + strScore, 32);
        }
    }

    /**
     * a function that tells us if we should stop the animation.
     *
     * @return a boolean that tells us if we should stop the animation.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
