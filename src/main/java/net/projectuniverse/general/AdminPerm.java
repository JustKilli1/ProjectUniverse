package net.projectuniverse.general;

import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

public enum AdminPerm {

    IGNORE_CHAT_CLEAR("chat.ignore_chat_clear"),
    IGNORE_CHAT_MUTE("chat.ignore_chat_mute"),
    USE_TEAM_CHAT("chat.use_team_chat")

    ;
    private static final String prefix = "projectuniverse.admin.";
    private String perm;

    AdminPerm(String perm) {
        this.perm = perm;
    }

    public static boolean hasPerm(Player target, AdminPerm perm) {
        return target.hasPermission(new Permission(perm.getPerm()));
    }

    public String getPerm() {
        return prefix + perm;
    }
}
