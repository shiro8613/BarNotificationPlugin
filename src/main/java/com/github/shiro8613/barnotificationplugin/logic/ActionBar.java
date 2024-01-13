package com.github.shiro8613.barnotificationplugin.logic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActionBar {
    private static JavaPlugin plugin;
    private static List<String> list = new ArrayList<>();
    private static String color = "#ffffff";
    private static Integer tick = 0;
    private static Integer time = 0;
    private static Integer counter = 0;

    public static void ReloadConfig() {
        loadConfig();
    }

    public static void Handle(JavaPlugin pl) {
        plugin = pl;
        loadConfig();
        handle();
    }

    private static void loadConfig() {
        FileConfiguration fileConfiguration = plugin.getConfig();
        list = fileConfiguration.getStringList("actionbar.database");
        color = fileConfiguration.getString("actionbar.color");
        time = fileConfiguration.getInt("actionbar.time") * 20;
    }

    private static void handle() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if(Objects.equals(tick, time)) {
                tick = 0;
                counter++;
            }
            if(list.isEmpty()) return;
            if(counter >= list.size()) {
                counter = 0;
            }

            plugin.getServer().sendActionBar(Component.text(list.get(counter))
                    .color(TextColor.fromCSSHexString(color))
                    .decorate(TextDecoration.BOLD));

            tick++;
        }, 0L, 1);
    }
}
