package tools;

/**
 * a class to count things. increase and decrease and get their value.
 */
public class Counter {
    private int value;

    /**
     * constructor.
     * @param val the initial value of the object we count.
     */
    public Counter(int val) {
        this.value = val;
    }

    /**
     * add number to current count.
     *
     * @param number the number to add to the current count
     */
    public void increase(int number) {
        this.value = this.value + number;
    }

    /**
     * subtract number from current count.
     *
     * @param number the number to subtract from the current count
     */
    public void decrease(int number) {
        this.value = this.value - number;
    }

    /**
     * get current count.
     *
     * @return the current count
     */
    public int getValue() {
        return this.value;
    }
}