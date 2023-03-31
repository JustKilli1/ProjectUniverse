package net.projectuniverse.general.messenger;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;

public class Messenger {

    public static void sendPlayerMessage(Player player, MessageDesign design, String message) {
        player.sendMessage(MessageDesign.apply(design, message));

    }

    public static void sendMessage(CommandSender sender, MessageDesign design, String message) {
        sender.sendMessage(MessageDesign.apply(design, message));

    }

}
