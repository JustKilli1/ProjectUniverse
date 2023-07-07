package net.projectuniverse.general.database;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a database.
 */

public record Database(String name, List<Column> fields) {

    /**
     * Returns the database creation query for creating a table with the given name and fields.
     * The query is in the form of a SQL statement.
     * If the table already exists, the "IF NOT EXISTS" clause is used to avoid errors.
     *
     * @return the database creation query as a string
     */
    public String getDatabaseCreationQuery() {
    String fieldsJoined = fields.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", "));
    return String.format("CREATE TABLE IF NOT EXISTS %s (%s);", name, fieldsJoined);
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
         * Represents a list of columns.
         * The list contains Column objects which represent individual fields.
         */
        private List<Column> fields;

        /**
         * Constructs a new DatabaseBuilder object with the specified name and fields.
         *
         * @param name   the name of the database (must not be null)
         * @param fields a list of column objects representing the fields of the database (must not be null)
         * @throws IllegalArgumentException if name or fields parameter is null
         */
        public DatabaseBuilder(String name, List<Column> fields) {
            if (name == null || fields == null) {
                throw new IllegalArgumentException("Name and fields must not be null");
            }
            this.name = name;
            this.fields = new ArrayList<>(fields);
        }

        /**
         * Constructs a new DatabaseBuilder with the given name and an empty field configuration.
         *
         * @param name the name of the database
         */
        public DatabaseBuilder(String name) {
            this(name, new ArrayList<>());
        }

        /**
         * Adds a field to the database builder.
         *
         * @param column the column representing the field to be added
         * @throws IllegalArgumentException if the column is null
         * @return the DatabaseBuilder instance with the added field
         */
        public DatabaseBuilder addField(Column column) {
            if (column == null) throw new IllegalArgumentException("Field name and type must not be null");
            fields.add(column);
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

    /**
     * The DBColumn class represents a column in a database table.
     * Each DBColumn object has a name, type, primary key indicator, auto-increment indicator, and not null indicator.
     */

    public record Column(String name, Database.ColumnType type, boolean isPrimaryKey, boolean isAutoIncrement, boolean isNotNull) {

        /**
         * Returns a string representation of the object.
         * The returned string contains the name and type of the object followed by any additional properties.
         * If the object is a primary key, " PRIMARY KEY" is appended to the string.
         * If the object has an autoincrement property, " AUTOINCREMENT" is appended.
         * If the object has a not null property, " NOT NULL" is appended.
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append(name)
              .append(' ')
              .append(type);

            if (isPrimaryKey)
                builder.append(" PRIMARY KEY");

            if (isAutoIncrement)
                builder.append(" AUTO_INCREMENT");

            if (isNotNull)
                builder.append(" NOT NULL");

            return builder.toString();
        }
    }

}
