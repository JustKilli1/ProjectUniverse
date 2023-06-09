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

public class SingletonInventory {

    private final Inventory inventory;
    private final Map<Integer, ClickableItem> inventoryItems = new HashMap<>();

    public SingletonInventory(InventoryType inventoryType, boolean clickable, String title){
        this.inventory = new Inventory(inventoryType, title);

        this.inventory.addInventoryCondition((player, slot, clickType, result) -> {
            result.setCancel(!clickable);

            this.clickIfPreset(slot, player);
        });
    }

    public void setItem(int slot, ItemStack stack, ClickAction clickAction) {
        this.inventoryItems.put(slot, new ClickableItem(stack, clickAction));
        this.inventory.setItemStack(slot, stack);
    }

    public void setItem(int slot, ClickableItem clickableItem) {
        this.inventoryItems.put(slot, clickableItem);
        this.inventory.setItemStack(slot, clickableItem.getStack());
    }

    private void clickIfPreset(int slot, Player player) {
        if(this.inventoryItems.containsKey(slot)) {
            this.inventoryItems.get(slot).click(player);
        }
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    public void removeItem(int slot) {
        this.inventory.setItemStack(slot, ItemStack.AIR);
        this.inventoryItems.remove(slot);
    }

    public void setTitle(String title) {
        this.inventory.setTitle(Component.text(title));
    }

    public Inventory getInventory() {
        return inventory;
    }
}
