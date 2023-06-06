package net.projectuniverse.general.money_system.database;

import net.projectuniverse.general.database.DBHandler;


public class DBHMoney extends DBHandler {

    private final DBALMoney sql;

    public DBHMoney(DBALMoney sql) {
        super(sql);
        this.sql = sql;
    }
}
