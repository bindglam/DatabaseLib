package io.github.bindglam.database;

import com.zaxxer.hikari.HikariConfig;

import java.io.File;

public final class H2Database extends SQLDatabase {
    public H2Database(File file, boolean autoCommit, int validTimeout) {
        super("jdbc:h2:" + file.getAbsolutePath(), autoCommit, validTimeout, 1);
    }

    @Override
    protected void loadDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void editHikariConfig(HikariConfig hikariConfig) {
    }
}
