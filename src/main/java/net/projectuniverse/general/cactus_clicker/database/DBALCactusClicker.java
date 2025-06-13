package net.projectuniverse.general.cactus_clicker.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBAccessLayer;

import java.sql.ResultSet;

public class DBALCactusClicker extends DBAccessLayer {

    public boolean insertNewPlayerIsland(Player player, String path) {
        String sqlQuery = String.format("INSERT INTO CactusClickerPlayerIslands (PlayerId, Path) VALUES ((%s), '%s')", String.format("SELECT PlayerID FROM Player WHERE UUID = '%s'", player.getUuid()), path);
        return executeSQLRequest(sqlQuery);
    }

    public ResultSet getPlayerIsland(Player player) {
        String sqlQuery = String.format("SELECT * FROM CactusClickerPlayerIslands WHERE PlayerId = (%s)", String.format("SELECT PlayerID FROM Player WHERE UUID = '%s'", player.getUuid()));
        return querySQLRequest(sqlQuery);
    }

    public ResultSet getPlayerIslandPath(Player player) {
        String sqlQuery = String.format("SELECT Path FROM CactusClickerPlayerIslands WHERE PlayerId = (%s)", String.format("SELECT PlayerID FROM Player WHERE UUID = '%s'", player.getUuid()));
        return querySQLRequest(sqlQuery);
    }

}
