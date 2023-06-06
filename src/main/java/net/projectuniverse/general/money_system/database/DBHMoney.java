package net.projectuniverse.general.money_system.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.money_system.PlayerPurse;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBHMoney extends DBHandler {

    private final DBALMoney sql;

    public DBHMoney(DBALMoney sql) {
        super(sql);
        this.sql = sql;
    }

    public Optional<PlayerPurse> getPlayerPurse(Player player, PlayerPurse.Currency currency) {
        try {
            ResultSet result = sql.getPlayerPurse(player, currency);
            if(result == null) return Optional.empty();
            if(!result.next()) return Optional.empty();
            int amount = result.getInt("Amount");
            return Optional.of(new PlayerPurse(player, currency, sql, amount));
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not retrieve Player Purse from Player " + player.getUsername(), ex);
            return Optional.empty();
        }
    }

    public List<PlayerPurse> getRichestPlayers(PlayerPurse.Currency currency, int limit) {
        List<PlayerPurse> richestPlayers = new ArrayList<>();
        try {
            ResultSet result = sql.getRichestPlayers(currency, limit);
            if(result == null) return richestPlayers;
            while(result.next()) {
                String playerName = result.getString("Name");
                int amount = result.getInt("Amount");
                richestPlayers.add(new PlayerPurse(playerName, currency, sql, amount));
            }
            return richestPlayers;
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not get a List of the richest Players", ex);
            return richestPlayers;
        }
    }

}
