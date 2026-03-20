package com.bindglam.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public final class SQLiteDatabase implements Database<Connection, SQLException> {
    private final File file;
    private final boolean autoCommit;
    private final int validTimeout;

    private HikariDataSource dataSource;

    public SQLiteDatabase(File file, boolean autoCommit, int validTimeout) {
        this.file = file;
        this.autoCommit = autoCommit;
        this.validTimeout = validTimeout;
    }

    @Override
    public void start() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:sqlite://" + file.getPath());
        hikariConfig.setAutoCommit(autoCommit);
        hikariConfig.setValidationTimeout(validTimeout*1000L);
        hikariConfig.setMaximumPoolSize(1);

        this.dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public void stop() {
        this.dataSource.close();
    }

    @Override
    public synchronized void getResource(ResourceConsumer<Connection, SQLException> consumer) {
        try(Connection connection = dataSource.getConnection()) {
            consumer.accept(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to proceed database connection", e);
        }
    }
}
