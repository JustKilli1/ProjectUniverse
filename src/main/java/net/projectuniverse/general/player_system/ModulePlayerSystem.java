package net.projectuniverse.general.player_system;

import net.projectuniverse.general.Module;
import net.projectuniverse.general.database.DatabaseTable;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.player_system.database.DBALPlayerSystem;
import net.projectuniverse.general.player_system.listener.JoinListener;
import net.projectuniverse.general.player_system.listener.LeaveListener;

import java.util.List;

public class ModulePlayerSystem extends Module {

    private DBALPlayerSystem sql;

    public ModulePlayerSystem() {
        super("PlayerSystem", "System responsible for managing Players");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        moduleLogger.log(LogLevel.INFO, "Startup...");
        createDatabase();
        sql = new DBALPlayerSystem();
        registerListener();
        moduleLogger.log(LogLevel.INFO, "Started successfully.");
    }
    private void registerListener() {
        new JoinListener(sql);
        new LeaveListener(sql);
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
        return List.of(buildPlayerSessionsTable());
    }

    private DatabaseTable buildPlayerSessionsTable() {
        return new DatabaseTable.DatabaseTableBuilder("PlayerSessions")
                .addField(new DatabaseTable.Column("PlayerSessionID", DatabaseTable.ColumnType.INTEGER, true, true, true, null))
                .addField(new DatabaseTable.Column("PlayerId", DatabaseTable.ColumnType.INTEGER, false, false, true, null))
                .addField(new DatabaseTable.Column("StartDate", DatabaseTable.ColumnType.DATE, false, false, true, null))
                .addField(new DatabaseTable.Column("StartTime", DatabaseTable.ColumnType.TIME, false, false, true, null))
                .addField(new DatabaseTable.Column("EndDate", DatabaseTable.ColumnType.DATE, false, false, false, null))
                .addField(new DatabaseTable.Column("EndTime", DatabaseTable.ColumnType.TIME, false, false, false, null))
                .build();
    }

}
