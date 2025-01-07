package com.github.deroq1337.partygames.core.data.game.database;

import com.github.deroq1337.partygames.api.database.DatabaseManager;
import com.github.deroq1337.partygames.core.PartyGames;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * DefaultSQLManager is responsible for managing database connections and executing SQL queries using HikariCP.
 * It handles establishing a connection to a MySQL database, executing asynchronous queries, and creating necessary database tables.
 */
public class DefaultDatabaseManager implements DatabaseManager {

    private final @NotNull PartyGames partyGames;
    private final HikariDataSource hikariDataSource;

    /**
     * Constructor for initializing the DefaultSQLManager and establishing a connection to the MySQL database.
     * It reads the configuration details from the provided YAML file and configures the HikariCP DataSource.
     *
     * @param partyGames The main plugin instance
     * @param configFile The YAML file containing database configuration settings
     * @throws IllegalArgumentException If the config file does not exist or cannot be read
     */
    public DefaultDatabaseManager(@NotNull PartyGames partyGames, @NotNull File configFile) {
        this.partyGames = partyGames;

        if (!configFile.exists()) {
            throw new IllegalArgumentException("Configuration file does not exist: " + configFile.getAbsolutePath());
        }

        YamlConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
        HikariConfig hikariConfig = new HikariConfig();

        // Set the JDBC URL, username, password, and pool settings based on the configuration
        hikariConfig.setJdbcUrl("jdbc:mysql://" + fileConfiguration.getString("mysql.host") + ":" +
                fileConfiguration.getInt("mysql.port") + "/" + fileConfiguration.getString("mysql.database"));
        hikariConfig.setUsername(fileConfiguration.getString("mysql.username"));
        hikariConfig.setPassword(fileConfiguration.getString("mysql.password"));

        // Set connection pool properties from the configuration
        hikariConfig.setMaximumPoolSize(fileConfiguration.getInt("mysql.pool.maximumPoolSize", 10));
        hikariConfig.setMinimumIdle(fileConfiguration.getInt("mysql.pool.minimumIdle", 2));
        hikariConfig.setIdleTimeout(fileConfiguration.getLong("mysql.pool.idleTimeout", 10000));
        hikariConfig.setMaxLifetime(fileConfiguration.getLong("mysql.pool.maxLifetime", 1800000));

        // Initialize the HikariDataSource with the configuration
        this.hikariDataSource = new HikariDataSource(hikariConfig);

        // Log successful database connection
        this.partyGames.getLogger().info("Successfully connected to the database.");
    }

    /**
     * Executes an SQL query asynchronously, making sure it doesn't block the main thread.
     *
     * @param query The SQL query to execute
     * @return A CompletableFuture representing the asynchronous operation
     */
    @Override
    public CompletableFuture<Void> executeAsync(@NotNull String query) {
        return CompletableFuture.runAsync(() -> {
            try (Connection connection = getConnection()) {
                connection.createStatement().execute(query);
            } catch (SQLException e) {
                this.partyGames.getLogger().severe("Error executing query: " + query);
            }
        });
    }

    /**
     * Retrieves a connection from the HikariDataSource.
     *
     * @return A database connection
     * @throws SQLException If a connection cannot be established
     */
    @Override
    public Connection getConnection() throws SQLException {
        return this.hikariDataSource.getConnection();
    }

    /**
     * Closes the HikariDataSource, releasing all database connections.
     */
    @Override
    public void close() {
        if (this.hikariDataSource != null && !this.hikariDataSource.isClosed()) {
            this.hikariDataSource.close();
        }
    }

    /**
     * Creates a table in the database if it does not already exist.
     * This method executes an asynchronous query to create the table with the specified schema.
     *
     * @param tableName   The name of the table to create
     * @param tableSchema The schema definition of the table
     */
    @Override
    public void createTable(@NotNull String tableName, @NotNull String tableSchema) {
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");";

        executeAsync(query).thenRun(() ->
                this.partyGames.getLogger().info("Table '" + tableName + "' has been created or already exists.")
        ).exceptionally(e -> {
            this.partyGames.getLogger().severe("Error creating table: " + e.getMessage());
            return null;
        });
    }
}