package net.projectuniverse.general.config;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.util.List;

public class ConfigManager {

    private static final ILogger configLogger = new LoggerBuilder("Config").addOutputPrinter(new TerminalPrinter()).build();
    private static final String DIR_PATH = "server_configurations/";
    private final YamlFile file;

    public ConfigManager(String fileName) {
        file = new YamlFile(DIR_PATH + fileName + ".yml");
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

    public void save() {
        try {
            file.save();
        } catch (final IOException ex) {
            configLogger.log(LogLevel.ERROR, "Could not save " + file.getName() + " file.", ex);
        }
    }

    public void addDefault(List<ConfigValue> defaultValues) {
        if(!fileExists()) return;
        defaultValues.forEach(value -> file.addDefault(value.getPath(), value.getRawValue()));
        save();
    }

    public void setValue(ConfigValue configValue) {
        if(!fileExists()) return;
        file.set(configValue.getPath(), configValue.getRawValue());
    }

    private boolean fileExists() {
        if(file == null) {
            configLogger.log(LogLevel.ERROR, "Could not add Default Config Value cause this.file is null", new NullPointerException());
            return false;
        }
        return true;
    }

    public void reload() {
        try {
            file.load();
        } catch(Exception ex) {
            configLogger.log(LogLevel.ERROR, "Could not reload Config File " + file.getFilePath(), ex);
        }
    }

    public void addDefault(ConfigValue defaultValue) {
        addDefault(List.of(defaultValue));
    }

    public String getValue(String path) {
        return (String) file.get(path);
    }

}
