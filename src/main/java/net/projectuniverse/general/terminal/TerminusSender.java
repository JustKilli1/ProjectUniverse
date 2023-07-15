package net.projectuniverse.general.terminal;

import net.minestom.server.command.ConsoleSender;
import org.jetbrains.annotations.NotNull;

/**
 * TerminusSender is a class that extends ConsoleSender and is used to send messages
 * to a server terminal. It provides a method to send messages to the terminal.
 *
 * @see ConsoleSender
 */

public class TerminusSender extends ConsoleSender {
    private final ServerTerminal shell;

    protected TerminusSender(ServerTerminal shell) {
        this.shell = shell;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        shell.print(message);
    }
}
