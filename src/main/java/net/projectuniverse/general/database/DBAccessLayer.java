package net.projectuniverse.general.database;


import net.minestom.server.entity.Player;
import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Normal Sql Queries no Data processing
 * */
public class DBAccessLayer {

    public static final ILogger logger = new LoggerBuilder("Database").addOutputPrinter(new TerminalPrinter()).build();

    private MySQL mySql;

    public DBAccessLayer(ConfigManager mysqlConfig) {
        mySql = new MySQL(logger, mysqlConfig);
        mySql.connect();
    }

    /*
    * Create Tables Queries
    * */

    public boolean createPlayerTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS Player (" +
                "PlayerID INT NOT NULL AUTO_INCREMENT, " +
                "UUID VARCHAR(128) NOT NULL," +
                "Name TEXT NOT NULL," +
                "PRIMARY KEY(PlayerID)" +
                ");";
        return executeSQLRequest(sqlQuery);
    }
    /*
     * Create Tables Queries END
     * */


    public boolean addPlayer(Player player) {
        String sqlQuery = "INSERT INTO Player (UUID, Name) VALUES (" +
                "'" + player.getUuid() + "'," +
                "'" + player.getUsername() + "'" +
                ");";
        return executeSQLRequest(sqlQuery);
    }

    public ResultSet getPlayer(Player player) {
        String sqlQuery = "SELECT * FROM Player WHERE UUID='" + player.getUuid() + "'";
        return querySQLRequest(sqlQuery);
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
