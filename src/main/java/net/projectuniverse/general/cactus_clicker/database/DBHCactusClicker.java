package net.projectuniverse.general.cactus_clicker.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.money_system.PlayerPurse;

import java.sql.ResultSet;
import java.util.Optional;

public class DBHCactusClicker extends DBHandler {

    private final DBALCactusClicker sql;

    public DBHCactusClicker(DBALCactusClicker sql) {
        super(sql);
        this.sql = sql;
    }

    public Optional<String> getPlayerIslandPath(Player player) {
        try {
            ResultSet result = sql.getPlayerIslandPath(player);
            if(result == null) return Optional.empty();
            if(!result.next()) return Optional.empty();
            String path = result.getString("Path");
            return Optional.of(path);
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, String.format("Could not retrieve Player Island Path from Player %s", player.getUsername()), ex);
            return Optional.empty();
        }
    }

    public boolean hasIsland(Player player) {
        try {
            ResultSet result = sql.getPlayerIsland(player);
            if(result == null) return false;
            return result.next();
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, String.format("Could not retrieve Player Island from Player %s", player.getUsername()), ex);
            return false;
        }
    }

}
