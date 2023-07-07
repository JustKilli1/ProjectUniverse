package net.projectuniverse.general;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ModuleLoader {

    private final ILogger logger = new LoggerBuilder("ModuleLoader").addOutputPrinter(new TerminalPrinter()).build();
    /**
     * Represents a list of modules.
     * @see Module
     */
    private final List<Module> modules;

    /**
     * Constructs a new ModuleLoader instance with the given modules.
     *
     * @param modules the list of modules to load
     */
    public ModuleLoader(List<Module> modules) {
        this.modules = modules;
    }

    /**
     * Constructs a new ModuleLoader instance and loads all modules in the net.projectuniverse package
     * and its sub-packages.
     */
    public ModuleLoader() {
        modules = loadModules();
    }

    /**
     * Loads all modules in the net.projectuniverse package and its sub-packages.
     *
     * @return a List of Module objects representing the loaded modules
     */
    public List<Module> loadModules() {
        Reflections reflections = new Reflections("net.projectuniverse");
        Set<Class<? extends Module>> classes = reflections.getSubTypesOf(Module.class);
        return classes.stream()
                .map(this::createModule)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Creates a module instance of the given class.
     *
     * @param clazz the class of the module
     * @return an instance of the module or null if any exception occurred
     */
    private Module createModule(Class<? extends Module> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, "Failed to create module instance for module " + clazz.getName() + " with error: ", ex);
            return null;
        }
    }

    /**
     * Starts all the modules.
     *
     * This method iterates over the list of modules and starts each module by calling their start method.
     * It logs the module startup process using the logger.
     */
    public void startModules() {
        logger.log(LogLevel.INFO, "Modules startup...");
        modules.forEach(Module::start);
        logger.log(LogLevel.INFO, "Modules started");
    }

    public void stopModules() {
        logger.log(LogLevel.INFO, "Module Shutdown...");
        modules.forEach(Module::stop);
        logger.log(LogLevel.INFO, "Module Shutdown successfully.");
    }

    public void reloadModules() {
        modules.forEach(Module::reload);
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        return modules.stream().filter(module -> module.getName().equals(name)).findFirst().orElse(null);
    }

}
