package net.projectuniverse.general.money_system;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.Optional;

public class MoneySystemAPI {

    private DBHMoney dbHandler;

    public MoneySystemAPI(DBHMoney dbHandler) {
        this.dbHandler = dbHandler;
    }

    public Optional<PlayerPurse> getPlayerPurse(Player player, UniCurrency currency) {
        return dbHandler.getPlayerPurse(player, currency);
    }

}
