package net.projectuniverse.general.player_system.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.projectuniverse.base.Utils;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.money_system.ModuleMoneySystem;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.UniCurrency;
import net.projectuniverse.general.player_system.database.DBALPlayerSystem;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class JoinListener {

    private DBALPlayerSystem sql;

    public JoinListener(DBALPlayerSystem sql) {
        this.sql = sql;
        onJoin();
    }

    public void onJoin() {
       GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
       globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
           final Player player = event.getPlayer();
           Date date = new Date(System.currentTimeMillis());
           Time time = new Time(System.currentTimeMillis());
           sql.insertPlayerSession(player, date, time);
       });
    }

}
