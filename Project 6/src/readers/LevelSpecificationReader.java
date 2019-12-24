package readers;

import background.BackgroundFromFile;
import block.BlocksFromSymbolsFactory;
import level.LevelFromFile;
import level.LevelInformation;
import sprite.Block;
import sprite.Sprite;
import tools.ColorParser;
import tools.Velocity;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a class to read the levels file.
 */
public class LevelSpecificationReader {
    /**
     * a function that returns a list of level information.
     *
     * @param reader a reader that reads the file.
     * @return a list of level information.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> levelsList = new ArrayList<LevelInformation>();
        //split the file into block defs and level defs.
        Map<String, List<List<String>>> blocksAndLvls = splitLevel(reader);
        List<List<String>> splitLevels = blocksAndLvls.get("levelDefinitions");
        List<List<String>> blockLvls = blocksAndLvls.get("blockDefs");
        //for each level in the level defs create new level info and add it to the list.
        for (int i = 0; i < splitLevels.size(); i++) {
            levelsList.add(createLevelInfo(splitLevels.get(i), blockLvls.get(i)));
        }
        //return the list of levels.
        return levelsList;
    }

    /**
     * a function that splits the level into block definitions and levels definitions.
     *
     * @param reader reader to read the file and get the strings.
     * @return a new map of block defs and level defs.
     */
    private Map<String, List<List<String>>> splitLevel(java.io.Reader reader) {
        // create new lists and a map
        List<List<String>> levelDefinitions = new ArrayList<List<String>>();
        List<List<String>> blockDefs = new ArrayList<List<String>>();
        Map<String, List<List<String>>> blocksAndLvls = new TreeMap<String, List<List<String>>>();
        List<String> levelDefinition = null;
        List<String> blockDef = null;
        //init the reader with null.
        BufferedReader br = null;
        //a variable to check if the line is not in the block sector.
        boolean notInBlocks = true;
        try {
            // create a new buffer reader and read the lines of the file.
            br = new BufferedReader(reader);
            String line;
            line = br.readLine();
            //read until the end of the file.
            while (line != null) {
                //create new list for blocks defs if the line is start blocks
                if (line.equals("START_BLOCKS")) {
                    blockDef = new ArrayList<String>();
                    notInBlocks = false;
                    line = br.readLine();
                    //add the list to the list of lists when the line is end blocks.
                } else if (line.equals("END_BLOCKS")) {
                    notInBlocks = true;
                    blockDefs.add(blockDef);
                    line = br.readLine();
                }
                //if the line is a start of a level create new list for level defs.
                if (line.equals("START_LEVEL")) {
                    levelDefinition = new ArrayList<String>();
                    //if its the end of the level add the list to the list of lists.
                } else if (line.equals("END_LEVEL")) {
                    levelDefinitions.add(levelDefinition);
                    //add the line to the level defs list if the line is relevant
                } else if (levelDefinition != null && !line.startsWith("#") && !line.trim().isEmpty() && notInBlocks) {
                    levelDefinition.add(line);
                    //add the line to the block defs list if the line is relevant
                } else if (blockDef != null && !line.startsWith("#") && !line.trim().isEmpty() && !notInBlocks) {
                    blockDef.add(line);
                }
                //keep reading the lines.
                line = br.readLine();
            }
            //if an io exception is thrown catch it and print a message.
        } catch (IOException e) {
            System.err.println("Failed reading file." + "message:" + e.getMessage());
            //in any case if the buffered reader is not null close the file.
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                //if an io exception is thrown catch it and print error message.
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }
        //add the level and block defs lists to the map.
        blocksAndLvls.put("levelDefinitions", levelDefinitions);
        blocksAndLvls.put("blockDefs", blockDefs);
        //return the map.
        return blocksAndLvls;
    }

    /**
     * a function that creates a new level information object.
     *
     * @param singleLevel a list of a single level definitions.
     * @param blockLvl    a list of a single level block definitions.
     * @return new level information.
     */
    private LevelInformation createLevelInfo(List<String> singleLevel, List<String> blockLvl) {
        //create a new map of strings and for every string in the level split it by regex ":"
        Map<String, String> map = new TreeMap<String, String>();
        for (String string : singleLevel) {
            String[] strings = string.split(":");
            //put the splitted line in the map.
            map.put(strings[0], strings[1]);
        }
        //create a list of blocks from the function.
        List<Block> bList = blockList(parseBlocks(map), blockLayout(blockLvl));
        //return the value returned from the function.
        return fromKeyAndValue(map, bList);
    }

    /**
     * a function that get the level information from the keys and values of the map.
     *
     * @param keyAndVal a map that holds the level info by keys.
     * @param bList     a list of blocks of the level.
     * @return a new level information.
     */
    private LevelInformation fromKeyAndValue(Map<String, String> keyAndVal, List<Block> bList) {
        //get the values of the information on the level from the map.
        String levelName = keyAndVal.get("level_name");
        List<Velocity> vList = getVelocities(keyAndVal);
        int paddleSpeed = Integer.parseInt(keyAndVal.get("paddle_speed"));
        int paddleWidth = Integer.parseInt(keyAndVal.get("paddle_width"));
        Sprite background = getBackground(keyAndVal);
        int blocksNum = Integer.parseInt(keyAndVal.get("num_blocks"));
        // create a new level using the values from the map and the list of blocks received above
        LevelInformation levelInfo = new LevelFromFile(levelName, vList, paddleSpeed, paddleWidth,
                background, bList, blocksNum);
        //return the new level info.
        return levelInfo;
    }

    /**
     * a function to get the list of velocities out of the map.
     *
     * @param keyAndVal a map that holds the value of the velocities
     * @return the list of the velocities.
     */
    private List<Velocity> getVelocities(Map<String, String> keyAndVal) {
        //create a new list of velocities and get its value out of the map.
        List<Velocity> velocities = new ArrayList<Velocity>();
        String strVelocities = keyAndVal.get("ball_velocities");
        //split the strings by space and comma, then create new velocities and add them to the list.
        String[] differVels = strVelocities.split(" ");
        for (String v : differVels) {
            String[] angleAndSpeed = v.split(",");
            Velocity velocity = Velocity.fromAngleAndSpeed(Double.parseDouble(angleAndSpeed[0])
                    , Double.parseDouble(angleAndSpeed[1]));
            velocities.add(velocity);
        }
        //return the list.
        return velocities;
    }

    /**
     * a function to get the background out of the map.
     *
     * @param keyAndVal the map that holds the background
     * @return the background.
     */
    private Sprite getBackground(Map<String, String> keyAndVal) {
        Sprite background;
        String filename;
        String backgroundStr = keyAndVal.get("background");
        String fillVal;
        Color color;
        //check the prefix of the background string. if its image create a new background with the file name.
        if (backgroundStr.startsWith("image")) {
            filename = backgroundStr.substring((backgroundStr.indexOf("(") + 1), backgroundStr.lastIndexOf(")"));
            background = new BackgroundFromFile(filename);
            //else its a color
        } else {
            //get the fill value out of the string.
            fillVal = backgroundStr.substring((backgroundStr.lastIndexOf("(") + 1), backgroundStr.indexOf(")"));
            //if the prefix of the background string is RGB extract the rgb values and create the new background.
            if (backgroundStr.startsWith("color(RGB")) {
                String[] rgbVals = fillVal.split(",");
                int r = Integer.parseInt(rgbVals[0]);
                int g = Integer.parseInt(rgbVals[1]);
                int b = Integer.parseInt(rgbVals[2]);
                color = new Color(r, g, b);
                //else get the color from color parser class and create the new background.
            } else {
                color = new ColorParser().getColor(fillVal);
            }
            background = new BackgroundFromFile(color);
        }
        //return the new background.
        return background;
    }

    /**
     * get the block layout of the level.
     *
     * @param level list of strings holds the level definitions.
     * @return list of strings holds the block layout
     */
    private List<String> blockLayout(List<String> level) {
        List<String> blockLayout = new ArrayList<String>();
        for (String s : level) {
            if (!s.startsWith("#") && !s.trim().isEmpty()) {
                blockLayout.add(s);
            }
        }
        return blockLayout;
    }

    /**
     * function to get the block information out of the level information.
     *
     * @param keyAndVal a map holds the level information.
     * @return a map holds the block information.
     */
    private Map<String, String> parseBlocks(Map<String, String> keyAndVal) {
        //extract the information out of the key and val map and put it in the new block map.
        Map<String, String> blockMap = new TreeMap<String, String>();
        blockMap.put("block_definitions", keyAndVal.get("block_definitions"));
        blockMap.put("blocks_start_x", keyAndVal.get("blocks_start_x"));
        blockMap.put("blocks_start_y", keyAndVal.get("blocks_start_y"));
        blockMap.put("row_height", keyAndVal.get("row_height"));
        blockMap.put("num_blocks", keyAndVal.get("num_blocks"));
        //return the block map.
        return blockMap;
    }

    /**
     * a function to create a new list of blocks.
     *
     * @param parsedBlocks the information about the blocks.
     * @param layout       the blocks layout.
     * @return a new list of blocks.
     */
    private List<Block> blockList(Map<String, String> parsedBlocks, List<String> layout) {
        //try loading the input stream with the path of block definitions.
        InputStream is;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(parsedBlocks.get("block_definitions"));
            Reader reader = new InputStreamReader(is);
            BlocksFromSymbolsFactory bfsf = BlocksDefinitionReader.fromReader(reader);
            //create a new list of blocks and get the x and y values of the blocks
            List<Block> listOfBlocks = new ArrayList<Block>();
            int x = Integer.parseInt(parsedBlocks.get("blocks_start_x"));
            int y = Integer.parseInt(parsedBlocks.get("blocks_start_y"));
            int firstX = x;
            Character ch;
            String symbol;
            //for every symbol in the layout create a block by the symbol definitions.
            for (String s : layout) {
                for (int i = 0; i < s.length(); i++) {
                    ch = s.charAt(i);
                    symbol = ch.toString();
                    //check if its a block and if it is get the block and move the x forward.
                    if (bfsf.isBlockSymbol(symbol)) {
                        listOfBlocks.add(bfsf.getBlock(symbol, x, y));
                        //if its a spacer move the x forward.
                        x += bfsf.getBlock(symbol, x, y).getBlockShape().getWidth();
                    } else if (bfsf.isSpaceSymbol(symbol)) {
                        x += bfsf.getSpaceWidth(symbol);
                    }
                }
                //if its the end of the line (row) move the y forward and get x to the head of the line(row) value.
                y += Integer.parseInt(parsedBlocks.get("row_height"));
                x = firstX;
            }
            //return the new list of blocks.
            return listOfBlocks;
            //if an exception is thrown print an error message and throw a runtime exception.
        } catch (Exception e) {
            System.err.println("Unable to find file: " + parsedBlocks.get("blocks_definitions"));
            throw new RuntimeException("Unable to find blocks_definitions file.");
        }
    }
}