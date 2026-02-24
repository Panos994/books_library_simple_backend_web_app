package library.management.system.demo.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import library.management.system.demo.dto.ApiErrorDTO.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<ApiErrorDto> handleDuplicateApplication(
            DuplicateApplicationException ex,
            HttpServletRequest request
    ) {
        ApiErrorDto apiError = new ApiErrorDto(
                "DUPLICATE_APPLICATION",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorDto> handle403(
            AccessDeniedException ex,
            HttpServletRequest request
    ) {
        ApiErrorDto apiError = new ApiErrorDto(
                "FORBIDDEN",
                "You are not allowed to access this resource",
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorDto> handleRuntime(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        ApiErrorDto apiError = new ApiErrorDto(
                "ERROR",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
