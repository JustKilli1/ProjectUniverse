package net.projectuniverse.general.logging.type;


import net.projectuniverse.general.logging.LogCategory;
import net.projectuniverse.general.logging.LogLevel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @deprecated New Implementation: {@link TerminalFilePrinter}
 * @since 0.4.7-dev
 * */
public class LoggerTypeTerminalFile extends LoggerTypeFile{

    public LoggerTypeTerminalFile(LogCategory logCategory, String logFileName) {
        super(logCategory, logFileName);
    }

    @Override
    public String formatMessage(LogLevel logLevel, String loggerName, List<String> message, Exception ex) {
        String messageMSG = message == null && message.size() == 0 ? "" : getMessageStr(message);
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "[" + currentDateTime.format(formatter) + "]" +
                messageMSG;
    }

}
