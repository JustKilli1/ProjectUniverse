package net.projectuniverse.general.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.BlockManager;
import net.minestom.server.instance.block.rule.vanilla.AxisPlacementRule;
import net.minestom.server.instance.block.rule.vanilla.RedstonePlacementRule;
import net.minestom.server.timer.Scheduler;
import net.minestom.server.timer.TaskSchedule;
import net.projectuniverse.general.cactus_clicker.Cactus;
import net.projectuniverse.general.cactus_clicker.instance_management.CactusCounter;
import net.projectuniverse.general.cactus_clicker.instance_management.InstanceManagement;
import net.projectuniverse.general.gui.inventories.TestInventory;
import net.projectuniverse.general.instance.InstanceImporter;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CmdTest extends UniverseCommand{

    private InstanceContainer instance, instance2;
    private List<Integer> testValues = new ArrayList<>();

    /**
     * Represents a test command that can be used to test things.
     *
     * This command opens a test inventory for the player.
     */
    public CmdTest() {
        super("test", "A Test Command to Test things", "/test");

        var testIntArg = ArgumentType.Integer("test-int");

        addSyntax((sender, context) -> {
           Player player = (Player) sender;
           List<Integer> values = new ArrayList<>();
            for(int i = 0; i < 20; i++) {
                values.add(i);
            }
           TestInventory inv = new TestInventory(values);
            inv.buildPage(2);
            inv.open(player);

            instance = InstanceImporter.importWorld("instances/cactus-clicker/cactus-clicker-island");
            instance2 = instance.copy();
            MinecraftServer.getInstanceManager().registerInstance(instance2);
            player.setInstance(instance2);

        });

        addSyntax((sender, context) -> {
           Player player = (Player) sender;
            CactusCounter counter = new CactusCounter();
            List<Cactus> cacti = counter.count(InstanceManagement.getIsland(player).getInstance());
            for(Cactus cactus : cacti) {
                printCactus(cactus);
            }
            System.out.println("Mit Kaktussen \n\n\n\n\n");
            for(Cactus cactus : cacti) {
                if(cactus.getCactusHeight() <= 0) continue;
                printCactus(cactus);
            }
            System.out.println(cacti);
/*            Scheduler scheduler = MinecraftServer.getSchedulerManager();
            scheduler.submitTask(() -> {
                System.out.println("Running directly and then every second!");
                return TaskSchedule.seconds(1);
            });*/
        }, testIntArg);

    }

    private Integer hasInt(Integer i) {
        for(Integer testValue : testValues) {
            if(testValue.equals(i)) {
                return testValue;
            }
        }
        return null;
    }

    private void printCactus(Cactus cactus) {
        System.out.printf("--------------------[%s]--------------------%n", cactus.getCactusBasePos());
        System.out.printf("Base Block: %s%n", cactus.getCactusBaseBlock());
        System.out.printf("Height: %s%n", cactus.getCactusHeight());
        System.out.printf("Highest Block pos: %s%n", cactus.getHeighestCactusPos());
        System.out.printf("Lowest Block pos: %s%n", cactus.getLowestCactusPos());
    }

}
