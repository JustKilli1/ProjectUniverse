package net.projectuniverse.general.server;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.base.Utils;
import net.projectuniverse.general.commands.*;
import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.instance.InstanceHandler;
import net.projectuniverse.general.listener.ChatListener;
import net.projectuniverse.general.listener.JoinListener;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.terminal.ServerTerminal;
import net.projectuniverse.modules.tower_defence.commands.CmdLeave;
import net.projectuniverse.modules.tower_defence.commands.CmdPlay;

public class Server {

    public static final ILogger SERVER_LOGGER = new LoggerBuilder("Server").addOutputPrinter(new TerminalPrinter()).build();
    private static DBAccessLayer sql;
    private static DBHandler dbHandler;
    private static ConfigManager serverConfig;
    private static MinecraftServer server;
    private static ServerTerminal terminal;
    private static String ip;
    private static int port;
    private static boolean mojangAuth;
    private static InstanceContainer spawnInstance;

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

        SERVER_LOGGER.log(LogLevel.INFO, "Starting Project Universe...");
        loadServerConfig();
        sql = new DBAccessLayer(new ConfigManager("mysql"));
        dbHandler = new DBHandler(sql);
        createSpawnInstance();
        SERVER_LOGGER.log(LogLevel.INFO, "Starting Minestom Service...");
        registerCommands();
        registerListener();
        server.start(ip, port);
        try {
            //TODO IS SHIT
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        SERVER_LOGGER.log(LogLevel.INFO, "Minestom Service started successfully");
        SERVER_LOGGER.log(LogLevel.INFO, "Bound IP-Adresse: " + ip);
        SERVER_LOGGER.log(LogLevel.INFO, "Bound Port: " + port);
        createDatabase();
        SERVER_LOGGER.log(LogLevel.INFO, "Project Universe startup complete.");
        SERVER_LOGGER.log(LogLevel.INFO, "Hello c:");

    }

    /**
     * Stops the Server
     * */
    public static void stop() {
        SERVER_LOGGER.log(LogLevel.INFO, "Server closed");
        System.exit(0);
    }

    private static void createSpawnInstance() {
        SERVER_LOGGER.log(LogLevel.INFO, "Creating spawn instance...");
        new InstanceHandler();
        // Create the instance
        spawnInstance = InstanceHandler.TOWER_DEFENCE_ARENA;
        InstanceHandler.addInstance("lobby", spawnInstance);
        // Set the ChunkGenerator
        spawnInstance.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
        SERVER_LOGGER.log(LogLevel.INFO, "Spawn instance created.");
    }
    private static void loadServerConfig() {
        SERVER_LOGGER.log(LogLevel.INFO, "Loading Server Configuration...");
        createDefaultServerConfig();
        ip = serverConfig.getValue("server.base.ip-adresse");
        port = Utils.convertToInt(serverConfig.getValue("server.base.port")).orElse(25565);
        mojangAuth = Utils.convertToBool(serverConfig.getValue("server.base.use-mojang-auth")).orElse(true);
        if(mojangAuth) MojangAuth.init();
        SERVER_LOGGER.log(LogLevel.INFO, "Server Configuration loaded successfully.");
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
        MinecraftServer.getCommandManager().register(new CmdTeamChat());
        MinecraftServer.getCommandManager().register(new CmdKick());
        MinecraftServer.getCommandManager().register(new CmdBan(sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdUnban(dbHandler));
        MinecraftServer.getCommandManager().register(new CmdGameMode());
        MinecraftServer.getCommandManager().register(new CmdTeleport());
        MinecraftServer.getCommandManager().register(new CmdPlay());
        MinecraftServer.getCommandManager().register(new CmdLeave());
    }

    private static void createDatabase() {
        SERVER_LOGGER.log(LogLevel.INFO, "Create Database...");
        sql.createPlayerTable();
        sql.createPunishmentReasonTable();
        SERVER_LOGGER.log(LogLevel.INFO, "Database created.");
    }

    private static void registerListener() {
        new JoinListener(sql, dbHandler, spawnInstance);
        new ChatListener();
    }


}
