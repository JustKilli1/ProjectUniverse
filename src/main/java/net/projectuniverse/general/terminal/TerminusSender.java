package net.projectuniverse.general.terminal;

import net.minestom.server.command.ConsoleSender;
import org.jetbrains.annotations.NotNull;

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
