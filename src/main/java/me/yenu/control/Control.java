package me.yenu.control;

import me.yenu.control.Spawn.Click;
import me.yenu.control.Spawn.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Control extends JavaPlugin {

    public static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getCommandMap().register("test", new Spawn("start"));
        getServer().getPluginManager().registerEvents(new Click(),this);
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Control getInstance() {
        return (Control) instance;
    }

}
