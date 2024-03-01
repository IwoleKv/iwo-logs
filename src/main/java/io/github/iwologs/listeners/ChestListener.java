package io.github.iwologs.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestListener implements Listener {

    @EventHandler
    public void onItemPut(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack cursorItem = event.getCursor();

        if (clickedInventory != null && clickedInventory.getType().name().contains("CHEST")) {
            if (cursorItem.getType().isAir()) {
                return;
            }
            player.sendMessage(player.getName() + " włożył " + cursorItem.getType() + " do skrzyni.");
        }
    }

    @EventHandler
    public void onItemTake(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack cursorItem = event.getCursor();

        if (clickedInventory != null && clickedInventory.getType().name().contains("PLAYER")) {
            if (cursorItem.getType().isAir()) {
                return;
            }
            if (event.isShiftClick()) {
                player.sendMessage(player.getName() + " zabrał " + cursorItem.getType() + " ze skrzyni (shift-click).");
            } else if (event.getRawSlot() != event.getView().convertSlot(event.getRawSlot())) {
                player.sendMessage(player.getName() + " zabrał " + cursorItem.getType() + " ze skrzyni (regular click).");
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (inventory.getType().name().contains("PLAYER")) {
            ItemStack cursorItem = player.getItemOnCursor();

            if (cursorItem != null && !cursorItem.getType().isAir()) {
                player.sendMessage(player.getName() + " zabrał " + cursorItem.getType() + " ze skrzyni (close inv).");
            }
        }
    }
}
