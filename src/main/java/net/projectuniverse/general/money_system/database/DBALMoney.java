package net.projectuniverse.general.money_system.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.money_system.PlayerPurse;

import java.sql.ResultSet;

public class DBALMoney extends DBAccessLayer {

    /**
     * Adds a new player purse record to the database.
     *
     * @param player   the player object for which the purse record will be created
     * @param currency the currency of the purse record
     * @param amount   the amount of the currency
     * @return true if the purse record was successfully added, false otherwise
     */
    public boolean addNewPlayerPurse(Player player, PlayerPurse.Currency currency, int amount) {
        String sqlQuery = "INSERT INTO PlayerMoneyTable (PlayerID, Currency, Amount) VALUES " +
                "((SELECT PlayerID FROM Player WHERE UUID='" + player.getUuid() + "'), " +
                "'" + currency + "', " +
                amount + ")";
        return executeSQLRequest(sqlQuery);
    }

    /**
     * Retrieves the player's purse record from the database for the specified currency.
     *
     * @param player   the player object for which the purse record will be retrieved
     * @param currency the currency of the purse record to be retrieved
     * @return a ResultSet object containing the purse record, or null if no record is found
     */
    public ResultSet getPlayerPurse(Player player, PlayerPurse.Currency currency) {
        String sqlQuery = "SELECT * FROM PlayerMoneyTable " +
                "WHERE PlayerID=(SELECT PlayerID FROM Player WHERE UUID='" + player.getUuid() + "') AND Currency='" + currency + "'";
        return querySQLRequest(sqlQuery);
    }

    /**
     * Updates the player's purse record in the database with the specified currency and new amount.
     *
     * @param player    the player object for which the purse record will be updated
     * @param currency  the currency of the purse record to be updated
     * @param newAmount the new amount to be set in the purse record
     * @return true if the update operation is successful, otherwise false
     */
    public boolean updatePlayerPurse(Player player, PlayerPurse.Currency currency, int newAmount) {
        String sqlQuery = "UPDATE PlayerMoneyTable " +
                "SET Amount=" + newAmount + " " +
                "WHERE PlayerID=(SELECT PlayerID FROM PLAYER WHERE UUID='" + player.getUuid() + "') " +
                "AND Currency='" + currency + "'";
        return executeSQLRequest(sqlQuery);
    }

    /**
     * Retrieves the list of the richest players based on the specified currency and limit.
     *
     * @param currency the currency used to determine the richest players
     * @param limit    the maximum number of players to retrieve
     * @return a ResultSet containing the records of the richest players
     */
    public ResultSet getRichestPlayers(PlayerPurse.Currency currency, int limit) {
        String sqlQuery = "SELECT * FROM PlayerMoneyTable " +
                "JOIN Player ON PlayerMoneyTable.PlayerID=Player.PlayerID " +
                "WHERE PlayerMoneyTable.Currency='" + currency + "' " +
                "ORDER BY PlayerMoneyTable.Amount DESC " +
                "LIMIT " + limit;
        return querySQLRequest(sqlQuery);
    }

}
