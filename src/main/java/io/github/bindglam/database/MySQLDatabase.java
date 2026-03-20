package io.github.bindglam.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class MySQLDatabase implements Database<Connection, SQLException> {
    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final int maxPoolSize;

    private HikariDataSource dataSource;

    public MySQLDatabase(String host, String database, String username, String password, int maxPoolSize) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
    }

    @Override
    public void start() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + "/" + database);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(maxPoolSize);

        this.dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public void stop() {
        this.dataSource.close();
    }

    @Override
    public void getResource(ResourceConsumer<Connection, SQLException> consumer) {
        try(Connection connection = dataSource.getConnection()) {
            consumer.accept(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to proceed database connection", e);
        }
    }
}