package net.projectuniverse.general.logging.loggers;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.output.IOutputPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class for creating instances of ILogger.
 */

public class LoggerBuilder {

    private String loggerName;
    private List<IOutputPrinter> printer;

    /**
     * LoggerBuilder class is used to build a logger with a specified logger name.
     */
    public LoggerBuilder(String loggerName) {
        this.loggerName = loggerName;
        printer = new ArrayList<>();
    }

    /**
     * Adds an output printer to the list of printers in the LoggerBuilder.
     *
     * @param outputPrinter the output printer to be added
     * @return the LoggerBuilder instance
     */
    public LoggerBuilder addOutputPrinter(IOutputPrinter outputPrinter) {
        printer.add(outputPrinter);
        return this;
    }

    /**
     * Builds and returns an instance of ILogger based on the configuration set in the LoggerBuilder.
     *
     * @return an instance of ILogger
     */
    public ILogger build() {
        return new BaseLogger(loggerName, printer);
    }



}
