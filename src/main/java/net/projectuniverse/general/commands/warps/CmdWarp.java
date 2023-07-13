package net.projectuniverse.general.commands.warps;

import net.minestom.server.command.builder.arguments.ArgumentType;
import net.projectuniverse.general.commands.UniverseCommand;

public class CmdWarp extends UniverseCommand {
    public CmdWarp() {
        super("warp", "Warp to a Location", "warp [warp-name]");

        var test = ArgumentType.Word("warp-location");
        test.from("Test", "Test2", "Test3");

        addSyntax((sender, context) -> {
            System.out.println("Hello World");
        }, test);

    }
}
