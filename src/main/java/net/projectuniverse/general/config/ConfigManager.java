package net.projectuniverse.general.config;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggergroups.LoggerGroupConsoleTerminalFile;
import org.simpleyaml.configuration.file.YamlFile;

public class ConfigManager {

    private static final ILogger configLogger = new LoggerGroupConsoleTerminalFile("Config", LogCategory.SYSTEM, "config");
    private static final String DIR_PATH = "configs/";
    private final YamlFile file;

    public ConfigManager(String fileName) {
        file = new YamlFile(DIR_PATH + fileName);
        file.options().copyDefaults(true);
        createFile();

    }

    private void createFile() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                configLogger.log(LogLevel.INFO, "New file has been created: " + file.getFilePath() + "\n");
            } else configLogger.log(LogLevel.INFO, file.getFilePath() + " already exists, loading configurations...\n");
            file.load();
            configLogger.log(LogLevel.INFO, "Configuration file loaded successfully.");
        } catch (final Exception ex) {
            configLogger.log(LogLevel.ERROR, "Could not create Config File", ex);
        }
    }

}