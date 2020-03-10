import animation.Animation;
import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import menu.MenuAnimation;
import menu.QuitTask;
import menu.ShowHiScoresTask;
import menu.StartTask;
import menu.Task;
import score.HighScoresTable;

import java.io.File;
import java.io.IOException;

/**
 * a class that hold the main of ass7 game.
 */
public class Ass7Game {
    /**
     * a main to initialize and run the game.
     *
     * @param args an array of strings that tells us which levels to run.
     */
    public static void main(String[] args) {
        //create new gui keyboard and sleeper to send the animation runner and the game flow.
        GUI gui = new GUI("My Beautiful Game", 800, 600);
        DialogManager dm = gui.getDialogManager();
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        Sleeper sleeper = new Sleeper();
        //create a new file for high scores
        File file = new File("highscores.txt");
        //make the high scores table null
        HighScoresTable scores = null;
        //init the frame rate to 60 per second
        int framesPerSecond = 60;
        //create new animation runner
        AnimationRunner ar = new AnimationRunner(framesPerSecond, sleeper, gui);
        //if the file of the high scores table exists load the table.
        if (file.exists()) {
            scores = HighScoresTable.loadFromFile(file);
            //else create new table then save it to the file.
        } else {
            try {
                scores = new HighScoresTable(5);
                scores.save(file);
            } catch (IOException e) {
                System.out.println("failed saving the new table");
            }
        }
        //create new animation for the high scores table.
        Animation scoreAnim = new KeyPressStoppableAnimation(keyboard, "space", new HighScoresAnimation(scores));
        //create new menu and sub menu
        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>(keyboard, ar);
        //add the "start" and the "quit" and "hi scores" tasks to the main menu
        menu.addSelection("h", "Hi scores", new ShowHiScoresTask(ar, scoreAnim));
        menu.addSelection("s", "Start", new StartTask(ar, keyboard, scores, dm, file));
        menu.addSelection("q", "Quit", new QuitTask(gui));
        //continue running the menu and run the tasks until the user quits.
        while (true) {
            ar.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            if (task != null) {
                task.run();
            }
        }
    }
}