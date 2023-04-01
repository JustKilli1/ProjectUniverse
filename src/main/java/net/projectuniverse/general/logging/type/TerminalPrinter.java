package net.projectuniverse.general.logging.type;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;
import net.projectuniverse.general.terminal.ServerTerminal;
import net.projectuniverse.general.terminal.TerminalColor;

import java.util.Arrays;
import java.util.List;

public class TerminalPrinter implements IOutputPrinter{


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

    @Override
    public void print(LogLevel level, String message) {
        print(level, Arrays.asList(message));
    }

    @Override
    public String format(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null ? "" : LoggingUtils.getMessageStr(message, true);
        String exceptionMSG = ex == null ? "" : "\nException: " + LoggingUtils.getStackTraceAsStr(ex);
        return "[" + loggerName + "] " +
                messageMSG +
                exceptionMSG;
    }

    @Override
    public String format(LogLevel logLevel, String loggerName, String message, Exception ex) {
        return format(logLevel, loggerName, List.of(message), ex);
    }
}
