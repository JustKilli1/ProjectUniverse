package net.projectuniverse.general.money_system;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.projectuniverse.general.Module;
import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;
import net.projectuniverse.general.database.DatabaseTable;
import net.projectuniverse.general.database.DatabaseCreator;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.money_system.commands.*;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;
import net.projectuniverse.general.money_system.listener.JoinListener;

import java.util.Arrays;
import java.util.List;


/**
 * The ModuleMoneySystem class is a module that controls everything related to money management and transactions.
 */

public class ModuleMoneySystem extends Module {

    public static final ArgumentWord CURRENCY_ARG = ArgumentType.Word("Currency").from(Arrays.stream(UniCurrency.values()).map(Enum::toString).toArray(String[]::new));
    private static final ConfigManager configManager = new ConfigManager("money_system");
    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    public ModuleMoneySystem() {
        super("Money System", "This System Controls everything related to Money Management/Transactions.");
        sql = new DBALMoney();
        dbHandler = new DBHMoney(sql);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        moduleLogger.log(LogLevel.INFO, "Startup...");
        createDefaultServerConfig();
        createDatabase();
        registerListener();
        registerCommands();

        moduleLogger.log(LogLevel.INFO, "Started successfully.");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<DatabaseTable> getDatabase() {
        return List.of(buildPlayerMoneyTable());
    }

    /**
     * Builds and returns the database for players.
     *
     * @return The constructed player database.
     */
    private DatabaseTable buildPlayerMoneyTable() {
        return new DatabaseTable.DatabaseTableBuilder("PlayerMoneyTable")
                .addField(new DatabaseTable.Column("PlayerPurseID", DatabaseTable.ColumnType.INTEGER, true, true, true, null))
                .addField(new DatabaseTable.Column("PlayerId", DatabaseTable.ColumnType.INTEGER, false, false, true, null))
                .addField(new DatabaseTable.Column("Currency", DatabaseTable.ColumnType.VARCHAR_20, false, false, true, null))
                .addField(new DatabaseTable.Column("Amount", DatabaseTable.ColumnType.INTEGER, false, false, false, "0"))
                .build();
    }

    /**
     * Register a new listener for the module.
     */
    private void registerListener() {
        new JoinListener(moduleLogger, sql, dbHandler);
    }

    /**
     * Register commands for the module.
     */
    private void registerCommands() {
        MinecraftServer.getCommandManager().register(new CmdPay(sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdMoney(sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdAddMoney(moduleLogger, sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdRemoveMoney(moduleLogger, sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdBalanceTop(dbHandler));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        moduleLogger.log(LogLevel.INFO, "Shutting down...");
        moduleLogger.log(LogLevel.INFO, "Shutting down successfully.");
    }

    /**
     * Creates the default Money System configuration.
     * This method adds default configuration values for each {@link UniCurrency}.
     * The configuration values specify the starting amount of each currency on the server.
     */
    private void createDefaultServerConfig() {
        for(UniCurrency currency : UniCurrency.values()) {
            configManager.addDefault(new ConfigValue(String.format("currency.%s.start_amount", currency.toString().toLowerCase()), "1000"));
        }
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean reload() {
        return true;
    }
}
