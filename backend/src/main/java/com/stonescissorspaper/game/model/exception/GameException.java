package com.stonescissorspaper.game.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GameException extends RuntimeException {

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(String operation, String reason) {
        super(String.format("Game operation failed: %s. Reason: %s", operation, reason));
    }
}
