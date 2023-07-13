package net.projectuniverse.general.penalty_system.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.projectuniverse.base.ColorDesign;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.database.DBAccessLayer;
import net.projectuniverse.general.database.DBHandler;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.penalty_system.database.DBALPenaltySystem;
import net.projectuniverse.general.penalty_system.database.DBHPenaltySystem;

public class CmdBan extends Command {
    private DBALPenaltySystem sql;
    private DBHPenaltySystem dbHandler;

    public CmdBan(DBALPenaltySystem sql, DBHPenaltySystem dbHandler) {
        super("ban");
        this.sql = sql;
        this.dbHandler = dbHandler;


        setDefaultExecutor(new DefaultExecutor("/ban [PlayerName] [Reason...]"));

        var playerArg = ArgumentType.Entity("player-name");
        var reasonArg = ArgumentType.StringArray("reason");

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            Player player = playerFinder.findFirstPlayer(sender);
            if(player == null) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_NOT_FOUND.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
                return;
            }
            if(player.equals(sender)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.BAN_YOURSELF.getValue());
                return;
            }
            String[] reasonArray = context.get(reasonArg);
            StringBuilder reason = new StringBuilder("");
            for(String s : reasonArray) reason.append(s + " ");
            TextComponent textComponent = Component.text()
                    .content("You were banned from the Server for")
                    .color(TextColor.color(ColorDesign.getPunishmentColor()))
                    .appendNewline()
                    .append(Component.text().content(reason.toString()).color(TextColor.color(ColorDesign.getHighlight())).build())
                    .build();
            player.kick(textComponent);
            if(dbHandler.addPlayerPunishmentReason(player, reason.toString(), 999, 'y')) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.BAN_SUCCESS.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                        .setConfigParamValue(MessagesParams.PUNISHMENT_REASON.clone().setValue(reason.toString()))
                        .getValue());
            } else {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.BAN_FAILED.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(player.getUsername()))
                        .setConfigParamValue(MessagesParams.PUNISHMENT_REASON.clone().setValue(reason.toString()))
                        .getValue());
            }
        }, playerArg, reasonArg);
    }
}