package net.projectuniverse.general.database;


import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles Database Connection
 * */
public class MySQL {

    private final ILogger logger;
    private ConfigManager configManager;
    private String host, port, database, username, password;
    private Connection con;

    public MySQL(ILogger logger, ConfigManager configManager) {
        this.logger = logger;
        this.configManager = configManager;
        createDefaultConfig();
    }

    /**
     * Creates Default Config File
     * */
    private void createDefaultConfig() {
        configManager.addDefault(new ConfigValue("sql.host", "SQL.IP"));
        configManager.addDefault(new ConfigValue("sql.port", "3306"));
        configManager.addDefault(new ConfigValue("sql.username", "USERNAME"));
        configManager.addDefault(new ConfigValue("sql.password", "PASSWORD"));
        configManager.addDefault(new ConfigValue("sql.database", "DATABASE"));
        configManager.save();
    }

    /**
     * Reloads the Config File
     * */
    private void reloadDBSettings() {
        configManager.reload();
        host = configManager.getValue("sql.host");
        port = configManager.getValue("sql.port");
        database = configManager.getValue("sql.database");
        username = configManager.getValue("sql.username");
        password = configManager.getValue("sql.password");
    }

    /**
     * Connects to Database
     * */
    public void connect() {
        if(isConnected()) return;
        reloadDBSettings();
        String conStr = "jdbc:mysql://"
                + host + ":"
                + port + "/"
                + database
                + "?autoReconnect=true&useSSL=false";
        try {
            con = DriverManager.getConnection(conStr, username, password);
        } catch (SQLException ex) {
            logger.log(LogLevel.ERROR, "Could not connect to Database. Connection Str: " + conStr, ex);
        }
    }

    /**
     * Disconnects from Database
     * */
    public void disconnect() {
        if(!isConnected()) return;
        try {
            con.close();
        } catch (SQLException ex) {
            logger.log(LogLevel.ERROR, "Could not disconnect from Database", ex);
        }
    }

    public boolean isConnected() {
        return (con != null);
    }

    public Connection getConnection() {
        return con;
    }

}
