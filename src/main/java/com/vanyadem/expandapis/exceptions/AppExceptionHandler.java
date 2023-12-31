package com.vanyadem.expandapis.exceptions;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> handleSuchUserExistException(SuchUserExistException exception) {
        AppError error = AppError.of(400, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleValidationException(ValidationException exception) {
        AppError error = AppError.of(400, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleSuchTableExistException(SuchTableExistException exception) {
        AppError error = AppError.of(400, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleSuchBadSqlGrammarException(BadSqlGrammarException exception) {
        AppError error = AppError.of(400,
                "Incorrect number or type of columns! Each record must have the same number and type of columns");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleAnyException(Exception exception) {
        AppError error = AppError.of(500, exception.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
