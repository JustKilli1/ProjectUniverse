package net.projectuniverse.general.cactus_clicker.island;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.general.cactus_clicker.Cactus;

import java.util.List;

public class CactusClickerIsland {

    private final Player player;
    private final InstanceContainer instance;
    private List<Cactus> cactusList;
    private int cactusCount;

    public CactusClickerIsland(Player player, InstanceContainer instance, List<Cactus> cactusList) {
        this.player = player;
        this.instance = instance;
        this.cactusList = cactusList;
    }

    public void setCactusList(List<Cactus> cactusList) {
        this.cactusList = cactusList;
    }

    public void addCactus(Cactus cactus) {
        cactusList.add(cactus);
    }

    public void addCactus(Block block, Pos pos) {
        for(Cactus cactus : cactusList) {
            if(cactus.isCactus(pos.add(0, -1, 0))) {
                cactus.addBlock(pos, block);
                return;
            }
        }
    }

    public void harvest() {
        cactusList.forEach(cactus -> {
            cactusCount += cactus.getCactusHeight();
        });
    }

    public Player getPlayer() {
        return player;
    }

    public InstanceContainer getInstance() {
        return instance;
    }

    public List<Cactus> getCactusList() {
        return cactusList;
    }

    public int getCactusCount() {
        return cactusCount;
    }
}
