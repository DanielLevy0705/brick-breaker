package menu;

import animation.Animation;
import animation.AnimationRunner;

/**
 * a task to show the high scores.
 *
 * @param <Void> a parameter that holds null value.
 */
public class ShowHiScoresTask<Void> implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * Constructor.
     *
     * @param runner              an animation runner to run the task.
     * @param highScoresAnimation an high scores animation that will run on the animationRunner.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * a function to run the task.
     *
     * @return null value.
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}