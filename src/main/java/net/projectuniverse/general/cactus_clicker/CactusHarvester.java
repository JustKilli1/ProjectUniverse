package net.projectuniverse.general.cactus_clicker;

import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.Scheduler;
import net.minestom.server.timer.TaskSchedule;
import net.projectuniverse.general.cactus_clicker.instance_management.InstanceManagement;

public class CactusHarvester {

    private static final int HARVEST_TIME = 10;

    public static void init() {
        Scheduler scheduler = MinecraftServer.getSchedulerManager();
        scheduler.submitTask(() -> {
            harvest();
            return TaskSchedule.seconds(HARVEST_TIME);
        });
    }

    private static void harvest() {
        InstanceManagement.getActiveInstances().forEach((player, island) -> {
            island.harvest();
            System.out.println("Harvested " + island.getCactusCount() + " cacti from " + player.getUsername());
        });
    }

}
