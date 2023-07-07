package net.projectuniverse.general.money_system;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.Module;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.money_system.commands.*;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;
import net.projectuniverse.general.money_system.listener.JoinListener;



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
    }

    private void createDatabases() {
        sql.createPlayerMoneyTable();
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
