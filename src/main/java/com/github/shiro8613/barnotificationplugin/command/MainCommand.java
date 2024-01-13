package com.github.shiro8613.barnotificationplugin.command;

import com.github.shiro8613.barnotificationplugin.logic.ActionBar;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MainCommand implements CommandExecutor,TabCompleter {

    private final JavaPlugin plugin;

    public MainCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("barnotificationplugin"))
        {
            sender.sendMessage(ChatColor.RED + "You don't have permission");
            return true;
        }

        if (args.length < 1)
        {
            sender.sendMessage(ChatColor.RED + "Wrong command");
            return false;
        }

        switch (args[0]) {
            case "actionbar":
            case "bossbar":
                return CommandLogic.onCommand(sender, args, plugin);

            case "reload":
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Wrong command");
                return false;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String label, @NotNull String[] args) {

        if(!sender.hasPermission("barnotificationplugin")) return new ArrayList<>();
        if(args.length < 1) return new ArrayList<>();

        List<String> complete = new ArrayList<>();

        switch (args.length) {
            case 1:
                StringUtil.copyPartialMatches(args[0], Arrays.asList("actionbar", "bossbar", "reload"), complete);
                break;

            case 2:
                switch (args[0]) {
                    case "actionbar":
                    case "bossbar":
                        StringUtil.copyPartialMatches(args[1], Arrays.asList("add", "edit", "list", "remove", "config"), complete);
                        break;

                    case "reload":
                        ActionBar.ReloadConfig();
                        break;

                    default:
                        break;
                }
                break;
            case 3:
                switch (args[1]) {
                    case "add":
                        replaceListItem(complete, "<message>");
                        break;

                    case"edit":
                        replaceListItem(complete,"<index>");
                        break;

                    case "remove":
                        replaceListItem(complete, "<index>");
                        complete.add("<index>");
                        break;

                    case "config":
                        StringUtil.copyPartialMatches(args[2], Arrays.asList("color","time"), complete);
                        break;

                    default:
                        break;
                }
                break;

            case 4:
                switch (args[1]) {
                    case "edit":
                        replaceListItem(complete, "<message>");
                        break;

                    case "config":
                        switch (args[2]) {
                            case "color":
                                StringUtil.copyPartialMatches(args[3], Collections.singletonList("<hex>"), complete);
                                break;

                            case "time":
                                StringUtil.copyPartialMatches(args[3], Collections.singletonList("<seconds>"), complete);
                                break;
                        }
                        break;
                }
                break;

            default:
                complete = new ArrayList<>();
                break;
        }

        return complete;

    }

    private static void replaceListItem(Collection<String> list, String text) {
        list.retainAll(new ArrayList<String>());
        list.add(text);
    }
}
