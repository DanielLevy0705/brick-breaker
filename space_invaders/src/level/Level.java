package level;

import sprite.Alien;
import sprite.Sprite;
import sprite.WallBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that create a new level from file.
 */
public class Level implements LevelInformation {
    private int pSpeed;
    private int pWidth;
    private String levelName;
    private Sprite levelBackground;
    private List<WallBlock> listOfBlocks;
    private int blocksNum;
    private List<Alien> aliensList;

    /**
     * Constructor.
     *
     * @param name        the level name.
     * @param paddleSpeed the paddle speed.
     * @param paddleWidth the paddle width.
     * @param background  the background.
     * @param bList       the blocks list.
     * @param numOfBlocks the number of blocks.
     * @param aList       the aliens list.
     */
    public Level(String name, int paddleSpeed, int paddleWidth,
                 Sprite background, List<WallBlock> bList, int numOfBlocks, List<Alien> aList) {
        this.levelBackground = background;
        this.levelName = name;
        this.pSpeed = paddleSpeed;
        this.pWidth = paddleWidth;
        this.listOfBlocks = new ArrayList<>(bList);
        this.blocksNum = numOfBlocks;
        this.aliensList = new ArrayList<>(aList);
    }

    /**
     * getter to the paddle speed.
     *
     * @return the paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return this.pSpeed;
    }

    /**
     * getter to the paddle width.
     *
     * @return the paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.pWidth;
    }

    /**
     * getter to the level name.
     *
     * @return the level name.
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * getter to the background of the level.
     *
     * @return the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return this.levelBackground;
    }

    /**
     * getter to the list of blocks.
     *
     * @return the list of blocks in this level.
     */
    @Override
    public List<WallBlock> blocks() {
        return this.listOfBlocks;
    }

    /**
     * the number of aliens to remove.
     *
     * @return the number of aliens need to be removed to pass the level.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksNum;
    }

    /**
     * getter to the list of aliens.
     *
     * @return the list of aliens in this level.
     */
    public List<Alien> aliens() {
        return this.aliensList;
    }
}
