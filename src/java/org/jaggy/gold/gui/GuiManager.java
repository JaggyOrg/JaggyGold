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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class GuiManager {
    private final Main plugin;
    /**
     * Container for the menus
     */
    public JSONObject menus;
    public Inventory inventory;

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

        inventory = Bukkit.createInventory(null, 54, "Gold Shop");
        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();
            String name = (String) item.get("name");
            String icon = (String) item.get("icon");
            String location = (String) item.get("location");
            String type = (String) item.get("type");
            String label = (String) item.get("label");
            Material mat = Material.matchMaterial(icon);
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            try {
                inventory.setItem((Integer) engine.eval(location), createGuiItem(name, label, mat));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        player.openInventory(inventory);
    }

    /**
     * Nice little method to create a gui item with a custom name, and description
     */
    public ItemStack createGuiItem(String name, String desc, Material mat) {
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        ArrayList<String> Lore = new ArrayList<String>();
        Lore.add(desc);
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
        JSONArray array = (JSONArray) menus.get("menus");
        Iterator it = array.iterator();
        String type;

        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();
            String name = (String) item.get("name");
            if (menu.equals(name)) {
                type = (String) item.get("type");
                if (type.equals("menu")) {
                    JSONArray items = (JSONArray) item.get("items");
                    plugin.log.info(items.toString());
                    //subMenu(player, items);
                } else {
                    action(item);
                }
                return;
            }

        }
    }

    private void subMenu(Player player, JSONArray items) { ;
        Iterator it = items.iterator();

        inventory = Bukkit.createInventory(null, 54, "Gold Shop");
        while (it.hasNext()) {
            JSONObject item = (JSONObject) it.next();
            String name = (String) item.get("name");
            String icon = (String) item.get("icon");
            String location = (String) item.get("location");
            String type = (String) item.get("type");
            String label = (String) item.get("label");
            plugin.log.info(label);
            Material mat = Material.matchMaterial(icon);
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            try {
                inventory.setItem((Integer) engine.eval(location), createGuiItem(name, label, mat));
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
        player.openInventory(inventory);

    }

    /**
     * Process item requests.
     * @param item
     */
    private void action(JSONObject item) {

    }
}
