package net.projectuniverse.general.cactus_clicker;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.Module;
import net.projectuniverse.general.cactus_clicker.commands.CmdPlay;
import net.projectuniverse.general.cactus_clicker.commands.CmdSpawn;
import net.projectuniverse.general.cactus_clicker.listener.JoinListener;
import net.projectuniverse.general.cactus_clicker.listener.LeaveListener;
import net.projectuniverse.general.cactus_clicker.listener.PlayerPlaceBlockListener;
import net.projectuniverse.general.database.DatabaseTable;
import net.projectuniverse.general.logging.LogLevel;

import java.util.List;

public class ModuleCactusClicker extends Module {


    /**
     * Creates a new Module instance.
     *
     * @param name        the name of the module
     * @param description the description of the module
     */
    public ModuleCactusClicker() {
        super("CactusClicker", "Cactus Clicker Game Mode");
    }

    @Override
    public void start() {
        moduleLogger.log(LogLevel.INFO, "Startup...");
        createDatabase();
        registerListener();
        registerCommands();
        CactusHarvester.init();
        moduleLogger.log(LogLevel.INFO, "Started successfully.");
    }

    private void registerListener() {
        new JoinListener();
        new LeaveListener();
        new PlayerPlaceBlockListener();
    }

    private void registerCommands() {
        MinecraftServer.getCommandManager().register(new CmdPlay());
        MinecraftServer.getCommandManager().register(new CmdSpawn());
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean reload() {
        return false;
    }

    @Override
    public List<DatabaseTable> getDatabase() {
        return List.of(buildCactusClickerPlayerIslandsTable());
    }

    /**
     * Builds and returns the database for players.
     *
     * @return The constructed player database.
     */
    private DatabaseTable buildCactusClickerPlayerIslandsTable() {
        return new DatabaseTable.DatabaseTableBuilder("CactusClickerPlayerIslands")
                .addField(new DatabaseTable.Column("PlayerIslandID", DatabaseTable.ColumnType.INTEGER, true, true, true, null))
                .addField(new DatabaseTable.Column("PlayerId", DatabaseTable.ColumnType.INTEGER, false, false, true, null))
                .addField(new DatabaseTable.Column("Path", DatabaseTable.ColumnType.LONG_TEXT, false, false, true, null))
                .addField(new DatabaseTable.Column("BoundYStart", DatabaseTable.ColumnType.INTEGER, false, false, false, "-64"))
                .addField(new DatabaseTable.Column("BoundYEnd", DatabaseTable.ColumnType.INTEGER, false, false, false, "320"))
                .build();
    }
}
