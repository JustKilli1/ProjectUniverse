package net.projectuniverse.general.logging.loggergroups;


import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.loggers.BaseConsoleLogger;
import net.projectuniverse.general.logging.loggers.BaseFileLogger;

import java.util.Arrays;
import java.util.List;

/**
 * @deprecated Combined to: {@link net.projectuniverse.general.logging.loggers.BaseLogger}
 * @since 0.4.7-dev
 * */
public class LoggerGroupConsoleFile implements ILoggerGroup {

    private String name;
    private List<ILogger> loggers;

    public LoggerGroupConsoleFile(String loggerName, LogCategory logCategory, String logFileName) {
        name = loggerName;
        loggers = Arrays.asList(
                new BaseFileLogger(logCategory, logFileName, name),
                new BaseConsoleLogger(name)
        );
    }

    @Override
    public void log(LogLevel logLevel, List<String> message, Exception ex) {
        loggers.forEach(logger -> logger.log(logLevel, message, ex));
    }

    @Override
    public void log(LogLevel logLevel, List<String> message) {
        loggers.forEach(logger -> logger.log(logLevel, message));
    }

    @Override
    public void log(LogLevel logLevel, String message, Exception ex) {
        log(logLevel, Arrays.asList(message), ex);
    }

    @Override
    public void log(LogLevel logLevel, String message) {
        log(logLevel, Arrays.asList(message));
    }

    @Override
    public void log(LogLevel logLevel, Exception ex) {
        loggers.forEach(logger -> logger.log(logLevel, ex));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<ILogger> getLogger() {
        return loggers;
    }
}
