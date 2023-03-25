package net.projectuniverse.general.listener;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.BaseConsoleLogger;
import net.projectuniverse.general.terminal.ServerTerminal;

public class ChatListener {

    private static final ILogger logger = new BaseConsoleLogger("PlayerChat");

    public ChatListener() {

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();

        eventHandler.addListener(PlayerChatEvent.class, event -> {
            Player sender = event.getPlayer();
            String msg = event.getMessage();
            logger.log(LogLevel.INFO, "[" + sender.getUsername() + "] " + msg);
        });


    }

}
