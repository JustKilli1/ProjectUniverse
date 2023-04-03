package net.projectuniverse.general.instance;

import net.minestom.server.instance.InstanceContainer;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InstanceHandler {

    private static final String folderPath = "instances/";
    public static final InstanceContainer LOBBY = InstanceImporter.importWorld(folderPath + "/lobby");
    public static final InstanceContainer WORLD2 = InstanceImporter.importWorld(folderPath + "/world2");
    public static final InstanceContainer WORLD3 = InstanceImporter.importWorld(folderPath + "/world3");
    public static final InstanceContainer TOWER_DEFENCE_ARENA = InstanceImporter.importWorld(folderPath + "/TowerDefenceArena");
    private static final ILogger instanceLogger = new LoggerBuilder("InstanceManager").addOutputPrinter(new TerminalPrinter()).build();
    private static Map<String, InstanceContainer> instances = new HashMap<>();

    public InstanceHandler() {
        new File("instances").mkdirs();
    }

    public static void addInstance(String key, InstanceContainer value) {
        instances.put(key, value);
        instanceLogger.log(LogLevel.INFO, "New Instance with the name " + key + " added");
    }

    public static void removeInstance(String key) {
        instances.remove(key);
        instanceLogger.log(LogLevel.INFO, "Instance with the name " + key + " removed");
    }

    public static InstanceContainer getInstance(UUID key) {
        return instances.get(key);
    }


}
