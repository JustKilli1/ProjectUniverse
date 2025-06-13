package net.projectuniverse.general.commands.command_executor;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.CommandExecutor;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import org.jetbrains.annotations.NotNull;

/**
 * The DefaultExecutor class is an implementation of the CommandExecutor interface.
 * It provides a default implementation for the apply method.
 */

public class DefaultExecutor implements CommandExecutor {

    private String syntax;

    /**
     * Creates a new instance of DefaultExecutor with the specified syntax.
     *
     * @param syntax the syntax to be used by the executor
     */
    public DefaultExecutor(String syntax) {
        this.syntax = syntax;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(@NotNull CommandSender sender, @NotNull CommandContext context) {
        Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, "Invalid Syntax use " + syntax);
    }
}
