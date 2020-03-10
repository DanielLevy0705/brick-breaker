package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import level.LevelInformation;
import listener.AliensGroupRemover;
import listener.BallRemover;
import listener.BlockRemover;
import listener.ScoreTrackingListener;
import sprite.Alien;
import sprite.Aliens;
import sprite.Ball;
import sprite.LevelNameIndicator;
import sprite.LivesIndicator;
import sprite.Paddle;
import sprite.ScoreIndicator;
import sprite.Sprite;
import sprite.SpriteCollection;
import sprite.WallBlock;
import tools.Counter;

import java.awt.Color;
import java.util.Random;

/**
 * a class that initialize and run the game.
 */
public class GameLevel implements Animation {
    private LevelInformation levelInfo;
    private AnimationRunner runner;
    private Boolean running;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter remainingBlocks;
    private Counter score;
    private Counter numberOfLives;
    private KeyboardSensor keyboard;
    private Paddle paddle;
    private Aliens aliens;
    private long newShot, lastShot;
    private boolean paused;

    /**
     * constructor.
     *
     * @param level the current level to play.
     * @param ar    the animation runner that runs the animation.
     * @param ks    the keyboard to perform actions with.
     * @param lives the lives of the player.
     * @param score the score of the player.
     */
    public GameLevel(LevelInformation level, AnimationRunner ar, KeyboardSensor ks, Counter lives, Counter score) {
        this.levelInfo = level;
        this.runner = ar;
        this.keyboard = ks;
        this.score = score;
        this.numberOfLives = lives;
        this.running = true;
        this.newShot = 0;
        this.lastShot = 0;
        this.paused = false;
    }

    /**
     * add collidable objects to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * add sprite objects to the sprite collection of the game.
     *
     * @param s the sprite object to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * remove the given collidable from the environment.
     *
     * @param c the collidable which is removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * remove the given sprite from the environment.
     *
     * @param s the sprite which is removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Sprite.Sprite.Ball (and Sprite.Sprite.Paddle)
     * and add them to the game.
     */
    public void initialize() {
        //initialize the members of the game and the keyboard sensor
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.remainingBlocks = new Counter(0);
        //variables that tell us how many frames per second and how many milliseconds per frame
        ScoreIndicator si = new ScoreIndicator(this.score);
        LivesIndicator li = new LivesIndicator(this.numberOfLives);
        LevelNameIndicator lni = new LevelNameIndicator(levelInfo.levelName());
        addSprite(levelInfo.getBackground());
        addSprite(si);
        addSprite(li);
        addSprite(lni);
        addBlocks();
        addSprite(this.aliens);
    }

    /**
     * Run the game start the animation loop.
     */
    public void playOneTurn() {
        this.paddle = createPaddle();
        this.runner.run(new CountDownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
        this.paddle.removeFromGame(this);
    }

    /**
     * getter function to Game environment.
     *
     * @return this game environment
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * a function to create the paddle's rectangle.
     *
     * @param pX the paddle x
     * @param pY the paddle y
     * @param pW the paddle width
     * @param pH the paddle height
     * @return the paddle rectangle
     */
    public Rectangle createPaddleRect(double pX, double pY, double pW, double pH) {
        double paddleStartX = pX, paddleStartY = pY, paddleWidth = pW, paddleHeight = pH;
        Rectangle paddleRect = new Rectangle(new Point(paddleStartX, paddleStartY), paddleWidth, paddleHeight);
        return paddleRect;
    }

    /**
     * create blocks for the game.
     */
    public void addBlocks() {
        this.aliens = new Aliens(levelInfo.aliens());
        //run the loop to add the blocks and its listeners.
        for (WallBlock block : levelInfo.blocks()) {
            BlockRemover brhl = new BlockRemover(this, this.remainingBlocks);
            block.addHitListener(brhl);
            BallRemover br = new BallRemover(this);
            block.addHitListener(br);
            //add the blocks to the game
            block.addToGame(this);
        }
        for (Alien alien : levelInfo.aliens()) {
            ScoreTrackingListener stl = new ScoreTrackingListener(this.score);
            alien.addHitListener(stl);
            BlockRemover brhl = new BlockRemover(this, this.remainingBlocks);
            alien.addHitListener(brhl);
            BallRemover br = new BallRemover(this);
            alien.addHitListener(br);
            AliensGroupRemover agl = new AliensGroupRemover(this.aliens);
            alien.addHitListener(agl);
            //add the blocks to the game
            alien.addToGame(this);
        }
        //increase the amount of the remaining blocks with numberOfBlocksToRemove.
        this.remainingBlocks.increase(levelInfo.numberOfBlocksToRemove());
    }

    /**
     * a function that do one frame of the animation.
     *
     * @param d  the drawing surface.
     * @param dt seconds per frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //draw all of the sprites.
        this.sprites.drawAllOn(d);
        //notify the sprites the time passed
        this.sprites.notifyAllTimePassed(dt);
        //if there are no blocks left increase the score by 100 and stop running.
        if (this.remainingBlocks.getValue() == 0 || this.numberOfLives.getValue() == 0) {
            this.running = false;
        }
        if (this.keyboard.isPressed("space") && !paused) {
            paddle.fire(this);
        } else if (!this.keyboard.isPressed("space")) {
            paused = false;
        }
        //if the p key is pressed pause the animation.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
            paused = true;
        }
        if (this.aliens.getAliens().size() > 0) {
            alienShoot();
        }
        if (this.aliens.gotKilled() || this.paddle.getGotShot()) {
            removeBalls();
            reset();
            this.running = false;
        }
    }

    /**
     * a function that tells us when to stop the animation.
     *
     * @return a boolean that tells us if the animation should stop running.
     */
    @Override
    public boolean shouldStop() {
        return (!this.running);
    }

    /**
     * a function that creates paddle, possibly with balls.
     *
     * @return the paddle that was created.
     */
    public Paddle createPaddle() {
        //create the paddle in the middle of the screen width and just above the bottom with the parameters received.
        double pW = levelInfo.paddleWidth(), pS = levelInfo.paddleSpeed();
        double pX = 400 - (pW / 2), pY = 560, pH = 10;
        Color pC = Color.cyan;
        Paddle paddle1 = new Paddle(this.keyboard, createPaddleRect(pX, pY, pW, pH), pC, pS);
        //add the paddle to the game and if it should be with balls create the balls on top of it.
        paddle1.addToGame(this);
        return paddle1;
    }

    /**
     * getter to the number of lives.
     *
     * @return the value of the number of lives.
     */
    public int getLives() {
        return this.numberOfLives.getValue();
    }

    /**
     * boolean function that tells us if there are still blocks in this level.
     *
     * @return boolean that tells us if there are blocks in this level.
     */
    public Boolean blocksRemained() {
        if (this.remainingBlocks.getValue() > 0) {
            return true;
        }
        return false;
    }

    /**
     * a function that makes aliens shoot.
     */
    public void alienShoot() {
        int x = getColX();
        boolean found = false;
        Alien shooter = null;
        //get the alien that needs to shoot.
        for (int i = 0; i < this.aliens.getAliens().size(); i++) {
            if ((int) this.aliens.getAliens().get(i).getBlockShape().getUpperLeft().getX() == x) {
                shooter = this.aliens.getAliens().get(i);
                found = true;
            } else {
                if (found) {
                    break;
                }
            }
        }
        //shoot every 500 milliseconds.
        newShot = System.currentTimeMillis();
        if (newShot - lastShot > 500 && shooter != null) {
            shooter.fire(this);
            lastShot = System.currentTimeMillis();
        }
    }

    /**
     * a function that gets the x of the shooter's column.
     *
     * @return the x of the shooter's column.
     */
    public int getColX() {
        int columns = 1;
        int tempX, x = 0, colX = -1;
        //get the amount of columns.
        if (!this.aliens.getAliens().isEmpty()) {
            x = (int) this.aliens.getAliens().get(0).getBlockShape().getUpperLeft().getX();
        }
        for (int i = 0; i < this.aliens.getAliens().size(); i++) {
            tempX = (int) this.aliens.getAliens().get(i).getBlockShape().getUpperLeft().getX();
            if (tempX != x) {
                columns++;
                x = tempX;
            }
        }
        //get a random column.
        Random rand = new Random();
        int col = (rand.nextInt(columns) + 1);
        columns = 1;
        //get the x value of the chosen column.
        if (!this.aliens.getAliens().isEmpty()) {
            x = (int) this.aliens.getAliens().get(0).getBlockShape().getUpperLeft().getX();
        }
        for (int i = 0; i < this.aliens.getAliens().size(); i++) {
            tempX = (int) this.aliens.getAliens().get(i).getBlockShape().getUpperLeft().getX();
            if (col == columns) {
                colX = x;
                break;
            }
            if (x != tempX) {
                columns++;
                x = tempX;
            }
            if (col == columns) {
                colX = x;
                break;
            }
        }
        //return that x value.
        return colX;
    }

    /**
     * a function to reset the game when the player looses a life.
     */
    public void reset() {
        this.paddle.reset();
        this.aliens.reset();
        this.numberOfLives.decrease(1);
    }

    /**
     * a function to remove the balls when the player looses a life.
     */
    public void removeBalls() {
        for (Ball ball : this.paddle.getPaddleBalls()) {
            ball.removeFromGame(this);
        }
        for (Alien alien : this.aliens.getAliens()) {
            for (Ball ball : alien.getAlienBalls()) {
                ball.removeFromGame(this);
            }
        }
    }

    /**
     * a function to add speed to the new level.
     *
     * @param newExtra the extra speed.
     */
    public void addSpeed(double newExtra) {
        this.aliens.addSpeed(newExtra);
    }
}