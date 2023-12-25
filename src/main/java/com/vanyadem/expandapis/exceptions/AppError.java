package com.vanyadem.expandapis.exceptions;

import lombok.Data;

import java.util.Date;

/**
 * Represents an error response in the application.
 * This class encapsulates information about an error, including the HTTP status code,
 * an error message, and a timestamp indicating when the error occurred.
 *
 * <p>Example Usage:
 * <pre>{@code
 * AppError error = AppError.of(404, "Resource not found");
 * }</pre>
 *
 * <p>Instances of this class can be created using the static factory method {@code of},
 * ensuring controlled initialization of the error information.
 *
 * @author Vanya Demydenko
 */
@Data
public class AppError {

    /** The HTTP status code indicating the type of error. */
    private int status;

    /** A descriptive message providing more details about the error. */
    private String message;

    /** The timestamp representing when the error occurred. */
    private Date timestamp;


    /**
     * Private no-argument constructor to enforce controlled initialization.
     */
    private AppError() {
    }

    /**
     * Static factory method to create an {@code AppError} instance with the specified status and message.
     *
     * @param status The HTTP status code.
     * @param message A descriptive message.
     * @return An {@code AppError} instance with the specified status, message, and timestamp.
     */
    public static AppError of(int status, String message) {
        AppError error = new AppError();
        error.setStatus(status);
        error.setMessage(message);
        error.setTimestamp(new Date());
        return error;
    }
}
