package net.projectuniverse.general.cactus_clicker.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.projectuniverse.general.cactus_clicker.instance_management.InstanceManagement;

public class LeaveListener {

    public LeaveListener() {
        onLeave();
    }

    private void onLeave() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            final Player player = event.getPlayer();
            InstanceManagement.removePlayerInstance(player);
        });
    }

}
