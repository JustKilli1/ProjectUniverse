package net.projectuniverse.general.penalty_system;

import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.Module;
import net.projectuniverse.general.database.DatabaseTable;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.penalty_system.commands.CmdBan;
import net.projectuniverse.general.penalty_system.commands.CmdKick;
import net.projectuniverse.general.penalty_system.commands.CmdUnban;
import net.projectuniverse.general.penalty_system.database.DBALPenaltySystem;
import net.projectuniverse.general.penalty_system.database.DBHPenaltySystem;
import net.projectuniverse.general.penalty_system.listener.JoinListener;

import java.util.List;

/**
 * The ModulePenaltySystem class is a concrete implementation of the Module class.
 * It is responsible for managing player penalties within the system.
 */

public class ModulePenaltySystem extends Module {

    private final DBALPenaltySystem SQL;
    private final DBHPenaltySystem DB_HANDLER;

    public ModulePenaltySystem() {
        super("PenaltySystem", "This Module is responsible for Player Penalty's");
        SQL = new DBALPenaltySystem();
        DB_HANDLER = new DBHPenaltySystem(SQL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        moduleLogger.log(LogLevel.INFO, "Startup...");
        createDatabase();
        registerListener();
        registerCommands();
        moduleLogger.log(LogLevel.INFO, "Started successfully.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DatabaseTable> getDatabase() {
        return List.of(buildPunishmentReasonDatabase());
    }

    /**
     * Builds and returns the database for punishment reasons.
     *
     * @return The constructed punishment reason database.
     */
    private DatabaseTable buildPunishmentReasonDatabase() {
        return new DatabaseTable.DatabaseTableBuilder("PunishmentSystemReason")
                .addField(new DatabaseTable.Column("ReasonID", DatabaseTable.ColumnType.INTEGER, true, true, true, null))
                .addField(new DatabaseTable.Column("PlayerId", DatabaseTable.ColumnType.INTEGER, false, false, true, null))
                .addField(new DatabaseTable.Column("Reason", DatabaseTable.ColumnType.LONG_TEXT, false, false, false, null))
                .addField(new DatabaseTable.Column("Duration", DatabaseTable.ColumnType.INTEGER, false, false, false, "9999"))
                .addField(new DatabaseTable.Column("DurationId", DatabaseTable.ColumnType.VARCHAR_1, false, false, false, "y"))
                .build();
    }

    private void registerListener() {
        new JoinListener(DB_HANDLER);
    }

    /**
     * Registers the commands related to punishment system.
     */
    private void registerCommands() {
        MinecraftServer.getCommandManager().register(new CmdKick());
        MinecraftServer.getCommandManager().register(new CmdBan(SQL, DB_HANDLER));
        MinecraftServer.getCommandManager().register(new CmdUnban(DB_HANDLER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        moduleLogger.log(LogLevel.INFO, "Shutdown...");
        moduleLogger.log(LogLevel.INFO, "Shutdown successfully.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean reload() {
        return false;
    }
}
