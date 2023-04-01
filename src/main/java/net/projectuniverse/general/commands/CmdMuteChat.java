package net.projectuniverse.general.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.projectuniverse.general.AdminPerm;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;

public class CmdMuteChat extends Command {

    private static boolean globalMute;

    public CmdMuteChat() {
        super("mutechat", "mc", "muteall");

        addSyntax((sender, context) -> {
            globalMute = !globalMute;
            sender.sendMessage(MessageDesign.apply(MessageDesign.SERVER_MESSAGE, MessagesConfig.MUTE_CHAT_CHANGED
                    .clone()
                    .setConfigParamValue(MessagesParams.MUTED.clone().setValue(String.valueOf(globalMute)))
                    .getValue()));
        });

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        eventHandler.addListener(PlayerChatEvent.class, event -> {
            Player player = event.getPlayer();
           if(!AdminPerm.has(player, AdminPerm.IGNORE_CHAT_MUTE, false)){
               event.setCancelled(globalMute);
               if(globalMute)
                   player.sendMessage(MessageDesign.apply(MessageDesign.SERVER_MESSAGE, MessagesConfig.CHAT_MUTED.getValue()));
           }
        });


    }

}
