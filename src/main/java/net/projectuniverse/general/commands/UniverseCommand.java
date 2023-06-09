package net.projectuniverse.general.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class UniverseCommand extends Command {


    public static final ArgumentEntity PLAYER_ARGUMENT = ArgumentType.Entity("player-name");
    public static final ArgumentInteger AMOUNT_ARGUMENT = ArgumentType.Integer("amount");
    private final String description, usage;

    public UniverseCommand(@NotNull String name, String description, String usage, @Nullable String... aliases) {
        super(name, aliases);
        this.description = description;
        this.usage = usage.contains("/") ? usage : "/" + usage;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }
}
