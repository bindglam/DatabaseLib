package com.bindglam.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLiteDatabase implements Database<Connection, SQLException> {
    private final File file;
    private final boolean autoCommit;
    private final int validTimeout;

    private Connection connection;

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

        connect();
    }

    @Override
    public void stop() {
        disconnect();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    private void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to disconnect to database", e);
        }
    }

    private Connection ensureConnection() {
        try {
            if(this.connection == null || this.connection.isClosed() || !this.connection.isValid(validTimeout)) {
                this.disconnect();
                this.connect();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to ensure connection to database", e);
        }

        return this.connection;
    }

    @Override
    public synchronized void getResource(ResourceConsumer<Connection, SQLException> consumer) {
        try {
            consumer.accept(ensureConnection());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to proceed database connection", e);
        }
    }
}
