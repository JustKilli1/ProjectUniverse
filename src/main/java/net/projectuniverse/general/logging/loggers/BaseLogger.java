package net.projectuniverse.general.logging.loggers;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.output.IOutputPrinter;

import java.util.List;

/**
 * BaseLogger is a class that implements the ILogger interface. It provides basic logging functionalities and sends logs to specified output printers.
 */

public class BaseLogger implements ILogger {

    private String loggerName;
    private List<IOutputPrinter> outputPrinter;

    /**
     * Initializes a new instance of the BaseLogger class.
     * @param loggerName The name of the logger.
     * @param outputPrinter The list of output printers to be used by the logger.
     */
    public BaseLogger(String loggerName, List<IOutputPrinter> outputPrinter) {
        this.loggerName = loggerName;
        this.outputPrinter = outputPrinter;
    }

    /**
     * Logs a message with the specified log level, message contents, and exception.
     * @param logLevel The log level of the message.
     * @param message The contents of the log message.
     * @param ex The exception associated with the log message, or null if no exception is present.
     */
    @Override
    public void log(LogLevel logLevel, List<String> message, Exception ex) {
        notifyPrinter(logLevel, message, ex);
    }

    /**
     * Logs a message with the specified log level, message contents, and no exception.
     * @param logLevel The log level of the message.
     * @param message The contents of the log message.
     */
    @Override
    public void log(LogLevel logLevel, List<String> message) {
        log(logLevel, message, null);
    }

    /**
     * Logs a message with the specified log level, message content, and exception.
     *
     * @param logLevel The log level of the message.
     * @param message The content of the log message.
     * @param ex The exception associated with the log message.
     */
    @Override
    public void log(LogLevel logLevel, String message, Exception ex) {
        log(logLevel, List.of(message), ex);
    }

    /**
     * Logs a message with the specified log level and message content.
     *
     * @param logLevel The log level of the message.
     * @param message The content of the log message.
     */
    @Override
    public void log(LogLevel logLevel, String message) {
        log(logLevel, List.of(message), null);
    }

    /**
     * Logs an exception with the specified log level.
     *
     * @param logLevel The log level of the exception.
     * @param ex The exception to be logged.
     */
    @Override
    public void log(LogLevel logLevel, Exception ex) {
        log(logLevel, (List<String>) null, ex);
    }

    @Override
    public String getName() {
        return loggerName;
    }
    /**
     * Notify all present OutputPrinter
     * @param level Level of the Message
     * @param message The Message that gets send to the OutputPrinter
     * */
    private void notifyPrinter(LogLevel level, List<String> message, Exception ex) {
        outputPrinter.forEach(printer -> printer.print(level, printer.format(level, loggerName, message, ex)));
    }

}
