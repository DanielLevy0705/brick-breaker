package menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * a class that implements menu.Menu and is the menu.Menu animation.
 *
 * @param <T> the task that is chosen by the user.
 */
public class MenuAnimation<T> implements menu.Menu<T> {
    private boolean stop;
    private KeyboardSensor keyboardSensor;
    private List<String> keyList;
    private List<String> messageList;
    private List<Task<T>> tList;
    private T chosen;
    private AnimationRunner ar;

    /**
     * Constructor.
     *
     * @param ks              a keyboard
     * @param animationRunner animation runner to run animations.
     */
    public MenuAnimation(KeyboardSensor ks, AnimationRunner animationRunner) {
        this.keyboardSensor = ks;
        this.ar = animationRunner;
        this.stop = false;
        this.keyList = new ArrayList<String>();
        this.messageList = new ArrayList<String>();
        this.tList = new ArrayList<Task<T>>();
    }

    /**
     * a function to add new tasks to the menu.
     *
     * @param key       the key to select the task.
     * @param message   the message of the task.
     * @param returnVal the task its self.
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        //add the parameters to their lists.
        this.keyList.add(key);
        this.messageList.add(message);
        this.tList.add(new Task<T>() {
            @Override
            public T run() {
                return returnVal;
            }
        });
    }

    /**
     * a function that get the chosen task and return it.
     *
     * @return the chosen task.
     */
    @Override
    public T getStatus() {
        T tempReturnVal = this.chosen;
        this.chosen = null;
        return tempReturnVal;
    }

    /**
     * a function that adds a submenu to the menu.
     *
     * @param key     the key of the submenu.
     * @param message the message of the submenu.
     * @param subMenu the sub menu to add.
     */
    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keyList.add(key);
        this.messageList.add(message);
        this.tList.add(new Task<T>() {
            @Override
            public T run() {
                ar.run(subMenu);
                return subMenu.getStatus();
            }
        });
    }

    /**
     * a function that do one frame of the animation.
     *
     * @param d  the drawing surface.
     * @param dt number of seconds per frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        for (int i = 0; i < this.keyList.size(); i++) {
            if (this.keyboardSensor.isPressed(this.keyList.get(i))) {
                this.chosen = this.tList.get(i).run();
                this.stop = true;
                break;
            }
        }
        //draw the background and the menu headline.
        Image img;
        String imageFilename = "images/menu.jpg";
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imageFilename));
            d.drawImage(0, 0, img);
        } catch (IOException e) {
            System.err.println("Failed reading image file.");
        }
        //draw all of the messages of every task.
        for (int i = 0; i < this.keyList.size(); i++) {
            d.setColor(Color.BLACK);
            d.drawText(230, 330 + (40 * i), "choose: " + this.keyList.get(i)
                    + " to " + this.messageList.get(i), 39);
            d.setColor(Color.WHITE);
            d.drawText(230, 330 + (40 * i), "choose: " + this.keyList.get(i)
                    + " to " + this.messageList.get(i), 40);
        }

    }

    /**
     * a function to stop the animation.
     *
     * @return boolean value that tells us to stop or not.
     */
    @Override
    public boolean shouldStop() {
        boolean tempStop = this.stop;
        this.stop = false;
        return tempStop;
    }
}
