package net.projectuniverse.general.logging.output;

import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * TerminalFilePrinter is a subclass of FilePrinter that prints log messages to the terminal and writes them to a log file.
 */

public class TerminalFilePrinter extends FilePrinter{

    private static final String logFileName = "terminal.txt";

    /**
     * Creates a new instance of TerminalFilePrinter.
     * This class is used to print logs to a file from the terminal.
     * It extends the superclass LogPrinter and sets the log category to SYSTEM
     * and specifies the log file name.
     */
    public TerminalFilePrinter() {
        super(LogCategory.SYSTEM, logFileName);
    }

    /**
     * Formats log messages into a printable string.
     *
     * @param logLevel   the log level of the message
     * @param loggerName the name of the logger
     * @param message    the list of log message strings
     * @param ex         the exception associated with the log message, can be null
     * @return the formatted log message string
     */
    @Override
    public String format(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null && message.size() == 0 ? "" : LoggingUtils.getMessageStr(message, false);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + currentDateTime.format(formatter) + "]" +
                messageMSG;
    }
}
