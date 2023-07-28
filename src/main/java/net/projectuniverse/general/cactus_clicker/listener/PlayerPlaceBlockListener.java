package net.projectuniverse.general.cactus_clicker.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;

public class PlayerPlaceBlockListener {

    public PlayerPlaceBlockListener() {
        onBlockPlace();
    }

    private void onBlockPlace() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            Block targetBlock = event.getBlock();
            if(targetBlock != Block.CACTUS) return;
            Block placedOn = event.getInstance().getBlock(event.getBlockPosition().add(0, -1, 0));
            if(placedOn != Block.SAND && placedOn != Block.RED_SAND && placedOn != Block.CACTUS) {
                event.setCancelled(true);
                return;
            }
        });
    }

}
