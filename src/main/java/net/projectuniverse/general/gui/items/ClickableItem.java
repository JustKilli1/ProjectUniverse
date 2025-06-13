package net.projectuniverse.general.gui.items;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
/**
 * Represents an item that can be interacted with by the player.
 * The item can be clicked, and when clicked, it performs a specified action.
 */

public class ClickableItem {
    private final ItemStack stack;
    private ClickAction clickAction = null;

    /**
     * Creates a ClickableItem object.
     *
     * @param stack       the ItemStack representing the item
     * @param clickAction the ClickAction to be performed when the item is clicked
     */
    public ClickableItem(ItemStack stack, ClickAction clickAction) {
        this.stack = stack;
        this.clickAction = clickAction;
    }

    /**
     * Performs the click action on the specified player.
     *
     * @param player the player who clicked the item
     */
    public void click(Player player) {
        if(clickAction != null) {
            clickAction.click(player);
        }
    }

    public ItemStack getStack() {
        return stack;
    }

    public ClickAction getClickAction() {
        return clickAction;
    }
}
