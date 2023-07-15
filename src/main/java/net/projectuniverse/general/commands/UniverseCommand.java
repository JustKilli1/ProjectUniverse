package net.projectuniverse.general.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class represents an abstract UniverseCommand.
 * It extends the Command class and provides methods for getting the description and usage of the command.
 *
 * @since 1.0
 */

public abstract class UniverseCommand extends Command {

    public static final ArgumentEntity PLAYER_ARGUMENT = ArgumentType.Entity("player-name");
    public static final ArgumentInteger AMOUNT_ARGUMENT = ArgumentType.Integer("amount");
    public static final ArgumentString NAME = ArgumentType.String("name");
    private final String description, usage;

    /**
     * Creates a new UniverseCommand with the specified name, description, usage, and aliases.
     *
     * @param name        the name of the command (cannot be null)
     * @param description the description of the command
     * @param usage       the usage instructions for the command (prefixed with "/")
     * @param aliases     optional aliases for the command
     */
    public UniverseCommand(@NotNull String name, String description, String usage, @Nullable String... aliases) {
        super(name, aliases);
        this.description = description;
        this.usage = usage.contains("/") ? usage : "/" + usage;
        setDefaultExecutor(new DefaultExecutor(usage));
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }
}
