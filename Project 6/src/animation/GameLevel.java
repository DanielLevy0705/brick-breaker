package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import collision.GameEnvironment;
import geometry.Point;
import geometry.Rectangle;
import level.LevelInformation;
import listener.BallRemover;
import listener.BlockRemover;
import listener.ScoreTrackingListener;
import sprite.Ball;
import sprite.Block;
import sprite.LevelNameIndicator;
import sprite.LivesIndicator;
import sprite.Paddle;
import sprite.ScoreIndicator;
import sprite.Sprite;
import sprite.SpriteCollection;
import tools.Counter;
import tools.Velocity;

import java.awt.Color;

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
    private Counter remainingBalls;
    private Counter score;
    private Counter numberOfLives;
    private KeyboardSensor keyboard;

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
        this.remainingBalls = new Counter(0);
        //variables that tell us how many frames per second and how many milliseconds per frame
        ScoreIndicator si = new ScoreIndicator(this.score);
        LivesIndicator li = new LivesIndicator(this.numberOfLives);
        LevelNameIndicator lni = new LevelNameIndicator(levelInfo.levelName());
        addSprite(levelInfo.getBackground());
        addSprite(si);
        addSprite(li);
        addSprite(lni);
        addBlocks();
        //initialize the parameters of the blocks
        double limit1 = 800, limit2 = 600;
        //add the limit blocks
        addLimitBlocks(limit1, limit2);
    }

    /**
     * Run the game start the animation loop.
     */
    public void playOneTurn() {
        Paddle paddle = createPaddle(true, 6);
        this.runner.run(new CountDownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
        paddle.removeFromGame(this);
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
     * a function to add limit blocks to the game.
     *
     * @param limit1 the limit on the x axis
     * @param limit2 the limit on the y axis
     */
    public void addLimitBlocks(double limit1, double limit2) {
        int lbSize = 25;
        BallRemover dr = new BallRemover(this, remainingBalls);
        //create new rectangles and block with the parameters and limits with gray color
        Rectangle top = new Rectangle(new Point(0, 15), limit1, lbSize);
        Rectangle bottom = new Rectangle(new Point(0, limit2), limit1, lbSize);
        Rectangle left = new Rectangle(new Point(0, lbSize), lbSize, limit2);
        Rectangle right = new Rectangle(new Point(limit1 - lbSize, lbSize), lbSize, limit2);
        Block topB = new Block(top, Color.GRAY, 0);
        Block deathRegion = new Block(bottom, Color.GRAY, 0);
        Block leftB = new Block(left, Color.GRAY, 0);
        Block rightB = new Block(right, Color.GRAY, 0);
        //add all of the blocks to the game.
        topB.addToGame(this);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(dr);
        leftB.addToGame(this);
        rightB.addToGame(this);
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
        //run the loop to add the blocks and its listeners.
        for (Block block : levelInfo.blocks()) {
            BlockRemover brhl = new BlockRemover(this, this.remainingBlocks);
            ScoreTrackingListener stl = new ScoreTrackingListener(this.score);
            block.addHitListener(brhl);
            block.addHitListener(stl);
            //add the blocks to the game
            block.addToGame(this);
        }
        //increase the amount of the remaining blocks with numberOfBlocksToRemove.
        this.remainingBlocks.increase(levelInfo.numberOfBlocksToRemove());
    }

    /**
     * a function to add a ball to the game.
     *
     * @param x the x of the center
     * @param y the y of the center
     * @param r the radius of the ball
     * @param c the color of the ball
     * @param v the velocity of the ball
     */
    public void addBall(double x, double y, int r, Color c, Velocity v) {
        Point center = new Point(x, y);
        Ball ball = new Ball(center, r, c);
        ball.setVelocity(v);
        //set the game environment of the ball
        ball.setGameEnv(this.getEnvironment());
        ball.addToGame(this);
    }

    /**
     * a function that create a ball for every velocity and place it on top of the paddle.
     *
     * @param x the x of the ball
     * @param y the y of the ball
     * @param r the radius of the ball
     */
    public void createBallsOnTopOfPaddle(double x, double y, int r) {
        for (int i = 0; i < levelInfo.numberOfBalls(); i++) {
            addBall(x, y - r, r, Color.white, levelInfo.initialBallVelocities().get(i));
        }
        //increase the remaining balls with the number of balls.
        this.remainingBalls.increase(levelInfo.numberOfBalls());
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
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        }
        //if there are no balls left decrease the number of lives by 1 and stop running
        if (this.remainingBalls.getValue() == 0) {
            this.numberOfLives.decrease(1);
            this.running = false;
        }
        //if the p key is pressed pause the animation.
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
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
     * @param withBalls tells us if the paddle should be created with balls on top it.
     * @param r         if the paddle is created with balls tell us the radius of the balls, else insert radius 0.
     * @return the paddle that was created.
     */
    public Paddle createPaddle(Boolean withBalls, int r) {
        //create the paddle in the middle of the screen width and just above the bottom with the parameters received.
        double pW = levelInfo.paddleWidth(), pS = levelInfo.paddleSpeed();
        double pX = 400 - (pW / 2), pY = 560, pH = 10;
        Color pC = Color.orange;
        Paddle paddle = new Paddle(this.keyboard, createPaddleRect(pX, pY, pW, pH), pC, pS);
        //add the paddle to the game and if it should be with balls create the balls on top of it.
        paddle.addToGame(this);
        if (withBalls) {
            createBallsOnTopOfPaddle(pX + (pW / 2), pY, r);
        }
        return paddle;
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
}