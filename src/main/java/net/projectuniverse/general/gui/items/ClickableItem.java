package net.projectuniverse.general.gui.items;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;

public class ClickableItem {

    private final ItemStack item;
    private ClickAction action;

    public ClickableItem(ItemStack item, ClickAction action) {
        this.item = item;
        this.action = action;
    }

    public void click(Player player) {
        if(action != null) {
            action.click(player);
        }
    }

}
