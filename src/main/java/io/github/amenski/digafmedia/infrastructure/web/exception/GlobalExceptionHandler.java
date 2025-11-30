package io.github.amenski.digafmedia.infrastructure.web.exception;

import io.github.amenski.digafmedia.domain.AuthorizationException;
import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    private String getCorrelationId(HttpServletRequest request) {
        return request.getHeader("X-Correlation-ID");
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail, String correlationId) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(title);
        problemDetail.setDetail(detail);
        problemDetail.setProperty("timestamp", Instant.now());
        if (correlationId != null) {
            problemDetail.setProperty("correlationId", correlationId);
        }
        return problemDetail;
    }

    @ExceptionHandler(DomainValidationException.class)
    public ResponseEntity<ProblemDetail> handleDomainValidationException(
            DomainValidationException ex, HttpServletRequest request) {
        String correlationId = getCorrelationId(request);
        logger.warn("Domain validation failed - correlationId: {}, message: {}", correlationId, ex.getMessage());

        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Domain Validation Failed",
            ex.getMessage(),
            correlationId
        );

        if (ex.getErrors() != null) {
            var violations = new java.util.ArrayList<ApiValidationError>();
            ex.getErrors().forEach((field, fieldErrors) ->
                fieldErrors.forEach(errorMessage ->
                    violations.add(new ApiValidationError("request", field, null, errorMessage))
                )
            );
            problemDetail.setProperty("violations", violations);
        }

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(
            EntityNotFoundException ex, HttpServletRequest request) {
        String correlationId = getCorrelationId(request);
        logger.info("Entity not found - correlationId: {}, message: {}", correlationId, ex.getMessage());
        
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.NOT_FOUND,
            "Entity Not Found",
            ex.getMessage(),
            correlationId
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ProblemDetail> handleAuthorizationException(
            AuthorizationException ex, HttpServletRequest request) {
        String correlationId = getCorrelationId(request);
        logger.warn("Authorization failed - correlationId: {}, message: {}", correlationId, ex.getMessage());
        
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.FORBIDDEN,
            "Authorization Failed",
            ex.getMessage(),
            correlationId
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {
        String correlationId = getCorrelationId(request);
        logger.warn("Access denied - correlationId: {}, message: {}", correlationId, ex.getMessage());
        
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.FORBIDDEN,
            "Access Denied",
            "You don't have permission to access this resource",
            correlationId
        );
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ProblemDetail> handleAuthenticationException(
            RuntimeException ex, HttpServletRequest request) {
        String correlationId = getCorrelationId(request);
        logger.warn("Authentication failed - correlationId: {}, message: {}", correlationId, ex.getMessage());
        
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.UNAUTHORIZED,
            "Authentication Failed",
            "Invalid credentials provided",
            correlationId
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String correlationId = getCorrelationId(request);

        var violations = new java.util.ArrayList<ApiValidationError>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error instanceof FieldError fieldError ? fieldError.getField() : error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            Object rejectedValue = error instanceof FieldError fieldError ? fieldError.getRejectedValue() : null;
            violations.add(new ApiValidationError(error.getObjectName(), fieldName, rejectedValue, errorMessage));
        });

        logger.info("Request validation failed - correlationId: {}, violations: {}", correlationId, violations.size());

        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Validation Failed",
            "Request validation failed",
            correlationId
        );

        problemDetail.setProperty("violations", violations);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(
            Exception ex, HttpServletRequest request) {
        String correlationId = getCorrelationId(request);
        logger.error("Unexpected error occurred - correlationId: {}, message: {}", correlationId, ex.getMessage(), ex);
        
        ProblemDetail problemDetail = createProblemDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error",
            "An unexpected error occurred",
            correlationId
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}