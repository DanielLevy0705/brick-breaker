package tools;

/**
 * a class to hold two Strings. (helps return a map with key, message and path).
 */
public class LvlAndMsg {
    private String message;
    private String path;

    /**
     * setter for the path.
     *
     * @param newPath a new path.
     */
    public void setPath(String newPath) {
        this.path = newPath;
    }

    /**
     * setter for the message.
     *
     * @param newMessage a new message
     */
    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    /**
     * a getter for the path.
     *
     * @return the path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * a getter for the message.
     *
     * @return the message.
     */
    public String getMessage() {
        return this.message;
    }

}