package menu;

import animation.Animation;

/**
 * an interface for menus that extends animation.
 *
 * @param <T> a generic variable.
 */
public interface Menu<T> extends Animation {
    /**
     * a function to add a new task.
     *
     * @param key       the key of the task.
     * @param message   the message of the task.
     * @param returnVal the task itself.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * a function to get the chosen task.
     *
     * @return the chosen task.
     */
    T getStatus();

    /**
     * a function to add sub menu to the main menu.
     *
     * @param key     the key of the submenu.
     * @param message the message of the submenu.
     * @param subMenu the submenu to add.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}