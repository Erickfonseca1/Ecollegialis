package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = MatriculaValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Matricula {
    String message() default "Matrícula deve ter 11 caracteres numéricos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
