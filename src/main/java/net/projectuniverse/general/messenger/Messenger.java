package net.projectuniverse.general.messenger;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;


public class Messenger {

    public static void sendPlayerMessage(Player player, MessageDesign design, String message) {
        player.sendMessage(MessageDesign.apply(design, message));
    }

    public static void sendMessage(CommandSender sender, MessageDesign design, String message) {
        sender.sendMessage(MessageDesign.apply(design, message));
    }

    public static void sendAudienceMessage(Audience audience, MessageDesign design, String message) {
        audience.sendMessage(Component.text(MessageDesign.apply(design, message)));
    }

    public static void sendAudienceMessage(Audience audience, Component message) {
        audience.sendMessage(message);
    }

}
