package net.projectuniverse.general.money_system;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.Module;
import net.projectuniverse.general.database.Database;
import net.projectuniverse.general.database.DatabaseCreator;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.money_system.commands.*;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;
import net.projectuniverse.general.money_system.listener.JoinListener;

import java.util.List;


/**
 * The ModuleMoneySystem class is a module that controls everything related to money management and transactions.
 */

public class ModuleMoneySystem extends Module {

    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    public ModuleMoneySystem() {
        super("Money System", "This System Controls everything related to Money Management/Transactions.", new LoggerBuilder("MoneySystem").addOutputPrinter(new TerminalPrinter()).build());
        sql = new DBALMoney();
        dbHandler = new DBHMoney(sql);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        moduleLogger.log(LogLevel.INFO, "Startup...");
        createDatabase();
        registerListener();
        registerCommands();

        moduleLogger.log(LogLevel.INFO, "Started successfully.");
    }

    /**
     * Create the database tables.
     */
    private void createDatabase() {
        moduleLogger.log(LogLevel.INFO, "Create Database Tables...");
        DatabaseCreator databaseCreator = new DatabaseCreator(moduleLogger, sql, getDatabaseList());
        databaseCreator.create();
        moduleLogger.log(LogLevel.INFO, "Database Tables created.");
    }

    /**
     * Retrieves the list of databases.
     *
     * @return The list of databases.
     */
    private List<Database> getDatabaseList() {
        return List.of(buildPlayerMoneyTable());
    }

    /**
     * Builds and returns the database for players.
     *
     * @return The constructed player database.
     */
    private Database buildPlayerMoneyTable() {
        return new Database.DatabaseBuilder("PlayerMoneyTable")
                .addField(new Database.Column("PlayerPurseID", Database.ColumnType.INTEGER, true, true, true, null))
                .addField(new Database.Column("PlayerId", Database.ColumnType.INTEGER, false, false, true, null))
                .addField(new Database.Column("Currency", Database.ColumnType.VARCHAR_20, false, false, true, null))
                .addField(new Database.Column("Amount", Database.ColumnType.INTEGER, false, false, false, "0"))
                .build();
    }

    private void registerListener() {
        new JoinListener(moduleLogger, sql, dbHandler);
    }

    private void registerCommands() {
        MinecraftServer.getCommandManager().register(new CmdPay(sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdMoney(sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdAddMoney(moduleLogger, sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdRemoveMoney(moduleLogger, sql, dbHandler));
        MinecraftServer.getCommandManager().register(new CmdBalanceTop(sql, dbHandler));
    }

    @Override
    public void stop() {
        moduleLogger.log(LogLevel.INFO, "Shutting down...");
        moduleLogger.log(LogLevel.INFO, "Shutting down successfully.");
    }

    @Override
    public boolean reload() {
        return true;
    }
}
