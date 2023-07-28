package net.projectuniverse.general.cactus_clicker.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class PlayerPlaceBlockListener {

    public PlayerPlaceBlockListener() {
        onBlockPlace();
    }

    private void onBlockPlace() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            Instance instance = event.getInstance();
            Block targetBlock = event.getBlock();
            if(targetBlock != Block.CACTUS) return;
            //check if block is placed on sand or cactus
            Block placedOn = instance.getBlock(event.getBlockPosition().add(0, -1, 0));
            if(placedOn != Block.SAND && placedOn != Block.RED_SAND && placedOn != Block.CACTUS) {
                event.setCancelled(true);
                return;
            }

            //Check if surrounding blocks are air
            Block blockAbove = instance.getBlock(event.getBlockPosition().add(0, 1, 0));
            Block blockNorth = instance.getBlock(event.getBlockPosition().add(0, 0, 1));
            Block blockEast = instance.getBlock(event.getBlockPosition().add(1, 0, 0));
            Block blockSouth = instance.getBlock(event.getBlockPosition().add(0, 0, -1));
            Block blockWest = instance.getBlock(event.getBlockPosition().add(-1, 0, 0));

            if(blockAbove != Block.AIR || blockNorth != Block.AIR || blockEast != Block.AIR || blockSouth != Block.AIR || blockWest != Block.AIR) event.setCancelled(true);

        });
    }

}
