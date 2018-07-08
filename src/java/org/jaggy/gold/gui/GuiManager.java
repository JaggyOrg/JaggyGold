package org.jaggy.gold.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jaggy.gold.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;

public class GuiManager {
    private Main plugin;
    /**
     * Container for the menus
     */
    public JSONObject menus;
    /**
     * Container to store generated inventory
     */
    public Inventory inventory;

    /**
     * Item registry
     */
    public HashMap registry;

    public GuiManager(Main main) {
        plugin = main;
        plugin.manager.registerEvents(new GUIListener(this), plugin);
        loadJSON();
    }

    /**
     * Displays Main menu
     */
    public void displayMainMenu(Player player) {
        JSONArray array = (JSONArray) menus.get("menus");
        Iterator it = array.iterator();
        registry = new HashMap();
        inventory = Bukkit.createInventory(null, 54, "Gold Shop");
        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();
            //Create menu item
            MenuItem menuItem = new MenuItem((String) item.get("name"),
                    (String) item.get("icon"), (String) item.get("action"), (String) item.get("type"),
                    (String) item.get("description"), Material.matchMaterial((String) item.get("icon")));

            //Convert into integer and set the location
            menuItem.setLocation((String) item.get("location"));

            //Convert into array and store the object data.
            String enchants = (String) item.get("enchants");
            if (enchants != null) {
                menuItem.setEnchantments((String) item.get("enchants"));
            }
            JSONArray items = (JSONArray) item.get("items");
            if (!items.isEmpty()) {
                menuItem.setItems(items);
            }
            registry.put(item.get("name"), menuItem);
            //Add item to inventory
            inventory.setItem(menuItem.location, createGuiItem(menuItem));
        }
        //Open inventory menu
        player.openInventory(inventory);
    }

    /**
     * Create a gui item with a custom name, and description
     */
    public ItemStack createGuiItem(MenuItem menuItem) {
        ItemStack i = new ItemStack(menuItem.material, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(menuItem.name);
        ArrayList<String> Lore = new ArrayList<String>();
        Lore.add(menuItem.description);
        iMeta.setLore(Lore);
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

    /**
     * Parses menus object to see if the item is a menu or item. If it is an item
     * do the necessary actions.
     * @param menu
     */
    public void accessItem(Player player, String menu) {
        MenuItem item = (MenuItem) registry.get(menu);
        if (item.type.equals("menu")) {
            JSONArray items = item.items;
            subMenu(player, items);
        } else {
            action(item);
        }
    }

    private void subMenu(Player player, JSONArray items) {
        JSONObject back = new JSONObject();
        back.put("name", "Back");
        back.put("icon", "BARRIER");
        back.put("location", "0");
        back.put("description", "Back to main menu.");
        items.add(back);
        Iterator it = items.iterator();
        registry = new HashMap();
        Inventory subInv = Bukkit.createInventory(null, 54, "Gold Shop");
        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();
            //Create menu item
            MenuItem menuItem = new MenuItem((String) item.get("name"),
                    (String) item.get("icon"), (String) item.get("action"), (String) item.get("type"),
                    (String) item.get("description"), Material.matchMaterial((String) item.get("icon")));

            //Convert into integer and set the location
            menuItem.setLocation((String) item.get("location"));

            //Convert into array and store the object data.
            if (item.get("enchants") != null) {
                menuItem.setEnchantments((String) item.get("enchants"));
            }
            // Add item
            registry.put((String) item.get("name"), menuItem);
            subInv.setItem(menuItem.location, createGuiItem(menuItem));
        }
        player.openInventory(subInv);

    }

    /**
     * Process item requests.
     * @param item
     */
    private void action(MenuItem item) {

    }
}
