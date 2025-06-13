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

public class CmdMuteChat extends UniverseCommand {

    private static boolean globalMute;

    /**
     * Constructor for the CmdMuteChat class.
     * This method initializes the command with its aliases and sets up the necessary event handler.
     * When executed, it toggles the globalMute flag and sends a server message.
     * It also cancels chat events if the player does not have the IGNORE_CHAT_MUTE permission.
     * If globalMute is true, it sends a chat muted message to the player.
     */
    public CmdMuteChat() {
        super("mutechat", "Mutes the Chat", "mutechat", "mc", "muteall");

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
