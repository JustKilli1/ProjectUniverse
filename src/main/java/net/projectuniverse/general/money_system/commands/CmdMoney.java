package net.projectuniverse.general.money_system.commands;

import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
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
        super("money", "Shows how much money a Player has", "money (Player-Name)");
        this.sql = sql;
        this.dbHandler = dbHandler;

        setDefaultExecutor(new DefaultExecutor(getUsage()));

        ArgumentEntity playerArg = PLAYER_ARGUMENT;

        addSyntax((sender, context) -> {
            EntityFinder playerFinder = context.get(playerArg);
            if(!(sender instanceof Player senderPlayer)) return;
            Player targetPlayer = playerFinder.findFirstPlayer(sender);
            if(targetPlayer == null) {
                Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.PLAYER_NOT_FOUND.clone()
                        .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(playerArg.toString()))
                        .getValue());
                return;
            }
            PlayerPurse.Currency currency = PlayerPurse.Currency.UNIS;
            PlayerPurse targetPurse = dbHandler.getPlayerPurse(targetPlayer, currency).orElse(new PlayerPurse(targetPlayer, PlayerPurse.Currency.UNIS, sql));
            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.SHOW_PLAYER_MONEY_TARGET.clone()
                    .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(targetPurse.getAmount())))
                    .setConfigParamValue(MessagesParams.PLAYER.clone().setValue(targetPlayer.getUsername()))
                    .setConfigParamValue(MessagesParams.CURRENCY_SYMBOL.clone().setValue(currency.getSymbol()))
                    .getValue());

        }, playerArg);

        addSyntax((sender, context) -> {
            if(!(sender instanceof Player senderPlayer)) return;

            PlayerPurse.Currency currency = PlayerPurse.Currency.UNIS;
            PlayerPurse playerPurse = dbHandler.getPlayerPurse(senderPlayer, currency).orElse(new PlayerPurse(senderPlayer, PlayerPurse.Currency.UNIS, sql));

            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.SHOW_PLAYER_MONEY.clone()
                    .setConfigParamValue(MessagesParams.AMOUNT.clone().setValue(String.valueOf(playerPurse.getAmount())))
                    .setConfigParamValue(MessagesParams.CURRENCY.clone().setValue(currency.getDisplayName() + (playerPurse.getAmount() > 1 ? "s" : "")))
                    .setConfigParamValue(MessagesParams.CURRENCY_SYMBOL.clone().setValue(currency.getSymbol()))
                    .getValue());

        });

    }

}
