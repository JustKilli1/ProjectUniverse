package net.projectuniverse.general.messenger;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;

public class Messenger {


    private static final String prefix = "&eProjectUniverse &f>> ";

    public static void sendPlayerMessage(Player player, MessageDesign design, String message) {
        player.sendMessage(prefix + MessageDesign.apply(design, message));

    }

    public static void sendMessage(CommandSender sender, MessageDesign design, String message) {
        sender.sendMessage(prefix + MessageDesign.apply(design, message));

    }

}
