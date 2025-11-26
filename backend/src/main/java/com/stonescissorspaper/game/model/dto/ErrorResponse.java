package com.stonescissorspaper.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard error response format")
public class ErrorResponse {

    @Schema(description = "HTTP status code")
    private int status;

    @Schema(description = "Error type")
    private String error;

    @Schema(description = "Detailed error message")
    private String message;

    @Schema(description = "API path where the error occurred")
    private String path;

    @Schema(description = "Timestamp when the error occurred")
    private Instant timestamp;

    /**
     * Creates an error response
     */
    public static ErrorResponse of(int status, String error, String message, String path) {
        return ErrorResponse.builder()
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .timestamp(Instant.now())
                .build();
    }
}
