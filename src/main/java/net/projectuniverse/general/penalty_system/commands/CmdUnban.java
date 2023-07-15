package net.projectuniverse.general.penalty_system.commands;

import net.minestom.server.command.builder.arguments.ArgumentType;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.penalty_system.database.DBHPenaltySystem;

/**
 * The CmdUnban class represents a command for unbanning a player.
 * It extends the UniverseCommand class.
 */

public class CmdUnban extends UniverseCommand {

    private DBHPenaltySystem dbHandler;

    /**
     * Command to unban a player.
     *
     * @param dbHandler The DBHPenaltySystem instance used for accessing the penalty system database.
     */
    public CmdUnban(DBHPenaltySystem dbHandler) {
        super("unban", "Unbans a player from the server", "unban [PlayerName]" );
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
