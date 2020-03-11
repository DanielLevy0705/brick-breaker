package sprite;

import biuoop.DrawSurface;
import listener.HitListener;

import java.util.ArrayList;
import java.util.List;

/**
 * a class that represent the formation of the aliens.
 */
public class Aliens implements Sprite {
    private List<Alien> aliensList;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private boolean moveLeft, moveRight, newLevel;
    private double dx, speed;

    /**
     * Constructor.
     *
     * @param aliens the list of aliens in the formation.
     */
    public Aliens(List<Alien> aliens) {
        this.aliensList = aliens;
        this.dx = 75;
        this.moveLeft = false;
        this.moveRight = true;
        this.newLevel = true;
    }

    /**
     * remove an alien from the list.
     *
     * @param alien an alien to remove from the formation.
     */
    public void remove(WallBlock alien) {
        this.aliensList.remove(alien);
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the drawing surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
    }

    /**
     * notify the sprite that time passed.
     *
     * @param dt seconds number in one frame.
     */
    @Override
    public void timePassed(double dt) {
        int borderRight = 800;
        int borderLeft = 0;
        int width = 40;
        double e = 0.0007;
        //if its a new level reset the speed.
        if (newLevel) {
            this.speed = dx * dt;
            newLevel = false;
        }
        //check if the formation should move left or right and move every alien by running on the list.
        if (moveRight) {
            for (int i = 0; i < aliensList.size(); i++) {
                this.aliensList.get(i).moveRight(dt, speed);
            }
            if (this.aliensList.get(this.aliensList.size() - 1).getBlockShape().
                    getUpperLeft().getX() + width + speed + e >= borderRight) {
                for (int i = 0; i < aliensList.size(); i++) {
                    this.aliensList.get(i).moveDown(dt);
                }
                this.moveLeft = true;
                this.moveRight = false;
                this.speed = this.speed * 1.1;
            }
        } else if (moveLeft) {
            for (int i = 0; i < aliensList.size(); i++) {
                this.aliensList.get(i).moveLeft(dt, speed);
            }
            if (this.aliensList.get(0).getBlockShape().
                    getUpperLeft().getX() - speed - e <= borderLeft) {
                for (int i = 0; i < aliensList.size(); i++) {
                    this.aliensList.get(i).moveDown(dt);
                }
                this.moveLeft = false;
                this.moveRight = true;
                this.speed = this.speed * 1.1;
            }
        }
    }

    /**
     * a function to reset the formation.
     */
    public void reset() {
        for (int i = 0; i < this.aliensList.size(); i++) {
            this.aliensList.get(i).reset();
        }
        this.newLevel = true;
        this.moveLeft = false;
        this.moveRight = true;
    }

    /**
     * getter to the list of aliens of the formation.
     *
     * @return the list of aliens of the formation.
     */
    public List<Alien> getAliens() {
        return aliensList;
    }

    /**
     * a function that checks if the player got killed.
     *
     * @return a boolean that say if the player got killed.
     */
    public boolean gotKilled() {
        double shields = 515;
        double y = 0;
        //check if the formation got to the shields.
        for (int i = 0; i < this.aliensList.size(); i++) {
            if (this.aliensList.get(i).getBlockShape().getBottomRight().getY() > y) {
                y = this.aliensList.get(i).getBlockShape().getBottomRight().getY();
            }
        }
        //if it did return true. else return false.
        if (y >= shields) {
            return true;
        }
        return false;
    }

    /**
     * a function that add speed to the formation.
     *
     * @param extra the extra speed to the formation.
     */
    public void addSpeed(double extra) {
        this.dx = this.dx * extra;
    }
}