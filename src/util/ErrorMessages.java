package util;

/**
 * Holds all error messages that can be displayed in the terminal+
 * in case the game crashes.
 */
public enum ErrorMessages {

    ERR_BG("Error loading background image!"), ERR_SPRITES("Error loading sprites!"), ERR_MAP("Error loading map!"),
    ERR_IMG("Error loading image!");

    private final String text;

    /**
     * Constructor.
     * @param text message that will be displayed
     */
    ErrorMessages(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
