package com.stonescissorspaper.game.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Move {
    STONE("STONE"),
    SCISSORS("SCISSORS"),
    PAPER("PAPER");

    private final String value;

    Move(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static Move fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Move value cannot be null");
        }

        for (Move move : Move.values()) {
            if (move.value.equalsIgnoreCase(value.trim())) {
                return move;
            }
        }

        throw new IllegalArgumentException("Unknown move: " + value);
    }
}