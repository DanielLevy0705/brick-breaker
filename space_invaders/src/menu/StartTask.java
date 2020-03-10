package menu;

import animation.AnimationRunner;
import background.BlockColorBackground;
import background.BlockImageBackground;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import game.GameFlow;
import geometry.Point;
import level.Level;
import level.LevelInformation;
import score.HighScoresTable;
import sprite.Alien;
import background.Background;
import sprite.WallBlock;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * a class that represent the task that starts the actual game.
 *
 * @param <Void> a generic parameter.
 */
public class StartTask<Void> implements Task<Void> {
    private AnimationRunner runner;
    private HighScoresTable scores;
    private KeyboardSensor keyboard;
    private DialogManager dialog;
    private File scoreFile;
    private List<LevelInformation> levels;

    /**
     * Constructor. with parameters to create a new game flow.
     *
     * @param ar   an animation runner.
     * @param ks   a keyboard sensor.
     * @param hst  an high scores table.
     * @param dm   a dialog manager.
     * @param file the file of the scores table.
     */
    public StartTask(AnimationRunner ar, KeyboardSensor ks, HighScoresTable hst, DialogManager dm, File file) {
        this.runner = ar;
        this.keyboard = ks;
        this.scores = hst;
        this.dialog = dm;
        this.scoreFile = file;
    }

    /**
     * make the new game flow run the new level then return null.
     *
     * @return null.
     */
    @Override
    public Void run() {
        this.levels = new ArrayList<LevelInformation>();
        this.levels.add(new Level("Battle no.1", 650, 60, getBackground(),
                getBlocks(), 50, getAliens()));
        new GameFlow(runner, keyboard, scores, dialog, scoreFile).runLevels(this.levels);
        return null;
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
     * getter to the alien image.
     *
     * @return the alien background.
     */
    public Image getAlienBG() {
        String imageFilename = "images/enemy.png";
        Image img = null;
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(imageFilename));
        } catch (IOException e) {
            System.err.println("Could not read image file.");
        }
        return img;
    }

    /**
     * create a new background and return it.
     *
     * @return a new background.
     */
    public Background getBackground() {
        return new Background(Color.BLACK);
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
}