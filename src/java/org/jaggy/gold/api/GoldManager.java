package org.jaggy.gold.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jaggy.gold.Config;
import org.jaggy.gold.DB;
import org.jaggy.gold.Main;
import org.jaggy.gold.util.Logging;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Jaggy Gold API Manager
 */
public class GoldManager {
    private final DB db;
    private final Config config;
    private final Logging logger;
    private final Main plugin;

    /**
     * API main class constructor
     */
    public GoldManager() {
        plugin = (Main) Bukkit.getServer().getPluginManager().getPlugin("JaggyGold");
        db = new DB(plugin);
        config = new Config(plugin);
        logger = new Logging();
    }

    /**
     * Adds to player's balance.
     * @param player The player we are doing an action on.
     * @param amount The amount we are adding to the player's balance
     */
    public void addGold(Player player, long amount) {
        long balance = getBalance(player);
        long newbal = balance + amount;
        db.query("UPDATE "+config.getPrefix()+"Gold SET Gold = '"+newbal+"' WHERE Player = '"+player.getName()+"'");
    }

    public void addGold(String player, long amount) {
        long balance = getBalance(player);
        long newbal = balance + amount;
        db.query("UPDATE "+config.getPrefix()+"Gold SET Gold = '"+newbal+"' WHERE Player = '"+player+"'");
    }

    /**
     * Subtracts from player's balance.
     * @param player The player we are doing an action on.
     * @param amount The amount we will subtract from player.
     */
    public void subGold(Player player, long amount) {
        long balance = getBalance(player);
        long newbal = balance - amount;
        db.query("UPDATE "+config.getPrefix()+"Gold SET Gold = '"+newbal+"' WHERE Player = '"+player.getName()+"'");
    }

    public void subGold(String player, long amount) {
        long balance = getBalance(player);
        long newbal = balance - amount;
        db.query("UPDATE "+config.getPrefix()+"Gold SET Gold = '"+newbal+"' WHERE Player = '"+player+"'");
    }

    /**
     * Gets a player's balance
     * @param player The player we are doing an action on.
     * @return double
     */
    public long getBalance(Player player) {
        this.addPlayer(player);
        ResultSet result = db.query("SELECT Gold FROM " + config.getPrefix() + "Gold WHERE Player = '" + player.getName() + "'");
        try {
            if (result.first()) {
                return result.getLong(1);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
        return 0;
    }

    public long getBalance(String player) {
        this.addPlayer(player);
        ResultSet result = db.query("SELECT Gold FROM " + config.getPrefix() + "Gold WHERE Player = '" + player + "'");
        try {
            if (result.first()) {
                return result.getLong(1);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
        return 0;
    }
    /**
     * Adds player to Gold table
      * @param player
     */
    public void addPlayer(Player player) {
        ResultSet result = db.query("SELECT COUNT(*) FROM " + config.getPrefix() + "Gold WHERE Player = '" + player.getName() + "'");
        try {
            result.first();
            if (result.getInt(1) == 0) {
                db.query("INSERT INTO "+config.getPrefix()+"Gold (Player) VALUES ('"+player.getName()+"')");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    /**
     * Adds player to gold table
     * @param player
     */
    public void addPlayer(String player) {
        ResultSet result = db.query("SELECT COUNT(*) FROM " + config.getPrefix() + "Gold WHERE Player = '" + player + "'");
        try {
            result.first();
            if (result.getInt(1) == 0) {
                db.query("INSERT INTO "+config.getPrefix()+"Gold (Player) VALUES ('"+player+"')");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

    }
}
