package net.projectuniverse.general.config.configs;

import net.projectuniverse.general.config.ConfigParam;

/**
 * This class represents the configuration parameters for messages.
 * Each parameter consists of a name and a default value.
 */

public class MessagesParams {

    public static final ConfigParam MUTED = new ConfigParam("muted", "Muted");
    public static final ConfigParam PLAYER = new ConfigParam("player", "Player");
    public static final ConfigParam PUNISHMENT_REASON = new ConfigParam("reason", "Reason");
    public static final ConfigParam GAME_MODE = new ConfigParam("gamemode", "Game Mode");
    public static final ConfigParam AMOUNT = new ConfigParam("amount", "0");
    public static final ConfigParam CURRENCY = new ConfigParam("currency", "Unis");
    public static final ConfigParam CURRENCY_SYMBOL = new ConfigParam("currency-symbol", "€");

}
