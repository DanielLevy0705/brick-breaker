package score;

/**
 * a class that holds info on the score.
 */
public class ScoreInfo {
    private String scoreName;
    private int scoreNum;

    /**
     * Constructor.
     *
     * @param name  the name of the user.
     * @param score the score of the user.
     */
    public ScoreInfo(String name, int score) {
        this.scoreNum = score;
        this.scoreName = name;
    }

    /**
     * getter to the name.
     *
     * @return the name of the user.
     */
    public String getName() {
        return this.scoreName;
    }

    /**
     * getter to the score.
     *
     * @return the user's score.
     */
    public int getScore() {
        return this.scoreNum;
    }
}