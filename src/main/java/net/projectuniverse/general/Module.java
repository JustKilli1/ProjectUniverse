package net.projectuniverse.general;

import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.database.DatabaseTable;
import net.projectuniverse.general.database.DatabaseCreator;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.server.Server;

import java.util.List;

/**
 * Represents a module that can be started, stopped, reloaded, and has a name and description.
 * This class implements the IReloadable interface.
 * @see IReloadable
 */

public abstract class Module implements IReloadable{

    private final String name, description;
    protected final ILogger moduleLogger;

    /**
     * Creates a new Module instance.
     *
     * @param name        the name of the module
     * @param description the description of the module
     * @param moduleLogger the logger for the module
     */
    public Module(String name, String description) {
        this.name = name;
        this.description = description;
        this.moduleLogger = new LoggerBuilder(this.name).addOutputPrinter(new TerminalPrinter()).build();
    }

    /**
     * Starts the module.
     *
     * This method should be implemented by subclasses to provide the logic
     * for starting the module.
     */
    public abstract void start();

    /**
     * Stops the module.
     *
     * This method should be implemented by subclasses to provide the logic
     * for stopping the module. It is called when the module needs to be
     * stopped or terminated.
     */
    public abstract void stop();

    /**
     * Reloads the module.
     *
     * This method should be implemented by subclasses to provide the logic
     * for reloading the module. It is called when the module needs to be
     * reloaded.
     *
     * @return true if the reload operation was successful, otherwise false
     * @see IReloadable
     */
    @Override
    public abstract boolean reload();

    /**
     * Creates the database tables.
     *
     * This method creates the necessary database tables for the module. It initializes
     * the necessary objects for database access and then calls the DatabaseCreator
     * class to create the tables. The logger logs the creation process and the success
     * or failure of the operation.
     *
     * @return true if the database tables were created successfully, otherwise false
     */
    public boolean createDatabase() {
        moduleLogger.log(LogLevel.INFO, "Create Database Tables...");
        DatabaseCreator databaseCreator = new DatabaseCreator(moduleLogger, Server.SQL, getDatabase());
        boolean result = databaseCreator.create();
        moduleLogger.log(LogLevel.INFO, "Database Tables created.");
        return result;
    }

    /**
     * Retrieves the database tables.
     *
     * This method should be implemented by subclasses to return a list of DatabaseTable
     * objects that represent the database tables required by the module. The implementation
     * should create and return the appropriate DatabaseTable objects based on the module's
     * specific database schema.
     *
     * @return a list of DatabaseTable objects representing the module's database tables
     */
    public abstract List<DatabaseTable> getDatabase();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
