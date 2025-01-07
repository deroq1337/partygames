package com.github.deroq1337.partygames.api.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public interface DatabaseManager {

    /**
     * Executes an SQL query asynchronously, ensuring it doesn't block the main thread.
     *
     * @param query The SQL query to execute.
     * @return A CompletableFuture representing the asynchronous operation.
     */
    CompletableFuture<Void> executeAsync(String query);

    /**
     * Retrieves a connection from the underlying data source.
     *
     * @return A database connection.
     * @throws SQLException If a connection cannot be established.
     */
    Connection getConnection() throws SQLException;

    /**
     * Closes the underlying data source, releasing all database connections.
     */
    void close();

    /**
     * Creates a table in the database if it does not already exist.
     *
     * @param tableName   The name of the table to create.
     * @param tableSchema The schema definition of the table.
     */
    void createTable(String tableName, String tableSchema);
}
