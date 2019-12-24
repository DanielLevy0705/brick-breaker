package block;

import background.BlockBackground;
import background.BlockStroke;
import geometry.Point;
import geometry.Rectangle;
import sprite.Block;

import java.util.Map;
import java.util.TreeMap;

/**
 * a class that creates blocks.
 */
public class BlockCreatorClass implements BlockCreator {
    private Integer width;
    private Integer height;
    private Map<Integer, BlockBackground> hitNumBg;
    private BlockBackground bg;
    private BlockStroke blockStroke;
    private Integer hp;

    /**
     * a constructor that initialize all the members.
     */
    public BlockCreatorClass() {
        this.width = null;
        this.height = null;
        this.hitNumBg = new TreeMap<Integer, BlockBackground>();
        this.bg = null;
        this.blockStroke = null;
        this.hp = null;
    }

    /**
     * setter for hit points.
     *
     * @param hitPoints the hit points of the block.
     */
    public void setHp(int hitPoints) {
        this.hp = hitPoints;
    }

    /**
     * setter for width.
     *
     * @param width1 the width of the block.
     */
    public void setWidth(int width1) {
        this.width = width1;
    }

    /**
     * setter for height.
     *
     * @param height1 the height of the block.
     */
    public void setHeight(int height1) {
        this.height = height1;
    }

    /**
     * setter for background of the block.
     *
     * @param bg1 the background of the block.
     */
    public void setBackground(BlockBackground bg1) {
        this.bg = bg1;
    }

    /**
     * a function that adds new background by hit points to the background map.
     *
     * @param hitPoint the hit point number.
     * @param bg1      the background fit to this number.
     */
    public void addBackground(int hitPoint, BlockBackground bg1) {
        hitNumBg.put(hitPoint, bg1);
    }

    /**
     * a setter for the stroke.
     *
     * @param s the stroke of the block.
     */
    public void setStroke(BlockStroke s) {
        this.blockStroke = s;
    }

    /**
     * a function that actually creates the block. get an x and y as parameters.
     *
     * @param xpos the x of top left of the block.
     * @param ypos the y of top left of the block.
     * @return the new block.
     */
    public Block create(int xpos, int ypos) {
        //use the block setters to set its values after you create it.
        Block b = new Block(xpos, ypos);
        b.setWidth(this.width);
        b.setHeight(this.height);
        b.setBlockShape(new Rectangle(new Point(xpos, ypos), this.width, this.height));
        b.setBackground(this.bg);
        b.setStroke(this.blockStroke);
        b.setHitPoints(this.hp);
        for (int hitPoint : this.hitNumBg.keySet()) {
            b.newBackground(hitPoint, this.hitNumBg.get(hitPoint));
        }
        return b;
    }
}