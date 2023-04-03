package net.projectuniverse.general.arena_system;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.projectuniverse.general.instance.InstanceHandler;

public class Arena {

    private static InstanceManager instanceManager = MinecraftServer.getInstanceManager();
    private InstanceContainer instance;
    private Player player;

    public Arena(Player player) {
        this.player = player;
        init();
    }

    private void init() {
        instance = InstanceHandler.TOWER_DEFENCE_ARENA.copy();
        instanceManager.registerInstance(instance);
    }

    public void start() {
        player.setInstance(instance, new Pos(4, 71, 19));
    }

    public void stop() {
        player.setInstance(InstanceHandler.LOBBY, new Pos(0, 100, 0));
        instanceManager.unregisterInstance(instance);
    }

}
