package net.projectuniverse.base;

import java.util.Optional;

public class Utils {

    public static Optional<Integer> convertToInt(String str) {
        try {
            return Optional.ofNullable(Integer.parseInt(str));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }

    public static Optional<Boolean> convertToBool(String str) {
        try {
            return Optional.ofNullable(Boolean.parseBoolean(str));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }

}
