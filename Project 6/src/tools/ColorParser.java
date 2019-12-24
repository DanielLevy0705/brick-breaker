package tools;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

/**
 * a class for mapping colors to their names.
 */
public class ColorParser {
    /**
     * return a color that fits the string input.
     *
     * @param fillVal a string that represent a color.
     * @return a color that fits the string.
     */
    public Color getColor(String fillVal) {
        //build a map with the static colors.
        Map<String, Color> colorMap = new TreeMap<String, Color>();
        colorMap.put("black", Color.black);
        colorMap.put("blue", Color.blue);
        colorMap.put("cyan", Color.cyan);
        colorMap.put("gray", Color.gray);
        colorMap.put("lightGray", Color.lightGray);
        colorMap.put("green", Color.green);
        colorMap.put("orange", Color.orange);
        colorMap.put("pink", Color.pink);
        colorMap.put("red", Color.red);
        colorMap.put("white", Color.white);
        colorMap.put("yellow", Color.yellow);
        //return the color.
        return colorMap.get(fillVal);
    }
}