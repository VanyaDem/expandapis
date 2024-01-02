package com.vanyadem.expandapis.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for performing validation on request objects using Jakarta Bean Validation API.
 */
@Component
@RequiredArgsConstructor
public class ValidationUtils {

    private final Validator validator;

    /**
     * Validates the provided request object against defined constraints.
     * If the request object is not null, it validates the object using the injected Validator.
     * If any constraint violations occur, it throws a ValidationException containing the error messages.
     *
     * @param <T>     Type of the request object to be validated
     * @param request Request object to be validated
     * @throws ValidationException if the request object fails validation, containing error messages
     */
    public <T> void validationRequest(T request) {
        if (request != null) {
            Set<ConstraintViolation<T>> result = validator.validate(request);
            if (!result.isEmpty()) {
                String resultValidate = result
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                throw new ValidationException(String.format("Request is not valid, %s", resultValidate));
            }
        }
    }

}
