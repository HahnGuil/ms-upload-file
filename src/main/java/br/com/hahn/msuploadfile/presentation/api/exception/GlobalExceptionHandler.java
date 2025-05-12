package br.com.hahn.msuploadfile.presentation.api.exception;

import br.com.hahn.msuploadfile.application.exception.FileEmptyException;
import br.com.hahn.msuploadfile.application.exception.WrongFileNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<Map<String, String>> handlerFileEmptyException(FileEmptyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(WrongFileNameException.class)
    public ResponseEntity<Map<String, String>>  handlerWrongFileNameException(WrongFileNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

}
