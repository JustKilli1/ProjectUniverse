package net.projectuniverse.general.gui.inventories;

import net.kyori.adventure.text.Component;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.projectuniverse.general.gui.items.ClickableItem;

import java.util.List;
import java.util.stream.IntStream;

public class TestInventory extends PageableInventory<Integer> {
    public TestInventory(List<Integer> values) {
        super(InventoryType.CHEST_2_ROW, "Test Inventory", false, IntStream.range(0, 9).toArray(), values);
    }

    @Override
    public ClickableItem constructItem(Integer value) {
        return new ClickableItem(ItemStack.of(Material.STONE).withDisplayName(Component.text(String.valueOf(value))), player -> player.sendMessage("Test " + value));
    }
}
