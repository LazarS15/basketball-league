package com.basketball.league.api.exceptionHandler;

import com.basketball.league.api.error.ErrorDTO;
import com.basketball.league.application.exception.alreadyExists.AlreadyExistsException;
import com.basketball.league.application.exception.notFound.NotFoundException;
import com.basketball.league.application.exception.password.PasswordException;
import com.basketball.league.domain.exception.BasketballLeagueDomainException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleInternalException(Exception exception) {
        log.error(exception.getMessage(), exception);
        ErrorDTO errorDTO = buildErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(value = {BasketballLeagueDomainException.class, AlreadyExistsException.class,
            PasswordException.class, NotFoundException.class, DataIntegrityViolationException.class,
            ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleBadRequest(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        ErrorDTO errorDTO;
        if (exception instanceof DataIntegrityViolationException) {
            errorDTO = buildErrorDTO(HttpStatus.BAD_REQUEST.value(),
                    "Entity you trying to create already exists in the database!");
        } else {
            errorDTO = buildErrorDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorDTO> handleBadCredentialsException(BadCredentialsException exception) {
        log.error("Bad credentials: {}", exception.getMessage());
        String errorMessage = "Invalid username or password. Please check your credentials and try again.";
        ErrorDTO errorDTO = buildErrorDTO(HttpStatus.UNAUTHORIZED.value(), errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDTO> handleAccessDeniedException(AccessDeniedException exception) {
        log.error("Access denied: {}", exception.getMessage());
        ErrorDTO errorDTO = buildErrorDTO(HttpStatus.FORBIDDEN.value(), "Access Denied");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDTO);
    }

    private ErrorDTO buildErrorDTO(int code, String message) {
        return ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .code(code)
                .message(message)
                .build();
    }
}
