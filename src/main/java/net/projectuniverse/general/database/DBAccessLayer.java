package net.projectuniverse.general.database;


import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.type.TerminalPrinter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccessLayer {

    protected ILogger logger = new LoggerBuilder("Database").addOutputPrinter(new TerminalPrinter()).build();
    /**
     * Normal Sql Commands no Data processing
     * */

    private MySQL mySql;

    public DBAccessLayer(ConfigManager mysqlConfig) {
        mySql = new MySQL(logger, mysqlConfig);
        mySql.connect();
    }

    public void disable() {
        mySql.disconnect();
    }

    /**
     * Reconnects to database if not connected
     */
    private void checkAndReconnectConnection() {
        if (!mySql.isConnected()) {
            mySql.connect();
        }
    }

    protected boolean executeSQLRequest(String sqlQuery) {
        checkAndReconnectConnection();
        if (mySql.isConnected()) {
            Connection connection = mySql.getConnection();
            try {
                PreparedStatement ps = connection.prepareStatement(sqlQuery);
                ps.executeUpdate();
                return true;
            } catch (SQLException ex) {
                logger.log(LogLevel.ERROR, "Execute SQL request failed. " + sqlQuery, ex);
                return false;
            }
        } else {
            return false;
        }
    }
    protected ResultSet querySQLRequest(String sqlQuery) {
        checkAndReconnectConnection();
        if (mySql.isConnected()) {
            Connection connection = mySql.getConnection();
            try {
                PreparedStatement ps = connection.prepareStatement(sqlQuery);
                return ps.executeQuery();
            } catch (SQLException ex) {
                logger.log(LogLevel.ERROR, "Query SQL request failed. " + sqlQuery, ex);
                return null;
            }
        } else {
            return null;
        }
    }
}
