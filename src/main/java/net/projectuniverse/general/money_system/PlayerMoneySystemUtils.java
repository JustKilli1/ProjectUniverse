package net.projectuniverse.general.money_system;

import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import net.projectuniverse.general.config.configs.MessagesConfig;
import net.projectuniverse.general.config.configs.MessagesParams;
import net.projectuniverse.general.messenger.MessageDesign;
import net.projectuniverse.general.messenger.Messenger;
import net.projectuniverse.general.money_system.database.DBHMoney;

import java.util.Optional;

public class PlayerMoneySystemUtils {

    public static Optional<PlayerPurse> getTargetPurse(DBHMoney dbHandler, CommandSender sender, Player targetPlayer, UniCurrency currency) {
        Optional<PlayerPurse> targetPurseOpt = dbHandler.getPlayerPurse(targetPlayer, currency);
        if(targetPurseOpt.isEmpty()) {
            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.ERROR.clone().getValue());
            return Optional.empty();
        }
        return targetPurseOpt;
    }

    /**
     * Retrieves the transaction currency as an Optional<UniCurrency> object.
     *
     * @param sender       The CommandSender object representing the sender of the command.
     * @param currencyStr  A String representing the currency.
     * @return An Optional<UniCurrency> object representing the transaction currency, or an empty Optional if the currency was not found.
     */
    public static Optional<UniCurrency> getTransactionCurrency(CommandSender sender, String currencyStr) {
        Optional<UniCurrency> currencyOpt = UniCurrency.getEnum(currencyStr);
        if(currencyOpt.isEmpty()) {
            Messenger.sendMessage(sender, MessageDesign.SERVER_MESSAGE, MessagesConfig.CURRENCY_NOT_FOUND.clone()
                    .setConfigParamValue(MessagesParams.CURRENCY.clone().setValue(currencyStr))
                    .getValue());
            return Optional.empty();
        }
        return currencyOpt;
    }
}
