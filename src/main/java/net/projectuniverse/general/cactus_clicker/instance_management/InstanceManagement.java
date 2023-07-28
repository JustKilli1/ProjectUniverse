package net.projectuniverse.general.cactus_clicker.instance_management;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.projectuniverse.general.cactus_clicker.database.DBALCactusClicker;
import net.projectuniverse.general.cactus_clicker.database.DBHCactusClicker;
import net.projectuniverse.general.cactus_clicker.island.CactusClickerIsland;
import net.projectuniverse.general.instance.InstanceImporter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InstanceManagement {

    private static final Map<Player, CactusClickerIsland> activeInstances = new HashMap<>();
    private static final DBALCactusClicker sql = new DBALCactusClicker();
    private static final DBHCactusClicker dbHandler = new DBHCactusClicker(sql);

    public static Optional<CactusClickerIsland> createNewPlayerInstance(Player player) {
        if(hasInstance(player)) {
            return loadPlayerInstance(player);
        }
        InstanceContainer playerInstanceContainer = InstanceImporter.importWorld("instances/cactus-clicker/cactus-clicker-island").copy();
        MinecraftServer.getInstanceManager().registerInstance(playerInstanceContainer);
        CactusClickerIsland playerInstance = new CactusClickerIsland(player, playerInstanceContainer);
        sql.insertNewPlayerIsland(player, buildSavePath(player));
        activeInstances.put(player, playerInstance);
        return Optional.of(playerInstance);
    }

    public static boolean savePlayerInstance(Player player) {
        if(!hasInstance(player)) return false;
        AnvilLoader loader = new AnvilLoader(buildSavePath(player));
        CactusClickerIsland island = getIsland(player);
        loader.saveChunks(island.instance().getChunks());
        return true;
    }

    public static void removePlayerInstance(Player player) {
        if(!hasInstance(player)) return;
        savePlayerInstance(player);
        activeInstances.remove(player);
    }

    private static String buildSavePath(Player player) {
        return String.format("instances/cactus-clicker/player-islands/%s", player.getUuid());
    }

    public static Optional<CactusClickerIsland> loadPlayerInstance(Player player) {
        if(activeInstances.containsKey(player)) return Optional.of(activeInstances.get(player));

        Optional<String> pathOpt = dbHandler.getPlayerIslandPath(player);
        if(pathOpt.isEmpty()) return Optional.empty();
        String path = pathOpt.get();

        InstanceContainer playerInstanceContainer = InstanceImporter.importWorld(path);
        MinecraftServer.getInstanceManager().registerInstance(playerInstanceContainer);
        CactusClickerIsland playerInstance = new CactusClickerIsland(player, playerInstanceContainer);
        activeInstances.put(player, playerInstance);
        return Optional.of(playerInstance);
    }

    public static boolean hasInstance(Player player) {
        return dbHandler.hasIsland(player);
    }

    public static CactusClickerIsland getIsland(Player player) {
        return activeInstances.get(player);
    }

}
