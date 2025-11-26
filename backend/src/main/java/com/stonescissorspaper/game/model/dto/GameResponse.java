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
@Schema(description = "Standard API response wrapper")
public class GameResponse {

    @Schema(description = "Indicates if the request was successful")
    private boolean success;

    @Schema(description = "Response message")
    private String message;

    @Schema(description = "Session information and statistics")
    private SessionInfo sessionInfo;

    @Schema(description = "Detailed game result")
    private GameResult gameResult;

    @Schema(description = "Timestamp of the response")
    private Instant timestamp;

    /**
     * Creates a successful response with session info
     */
    public static GameResponse success(String message, SessionInfo sessionInfo) {
        return GameResponse.builder()
                .success(true)
                .message(message)
                .sessionInfo(sessionInfo)
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Creates a successful response with session info and game result
     */
    public static GameResponse success(String message, SessionInfo sessionInfo, GameResult gameResult) {
        return GameResponse.builder()
                .success(true)
                .message(message)
                .sessionInfo(sessionInfo)
                .gameResult(gameResult)
                .timestamp(Instant.now())
                .build();
    }

    /**
     * Creates an error response
     */
    public static GameResponse error(String message) {
        return GameResponse.builder()
                .success(false)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }
}
