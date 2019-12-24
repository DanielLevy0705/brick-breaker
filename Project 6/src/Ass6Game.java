import animation.Animation;
import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import game.GameFlow;
import level.LevelInformation;
import menu.MenuAnimation;
import menu.QuitTask;
import menu.ShowHiScoresTask;
import menu.Task;
import readers.LevelSetReader;
import readers.LevelSpecificationReader;
import score.HighScoresTable;
import tools.LvlAndMsg;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * this class includes a main function to initialize and run the game.
 *
 * @author Daniel Levy
 */
public class Ass6Game {
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
        String path = null;
        //if there are no arguments use the level_sets as path.
        if (args.length == 0) {
            path = "level_sets.txt";
            //else get the path from the args list
        } else {
            path = args[0];
        }
        //create a new map of Two Strings and a list of levels.
        Map<String, LvlAndMsg> tasks = null;
        //create new level set reader and a reader with the path received above
        LevelSetReader lsr = new LevelSetReader();
        Reader reader;
        final InputStream[] is = new InputStream[1];
        is[0] = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        reader = new InputStreamReader(is[0]);
        //get the task and the level to run.
        tasks = lsr.fromReader(reader);
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
        MenuAnimation<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(keyboard, ar);
        //add the sub menu and the "quit" and "hi scores" tasks to the main menu
        menu.addSelection("h", "Hi scores", new ShowHiScoresTask(ar, scoreAnim));
        final Reader[] newReader = new Reader[1];
        final InputStream[] newIs = new InputStream[1];
        //for every entry of the map add new tasks to the sub menu with the key and value of the map.
        for (Map.Entry<String, LvlAndMsg> entry : tasks.entrySet()) {
            HighScoresTable finalScores = scores;
            subMenu.addSelection(entry.getKey(), entry.getValue().getMessage()
                    , new Task<Void>() {
                        @Override
                        public Void run() {
                            newIs[0] = ClassLoader.getSystemClassLoader()
                                    .getResourceAsStream(entry.getValue().getPath());
                            newReader[0] = new InputStreamReader(newIs[0]);
                            List<LevelInformation> lvls = new LevelSpecificationReader().fromReader(newReader[0]);
                            new GameFlow(ar, keyboard, finalScores, dm, file).runLevels(lvls);
                            return null;
                        }
                    });
        }
        subMenu.addSelection("r", "Return", new Task<Void>() {
            @Override
            public Void run() {
                return null;
            }
        });
        menu.addSubMenu("s", "Start", subMenu);
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