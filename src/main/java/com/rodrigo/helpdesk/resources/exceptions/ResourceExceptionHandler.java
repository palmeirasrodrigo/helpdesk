package com.rodrigo.helpdesk.resources.exceptions;

import com.rodrigo.helpdesk.service.exceptions.DataIntegrityViolationException;
import com.rodrigo.helpdesk.service.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
        StandardError error = StandardError.builder().timestamp(System.currentTimeMillis()).status(HttpStatus.NOT_FOUND.value())
                .error("Object not found").message(ex.getMessage()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        StandardError error = StandardError.builder().timestamp(System.currentTimeMillis()).status(HttpStatus.BAD_REQUEST.value())
                .error("Violação de dados").message(ex.getMessage()).path(request.getRequestURI()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ValidationsError errors = new ValidationsError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "validation error", "Erro na validação dos campos", request.getRequestURI());

        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.addErrors(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> validationErrors(ConstraintViolationException ex, HttpServletRequest request) {
        ValidationsError errors = new ValidationsError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "validation error", "Erro na validação dos campos", request.getRequestURI());

        for (ConstraintViolation<?> x : ex.getConstraintViolations()) {
            errors.addErrors(x.getPropertyPath().toString(), x.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
