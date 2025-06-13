package net.projectuniverse.general.money_system;

    /**
     * Represents the Result of an Transaction
     * */
    public enum TransactionResult {
        /**
         * The Transaction succeeded
         * */
        SUCCESS,
        /**
         * The Transaction is still pending
         * */
        PENDING,
        /**
         * The Transaction Failed
         * */
        FAILED,
        /**
         * The Transaction Failed because the Player has not enough money
         * */
        NOT_ENOUGH_MONEY
        ;
    }
