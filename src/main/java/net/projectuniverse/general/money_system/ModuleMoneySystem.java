package net.projectuniverse.general.money_system;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.Module;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.money_system.commands.CmdAddMoney;
import net.projectuniverse.general.money_system.commands.CmdMoney;
import net.projectuniverse.general.money_system.commands.CmdPay;
import net.projectuniverse.general.money_system.commands.CmdRemoveMoney;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;
import net.projectuniverse.general.money_system.listener.JoinListener;

public class ModuleMoneySystem extends Module {

    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    public ModuleMoneySystem() {
        super("Money System", "This System Controls everything related to Money Management/Transactions.", new LoggerBuilder("ModuleMoneySystem").addOutputPrinter(new TerminalPrinter()).build());
        sql = new DBALMoney();
        dbHandler = new DBHMoney(sql);
    }

    @Override
    public void start() {
        moduleLogger.log(LogLevel.INFO, "Module [" + getName() + "] startup...");
        moduleLogger.log(LogLevel.INFO, "Creating Databases...");
        createDatabases();
        moduleLogger.log(LogLevel.INFO, "Databases created.");
        registerListener();
        registerCommands();

        moduleLogger.log(LogLevel.INFO, "Module [" + getName() + "] started");
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
    }

    @Override
    public void stop() {

    }

    @Override
    public void reload() {

    }
}
