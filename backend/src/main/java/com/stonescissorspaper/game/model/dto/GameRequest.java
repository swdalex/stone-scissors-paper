package com.stonescissorspaper.game.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for submitting a player move")
public class GameRequest {

    @NotBlank(message = "Player move is required")
    @Pattern(regexp = "^(?i)(STONE|SCISSORS|PAPER)$",
            message = "Player move must be one of: STONE, SCISSORS, PAPER")
    @Schema(
            description = "The player's move",
            example = "STONE",
            allowableValues = {"STONE", "SCISSORS", "PAPER"}
    )
    private String playerMove;

    @Schema(
            description = "Optional session ID for multi-game sessions",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private String sessionId;
}