package net.projectuniverse.general.cactus_clicker.commands;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.cactus_clicker.instance_management.InstanceManagement;
import net.projectuniverse.general.commands.UniverseCommand;

public class CmdSpawn extends UniverseCommand {
    /**
     * Creates a new UniverseCommand with the specified name, description, usage, and aliases.
     *
     * @param name        the name of the command (cannot be null)
     * @param description the description of the command
     * @param usage       the usage instructions for the command (prefixed with "/")
     * @param aliases     optional aliases for the command
     */
    public CmdSpawn() {
        super("spawn", "Teleports you to the Spawn", "spawn");

        addSyntax((sender, context) -> {
           if(!(sender instanceof Player player)) return;
           if(!InstanceManagement.hasInstance(player)) return;
           InstanceManagement.savePlayerInstance(player);
        });

    }
}
