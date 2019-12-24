package score;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * a class for the high score table.
 */
public class HighScoresTable {
    private int tableSize;
    private List<ScoreInfo> scoreInfos;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the maximum size of the table.
     */
    public HighScoresTable(int size) {
        this.tableSize = size;
        this.scoreInfos = new ArrayList<ScoreInfo>();
    }

    /**
     * add a high score.
     *
     * @param score the high score to add.
     */
    public void add(ScoreInfo score) {
        if (getRank(score.getScore()) <= size()) {
            this.scoreInfos.add((getRank(score.getScore()) - 1), score);
        }
        if (this.scoreInfos.size() > size()) {
            this.scoreInfos.remove(this.scoreInfos.size() - 1);
        }
    }

    /**
     * a getter to the maximum size of the table.
     *
     * @return the table maximum size.
     */
    public int size() {
        return this.tableSize;
    }

    /**
     * return the current high scores. the list is sorted such that the highest scores will appear first.
     *
     * @return a list of the high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoreInfos;
    }

    /**
     * return the rank of the current score: where will it be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     *
     * @param score the score that need to get its rank.
     * @return the rank of the score.
     */
    public int getRank(int score) {
        int rank = -1;
        //if there are no other scores this will be the first.
        if (this.scoreInfos.size() == 0) {
            rank = 1;
            return rank;
        }
        //for every score if its smaller than the new one,the new one will take its place.
        for (int i = 0; i < this.scoreInfos.size(); i++) {
            if (this.scoreInfos.get(i).getScore() < score) {
                rank = i + 1;
                break;
            }
        }
        //if our rank stayed -1 it was lower than all the others. if the list isnt full put it in its end.
        if (rank == -1) {
            if (this.scoreInfos.size() < size()) {
                rank = this.scoreInfos.size() + 1;
                //else if its full the rank will be out of the list.
            } else {
                rank = size() + 1;
            }
        }
        //return the rank.
        return rank;
    }

    /**
     * clear the table.
     */
    public void clear() {
        this.scoreInfos = new ArrayList<ScoreInfo>();
    }

    /**
     * load table from data file. current table is clear.
     *
     * @param filename the file that need to load from.
     * @throws IOException if there are problems with the file throw an io exception.
     */
    public void load(File filename) throws IOException {
        this.clear();
        BufferedReader reader = null;
        //try reading the file.
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filename)));
            String line;
            String name = null;
            int score;
            //get the lines and for each line get the name read another line and then the score.
            do {
                line = reader.readLine();
                if (line != null) {
                    name = line;
                }
                line = reader.readLine();
                if (line != null) {
                    score = Integer.parseInt(line);
                    ScoreInfo scoreInfo = new ScoreInfo(name, score);
                    this.scoreInfos.add(scoreInfo);
                }
                //continue while the file isn't over
            } while (line != null);
            //catch file not found exception and io exception and print error messages.
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + filename.getName());
        } catch (IOException e) {
            System.err.println("Failed reading file: " + filename.getName()
                    + ", message:" + e.getMessage());
            e.printStackTrace(System.err);
            //try closing the file if it was opened.
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
    }

    /**
     * save table data to the specified file.
     *
     * @param filename the file to save the data to.
     * @throws IOException an exception if thrown if there are problems with the file.
     */
    public void save(File filename) throws IOException {
        PrintWriter writer = null;
        //try writing into the file.
        try {

            // create output stream with writer wrappers
            writer = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(filename)));

            // write a random line on each line
            for (int i = 0; i < this.scoreInfos.size(); i++) {
                writer.println(scoreInfos.get(i).getName());
                writer.println(scoreInfos.get(i).getScore());
            }
            //if an io exception is thrown catch it and print error message.
        } catch (IOException e) {
            System.err.println("Failed writing file: " + filename.getName()
                    + ", message:" + e.getMessage());
            e.printStackTrace(System.err);
            //try closing the file if it was opened.
        } finally {
            // close stream in case it was created
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Read a table from file and return it. If the file does not exist,
     * or there is a problem with reading it, an empty table is returned.
     *
     * @param filename the file that the table need to be loaded from.
     * @return an high score table.
     */
    public static HighScoresTable loadFromFile(File filename) {
        int size = 5;
        HighScoresTable table = new HighScoresTable(size);
        //try loading the table from the file.
        try {
            table.load(filename);
            //catch an io exception and print an error message.
        } catch (IOException e) {
            System.err.println("Failed reading file: " + filename.getName()
                    + ", message:" + e.getMessage());
        }
        //return the table.
        return table;
    }
}