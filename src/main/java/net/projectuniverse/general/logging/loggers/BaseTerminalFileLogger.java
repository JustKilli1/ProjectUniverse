package net.projectuniverse.general.logging.loggers;


import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.type.LoggerTypeTerminalFile;

import java.util.Arrays;
import java.util.List;
/**
 * @deprecated Combined to: {@link BaseLogger}
 * @since 0.4.7-dev
 * */
public class BaseTerminalFileLogger extends LoggerTypeTerminalFile implements ILogger {

    private static final String logFileName = "terminal.txt";

    public BaseTerminalFileLogger() {
        super(LogCategory.SYSTEM, logFileName);
    }

    @Override
    public void log(LogLevel logLevel, List<String> message, Exception ex) {
        logToFile(LogLevel.INFO, formatMessage(null, null, message, null));
    }

    @Override
    public void log(LogLevel logLevel, List<String> message) {
        log(logLevel, message, null);
    }

    @Override
    public void log(LogLevel logLevel, String message, Exception ex) {
        log(logLevel, Arrays.asList(message), ex);
    }

    @Override
    public void log(LogLevel logLevel, String message) {
        log(logLevel, message, null);
    }

    @Override
    public void log(LogLevel logLevel, Exception ex) {
        log(logLevel, (String) null, ex);
    }

    @Override
    public String getName() {
        return null;
    }
}
