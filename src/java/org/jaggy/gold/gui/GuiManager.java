package org.jaggy.gold.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jaggy.gold.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * GUI Manager Class
 */
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
            menuItem.setAmount((Long) item.get("gold"));
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

        //add enchantments to item
        for (String enchantment: menuItem.enchants) {
            String[] args = enchantment.split(":", 2);
            int level = Integer.parseInt(args[1]);
            String name = args[0];
            Enchantment enchant = this.getEnchant(name);
            iMeta.addEnchant(enchant, level, true);
        }

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
     * @param selected
     */
    public void accessItem(Player player, String selected) {
        MenuItem item = (MenuItem) registry.get(selected);
        String type = item.type.toLowerCase();

        if (type.equals("menu")) {
            JSONArray items = item.items;
            subMenu(player, items);
        } else {
            action(player, item);
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
            menuItem.setAmount((Long) item.get("gold"));
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
    private void action(Player player, MenuItem item) {
        long balance = plugin.api.getBalance(player);
        long cost = item.amount;
        long total = balance - cost;
        if (total < 0) {
            player.sendMessage(ChatColor.GOLD+"You do not have enough gold coins.");
        } else {
            plugin.api.subGold(player, cost);
            ItemStack spawner = createGuiItem(item);
            player.getInventory().addItem(spawner);
        }
    }


    /**
     * Converts String name into enchantment.
     * @param name Name of the enchantment
     * @return The enchantment we need to enchant item
     */
    public Enchantment getEnchant(String name) {
        Enchantment enchant = null;
        switch (name.toLowerCase()) {
            case "aqua_affinity": enchant = Enchantment.WATER_WORKER; break;
            case "bane_of_arthropods": enchant = Enchantment.DAMAGE_ARTHROPODS; break;
            case "blast_protection": enchant = Enchantment.PROTECTION_EXPLOSIONS; break;
            case "binding_curse": enchant = Enchantment.BINDING_CURSE; break;
            case "vanishing_curse": enchant = Enchantment.VANISHING_CURSE; break;
            case "depth_strider": enchant = Enchantment.DEPTH_STRIDER; break;
            case "efficiency": enchant = Enchantment.DIG_SPEED; break;
            case "feather_falling": enchant = Enchantment.PROTECTION_FALL; break;
            case "fire_aspect": enchant = Enchantment.FIRE_ASPECT; break;
            case "fire_protection": enchant = Enchantment.PROTECTION_FIRE; break;
            case "flame": enchant = Enchantment.ARROW_FIRE; break;
            case "fortune": enchant = Enchantment.LOOT_BONUS_BLOCKS; break;
            case "frost_walker": enchant = Enchantment.FROST_WALKER; break;
            case "infinity": enchant = Enchantment.ARROW_INFINITE; break;
            case "knockback": enchant = Enchantment.KNOCKBACK; break;
            case "looting": enchant = Enchantment.LOOT_BONUS_MOBS; break;
            case "luck_of_the_sea": enchant = Enchantment.LUCK; break;
            case "lure": enchant = Enchantment.LURE; break;
            case "mending": enchant = Enchantment.MENDING; break;
            case "power": enchant = Enchantment.ARROW_KNOCKBACK; break;
            case "projectile_protection": enchant = Enchantment.PROTECTION_PROJECTILE; break;
            case "protection": enchant = Enchantment.PROTECTION_ENVIRONMENTAL; break;
            case "punch": enchant = Enchantment.ARROW_DAMAGE; break;
            case "respiration": enchant = Enchantment.OXYGEN; break;
            case "sharpness": enchant = Enchantment.DAMAGE_ALL; break;
            case "silk_touch": enchant = Enchantment.SILK_TOUCH; break;
            case "smite": enchant = Enchantment.DAMAGE_UNDEAD; break;
            case "sweeping": enchant = Enchantment.SWEEPING_EDGE; break;
            case "thorns": enchant = Enchantment.THORNS; break;
            case "unbreaking": enchant = Enchantment.DURABILITY; break;
        }
        return enchant;
    }
}
