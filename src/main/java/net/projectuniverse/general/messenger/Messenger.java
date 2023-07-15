package net.projectuniverse.general.messenger;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;


/**
 * The Messenger class provides utility methods for sending messages to players, command senders, and audiences.
 */

public class Messenger {

    /**
     * Sends a formatted message to a player using the specified message design.
     *
     * @param player   the player to send the message to
     * @param design   the message design to apply to the message
     * @param message  the content of the message
     */
    public static void sendPlayerMessage(Player player, MessageDesign design, String message) {
        player.sendMessage(MessageDesign.apply(design, message));
    }

    /**
     * Sends a formatted message to a command sender using the specified message design.
     *
     * @param sender   the command sender to send the message to
     * @param design   the message design to apply to the message
     * @param message  the content of the message
     */
    public static void sendMessage(CommandSender sender, MessageDesign design, String message) {
        sender.sendMessage(MessageDesign.apply(design, message));
    }

    /**
     * Sends a formatted message to an audience using the specified message design.
     *
     * @param audience  the audience to send the message to
     * @param design    the message design to apply to the message
     * @param message   the content of the message
     */
    public static void sendAudienceMessage(Audience audience, MessageDesign design, String message) {
        audience.sendMessage(Component.text(MessageDesign.apply(design, message)));
    }

    /**
     * Sends a message to an audience.
     *
     * @param audience  the audience to send the message to
     * @param message   the message to send
     */
    public static void sendAudienceMessage(Audience audience, Component message) {
        audience.sendMessage(message);
    }

}
