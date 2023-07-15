package net.projectuniverse.general.terminal;

import java.util.List;

/**
 * The TerminalColor enum provides ANSI color codes that can be used to add colors to terminal output.
 */

public enum TerminalColor {

    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),

    //Background Colors
    BLACK_BACKGROUND("\u001B[40m"),
    RED_BACKGROUND("\u001B[41m"),
    GREEN_BACKGROUND("\u001B[42m"),
    YELLOW_BACKGROUND("\u001B[43m"),
    BLUE_BACKGROUND("\u001B[44m"),
    PURPLE_BACKGROUND("\u001B[45m"),
    CYAN_BACKGROUND("\u001B[46m"),
    WHITE_BACKGROUND("\u001B[47m")
    ;

    private String ansiCode;

    TerminalColor(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    /**
     * Adds the given Colors {@code colors} to the given String {@code target}. Resets color at the end of the String
     * @param target The String where the Colors get added
     * @param colors The Colors that get added to the String.
     * @return Target String with applied Colors
     * */
    public static String apply(String target, List<TerminalColor> colors) {
        StringBuilder builder = new StringBuilder();
        colors.forEach(builder::append);
        String colorStr = builder.toString();
        return colorStr + target + RESET;
    }

    @Override
    public String toString() {
        return ansiCode;
    }
}
