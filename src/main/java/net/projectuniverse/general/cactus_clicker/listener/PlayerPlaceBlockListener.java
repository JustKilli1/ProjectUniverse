package net.projectuniverse.general.cactus_clicker.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

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
            if(targetBlock.equals(Block.CACTUS)) handleCactusPlace(instance, event);
            else handleBlockPlace(instance, event);
        });
    }

    private void handleCactusPlace(Instance instance, PlayerBlockPlaceEvent event) {
        Block baseBlock = instance.getBlock(event.getBlockPosition().add(0, -1, 0));
        if (!validBaseBlock(baseBlock) || hasNonAirSurroundingBlocks(instance, event)) event.setCancelled(true);
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
