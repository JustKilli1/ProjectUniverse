package net.projectuniverse.general.penalty_system.commands;

import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.projectuniverse.general.AdminPerm;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;

/**
 * Represents a command that kicks a player from the universe.
 * Inherits from the UniverseCommand class.
 *
 * Usage: /kick [Player-Name] [Reason...]
 * Aliases: /k
 */

public class CmdKick extends UniverseCommand {

    /**
     * Represents a command for kicking a player.
     * This command is used to kick a specified player with an optional reason.
     * The kick command can only be executed by players with appropriate permissions.
     *
     * Command Syntax:
     *     kick [Player-Name] [Reason...]
     *
     * Examples:
     *     kick JohnDoe Disruptive Behavior
     *     kick JaneSmith AFK too long
     */
    public CmdKick() {
        super("kick", "Kicks a Player", "kick [Player-Name] [Reason...]", "k");

        setDefaultExecutor(new DefaultExecutor(getUsage()));

        var playerArg = ArgumentType.Entity("player-name");
        var reasonArg = ArgumentType.StringArray("reason");

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            Player player = playerFinder.findFirstPlayer(sender);
            if(player == null) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_NOT_FOUND
                        .clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
                return;
            }
            if(player.equals(sender)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.KICK_YOURSELF.getValue());
                return;
            }
            if(AdminPerm.has(player, AdminPerm.IGNORE_KICK, false)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.CANT_KICK_PLAYER.getValue());
                return;
            }
            String[] reasonArray = context.get(reasonArg);
            StringBuilder reason = new StringBuilder("");
            for(String s : reasonArray) reason.append(s + " ");
            player.kick(reason.toString());
            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.KICK_SUCCESS
                            .clone()
                            .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                            .setConfigParamValue(MessagesParams.PUNISHMENT_REASON.clone().setValue(reason.toString()))
                            .getValue());
        }, playerArg, reasonArg);

    }

}
