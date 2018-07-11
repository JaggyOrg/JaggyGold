package org.jaggy.gold.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jaggy.gold.Main;

public class GoldShop implements CommandExecutor {
    private final Main plugin;

    public GoldShop(Main main) {
        plugin = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            plugin.gui.displayMainMenu(((Player) sender).getPlayer());
        }
        return true;
    }
}
