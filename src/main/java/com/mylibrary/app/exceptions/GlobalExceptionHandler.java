package com.mylibrary.app.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mylibrary.app.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Form validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<Map<String, String>>>> handleValidationExceptions(MethodArgumentNotValidException ex){
        List<Map<String, String>> errorsList = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            Map<String, String> errorMessageMap = new HashMap<>();
            errorMessageMap.put(fieldName, errorMessage);

            errorsList.add(errorMessageMap);
        });

        Map<String, List<Map<String, String>>> errorsMap = new HashMap<>();
        errorsMap.put("errors", errorsList);
        return new ResponseEntity<Map<String, List<Map<String, String>>>>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // Resource not fond exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public void internalAuthenticationServiceException(InternalAuthenticationServiceException ex){
        System.out.println(ex.getMessage());
        System.out.println(ex.getCause());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex){
        return new ResponseEntity<ApiResponse>(new ApiResponse(ex.getMessage(), false), HttpStatus.NOT_FOUND);
    }
}
