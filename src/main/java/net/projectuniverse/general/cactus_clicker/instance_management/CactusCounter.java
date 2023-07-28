package net.projectuniverse.general.cactus_clicker.instance_management;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.general.cactus_clicker.Cactus;
import net.projectuniverse.general.cactus_clicker.IBlockCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CactusCounter implements IBlockCounter<Cactus> {

    public List<Cactus> count(InstanceContainer instance) {
        List<Cactus> result = new ArrayList<>();

        for(Chunk chunk : instance.getChunks()) {
            int x = chunk.getChunkX();
            int yStart = -64;
            int yEnd = 320;
            int z = chunk.getChunkZ();
            for(int i = 0; i < 16; i++) {
                for(int j = 0; j < 16; j++) {
                    for(int k = yStart; k < yEnd; k++) {
                        Pos pos = new Pos(i, k, j);
                        Block block = chunk.getBlock(pos);
                        if(block.equals(Block.AIR)) continue;
                        if(block.equals(Block.SAND) || block.equals(Block.RED_SAND)) {
                            result.add(new Cactus(instance, block, pos));
                            continue;
                        }
                        if(!block.equals(Block.CACTUS)) continue;

                        Pos lowerBlockPos = new Pos(i, k - 1, j);

                        Optional<Cactus> cactusOpt = findCactus(result, lowerBlockPos);
                        if(cactusOpt.isEmpty()) continue;
                        Cactus cactus = cactusOpt.get();

                        cactus.addBlock(pos, block);
                    }
                }
            }
        }

        return result;
    }

    private Optional<Cactus> findCactus(List<Cactus> target, Pos pos) {
        for(Cactus cactus : target) {
            if(cactus.isCactus(pos)) return Optional.of(cactus);
        }
        return Optional.empty();
    }
}
