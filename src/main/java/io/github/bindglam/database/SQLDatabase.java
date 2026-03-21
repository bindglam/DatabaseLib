package io.github.bindglam.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLDatabase implements Database<Connection, SQLException> {
    private final String url;
    private final boolean autoCommit;
    private final int validTimeout;
    private final int maxPoolSize;

    protected HikariDataSource dataSource;

    public SQLDatabase(String url, boolean autoCommit, int validTimeout, int maxPoolSize) {
        this.url = url;
        this.autoCommit = autoCommit;
        this.validTimeout = validTimeout;
        this.maxPoolSize = maxPoolSize;
    }

    protected abstract void loadDriver();

    protected abstract void editHikariConfig(HikariConfig hikariConfig);

    @Override
    public void start() {
        loadDriver();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setAutoCommit(autoCommit);
        hikariConfig.setValidationTimeout(validTimeout);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        editHikariConfig(hikariConfig);

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
