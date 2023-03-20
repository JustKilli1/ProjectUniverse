package net.projectuniverse.general.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

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

}
