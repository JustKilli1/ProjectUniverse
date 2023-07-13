package net.projectuniverse.general.commands.warps;

import net.minestom.server.command.builder.arguments.ArgumentString;
import net.projectuniverse.general.commands.UniverseCommand;

public class CmdSetWarp extends UniverseCommand {
    public CmdSetWarp() {
        super("setWarp", "Sets a new Warp at the Position of the Player with the specified name", "setWarp [name]");

        ArgumentString warpName = UniverseCommand.NAME;

        addSyntax((sender, context) -> {



        }, warpName);
    }
}
