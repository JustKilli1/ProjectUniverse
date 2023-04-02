package net.projectuniverse.general.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;

public class CmdTeleport extends Command {
    public CmdTeleport() {
        super("teleport", "tp");
        var playerArg = ArgumentType.Entity("player-name");

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            Player player = playerFinder.findFirstPlayer(sender);
            if(player == null) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_NOT_FOUND.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
                return;
            }
            if(player.equals(sender)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.TELEPORT_TO_YOURSELF.getValue());
                return;
            }
            if(!(sender instanceof Player)) return;
            Player senderPlayer = (Player) sender;
            senderPlayer.setInstance(player.getInstance());
            senderPlayer.teleport(player.getPosition());
            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.TELEPORT_SUCCESS.clone()
                    .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                    .getValue());
        }, playerArg);
    }
}
