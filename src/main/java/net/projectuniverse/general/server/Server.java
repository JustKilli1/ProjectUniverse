package net.projectuniverse.general.server;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.terminal.ServerTerminal;

public class Server {

    private static MinecraftServer server;
    private static ServerTerminal terminal;

    /**
     * Starts the Server
     * */
    public static void start() {
        MinecraftServer.setTerminalEnabled(false);
        server = MinecraftServer.init();
        terminal = new ServerTerminal();
        terminal.start();
        server.start("127.0.0.1", 25565);
    }

    /**
     * Stops the Server
     * */
    public static void stop() {
        System.exit(0);
    }

}
