package dk.ek.onidesign.catalog.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ApiError(HttpStatus status, String code, String message) {}

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

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ValidationErrorResponse body = new ValidationErrorResponse(
                errors,
                Instant.now().toString()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public record ValidationErrorResponse(List<String> errors, String timestamp) {}


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        ApiError error = new ApiError(ex.getStatus(), ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiError> handleInternalServer(InternalServerException ex) {
        ApiError error = new ApiError(ex.getStatus(), ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, ex.getStatus());
    }

}
