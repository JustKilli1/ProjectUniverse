package net.projectuniverse.general.commands.warps;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;

public record Warp(int id, String name, Instance instance, Pos position) {
    @Override
    public Instance instance() {
        instance.toString();
        return instance;
    }
}
