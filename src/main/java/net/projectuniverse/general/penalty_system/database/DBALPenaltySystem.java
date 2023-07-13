package net.projectuniverse.general.penalty_system.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBAccessLayer;

import java.sql.ResultSet;

public class DBALPenaltySystem extends DBAccessLayer {

    /**
     * Adds a punishment reason for a player.
     *
     * @param playerId the ID of the player
     * @param reason the reason for the punishment
     * @param duration the duration of the punishment
     * @param durationId the ID representing the duration (e.g. 'd' for days, 'h' for hours)
     * @return true if the punishment reason is successfully added, false otherwise
     */
    public boolean addPunishmentReason(int playerId, String reason, int duration, char durationId) {
        String sqlQuery = String.format("INSERT INTO PunishmentSystemReason (PlayerId, Reason, Duration, DurationId) " +
                "VALUES(%s, '%s', %s, '%s');", playerId, reason, duration, durationId);
        return executeSQLRequest(sqlQuery);
    }

    /**
     * Adds a punishment reason for a player.
     *
     * @param player the player object
     * @param reason the reason for the punishment
     * @param duration the duration of the punishment
     * @param durationId the ID representing the duration (e.g. 'd' for days, 'h' for hours)
     * @return true if the punishment reason is successfully added, false otherwise
     */
    public boolean addPunishmentReason(Player player, String reason, int duration, char durationId) {
        String sqlQuery = String.format("INSERT INTO PunishmentSystemReason (PlayerId, Reason, Duration, DurationId) " +
                "VALUES((SELECT PlayerID FROM Player WHERE UUID='%s', '%s', %s, '%s');", player.getUuid(), reason, duration, durationId);
        return executeSQLRequest(sqlQuery);
    }

    /**
     * Removes a punishment reason for a player.
     *
     * @param playerId the ID of the player
     * @return true if the punishment reason is successfully removed, false otherwise
     */
    public boolean removePunishmentReason(int playerId) {
        String sqlQuery = String.format("DELETE FROM PunishmentSystemReason WHERE PlayerId=%s;", playerId);
        return executeSQLRequest(sqlQuery);
    }

    /**
     * Removes a punishment reason for a player.
     *
     * @param player the player whose punishment reason will be removed
     * @return true if the punishment reason is successfully removed, false otherwise
     */
    public boolean removePunishmentReason(Player player) {
        String sqlQuery = String.format("DELETE FROM PunishmentSystemReason WHERE PlayerId='(SELECT PlayerID FROM Player WHERE UUID='%s');", player.getUuid());
        return executeSQLRequest(sqlQuery);
    }

    public boolean removePunishmentReason(String playerName) {
        String sqlQuery = String.format("DELETE FROM PunishmentSystemReason WHERE PlayerId='(SELECT PlayerID FROM Player WHERE Name='%s');", playerName);
        return executeSQLRequest(sqlQuery);
    }

    /**
     * Retrieves the punishment details for a player.
     *
     * @param playerId the ID of the player for whom to retrieve the punishment details
     * @return a ResultSet containing the punishment details for the player
     */
    public ResultSet getPunishment(int playerId) {
        String sqlQuery = String.format("SELECT * FROM PunishmentSystemReason WHERE PlayerID=%s;", playerId);
        return querySQLRequest(sqlQuery);
    }

    /**
     * Retrieves the punishment details for a player.
     *
     * @param player the player for whom to retrieve the punishment details
     * @return a ResultSet containing the punishment details for the player
     */
    public ResultSet getPunishment(Player player) {
        String sqlQuery = String.format("SELECT * FROM PunishmentSystemReason WHERE PlayerID='(SELECT PlayerID FROM Player WHERE UUID=%s)';", player.getUuid());
        return querySQLRequest(sqlQuery);
    }

    /**
     * Retrieves the punishment details for a player.
     *
     * @param playerName the name of the player for whom to retrieve the punishment details
     * @return a ResultSet containing the punishment details for the player
     */
    public ResultSet getPunishment(String playerName) {
        String sqlQuery = String.format("SELECT * FROM PunishmentSystemReason WHERE PlayerID='(SELECT PlayerID FROM Player WHERE Name=%s)';", playerName);
        return querySQLRequest(sqlQuery);
    }

}
