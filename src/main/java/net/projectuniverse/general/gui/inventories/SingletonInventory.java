package net.projectuniverse.general.gui.inventories;

import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.projectuniverse.general.gui.items.ClickAction;
import net.projectuniverse.general.gui.items.ClickableItem;

import java.util.HashMap;
import java.util.Map;

public class SingletonInventory {

    private final Inventory inventory;
    private final Map<Integer, ClickableItem> invItems = new HashMap<>();

    public SingletonInventory(InventoryType type, String title, boolean clickable) {
        inventory = new Inventory(type, title);

        inventory.addInventoryCondition(((player, slot, clickType, inventoryConditionResult) -> {
            inventoryConditionResult.setCancel(!clickable);
            clickIfPresent(slot, player);
        }));
    }

    public void setItem(int slot, ItemStack item, ClickAction action) {
        invItems.put(slot, new ClickableItem(item, action));
        inventory.setItemStack(slot, item);
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    private void clickIfPresent(int slot, Player player) {
        if(invItems.containsKey(slot)) invItems.get(slot).click(player);
    }

}
