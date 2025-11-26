package com.stonescissorspaper.game.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(String message) {
        super(message);
    }

    public SessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionNotFoundException(String sessionId, String additionalInfo) {
        super(String.format("Session not found: %s. %s", sessionId, additionalInfo));
    }
}
