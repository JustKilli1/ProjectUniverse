package net.projectuniverse.general.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class LoggingUtils {

    /**
     * Utils Method for Logger to get the StackTrace of an Exception as String
     * @param ex Exception that gets turned into a String
     * @return StackTrace from Exception as String
     * */
    public static String getStackTraceAsStr(Exception ex) {
        if(ex == null) return null;
        StringWriter strWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(strWriter);
        ex.printStackTrace(printWriter);
        return strWriter.toString();
    }

    public static String getMessageStr(List<String> messages, boolean oneLine) {
        StringBuilder combined = new StringBuilder();
        for(int i = 0; i < messages.size(); i++) {
            combined.append(messages.get(i));
            if(i + 1 != messages.size() && !oneLine) combined.append(System.lineSeparator());
            if(oneLine) combined.append(" ");
        }
        return combined.toString();
    }


}
