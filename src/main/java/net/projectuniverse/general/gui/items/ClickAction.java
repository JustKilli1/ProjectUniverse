package net.projectuniverse.general.gui.items;

import net.minestom.server.entity.Player;

/**
 * ClickAction is an interface defining the contract for classes that perform an action
 * when a player clicks on something.
 */

public interface ClickAction {

    /**
     * Performs a click action for the specified player.
     *
     * @param player the player who is clicking
     */
    void click(Player player);

}
