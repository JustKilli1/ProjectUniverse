package net.projectuniverse.general.server;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.BaseConsoleLogger;
import net.projectuniverse.general.terminal.ServerTerminal;

public class Server {

    private static final ILogger serverLogger = new BaseConsoleLogger("Server");
    private static MinecraftServer server;
    private static ServerTerminal terminal;

    /**
     * Starts the Server
     * */
    public static void start() {
        server = MinecraftServer.init();
        MinecraftServer.setTerminalEnabled(false);
        terminal = new ServerTerminal();
        terminal.start();
        try {
            //TODO IS SHIT
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        serverLogger.log(LogLevel.INFO, "Starting Project Universe...");
        serverLogger.log(LogLevel.INFO, "Creating spawn instance...");
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        // Create the instance
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        // Set the ChunkGenerator
        instanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
            serverLogger.log(LogLevel.INFO, "Player " + player.getName() + " connected.");
        });
        serverLogger.log(LogLevel.INFO, "Spawn instance created.");
        serverLogger.log(LogLevel.INFO, "Starting Minestom Service...");

        server.start("127.0.0.1", 25565);
        try {
            //TODO IS SHIT
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        serverLogger.log(LogLevel.INFO, "Minestom Service started successfully");
        serverLogger.log(LogLevel.INFO, "Project Universe startup complete.");
        serverLogger.log(LogLevel.INFO, "Hello c:");

    }

    /**
     * Stops the Server
     * */
    public static void stop() {
        serverLogger.log(LogLevel.INFO, "Server closed");
        System.exit(0);
    }

}
