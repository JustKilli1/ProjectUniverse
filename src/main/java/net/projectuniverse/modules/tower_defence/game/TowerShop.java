package net.projectuniverse.modules.tower_defence.game;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.PlayerInventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.concurrent.ThreadLocalRandom;

public class TowerShop {

    public static final ItemStack TOWER_BOW = ItemStack.of(Material.SKELETON_SPAWN_EGG).withDisplayName(Component.text("Bogen Turm"));
    public static final ItemStack TOWER_SWORD = ItemStack.of(Material.ZOMBIE_SPAWN_EGG).withDisplayName(Component.text("Schwert Turm"));
    public static final ItemStack TOWER_EXPLOSION = ItemStack.of(Material.CREEPER_SPAWN_EGG).withDisplayName(Component.text("Explosions Turm"));
    public static final ItemStack TOWER_MAGE = ItemStack.of(Material.WITCH_SPAWN_EGG).withDisplayName(Component.text("Magier Turm"));
    private PlayerInventory playerInv;
    private GameManager gameManager;


    public TowerShop(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void openShop() {
        Player player = gameManager.getPlayer();
        playerInv = player.getInventory();
        player.getInventory().clear();
        ItemStack[] shopItems = getShopTower();
        for(int i = 0; i < shopItems.length; i++) {
            player.getInventory().setItemStack(i, shopItems[i]);
        }
    }

    public ItemStack[] getShopTower() {
        ItemStack[] shopItems = new ItemStack[9];
        shopItems[0] = ItemStack.AIR;
        shopItems[8] = ItemStack.AIR;
        for(int i = 1; i < 8; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(101);
            ItemStack itemStack;
            if(randomNum > 0 && randomNum < 25) itemStack = TOWER_BOW;
            else if(randomNum > 24 && randomNum < 50) itemStack = TOWER_SWORD;
            else if(randomNum > 49 && randomNum < 75) itemStack = TOWER_EXPLOSION;
            else itemStack = TOWER_MAGE;
            shopItems[i] = itemStack;
        }
        return shopItems;
    }

    public void closeShop() {
        gameManager.setState(GameState.BUILDING);
    }

}
