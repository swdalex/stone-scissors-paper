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
@Schema(description = "Game session information and statistics")
public class SessionInfo {

    @Schema(description = "Unique session identifier")
    private String sessionId;

    @Schema(description = "Total number of games played in this session")
    private int gamesPlayed;

    @Schema(description = "Number of games won by the player")
    private int playerWins;

    @Schema(description = "Number of games won by the computer")
    private int computerWins;

    @Schema(description = "Number of games that ended in a draw")
    private int draws;

    @Schema(description = "Player win percentage")
    private double playerWinPercentage;

    @Schema(description = "When the session was created")
    private Instant createdAt;

    @Schema(description = "Last activity timestamp")
    private Instant lastActivity;

}
