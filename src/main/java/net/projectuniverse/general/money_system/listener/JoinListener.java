package net.projectuniverse.general.money_system.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.projectuniverse.base.Utils;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.money_system.ModuleMoneySystem;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.UniCurrency;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.Optional;


/**
 * JoinListener is a class that listens for player join events and performs certain actions when a player joins.
 * It adds new player purses for UNIS and COINS currencies if the player does not already have them.
 * It logs the addition of player purses using a provided logger.
 */

public class JoinListener {

    private final ILogger logger;
    private DBALMoney sql;
    private DBHMoney dbHandler;

    /**
     * Constructs a new JoinListener with the specified logger, SQL, and DBHandler.
     * This class listens for new join events and performs actions when a user joins.
     *
     * @param logger    the logger used for logging join events
     * @param sql       the SQL database access layer for managing money data
     * @param dbHandler the database handler used for performing actions on the SQL database
     */
    public JoinListener(ILogger logger, DBALMoney sql, DBHMoney dbHandler) {
        this.logger = logger;
        this.sql = sql;
        this.dbHandler = dbHandler;
        onJoin();
    }

    /**
     * Listens for join events and performs actions when a player joins.
     *
     * This method adds a new player purse for the joined player if the player does not already have a purse.
     * The player is given an initial amount of UNIS and COINS currencies.
     *
     * @param logger    the logger used for logging events
     * @param sql       the SQL database access layer for managing money data
     * @param dbHandler the database handler used for performing actions on the SQL database
     */
    private void onJoin() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();

            for(UniCurrency currency : UniCurrency.values()) {
                Optional<PlayerPurse> targetPurseOpt = dbHandler.getPlayerPurse(player, currency);
                if(targetPurseOpt.isPresent()) return;

                Optional<Integer> startAmountOpt = Utils.convertToInt(ModuleMoneySystem.getConfigManager().getValue(String.format("currency.%s.start_amount", currency.toString().toLowerCase())));
                if(startAmountOpt.isEmpty()) return;
                int startAmount = startAmountOpt.get();

                sql.addNewPlayerPurse(player, currency, startAmount);
                logger.log(LogLevel.INFO, String.format("New %s Player Purse added for Player %s", currency.toString(), player.getUsername()));
            }
        });
    }
}