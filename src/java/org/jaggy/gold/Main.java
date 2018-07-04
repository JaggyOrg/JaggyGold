package org.jaggy.gold;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jaggy.gold.util.Logging;

public class Main extends JavaPlugin {
    /**
     * Pointer to the Log handling class
     */
    public Logging log;

    /**
     * Pointer to Bukkit's plugin manager
     */
    private PluginManager manager;
    public Config config;
    public DB db;
    public boolean isLoaded = false;

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
        //Register Event Listeners
        if(manager.isPluginEnabled("Votifier")) {
            manager.registerEvents(new VoteEvent(this), this);
            log.info("Using NuVotifier hook.");
        }
    }
}
