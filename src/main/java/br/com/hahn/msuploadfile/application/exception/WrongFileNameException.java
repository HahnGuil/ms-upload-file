package br.com.hahn.msuploadfile.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongFileNameException extends RuntimeException {
    public WrongFileNameException(String message) {
        super(message);
    }
}
