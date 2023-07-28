package net.projectuniverse.general.cactus_clicker;

import net.minestom.server.instance.InstanceContainer;

import java.util.List;

public interface IBlockCounter<T> {

    List<T> count(InstanceContainer instance);

}
