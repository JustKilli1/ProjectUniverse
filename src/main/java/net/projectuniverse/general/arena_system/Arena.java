package net.projectuniverse.general.arena_system;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.projectuniverse.general.instance.InstanceHandler;

public class Arena {

    private Instance instance;
    private Player player;

    public Arena(Player player, Instance instance) {
        this.player = player;
        this.instance = instance;
        init();
    }

    private void init() {
        //Init stuff idk
    }

    public void start() {
        player.setInstance(instance, new Pos(4, 71, 19));
    }

    public void stop() {
        player.setInstance(InstanceHandler.LOBBY, new Pos(0, 100, 0));
    }

    public Instance getInstance() {
        return instance;
    }
}
