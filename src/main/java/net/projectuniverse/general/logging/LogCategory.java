package net.projectuniverse.general.logging;

/**
 * The {@code LogCategory} enum represents different categories for logging purposes.
 * Each category has a corresponding folder name where the log files are stored.
 * <p>
 * The supported log categories are:
 * - SYSTEM: for system-related logs
 * - WORLD: for logs related to world activities
 * - PLAYER: for logs related to player actions
 * - OTHER: for logs that do not fit into any specific category
 * </p>
 */

public enum LogCategory {

    SYSTEM("System"),
    WORLD("World"),
    PLAYER("Player"),
    OTHER("Other")
    ;

    private String folderName;

    LogCategory(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
