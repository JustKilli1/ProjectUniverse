package net.projectuniverse.general;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleLoader {

    private final ILogger logger = new LoggerBuilder("ModuleLoader").addOutputPrinter(new TerminalPrinter()).build();
    private final List<Module> modules;

    public ModuleLoader(List<Module> modules) {
        this.modules = modules;
    }

    public ModuleLoader() {
        modules = loadModules();
    }

    public List<Module> loadModules() {
        List<Module> modules = new ArrayList<>();
        Reflections reflections = new Reflections("net.projectuniverse");
        Set<Class<? extends Module>> classes = reflections.getSubTypesOf(Module.class);
        classes.forEach(clazz -> {
            try {
                Module module = clazz.getConstructor().newInstance();
                modules.add(module);
            } catch (Exception ex) {
                logger.log(LogLevel.ERROR, "Failed to create module instance for module " + clazz.getName() + " with error: ", ex);
            }
        });
        return modules;
    }

    public void startModules() {
        logger.log(LogLevel.INFO, "Modules startup...");
        modules.forEach(Module::start);
        logger.log(LogLevel.INFO, "Modules started");
    }

    public void stopModules() {
        modules.forEach(Module::stop);
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
