package net.projectuniverse.general.player_system;

import net.projectuniverse.general.Module;
import net.projectuniverse.general.database.DatabaseTable;

import java.util.List;

public class ModulePlayerSystem extends Module {
    public ModulePlayerSystem() {
        super("PlayerSystem", "System responsible for managing Players");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean reload() {
        return false;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<DatabaseTable> getDatabase() {
        return null;
    }
}
