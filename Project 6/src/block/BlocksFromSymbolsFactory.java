package block;

import sprite.Block;

import java.util.Map;

/**
 * a class that turn symbols into blocks.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spaceMap;
    private Map<String, BlockCreator> blockMap;

    /**
     * constructor.
     *
     * @param spacers  a map that holds Strings and integers.
     * @param creators a map that holds strings and block creators.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacers, Map<String, BlockCreator> creators) {
        this.spaceMap = spacers;
        this.blockMap = creators;
    }

    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param s a symbol
     * @return true if s is a valid space symbol, else false.
     */
    public boolean isSpaceSymbol(String s) {
        if (spaceMap.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * returns true if 's' is a valid block symbol.
     *
     * @param s a symbol
     * @return true if s is a valid space block, else false.
     */
    public boolean isBlockSymbol(String s) {
        if (blockMap.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * Return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     *
     * @param s    the symbol of the block.
     * @param xpos the x position of the block
     * @param ypos the y position of the block.
     * @return a new block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockMap.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s a spacer symbol
     * @return the width of the symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spaceMap.get(s);
    }
}