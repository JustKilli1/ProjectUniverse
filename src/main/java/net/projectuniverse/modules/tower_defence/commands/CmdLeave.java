package net.projectuniverse.modules.tower_defence.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.arena_system.Arena;
import net.projectuniverse.general.arena_system.ArenaManager;

public class CmdLeave extends Command {

    public CmdLeave() {
        super("leave");
        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;
            Player player = (Player) sender;
            ArenaManager.unregister(player);
        });
    }

}
