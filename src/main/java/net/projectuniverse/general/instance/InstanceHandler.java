package net.projectuniverse.general.instance;

import net.minestom.server.instance.InstanceContainer;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InstanceHandler {

    private static final String folderPath = "instances/";
    public static final InstanceContainer LOBBY = InstanceImporter.importWorld(folderPath + "/lobby");
    private static final ILogger instanceLogger = new LoggerBuilder("InstanceManager").addOutputPrinter(new TerminalPrinter()).build();
    private static Map<String, InstanceContainer> instances = new HashMap<>();

    public InstanceHandler() {
        new File("instances").mkdirs();
    }

    public static void addInstance(String key, InstanceContainer value) {
        instances.put(key, value);
    }

    public static void removeInstance(String key) {
        instances.remove(key);
    }

    public static InstanceContainer getInstance(UUID key) {
        return instances.get(key);
    }


}
