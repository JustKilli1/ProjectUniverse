package net.projectuniverse.general.database;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;

import java.sql.ResultSet;


public class DBHandler {

    /**
     * Gets ResultSets from sql request
     * --> Gets Information needed from ResultSet and returns it
     * Works with sql return data(ResultSets)
     * */
    protected DBAccessLayer sql;
    private static final ILogger logger = DBAccessLayer.logger;

    public DBHandler(DBAccessLayer sql) {
        this.sql = sql;
    }


    public boolean playerInDatabase(Player player) {
        try {
            ResultSet result = sql.getPlayer(player);
            if(result == null || !result.next()) return false;
            return true;
        } catch(Exception ex) {
            logger.log(LogLevel.ERROR, "Could not execute Player in Database Query", ex);
            return false;
        }
    }

}
