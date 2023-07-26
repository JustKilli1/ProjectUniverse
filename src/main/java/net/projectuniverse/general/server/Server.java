package net.projectuniverse.general.server;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.projectuniverse.base.Utils;
import net.projectuniverse.general.ModuleLoader;
import net.projectuniverse.general.commands.*;
import net.projectuniverse.general.commands.warps.CmdWarp;
import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.database.DatabaseTable;
import net.projectuniverse.general.database.DatabaseCreator;
import net.projectuniverse.general.instance.InstanceHandler;
import net.projectuniverse.general.listener.ChatListener;
import net.projectuniverse.general.listener.JoinListener;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.terminal.ServerTerminal;

import java.util.List;

public class Server {

    public static final long START_TIME = System.currentTimeMillis();
    public static final ILogger SERVER_LOGGER = new LoggerBuilder("Server").addOutputPrinter(new TerminalPrinter()).build();
    public static ModuleLoader MODULE_LOADER;
    public static ConfigManager MYSQL_CONFIG;
    public static DBAccessLayer SQL;
    public static DBHandler DB_HANDLER;
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

        MYSQL_CONFIG = new ConfigManager("mysql");
        SQL = new DBAccessLayer();
        DB_HANDLER = new DBHandler(SQL);

        try {
            //TODO IS SHIT
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }

        SERVER_LOGGER.log(LogLevel.INFO, "Starting Project Universe...");
        loadServerConfig();
        createSpawnInstance();
        SERVER_LOGGER.log(LogLevel.INFO, "Starting Minestom Service...");
        registerCommands();
        registerListener();
        server.start(ip, port);
        SERVER_LOGGER.log(LogLevel.INFO, "Minestom Service started successfully");
        try {
            //TODO IS SHIT
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        createDatabase();
        startModules();

        SERVER_LOGGER.log(LogLevel.INFO, String.format("Bound IP-Address: %s", ip));
        SERVER_LOGGER.log(LogLevel.INFO, String.format("Bound Port: %d", port));
        SERVER_LOGGER.log(LogLevel.INFO, "Project Universe startup complete.");
        SERVER_LOGGER.log(LogLevel.INFO, "Hello c:");

    }

    /**
     * Stops the Server
     * */
    public static void stop() {
        MODULE_LOADER.stopModules();
        SERVER_LOGGER.log(LogLevel.INFO, "Server closed");
        System.exit(0);
    }

    private static void startModules() {
        MODULE_LOADER = new ModuleLoader();
        MODULE_LOADER.startModules();
    }

    private static void createSpawnInstance() {
        SERVER_LOGGER.log(LogLevel.INFO, "Creating spawn instance...");
        // Create the instance
        spawnInstance = InstanceHandler.TOWER_DEFENCE_ARENA;
        InstanceHandler.addInstance("lobby", spawnInstance);
        // Set the ChunkGenerator
        spawnInstance.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
        spawnInstance.saveChunksToStorage();
        SERVER_LOGGER.log(LogLevel.INFO, "Spawn instance created.");
    }
    private static void loadServerConfig() {
        SERVER_LOGGER.log(LogLevel.INFO, "Loading Server Configuration...");
        createDefaultServerConfig();
        ip = serverConfig.getValue("server.base.ip-address");
        port = Utils.convertToInt(serverConfig.getValue("server.base.port")).orElse(25565);
        mojangAuth = Utils.convertToBool(serverConfig.getValue("server.base.use-mojang-auth")).orElse(true);
        if(mojangAuth) MojangAuth.init();
        SERVER_LOGGER.log(LogLevel.INFO, "Server Configuration loaded successfully.");
    }

    private static void createDefaultServerConfig() {
        serverConfig = new ConfigManager("server");

        serverConfig.addDefault(new ConfigValue("server.base.ip-address", "127.0.0.1"));
        serverConfig.addDefault(new ConfigValue("server.base.port", "25565"));
        serverConfig.addDefault(new ConfigValue("server.base.use-mojang-auth", "true"));
    }

    private static void registerCommands() {
        MinecraftServer.getCommandManager().register(new CmdClearChat());
        MinecraftServer.getCommandManager().register(new CmdMuteChat());
        MinecraftServer.getCommandManager().register(new CmdTeamChat());
        MinecraftServer.getCommandManager().register(new CmdGameMode());
        MinecraftServer.getCommandManager().register(new CmdTeleport());
        MinecraftServer.getCommandManager().register(new CmdTest());
        MinecraftServer.getCommandManager().register(new CmdWarp());
        MinecraftServer.getCommandManager().register(new CmdPerformance());
    }

    /**
     * Create Database Tables
     * This method creates the necessary database tables for the server to function properly.
     * It initializes a DatabaseCreator object and uses it to create the tables.
     *
     * <p>The DatabaseCreator object is initialized with the following parameters:
     * <ul>
     *   <li>SERVER_LOGGER: The logger object used to log messages during the database creation process.</li>
     *   <li>sql: The SQL database connection Object.</li>
     *   <li>getDatabaseList(): A method that returns a list of tables to be created.</li>
     * </ul>
     *
     * <p>After creating the tables, a log message is generated to indicate successful completion.
     *
     * <p>Note: The DatabaseCreator class and the LOGGER object used must be properly initialized before calling this method.
     *
     * @see DatabaseCreator
     * @see ILogger
     */
    private static void createDatabase() {
        SERVER_LOGGER.log(LogLevel.INFO, "Create Database Tables...");
        DatabaseCreator databaseCreator = new DatabaseCreator(SERVER_LOGGER, SQL, getDatabaseList());
        databaseCreator.create();
        SERVER_LOGGER.log(LogLevel.INFO, "Database Tables created.");
    }

    /**
     * Retrieves the list of databases.
     *
     * @return The list of databases.
     */
    private static List<DatabaseTable> getDatabaseList() {
        return List.of(buildPlayerDatabase());
    }

    /**
     * Builds and returns the database for players.
     *
     * @return The constructed player database.
     */
    private static DatabaseTable buildPlayerDatabase() {
        return new DatabaseTable.DatabaseTableBuilder("Player")
                .addField(new DatabaseTable.Column("PlayerID", DatabaseTable.ColumnType.INTEGER, true, true, true, null))
                .addField(new DatabaseTable.Column("UUID", DatabaseTable.ColumnType.TEXT, false, false, true, ""))
                .addField(new DatabaseTable.Column("Name", DatabaseTable.ColumnType.TEXT, false, false, true, null))
                .build();
    }

    private static void registerListener() {
        new JoinListener(SQL, DB_HANDLER, spawnInstance);
        new ChatListener();
    }


}
