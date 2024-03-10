package br.com.rinha.exception;

import br.com.rinha.models.Exception.UsuarioJaRegistrado;
import br.com.rinha.models.Exception.UsuarioNaoEncontrado;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioJaRegistrado.class)
    public ResponseEntity<String> handleMinhaExcecaoPersonalizada(UsuarioJaRegistrado e) {
        // Você pode usar o status que achar mais adequado para sua exceção
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY) // Use o código de status adequado
                .body(e.toString());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, String> handleValidationExceptions(WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(UsuarioNaoEncontrado.class)
    public ResponseEntity<String> handleMinhaExcecaoUsuarioNaoEncontrado(UsuarioNaoEncontrado e) {
        // Você pode usar o status que achar mais adequado para sua exceção
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // Use o código de status adequado
                .body(e.toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        // Você pode usar o status que achar mais adequado para sua exceção
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY) // Use o código de status adequado
                .body(e.toString());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleApelidoDuplicado(DataIntegrityViolationException e) {
        // Você pode usar o status que achar mais adequado para sua exceção
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY) // Use o código de status adequado
                .body(e.getCause().getMessage());
    }


}