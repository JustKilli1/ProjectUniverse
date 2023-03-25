package net.projectuniverse.general.messenger;

public enum MessageDesign {

    PLAYER_MESSAGE()

    ;
    private static final String prefix = " >> ";

    public static String apply(MessageDesign design, String message) {
        return prefix + message;
    }

}
