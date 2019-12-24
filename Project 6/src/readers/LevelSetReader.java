package readers;

import tools.LvlAndMsg;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

/**
 * a class to read the level set file.
 */
public class LevelSetReader {
    /**
     * get the key,message and path of the level sets.
     *
     * @param reader a reader that reads the file.
     * @return a map that holds a key message and path.
     */
    public Map<String, LvlAndMsg> fromReader(Reader reader) {
        //initialize variables to hold the information.
        LvlAndMsg msgAndLvl = new LvlAndMsg();
        String key = null;
        Map<String, LvlAndMsg> lvlSet = new TreeMap<String, LvlAndMsg>();
        LineNumberReader lnr;
        String line;
        //try reading the level set file to get the key,message and path for each level set.
        try {
            lnr = new LineNumberReader(reader);
            line = lnr.readLine();
            //read till the end of the file.
            while (line != null) {
                //if the line number is even get the path.
                if (lnr.getLineNumber() % 2 == 0) {
                    msgAndLvl.setPath(line);
                    lvlSet.put(key, msgAndLvl);
                    msgAndLvl = new LvlAndMsg();
                    //else get the key and the message.
                } else {
                    String[] arr = line.split(":");
                    key = arr[0];
                    msgAndLvl.setMessage(arr[1]);
                }
                line = lnr.readLine();
            }
            //if an io exception is thrown catch it and print an error message.
        } catch (IOException e) {
            System.err.println("Could not read File.");
            //close the file if it was open.
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Could not close file.");
            }
        }
        //return the level set map.
        return lvlSet;
    }
}