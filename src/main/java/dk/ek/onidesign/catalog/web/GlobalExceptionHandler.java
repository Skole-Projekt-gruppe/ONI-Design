package dk.ek.onidesign.catalog.web;


import dk.ek.onidesign.exception.ModuleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ModuleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleModuleNotFound(ModuleNotFoundException ex) {
        ErrorResponse body = new ErrorResponse(
                ex.getMessage(),
                Instant.now().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // lille indre klasse til fejlrespons
        public record ErrorResponse(String message, String timestamp) {
    }
}
