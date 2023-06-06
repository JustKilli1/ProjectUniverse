package net.projectuniverse.general;

import net.projectuniverse.general.logging.ILogger;

public abstract class Module {

    private final String name, description;
    protected final ILogger moduleLogger;

    protected Module(String name, String description, ILogger moduleLogger) {
        this.name = name;
        this.description = description;
        this.moduleLogger = moduleLogger;
    }

    public abstract void start();
    public abstract void stop();

    public abstract void reload();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
