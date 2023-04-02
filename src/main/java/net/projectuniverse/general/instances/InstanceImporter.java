package net.projectuniverse.general.instances;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstanceImporter {

    /**
     * Imports a World with the Given path
     * @param dirPath Path of the world that gets imported
     * */
    public static InstanceContainer importWorld(@NotNull String dirPath) {
        InstanceContainer container = MinecraftServer.getInstanceManager().createInstanceContainer(new AnvilLoader(dirPath));
        container.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));
        return container;
    }

    /**
     * Imports all Worlds from the given directory
     * @param dirPath Path of the Directory where the worlds are located
     * */
    public static List<InstanceContainer> importWorlds(String dirPath) {
        List<InstanceContainer> containerList = new ArrayList<>();
        File dirFile = new File(dirPath);
        Arrays.stream(dirFile.listFiles())
                .filter(file -> file.isDirectory())
                .forEach(file -> containerList.add(importWorld(file.getName()))
                );
        return containerList;
    }

    /**
     * Imports all Worlds from the standard directory "instances"
     * */
    public static List<InstanceContainer> importWorlds() {
        return importWorlds("instances");
    }


}
