package net.projectuniverse.general.arena_system;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceManager;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ArenaManager {

    private static InstanceManager instanceManager = MinecraftServer.getInstanceManager();
    private static final ILogger logger = new LoggerBuilder("ArenaManager").addOutputPrinter(new TerminalPrinter()).build();
    private static ArenaManager instance = new ArenaManager();
    private static Map<Player, Arena> arenas = new HashMap<>();

    private ArenaManager() {

    }

    public static void register(Player player, Arena arena) {
        arenas.put(player, arena);
        instanceManager.registerInstance(arena.getInstance());
        arena.start();
        logger.log(LogLevel.INFO, "Player " + player.getUsername() + " created a new Arena");
    }

    public static void unregister(Player player) {
        Arena arena = find(player);
        if(arena == null) {
            logger.log(LogLevel.WARN, "Player " + player.getUsername() + " left a Arena. But the Arena could not be found in the arenas Map");
            return;
        }
        arena.stop();
        arenas.remove(player);
        //TODO UGLY
        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                            instanceManager.unregisterInstance(arena.getInstance());
                            logger.log(LogLevel.INFO, "Player " + player.getUsername() + " left his Arena");
                        }
                );
    }

    public static Arena find(Player player) {
        return arenas.get(player);
    }

    public static ArenaManager getInstance() { return instance; }


}
