package net.projectuniverse.general.database;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;

import java.util.List;

/**
 * The DatabaseCreator class is responsible for creating databases and logging the status of each creation.
 */

public class DatabaseCreator {

    /**
     * Represents the logger used to log creation status.
     * This logger is used to log the status of each database creation.
     */
    private final ILogger logger;

    /**
     * Represents a database access layer using SQL.
     * This class provides methods to interact with the database using SQL queries.
     */
    private final DBAccessLayer sql;
    /**
     * Represents a list of databases to be created.
     * This list contains the databases that need to be created.
     */
    private final List<Database> databases;

    /**
     * Constructs a new DatabaseCreator with the specified logger, SQL access layer, and list of databases.
     *
     * @param logger    the logger used to log creation status
     * @param sql       the SQL access layer used to create databases
     * @param databases the list of databases to be created
     */
    public DatabaseCreator(ILogger logger, DBAccessLayer sql, List<Database> databases) {
        this.logger = logger;
        this.sql = sql;
        this.databases = databases;
    }

    /**
     * Creates all databases in the list and logs the status for each creation.
     *
     * @return true if all database creations are successful, false otherwise.
     */
    public boolean create() {
        return databases.stream().allMatch(this::createDatabaseAndLogStatus);
    }

    /**
     * Creates a database and logs the status.
     *
     * @param database the database to create
     * @return true if the database creation is successful, false otherwise
     */
    private boolean createDatabaseAndLogStatus(Database database) {
        String databaseName = database.name();

        logger.log(LogLevel.INFO, String.format("Creating database %s...", databaseName));
        if(!sql.createDatabase(database)) {
            logger.log(LogLevel.ERROR, String.format("Failed to create database %s", databaseName));
            return false;
        }
        logger.log(LogLevel.INFO, String.format("Created database %s", databaseName));
        return true;
    }

}
