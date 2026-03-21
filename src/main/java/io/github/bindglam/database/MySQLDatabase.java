package io.github.bindglam.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class MySQLDatabase extends SQLDatabase {
    private final String username;
    private final String password;

    public MySQLDatabase(String host, String database, String username, String password, boolean autoCommit, int validTimeout, int maxPoolSize) {
        super("jdbc:mysql://" + host + "/" + database, autoCommit, validTimeout, maxPoolSize);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void loadDriver() {
    }

    @Override
    protected void editHikariConfig(HikariConfig hikariConfig) {
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
    }
}