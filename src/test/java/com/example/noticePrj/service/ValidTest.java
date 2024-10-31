package com.example.noticePrj.service;

import com.example.noticePrj.valid.ValidMember;
import jakarta.validation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Transactional
@Validated
public class ValidTest {

    private Validator validator;


    public ValidTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    public void validMember(ValidMember validMember){
        Set<ConstraintViolation<ValidMember>> validates = validator.validate(validMember);

        if(!validates.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<ValidMember> validate : validates) {
                sb.append(validate.getMessage());
                sb.append("\n");
            }
            throw new ValidationException(sb.toString());
        }
    }
}
