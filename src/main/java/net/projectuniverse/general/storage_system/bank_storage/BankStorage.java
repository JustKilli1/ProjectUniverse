package net.projectuniverse.general.storage_system.bank_storage;

import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.projectuniverse.general.gui.inventories.PageableInventory;
import net.projectuniverse.general.gui.items.ClickableItem;

import java.util.List;

public class BankStorage extends PageableInventory<ItemStack> {
    public BankStorage(InventoryType inventoryType, String title, boolean clickable, int[] possibleSlots, List values) {
        super(inventoryType, title, clickable, possibleSlots, values);
    }

    @Override
    public void buildPage(int id) {
        super.buildPage(id);
        //Add own next Page icon with buy page click action if currentPage = maxBoughtPages < maxPossiblePages
    }

    @Override
    public ClickableItem constructItem(ItemStack value) {
        return new ClickableItem(value, null);
    }
}
