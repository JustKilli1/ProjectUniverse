package net.projectuniverse.general;

import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import net.projectuniverse.general.config.configs.PlayerMessagesConfig;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;

public enum AdminPerm {

    IGNORE_CHAT_CLEAR("chat.ignore_chat_clear"),
    IGNORE_CHAT_MUTE("chat.ignore_chat_mute"),
    IGNORE_KICK("punishment_system.ignore_kick"),
    USE_TEAM_CHAT("chat.use_team_chat")

    ;
    private static final String prefix = "projectuniverse.admin.";
    private String perm;

    AdminPerm(String perm) {
        this.perm = perm;
    }

    public static boolean has(Player target, AdminPerm perm) {
        boolean hasPerm = target.hasPermission(new Permission(perm.getPerm()));
        if(!hasPerm) Messenger.sendMessage(target, MessageDesign.PLAYER_MESSAGE, PlayerMessagesConfig.NO_PERMISSION.getValue());
        return hasPerm;
    }

    public String getPerm() {
        return prefix + perm;
    }
}
