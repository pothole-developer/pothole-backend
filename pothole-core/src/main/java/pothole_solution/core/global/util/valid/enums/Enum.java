package pothole_solution.core.global.util.valid.enums;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pothole_solution.core.global.util.valid.validator.DefaultEnumValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DefaultEnumValidator.class)
public @interface Enum {
    String message() default "존재하지 않는 진행 상태입니다.";

    Class<? extends java.lang.Enum<?>> enumClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean ignorable() default false;
}
