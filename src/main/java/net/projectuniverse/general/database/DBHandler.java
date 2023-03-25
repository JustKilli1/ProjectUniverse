package net.projectuniverse.general.database;

public class DBHandler {

    /**
     * Gets ResultSets from sql request
     * --> Gets Information needed from ResultSet and returns it
     * Works with sql return data(ResultSets)
     * */
    protected DBAccessLayer sql;

    public DBHandler(DBAccessLayer sql) {
        this.sql = sql;
    }

}
