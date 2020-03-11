package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.EndScreen;
import animation.GameLevel;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import background.BlockColorBackground;
import background.BlockImageBackground;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import geometry.Point;
import level.Level;
import level.LevelInformation;
import score.HighScoresTable;
import score.ScoreInfo;
import sprite.Alien;
import sprite.WallBlock;
import tools.Counter;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private boolean newLevel;

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
        //initialize the score to 0 and number of lives to 3
        this.score = new Counter(0);
        this.numOfLives = new Counter(3);
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
        int i = 1;
        double speedextra = 1;
        // run for every level on the list
        for (LevelInformation levelInfo : levels) {
            while (true) {
                //create a new level and give it the values.
                GameLevel level = new GameLevel(levelInfo, this.animationRunner, this.keyboardSensor
                        , this.numOfLives, this.score);
                //initialize the members and parameters of the level.
                level.initialize();
                //while there are blocks and lives left play one turn.
                if (newLevel) {
                    newLevel = false;
                    level.addSpeed(speedextra);
                }
                while ((level.blocksRemained()) && (this.numOfLives.getValue() > 0)) {
                    level.playOneTurn();
                }
                //if the lives are over run "EndScreen" animation give false value to WinOrLose.
                if (level.getLives() <= 0) {
                    Animation end = new EndScreen(false, this.score);
                    this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space", end));
                    lost = true;
                    break;
                } else {
                    i++;
                    String iStr = String.valueOf(i);
                    levelInfo = new Level("Battle no." + iStr, 650, 60, levelInfo.getBackground(),
                            getBlocks(), 50, getAliens());
                    newLevel = true;
                    speedextra = speedextra * 1.5;
                }
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
    }

    /**
     * a getter to a new Alien list.
     *
     * @return a new Alien list.
     */
    public List<Alien> getAliens() {
        List<Alien> aliens = new ArrayList<Alien>();
        int x = 10, y = 25;
        //use loops to create the aliens in a unique form.
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 5; i++) {
                Alien alien = new Alien(x + (j * 50), y + (i * 40));
                alien.setWidth(40);
                alien.setHeight(30);
                alien.setBlockShape(new geometry.Rectangle(new Point(x + (j * 50), y + (i * 40)), 40, 30));
                alien.setBackground(new BlockImageBackground(getAlienBG()));
                aliens.add(alien);
            }
        }
        return aliens;
    }

    /**
     * a getter to a new WallBlock list.
     *
     * @return a new wallBlock list.
     */
    public List<WallBlock> getBlocks() {
        int x = 75, y = 515;
        List<WallBlock> blocks = new ArrayList<WallBlock>();
        //create a unique form of shields by using loops.
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 33; j++) {
                    WallBlock block = new WallBlock(x + (5 * j), y + (8 * i));
                    block.setWidth(5);
                    block.setHeight(8);
                    block.setBlockShape(new geometry.Rectangle(new Point(x + (j * 5), y + (i * 8)), 5, 8));
                    block.setBackground(new BlockColorBackground(Color.magenta));
                    blocks.add(block);
                }
            }
            x = x + 240;
        }
        return blocks;
    }

    /**
     * getter to the alien image.
     *
     * @return the alien background.
     */
    public Image getAlienBG() {
        String imageFilename = "images/enemy.png";
        Image img = null;
        //try getting the alien image.
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imageFilename));
        } catch (IOException e) {
            System.err.println("Could not read image file.");
        }
        return img;
    }
}