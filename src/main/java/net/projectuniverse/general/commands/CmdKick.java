package net.projectuniverse.general.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;

public class CmdKick extends Command {

    public CmdKick() {
        super("kick", "k");

        /*setDefaultExecutor((sender, context));
        var player =*/

        var playerArg = ArgumentType.Entity("player-name");
        var reasonArg = ArgumentType.StringArray("reason");

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            Player player = playerFinder.findFirstPlayer(sender);
            if(player == null) {
                Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, "Could not find the requested Player");
                return;
            }
            if(player.equals(sender)) {
                Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, "You cannot kick yourself");
                return;
            }
            String[] reasonArray = context.get(reasonArg);
            StringBuilder reason = new StringBuilder("");
            for(String s : reasonArray) reason.append(s + " ");
            player.kick(reason.toString());
            Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, "Player " + player.getUsername() + " kicked for \"" + reason + "\"");
        }, playerArg, reasonArg);

    }

}
