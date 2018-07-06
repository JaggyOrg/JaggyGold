package org.jaggy.gold.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jaggy.gold.Main;

public class GoldShop implements CommandExecutor {
    private final Main plugin;

    public GoldShop(Main main) {
        plugin = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        plugin.gui.displayMainMenu();
        return true;
    }
}
