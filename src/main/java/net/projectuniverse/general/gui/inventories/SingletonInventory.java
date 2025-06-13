package net.projectuniverse.general.gui.inventories;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.projectuniverse.general.gui.items.ClickAction;
import net.projectuniverse.general.gui.items.ClickableItem;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a singleton inventory.
 * This class provides methods to manage and interact with the inventory.
 */

public class SingletonInventory {

    private final Inventory inventory;
    private final Map<Integer, ClickableItem> inventoryItems = new HashMap<>();

    /**
     * Creates a new SingletonInventory object with the specified parameters.
     *
     * @param inventoryType the type of inventory to create
     * @param clickable true if the inventory is clickable, false otherwise
     * @param title the title of the inventory
     */
    public SingletonInventory(InventoryType inventoryType, boolean clickable, String title){
        this.inventory = new Inventory(inventoryType, title);

        this.inventory.addInventoryCondition((player, slot, clickType, result) -> {
            result.setCancel(!clickable);

            this.clickIfPreset(slot, player);
        });
    }

    /**
     * Sets an item in the inventory at the specified slot with the specified ItemStack and ClickAction.
     *
     * @param slot the slot to set the item at
     * @param stack the ItemStack to set
     * @param clickAction the ClickAction to associate with the item
     */
    public void setItem(int slot, ItemStack stack, ClickAction clickAction) {
        this.inventoryItems.put(slot, new ClickableItem(stack, clickAction));
        this.inventory.setItemStack(slot, stack);
    }

    /**
     * Sets a ClickableItem in the inventory at the specified slot.
     *
     * @param slot the slot to set the item at
     * @param clickableItem the ClickableItem to set
     */
    public void setItem(int slot, ClickableItem clickableItem) {
        this.inventoryItems.put(slot, clickableItem);
        this.inventory.setItemStack(slot, clickableItem.getStack());
    }

    /**
     * Clicks the ClickableItem in the inventory at the specified slot if it is present.
     *
     * @param slot the slot to click the item at
     * @param player the player who clicked the item
     */
    private void clickIfPreset(int slot, Player player) {
        if(this.inventoryItems.containsKey(slot)) {
            this.inventoryItems.get(slot).click(player);
        }
    }

    /**
     * Opens the inventory for the specified player.
     *
     * @param player the player for whom the inventory should be opened
     */
    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    /**
     * Removes the item at the specified slot from the inventory.
     *
     * @param slot the slot of the item to be removed
     */
    public void removeItem(int slot) {
        this.inventory.setItemStack(slot, ItemStack.AIR);
        this.inventoryItems.remove(slot);
    }

    /**
     * Sets the title of the inventory.
     *
     * @param title the new title of the inventory
     */
    public void setTitle(String title) {
        this.inventory.setTitle(Component.text(title));
    }

    public Inventory getInventory() {
        return inventory;
    }
}
