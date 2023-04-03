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

    public static ArenaManager getInstance() { return instance; }


}
