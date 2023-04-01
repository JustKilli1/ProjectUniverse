package net.projectuniverse.general.listener;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.projectuniverse.general.AdminPerm;
import net.projectuniverse.general.commands.CmdTeamChat;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.LoggerBuilder;
import net.projectuniverse.general.logging.output.TerminalPrinter;
import net.projectuniverse.general.messenger.MessageDesign;

public class ChatListener {

    private static final ILogger logger = new LoggerBuilder("PlayerChat").addOutputPrinter(new TerminalPrinter()).build();

    public ChatListener() {

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();

        eventHandler.addListener(PlayerChatEvent.class, event -> {
            Player player = event.getPlayer();
            String msg = event.getMessage();
            //CmdTeamChat
            if(CmdTeamChat.getTeamChatMember().contains(player)) {
                Audience audience = Audiences.players(p -> AdminPerm.has(p, AdminPerm.USE_TEAM_CHAT));
                String playerMsg = CmdTeamChat.prefix + "[" + player.getUsername() + "] " + MessageDesign.apply(MessageDesign.PLAYER_MESSAGE, msg);
                audience.sendMessage(Component.text(playerMsg));
                player.sendMessage(Component.text(playerMsg));
                event.setCancelled(true);
            }
            logger.log(LogLevel.INFO, "[" + player.getUsername() + "] " + msg);
        });


    }

}
