package org.jaggy.gold.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jaggy.gold.Main;
import org.jaggy.gold.cmds.GoldShop;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;

public class GuiManager {
    private final Main plugin;
    /**
     * Container for the menus
     */
    private JSONObject menus;

    public GuiManager(Main main) {
        plugin = main;
        plugin.manager.registerEvents(new GUIListener(), plugin);
        loadJSON();
    }

    /**
     * Displays Main menu
     */
    public void displayMainMenu() {
        JSONArray array = (JSONArray) menus.get("menus");
        Iterator it = array.iterator();

        Inventory inventory = Bukkit.createInventory(null, 54, "Gold Shop");
        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();
            String name = (String) item.get("name");
            String icon = (String) item.get("icon");
            String location = (String) item.get("location");
            String type = (String) item.get("type");
            String label = (String) item.get("label");
            inventory.addItem(createGuiItem(name,new ArrayList<String>(Arrays.asList(label)),Material.getMaterial(icon)));
        }
    }

    /**
     * Nice little method to create a gui item with a custom name, and description
     */
    public ItemStack createGuiItem(String name, ArrayList<String> desc, Material mat) {
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        iMeta.setLore(desc);
        i.setItemMeta(iMeta);
        return i;
    }

    /**
     * Loads the menus into memory
     */
    public void loadJSON() {
        JSONParser parser = new JSONParser();
        try {
            File File = new File(plugin.getDataFolder(), "menus.json");

            if (File.exists()) { // menus.json exists? use it
                menus = (JSONObject) parser.parse(new FileReader(File));
            } else { // neither exists yet (new installation), create and use it
                plugin.saveResource("menus.json", false);
                menus = (JSONObject) parser.parse(new FileReader(File));
            }
        } catch (ParseException | IOException e) {
            plugin.log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
