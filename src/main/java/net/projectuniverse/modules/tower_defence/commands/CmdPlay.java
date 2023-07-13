package net.projectuniverse.modules.tower_defence.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.projectuniverse.general.arena_system.Arena;
import net.projectuniverse.general.arena_system.ArenaManager;
import net.projectuniverse.general.gui.inventories.SingletonInventory;
import net.projectuniverse.general.gui.items.ClickAction;
import net.projectuniverse.general.instance.InstanceHandler;
import net.projectuniverse.modules.tower_defence.entities.enemies.TargetEntity;
import net.projectuniverse.modules.tower_defence.entities.enemies.TestEnemy;

public class CmdPlay  extends Command {

    public CmdPlay() {
        super("play");
        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;
            Player player = (Player) sender;

/*            TargetEntity target = new TargetEntity();
            TestEnemy enemy = new TestEnemy(target);
            target.setInstance(player.getInstance(), new Pos(14,71,13));
            enemy.setInstance(player.getInstance(), new Pos(14,71,-20));*/

/*            SingletonInventory inventory = new SingletonInventory(InventoryType.CHEST_1_ROW, "Test", false);
            inventory.setItem(4, ItemStack.of(Material.CHEST), new ClickAction() {
                @Override
                public void click(Player player) {
                    player.sendMessage("Hat geklappt lul");
                }
            });
            inventory.open(player);*/
            Arena arena = new Arena(player, InstanceHandler.TOWER_DEFENCE_ARENA);
            ArenaManager.register(player, arena);
        });
    }

}
