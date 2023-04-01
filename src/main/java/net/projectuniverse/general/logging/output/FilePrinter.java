package net.projectuniverse.general.logging.type;

import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;
import net.projectuniverse.general.logging.files.FileHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilePrinter implements IOutputPrinter{
    public static final String LOG_DIRECTORY = "logs";
    public static final String LOG_FILE_PREFIX = "log";
    //FileHandler that Manages the LogFile
    private FileHandler fileHandler;
    protected LogCategory logCategory;

    public FilePrinter(LogCategory logCategory, String logFileName) {
        this.logCategory = logCategory;
        fileHandler = new FileHandler(LOG_DIRECTORY + "/" + logCategory.getFolderName() + "/" + LOG_FILE_PREFIX + "_" + logFileName);

    }

    @Override
    public void print(LogLevel level, List<String> message) {
        try {
            if(level != null && !level.equals(LogLevel.DEBUG)) if(!ILogger.debugMode) return;
            fileHandler.write(message, fileHandler.fileExists());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void print(LogLevel level, String message) {
        print(level, List.of(message));
    }

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

    @Override
    public String format(LogLevel logLevel, String loggerName, String message, Exception ex) {
        return format(logLevel, loggerName, List.of(message), ex);
    }
}
