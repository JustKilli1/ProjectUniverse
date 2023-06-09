package net.projectuniverse.general.money_system.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.money_system.PlayerPurse;

import java.sql.ResultSet;

public class DBALMoney extends DBAccessLayer {

    public boolean addNewPlayerPurse(Player player, PlayerPurse.Currency currency, int amount) {
        String sqlQuery = "INSERT INTO PlayerMoneyTable (PlayerID, Currency, Amount) VALUES " +
                "((SELECT PlayerID FROM Player WHERE UUID='" + player.getUuid() + "'), " +
                "'" + currency + "', " +
                amount + ")";
        return executeSQLRequest(sqlQuery);
    }

    public ResultSet getPlayerPurse(Player player, PlayerPurse.Currency currency) {
        String sqlQuery = "SELECT * FROM PlayerMoneyTable " +
                "WHERE PlayerID=(SELECT PlayerID FROM Player WHERE UUID='" + player.getUuid() + "') AND Currency='" + currency + "'";
        return querySQLRequest(sqlQuery);
    }

    public boolean updatePlayerPurse(Player player, PlayerPurse.Currency currency, int newAmount) {
        String sqlQuery = "UPDATE PlayerMoneyTable " +
                "SET Amount=" + newAmount + " " +
                "WHERE PlayerID=(SELECT PlayerID FROM PLAYER WHERE UUID='" + player.getUuid() + "') " +
                "AND Currency='" + currency + "'";
        return executeSQLRequest(sqlQuery);
    }

    public ResultSet getRichestPlayers(PlayerPurse.Currency currency, int limit) {
        String sqlQuery = "SELECT * FROM PlayerMoneyTable " +
                "JOIN Player ON PlayerMoneyTable.PlayerID=Player.PlayerID " +
                "WHERE PlayerMoneyTable.Currency='" + currency + "' " +
                "ORDER BY PlayerMoneyTable.Amount DESC " +
                "LIMIT " + limit;
        return querySQLRequest(sqlQuery);
    }

}
