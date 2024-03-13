package br.com.rinha.validators.string;

import br.com.rinha.util.Utils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class StringValidator implements ConstraintValidator<StringOnly, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // `@NotNull` deve ser usado para checar nulo
        }

        if (value instanceof String valueString) {
            return Utils.isNumeric(valueString);
        } else if (value instanceof Collection<?> collection) {
            return collection.stream().allMatch(item -> item instanceof String itemString && Utils.isNumeric(itemString));
        }

        return false; // Não é um tipo suportado
    }


}