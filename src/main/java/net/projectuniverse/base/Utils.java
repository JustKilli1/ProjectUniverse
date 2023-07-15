package net.projectuniverse.base;

import java.util.Optional;

public class Utils {

    /**
     * Converts a given string to an integer value.
     *
     * @param str the string to be converted to an integer
     * @return an optional containing the converted integer value, or an empty optional if the conversion fails
     */
    public static Optional<Integer> convertToInt(String str) {
        try {
            return Optional.ofNullable(Integer.parseInt(str));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }

    /**
     * Converts a given string to a boolean value.
     *
     * @param str the string to be converted to a boolean
     * @return an optional containing the converted boolean value, or an empty optional if the conversion fails
     */
    public static Optional<Boolean> convertToBool(String str) {
        try {
            return Optional.ofNullable(Boolean.parseBoolean(str));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }

}
