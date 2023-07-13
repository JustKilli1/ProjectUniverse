package net.projectuniverse.general.penalty_system.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.logging.LogLevel;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * The DBHPenaltySystem class provides methods for managing player punishments and retrieving punishment information from a database.
 * It extends the DBHandler class and uses the DBALPenaltySystem class for handling the database operations.
 */

public class DBHPenaltySystem extends DBHandler {

    private final DBALPenaltySystem sql;

    public DBHPenaltySystem(DBALPenaltySystem sql) {
        super(sql);
        this.sql = sql;
    }

    /**
     * Checks if a player has an active punishment.
     *
     * @param playerName the name of the player to check for active punishment
     * @return true if the player has an active punishment, false otherwise
     */
    public boolean playerHasActivePunishment(Player player) {
        try {
            return hasActivePunishment(sql.getPunishment(player));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not check for active Player Punishment for Player " + player.getUsername(), ex);
            return false;
        }
    }

    /**
     * Checks if a player has an active punishment.
     *
     * @param playerName the name of the player to check for active punishment
     * @return true if the player has an active punishment, false otherwise
     */
    public boolean playerHasActivePunishment(String playerName) {
        try {
            return hasActivePunishment(sql.getPunishment(playerName));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not check for active Player Punishment for Player " + playerName, ex);
            return false;
        }
    }

    /**
     * Checks if a player has an active punishment.
     *
     * @param result the ResultSet object containing the player's punishment information
     * @return true if the player has an active punishment, false otherwise
     */
    private boolean hasActivePunishment(ResultSet result) {
        return resultSetIsEmpty(result).isPresent();
    }

    /**
     * Retrieves the reason for a player's punishment, if they have an active one.
     *
     * @param player the player for whom to retrieve the punishment reason
     * @return an Optional containing the punishment reason if the player has an active punishment,
     *         or an empty Optional if they do not have an active punishment or an exception occurs
     */
    public Optional<String> getPunishmentReason(Player player) {
        try {
            Optional<ResultSet> resultOpt = resultSetIsEmpty(sql.getPunishment(player));
            if(resultOpt.isEmpty()) return Optional.empty();
            return Optional.ofNullable(resultOpt.get().getString("Reason"));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not get Punishment Reson for Player " + player.getUsername(), ex);
            return Optional.empty();
        }
    }

    /**
     * Adds a punishment reason for a player.
     *
     * @param player     the player to add the punishment reason for
     * @param reason     the reason for the punishment
     * @param duration   the duration of the punishment
     * @param durationId the identifier for the duration unit (e.g., 'd' for days, 'h' for hours)
     * @return true if the punishment reason was added successfully, false otherwise
     */
    public boolean addPlayerPunishmentReason(Player player, String reason, int duration, char durationId) {
        return sql.addPunishmentReason(player, reason, duration, durationId);
    }

    /**
     * Removes a punishment reason for a player.
     *
     * @param playerName the name of the player to remove the punishment reason for
     * @return true if the punishment reason was removed successfully, false otherwise
     */
    public boolean removePlayerPunishment(String playerName) {
        return sql.removePunishmentReason(playerName);
    }

}
