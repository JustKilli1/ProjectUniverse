package net.projectuniverse.general.logging;

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
