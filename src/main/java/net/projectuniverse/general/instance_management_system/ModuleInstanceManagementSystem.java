package net.projectuniverse.general.instance_management_system;

import net.projectuniverse.general.Module;
import net.projectuniverse.general.database.DatabaseTable;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;

import java.util.List;

public class ModuleInstanceManagementSystem extends Module {
    public ModuleInstanceManagementSystem() {
        super("InstanceManagementSystem", "Manages Instances");
    }

    @Override
    public void start() {
        moduleLogger.log(LogLevel.INFO, "Startup...");
        createDatabase();
        registerListener();
        registerCommands();
        moduleLogger.log(LogLevel.INFO, "Started successfully.");
    }

    @Override
    public List<DatabaseTable> getDatabase() {
        return null;
    }

    private void registerCommands() {
    }

    private void registerListener() {

    }


    @Override
    public void stop() {

    }

    @Override
    public boolean reload() {
        return false;
    }
}
