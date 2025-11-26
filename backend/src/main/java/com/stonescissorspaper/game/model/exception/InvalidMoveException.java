package com.stonescissorspaper.game.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMoveException extends RuntimeException {

    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMoveException(String move, String validMoves) {
        super(String.format("Invalid move: '%s'. Valid moves are: %s", move, validMoves));
    }
}