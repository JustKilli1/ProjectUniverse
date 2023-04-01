package net.projectuniverse.general.commands.command_executor;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.CommandExecutor;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import org.jetbrains.annotations.NotNull;

public class DefaultExecutor implements CommandExecutor {

    private String syntax;

    public DefaultExecutor(String syntax) {
        this.syntax = syntax;
    }

    @Override
    public void apply(@NotNull CommandSender sender, @NotNull CommandContext context) {
        Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, "Invalid Syntax use " + syntax);
    }
}
