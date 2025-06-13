package net.projectuniverse.general.cactus_clicker.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.projectuniverse.general.cactus_clicker.instance_management.InstanceManagement;

public class JoinListener {

    public JoinListener() {

        onJoin();

    }

    private void onJoin() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {

        });
    }

}
