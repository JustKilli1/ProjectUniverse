package net.projectuniverse.general.money_system.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.money_system.PlayerMoneySystemUtils;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.UniCurrency;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.Optional;

import static net.projectuniverse.general.money_system.ModuleMoneySystem.CURRENCY_ARG;

/**
 * Represents a command for displaying a player's money.
 * Extends the UniverseCommand class.
 */

public class CmdMoney extends UniverseCommand {


    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    /**
     * Creates a new instance of CmdMoney.
     *
     * @param sql The DBALMoney object used for database access.
     * @param dbHandler The DBHMoney object used for handling database operations.
     */
    public CmdMoney(DBALMoney sql, DBHMoney dbHandler) {
        super("money", "Shows how much money a Player has", "money [currency] (player-name)");
        this.sql = sql;
        this.dbHandler = dbHandler;

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player senderPlayer)) return;

            Optional<UniCurrency> currencyOpt = PlayerMoneySystemUtils.getTransactionCurrency(sender, context.get(CURRENCY_ARG));
            if(currencyOpt.isEmpty()) return;
            UniCurrency currency = currencyOpt.get();

            PlayerPurse playerPurse = dbHandler.getPlayerPurse(senderPlayer, currency).orElse(new PlayerPurse(senderPlayer, UniCurrency.UNI, sql));
            sendPlayerMoney(sender, playerPurse, currency);
        }, CURRENCY_ARG);

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;

            Optional<Player> targetPlayerOpt = getPlayerFromArgument(sender, context);
            if(targetPlayerOpt.isEmpty()) return;
            Player targetPlayer = targetPlayerOpt.get();

            Optional<UniCurrency> currencyOpt = PlayerMoneySystemUtils.getTransactionCurrency(sender, context.get(CURRENCY_ARG));
            if(currencyOpt.isEmpty()) return;
            UniCurrency currency = currencyOpt.get();

            Optional<PlayerPurse> targetPurseOpt = PlayerMoneySystemUtils.getTargetPurse(dbHandler, sender, targetPlayer, currency);
            if(targetPurseOpt.isEmpty()) return;
            PlayerPurse targetPurse = targetPurseOpt.get();

            sendPlayerMoney(sender, targetPurse, currency);

        }, CURRENCY_ARG, PLAYER_ARGUMENT);
    }

    /**
     * Sends a message to the specified command sender with information about a player's money.
     *
     * @param sender The command sender to send the message to.
     * @param playerPurse The PlayerPurse object containing the player's money information.
     * @param currency The currency of the player's money.
     */
    private void sendPlayerMoney(CommandSender sender, PlayerPurse playerPurse, UniCurrency currency) {
        Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.SHOW_PLAYER_MONEY.clone()
                .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(String.valueOf(playerPurse.getPlayerName())))
                .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(playerPurse.getAmount())))
                .setConfigParamValue(MessagesParams.CURRENCY.clone().setValue(currency.toString() + (playerPurse.getAmount() > 1 ? "s" : "")))
                .setConfigParamValue(MessagesParams.CURRENCY_SYMBOL.clone().setValue(currency.getSymbol()))
                .getValue());
    }

}
