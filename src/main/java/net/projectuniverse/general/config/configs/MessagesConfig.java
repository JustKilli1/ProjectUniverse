package net.projectuniverse.general.config.configs;

import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;
import net.projectuniverse.general.logging.LogLevel;

import java.lang.reflect.Field;

/**
 * Represents a class for managing message configurations.
 * Extends the {@link ConfigManager} class.
 */

public class MessagesConfig extends ConfigManager {

    public static final ConfigValue NO_PERMISSION = new ConfigValue("generell.no_permission", "You don't have Permission to use this Command");
    public static final ConfigValue PLAYER_NOT_FOUND = new ConfigValue("generell.player_not_found", "Could not find a Player with the Name " + MessagesParams.PLAYER.getName(), MessagesParams.PLAYER);
    public static final ConfigValue PLAYER_JOINED = new ConfigValue("generell.player_join_message", "Player " + MessagesParams.PLAYER.getName() + " joined", MessagesParams.PLAYER);
    public static final ConfigValue GAME_MODE_CHANGED = new ConfigValue("generell.game_mode.changed", "GameMode changed to " + MessagesParams.GAME_MODE.getName(), MessagesParams.GAME_MODE);
    public static final ConfigValue GAME_MODE_NOT_FOUND = new ConfigValue("generell.game_mode.not_found", "Could not find Gamemode with the id " + MessagesParams.GAME_MODE.getName(), MessagesParams.GAME_MODE);
    public static final ConfigValue TELEPORT_TO_YOURSELF = new ConfigValue("generell.teleport.teleport_yourself", "You cannot teleport to yourself");
    public static final ConfigValue TELEPORT_SUCCESS = new ConfigValue("generell.teleport.teleport_success", "You teleported to " + MessagesParams.PLAYER.getName(), MessagesParams.PLAYER);
    public static final ConfigValue CLEAR_CHAT_DONE = new ConfigValue("chat.clear_chat.done", "Chat has been cleared.");
    public static final ConfigValue MUTE_CHAT_CHANGED = new ConfigValue("chat.mute_chat", "Global mute changed to " + MessagesParams.MUTED.getName(), MessagesParams.MUTED);
    public static final ConfigValue TEAM_CHAT_ACTIVATED = new ConfigValue("chat.team_chat.activated", "Team Chat activated");
    public static final ConfigValue TEAM_CHAT_DEACTIVATED = new ConfigValue("chat.team_chat.deactivated", "Team Chat deactivated");
    public static final ConfigValue CHAT_MUTED = new ConfigValue("chat.chat_muted", "The chat is muted at the moment.");
    public static final ConfigValue KICK_YOURSELF = new ConfigValue("punishment_system.kick.kick_yourself", "You cannot kick yourself");
    public static final ConfigValue BAN_YOURSELF = new ConfigValue("punishment_system.ban.ban_yourself", "You cannot ban yourself");
    public static final ConfigValue CANT_KICK_PLAYER = new ConfigValue("punishment_system.kick.cannot_kick_player", "You don't have Permission to kick this Player");
    public static final ConfigValue KICK_SUCCESS = new ConfigValue("punishment_system.kick.kick_success", "Player " + MessagesParams.PLAYER.getName() + " kicked for " + MessagesParams.PUNISHMENT_REASON.getName(), MessagesParams.PLAYER, MessagesParams.PUNISHMENT_REASON);
    public static final ConfigValue BAN_SUCCESS = new ConfigValue("punishment_system.ban.ban_success", "Player " + MessagesParams.PLAYER.getName() + " banned for " + MessagesParams.PUNISHMENT_REASON.getName(), MessagesParams.PLAYER, MessagesParams.PUNISHMENT_REASON);
    public static final ConfigValue UNBAN_SUCCESS = new ConfigValue("punishment_system.ban.unban_success", "Player " + MessagesParams.PLAYER.getName() + " unbanned", MessagesParams.PLAYER);
    public static final ConfigValue PLAYER_NOT_BANNED = new ConfigValue("punishment_system.ban.player_not_banned", "Player " + MessagesParams.PLAYER.getName() + " is not banned", MessagesParams.PLAYER);
    public static final ConfigValue BAN_FAILED = new ConfigValue("punishment_system.ban.ban_failed", "Player " + MessagesParams.PLAYER.getName() + " could not get banned for " + MessagesParams.PUNISHMENT_REASON.getName(), MessagesParams.PLAYER, MessagesParams.PUNISHMENT_REASON);
    public static final ConfigValue UNBAN_FAILED = new ConfigValue("punishment_system.ban.unban_failed", "Player " + MessagesParams.PLAYER.getName() + " could not get unbanned", MessagesParams.PLAYER);
    public static final ConfigValue CANNOT_PAY_TO_YOURSELF = new ConfigValue("money_system.transactions.pay.cannot_pay_yourself", "You cannot Pay Money to yourself");
    public static final ConfigValue PAY_AMOUNT_GREATER_THAN_ZERO = new ConfigValue("money_system.transactions.pay.amount_greater_than_zero", "The Amount must be greater than 0");
    public static final ConfigValue NOT_ENOUGH_MONEY = new ConfigValue("money_system.transactions.pay.not_enough_money", "You don't have enough Money");
    public static final ConfigValue TRANSACTION_FAILED = new ConfigValue("money_system.transactions.pay.transaction_failed", "The Transaction Failed please Contact an Administrator.");
    public static final ConfigValue TRANSACTION_SUCCESS = new ConfigValue("money_system.transactions.pay.transaction_success", "The Transaction was Successful. You Transferred " + MessagesParams.AMOUNT + MessagesParams.CURRENCY_SYMBOL + " to " + MessagesParams.PLAYER, MessagesParams.AMOUNT, MessagesParams.CURRENCY_SYMBOL, MessagesParams.PLAYER);
    public static final ConfigValue TRANSACTION_SUCCESS_TARGET = new ConfigValue("money_system.transactions.pay.transaction_success_target", MessagesParams.PLAYER + " send you " + MessagesParams.AMOUNT + MessagesParams.CURRENCY_SYMBOL, MessagesParams.AMOUNT, MessagesParams.PLAYER, MessagesParams.CURRENCY_SYMBOL);
    public static final ConfigValue SHOW_PLAYER_MONEY = new ConfigValue("money_system.transactions.money.show_player_money", MessagesParams.CURRENCY + ": " + MessagesParams.AMOUNT + MessagesParams.CURRENCY_SYMBOL, MessagesParams.AMOUNT, MessagesParams.CURRENCY, MessagesParams.CURRENCY_SYMBOL);
    public static final ConfigValue SHOW_PLAYER_MONEY_TARGET = new ConfigValue("money_system.transactions.money.show_player_money_target", MessagesParams.PLAYER + ": " + MessagesParams.AMOUNT + MessagesParams.CURRENCY_SYMBOL, MessagesParams.AMOUNT, MessagesParams.PLAYER, MessagesParams.CURRENCY_SYMBOL);
    public static final ConfigValue ADD_SERVER_MONEY = new ConfigValue("money_system.transactions.money.add_server_money", "You received " + MessagesParams.AMOUNT + " " + MessagesParams.CURRENCY + " from the Server.", MessagesParams.AMOUNT, MessagesParams.CURRENCY);
    public static final ConfigValue REMOVE_SERVER_MONEY = new ConfigValue("money_system.transactions.money.remove_server_money", "The Server removed " + MessagesParams.AMOUNT + " " + MessagesParams.CURRENCY + " from your Account.", MessagesParams.AMOUNT, MessagesParams.CURRENCY);
    private static final MessagesConfig instance = new MessagesConfig();


    private MessagesConfig() {
        super("messages_player");
        createDefaultConfig();
    }

    public static MessagesConfig getInstance() {
        return instance;
    }

    private void createDefaultConfig() {
        for(Field declaredField : this.getClass().getDeclaredFields()) {
            try {
                Object value = declaredField.get(null);
                if(!(value instanceof ConfigValue configValue)) continue;
                addDefault(configValue);
            } catch(Exception ex) {
                configLogger.log(LogLevel.ERROR, "Could not Create Default Messages Config", ex);
            }
        }
    }
}
