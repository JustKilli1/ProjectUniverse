package net.projectuniverse.general.logging.output;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;
import net.projectuniverse.general.logging.files.FileHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * FilePrinter is an implementation of the IOutputPrinter interface that writes log messages to a file.
 * It manages the log file using a FileHandler and provides methods to print log messages at different log levels.
 */

public class FilePrinter implements IOutputPrinter{
    public static final String LOG_DIRECTORY = "logs";
    public static final String LOG_FILE_PREFIX = "log";
    //FileHandler that Manages the LogFile
    private FileHandler fileHandler;
    protected LogCategory logCategory;

    /**
     * Creates a new instance of FilePrinter.
     *
     * @param logCategory the log category for which the file printer is created
     * @param logFileName the name of the log file
     */
    public FilePrinter(LogCategory logCategory, String logFileName) {
        this.logCategory = logCategory;
        fileHandler = new FileHandler(LOG_DIRECTORY + "/" + logCategory.getFolderName() + "/" + LOG_FILE_PREFIX + "_" + logFileName);

    }

    /**
     * Prints a list of messages to a file.
     *
     * @param level the log level of the messages
     * @param message the list of messages to be printed
     */
    @Override
    public void print(LogLevel level, List<String> message) {
        try {
            if(level != null && !level.equals(LogLevel.DEBUG)) if(!ILogger.debugMode) return;
            fileHandler.write(message, fileHandler.fileExists());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints a single message to a file.
     *
     * @param level the log level of the message
     * @param message the message to be printed
     */
    @Override
    public void print(LogLevel level, String message) {
        print(level, List.of(message));
    }

    /**
     * Formats the log message into a specific format.
     *
     * @param logLevel the log level of the message
     * @param loggerName the name of the logger
     * @param message the list of messages to be formatted
     * @param ex the exception associated with the log message
     * @return the formatted log message as a string
     */
    @Override
    public String format(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null ? "" : "\n" + LoggingUtils.getMessageStr(message, false);
        String exceptionMSG = ex == null ? "" : "\nException: " + LoggingUtils.getStackTraceAsStr(ex);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "----------------------[" + currentDateTime.format(formatter) + "]----------------------\n" +
                "Level: " + logLevel.getName() + "\n" +
                "Logger Name: " + loggerName +
                messageMSG +
                exceptionMSG;
    }

    /**
     * Formats the log message into a specific format.
     *
     * @param logLevel the log level of the message
     * @param loggerName the name of the logger
     * @param message the message to be formatted
     * @param ex the exception associated with the log message
     * @return the formatted log message as a string
     */
    @Override
    public String format(LogLevel logLevel, String loggerName, String message, Exception ex) {
        return format(logLevel, loggerName, List.of(message), ex);
    }
}
