package com.vanyadem.expandapis.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ValidationUtils {

    private final Validator validator;

    public <T> void validationRequest(T request){
        if(request != null){
            Set<ConstraintViolation<T>> result = validator.validate(request);
            if(!result.isEmpty()){
                String resultValidate = result
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
//                        .reduce((s1, s2) -> s1 + ", " + s2)
//                        .orElse("");
                throw new ValidationException(String.format("Request is not valid, %s" , resultValidate));
            }
        }
    }

}
