package io.github.iwologs;
import io.github.iwologs.commands.Hello;
import io.github.iwologs.listeners.JoinListener;
import io.github.iwologs.loggers.PickupLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Iwo_logs extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("siema")).setExecutor(new Hello());
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new PickupLogger(), this);
//        getServer().getPluginManager().registerEvents(new ChestListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
