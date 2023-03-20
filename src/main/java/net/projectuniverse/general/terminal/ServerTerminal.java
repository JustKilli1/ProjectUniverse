package net.projectuniverse.general.terminal;

import net.minestom.server.MinecraftServer;
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

    public static final Set<String> SHELL_COMMANDS = Set.of("exit", "whoami");
    private static final String name = "Project-Universe";
    private static final String prefix = "[" + name + "]";
    private static volatile LineReader lineReader;
    private static volatile Terminal terminal;
    private static volatile boolean running = false;

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
                terminal.close();
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
                .build();
        running = true;
        while(running) {
            String command;
            try {
                command = lineReader.readLine(prefix);
                MinecraftServer.getCommandManager().execute(MinecraftServer.getCommandManager().getConsoleSender(), command);
            } catch(UserInterruptException ex) {
                System.exit(0);
            } catch(EndOfFileException endOfFileEx) {
                return;
            }
        }
    }
}
