package net.projectuniverse.general.server;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.general.terminal.ServerTerminal;

public class Server {

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
        });

        server.start("5.75.144.98", 25565);
    }

    /**
     * Stops the Server
     * */
    public static void stop() {
        terminal.print("Server wird geschlossen");
        System.exit(0);
        terminal.print("Server geschlossen");
    }

}
