package listener;

import sprite.Ball;

/**
 * an interface for objects that notifies about hits.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     *
     * @param hl the hit listener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hit listener
     */
    void removeHitListener(HitListener hl);

    /**
     * a function that notifys the hit listener when the block is being hit.
     *
     * @param hitter the ball that is hitting the notifier.
     */
    void notifyHit(Ball hitter);
}