package readers;

import background.BlockBackground;
import background.BlockColorBackground;
import background.BlockImageBackground;
import background.BlockStroke;
import block.BlockCreator;
import block.BlockCreatorClass;
import block.BlocksFromSymbolsFactory;
import tools.ColorParser;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * a class to read the block definitions.
 */
public class BlocksDefinitionReader {
    /**
     * a static function that reads a file and return blocksFromSymbolsFactory object.
     *
     * @param reader a reader that reads a file.
     * @return a blocksFromSymbolsFactory object to create blocks from symbols.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        List<String> lines = readLines(reader);
        return parseBlockDef(lines);
    }

    /**
     * a function that reads the lines and return a list of relevant separate lines.
     *
     * @param reader a reader that reads the file
     * @return a list of separate lines.
     */
    public static List<String> readLines(java.io.Reader reader) {
        List<String> lines = new ArrayList<String>();
        String line;
        BufferedReader br = null;
        //try reading the file till its end and add every relevant line.
        try {
            br = new BufferedReader(reader);
            line = br.readLine();
            while (line != null) {
                if (!line.trim().isEmpty() && !line.startsWith("#")) {
                    lines.add(line);
                }
                line = br.readLine();
            }
            //return the lines.
            return lines;
            //if an io exception is thrown print an error message and throw new run time exception.
        } catch (IOException e) {
            System.err.println("Failed reading from blockdef file.");
            throw new RuntimeException("Failed reading from blockdef file.");
            //try closing the file if it was opened.
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing blockdef file.");
            }
        }
    }

    /**
     * a function that get a list of strings and return a blocksFromSymbolsFactory object.
     *
     * @param list a list of Strings.
     * @return a block.BlocksFromSymbolsFactory object.
     */
    public static BlocksFromSymbolsFactory parseBlockDef(List<String> list) {
        List<String> defs = new ArrayList<String>();
        List<String> bdefs = new ArrayList<String>();
        List<String> sdefs = new ArrayList<String>();
        //for every string in the list part the list to different list: defaults block definitions and spacers.
        for (String s : list) {
            if (s.startsWith("default")) {
                defs.add(s);
            } else if (s.startsWith("bdef")) {
                bdefs.add(s);
            } else if (s.startsWith("sdef")) {
                sdefs.add(s);
            }
        }
        //create a new map of strings (spacers symbols) and integers(spacers width).
        Map<String, Integer> spacers = parseSpacers(sdefs);
        //create a new map of strings(block symbols) and block creators.
        Map<String, BlockCreator> creators = parseCreators(defs, bdefs);
        //return a block from symbolsFactory object.
        return new BlocksFromSymbolsFactory(spacers, creators);
    }

    /**
     * a function that get a mapping between a symbol and width of spacers.
     *
     * @param sdefs a list of strings that is related to spacers.
     * @return a map of strings and integers.
     */
    public static Map<String, Integer> parseSpacers(List<String> sdefs) {
        String[] symbolAndWidth;
        Map<String, Integer> spacers = new TreeMap<String, Integer>();
        String symbol;
        Integer width;
        // for every string in the list split it to get the symbols and the width.
        for (String s : sdefs) {
            symbolAndWidth = s.split(" ");
            symbol = symbolAndWidth[1].split(":")[1];
            width = Integer.parseInt(s.split("width:")[1]);
            spacers.put(symbol, width);
        }
        //return the map of spacers.
        return spacers;
    }

    /**
     * a function that get a mapping between block symbols and block creators.
     *
     * @param defs  a list of strings of the default values.
     * @param bdefs a list of strings of the block values.
     * @return a map of symbols and block creators.
     */
    public static Map<String, BlockCreator> parseCreators(List<String> defs, List<String> bdefs) {
        String[] defArr;
        Map<String, BlockCreator> creatorMap = new TreeMap<String, BlockCreator>();
        Map<String, String> map = new TreeMap<String, String>();
        Map<String, String> map1;
        Map<Integer, BlockBackground> fillMap = new TreeMap<Integer, BlockBackground>();
        BlockCreatorClass bc = new BlockCreatorClass();
        BlockCreatorClass bc1;
        //for every string in the defaults,split it, and add to the first map.
        for (String s : defs) {
            if (s.startsWith("default")) {
                s = s.substring(s.indexOf(" ") + 1);
            }
            defArr = s.split(" ");
            for (String def : defArr) {
                if (def.startsWith("fill")) {
                    fillMap.put(getFillMapInt(def), getFillMapBG(def));
                } else {
                    map.put(def.split(":")[0], def.split(":")[1]);
                }
            }
            //if the first map contains any of the values set the block creator with these values.
            if (map.containsKey("height")) {
                bc.setHeight(Integer.parseInt(map.get("height")));
            }
            if (map.containsKey("width")) {
                bc.setWidth(Integer.parseInt(map.get("width")));
            }
            if (map.containsKey("stroke")) {
                String strokeColor = map.get("stroke").substring(map.get("stroke").indexOf("(") + 1,
                        map.get("stroke").lastIndexOf(")"));

                bc.setStroke(new BlockStroke(new ColorParser().getColor(strokeColor)));
            }
            if (map.containsKey("hit_points")) {
                bc.setHp(Integer.parseInt(map.get("hit_points")));
                //run for every hit point and check if the fillMap contains it,if it does add new background.
                for (int i = 0; i <= Integer.parseInt(map.get("hit_points")); i++) {
                    if (fillMap.containsKey(i)) {
                        if (i >= 1) {
                            bc.addBackground(i, fillMap.get(i));
                            //if the index is 0 set the default background.
                        } else {
                            bc.setBackground(fillMap.get(i));
                        }
                    }
                }
            }

        }
        //for every string in the bdefs,split it, and add to the second map.
        for (String s : bdefs) {
            bc1 = new BlockCreatorClass();
            map1 = new TreeMap<String, String>();
            if (s.startsWith("bdef")) {
                s = s.substring(s.indexOf(" ") + 1);
            }
            defArr = s.split(" ");
            for (String def : defArr) {
                if (def.startsWith("fill")) {
                    fillMap.put(getFillMapInt(def), getFillMapBG(def));
                } else {
                    map1.put(def.split(":")[0], def.split(":")[1]);
                }
            }
            /*
             * if the second map contains any of the values set the block creator with these values.
             * if it doesn't,get the value from the first map.
             */
            if (map1.containsKey("height")) {
                bc1.setHeight(Integer.parseInt(map1.get("height")));
            } else if (map.containsKey("height")) {
                bc1.setHeight(Integer.parseInt(map.get("height")));
            }
            if (map1.containsKey("width")) {
                bc1.setWidth(Integer.parseInt(map1.get("width")));
            } else if (map.containsKey("width")) {
                bc1.setWidth(Integer.parseInt(map.get("width")));
            }
            if (map1.containsKey("stroke")) {
                String strokeColor = map1.get("stroke").substring(map1.get("stroke").indexOf("(") + 1,
                        map1.get("stroke").lastIndexOf(")"));

                bc1.setStroke(new BlockStroke(new ColorParser().getColor(strokeColor)));
            } else if (map.containsKey("stroke")) {
                String strokeColor = map.get("stroke").substring(map.get("stroke").indexOf("(") + 1,
                        map.get("stroke").lastIndexOf(")"));

                bc1.setStroke(new BlockStroke(new ColorParser().getColor(strokeColor)));
            }
            if (map1.containsKey("hit_points")) {
                bc1.setHp(Integer.parseInt(map1.get("hit_points")));
                for (int i = 0; i <= Integer.parseInt(map1.get("hit_points")); i++) {
                    if (fillMap.containsKey(i)) {
                        if (i >= 1) {
                            bc1.addBackground(i, fillMap.get(i));
                        } else {
                            bc1.setBackground(fillMap.get(i));
                        }
                    }
                }
            } else if (map.containsKey("hit_points")) {
                bc1.setHp(Integer.parseInt(map.get("hit_points")));
                //run for every hit point and check if the fillMap contains it,if it does add new background.
                for (int i = 0; i <= Integer.parseInt(map.get("hit_points")); i++) {
                    if (fillMap.containsKey(i)) {
                        if (i >= 1) {
                            bc1.addBackground(i, fillMap.get(i));
                            //if the index is 0 set the default background.
                        } else {
                            bc1.setBackground(fillMap.get(i));
                        }
                    }
                }
            }
            //put the second block creator and its symbol in the creators map.
            creatorMap.put(map1.get("symbol"), bc1);
        }
        //return the new map
        return creatorMap;
    }

    /**
     * a function that return the integer to the fill Map.
     *
     * @param fillDef a string that holds the integer that needs to be returned.
     * @return the int hit point value.
     */
    private static Integer getFillMapInt(String fillDef) {
        String kOfFillK;
        //get the substring that holds the number, turn it into integer and return it.
        if (fillDef.startsWith("fill-")) {
            kOfFillK = fillDef.substring(fillDef.indexOf("-") + 1, fillDef.indexOf(":"));
            return Integer.parseInt(kOfFillK);
        } else {
            return 0;
        }
    }

    /**
     * a function to get the background to the fill Map.
     *
     * @param fillDef a string that holds the background that needs to be returned.
     * @return the background of the specific hit point.
     */
    private static BlockBackground getFillMapBG(String fillDef) {
        String imgOrColor = fillDef.substring(fillDef.indexOf(":") + 1);
        String fillString = fillDef.substring(fillDef.indexOf("(") + 1, fillDef.lastIndexOf(")"));
        String[] rgb;
        int r, g, b;
        Image img = null;
        //if its an image try to read the image file and return a new image background.
        if (imgOrColor.startsWith("image")) {
            try {
                img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(fillString));
                return new BlockImageBackground(img);
                //if an exception is thrown print an error message and throw runtime exception.
            } catch (IOException e) {
                System.err.println("Failed reading image file.");
                throw new RuntimeException("Can't read image file. no background available.");
            }
            //else check if the string hold a static color name or rgb values and return a new color background.
        } else {
            if (fillString.startsWith("RGB")) {
                fillString = fillString.substring(fillString.indexOf("(") + 1, fillString.lastIndexOf(")"));
                rgb = fillString.split(",");
                r = Integer.parseInt(rgb[0]);
                g = Integer.parseInt(rgb[1]);
                b = Integer.parseInt(rgb[2]);
                return new BlockColorBackground(new Color(r, g, b));
            }
            return new BlockColorBackground(new ColorParser().getColor(fillString));
        }
    }
}