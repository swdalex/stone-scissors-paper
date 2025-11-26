package com.stonescissorspaper.game.service;

import com.stonescissorspaper.game.model.dto.GameRequest;
import com.stonescissorspaper.game.model.dto.GameResponse;
import com.stonescissorspaper.game.model.dto.GameResult;
import com.stonescissorspaper.game.model.dto.SessionInfo;
import com.stonescissorspaper.game.model.enums.Move;
import com.stonescissorspaper.game.model.enums.Result;
import com.stonescissorspaper.game.model.exception.InvalidMoveException;
import com.stonescissorspaper.game.model.exception.SessionNotFoundException;
import com.stonescissorspaper.game.util.GameRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {

    private final SessionService sessionService;
    private final ComputerMoveService computerMoveService;

    public GameResponse startNewGame() {
        String sessionId = UUID.randomUUID().toString();
        SessionInfo session = sessionService.createSession(sessionId);

        log.info("Started new game session: {}", sessionId);

        return GameResponse.success("Game session created successfully", session);
    }

    public GameResponse playMove(String sessionId, GameRequest request) {
        validateSession(sessionId);
        validatePlayerMove(request.getPlayerMove());

        Move playerMove = Move.valueOf(request.getPlayerMove().toUpperCase());
        Move computerMove = computerMoveService.generateComputerMove();

        Result result = GameRules.determineWinner(playerMove, computerMove);

        GameResult gameResult = buildGameResult(playerMove, computerMove, result);
        SessionInfo updatedSession = sessionService.recordGameResult(sessionId, gameResult);

        log.info("Game played - Session: {}, Player: {}, Computer: {}, Result: {}",
                sessionId, playerMove, computerMove, result);

        return GameResponse.success("Move processed successfully", updatedSession, gameResult);
    }

    public GameResponse getSessionInfo(String sessionId) {
        SessionInfo session = sessionService.getSession(sessionId)
                .orElseThrow(() -> new SessionNotFoundException("Session not found: " + sessionId));

        return GameResponse.success("Session info retrieved successfully", session);
    }

    private void validateSession(String sessionId) {
        if (!sessionService.sessionExists(sessionId)) {
            throw new SessionNotFoundException("Session not found: " + sessionId);
        }
    }

    private void validatePlayerMove(String playerMove) {
        if (playerMove == null || playerMove.trim().isEmpty()) {
            throw new InvalidMoveException("Player move cannot be null or empty");
        }

        try {
            Move.valueOf(playerMove.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidMoveException("Invalid move: " + playerMove);
        }
    }

    private GameResult buildGameResult(Move playerMove, Move computerMove, Result result) {
        return GameResult.builder()
                .playerMove(playerMove)
                .computerMove(computerMove)
                .result(result)
                .message(generateResultMessage(playerMove, computerMove, result))
                .timestamp(Instant.now())
                .build();
    }

    private String generateResultMessage(Move playerMove, Move computerMove, Result result) {
        return switch (result) {
            case WIN -> String.format("%s beats %s - You win!", playerMove, computerMove);
            case LOSE -> String.format("%s beats %s - Computer wins!", computerMove, playerMove);
            case DRAW -> String.format("Both chose %s - It's a draw!", playerMove);
            default -> "Unknown result";
        };
    }
}