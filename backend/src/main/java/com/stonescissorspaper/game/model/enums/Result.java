package com.stonescissorspaper.game.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Result {
    WIN("win"),
    LOSE("lose"),
    DRAW("draw");

    private final String value;

    Result(String value) {
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

    public static Result fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Result value cannot be null");
        }

        for (Result result : Result.values()) {
            if (result.value.equalsIgnoreCase(value.trim())) {
                return result;
            }
        }

        throw new IllegalArgumentException("Unknown result: " + value);
    }
}