package net.projectuniverse.general.player_system.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.projectuniverse.general.player_system.database.DBALPlayerSystem;

import java.sql.Date;
import java.sql.Time;

public class LeaveListener {

    private DBALPlayerSystem sql;

    public LeaveListener(DBALPlayerSystem sql) {
        this.sql = sql;
        onLeave();
    }

    public void onLeave() {
       GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
       globalEventHandler.addListener(PlayerDisconnectEvent.class, event -> {
           final Player player = event.getPlayer();
           Date date = new Date(System.currentTimeMillis());
           Time time = new Time(System.currentTimeMillis());
           sql.updatePlayerSession(player, date, time);
       });
    }

}
