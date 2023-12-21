package br.edu.ifpb.tsi.pweb2.ecollegialis.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatriculaValidator implements ConstraintValidator<Matricula, String>{

    @Override
    public void initialize(Matricula matriculaField) {
    }

    @Override
    public boolean isValid(String matriculaField ,ConstraintValidatorContext cxt) {
        return matriculaField.matches("[0-9]{11}");
    }
}
