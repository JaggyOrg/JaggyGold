package org.jaggy.gold;

import org.bukkit.configuration.file.FileConfiguration;
import org.jaggy.gold.api.GoldManager;

import java.io.File;

public class Config {
    /**
     * Reference pointer to parent class
     */
    private Main plugin;
    /**
     * Container for the config.yml
     */
    private FileConfiguration config;
    /**
     * Sets the default Mysql Host.
     */
    private String defaultMysqlHost = "localhost";
    /**
     * Sets the default Mysql Username.
     */
    private String defaultMysqlUser = "root";
    /**
     * Sets the default Mysql Port.
     */
    private String defaultMysqlPass = "";
    /**
     * Set the default Mysql port
     */
    private int defaultMysqlPort = 3306;
    /**
     * Sets the default Database name
     */
    private String defaultDBName = "minecraft";
    /**
     * Sets the default Table Prefix
     */
    private String defaultDBPrefix = "jaggygold_";
    /**
     * Set the default for SSL use
     */
    private boolean defaultSSL = false;
    /**
     * Set the default gold to give for Votifier
     */
    private double defaultVotifier = 0.5;


    /**
     * Class Constructor
     * @param main Passes the parent class to this class
     */
    public Config(Main main) {
        plugin = main;

        //load config.yml
        File File = new File(plugin.getDataFolder(), "JaggedGold/config.yml");
        if (File.exists()) { // config.yml exists? use it
            config = plugin.getConfig();
        } else { // neither exists yet (new installation), create and use it
            plugin.saveDefaultConfig();
            config = plugin.getConfig();
        }

    }

    /**
     * Access this class for GoldManager
     */
    public Config() {

    }


    /**
     * Gets the database name.
     *
     * @return String
     */
    public String getDBName() {
        return config.getString("DatabaseName", defaultDBName);
    }

    /**
     * Gets the Mysql host.
     *
     * @return String
     */
    public String getMysqlHost() {
        return config.getString("MysqlHost", defaultMysqlHost);
    }

    /**
     * Gets the Mysql username.
     *
     * @return String
     */
    public String getMysqlUser() {
        return config.getString("MysqlUser", defaultMysqlUser);
    }

    /**
     * Gets the Mysql password.
     *
     * @return String
     */
    public String getMysqlPass() {
        return config.getString("MysqlPass", defaultMysqlPass);
    }
    /**
     * Gets the Mysql port.
     *
     * @return String
     */
    public int getMysqlPort() {
        return config.getInt("MysqlPort", defaultMysqlPort);
    }

    /**
     * Gets the table prefix
     *
     * @return String
     */
    public String getPrefix() {
        return config.getString("DBPrefix", defaultDBPrefix);
    }

    /**
     * Gets the Votifier gold setting
     *
     * @return String
     */
    public Double getVotifier() {
        return config.getDouble("Votifier", defaultVotifier);
    }

    /**
     * Gets if ssl is required
     *
     * @return String
     */
    public boolean useSSL() {
        return config.getBoolean("DBPrefix", defaultSSL);
    }


}
