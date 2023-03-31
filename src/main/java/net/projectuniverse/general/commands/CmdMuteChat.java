package net.projectuniverse.general.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.projectuniverse.general.AdminPerm;
import net.projectuniverse.general.messenger.MessageDesign;

public class CmdMuteChat extends Command {

    private static boolean globalMute;

    public CmdMuteChat() {
        super("mutechat", "mc", "muteall");

        addSyntax((sender, context) -> {
            globalMute = !globalMute;
            sender.sendMessage(MessageDesign.apply(MessageDesign.PLAYER_MESSAGE, "Global mute changed to " + globalMute));
        });

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        eventHandler.addListener(PlayerChatEvent.class, event -> {
            Player player = event.getPlayer();
           if(!AdminPerm.hasPerm(player, AdminPerm.IGNORE_CHAT_MUTE)){
               event.setCancelled(globalMute);
               if(globalMute)
                   player.sendMessage(MessageDesign.apply(MessageDesign.PLAYER_MESSAGE, "The chat is muted at the moment."));
           }
        });


    }

}
