package net.projectuniverse.general.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.messenger.MessageDesign;

import java.util.HashSet;
import java.util.Set;

public class CmdTeamChat extends Command {

    public static final String prefix = "[TeamChat] ";
    private static Set<Player> teamChatMember = new HashSet<>();

    public CmdTeamChat() {
        super ("teamchat", "tc");

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;
            Player player = (Player) sender;
            //if(!AdminPerm.has(player, AdminPerm.USE_TEAM_CHAT, true)) return;
            if(!teamChatMember.contains(player)) {
                teamChatMember.add(player);
                player.sendMessage(MessageDesign.apply(MessageDesign.SERVER_MESSAGE, MessagesConfig.TEAM_CHAT_ACTIVATED.getValue()));
            } else {
                teamChatMember.remove(player);
                player.sendMessage(MessageDesign.apply(MessageDesign.SERVER_MESSAGE, MessagesConfig.TEAM_CHAT_DEACTIVATED.getValue()));
            }
        });

    }

    public static Set<Player> getTeamChatMember() { return teamChatMember; }

}
