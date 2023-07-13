package net.projectuniverse.general.commands;

import net.projectuniverse.general.server.Server;

public class CmdPerformance extends UniverseCommand{

     private static final long MEGABYTE = 1024L * 1024L;

    public CmdPerformance() {
        super("performance", "Shows the current Server Performance", "performance");


        addSyntax((sender, context) -> {
            // Get the Java runtimeObj
            Runtime runtimeObj = Runtime.getRuntime();
            // Run the garbage collector
            runtimeObj.gc();
            // Calculate the used memory
            long memory = runtimeObj.totalMemory() - runtimeObj.freeMemory();
            long runtime = (System.currentTimeMillis() - Server.START_TIME) / 60000;

            sender.sendMessage("Used Memory: " + bytesToMegabytes(memory));
            sender.sendMessage("Runtime: " + runtime + " minutes");
        });

    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

}
