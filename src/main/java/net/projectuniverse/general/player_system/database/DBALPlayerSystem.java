package net.projectuniverse.general.player_system.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.database.DatabaseTable;

import java.sql.Date;
import java.sql.Time;

public class DBALPlayerSystem extends DBAccessLayer {
/*                .addField(new DatabaseTable.Column("PlayerSessionID", DatabaseTable.ColumnType.INTEGER, true, true, true, null))
                .addField(new DatabaseTable.Column("PlayerId", DatabaseTable.ColumnType.INTEGER, false, false, true, null))
                .addField(new DatabaseTable.Column("StartDate", DatabaseTable.ColumnType.DATE, false, false, true, null))
                .addField(new DatabaseTable.Column("StartTime", DatabaseTable.ColumnType.TIME, false, false, true, null))
                .addField(new DatabaseTable.Column("EndDate", DatabaseTable.ColumnType.DATE, false, false, false, null))
                .addField(new DatabaseTable.Column("EndTime", DatabaseTable.ColumnType.TIME, false, false, false, null))
                .build();*/

    public boolean insertPlayerSession(Player player, Date startDate, Time startTime) {
        String sqlQuery = String.format("INSERT INTO PlayerSessions (PlayerId, StartDate, StartTime) VALUES ((%s), '%s', '%s')",
                String.format("SELECT PlayerID FROM Player WHERE UUID = '%s'", player.getUuid()),
                startDate.toString(),
                startTime.toString());
        return executeSQLRequest(sqlQuery);
    }

    public boolean updatePlayerSession(Player player, Date endDate, Time endTime) {
        String sqlQuery = String.format("UPDATE PlayerSessions SET EndDate = '%s', EndTime = '%s' WHERE PlayerId = (%s) AND EndDate IS NULL",
                endDate.toString(),
                endTime.toString(),
                String.format("SELECT PlayerID FROM Player WHERE UUID = '%s'", player.getUuid()));
        return executeSQLRequest(sqlQuery);
    }

}
