package com.example.aichat.exception;

import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(Map.of("error", "validation_error", "message", message));
    }

    @ExceptionHandler(NonTransientAiException.class)
    public ResponseEntity<Map<String, String>> handleAiException(NonTransientAiException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "ai_service_error", "message",
                        ex.getMessage() != null ? ex.getMessage() : "AI service returned an error"));
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, String>> handleRestClient(RestClientException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "upstream_error", "message",
                        "Failed to communicate with AI service: " + ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "internal_error", "message", "An unexpected error occurred"));
    }
}
