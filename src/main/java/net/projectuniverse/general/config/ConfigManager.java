package net.projectuniverse.general.config;

import net.projectuniverse.general.IReloadable;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.util.List;

/**
 * ConfigManager class implements the IReloadable interface and manages the configuration file.
 * It provides methods to create and save the configuration file,
 * add default values to the file,
 * set values in the file,
 * retrieve values from the file,
 * and reload the file.
 */

public class ConfigManager implements IReloadable {

    protected static final ILogger configLogger = new LoggerBuilder("Config").addOutputPrinter(new TerminalPrinter()).build();
    private static final String DIR_PATH = "server_configurations/";
    private final YamlFile file;

    /**
     * ConfigManager class is responsible for managing the configuration file.
     */
    public ConfigManager(String fileName) {
        file = new YamlFile(DIR_PATH + fileName + ".yml");
        file.options().copyDefaults(true);
        createFile();

    }

    /**
     * Creates the configuration file if it does not exist.
     * If the file already exists, it loads the configurations.
     */
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

    /**
     * Saves the configuration file.
     *
     * @throws IOException if an error occurs while saving the file.
     */
    public void save() {
        try {
            file.save();
        } catch (final IOException ex) {
            configLogger.log(LogLevel.ERROR, "Could not save " + file.getFilePath() + " file.", ex);
        }
    }

    /**
     * Adds default values to the configuration file.
     *
     * @param defaultValues the list of default values to add.
     */
    public void addDefault(List<ConfigValue> defaultValues) {
        if(!fileExists()) return;
        defaultValues.forEach(value -> file.addDefault(value.getPath(), value.getRawValue()));
        save();
    }

    /**
     * Sets the value of a configuration property.
     *
     * @param configValue the ConfigValue object representing the property to set the value for.
     */
    public void setValue(ConfigValue configValue) {
        if(!fileExists()) return;
        file.set(configValue.getPath(), configValue.getRawValue());
    }

    /**
     * Checks if the file exists.
     *
     * @return {@code true} if the file exists, {@code false} otherwise.
     */
    private boolean fileExists() {
        if(file == null) {
            configLogger.log(LogLevel.ERROR, "Could not add Default Config Value cause this.file is null", new NullPointerException());
            return false;
        }
        return true;
    }

    /**
     * Reloads the configuration file.
     *
     * This method first checks if the file exists by calling the private method {@link #fileExists()}.
     * If the file exists, it attempts to reload the file using the {@link #load()} method of the file object.
     * If the file is successfully reloaded, a success message is logged with log level {@link LogLevel#INFO},
     * and the method returns {@code true}.
     * If an exception occurs during the reloading process, an error message is logged with log level {@link LogLevel#ERROR},
     * and the exception is captured and logged with the error message.
     * In both cases, the method returns {@code false}.
     *
     * @return {@code true} if the file is successfully reloaded, {@code false} otherwise.
     */
    @Override
    public boolean reload() {
        try {
            configLogger.log(LogLevel.INFO, "Reloading...");
            file.load();
            configLogger.log(LogLevel.INFO, "Reloading successful!");
            return true;
        } catch(Exception ex) {
            configLogger.log(LogLevel.ERROR, "Could not reload Config File " + file.getFilePath(), ex);
            return false;
        }
    }

    /**
     * Adds a default value to the configuration.
     *
     * This method is used to add a default value to the configuration. It takes a single {@code ConfigValue} parameter,
     * which represents the default value to be added.
     * The method then calls the overloaded {@link #addDefault(List)} method, passing a singleton list containing the default value.
     *
     * @param defaultValue the default value to be added to the configuration.
     */
    public void addDefault(ConfigValue defaultValue) {
        addDefault(List.of(defaultValue));
    }

    public String getValue(String path) {
        return (String) file.get(path);
    }

}
