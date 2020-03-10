package menu;

import biuoop.GUI;

/**
 * a task to quit the game.
 *
 * @param <Void> holds null value.
 */
public class QuitTask<Void> implements Task<Void> {
    private GUI g;

    /**
     * Constructor.
     *
     * @param gui a gui to close before quitting the game.
     */
    public QuitTask(GUI gui) {
        this.g = gui;
    }

    /**
     * a function to run the task. will exit the program.
     *
     * @return null value.
     */
    @Override
    public Void run() {
        //close the gui then exit the system.
        g.close();
        System.exit(0);
        return null;
    }
}