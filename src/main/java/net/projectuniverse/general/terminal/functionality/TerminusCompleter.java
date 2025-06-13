package net.projectuniverse.general.terminal.functionality;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.projectuniverse.general.terminal.ServerTerminal;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A class that provides completion suggestions for the Terminus command line interface.
 */

public class TerminusCompleter implements Completer {
    private static final Set<Candidate> SHELL_COMMANDS = Set.of(ServerTerminal.SHELL_COMMANDS.stream()
            .map(cmd -> new Candidate(cmd.name())).toArray(Candidate[]::new));

    /**
     * Completes the input line during tab completion.
     *
     * @param reader     the line reader.
     * @param line       the parsed line object.
     * @param candidates the list of completion candidates.
     */
    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        if (line.wordIndex() == 0) {
            Set<Command> commands = MinecraftServer.getCommandManager().getDispatcher().getCommands();
            for (Command command : commands) {
                candidates.addAll(Arrays.stream(command.getNames()).map(Candidate::new).toList());
            }
            candidates.addAll(SHELL_COMMANDS);
        } else {
            Command command = MinecraftServer.getCommandManager().getCommand(line.words().get(0));
            if (command == null) return;
            candidates.addAll(command.getSyntaxesStrings().stream().map(Candidate::new).toList());
        }
    }
}
