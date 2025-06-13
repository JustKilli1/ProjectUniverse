package net.projectuniverse.general.messenger;

/**
 * Represents the various designs of messages in the ProjectUniverse system.
 */

public enum MessageDesign {

    SERVER_MESSAGE(),
    PLAYER_MESSAGE()

    ;
    private static final String prefix = "ProjectUniverse >> ";

    public static String apply(MessageDesign design, String message) {
        return prefix + message;
    }

}
