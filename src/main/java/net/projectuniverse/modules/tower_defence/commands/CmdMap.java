package net.projectuniverse.modules.tower_defence.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerChatEvent;
import net.projectuniverse.modules.tower_defence.game.map.Path;

import java.util.HashMap;
import java.util.Map;

public class CmdMap extends Command {

    private static Map<Player, Path> activePathSetups = new HashMap<>();

    public CmdMap() {
        super("map");
        //addSubcommand();

        addSyntax((sender, context) -> {
                GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
                globalEventHandler.addListener(PlayerBlockInteractEvent.class, event -> {
                    Player player = event.getPlayer();
                });
                globalEventHandler.addListener(PlayerChatEvent.class, event -> {
                    event.getPlayer().sendMessage("Du hast was geschrieben lol");
                });
        });



    }
}
