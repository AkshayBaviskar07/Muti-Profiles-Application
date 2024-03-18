package com.telusko.MultProfilesApp.advise;

import com.telusko.MultProfilesApp.exceptions.company.CompanyExists;
import com.telusko.MultProfilesApp.exceptions.company.CompanyNotFound;
import com.telusko.MultProfilesApp.model.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles any general exception by creating an ErrorDetails object with the exception message and current timestamp.
     * Returns a ResponseEntity with the ErrorDetails and an HTTP status of INTERNAL_SERVER_ERROR.
     *
     * @param exception the exception to handle
     * @return a ResponseEntity containing the error details and status code
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorDetails> handleException(Exception exception) {
        // Create an ErrorDetails object with the exception message and current timestamp
        ErrorDetails errorDetails = new ErrorDetails(
                exception.getMessage(),
                LocalDateTime.now()
        );

        // Return a ResponseEntity with the ErrorDetails and HTTP status of INTERNAL_SERVER_ERROR
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles CompanyNotFound exceptions by returning a ResponseEntity with error details.
     *
     * @param companyNotFound The CompanyNotFound exception to handle
     * @return A ResponseEntity containing error details with HTTP status NOT_FOUND
     */
    @ExceptionHandler(CompanyNotFound.class)
    private ResponseEntity<ErrorDetails> companyNotFoundException(CompanyNotFound companyNotFound) {
        // Create error details object with exception message and current timestamp
        ErrorDetails errorDetails = new ErrorDetails(
                companyNotFound.getMessage(),
                LocalDateTime.now()
        );

        // Return ResponseEntity with error details and HTTP status NOT_FOUND
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for CompanyExists.
     *
     * @param companyExists the exception thrown
     * @return ResponseEntity with ErrorDetails and INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(CompanyExists.class)
    private ResponseEntity<ErrorDetails> companyAlreadyExists(CompanyExists companyExists) {
        // Create ErrorDetails object with exception message and current date time
        ErrorDetails errorDetails = new ErrorDetails(
                companyExists.getMessage(),
                LocalDateTime.now()
        );
        // Return ResponseEntity with ErrorDetails and INTERNAL_SERVER_ERROR status
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
