package net.projectuniverse.general.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * LoggingUtils is a utility class that provides methods for logging purposes.
 * This class contains methods to convert an exception stack trace to a string and to concatenate multiple messages into a single string.
 */

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

    /**
     * Utils Method to combine a list of strings into a single string message.
     * @param messages A list of string messages to be combined.
     * @param oneLine Flag indicating whether the messages should be combined into a single line or not.
     * @return A string containing the combined messages.
     */
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
