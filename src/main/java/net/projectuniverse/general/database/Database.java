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
     * The DatabaseBuilder class is used to build instances of the Database class.
     * It provides methods to set the name of the database and add fields to it.
     */
    public static class DatabaseBuilder {

        /**
         * Represents the name of the Database Table.
         */
        private final String name;
        /**
         * A map representing the fields.
         */
        private final Map<String, ColumnType> fields;

        /**
         * Constructs a new DatabaseBuilder object with the specified name and fields.
         *
         * @param name   the name of the database (must not be null)
         * @param fields a map of field names and their corresponding values (must not be null)
         * @throws IllegalArgumentException if name or fields parameter is null
         */
        public DatabaseBuilder(String name, Map<String, ColumnType> fields) {
            if (name == null || fields == null) {
                throw new IllegalArgumentException("Name and fields must not be null");
            }
            this.name = name;
            this.fields = new HashMap<>(fields);
        }

        /**
         * Constructs a new DatabaseBuilder with the given name and an empty field configuration.
         *
         * @param name the name of the database
         */
        public DatabaseBuilder(String name) {
            this(name, new HashMap<>());
        }

        /**
         * Adds a field to the database builder.
         *
         * @param name the name of the field to be added
         * @param type the type of the field to be added
         * @throws IllegalArgumentException if the name or type is null
         * @return the DatabaseBuilder instance with the added field
         */
        public DatabaseBuilder addField(String name, ColumnType type) {
            if (name == null || type == null) {
                throw new IllegalArgumentException("Field name and type must not be null");
            }
            fields.put(name, type);
            return this;
        }

        /**
         * Builds and returns a Database object using the specified name and fields.
         *
         * @return The newly created Database object.
         */
        public Database build() {
            return new Database(name, fields);
        }
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
