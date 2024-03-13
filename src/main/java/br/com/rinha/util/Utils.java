package br.com.rinha.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Utils {
    private final static Pattern UUID_REGEX_PATTERN =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isValidUUID(String str) {
        if (str == null) {
            return false;
        }
        return UUID_REGEX_PATTERN.matcher(str).matches();
    }

    public static boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return false; // É um número
        } catch (NumberFormatException e) {
            return true; // Não é um número, é uma string
        }
    }
}
