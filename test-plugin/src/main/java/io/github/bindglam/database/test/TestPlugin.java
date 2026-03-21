package io.github.bindglam.database.test;

import io.github.bindglam.database.H2Database;
import io.github.bindglam.database.SQLDatabase;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TestPlugin extends JavaPlugin {
    private SQLDatabase sqlDatabase;

    @Override
    public void onEnable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();

        sqlDatabase = new H2Database(new File(getDataFolder(), "database.db"), true, 5000);
        sqlDatabase.start();
    }

    @Override
    public void onDisable() {
        sqlDatabase.stop();
    }
}
