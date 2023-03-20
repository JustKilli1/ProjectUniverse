package net.projectuniverse.general.logging.type;


import net.minestom.server.MinecraftServer;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;
import net.projectuniverse.general.terminal.ServerTerminal;
import net.projectuniverse.general.terminal.TerminalColor;

import java.util.Arrays;
import java.util.List;

public class LoggerTypeConsole {

    /**
     * Writes A LogMessage to the Console
     * @param level The {@link LogLevel} of the Log
     * @param message The Message that gets written to the File
     * */
    public void logToConsole(LogLevel level, List<String> message) {
        switch(level) {
            case INFO -> message.forEach(ServerTerminal::print);
            case WARN -> message.forEach(msg -> ServerTerminal.print(msg, TerminalColor.YELLOW));
            case ERROR -> message.forEach(msg -> ServerTerminal.print(msg, TerminalColor.RED));
            case DEBUG -> {
                if(ILogger.debugMode) message.forEach(msg -> ServerTerminal.print(msg, TerminalColor.BLUE));
            }
        }
    }

    /**
     * Writes A LogMessage to the Console
     * @param level The {@link LogLevel} of the Log
     * @param message The Message that gets written to the Console
     * */
    public void logToConsole(LogLevel level, String message) {
        logToConsole(level, Arrays.asList(message));
    }

    /**
     * Method for Logger to Format a Console Log Message
     * @param level The {@link LogLevel} of the Log
     * @param message Custom Message
     * @param ex occurring Exception
     * @return Formatted Console LogMessage
     * {@link LogLevel}
     * */
    public String formatMessage(String loggerName, String message, Exception ex) {
        String messageMSG = message == null ? "" : message;
        String exceptionMSG = ex == null ? "" : "\nException: " + LoggingUtils.getStackTraceAsStr(ex);
        return "[" + loggerName + "] " +
                messageMSG +
                exceptionMSG;
    }

}
