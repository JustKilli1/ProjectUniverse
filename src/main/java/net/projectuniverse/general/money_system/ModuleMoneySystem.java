package net.projectuniverse.general.money_system;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.Module;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;

public class ModuleMoneySystem extends Module {


    public ModuleMoneySystem() {
        super("Money System", "This System Controls everything related to Money Management/Transactions.", new LoggerBuilder("ModuleMoneySystem").addOutputPrinter(new TerminalPrinter()).build());
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
    }

    private void registerListener() {
    }

    private void registerCommands() {
    }

    @Override
    public void stop() {

    }

    @Override
    public void reload() {

    }
}
