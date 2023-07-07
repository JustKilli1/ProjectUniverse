package net.projectuniverse.general.database;


import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Represents a database.
 */

public record Database(String name, Map<String, ColumnType> fields) {

    /**
     * Returns the database creation query for creating a table with the given name and fields.
     * The query is in the form of a SQL statement.
     * If the table already exists, the "IF NOT EXISTS" clause is used to avoid errors.
     *
     * @return the database creation query as a string
     */
    public String getDatabaseCreationQuery() {
        StringJoiner fieldsJoiner = new StringJoiner(", ");
        for(Map.Entry<String, ColumnType> entry : fields.entrySet()) {
            fieldsJoiner.add(entry.getKey() + " " + entry.getValue());
        }
        return "CREATE TABLE IF NOT EXISTS " + name + " (" + fieldsJoiner.toString() + ");";
    }

    /**
     * The ColumnType enum represents the different types of columns that can be used in a database.
     */
    public static enum ColumnType {
        INTEGER("INTEGER"),
        TEXT("TEXT"),
        VARCHAR_1("VARCHAR(1)"),
        VARCHAR_2("VARCHAR(2)"),
        VARCHAR_3("VARCHAR(3)"),
        VARCHAR_5("VARCHAR(5)"),
        VARCHAR_10("VARCHAR(10)"),
        VARCHAR_15("VARCHAR(15)"),
        VARCHAR_20("VARCHAR(20)")
        ;
        private final String sqlType;

        private ColumnType(String sqlType) {
            this.sqlType = sqlType;
        }

        @Override
        public String toString() {
            return sqlType;
        }
    }

}
