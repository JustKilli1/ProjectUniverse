package net.projectuniverse.general.money_system.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;


public class JoinListener {

    private final ILogger logger;
    private DBALMoney sql;
    private DBHMoney dbHandler;

    public JoinListener(ILogger logger, DBALMoney sql, DBHMoney dbHandler) {
        this.logger = logger;
        this.sql = sql;
        this.dbHandler = dbHandler;
        onJoin();
    }

    private void onJoin() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            if(dbHandler.getPlayerPurse(player, PlayerPurse.Currency.UNIS).isPresent()) return;
            if(dbHandler.getPlayerPurse(player, PlayerPurse.Currency.COINS).isPresent()) return;
            sql.addNewPlayerPurse(player, PlayerPurse.Currency.UNIS, 1000);
            sql.addNewPlayerPurse(player, PlayerPurse.Currency.COINS, 100);
            logger.log(LogLevel.INFO, "New Player Purse added for Player " + player.getUsername());
        });
    }


}
