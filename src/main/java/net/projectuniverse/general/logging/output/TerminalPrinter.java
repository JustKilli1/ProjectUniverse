package net.projectuniverse.general.logging.output;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;
import net.projectuniverse.general.terminal.ServerTerminal;
import net.projectuniverse.general.terminal.TerminalColor;

import java.util.Arrays;
import java.util.List;

/**
 * The TerminalPrinter class is an implementation of the IOutputPrinter interface
 * that prints log messages to the terminal.
 */

public class TerminalPrinter implements IOutputPrinter{

    /**
     * Prints a list of messages based on the specified log level.
     *
     * @param level   the log level to determine the type of printing
     * @param message the list of messages to be printed
     */
    @Override
    public void print(LogLevel level, List<String> message) {
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
     * Prints a message based on the specified log level.
     *
     * @param level   the log level to determine the type of printing
     * @param message the message to be printed
     */
    @Override
    public void print(LogLevel level, String message) {
        print(level, Arrays.asList(message));
    }

    /**
     * Formats the log message based on the specified log level, logger name, message, and exception.
     *
     * @param logLevel    the log level to determine the type of formatting
     * @param loggerName  the name of the logger to include in the formatted message
     * @param message     the message to be formatted
     * @param ex          the exception to be included in the formatted message
     * @return the formatted log message
     */
    @Override
    public String format(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null ? "" : LoggingUtils.getMessageStr(message, false);
        String exceptionMSG = ex == null ? "" : "\nException: " + LoggingUtils.getStackTraceAsStr(ex);
        return "[" + loggerName + "] " +
                messageMSG +
                exceptionMSG;
    }

    /**
     * Formats the log message based on the specified log level, logger name, message, and exception.
     *
     * @param logLevel    the log level to determine the type of formatting
     * @param loggerName  the name of the logger to include in the formatted message
     * @param message     the message to be formatted
     * @param ex          the exception to be included in the formatted message
     * @return the formatted log message
     */
    @Override
    public String format(LogLevel logLevel, String loggerName, String message, Exception ex) {
        return format(logLevel, loggerName, List.of(message), ex);
    }
}
