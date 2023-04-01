package net.projectuniverse.general.logging.type;

import net.projectuniverse.general.logging.LogLevel;

import java.util.List;

public interface IOutputPrinter {

    void print(LogLevel level, List<String> message);
    void print(LogLevel level, String message);
    String format(List<String> message);
    String format(String message);

}
