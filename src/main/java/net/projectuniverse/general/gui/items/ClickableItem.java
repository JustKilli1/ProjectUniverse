package net.projectuniverse.general.gui.items;

import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
public class ClickableItem {
    private final ItemStack stack;
    private ClickAction clickAction = null;

    public ClickableItem(ItemStack stack, ClickAction clickAction) {
        this.stack = stack;
        this.clickAction = clickAction;
    }

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
