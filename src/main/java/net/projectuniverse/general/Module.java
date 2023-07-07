package net.projectuniverse.general;

import net.projectuniverse.general.logging.ILogger;

public abstract class Module implements IReloadable{

    private final String name, description;
    protected final ILogger moduleLogger;

    protected Module(String name, String description, ILogger moduleLogger) {
        this.name = name;
        this.description = description;
        this.moduleLogger = moduleLogger;
    }

    public abstract void start();
    public abstract void stop();

    @Override
    public abstract boolean reload();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
