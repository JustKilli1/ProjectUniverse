package net.projectuniverse.general.server;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.base.Utils;
import net.projectuniverse.general.commands.CmdClearChat;
import net.projectuniverse.general.commands.CmdMuteChat;
import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.BaseConsoleLogger;
import net.projectuniverse.general.terminal.ServerTerminal;

import java.util.Optional;

public class Server {

    private static final ILogger serverLogger = new BaseConsoleLogger("Server");
    private static ConfigManager serverConfig;
    private static MinecraftServer server;
    private static ServerTerminal terminal;
    private static String ip;
    private static int port;
    private static boolean mojangAuth;

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
        loadServerConfig();
        createSpawnInstance();
        serverLogger.log(LogLevel.INFO, "Starting Minestom Service...");
        registerCommands();
        server.start(ip, port);
        try {
            //TODO IS SHIT
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        serverLogger.log(LogLevel.INFO, "Minestom Service started successfully");
        serverLogger.log(LogLevel.INFO, "Bound IP-Adresse: " + ip);
        serverLogger.log(LogLevel.INFO, "Bound Port: " + port);
        serverLogger.log(LogLevel.INFO, "Project Universe startup complete.");
        serverLogger.log(LogLevel.INFO, "Hello c:");
        DBAccessLayer sql = new DBAccessLayer(new BaseConsoleLogger("Database"), new ConfigManager("mysql"));
        DBHandler dbHandler = new DBHandler(sql);

    }

    /**
     * Stops the Server
     * */
    public static void stop() {
        serverLogger.log(LogLevel.INFO, "Server closed");
        System.exit(0);
    }

    private static void createSpawnInstance() {
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
            serverLogger.log(LogLevel.INFO, "Player " + player.getUsername() + " connected.");
        });
        serverLogger.log(LogLevel.INFO, "Spawn instance created.");
    }
    private static void loadServerConfig() {
        serverLogger.log(LogLevel.INFO, "Loading Server Configuration...");
        createDefaultServerConfig();
        ip = serverConfig.getValue("server.base.ip-adresse");
        port = Utils.convertToInt(serverConfig.getValue("server.base.port")).orElse(25565);
        mojangAuth = Utils.convertToBool(serverConfig.getValue("server.base.use-mojang-auth")).orElse(true);
        if(mojangAuth) MojangAuth.init();
        serverLogger.log(LogLevel.INFO, "Server Configuration loaded successfully.");
    }

    private static void createDefaultServerConfig() {
        serverConfig = new ConfigManager("server");

        serverConfig.addDefault(new ConfigValue("server.base.ip-adresse", "127.0.0.1"));
        serverConfig.addDefault(new ConfigValue("server.base.port", "25565"));
        serverConfig.addDefault(new ConfigValue("server.base.use-mojang-auth", "true"));
    }

    private static void registerCommands() {
        MinecraftServer.getCommandManager().register(new CmdClearChat());
        MinecraftServer.getCommandManager().register(new CmdMuteChat());
    }


}
