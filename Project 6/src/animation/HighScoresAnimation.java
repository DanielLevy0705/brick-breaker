package animation;

import biuoop.DrawSurface;
import score.HighScoresTable;

import java.awt.Color;

/**
 * a class to show the high scores table.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable table;

    /**
     * Constructor.
     *
     * @param scores a table of the scores of the users.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.table = scores;
    }

    /**
     * a function that shows the table over the surface.
     *
     * @param d  the drawing surface.
     * @param dt number of seconds per frame.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //draw the high scores animation.
        String highScores = "High Scores";
        String playerName = "Player Name";
        String score = "Score";
        String spaceContinue = "Press space to Continue";
        d.setColor(Color.YELLOW);
        d.drawText(20, 50, highScores, 30);
        d.setColor(Color.GREEN);
        d.drawText(40, 90, playerName, 25);
        d.drawText(500, 90, score, 25);
        d.setColor(Color.ORANGE);
        for (int i = 0; i < this.table.getHighScores().size(); i++) {
            d.drawText(50, 130 + (40 * i), this.table.getHighScores().get(i).getName(), 30);
            d.drawText(500, 130 + (40 * i), String.valueOf(this.table.getHighScores().get(i).getScore()), 30);
        }
    }

    /**
     * a function that once told us when to stop but now is irrelevant.
     *
     * @return boolean value (false).
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}