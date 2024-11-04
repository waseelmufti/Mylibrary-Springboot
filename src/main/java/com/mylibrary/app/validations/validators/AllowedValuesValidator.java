package com.mylibrary.app.validations.validators;

import com.mylibrary.app.validations.AllowedValues;

import java.util.Arrays;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedValuesValidator implements ConstraintValidator<AllowedValues, Object>{
    private String[] allowedValues;
    private Class<?> targetType;
    @Override
    public void initialize(AllowedValues allowedValues) {
        ConstraintValidator.super.initialize(allowedValues);
        this.allowedValues = allowedValues.value();
        this.targetType = allowedValues.targetType();
    }

    @Override
    public boolean isValid(Object inputValue, ConstraintValidatorContext context) {
        if(inputValue == null){
            return true;
        }

        try {
            Object convertedValue = this.convertToTargetType(inputValue.toString(), this.targetType);
            return Arrays.stream(this.allowedValues)
                    .anyMatch(allowed -> allowed.equals(String.valueOf(convertedValue)));
        } catch (Exception e) {
            return false;
        }
    }

    private Object convertToTargetType(String value, Class<?> targetType){
        if(targetType == Integer.class){
            return Integer.parseInt(value);
        }else if(targetType == Long.class){
            return Long.parseLong(value);
        }else if(targetType == Double.class){
            return Double.parseDouble(value);
        }else if(targetType == Float.class){
            return Float.parseFloat(value);
        }else if(targetType == Boolean.class){
            return Boolean.parseBoolean(value);
        }else{
            return value;
        }
    }

}
