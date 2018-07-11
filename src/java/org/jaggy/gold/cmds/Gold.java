package org.jaggy.gold.cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jaggy.gold.Main;

public class Gold implements CommandExecutor {
    private final Main plugin;

    public Gold(Main main) {
        plugin = main;
    }
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
    if (sender.hasPermission("jaggygold.admin")) {
        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length == 3) {
                    plugin.api.addGold(args[1], Long.parseLong(args[2]));
                    plugin.sendMessage(sender, ChatColor.GOLD + args[2] + " was added to " + args[1] + " account");
                } else {
                    plugin.sendMessage(sender, ChatColor.GOLD + "Usage: /gold add <player> <amount>");
                }
                break;
            case "sub":
                if (args.length == 3) {
                    plugin.api.subGold(args[1], Long.parseLong(args[2]));
                    plugin.sendMessage(sender, ChatColor.GOLD + args[2] + " was subtracted from " + args[1] + "'s account.");
                } else {
                    plugin.sendMessage(sender, ChatColor.GOLD + "Usage: /gold sub <player> <amount>");
                }
                break;
            case "reload":
                plugin.config.load();
                plugin.db.reload();
                plugin.gui.loadJSON();
                plugin.sendMessage(sender, ChatColor.GOLD + "Config is reloaded.");
                break;
        }
    }
        return true;
    }
}
