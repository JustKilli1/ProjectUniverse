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
import net.projectuniverse.general.money_system.TransactionResult;
import net.projectuniverse.general.money_system.UniCurrency;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.Optional;

import static net.projectuniverse.general.money_system.ModuleMoneySystem.CURRENCY_ARG;

/**
 * The CmdPay class is a command that allows a player to pay a specified amount to another player.
 * It extends the UniverseCommand class.
 */

public class CmdPay extends UniverseCommand {

    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    /**
     * Constructor for CmdPay class.
     *
     * @param sql The DBALMoney object connected to the database.
     * @param dbHandler The DBHMoney object used for handling money transactions.
     */
    public CmdPay(DBALMoney sql, DBHMoney dbHandler) {
        super("pay", "Pays a specified Amount to another Player", "pay [player-name] [amount]");
        this.sql = sql;
        this.dbHandler = dbHandler;

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player senderPlayer)) return;
            Optional<Player> targetPlayerOpt = getPlayerFromArgument(sender, context);
            if(targetPlayerOpt.isEmpty()) return;
            Player targetPlayer = targetPlayerOpt.get();

            if(targetPlayer.equals(senderPlayer)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.CANNOT_PAY_TO_YOURSELF.getValue());
                return;
            }

            int amount = context.get(AMOUNT_ARGUMENT);
            if(amount <= 0) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PAY_AMOUNT_GREATER_THAN_ZERO.getValue());
                return;
            }

            Optional<UniCurrency> currencyOpt = PlayerMoneySystemUtils.getTransactionCurrency(sender, context.get(CURRENCY_ARG));
            if(currencyOpt.isEmpty()) return;
            UniCurrency currency = currencyOpt.get();

            Optional<PlayerPurse> senderPurseOpt = PlayerMoneySystemUtils.getTargetPurse(dbHandler, sender, senderPlayer, currency);
            if(senderPurseOpt.isEmpty()) return;
            PlayerPurse senderPurse = senderPurseOpt.get();

            Optional<PlayerPurse> targetPurseOpt = PlayerMoneySystemUtils.getTargetPurse(dbHandler, sender, targetPlayer, currency);
            if(targetPurseOpt.isEmpty()) return;
            PlayerPurse targetPurse = targetPurseOpt.get();

            TransactionResult transactionResultRemove = senderPurse.removeMoney(amount);
            if(!transactionResultRemove.equals(TransactionResult.SUCCESS) && !handleTransactionResult(sender, transactionResultRemove, amount, currency, senderPlayer, targetPlayer)) return;

            TransactionResult transactionResultAdd = targetPurse.addMoney(amount);
            handleTransactionResult(sender, transactionResultAdd, amount, currency, senderPlayer, targetPlayer);

        }, CURRENCY_ARG, PLAYER_ARGUMENT, AMOUNT_ARGUMENT);
    }

    /**
     * Handles the result of a transaction.
     *
     * @param sender The command sender.
     * @param transactionResult The transaction result.
     * @param amount The amount of money transferred.
     * @param currency The currency of the transaction.
     * @param senderPlayer The sender player.
     * @param targetPlayer The target player.
     * @return {@code true} if the transaction is handled successfully, {@code false} otherwise.
     */
    private boolean handleTransactionResult(CommandSender sender, TransactionResult transactionResult, int amount, UniCurrency currency, Player senderPlayer, Player targetPlayer) {
        switch(transactionResult) {
            case NOT_ENOUGH_MONEY -> {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.NOT_ENOUGH_MONEY.getValue());
                return false;
            }
            case FAILED -> {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.TRANSACTION_FAILED.getValue());
                return false;
            }
            case SUCCESS -> {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.TRANSACTION_SUCCESS.clone()
                        .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(amount)))
                        .setConfigParamValue(MessagesParams.CURRENCY_SYMBOL.clone().setValue(currency.getSymbol()))
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(targetPlayer.getUsername()))
                        .getValue());
                Messenger.sendMessage(targetPlayer, MessageDesign.SERVER_MESSAGE, MessagesConfig.TRANSACTION_SUCCESS_TARGET.clone()
                        .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(amount)))
                        .setConfigParamValue(MessagesParams.CURRENCY_SYMBOL.clone().setValue(currency.getSymbol()))
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(senderPlayer.getUsername()))
                        .getValue());
                return true;
            }
        }
        return true;
    }
}
