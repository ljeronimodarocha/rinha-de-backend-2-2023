package br.com.rinha.validators.string;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.util.Collection;

public class StringValidator implements ConstraintValidator<StringOnly, Object> {

    @Override
    public void initialize(StringOnly constraint) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // `@NotNull` deve ser usado para checar nulo
        }

        if (value instanceof String) {
            return isNumeric((String) value);
        } else if (value instanceof Collection<?> collection) {
            return collection.stream().allMatch(item -> item instanceof String && isNumeric((String) item));
        }

        return false; // Não é um tipo suportado
    }

    private boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return false; // É um número
        } catch (NumberFormatException e) {
            return true; // Não é um número, é uma string
        }
    }



}