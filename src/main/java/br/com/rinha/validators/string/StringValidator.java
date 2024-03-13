package br.com.rinha.validators.string;

import br.com.rinha.util.Utils;
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
            return Utils.isNumeric((String) value);
        } else if (value instanceof Collection<?> collection) {
            return collection.stream().allMatch(item -> item instanceof String && Utils.isNumeric((String) item));
        }

        return false; // Não é um tipo suportado
    }



}