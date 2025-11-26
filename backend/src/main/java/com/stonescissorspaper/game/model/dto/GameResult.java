package com.stonescissorspaper.game.model.dto;

import com.stonescissorspaper.game.model.enums.Move;
import com.stonescissorspaper.game.model.enums.Result;
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
@Schema(description = "Detailed result of a single game round")
public class GameResult {

    @Schema(description = "The move chosen by the player")
    private Move playerMove;

    @Schema(description = "The move chosen by the computer")
    private Move computerMove;

    @Schema(description = "The result of the game from player's perspective")
    private Result result;

    @Schema(description = "Human-readable description of the result")
    private String message;

    @Schema(description = "When the game was played")
    private Instant timestamp;
}
