package net.projectuniverse.general.logging.type;


import com.google.gson.annotations.Since;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;
import net.projectuniverse.general.logging.files.FileHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Class that provides default Methods to Write Logs to a File
 * @deprecated New Implementation: {@link FilePrinter}
 * @since 0.4.7-dev
 * */
public class LoggerTypeFile {

    public static final String LOG_DIRECTORY = "logs";
    public static final String LOG_FILE_PREFIX = "log";
    //FileHandler that Manages the LogFile
    private FileHandler fileHandler;
    protected LogCategory logCategory;

    public LoggerTypeFile(LogCategory logCategory, String logFileName) {
        this.logCategory = logCategory;
        fileHandler = new FileHandler(LOG_DIRECTORY + "/" + logCategory.getFolderName() + "/" + LOG_FILE_PREFIX + "_" + logFileName);

    }

    /**
     * Writes A LogMessage to a File
     * @param message The Message that gets written to the File
     * @see FileHandler
     * */
    public void logToFile(LogLevel level, List<String> message) {
        try {
            if(!level.equals(LogLevel.DEBUG)) if(!ILogger.debugMode) return;
            fileHandler.write(message, fileHandler.fileExists());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Writes A LogMessage to a File
     * @param message The Message that gets written to the File
     * */
    public void logToFile(LogLevel level, String message) {
        logToFile(level, Arrays.asList(message));
    }

    /**
     * Method for Logger to Format a File Log Message
     * @param logLevel LogLevel
     * @param loggerName Name of the calling logger
     * @param message Custom Message
     * @param ex occurring Exception
     * @return Formatted LogMessage
     * {@link LogLevel}
     * */
    public String formatMessage(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null ? "" : "\n" + getMessageStr(message);
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
     * Method for Logger to Format a File Log Message
     * @param logLevel LogLevel
     * @param loggerName Name of the calling logger
     * @param message Custom Message
     * @param ex occurring Exception
     * @return Formatted LogMessage
     * {@link LogLevel}
     * */
    public  String formatMessage(LogLevel logLevel, String loggerName, String message, Exception ex) {
        return formatMessage(logLevel, loggerName, Arrays.asList(message), ex);
    }

    /**
     * @return the given Messages List as one combined String with new line chars between the List entrys
     * */
    protected static String getMessageStr(List<String> messages) {
        String combined = "";
        for(int i = 0; i < messages.size(); i++) {
            String current = messages.get(i);
            combined += current;
            if(i + 1 != messages.size()) combined += "\n";
        }
        return combined;
    }

}
