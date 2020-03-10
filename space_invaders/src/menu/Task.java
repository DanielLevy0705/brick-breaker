package menu;

/**
 * interface to run tasks.
 *
 * @param <T> a generic variable.
 */
public interface Task<T> {
    /**
     * run the current task.
     *
     * @return a generic variable.
     */
    T run();
}