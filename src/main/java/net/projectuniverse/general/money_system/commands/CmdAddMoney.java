package net.projectuniverse.general.money_system.commands;

import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import net.projectuniverse.general.commands.UniverseCommand;
import net.projectuniverse.general.commands.command_executor.DefaultExecutor;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.logging.ILogger;
import net.projectuniverse.general.logging.LogLevel;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.money_system.PlayerPurse;
import net.projectuniverse.general.money_system.database.DBALMoney;
import net.projectuniverse.general.money_system.database.DBHMoney;

public class CmdAddMoney extends UniverseCommand {

    private final ILogger logger;
    private final DBALMoney sql;
    private final DBHMoney dbHandler;

    public CmdAddMoney(ILogger logger, DBALMoney sql, DBHMoney dbHandler) {
        super("addmoney", "Adds Money to the Specified Player purse", "addmoney (player-name)");
        this.logger = logger;
        this.sql = sql;
        this.dbHandler = dbHandler;

        setDefaultExecutor(new DefaultExecutor(getUsage()));

        ArgumentEntity playerArg = PLAYER_ARGUMENT;
        ArgumentInteger amountArg = AMOUNT_ARGUMENT;

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            Player targetPlayer = playerFinder.findFirstPlayer(sender);
            if(targetPlayer == null) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_NOT_FOUND.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
                return;
            }
            int amount = context.get(amountArg);
            PlayerPurse.Currency currency = PlayerPurse.Currency.UNIS;
            PlayerPurse targetPurse = this.dbHandler.getPlayerPurse(targetPlayer, currency).orElse(new PlayerPurse(targetPlayer, PlayerPurse.Currency.UNIS, this.sql));
            PlayerPurse.TransactionResult result = targetPurse.addMoney(amount);

            switch(result) {
                case SUCCESS -> logger.log(LogLevel.INFO, "The Player " + targetPlayer.getUsername() + " got " + amount + " " + currency.getDisplayName() + "s from the Server");
                case FAILED -> {
                    logger.log(LogLevel.WARN, "Could not give the Player " + targetPlayer.getUsername() + " " + amount + " " + currency.getDisplayName() + "s");
                    return;
                }
            }
            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.ADD_SERVER_MONEY.clone()
                    .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(amount)))
                    .setConfigParamValue(MessagesParams.CURRENCY.clone().setValue(currency.getDisplayName() + (amount > 1 ? "s" : "")))
                    .getValue());

        }, playerArg, amountArg);

    }
}
