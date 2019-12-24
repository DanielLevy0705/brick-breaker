package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * a class that "wraps" the animations that are stopped by a key press.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboard;
    private String stopKey;
    private Animation anim;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     * @param ks a keyboard to let us information about what was pressed.
     * @param key if this key is pressed stop the animation.
     * @param animation an animation that need to be stopped if a key is pressed.
     */
    public KeyPressStoppableAnimation(KeyboardSensor ks, String key, Animation animation) {
        this.keyboard = ks;
        this.stopKey = key;
        this.anim = animation;
        //initialize isAlreadyPressed to true.
        isAlreadyPressed = true;
    }

    /**
     * a function that do one frame.
     * @param d the drawing surface.
     * @param dt the number of seconds in one frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //call doOneFrame of the animation.
        this.anim.doOneFrame(d, dt);
    }

    /**
     * a function that tells us when to stop.
     * @return boolean that tells us if we should stop.
     */
    @Override
    public boolean shouldStop() {
        //if a keyboard is pressed return false until its not pressed.
        if (keyboard.isPressed(this.stopKey)) {
            return !isAlreadyPressed;
        }
        //then if its not pressed isAlreadyPressed becomes false then if its still be pressed it will return true.
        this.isAlreadyPressed = false;
        //return false.
        return false;
    }
}