package net.projectuniverse.general.messenger;

public enum MessageDesign {

    SERVER_MESSAGE(),
    PLAYER_MESSAGE()

    ;
    private static final String prefix = "ProjectUniverse >> ";

    public static String apply(MessageDesign design, String message) {
        return prefix + message;
    }

}
