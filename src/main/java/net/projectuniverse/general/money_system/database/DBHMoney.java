package net.projectuniverse.general.money_system.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.UniCurrency;

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

    /**
     * Retrieves the PlayerPurse for the given player and currency from the SQL database.
     *
     * @param player   The player for which to retrieve the purse.
     * @param currency The currency of the purse to retrieve.
     * @return An Optional containing the PlayerPurse if it exists in the database, otherwise returns an empty Optional.
     */
    public Optional<PlayerPurse> getPlayerPurse(Player player, UniCurrency currency) {
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

    /**
     * Retrieves a list of the richest players based on the specified currency and limit.
     *
     * @param currency The currency of the player purses to consider.
     * @param limit    The maximum number of players to retrieve.
     * @return A list of PlayerPurse objects representing the richest players, or an empty list if no players are found.
     */
    public List<PlayerPurse> getRichestPlayers(UniCurrency currency, int limit) {
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
