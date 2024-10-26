package com.mylibrary.app.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mylibrary.app.validations.validators.AllowedValuesValidator;

import jakarta.validation.Constraint;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedValuesValidator.class)
public @interface AllowedValues {
    public String[] value() default {"true", "false"};
    Class<?> targetType() default String.class; // Specify target type for values
    public String message() default "Invalid value";
    public Class<?>[] groups() default {};
    public Class<?>[] payload() default {};
}
