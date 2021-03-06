package org.jaggy.gold.gui;

import org.bukkit.Material;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Menu Item Object Class
 */
public class MenuItem {
    /**
     * Stores the material
     */
    public Material material;
    /**
     * Stores the name of the item
     */
    public String name;
    /**
     * Stores the description
     */
    public String description;
    /**
     * Stores the location
     */
    public int location;
    /**
     * Stores the item/icon
     */
    public String icon;
    /**
     * Stores if it a menu or not.
     */
    public String type;
    /**
     * When clicked what we do.
     */
    public String action;
    /**
     * Stores a list of enchantments
     */
    public ArrayList<String> enchants = new ArrayList<String>();

    /**
     * Stores the paypal url for Jaggy Gold
     */
    public String url;

    /**
     * Store child items of menu
     */
    public JSONArray items;
    public Long amount;

    public MenuItem(String name, String icon, String action, String type, String description, Material material) {
        this.name = name;
        this.icon = icon;
        this.action = action;
        this.type = type;
        this.description = description;
        this.material = material;
    }

    /**
     * Parses and places them in enchants
     * @param enchantments String
     */
    public void setEnchantments(String enchantments) {
        if (!enchantments.isEmpty()) {
            String[] args = enchantments.split(",");
            Collection<String> list = Arrays.asList(args);
            this.enchants.addAll(list);
        }
    }

    /**
     * Parse and set the location of the item
     * @param location
     */
    public void setLocation(String location) {
        this.location = Integer.parseInt(location);
    }

    /**
     * Sets the sub items of a menu
     * @param items
     */
    public void setItems(JSONArray items) {
        this.items = items;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAmount(Long gold) {
        this.amount = gold;
    }
}
