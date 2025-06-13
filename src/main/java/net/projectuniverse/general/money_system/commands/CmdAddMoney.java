package net.projectuniverse.general.money_system.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.money_system.PlayerMoneySystemUtils;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.TransactionResult;
import net.projectuniverse.general.money_system.UniCurrency;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.Optional;

import static net.projectuniverse.general.money_system.ModuleMoneySystem.CURRENCY_ARG;

/**
 * The CmdAddMoney class is a command that adds money to a specified player's purse.
 */

public class CmdAddMoney extends UniverseCommand {

    private final ILogger logger;
    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    /**
     * Adds Money to the Specified Player's purse.
     *
     * @param logger     The ILogger object used for logging messages.
     * @param sql        The DBALMoney object used for database access.
     * @param dbHandler  The DBHMoney object used for handling player purse data.
     */
    public CmdAddMoney(ILogger logger, DBALMoney sql, DBHMoney dbHandler) {
        super("addmoney", "Adds Money to the Specified Player purse", "addmoney [player-name] [amount] [currency]");
        this.logger = logger;
        this.sql = sql;
        this.dbHandler = dbHandler;

        addSyntax((sender, context) -> {
            Optional<Player> playerOpt = getPlayerFromArgument(sender, context);
            if(playerOpt.isEmpty()) return;
            Player targetPlayer = playerOpt.get();

            int amount = context.get(AMOUNT_ARGUMENT);

            Optional<UniCurrency> currencyOpt = PlayerMoneySystemUtils.getTransactionCurrency(sender, context.get(CURRENCY_ARG));
            if(currencyOpt.isEmpty()) return;
            UniCurrency currency = currencyOpt.get();

            Optional<PlayerPurse> targetPurseOpt = PlayerMoneySystemUtils.getTargetPurse(dbHandler, sender, targetPlayer, currency);
            if(targetPurseOpt.isEmpty()) return;
            PlayerPurse targetPurse = targetPurseOpt.get();

            TransactionResult result = targetPurse.addMoney(amount);
            handleTransactionResult(sender, targetPlayer, result, currency, amount);

        }, PLAYER_ARGUMENT, AMOUNT_ARGUMENT, CURRENCY_ARG);

    }

    /**
     * Handles the transaction result after adding money to the specified player's purse.
     *
     * @param sender        The CommandSender object representing the command sender.
     * @param targetPlayer  The Player object representing the target player.
     * @param result        The TransactionResult Object representing the result of the transaction.
     * @param currency      The UniCurrency object representing the currency used for the transaction.
     * @param amount        The amount of money added to the player's purse.
     */
    private void handleTransactionResult(CommandSender sender, Player targetPlayer, TransactionResult result, UniCurrency currency, int amount) {
        switch(result) {
            case SUCCESS -> logger.log(LogLevel.INFO, String.format("The Player %s got %d %ss from the Server", targetPlayer.getUsername(), amount, currency));
            case FAILED -> {
                logger.log(LogLevel.WARN, String.format("Could not give the Player %s %d %ss", targetPlayer.getUsername(), amount, currency));
                return;
            }
        }
        Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.ADD_SERVER_MONEY.clone()
                .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(amount)))
                .setConfigParamValue(MessagesParams.CURRENCY.clone().setValue(currency.getDisplayName() + (amount > 1 ? "s" : "")))
                .getValue());

    }
}
