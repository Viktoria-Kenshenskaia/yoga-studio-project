package com.example.yogastudioproject.domain.validation;

import com.example.yogastudioproject.domain.annotation.PasswordMatches;
import com.example.yogastudioproject.domain.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest signupRequest = (SignupRequest) o;

        return signupRequest.getPassword().equals(signupRequest.getConfirmPassword());
    }
}
