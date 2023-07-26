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
 * CmdRemoveMoney is a class that represents a command to remove money from a player's purse.
 * It extends the UniverseCommand class.
 */

public class CmdRemoveMoney extends UniverseCommand {

    private final ILogger logger;
    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    /**
     * Removes Money from the Specified Player's purse.
     *
     * @param logger      the logger instance used for logging messages
     * @param sql         the SQL database abstraction layer for handling money
     * @param dbHandler   the database handler for managing player purses
     */
    public CmdRemoveMoney(ILogger logger, DBALMoney sql, DBHMoney dbHandler) {
        super("removemoney", "Removes Money from the Specified Players purse", "removemoney [currency] [player-name] [amount]");
        this.logger = logger;
        this.sql = sql;
        this.dbHandler = dbHandler;

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player)) return;

            Optional<Player> targetPlayerOpt = getPlayerFromArgument(sender, context);
            if(targetPlayerOpt.isEmpty()) return;
            Player targetPlayer = targetPlayerOpt.get();

            int amount = context.get(AMOUNT_ARGUMENT);

            Optional<UniCurrency> currencyOpt = PlayerMoneySystemUtils.getTransactionCurrency(sender, context.get(CURRENCY_ARG));
            if(currencyOpt.isEmpty()) return;
            UniCurrency currency = currencyOpt.get();

            Optional<PlayerPurse> targetPurseOpt = PlayerMoneySystemUtils.getTargetPurse(dbHandler, sender, targetPlayer, currency);
            if(targetPurseOpt.isEmpty()) return;
            PlayerPurse targetPurse = targetPurseOpt.get();

            TransactionResult result = targetPurse.removeMoney(amount);
            handleTransactionResult(sender, result, targetPlayer, amount, currency, targetPurse);

        }, CURRENCY_ARG, PLAYER_ARGUMENT, AMOUNT_ARGUMENT);
    }

    /**
     * Handles the result of a transaction when removing money from a player's purse.
     *
     * @param sender       the command sender
     * @param result       the transaction result
     * @param targetPlayer the player whose money is being removed
     * @param amount       the amount of money being removed
     * @param currency     the currency in which the money is being removed
     * @param targetPurse  the player's purse from which the money is being removed
     */
    private void handleTransactionResult(CommandSender sender, TransactionResult result, Player targetPlayer, int amount, UniCurrency currency, PlayerPurse targetPurse) {
        switch(result) {
            case SUCCESS -> {
                this.logger.log(LogLevel.INFO, String.format("The Player %s got %d %ss removed from his Account by the Server", targetPlayer.getUsername(), amount, currency));
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.REMOVE_SERVER_MONEY.clone()
                    .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(amount)))
                    .setConfigParamValue(MessagesParams.CURRENCY.clone().setValue(currency.getDisplayName() + (amount > 1 ? "s" : "")))
                    .getValue());
            }
            case NOT_ENOUGH_MONEY -> {
                this.logger.log(LogLevel.INFO, String.format("The Player %s has not enough money to remove %d%ss Player Money: %d", targetPlayer.getUsername(), amount, currency, targetPurse.getAmount()));
            }
            case FAILED -> {
                this.logger.log(LogLevel.WARN, String.format("Could not remove %d from the Player %s", amount, targetPlayer.getUsername()));
            }
        }
    }
}
