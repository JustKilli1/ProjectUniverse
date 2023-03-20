package net.projectuniverse.general.terminal;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.CommandData;
import net.minestom.server.command.builder.CommandResult;
import net.projectuniverse.general.server.Server;
import net.projectuniverse.general.terminal.functionality.TerminusCompleter;
import net.projectuniverse.general.terminal.functionality.TerminusHighlighter;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultExpander;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Set;

public class ServerTerminal implements Runnable {

    public static final Set<String> SHELL_COMMANDS = Set.of("exit", "stop");
    private static final CommandManager COMMAND_MANAGER = MinecraftServer.getCommandManager();
    private final TerminusSender terminusSender;
    private static final String name = "Project-Universe";
    private static final String prefix = "[" + name + "] ";
    private static volatile LineReader lineReader;
    private static volatile Terminal terminal;
    private static volatile boolean running = false;
    private final Highlighter highlighter = new TerminusHighlighter();
    private final Completer completer = new TerminusCompleter();
    private final Expander expander = new DefaultExpander();
    private final History history = new DefaultHistory();

    public ServerTerminal() {
        terminusSender = new TerminusSender(this);
    }

    /**
     * Starts the Terminal
     * */
    public void start() {
        final Thread terminalThread = new Thread(null, this, name);
        terminalThread.setDaemon(true);
        terminalThread.start();
    }

    /**
     * Stops the Terminal
     * */
    public void stop() {
        running = false;
        if(terminal != null) {
            try {
                print("Terminal wird gestoppt");
                terminal.close();
                print("Terminal gestoppt");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        try {
            terminal = TerminalBuilder.builder().system(true).dumb(true).encoding("UTF-8").name(name).build();
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }

        lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .highlighter(highlighter)
                .completer(completer)
                .expander(expander)
                .history(history)
                .build();
        running = true;
        while(running) {
            String command;
            try {
                command = lineReader.readLine(prefix);
            } catch (UserInterruptException ignored) {
                continue;
            } catch (EndOfFileException ignored) {
                break;
            }
            if (command == null) break;
            if (command.isBlank()) continue;

            executeCommand(command);
        }
    }

    public void executeCommand(String command) {
        String[] words = command.split(" ");
        if (SHELL_COMMANDS.contains(words[0]))
            switch (words[0]) {
                case "exit" -> stop();
                case "stop" -> {
                    Server.stop();
                    stop();
                }
            }
        else {
            CommandResult result = COMMAND_MANAGER.execute(terminusSender, command);
            switch (result.getType()) {
                case UNKNOWN -> print("Unknown command: %s\n".formatted(result.getInput()));
                case INVALID_SYNTAX -> print("Invalid command syntax: %s\n".formatted(result.getInput()));
                case CANCELLED -> print("Command was cancelled: %s\n".formatted(result.getInput()));
            }
            CommandData commandData = result.getCommandData();
            if (commandData != null && !commandData.getDataMap().isEmpty()) {
                for (String key : commandData.getDataMap().keySet()) {
                    print(" - \"%s\": \"%s\"".formatted(key, commandData.get(key)));
                }
            }
        }
    }

    public void print(String line) {
        lineReader.printAbove(prefix + line);
    }

}
