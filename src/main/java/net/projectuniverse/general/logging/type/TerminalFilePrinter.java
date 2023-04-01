package net.projectuniverse.general.logging.type;

import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.logging.LoggingUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TerminalFilePrinter extends FilePrinter{

    public TerminalFilePrinter(LogCategory logCategory, String logFileName) {
        super(logCategory, logFileName);
    }
    public String format(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null && message.size() == 0 ? "" : LoggingUtils.getMessageStr(message, false);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + currentDateTime.format(formatter) + "]" +
                messageMSG;
    }
}
