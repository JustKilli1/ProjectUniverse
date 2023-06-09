package net.projectuniverse.general.commands;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.gui.inventories.TestInventory;


import java.util.ArrayList;
import java.util.List;

public class CmdTest extends UniverseCommand{
    public CmdTest() {
        super("test", "A Test Command to Test things", "/test");

        addSyntax((sender, context) -> {
           Player player = (Player) sender;
           List<Integer> values = new ArrayList<>();
            for(int i = 0; i < 20; i++) {
                values.add(i);
            }
           TestInventory inv = new TestInventory(values);
            inv.buildPage(2);
            inv.open(player);
        });

    }
}
