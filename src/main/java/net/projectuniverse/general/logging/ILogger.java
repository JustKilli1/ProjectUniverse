package net.projectuniverse.general.logging;

import java.util.List;

public interface ILogger {

    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param message A Custom Message
     * @param ex The occurring Exception
     * {@link LogLevel}
     * */
    void log(LogLevel logLevel, List<String> message, Exception ex);
    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param message A Custom Message
     * {@link LogLevel}
     * */
    void log(LogLevel logLevel, List<String> message);

    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param message A Custom Message
     * @param ex The occurring Exception
     * {@link LogLevel}
     * */
    void log(LogLevel logLevel, String message, Exception ex);
    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param message A Custom Message
     * {@link LogLevel}
     * */
    void log(LogLevel logLevel, String message);
    /**
     * Create a Log with the Given Params
     * @param logLevel The Level of the Log
     * @param ex The occurring Exception
     * {@link LogLevel}
     * */
    void log(LogLevel logLevel, Exception ex);
    /**
     * @return the Name of the Logger instance
     * */
    String getName();



}
