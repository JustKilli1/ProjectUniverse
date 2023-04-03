package net.projectuniverse.modules.tower_defence.game;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.projectuniverse.general.instance.InstanceHandler;

public class GameManager {
    private static final int startGold = 20;
    private Player player;
    private InstanceContainer instance;
    private GameState state;
    private int gold = startGold;
    private TowerShop shop;

    public GameManager(Player player) {
        this.player = player;
        instance = InstanceHandler.TOWER_DEFENCE_ARENA.copy();
        InstanceManager instanceManager = new InstanceManager();
        instanceManager.registerInstance(instance);
        player.setInstance(instance, new Pos(4, 71, 19));
        startGame();
    }

    private void startGame() {
        state = GameState.SHOPPING;
        shop = new TowerShop(this);
        shop.openShop();
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
/*        switch(this.state) {
            case BUILDING ->
        }*/
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public boolean reduceGold(int amount) {
        if(gold < amount) return false;
        gold -= amount;
        return true;
    }

    public Player getPlayer() {
        return player;
    }

    public int getGold() {
        return gold;
    }
}
