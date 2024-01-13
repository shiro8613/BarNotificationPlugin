package com.github.shiro8613.barnotificationplugin.command;

import com.github.shiro8613.barnotificationplugin.Utils;
import com.github.shiro8613.barnotificationplugin.logic.ActionBar;
import com.github.shiro8613.barnotificationplugin.logic.BossBarLogic;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandLogic {
    public static boolean onCommand(CommandSender sender, String[] args, JavaPlugin plugin) {

        Configuration configuration = plugin.getConfig();
        String path = args[0] + ".database";
        List<String> list = configuration.getStringList(path);

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Wrong Command");
            return false;
        }

        switch (args[1]) {
            case "add":
                if(args[2] == null) {
                    sender.sendMessage(ChatColor.RED + "text is Empty");
                    return true;
                }

                list.add(args[2]);

                configuration.set(path, list);
                plugin.saveConfig();
                ActionBar.ReloadConfig();
                BossBarLogic.ReloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Add Successful");
                return true;

            case "remove":
                try {
                    int index = Integer.parseInt(args[2]);

                    if (index > list.size() || list.isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "not Index");
                        return true;
                    }


                    list.remove(index);

                    configuration.set(path, list);
                    plugin.saveConfig();
                    ActionBar.ReloadConfig();
                    BossBarLogic.ReloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Remove Successful");
                    return true;
                } catch(NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Index is number");
                    return true;
                }

            case "edit":
                if(args.length < 4) {
                    sender.sendMessage(ChatColor.RED + "Not enough arguments");
                    return true;
                }

                try {
                    int i = Integer.parseInt(args[2]);

                    if(i > list.size() || list.isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "not Index");
                        return true;
                    }
                    String str = list.get(i);

                    if(str == null) {
                        sender.sendMessage(ChatColor.RED + "not Index");
                        return true;
                    }

                    if(args[3] == null || args[3].isEmpty()) {
                        sender.sendMessage(ChatColor.RED + "text is Empty");
                        return true;
                    }

                    list.set(i, args[3]);

                    configuration.set(path, list);
                    plugin.saveConfig();
                    ActionBar.ReloadConfig();
                    BossBarLogic.ReloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Edit Successful");
                    return true;
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Index is number");
                    return true;
                }

            case "list":

                sender.sendMessage("------------List------------");

                if (list.isEmpty()) {
                    sender.sendMessage("No Data");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        sender.sendMessage(ChatColor.AQUA + "[" + i + "]"
                                + ChatColor.WHITE + " " + list.get(i));
                    }
                }
                sender.sendMessage("----------------------------");

                return true;

            case "config":
                if(args.length < 4) {
                    sender.sendMessage("------------Configure------------");
                    sender.sendMessage("time: " + configuration.getString(args[0] + ".time"));
                    sender.sendMessage("color: " + configuration.getString(args[0] + ".color"));
                    sender.sendMessage("----------------------------");
                    return true;
                }

                switch (args[2]) {
                    case "time":
                        try {
                            int i = Integer.parseInt(args[3]);
                            configuration.set(args[0] + ".time", i);
                            plugin.saveConfig();
                            ActionBar.ReloadConfig();
                            BossBarLogic.ReloadConfig();
                            sender.sendMessage(ChatColor.GREEN + "Set time " + i + "s");
                            return true;
                        } catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + "Please enter a number");
                            return true;
                        }

                    case "color":
                        if(!Utils.isHexColor(args[3])) {
                            sender.sendMessage(ChatColor.RED + "Not compete color");
                            return true;
                        }

                        configuration.set(args[0] + ".color", args[3]);
                        plugin.saveConfig();
                        ActionBar.ReloadConfig();
                        BossBarLogic.ReloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "Set color " + args[3]);
                        return true;
                }
            default:
                sender.sendMessage(ChatColor.RED + "Wrong command");
                return false;
        }
    }
}
