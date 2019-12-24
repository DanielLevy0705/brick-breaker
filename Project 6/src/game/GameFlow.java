package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.EndScreen;
import animation.GameLevel;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import level.LevelInformation;
import score.HighScoresTable;
import score.ScoreInfo;
import tools.Counter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * a class that controls the game flow.
 */
public class GameFlow {
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter numOfLives;
    private Counter score;
    private HighScoresTable table;
    private DialogManager dialogManager;
    private File file;
    private boolean lost;

    /**
     * constructor.
     *
     * @param ar  an animation runner that will run the game.
     * @param ks  a keyboard to perform actions in the game.
     * @param dm  the dialog manager.
     * @param f   the file of the high score table.
     * @param hst the high score table.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, HighScoresTable hst, DialogManager dm, File f) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.lost = false;
        //initialize the score to 0 and number of lives to 7
        this.score = new Counter(0);
        this.numOfLives = new Counter(7);
        this.table = hst;
        this.dialogManager = dm;
        this.file = f;
    }

    /**
     * a function to run the levels received from main function.
     *
     * @param levels the list of levels received from main function.
     */
    public void runLevels(List<LevelInformation> levels) {
        // run for every level on the list
        for (LevelInformation levelInfo : levels) {
            //create a new level and give it the values.
            GameLevel level = new GameLevel(levelInfo, this.animationRunner, this.keyboardSensor
                    , this.numOfLives, this.score);
            //initialize the members and parameters of the level.
            level.initialize();
            //while there are blocks and lives left play one turn.
            while ((level.blocksRemained()) && (this.numOfLives.getValue() > 0)) {
                level.playOneTurn();
            }
            //if the lives are over run "EndScreen" animation give false value to WinOrLose.
            if (level.getLives() == 0) {
                Animation end = new EndScreen(false, this.score);
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space", end));
                lost = true;
            }

        }
        if (!lost) {
            //if the game is over and there's lives left run EndScreen animation give true value to WinOrLose.
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space"
                    , new EndScreen(true, this.score)));
        }
        if (this.table.getRank(score.getValue()) <= this.table.size()) {
            String name = dialogManager.showQuestionDialog("Name", "What is your name?", "");
            this.table.add(new ScoreInfo(name, score.getValue()));
            try {
                this.table.save(this.file);
            } catch (IOException e) {
                System.out.println("failed saving the new table");
            }
        }
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor,
                "space", new HighScoresAnimation(this.table)));
        return;
    }

    /**
     * getter to the file that the game flow holds.
     *
     * @return the game flow file.
     */
    public File getFile() {
        return file;
    }

}