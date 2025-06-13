package net.projectuniverse.general.logging;

/**
 * The {@code LogLevel} enum represents different levels of logging.
 * Each level has a unique name associated with it.
 */

public enum LogLevel {

    INFO("Info"),
    WARN("Warn"),
    ERROR("Du hast verkackt"),
    DEBUG("Debug")
    ;

    private String name;

    LogLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
