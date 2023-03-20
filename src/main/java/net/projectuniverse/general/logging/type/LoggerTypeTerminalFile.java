package net.projectuniverse.general.logging.type;


import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoggerTypeTerminalFile extends LoggerTypeFile{

    public LoggerTypeTerminalFile(LogCategory logCategory, String logFileName) {
        super(logCategory, logFileName);
    }

    @Override
    public String formatMessage(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null ? "" : getMessageStr(message);
        String exceptionMSG = ex == null ? "" : "\nException: " + LoggingUtils.getStackTraceAsStr(ex);
        String level = logLevel == LogLevel.INFO ? "[" + logLevel + "]" : "Level: " + logLevel.getName() + "\n";
        String name = logLevel == LogLevel.INFO ? "[" + loggerName + "] " : "Logger Name: " + loggerName + "\n";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = logLevel == LogLevel.INFO ? "[" + formatter.format(LocalDateTime.now()) + "]" : "--------------------------------[" + formatter.format(LocalDateTime.now()) + "]--------------------------------\n";

        return  time +
                level +
                name +
                messageMSG +
                exceptionMSG;
    }

}
