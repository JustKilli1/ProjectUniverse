package net.projectuniverse.general.money_system;

import net.minestom.server.entity.Player;
import net.projectuniverse.general.money_system.database.DBALMoney;

import java.util.Optional;

/**
 * Represents a purse for a player in a game.
 */

public class PlayerPurse {

    private final String playerName;
    private final UniCurrency currency;
    private final DBALMoney sql;
    private Player player;
    private int amount;

    public PlayerPurse(Player player, UniCurrency currency, DBALMoney sql, int amount) {
        this.currency = currency;
        this.player = player;
        this.playerName = this.player.getUsername();
        this.sql = sql;
        this.amount = amount;
    }

    public PlayerPurse(String playerName, UniCurrency currency, DBALMoney sql, int amount) {
        this.playerName = playerName;
        this.currency = currency;
        this.sql = sql;
        this.amount = amount;
    }

    public PlayerPurse(Player player, UniCurrency currency, DBALMoney sql) {
        this(player, currency, sql, 0);
    }


    /**
     * Updates the amount in the player's purse and returns the result of the transaction.
     *
     * @param newAmount The new amount to update in the player's purse.
     * @return The result of the transaction. Returns {@link TransactionResult#SUCCESS} if the transaction succeeded.
     *         Returns {@link TransactionResult#FAILED} if the transaction failed with no reason.
     */
    private TransactionResult updateAmount(int newAmount) {
        if(newAmount < 0) return TransactionResult.FAILED;
        amount = newAmount;
        if(!sql.updatePlayerPurse(player, currency, newAmount)) return TransactionResult.FAILED;
        return TransactionResult.SUCCESS;
    }

    /**
     * Adds an Amount to the Players Purse.
     * @param value The Amount that gets added to the Players purse
     * @return Returns {@link TransactionResult#SUCCESS} if the Transaction succeeded.
     *         <p>Returns {@link TransactionResult#FAILED} if the Transaction Failed with no Reason.</p>
     * */
    public TransactionResult addMoney(int value) {
        return updateAmount(amount + value);
    }

    /**
     * Removes an Amount from the Players Purse
     * @param value The Amount that gets removed from the Players purse
     * @return <p>Returns {@link TransactionResult#SUCCESS} if the Transaction succeeded.</p>
     *         <p>Returns {@link TransactionResult#NOT_ENOUGH_MONEY} if the Transaction Failed because the Player has not enough Money.</p>
     *         <p>Returns {@link TransactionResult#FAILED} if the Transaction Failed with no Reason.</p>
     * */
    public TransactionResult removeMoney(int value) {
        if(value > amount) return TransactionResult.NOT_ENOUGH_MONEY;
        return updateAmount(amount - value);
    }

    public UniCurrency getCurrency() {
        return currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getPlayerName() {
        return playerName;
    }

}
