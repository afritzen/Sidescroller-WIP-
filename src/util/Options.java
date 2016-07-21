package util;

/**
 * Holds all options that are used over and over in the game (e.g. in
 * menu-screens).
 */
public enum Options {

    HELP("help"), BACK("back");

    private final String text;

    /**
     * Constructor.
     * @param text the text that will be displayed on the screen
     */
    Options(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
