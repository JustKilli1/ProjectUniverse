package net.projectuniverse.general.money_system;

import java.util.Optional;

/**
     * Defines the usable Currency's
     * */
    public enum UniCurrency {

        /**
         * Standard ProjectUniverse Currency
         * <p>display name: Uni</p>
         * <p>Symbol: 💰</p>
         * */
        UNI("Uni", "€"),
        /**
         * Premium ProjectUniverse Currency
         * <p>display name: Coin</p>
         * <p>Symbol: 🪙</p>
         * */
        COIN("Coin", "$")
        ;
        /**
         * Field for the Display Name of the Currency inGame
         * */
        private String displayName;
        /**
         * Field for the Symbol that used for the Currency inGame
         * */
        private String symbol;

        UniCurrency(String displayName, String symbol) {
            this.displayName = displayName;
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return displayName;
        }

         public static Optional<UniCurrency> getEnum(String name) {
            for(UniCurrency currency : values()) {
                if(currency.toString().equalsIgnoreCase(name)) return Optional.of(currency);
            }
            return Optional.empty();
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getSymbol() {
            return symbol;
        }
    }
