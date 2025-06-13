package net.projectuniverse.general.player_system;

import net.projectuniverse.general.money_system.PlayerPurse;

public record UniPlayer(int id, String uuid, String name, PlayerPurse purse) {

}
