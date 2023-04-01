package net.projectuniverse.general.config.configs;

import net.projectuniverse.general.config.ConfigManager;
import net.projectuniverse.general.config.ConfigValue;

public class MessagesConfig extends ConfigManager {

    public static final ConfigValue NO_PERMISSION = new ConfigValue("generell.no_permission", "You don't have Permission to use this Command");
    public static final ConfigValue CLEAR_CHAT_DONE = new ConfigValue("chat.clear_chat.done", "Chat has been cleared.");
    public static final ConfigValue MUTE_CHAT_CHANGED = new ConfigValue("chat.mute_chat", "Global mute changed to #muted#", MessagesParams.MUTED);
    public static final ConfigValue TEAM_CHAT_ACTIVATED = new ConfigValue("chat.team_chat.activated", "Team Chat activated");
    public static final ConfigValue TEAM_CHAT_DEACTIVATED = new ConfigValue("chat.team_chat.deactivated", "Team Chat deactivated");
    public static final ConfigValue CHAT_MUTED = new ConfigValue("chat.chat_muted", "The chat is muted at the moment.");
    public static final ConfigValue KICK_PLAYER_NOT_FOUND = new ConfigValue("punishment_system.kick.player_not_found", "Could not find a Player with the Name #player#", MessagesParams.PLAYER);
    public static final ConfigValue KICK_YOURSELF = new ConfigValue("punishment_system.kick.kick_yourself", "You cannot kick yourself");
    public static final ConfigValue CANT_KICK_PLAYER = new ConfigValue("punishment_system.kick.cannot_kick_player", "You don't have Permission to kick this Player");
    public static final ConfigValue KICK_SUCCESS = new ConfigValue("punishment_system.kick.kick_success", "Player #player# kicked for \"#reason#\"", MessagesParams.PLAYER, MessagesParams.PUNISHMENT_REASON);
    private static final MessagesConfig instance = new MessagesConfig();


    private MessagesConfig() {
        super("messages_player");
        createDefaultConfig();
    }

    public static MessagesConfig getInstance() {
        return instance;
    }

    private void createDefaultConfig() {

        addDefault(CLEAR_CHAT_DONE);
        addDefault(MUTE_CHAT_CHANGED);
        addDefault(KICK_PLAYER_NOT_FOUND);
        addDefault(KICK_YOURSELF);
        addDefault(KICK_SUCCESS);
        addDefault(CHAT_MUTED);

    }
}