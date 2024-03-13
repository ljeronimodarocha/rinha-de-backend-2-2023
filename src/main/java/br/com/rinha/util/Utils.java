package br.com.rinha.util;

import java.math.BigDecimal;

public class Utils {

    private Utils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
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
