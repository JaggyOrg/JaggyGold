package org.jaggy.gold.api;

import org.jaggy.gold.Config;
import org.jaggy.gold.DB;
import org.jaggy.gold.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Jaggy Gold API Manager
 */
public class GoldManager {
    private final DB db;
    private final Config config;
    private final Main plugin;

    /**
     * API main class constructor
     */
    public GoldManager() {
        plugin = new Main();
        db = new DB();
        config = new Config();
    }

    /**
     * Adds to player's balance.
     * @param player The player we are doing an action on.
     * @param amount The amount we are adding to the player's balance
     */
    public void addGold(String player, Double amount) {
        double balance = getBalance(player);
        double newbal = balance + amount;
        db.query("UPDATE "+config.getPrefix()+"Gold SET Gold = '"+newbal+"' WHERE Player = '"+player+"'");
    }

    /**
     * Subtracts from player's balance.
     * @param player The player we are doing an action on.
     * @param amount The amount we will subtract from player.
     */
    public void subGold(String player, int amount) {
        double balance = getBalance(player);
        double newbal = balance - amount;
        db.query("UPDATE "+config.getPrefix()+"Gold SET Gold = '"+newbal+"' WHERE Player = '"+player+"'");
    }

    /**
     * Gets a player's balance
     * @param player The player we are doing an action on.
     * @return double
     */
    public double getBalance(String player) {
        ResultSet result = db.query("SELECT Gold FROM " + config.getPrefix() + "Gold WHERE Player = '" + player + "'");
        try {
            if (result.first()) {
                return result.getDouble(1);
            }
        } catch (SQLException e) {
            plugin.log.severe(e.getMessage());
        }
        return 0;
    }
}
