package net.projectuniverse.general.commands;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.builder.Command;
import net.projectuniverse.general.AdminPerm;
import net.projectuniverse.general.config.configs.MessagesConfig;

public class CmdClearChat extends Command {

    private static final MessagesConfig config = MessagesConfig.getInstance();

    public CmdClearChat() {
        super("clearchat", "cc", "clear");

        addSyntax((sender, context) -> {
            Audience audience = Audiences.players(player -> !AdminPerm.has(player, AdminPerm.IGNORE_CHAT_CLEAR));
            String str = " ";
            for(int i = 0; i < 100; i++) {
                audience.sendMessage(Component.text(str));
                str += " ";
                if(i == 99) audience.sendMessage(Component.text(MessagesConfig.CLEAR_CHAT_DONE.getValue()));
            }
        });
    }
}
