package pothole_solution.core.global.util.valid.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pothole_solution.core.global.util.valid.enums.Enum;

import java.util.Arrays;

public class DefaultEnumValidator implements ConstraintValidator<Enum, java.lang.Enum> {
    private Enum valueList;
    private boolean ignorable;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.valueList = constraintAnnotation;
        this.ignorable = constraintAnnotation.ignorable();
    }

    @Override
    public boolean isValid(java.lang.Enum value, ConstraintValidatorContext context) {
        Object[] enumConstants = this.valueList.enumClass().getEnumConstants();

        if ((enumConstants == null || enumConstants.length == 0) && ignorable)
            return true;

        if (enumConstants == null || enumConstants.length == 0) {
            return false;
        }

        return Arrays.asList(enumConstants).contains(value);
    }
}
