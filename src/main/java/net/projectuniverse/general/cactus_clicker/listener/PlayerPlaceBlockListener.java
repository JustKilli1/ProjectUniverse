package net.projectuniverse.general.cactus_clicker.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.general.cactus_clicker.Cactus;
import net.projectuniverse.general.cactus_clicker.instance_management.InstanceManagement;
import net.projectuniverse.general.cactus_clicker.island.CactusClickerIsland;

import java.util.ArrayList;
import java.util.List;

public class PlayerPlaceBlockListener {

    public static final List<Block> PLACEABLE_BLOCKS = List.of(Block.CACTUS, Block.SAND, Block.RED_SAND);

    public PlayerPlaceBlockListener() {
        onBlockPlace();
    }

    private void onBlockPlace() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            Instance instance = event.getInstance();
            Block targetBlock = event.getBlock();
            if(targetBlock.equals(Block.CACTUS) || targetBlock.equals(Block.SAND) || targetBlock.equals(Block.RED_SAND)) handleCactusPlace(instance, event);
            else handleBlockPlace(instance, event);
        });
    }

    private void handleCactusPlace(Instance playerInstance, PlayerBlockPlaceEvent event) {
        Block block = event.getBlock();
        Block baseBlock = playerInstance.getBlock(event.getBlockPosition().add(0, -1, 0));
        if (!validBaseBlock(baseBlock) || hasNonAirSurroundingBlocks(playerInstance, event)) event.setCancelled(true);
        Player player = event.getPlayer();
        if(!InstanceManagement.hasInstance(player)) {
            event.setCancelled(true);
            return;
        }
        CactusClickerIsland island = InstanceManagement.getIsland(player);
        if(!playerInstance.equals(island.getInstance())) {
            event.setCancelled(true);
            return;
        }
        if(block.equals(Block.SAND) || block.equals(Block.RED_SAND)) {
            InstanceManagement.addCactus(player, new Cactus(island.getInstance(), block, new Pos(event.getBlockPosition())));
        } else if(block.equals(Block.CACTUS)) {
            InstanceManagement.addCactus(player, block, new Pos(event.getBlockPosition()));
        }
    }
    private boolean validBaseBlock(Block baseBlock) {
        return PLACEABLE_BLOCKS.stream().anyMatch(block -> block.equals(baseBlock));
    }

    private boolean hasNonAirSurroundingBlocks(Instance instance, PlayerBlockPlaceEvent event) {
        List<Block> surroundingBlocks = getSurroundingBlocks(instance, event.getBlockPosition());
        return surroundingBlocks.stream().anyMatch(block -> block != Block.AIR);
    }

    private void handleBlockPlace(Instance instance, PlayerBlockPlaceEvent event) {
        List<Block> surroundingBlocks = getSurroundingBlocks(instance, event.getBlockPosition());
        if (surroundingBlocks.stream().anyMatch(block -> block.equals(Block.CACTUS))) event.setCancelled(true);
    }

    private List<Block> getSurroundingBlocks(Instance instance, Point pos) {
        return List.of(
                instance.getBlock(pos.add(0, 1, 0)),
                instance.getBlock(pos.add(0, 0, 1)),
                instance.getBlock(pos.add(1, 0, 0)),
                instance.getBlock(pos.add(0, 0, -1)),
                instance.getBlock(pos.add(-1, 0, 0))
    );
    }

}
