package sprite;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is a collection of all of the sprites in a game.
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<Sprite>();

    /**
     * add sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * remove sprite from the collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    /**
     * call timePassed() on all sprites.
     * @param dt seconds number in one frame.
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);
        for (int i = 0; i < spritesCopy.size(); i++) {
            spritesCopy.get(i).timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the surface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);
        for (int i = 0; i < spritesCopy.size(); i++) {
            spritesCopy.get(i).drawOn(d);
        }
    }
}