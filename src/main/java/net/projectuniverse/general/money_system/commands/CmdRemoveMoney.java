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
        super("removemoney", "Removes Money from the Specified Players purse", "removemoney (player-name)");
        this.logger = logger;
        this.sql = sql;
        this.dbHandler = dbHandler;

        setDefaultExecutor(new DefaultExecutor(getUsage()));

        ArgumentEntity playerArg = PLAYER_ARGUMENT;
        ArgumentInteger amountArg = AMOUNT_ARGUMENT;

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            if(!(sender instanceof Player)) return;
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
            PlayerPurse.TransactionResult result = targetPurse.removeMoney(amount);

            switch(result) {
                case SUCCESS -> this.logger.log(LogLevel.INFO, "The Player " + targetPlayer.getUsername() + " got " + amount + " " + currency.getDisplayName() + "s removed from his Account by the Server");
                case NOT_ENOUGH_MONEY -> {
                    this.logger.log(LogLevel.INFO, "The Player " + targetPlayer.getUsername() + " has not enough money to remove " + amount + " " + currency.getDisplayName() + "s Player Money: " + targetPurse.getAmount());
                    return;
                }
                case FAILED -> {
                    this.logger.log(LogLevel.WARN, "Could not remove " + amount + " from the Player " + targetPlayer.getUsername());
                    return;
                }
            }
            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.REMOVE_SERVER_MONEY.clone()
                    .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(amount)))
                    .setConfigParamValue(MessagesParams.CURRENCY.clone().setValue(currency.getDisplayName() + (amount > 1 ? "s" : "")))
                    .getValue());

        }, playerArg, amountArg);
    }
}
