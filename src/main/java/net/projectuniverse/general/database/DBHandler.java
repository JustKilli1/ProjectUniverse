package net.projectuniverse.general.database;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;

import java.sql.ResultSet;
import java.util.Optional;


/**
 * DBHandler class is responsible for handling database operations.
 * It works with ResultSets obtained from SQL queries.
 *
 * @see DBAccessLayer
 */

public class DBHandler {

    protected static final ILogger logger = DBAccessLayer.logger;
    /**
     * Gets ResultSets from sql request
     * --> Gets Information needed from ResultSet and returns it
     * Works with sql return data(ResultSets)
     * */
    protected DBAccessLayer sql;

    public DBHandler(DBAccessLayer sql) {
        this.sql = sql;
    }

    /**
     * Checks if the given player exists in the database.
     *
     * @param player the player to check for existence in the database
     * @return true if the player exists in the database, false otherwise
     */
    public boolean playerInDatabase(Player player) {
        try {
            return resultSetIsEmpty(sql.getPlayer(player)).isPresent();
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not execute Player in Database Query", ex);
            return false;
        }
    }

    /**
     * Gets the ID of the given player.
     *
     * @param player the name of the player to get the ID for
     * @return an Optional containing the ID of the player if it exists, otherwise an empty Optional
     */
    public Optional<Integer> getPlayerId(Player player) {
        try {
            Optional<ResultSet> resultOpt = resultSetIsEmpty(sql.getPlayer(player));
            if(resultOpt.isEmpty()) return Optional.empty();
            ResultSet result = resultOpt.get();
            return Optional.of(result.getInt("PlayerID"));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, String.format("Could not get Id from Player %s.", player.getUsername()), ex);
            return Optional.empty();
        }
    }

    /**
     * Gets the ID of the player with the given name.
     *
     * @param playerName the name of the player to get the ID for
     * @return an Optional containing the ID of the player if it exists, otherwise an empty Optional
     */
    public Optional<Integer> getPlayerId(String playerName) {
        try {
            Optional<ResultSet> resultOpt = resultSetIsEmpty(sql.getPlayer(playerName));
            if(resultOpt.isEmpty()) return Optional.empty();
            ResultSet result = resultOpt.get();
            return Optional.of(result.getInt("PlayerID"));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, String.format("Could not get Id from Player %s.", playerName), ex);
            return Optional.empty();
        }
    }

    /**
     * Closes the provided ResultSet
     *
     * @param result ResultSet to be closed
     */
    private void closeResultSet(ResultSet result) {
        try {
            result.close();
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, "Could not close ResultSet", ex);
        }
    }

    /**
     * Checks if the given ResultSet is empty.
     *
     * @param result the ResultSet to check
     * @param close if true, the ResultSet will be closed after checking
     * @return an Optional containing the non-empty ResultSet if it exists, otherwise an empty Optional
     */
    public Optional<ResultSet> resultSetIsEmpty(ResultSet result, boolean close) {
        if(result == null) return Optional.empty();
        try {
            if(!result.next()) return Optional.empty();
            return Optional.of(result);
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not check if ResultSet is empty", ex);
            return Optional.empty();
        } finally {
            if(close) closeResultSet(result);
        }
    }

    /**
     * Checks if the given ResultSet is empty.
     *
     * @param result the ResultSet to check for emptiness
     * @return an Optional containing the ResultSet if it is not empty, otherwise an empty Optional
     */
    public Optional<ResultSet> resultSetIsEmpty(ResultSet result) {
        return resultSetIsEmpty(result, false);
    }

    /**
     * Checks if the given Optional<ResultSet> is empty.
     *
     * @param resultOpt the Optional<ResultSet> to check for emptiness
     * @param close a flag specifying whether to close the ResultSet after checking for emptiness
     * @return an Optional containing the ResultSet if it is not empty, otherwise an empty Optional
     */
    public Optional<ResultSet> resultSetIsEmpty(Optional<ResultSet> resultOpt, boolean close) {
        if(resultOpt.isEmpty()) return Optional.empty();
        return resultSetIsEmpty(resultOpt.get(), close);
    }

    /**
     * Checks if the given ResultSet is empty.
     *
     * @param resultOpt the ResultSet to check for emptiness
     * @return an Optional containing the ResultSet if it is not empty, otherwise an empty Optional
     */
    public Optional<ResultSet> resultSetIsEmpty(Optional<ResultSet> resultOpt) {
        return resultSetIsEmpty(resultOpt, false);
    }
}
