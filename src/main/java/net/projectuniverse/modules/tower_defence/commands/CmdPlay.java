package net.projectuniverse.modules.tower_defence.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.arena_system.Arena;
import net.projectuniverse.general.arena_system.ArenaManager;

public class CmdPlay  extends Command {

    public CmdPlay() {
        super("play");
        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;
            Player player = (Player) sender;
            //Arena arena = new Arena(player);
            //ArenaManager.register(player, arena);
        });
    }

}
