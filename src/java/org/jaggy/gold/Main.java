package org.jaggy.gold;

import org.bstats.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jaggy.gold.cmds.GoldShop;
import org.jaggy.gold.gui.GuiManager;
import org.jaggy.gold.util.Logging;

public class Main extends JavaPlugin {
    /**
     * Pointer to the Log handling class
     */
    public Logging log;

    /**
     * Pointer to Bukkit's plugin manager
     */
    public PluginManager manager;
    /**
     * Pointer to the config class
     */
    public Config config;
    /**
     * Pointer to the DB class
     */
    public DB db;
    /**
     * A boolean to use later if no connection to the db is made.
     */
    public boolean isLoaded = false;
    /**
     * Container for gui manager
     */
    public GuiManager gui;

    /**
     * Load stuff we need done before we enable
     */
    public void onLoad() {
        manager = getServer().getPluginManager();
        log = new Logging();
        config = new Config(this);
        db = new DB(this);

    }

    /**
     * Start up the plugin
     */
    public void onEnable() {
        Metrics metrics = new Metrics(this);
        //Register Event Listeners
        if(manager.isPluginEnabled("Votifier")) {
            manager.registerEvents(new VoteEvent(this), this);
            log.info("Using NuVotifier hook.");
        }

        //Load GUI Manager
        gui = new GuiManager(this);
        //register goldshop command
        this.getCommand("goldshop").setExecutor(new GoldShop(this));
    }
}
