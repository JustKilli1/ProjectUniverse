package net.projectuniverse.general.config.configs;

import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;

public class MessagesConfig extends ConfigManager {

    public static final ConfigValue NO_PERMISSION = new ConfigValue("generell.no_permission", "You don't have Permission to use this Command");
    public static final ConfigValue PLAYER_NOT_FOUND = new ConfigValue("generell.player_not_found", "Could not find a Player with the Name " + MessagesParams.PLAYER.getName(), MessagesParams.PLAYER);
    public static final ConfigValue PLAYER_JOINED = new ConfigValue("generell.player_join_message", "Player " + MessagesParams.PLAYER.getName() + " joined", MessagesParams.PLAYER);
    public static final ConfigValue GAME_MODE_CHANGED = new ConfigValue("generell.game_mode.changed", "GameMode changed to " + MessagesParams.GAME_MODE.getName() + " joined", MessagesParams.GAME_MODE);
    public static final ConfigValue GAME_MODE_NOT_FOUND = new ConfigValue("generell.game_mode.not_found", "Could not find Gamemode with the id " + MessagesParams.GAME_MODE.getName(), MessagesParams.GAME_MODE);
    public static final ConfigValue CLEAR_CHAT_DONE = new ConfigValue("chat.clear_chat.done", "Chat has been cleared.");
    public static final ConfigValue MUTE_CHAT_CHANGED = new ConfigValue("chat.mute_chat", "Global mute changed to " + MessagesParams.MUTED.getName(), MessagesParams.MUTED);
    public static final ConfigValue TEAM_CHAT_ACTIVATED = new ConfigValue("chat.team_chat.activated", "Team Chat activated");
    public static final ConfigValue TEAM_CHAT_DEACTIVATED = new ConfigValue("chat.team_chat.deactivated", "Team Chat deactivated");
    public static final ConfigValue CHAT_MUTED = new ConfigValue("chat.chat_muted", "The chat is muted at the moment.");
    public static final ConfigValue KICK_YOURSELF = new ConfigValue("punishment_system.kick.kick_yourself", "You cannot kick yourself");
    public static final ConfigValue BAN_YOURSELF = new ConfigValue("punishment_system.ban.ban_yourself", "You cannot ban yourself");
    public static final ConfigValue UNBAN_YOURSELF = new ConfigValue("punishment_system.ban.unban_yourself", "You cannot unban yourself");
    public static final ConfigValue CANT_KICK_PLAYER = new ConfigValue("punishment_system.kick.cannot_kick_player", "You don't have Permission to kick this Player");
    public static final ConfigValue KICK_SUCCESS = new ConfigValue("punishment_system.kick.kick_success", "Player " + MessagesParams.PLAYER.getName() + " kicked for " + MessagesParams.PUNISHMENT_REASON.getName(), MessagesParams.PLAYER, MessagesParams.PUNISHMENT_REASON);
    public static final ConfigValue BAN_SUCCESS = new ConfigValue("punishment_system.ban.ban_success", "Player " + MessagesParams.PLAYER.getName() + " banned for " + MessagesParams.PUNISHMENT_REASON.getName(), MessagesParams.PLAYER, MessagesParams.PUNISHMENT_REASON);
    public static final ConfigValue UNBAN_SUCCESS = new ConfigValue("punishment_system.ban.unban_success", "Player " + MessagesParams.PLAYER.getName() + " unbanned", MessagesParams.PLAYER);
    public static final ConfigValue PLAYER_NOT_BANNED = new ConfigValue("punishment_system.ban.player_not_banned", "Player " + MessagesParams.PLAYER.getName() + " is not banned", MessagesParams.PLAYER);
    public static final ConfigValue BAN_FAILED = new ConfigValue("punishment_system.ban.ban_failed", "Player " + MessagesParams.PLAYER.getName() + " could not get banned for " + MessagesParams.PUNISHMENT_REASON.getName(), MessagesParams.PLAYER, MessagesParams.PUNISHMENT_REASON);
    public static final ConfigValue UNBAN_FAILED = new ConfigValue("punishment_system.ban.unban_failed", "Player " + MessagesParams.PLAYER.getName() + " could not get unbanned", MessagesParams.PLAYER);
    private static final MessagesConfig instance = new MessagesConfig();


    private MessagesConfig() {
        super("messages_player");
        createDefaultConfig();
    }

    public static MessagesConfig getInstance() {
        return instance;
    }

    private void createDefaultConfig() {

        addDefault(NO_PERMISSION);
        addDefault(PLAYER_NOT_FOUND);
        addDefault(PLAYER_JOINED);
        addDefault(GAME_MODE_CHANGED);
        addDefault(GAME_MODE_NOT_FOUND);
        addDefault(CLEAR_CHAT_DONE);
        addDefault(MUTE_CHAT_CHANGED);
        addDefault(TEAM_CHAT_ACTIVATED);
        addDefault(TEAM_CHAT_DEACTIVATED);
        addDefault(CHAT_MUTED);
        addDefault(KICK_YOURSELF);
        addDefault(BAN_YOURSELF);
        addDefault(UNBAN_YOURSELF);
        addDefault(CANT_KICK_PLAYER);
        addDefault(KICK_SUCCESS);
        addDefault(BAN_SUCCESS);
        addDefault(UNBAN_SUCCESS);
        addDefault(PLAYER_NOT_BANNED);
        addDefault(BAN_FAILED);
        addDefault(UNBAN_FAILED);

    }
}
