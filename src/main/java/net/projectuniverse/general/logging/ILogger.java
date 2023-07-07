package net.projectuniverse.general.logging;

import java.util.List;


/**
 * ILogger interface defines the contract for logging functionality.
 * <p>
 * Implementations of ILogger should provide methods to create logs with different parameters, such as log level,
 * message, and exception. The logs can be created with or without an exception, and with or without a custom message.
 * The log level determines the severity of the log.
 */

public interface ILogger {

    /**
     * Indicates whether the application is running in debug mode.
     * Debug mode allows for additional logging and debugging functionality.
     */
    boolean debugMode = true;

    /**
     * Creates a log with the given log level, message, and exception.
     *
     * @param logLevel The level of the log. Must be one of the values defined in {@link LogLevel}.
     * @param message A list of custom messages to be included in the log.
     * @param ex The exception that occurred, if any. Can be null if no exception occurred.
     *
     * @see LogLevel
     */
    void log(LogLevel logLevel, List<String> message, Exception ex);

    /**
     * Creates a log with the given log level and message.
     *
     * @param logLevel The level of the log. Must be one of the values defined in {@link LogLevel}.
     * @param message A list of custom messages to be included in the log.
     *
     * @see LogLevel
     */
    void log(LogLevel logLevel, List<String> message);

    /**
     * Creates a log with the given log level, message, and exception.
     *
     * @param logLevel The level of the log. Must be one of the values defined in {@link LogLevel}.
     * @param message The custom message to be included in the log.
     * @param ex The exception that occurred and is to be included in the log.
     *
     * @see LogLevel
     */
    void log(LogLevel logLevel, String message, Exception ex);

    /**
     * Creates a log with the given log level and message.
     *
     * @param logLevel The level of the log. Must be one of the values defined in {@link LogLevel}.
     * @param message The custom message to be included in the log.
     *
     * @see LogLevel
     */
    void log(LogLevel logLevel, String message);

    /**
     * Creates a log with the given log level and exception.
     *
     * @param logLevel The level of the log. Must be one of the values defined in {@link LogLevel}.
     * @param ex The exception to be included in the log.
     *
     * @see LogLevel
     */
    void log(LogLevel logLevel, Exception ex);
    /**
     * @return the Name of the Logger instance
     * */
    String getName();



}
