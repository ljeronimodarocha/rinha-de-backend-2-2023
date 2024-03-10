package br.com.rinha.validators.string;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringOnly {
    String message() default "O campo deve conter apenas caracteres não numéricos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}