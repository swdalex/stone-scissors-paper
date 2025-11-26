package com.stonescissorspaper.game.controller;

import com.stonescissorspaper.game.model.dto.GameRequest;
import com.stonescissorspaper.game.model.dto.GameResponse;
import com.stonescissorspaper.game.model.enums.Move;
import com.stonescissorspaper.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
@Tag(name = "Game Controller", description = "APIs for playing Stone Scissors Paper")
public class GameController {

    private final GameService gameService;

    @Operation(summary = "Start a new game session", description = "Creates a new game session and returns session ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game session created successfully",
                    content = @Content(schema = @Schema(implementation = GameResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/start")
    public ResponseEntity<GameResponse> startNewGame() {
        log.info("Received request to start new game session");

        GameResponse response = gameService.startNewGame();
        log.debug("Started new game session: {}", response.getSessionInfo().getSessionId());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Play a move", description = "Submit a player move and get the game result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Move processed successfully",
                    content = @Content(schema = @Schema(implementation = GameResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid move or request"),
            @ApiResponse(responseCode = "404", description = "Session not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/play")
    public ResponseEntity<GameResponse> playMove(
            @Parameter(description = "Game session ID") @RequestParam(required = false) String sessionId,
            @Valid @RequestBody GameRequest request) {

        // Use sessionId from request body if provided, otherwise use query parameter
        String effectiveSessionId = request.getSessionId() != null ? request.getSessionId() : sessionId;

        log.info("Received move request - Session: {}, Move: {}", effectiveSessionId, request.getPlayerMove());

        GameResponse response = gameService.playMove(effectiveSessionId, request);
        log.debug("Processed move - Session: {}, Result: {}",
                effectiveSessionId, response.getGameResult().getResult());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get session info", description = "Retrieve current session information and statistics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session info retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GameResponse.class))),
            @ApiResponse(responseCode = "404", description = "Session not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<GameResponse> getSessionInfo(
            @Parameter(description = "Game session ID") @PathVariable String sessionId) {

        log.debug("Retrieving session info: {}", sessionId);

        GameResponse response = gameService.getSessionInfo(sessionId);
        log.debug("Retrieved session info: {}", sessionId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get game rules", description = "Returns the rules of the Game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rules retrieved successfully")
    })
    @GetMapping("/rules")
    public ResponseEntity<String> getGameRules() {
        log.debug("Retrieving game rules");

        String rules = """
            Stone Scissors Paper Rules:
            - Stone crushes Scissors (and wins)
            - Scissors cuts Paper (and wins)
            - Paper covers Stone (and wins)
            - Same moves result in a draw
            """;

        return ResponseEntity.ok(rules);
    }

    @Operation(summary = "Get available moves", description = "Returns all available moves in the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moves retrieved successfully")
    })
    @GetMapping("/moves")
    public ResponseEntity<List<String>> getAvailableMoves() {
        log.debug("Retrieving available moves");

        List<String> moves = Arrays.stream(Move.values()).map(Move::getValue).collect(Collectors.toList());
        return ResponseEntity.ok(moves);
    }
}