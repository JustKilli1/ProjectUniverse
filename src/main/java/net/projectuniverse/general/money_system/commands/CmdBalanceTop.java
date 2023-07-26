package net.projectuniverse.general.money_system.commands;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.money_system.PlayerMoneySystemUtils;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.UniCurrency;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.List;
import java.util.Optional;

import static net.projectuniverse.general.money_system.ModuleMoneySystem.CURRENCY_ARG;

/**
 * Represents a command that shows the top 10 richest players on the server.
 * Extends the UniverseCommand class.
 */

public class CmdBalanceTop extends UniverseCommand {


    private static final int MAX_DISPLAYABLE_PLAYER = 10;

    private final DBHMoney dbHandler;

    /**
     * Initializes a new instance of the CmdBalanceTop class.
     *
     * @param sql The DBALMoney object used for database access and querying.
     * @param dbHandler The DBHMoney object used for handling money-related operations.
     */
    public CmdBalanceTop(DBHMoney dbHandler) {
        super("baltop", "Shows the top 10 Richest Players on the Server", "baltop [currency]");
        this.dbHandler = dbHandler;

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;

            Optional<UniCurrency> currencyOpt = PlayerMoneySystemUtils.getTransactionCurrency(sender, context.get(CURRENCY_ARG));
            if(currencyOpt.isEmpty()) return;
            UniCurrency currency = currencyOpt.get();

            List<PlayerPurse> richestPlayers = dbHandler.getRichestPlayers(currency, MAX_DISPLAYABLE_PLAYER);
            Messenger.sendMessage(sender, MessageDesign.PLAYER_MESSAGE, buildBalanceTopComponent(richestPlayers, currency));
        }, CURRENCY_ARG);
    }

    /**
     * Builds the message for displaying the top richest players.
     *
     * @param richestPlayers A list of PlayerPurse objects representing the richest players.
     * @param currency The currency to display the player's amount in.
     * @return A string containing the formatted message for displaying the top richest players.
     */
    private String buildBalanceTopComponent(List<PlayerPurse> richestPlayers, UniCurrency currency) {
        StringBuilder message = new StringBuilder(String.format("[Top %d Richest Players]\n", MAX_DISPLAYABLE_PLAYER));
        for(int i = 1; i <= richestPlayers.size(); i++) {
                PlayerPurse purse = richestPlayers.get(i - 1);
                message.append(i)
                        .append(". ")
                        .append(purse.getPlayerName())
                        .append(": ")
                        .append(purse.getAmount())
                        .append(" ")
                        .append(currency)
                        .append(purse.getAmount() > 1 ? "s" : "");
                if(i < richestPlayers.size()) message.append("\n");
        }
        return message.toString();
    }
}
