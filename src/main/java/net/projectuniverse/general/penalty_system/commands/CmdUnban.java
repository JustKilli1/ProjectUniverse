package net.projectuniverse.general.penalty_system.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.penalty_system.database.DBHPenaltySystem;

public class CmdUnban extends Command {

    private DBHPenaltySystem dbHandler;

    public CmdUnban(DBHPenaltySystem dbHandler) {
        super("unban");
        this.dbHandler = dbHandler;

        var playerArg = ArgumentType.String("player-name");

        addSyntax((sender, context) -> {
            String playerName = context.get(playerArg);
            if(!dbHandler.playerHasActivePunishment(playerName)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_NOT_BANNED.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerName))
                        .getValue());
                return;
            }
            if(dbHandler.removePlayerPunishment(playerName)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.UNBAN_SUCCESS.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerName))
                        .getValue());
            } else {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.UNBAN_FAILED.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
            }
        }, playerArg);
    }

}
