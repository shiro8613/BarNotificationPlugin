package com.github.shiro8613.barnotificationplugin;

import com.github.shiro8613.barnotificationplugin.command.MainCommand;
import com.github.shiro8613.barnotificationplugin.logic.ActionBar;
import com.github.shiro8613.barnotificationplugin.logic.BossBarLogic;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BarNotificationPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        //Config
        saveDefaultConfig();

        //Logic
        ActionBar.Handle(this);
        BossBarLogic.Handle(this);

        //command
        String pluginCommand = "barnotification";
        Objects.requireNonNull(Bukkit.getPluginCommand(pluginCommand))
                .setExecutor(new MainCommand(this));
        Objects.requireNonNull(Bukkit.getPluginCommand(pluginCommand))
                .setTabCompleter(new MainCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
