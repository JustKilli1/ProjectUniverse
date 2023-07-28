package net.projectuniverse.general.cactus_clicker;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Cactus {

    private final InstanceContainer instance;
    private final Map<Pos, Block> cactusMap;
    private Block cactusBaseBlock;
    private Pos cactusBasePos;

    public Cactus(InstanceContainer instance, Map<Pos, Block> cactusMap, Block cactusBaseBlock, Pos cactusBasePos) {
        this.instance = instance;
        this.cactusMap = cactusMap;
        this.cactusBaseBlock = cactusBaseBlock;
        this.cactusBasePos = cactusBasePos;
    }

    public Cactus(InstanceContainer instance, Block cactusBaseBlock, Pos cactusBasePos) {
        this(instance, new HashMap<>(), cactusBaseBlock, cactusBasePos);
    }

    public void addBlock(Pos pos, Block block) {
        if(block == Block.CACTUS) {
            cactusMap.put(pos, block);
        } else {
            cactusBasePos = pos;
            cactusBaseBlock = block;
        }
    }

    public void removeCactus(Pos pos) {
        cactusMap.remove(pos);
    }

    public void harvestCactus() {
        cactusMap.forEach((position, block) -> {
            instance.setBlock(position, Block.AIR);
            removeCactus(position);
        });
    }

    public void deleteCactus() {
        cactusMap.forEach((position, block) -> {
            if(block != Block.CACTUS) return;
            instance.setBlock(position, Block.AIR);
        });
    }

    public int getCactusHeight() {
        return cactusMap.size();
    }

    public Optional<Pos> getHeighestCactusPos() {
        return cactusMap.keySet().stream()
            .max(Comparator.comparingDouble(Pos::y));
    }

    public Optional<Pos> getLowestCactusPos() {
        return cactusMap.keySet().stream()
            .min(Comparator.comparingDouble(Pos::y));
    }

    public boolean isCactus(Pos pos) {
        if(pos.equals(cactusBasePos)) return true;
        return cactusMap.containsKey(pos);
    }

    public Optional<Block> getBlock(Pos pos) {
        if(pos.equals(cactusBasePos)) return Optional.of(cactusBaseBlock);
        return Optional.ofNullable(cactusMap.get(pos));
    }

    public InstanceContainer getInstance() {
        return instance;
    }

    public Map<Pos, Block> getCactusMap() {
        return cactusMap;
    }

    public Block getCactusBaseBlock() {
        return cactusBaseBlock;
    }

    public Pos getCactusBasePos() {
        return cactusBasePos;
    }
}
