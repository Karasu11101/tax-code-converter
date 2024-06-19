package it.forcina.esercizio_medialogic.exception;

import it.forcina.esercizio_medialogic.commons.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaxCodeException.class)
    public ResponseEntity<ErrorResponse> handleTaxCodeException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Invalid tax code", "E001", Instant.now()));
    }
}
