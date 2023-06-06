package net.projectuniverse.general.money_system.database;

import net.projectuniverse.general.database.DBAccessLayer;

import java.sql.ResultSet;

public class DBALMoney extends DBAccessLayer {
    
    public boolean createPlayerMoneyTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS PlayerMoneyTable (" +
                "PlayerPurseID INT NOT NULL AUTO_INCREMENT, " +
                "PlayerID INT NOT NULL, " +
                "Currency VARCHAR(20) NOT NULL," +
                "Amount INTEGER DEFAULT 0," +
                "PRIMARY KEY(PlayerPurseID)" +
                ");";
        return executeSQLRequest(sqlQuery);
    }
}
