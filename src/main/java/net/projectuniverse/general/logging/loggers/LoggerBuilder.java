package net.projectuniverse.general.logging.loggers;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.output.IOutputPrinter;

import java.util.ArrayList;
import java.util.List;

public class LoggerBuilder {

    private String loggerName;
    private List<IOutputPrinter> printer;

    public LoggerBuilder(String loggerName) {
        this.loggerName = loggerName;
        printer = new ArrayList<>();
    }

    public LoggerBuilder addOutputPrinter(IOutputPrinter outputPrinter) {
        printer.add(outputPrinter);
        return this;
    }

    public ILogger build() {
        return new BaseLogger(loggerName, printer);
    }



}
