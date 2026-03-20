package io.github.bindglam.database.test;

import com.bindglam.database.Database;
import com.bindglam.database.SQLiteDatabase;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class TestPlugin extends JavaPlugin {
    private Database<Connection, SQLException> sqlDatabase;

    @Override
    public void onEnable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();

        sqlDatabase = new SQLiteDatabase(new File(getDataFolder(), "database.db"), true, 5000);
        sqlDatabase.start();
    }

    @Override
    public void onDisable() {
        sqlDatabase.stop();
    }
}
