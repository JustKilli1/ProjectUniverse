package net.projectuniverse.general.listener;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.projectuniverse.base.ColorDesign;
import net.projectuniverse.general.AdminPerm;
import net.projectuniverse.general.commands.CmdTeamChat;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;

/**
 * The ChatListener class is responsible for listening to player chat events and performing actions based on the chat messages.
 */

public class ChatListener {

    private static final ILogger logger = new LoggerBuilder("PlayerChat").addOutputPrinter(new TerminalPrinter()).build();

    public ChatListener() {
        onChat();
    }

    /**
     * The onChat method is a private method that handles the player chat event.
     * It listens for the PlayerChatEvent and processes the chat message accordingly.
     */
    private void onChat() {
        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();

        eventHandler.addListener(PlayerChatEvent.class, event -> {
            Player player = event.getPlayer();
            String msg = event.getMessage();
            //CmdTeamChat
            TextComponent msgComponent = Component.text()
                    .content(player.getUsername())
                    .color(TextColor.color(ColorDesign.getPlayerName()))
                    .append(Component.text().content(" >> ").color(TextColor.color(ColorDesign.getChatSeparator())).build())
                    .append(Component.text().content(msg).color(TextColor.color(ColorDesign.getBaseText())).build())
                    .build();
            if(CmdTeamChat.getTeamChatMember().contains(player)) {
                Audience audience = Audiences.players(p -> !AdminPerm.has(p, AdminPerm.USE_TEAM_CHAT, false));
                Messenger.sendAudienceMessage(audience, msgComponent.content("[TeamChat] " + player.getUsername()));
                event.setCancelled(true);
                logger.log(LogLevel.INFO, CmdTeamChat.prefix + "[" + player.getUsername() + "] " + msg);
                return;
            }

            //Send Player Message

            event.setChatFormat(events -> msgComponent);
            logger.log(LogLevel.INFO, "[" + player.getUsername() + "] " + msg);
        });
    }

}
