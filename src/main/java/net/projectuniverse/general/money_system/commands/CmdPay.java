package net.projectuniverse.general.money_system.commands;

import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;

public class CmdPay extends UniverseCommand {

    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    public CmdPay(DBALMoney sql, DBHMoney dbHandler) {
        super("pay", "Pays a specified Amount to another Player", "pay [Player-Name] [Amount]");
        this.sql = sql;
        this.dbHandler = dbHandler;

        setDefaultExecutor(new DefaultExecutor(getUsage()));

        ArgumentEntity playerArg = PLAYER_ARGUMENT;
        ArgumentInteger amountArg = AMOUNT_ARGUMENT;

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            if(!(sender instanceof Player)) return;
            Player senderPlayer = (Player) sender;
            Player targetPlayer = playerFinder.findFirstPlayer(sender);
            if(targetPlayer == null) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_NOT_FOUND.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
                return;
            }
            if(targetPlayer.equals(sender)) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.CANNOT_PAY_TO_YOURSELF.getValue());
                return;
            }
            int amount = context.get(amountArg);
            if(amount <= 0) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PAY_AMOUNT_GREATER_THAN_ZERO.getValue());
                return;
            }
            PlayerPurse.Currency currency = PlayerPurse.Currency.UNIS;
            PlayerPurse senderPurse = dbHandler.getPlayerPurse(senderPlayer, currency).orElse(new PlayerPurse(senderPlayer, PlayerPurse.Currency.UNIS, sql));
            PlayerPurse targetPurse = dbHandler.getPlayerPurse(targetPlayer, currency).orElse(new PlayerPurse(targetPlayer, PlayerPurse.Currency.UNIS, sql));

            PlayerPurse.TransactionResult transactionResult = senderPurse.removeMoney(amount);
            switch(transactionResult) {
                case NOT_ENOUGH_MONEY -> {
                    Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.NOT_ENOUGH_MONEY.getValue());
                    return;
                }
                case FAILED -> {
                    Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.TRANSACTION_FAILED.getValue());
                    return;
                }
            }
            PlayerPurse.TransactionResult targetResult = targetPurse.addMoney(amount);

            switch(targetResult) {
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
                }
                case FAILED -> Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.TRANSACTION_FAILED.getValue());
            }

        }, playerArg, amountArg);

    }
}
