package net.projectuniverse.general.money_system.commands;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.List;

public class CmdBalanceTop extends UniverseCommand {


    private static final int max_displayable_player = 10;
    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    public CmdBalanceTop(DBALMoney sql, DBHMoney dbHandler) {
        super("baltop", "Shows the top 10 Richest Players on the Server", "baltop");
        this.sql = sql;
        this.dbHandler = dbHandler;

        setDefaultExecutor(new DefaultExecutor(getUsage()));

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player senderPlayer)) return;

            PlayerPurse.Currency currency = PlayerPurse.Currency.UNIS;

            StringBuilder message = new StringBuilder("[Top 10 Richest Players]\n");
            List<PlayerPurse> richestPlayers = dbHandler.getRichestPlayers(currency, max_displayable_player);
            for(int i = 1; i <= richestPlayers.size(); i++) {
                PlayerPurse purse = richestPlayers.get(i - 1);
                message.append(i)
                        .append(". ")
                        .append(purse.getPlayerName())
                        .append(": ")
                        .append(purse.getAmount())
                        .append(" ")
                        .append(currency.getDisplayName())
                        .append(purse.getAmount() > 1 ? "s" : "");
                if(i < richestPlayers.size()) message.append("\n");
            }
            Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, message.toString());
        });

    }

}
