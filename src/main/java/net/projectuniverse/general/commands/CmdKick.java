package net.projectuniverse.general.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.projectuniverse.general.AdminPerm;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;

public class CmdKick extends Command {

    public CmdKick() {
        super("kick", "k");

        setDefaultExecutor(new DefaultExecutor("/kick [PlayerName] [Reason...]"));

        var playerArg = ArgumentType.Entity("player-name");
        var reasonArg = ArgumentType.StringArray("reason");

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            Player player = playerFinder.findFirstPlayer(sender);
            if(player == null) {
                Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, MessagesConfig.KICK_PLAYER_NOT_FOUND
                        .clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
                return;
            }
            if(player.equals(sender)) {
                Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, MessagesConfig.KICK_YOURSELF.getValue());
                return;
            }
            if(AdminPerm.has(player, AdminPerm.IGNORE_KICK)) {
                Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, MessagesConfig.CANT_KICK_PLAYER.getValue());
                return;
            }
            String[] reasonArray = context.get(reasonArg);
            StringBuilder reason = new StringBuilder("");
            for(String s : reasonArray) reason.append(s + " ");
            player.kick(reason.toString());
            Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, MessagesConfig.KICK_SUCCESS
                            .clone()
                            .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                            .setConfigParamValue(MessagesParams.PUNISHMENT_REASON.clone().setValue(reason.toString()))
                            .getValue());
        }, playerArg, reasonArg);

    }

}
