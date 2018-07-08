package org.jaggy.gold.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIListener implements Listener {
    private final GuiManager manager;

    public GUIListener(GuiManager guiManager) {
        manager = guiManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        //escape if not a player
        Inventory inventory = event.getClickedInventory();
        Inventory mainInv = manager.inventory;
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        //get the player
        Player player = (Player) event.getWhoClicked();

        //escape if it isn't our inventory
        if (!inventory.getName().equals(mainInv.getName())) {
            return;
        }

        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        // What if the clicked item is null? Null pointer, so return.
        if (item == null) {
            return;
        }
        // What if the clicked item doesn't have itemmeta? Null pointer, so return.
        if (!item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        // What if the clicked item has no display name? Null pointer, so return.
        if (!meta.hasDisplayName()) {
            return;
        }
        // What if the clicked item is the back button.
        if (meta.getDisplayName().equals("Back")) {
            manager.displayMainMenu(player);
            return;
        }
        manager.accessItem(player, meta.getDisplayName());
    }
}
